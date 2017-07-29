package com.medical.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.Circle;
import com.google.gson.Gson;
import com.medical.adapter.PastAppointmentFragmentAdapter;
import com.medical.doctfin.PagerSlidingTabStrip;
import com.medical.doctfin.R;
import com.medical.model.PatientAppointmentModel;
import com.medical.model.PatientAppointmentResponse;
import com.medical.ratrofitinterface.DoctFin;
import com.medical.utils.ConnectionDetector;
import com.medical.utils.GetAllUrl;
import com.medical.utils.TagClass;
import com.medical.utils.UserSharedPreference;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PastAppoinmentsFragment extends Fragment {
	private View view;
	private GetAllUrl url;
	public static ViewPager viewpager;
	private PagerSlidingTabStrip tabSlidingStrip;
	private SharedPreferences sharedpreferences;
	ArrayList<PatientAppointmentModel> patientAppointmentArrayList = new ArrayList<>();
	private UserSharedPreference userSharedPreference;
	private String doctProfileModelString;
	private Gson doctProfileGson;
	private TextView loginTextView;
	private LinearLayout appointmentLinearLayout, loginLinearLayout;
	private ConnectionDetector connectionDetector;
	private boolean isConnectionActive = false;

	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.past_appointment_fragment, container,
				false);
		connectionDetector = new ConnectionDetector(getActivity());
		isConnectionActive = connectionDetector.isConnectingToInternet();

		doctProfileGson = new Gson();
		loginTextView = (TextView) view.findViewById(R.id.loginTextView);
		loginTextView.setOnClickListener(loginClick);
		appointmentLinearLayout = (LinearLayout) view
				.findViewById(R.id.appointmentLinearLayout);
		loginLinearLayout = (LinearLayout) view
				.findViewById(R.id.loginLinearLayout);
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
				"Appointments");
		((ActionBarActivity) getActivity()).getSupportActionBar().setElevation(
				8);
		viewpager = (ViewPager) view.findViewById(R.id.pastAppoViewPager);

		tabSlidingStrip = (PagerSlidingTabStrip) view
				.findViewById(R.id.pastAppoTab);
		userSharedPreference = new UserSharedPreference(getActivity());
		sharedpreferences = getActivity().getSharedPreferences(
				TagClass.UserArrayList, getActivity().MODE_PRIVATE);

		url = new GetAllUrl();

		return view;
	}

	public int getItem(int i) {
		return viewpager.getCurrentItem() + i;
	}

	OnClickListener loginClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

		}
	};

	@Override
	public void onResume() {
		super.onResume();
		checkUserSharePref();
	}

	private void setRestAdapter() {
		if (!isConnectionActive) {
			initAlertDialog();
		} else {
			RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
					url.getUrl()).build();
			DoctFin doctFinInterFace = restAdapter.create(DoctFin.class);

			final ProgressDialog pDialog = ProgressDialog.show(
					view.getContext(), "", "", true);

			pDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));

			pDialog.setContentView(R.layout.dialog);

			ProgressBar progressBar = (ProgressBar) pDialog
					.findViewById(R.id.progressWheel);
			Circle circle = new Circle();

			circle.setColor(getResources().getColor(R.color.white));
			progressBar.setIndeterminateDrawable(circle);
			doctFinInterFace.getPatientAppointmentList(
					3015,
					new Callback<PatientAppointmentResponse>() {

						@Override
						public void failure(RetrofitError error) {
							if (pDialog.isShowing())
								pDialog.dismiss();
						}

						@Override
						public void success(PatientAppointmentResponse model,
								Response response) {
							try {
								if (model.getErrorCode() == 0) {
									patientAppointmentArrayList = new ArrayList<PatientAppointmentModel>();
									if (model.getCustomerAppointmentData()
											.size() != 0) {
										patientAppointmentArrayList.addAll(model
												.getCustomerAppointmentData());
										if (pDialog.isShowing())
											pDialog.dismiss();

										viewpager
												.setAdapter(new PastAppointmentFragmentAdapter(
														getChildFragmentManager(),
														patientAppointmentArrayList));

										tabSlidingStrip
												.setTextColor(getResources().getColor(R.color.colorPrimary));

										tabSlidingStrip.setIndicatorColor(getResources().getColor(R.color.colorPrimary));
										tabSlidingStrip.setBackgroundColor(Color.WHITE);
										tabSlidingStrip.setViewPager(viewpager);

									}
								}
							} catch (Exception ex) {
								Log.i("Exception", ex.toString());
							}
						}

					});
		}
	}

	public void checkUserSharePref() {
			doctProfileModelString = sharedpreferences.getString(TagClass.UserArrayList, null);
				setRestAdapter();
			appointmentLinearLayout.setVisibility(View.VISIBLE);
			loginLinearLayout.setVisibility(View.GONE);
	}

	private void initAlertDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();
		alertDialog.setTitle(getResources().getString(
				R.string.internet_error_header));
		alertDialog.setMessage(getResources().getString(
				R.string.internet_error_string));
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				java.lang.System.exit(0);
				isConnectionActive = false;
			}
		});
		alertDialog.show();
	}
}
