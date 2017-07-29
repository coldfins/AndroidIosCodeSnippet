package com.medical.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medical.adapter.AppointmentAdapter;
import com.medical.doctfin.R;
import com.medical.model.PatientAppointmentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PastAppointmentFragment extends Fragment {
	private int fragmentPage;
	private ArrayList<PatientAppointmentModel> patientAppointmentArrayList;
	private ArrayList<PatientAppointmentModel> pastAppointmentArrayList;
	public static final String ARG_PAGE = "ARG_PAGE";
	private RecyclerView pastAppointmentRecyclerView;
	private CardView pastAppointmentCardView;
	private Calendar c;
	private View view;
	private SimpleDateFormat df;
	private String formattedDate;

	public static Fragment newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		PastAppointmentFragment fragment = new PastAppointmentFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.past_appo_fragment, container, false);
		setResources();
		getDataFromBundle();

		fillRecyclerAdapter();
		return view;
	}

	private void getDataFromBundle() {
		Bundle bundle = getArguments();
		if (bundle != null) {

			patientAppointmentArrayList = (ArrayList<PatientAppointmentModel>) bundle
					.getSerializable("pastPatientArraylist");

		}

	}

	private void setResources() {
		pastAppointmentRecyclerView = (RecyclerView) view
				.findViewById(R.id.pastAppointmentRecyleView);
		pastAppointmentCardView = (CardView) view
				.findViewById(R.id.pastAppointmentCardView);
		pastAppointmentRecyclerView.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		pastAppointmentRecyclerView.setLayoutManager(llm);
		patientAppointmentArrayList = new ArrayList<PatientAppointmentModel>();
		pastAppointmentArrayList = new ArrayList<PatientAppointmentModel>();
		c = Calendar.getInstance();
		df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		formattedDate = df.format(c.getTime());
	}

	private void fillRecyclerAdapter() {
		try {
			for (int i = 0; i < patientAppointmentArrayList.size(); i++) {

				String dtStart = patientAppointmentArrayList.get(i)
						.getAppointmentDate();
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date;
				date = dtFormat.parse(dtStart);

				SimpleDateFormat currentFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date date2;
				date2 = currentFormat.parse(formattedDate);

				if (date.before(date2)) {
					if (((patientAppointmentArrayList.get(i).getAttend() == 2) || (patientAppointmentArrayList
							.get(i).getAttend() == 1))) {
						Log.i("DateFormat", dtFormat.format(date).toString());
						pastAppointmentArrayList
								.add(patientAppointmentArrayList.get(i));
						Log.i("todaycnt", " " + pastAppointmentArrayList.size());
						AppointmentAdapter adapter = new AppointmentAdapter(
								pastAppointmentArrayList, getActivity(), 2);
						pastAppointmentRecyclerView.setAdapter(adapter);
						if(pastAppointmentArrayList.size()>0)
						{
							pastAppointmentRecyclerView.setVisibility(View.VISIBLE);
							pastAppointmentCardView.setVisibility(View.GONE);
						}
						else
						{
							pastAppointmentRecyclerView.setVisibility(View.GONE);
							pastAppointmentCardView.setVisibility(View.VISIBLE);
						}
					}
				}
			}
		} catch (Exception ex) {
			Log.i("Error", ex.toString());
		}
	}

}
