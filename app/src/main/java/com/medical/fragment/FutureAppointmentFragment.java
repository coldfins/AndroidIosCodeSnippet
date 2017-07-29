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
import java.util.Locale;

public class FutureAppointmentFragment extends Fragment {
	public static final String ARG_PAGE = "ARG_PAGE";
	private ArrayList<PatientAppointmentModel> patientAppointmentArrayList;
	private ArrayList<PatientAppointmentModel> futureAppointmentArrayList;

	private View view;
	private RecyclerView futureAppointmentRecyclerView;
	private CardView futureAppointmentCardView;
	private Calendar c;

	private SimpleDateFormat df;
	private String formattedDate;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.past_appo_fragment, container, false);

		setResources();
		getDataFromBundle();
		fillRecyclerAdapter();

		return view;
	}

	private void setResources() {
		futureAppointmentRecyclerView = (RecyclerView) view
				.findViewById(R.id.pastAppointmentRecyleView);
		futureAppointmentCardView = (CardView) view
				.findViewById(R.id.pastAppointmentCardView);
		futureAppointmentRecyclerView.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		futureAppointmentRecyclerView.setLayoutManager(llm);
		patientAppointmentArrayList = new ArrayList<PatientAppointmentModel>();
		futureAppointmentArrayList = new ArrayList<PatientAppointmentModel>();
		c = Calendar.getInstance();
		df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
		formattedDate = df.format(c.getTime());
	}

	private void getDataFromBundle() {
		Bundle bundle = getArguments();
		if (bundle != null) {
			patientAppointmentArrayList = (ArrayList<PatientAppointmentModel>) bundle
					.getSerializable("futurePatientArraylist");
		}
	}

	private void fillRecyclerAdapter() {
		try {
			Log.i("Futurecnt", " " + patientAppointmentArrayList.size());
			for (int i = 0; i < patientAppointmentArrayList.size(); i++) {
				String dtStart = patientAppointmentArrayList.get(i)
						.getAppointmentDate();
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd",
						Locale.US);
				Date date;

				date = dtFormat.parse(dtStart);
				dtFormat.format(date).toString();
				SimpleDateFormat currentFormat = new SimpleDateFormat(
						"yyyy-MM-dd", Locale.US);
				Date date2;
				date2 = currentFormat.parse(formattedDate);
				if (date.after(date2)) {
					if (((patientAppointmentArrayList.get(i).getAttend() == 2)
							|| (patientAppointmentArrayList.get(i).getAttend() == 1) || (patientAppointmentArrayList
							.get(i).getAttend() == 0))) {
						futureAppointmentArrayList
								.add(patientAppointmentArrayList.get(i));

						AppointmentAdapter adapter = new AppointmentAdapter(
								futureAppointmentArrayList, getActivity(), 1);
						futureAppointmentRecyclerView.setAdapter(adapter);
						if (futureAppointmentArrayList.size() > 0) {
							futureAppointmentCardView.setVisibility(View.GONE);
							futureAppointmentRecyclerView
									.setVisibility(View.VISIBLE);
						} else {
							futureAppointmentCardView
									.setVisibility(View.VISIBLE);
							futureAppointmentRecyclerView
									.setVisibility(View.GONE);
						}
					}
				}
			}
		} catch (Exception ex) {
			Log.i("Error", ex.toString());
		}

	}

}
