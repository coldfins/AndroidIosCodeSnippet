package com.medical.fragment;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.Circle;
import com.medical.adapter.AppointmentAdapter;
import com.medical.doctfin.R;
import com.medical.model.PatientAppointmentModel;
import com.medical.utils.GetAllUrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TodayAppointmentFragment extends Fragment {
	private int fragmentPage;
	public static final String ARG_PAGE = "ARG_PAGE";
	private RecyclerView todayAppointmentRecyclerView;
	private ArrayList<PatientAppointmentModel> patientAppointmentArrayList;
	private ArrayList<PatientAppointmentModel> todayAppointmentArrayList;
	private CardView todayAppointmentCardView;
	private GetAllUrl url;
	private View view;
	private Calendar c;
	private SimpleDateFormat df;
	private String formattedDate;
	private ProgressDialog pDialog;
	PastAppoinmentsFragment p;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.today_appointmentt_fragment,
				container, false);

		setResources();
		getDataFromBundle();
		fillRecyclerAdapter();

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void setResources() {
		p = new PastAppoinmentsFragment();
		todayAppointmentRecyclerView = (RecyclerView) view
				.findViewById(R.id.todayAppointmentRecyleView);
		todayAppointmentCardView = (CardView) view
				.findViewById(R.id.todayAppointmentCardView);
		todayAppointmentRecyclerView.setHasFixedSize(true);
		url = new GetAllUrl();
		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		todayAppointmentRecyclerView.setLayoutManager(llm);
		patientAppointmentArrayList = new ArrayList<PatientAppointmentModel>();
		todayAppointmentArrayList = new ArrayList<PatientAppointmentModel>();
		c = Calendar.getInstance();
		df = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate = df.format(c.getTime());

	}

	private void getDataFromBundle() {
		Bundle bundle = getArguments();
		if (bundle != null) {

			patientAppointmentArrayList = (ArrayList<PatientAppointmentModel>) bundle
					.getSerializable("todayPatientArraylist");
		}
	}

	private void fillRecyclerAdapter() {
		try {
			pDialog = ProgressDialog.show(view.getContext(), "", "", true);

			pDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));

			pDialog.setContentView(R.layout.dialog);

			ProgressBar progressBar = (ProgressBar) pDialog
					.findViewById(R.id.progressWheel);
			Circle circle = new Circle();

			circle.setColor(getResources().getColor(R.color.white));
			progressBar.setIndeterminateDrawable(circle);

			for (int i = 0; i < patientAppointmentArrayList.size(); i++) {

				String dtStart = patientAppointmentArrayList.get(i)
						.getAppointmentDate();
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date;
				date = dtFormat.parse(dtStart);
				String formattedDates = dtFormat.format(date).toString();

				if (formattedDate.equals(formattedDates)) {
					if (((patientAppointmentArrayList.get(i).getAttend() == 2)
							|| (patientAppointmentArrayList.get(i).getAttend() == 1) || (patientAppointmentArrayList
							.get(i).getAttend() == 0))) {
						Log.i("DateFormat", dtFormat.format(date).toString());
						todayAppointmentArrayList
								.add(patientAppointmentArrayList.get(i));

					}
				}
			}

			if (todayAppointmentArrayList.size() > 0) {
				todayAppointmentRecyclerView.setVisibility(View.VISIBLE);
				todayAppointmentCardView.setVisibility(View.GONE);
				AppointmentAdapter adapter = new AppointmentAdapter(
						todayAppointmentArrayList, getActivity(), 0);

				todayAppointmentRecyclerView.setAdapter(adapter);
			} else {

				todayAppointmentRecyclerView.setVisibility(View.GONE);
				todayAppointmentCardView.setVisibility(View.VISIBLE);

			}

			if (pDialog.isShowing())
				pDialog.dismiss();
		} catch (Exception ex) {
			Log.i("Error", ex.toString());
		}

	}

}
