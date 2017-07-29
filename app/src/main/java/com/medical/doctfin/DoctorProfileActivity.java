package com.medical.doctfin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.medical.model.DisplayDoctProfileModel;
import com.medical.model.DisplayDoctProfileResponse;
import com.medical.model.SpecialistSubCategoryModel;
import com.medical.ratrofitinterface.DoctFin;
import com.medical.utils.ConnectionDetector;
import com.medical.utils.GetAllUrl;
import com.medical.utils.UserSharedPreference;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

@SuppressLint("NewApi")
public class DoctorProfileActivity extends ActionBarActivity implements
		LocationListener, OnMapClickListener {

	private TextView doctNameTextView, doctDegreeTextView, experianceTextView,
			doctAddressTextView, doctContactNoTextView, doctClinicTimeTextView,
			doctConsultFeeTextView, doctCategoryTextView,
			timeAvailableTextView, doctSubCategoryTextView, calenderTextView;
	private Gson doctProfileGson;
	private Spinner subSpecialistSpinner;
	private ImageView profilePicImageView;
	private int total;
	private Button bookAppointmentButton;
	private String doctProfileModelString;
	private GetAllUrl url;
	private GoogleMap googleMap;
	private DatePickerDialog datePickerDialog;
	private Calendar newCalander;
	private UserSharedPreference userSharedPreference;
	private SimpleDateFormat dateFormatter;
	private ArrayList<SpecialistSubCategoryModel> restSpecialistSubCatArrayList;
	private Toolbar displayToolbar;
	private double latitude, longitude;
	private String doctIdString, dateStringFormat, tempDateString;
	private CardView mapCardView;
	private LinearLayout timeSlotLinear;
	private DisplayDoctProfileModel doctProfileModel;
	private int catIdInt,subCatId;
	private String appoinmentDate;
	private ConnectionDetector connectionDetector;
	private boolean isConnectionActive = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (!isGooglePlayServicesAvailable()) {
			finish();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_profile);

		setToolBar();

		connectionDetector = new ConnectionDetector(this);
		isConnectionActive = connectionDetector.isConnectingToInternet();
		if (!isConnectionActive) {
			initAlertDialog();
		} else {
			getIntentIfAvailable();
			setResources();
			setRestAdapter();
		}
		// getAppointmentTimeListOfDoctor();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.doctor_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (item.getItemId() == R.id.homeAppointment) {
			finish();
			Intent homeActivity = new Intent(this, UserHomeActivity.class);
			startActivity(homeActivity);
			overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setResources() {
		userSharedPreference = new UserSharedPreference(this);
		url = new GetAllUrl();
		newCalander = Calendar.getInstance();
		restSpecialistSubCatArrayList = new ArrayList<SpecialistSubCategoryModel>();
		doctNameTextView = (TextView) findViewById(R.id.doctNameTextView);
		doctDegreeTextView = (TextView) findViewById(R.id.doctDegreeTextView);
		experianceTextView = (TextView) findViewById(R.id.experianceYrTextView);
		doctAddressTextView = (TextView) findViewById(R.id.doctAddressTextView);
		bookAppointmentButton = (Button) findViewById(R.id.bookAppointmentButton);
		doctProfileGson = new Gson();
		doctProfileModel = new DisplayDoctProfileModel();
		// doctContactNoTextView = (TextView)
		// findViewById(R.id.doctContactNoTextView);
		// doctClinicTimeTextView = (TextView)
		// findViewById(R.id.clinicTimeTextView);
		timeAvailableTextView = (TextView) findViewById(R.id.timeAvailableTextView);
		timeAvailableTextView.setOnClickListener(timeAvailableClick);
		// timeSlotLinear = (LinearLayout)
		// findViewById(R.id.timeslotLinearLayout);
		timeAvailableTextView.setOnTouchListener(new CustomTouchListener());
		doctConsultFeeTextView = (TextView) findViewById(R.id.consultFeeTextView);
		doctCategoryTextView = (TextView) findViewById(R.id.doctCategoryTextView);
		doctSubCategoryTextView = (TextView) findViewById(R.id.doctSubCategoryTextView);
		profilePicImageView = (ImageView) findViewById(R.id.profilePicImageView);
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		bookAppointmentButton.setOnClickListener(bookAppointmentClick);
		// subSpecialistSpinner = (Spinner) findViewById(R.id.subCatSpinner);
		// calenderTextView = (TextView) findViewById(R.id.calenderTextView);
		// calenderTextView.setOnClickListener(calenderClick);
		// mapCardView = (CardView) findViewById(R.id.mapCardView);
		// setSubCatRestAdapter();

		/*
		 * Map SupportMapFragment supportMapFragment = (SupportMapFragment)
		 * getSupportFragmentManager() .findFragmentById(R.id.mapFragment);
		 * 
		 * googleMap = supportMapFragment.getMap();
		 * googleMap.setMyLocationEnabled(true); LocationManager locationManager
		 * = (LocationManager) getSystemService(LOCATION_SERVICE); Criteria
		 * criteria = new Criteria(); String bestProvider =
		 * locationManager.getBestProvider(criteria, true); Location location =
		 * locationManager.getLastKnownLocation(bestProvider); if (location !=
		 * null) { onLocationChanged(location); }
		 * locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
		 * googleMap.setOnMapClickListener(this);
		 */

		// googleMap.setOnMapClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent=new Intent(getBaseContext(),MapFragmentActivity.class);
		// startActivity(intent);
		// }
		// });

	}

	OnClickListener calenderClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			datePickerDialog.show();

		}
	};
	OnClickListener timeAvailableClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			// TODO Auto-generated method stub
			doctProfileModelString = doctProfileGson.toJson(doctProfileModel);
			isConnectionActive = connectionDetector.isConnectingToInternet();
			if (!isConnectionActive) {
				initAlertDialogForCheckConnectivity();
			} else {
				Intent intent = new Intent(getApplicationContext(),
						DateAndTimeActivity.class);
				intent.putExtra("doctProfileModelString",
						doctProfileModelString);
				intent.putExtra("doctIdString", doctIdString);
				intent.putExtra("catIdInt", catIdInt);
				intent.putExtra("subCatId", subCatId);
				intent.putExtra("appoinmentDate", appoinmentDate);

				startActivityForResult(intent, 28);
				overridePendingTransition(R.anim.fadein, R.anim.exit_to_left);

			}

		}
	};

	public class CustomTouchListener implements View.OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((TextView) view).setTextColor(0xFF03A9F4); // Accent Color
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				((TextView) view).setTextColor(0xFF546E7A); // Application Blue
															// Color
				break;
			}
			return false;
		}
	}

	OnClickListener bookAppointmentClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// if (userSharedPreference.checkUserLogin()) {
			isConnectionActive = connectionDetector.isConnectingToInternet();
			if (!isConnectionActive) {
				initAlertDialogForCheckConnectivity();
			} else {

				doctProfileModelString = doctProfileGson
						.toJson(doctProfileModel);
				Intent intent = new Intent(getApplicationContext(),
						DateAndTimeActivity.class);
				intent.putExtra("doctProfileModelString",
						doctProfileModelString);
				intent.putExtra("doctIdString", doctIdString);
				intent.putExtra("catIdInt", catIdInt);
				intent.putExtra("appoinmentDate", appoinmentDate);
				startActivityForResult(intent, 28);
				overridePendingTransition(R.anim.fadein, R.anim.exit_to_left);
			}
		}
	};

	private void setToolBar() {
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setTitle("Book Appointment");
		setSupportActionBar(displayToolbar);
		displayToolbar.setNavigationIcon(R.drawable.back);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	private void setRestAdapter() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				url.getUrl()).build();
		DoctFin doctFinInterFace = restAdapter.create(DoctFin.class);

		final ProgressDialog mProgressDialog = ProgressDialog.show(this, "",
				"", true);
		mProgressDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		mProgressDialog.setContentView(R.layout.dialog);
		ProgressBar progressBar = (ProgressBar) mProgressDialog
				.findViewById(R.id.progressWheel);
		Circle circle = new Circle();
		circle.setColor(getResources().getColor(R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		doctFinInterFace.getDoctorProfile(Integer.parseInt(doctIdString),
				new Callback<DisplayDoctProfileResponse>() {
					@Override
					public void success(DisplayDoctProfileResponse model, Response response) {
						if (model.getErrorCode() == 0) {
							if (model.getDoctordetails() != null) {

								doctProfileModel = model.getDoctordetails();
								Picasso.with(getBaseContext())
										.load(model.getDoctordetails()
												.getUserImage())
										.placeholder(R.drawable.reguserimage)
										.error(R.drawable.reguserimage)
										.into(profilePicImageView);

								doctNameTextView.setText(model
										.getDoctordetails().getUserName()
										.toString());
								doctDegreeTextView.setText(model
										.getDoctordetails().getDegreeName()
										.toString() + " - ");
								doctCategoryTextView.setText(model
										.getDoctordetails().getCategoryName()
										.toString());
								doctSubCategoryTextView.setText(model
										.getDoctordetails()
										.getSubCategoryName().toString());
								experianceTextView.setText(model
										.getDoctordetails().getExperienceYear()
										.toString()
										+ " Year Experience");
								doctAddressTextView.setText(model
										.getDoctordetails().getClinicAddress()
										.toString());
								String startTime = model.getDoctordetails()
										.getClinicStartTime().toString();
								String endTime = model.getDoctordetails()
										.getClinicEndTime().toString();
								SimpleDateFormat startTime_24HourSDF = new SimpleDateFormat(
										"HH:mm");
								SimpleDateFormat startTime_12HourSDF = new SimpleDateFormat(
										"hh:mm a");
								try {
									Date startTimeDt = startTime_24HourSDF
											.parse(startTime);
									Date endTimeDt = startTime_24HourSDF
											.parse(endTime);

									timeAvailableTextView
											.setText(startTime_12HourSDF
													.format(startTimeDt)
													.toString()
													+ " - "
													+ startTime_12HourSDF
															.format(endTimeDt)
															.toString());

								} catch (ParseException e) {
									e.printStackTrace();
								}

								doctConsultFeeTextView.setText("$ "
										+ model.getDoctordetails()
												.getConsultationFees()
												.toString()
										+ " consultation fees");

								if (mProgressDialog.isShowing())
									mProgressDialog.dismiss();
							}
						}
					}

					@Override
					public void failure(RetrofitError error) {
						if (mProgressDialog.isShowing())
							mProgressDialog.dismiss();
					}
				});
	}

	private boolean isGooglePlayServicesAvailable() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (ConnectionResult.SUCCESS == status) {
			return true;
		} else {
			GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
			return false;
		}
	}

	private void getIntentIfAvailable() {
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			doctIdString = intent.getStringExtra("doctIdString");
			catIdInt = intent.getIntExtra("catId", 0);
			subCatId=intent.getIntExtra("subCatId", 0);
			appoinmentDate = intent.getStringExtra("appoinmentDate");
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
		super.onBackPressed();
	}

	@Override
	public void onMapClick(LatLng arg0) {
		Intent intent = new Intent(getBaseContext(), MapFragmentActivity.class);
		intent.putExtra("Latitude", latitude);
		intent.putExtra("Longitude", longitude);
		startActivityForResult(intent, 25);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 28) {
			if (data != null) {
				finish();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void initAlertDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

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

	private void initAlertDialogForCheckConnectivity() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(getResources().getString(
				R.string.internet_error_header));
		alertDialog.setMessage(getResources().getString(
				R.string.internet_error_string));
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				isConnectionActive = false;
			}
		});
		alertDialog.show();
	}
}
