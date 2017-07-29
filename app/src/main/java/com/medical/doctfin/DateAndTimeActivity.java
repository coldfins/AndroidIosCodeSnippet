package com.medical.doctfin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.Circle;
import com.google.gson.Gson;
import com.medical.model.AppointmentTimeResponse;
import com.medical.model.DisplayDoctProfileModel;
import com.medical.ratrofitinterface.DoctFin;
import com.medical.utils.ConnectionDetector;
import com.medical.utils.GetAllUrl;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DateAndTimeActivity extends ActionBarActivity {

	private Toolbar displayToolbar;
	private ImageView profilePicImageView;
	private GetAllUrl url;
	private DatePickerDialog datePickerDialog;
	private Calendar newCalander;
	private SimpleDateFormat dateFormatter;
	private TextView doctNameTextView, doctAddressTextView, calenderTextView;
	private String tempDateString, doctIdString, doctProfileModelString, appoinmentDate;
	private int total, catIdInt, subCatId;
	private LinearLayout timeSlotLinear, timeslotsLinearLayout;
	private DisplayDoctProfileModel doctProfileModel;
	private ScrollView dateAndTimeScrollView, timeScrollView;
	private ConnectionDetector connectionDetector;
	private boolean isConnectionActive = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_and_time);
		setToolBar();
		connectionDetector = new ConnectionDetector(this);
		isConnectionActive = connectionDetector.isConnectingToInternet();
		if (!isConnectionActive) {
			initAlertDialog();
		} else {
			getIntentIfAvailable();
			setResources();
			setDatePicker();
			setDateTimeField();
			setRestAdapter();
			getAppointmentTimeListOfDoctor();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.date_and_time, menu);

		return true;
	}

	private void getIntentIfAvailable() {
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			doctProfileModelString = intent.getStringExtra("doctProfileModelString");
			Gson doctProfileGson = new Gson();
			doctProfileModel = doctProfileGson.fromJson(doctProfileModelString, DisplayDoctProfileModel.class);
			doctIdString = intent.getStringExtra("doctIdString");
			catIdInt = intent.getIntExtra("catIdInt", 0);
			subCatId = intent.getIntExtra("subCatId", 0);
			appoinmentDate = intent.getStringExtra("appoinmentDate");
		}
	}

	private void setToolBar() {
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setTitle("Date And Time");
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

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
		super.onBackPressed();
	}

	private void setResources() {
		url = new GetAllUrl();
		newCalander = Calendar.getInstance();
		doctNameTextView = (TextView) findViewById(R.id.doctNameTextView);
		doctAddressTextView = (TextView) findViewById(R.id.doctAddressTextView);
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		profilePicImageView = (ImageView) findViewById(R.id.profilePicImageView);
		calenderTextView = (TextView) findViewById(R.id.calenderTextView);
		calenderTextView.setOnClickListener(calenderClick);
		calenderTextView.setOnTouchListener(new CustomTouchListener());
		dateAndTimeScrollView = (ScrollView) findViewById(R.id.dateAndTimeScrollView);
		timeScrollView = (ScrollView) findViewById(R.id.timeScrollView);
		timeSlotLinear = (LinearLayout) findViewById(R.id.timeslotLinearLayout);
		dateAndTimeScrollView.setOnTouchListener(dateAndTimeScrollViewTouch);
		timeslotsLinearLayout = (LinearLayout) findViewById(R.id.timeslotsLinearLayout);
		timeslotsLinearLayout.setOnTouchListener(timeScrollViewTouch);
	}

	OnClickListener calenderClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			datePickerDialog.show();
		}
	};

	OnTouchListener dateAndTimeScrollViewTouch = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			timeScrollView.getParent().requestDisallowInterceptTouchEvent(false);
			return false;
		}
	};

	OnTouchListener timeScrollViewTouch = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			v.getParent().requestDisallowInterceptTouchEvent(true);
			return false;
		}
	};

	private void setDatePicker() {
		calenderTextView = (TextView) findViewById(R.id.calenderTextView);
		calenderTextView.setInputType(InputType.TYPE_NULL);
		calenderTextView.requestFocus();
	}

	private void setDateTimeField() {
		try {
			long currentdateLong = System.currentTimeMillis();
			Date date = new Date(appoinmentDate);
			SimpleDateFormat sdf = new SimpleDateFormat("dd, MMM yyyy", Locale.US);
			String curdateString = sdf.format(currentdateLong);
			final Date currentDate = sdf.parse(curdateString);
			String dateString = sdf.format(date);
			calenderTextView.setText(dateString);
			datePickerDialog = new DatePickerDialog(this,
					new OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							try {
								Date seldate = null;
								tempDateString = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
								SimpleDateFormat df = new SimpleDateFormat(
										"yyyy-MM-dd", Locale.US);
								seldate = df.parse(tempDateString.toString());

								if ((seldate.after(currentDate) || isSameDay(currentDate, seldate))) {
									dateFormatter = new SimpleDateFormat(
											"dd, MMM yyyy", Locale.US);

									isConnectionActive = connectionDetector
											.isConnectingToInternet();
									if (!isConnectionActive) {
										initAlertDialogForCheckConnectivity();
									} else {
										calenderTextView.setText(dateFormatter
												.format(seldate).toString());
										getAppointmentTimeListOfDoctor();
									}
								} else {
									Toast.makeText(getApplicationContext(), "Select Appropriate Date", Toast.LENGTH_SHORT).show();
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}, newCalander.get(Calendar.YEAR),
					newCalander.get(Calendar.MONTH),
					newCalander.get(Calendar.DAY_OF_MONTH));

			datePickerDialog.getDatePicker().setMinDate(currentdateLong);
		} catch (Exception ex) {
			Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
		}

	}

	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
					.get(Calendar.DAY_OF_YEAR) == cal2
				.get(Calendar.DAY_OF_YEAR));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 22) {
			if (data != null) {
				Intent intentMessage = new Intent();
				setResult(21, intentMessage);
				finish();
				overridePendingTransition(R.anim.fadein, R.anim.exit_to_left);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.homeAppointment) {
			finish();
			Intent homeActivity = new Intent(this, UserHomeActivity.class);
			startActivity(homeActivity);
			overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setRestAdapter() {
		Picasso.with(getBaseContext()).load(doctProfileModel.getUserImage())
				.placeholder(R.drawable.reguserimage)
				.error(R.drawable.reguserimage).into(profilePicImageView);
		doctNameTextView.setText(doctProfileModel.getUserName().toString());
		doctAddressTextView.setText(doctProfileModel.getClinicAddress().toString());
	}

	private void getAppointmentTimeListOfDoctor() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).build();

		String tempAppoDate = calenderTextView.getText().toString();
		Date date = null;

		SimpleDateFormat df = new SimpleDateFormat("dd, MMM yyyy");
		try {
			date = df.parse(tempAppoDate.toString());
			dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

			final ProgressDialog mProgressDialog = ProgressDialog.show(this, "", "", true);
			mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			mProgressDialog.setContentView(R.layout.dialog);
			ProgressBar progressBar = (ProgressBar) mProgressDialog.findViewById(R.id.progressWheel);
			Circle circle = new Circle();

			circle.setColor(getResources().getColor(R.color.white));
			progressBar.setIndeterminateDrawable(circle);
			DoctFin doctFinInterFace = restAdapter.create(DoctFin.class);
			doctFinInterFace.getAppointmentTimeList(Integer.parseInt(doctIdString), dateFormatter.format(date).toString(), new Callback<AppointmentTimeResponse>() {
				@Override
				public void failure(RetrofitError error) {
					if (mProgressDialog.isShowing())
						mProgressDialog.dismiss();
				}

				@Override
				public void success(final AppointmentTimeResponse model, Response error) {
					if (model.getErrorCode() == 0) {
						if (model.getDoctorDetailWithAppointmentTime().size() != 0) {
							timeSlotLinear.removeAllViews();

							for (int j = 0; j < model.getDoctorDetailWithAppointmentTime().size(); j++) {
								if (model.getDoctorDetailWithAppointmentTime().get(j).getAppointmentStartTimes() != null) {
									for (int i = 0; i < model
											.getDoctorDetailWithAppointmentTime()
											.get(j).getAppointmentStartTimes()
											.size(); i++) {

										total = model.getDoctorDetailWithAppointmentTime().get(j).getAppointmentStartTimes().size();
										final TextView timeSlotTextView = new TextView(getApplicationContext());
										final TextView startTimeTextView=new TextView(getApplicationContext());
										final TextView endTimeTextView=new TextView(getApplicationContext());
										startTimeTextView.setVisibility(View.GONE);
										endTimeTextView.setVisibility(View.GONE);
										LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

										String appointmentStartTime = model
												.getDoctorDetailWithAppointmentTime()
												.get(j)
												.getAppointmentStartTimes()
												.get(i).getStartTime()
												.toString();

										String appointmentEndTime = model
												.getDoctorDetailWithAppointmentTime()
												.get(j)
												.getAppointmentStartTimes()
												.get(i).getEndTime().toString();
										
										
										SimpleDateFormat format = new SimpleDateFormat(
												"yyyy-MM-dd'T'HH:mm:ss");
										try {

											Date startTimeDate = format
													.parse(appointmentStartTime);
											Date endTimeDate = format
													.parse(appointmentEndTime);
											SimpleDateFormat startTimeDateFormat = new SimpleDateFormat(
													"hh:mm a");
											SimpleDateFormat endTimeDateFormat = new SimpleDateFormat(
													"hh:mm a");

											timeSlotTextView
													.setGravity(Gravity.CENTER);
											timeSlotTextView
													.setLayoutParams(lp);
											timeSlotTextView
													.setTextColor(getResources()
															.getColor(
																	R.color.colorPrimary));
											timeSlotTextView.setTextSize(16);
											timeSlotTextView.setPadding(3, 3,
													3, 3);
											timeSlotTextView
													.setText(startTimeDateFormat
															.format(startTimeDate)
															.toString()
															+ " - "
															+ endTimeDateFormat
																	.format(endTimeDate)
																	.toString());
											timeSlotTextView
													.setOnTouchListener(new CustomTouchListener());
											timeSlotTextView
													.setOnClickListener(new OnClickListener() {
														@Override
														public void onClick(View v) {
															isConnectionActive = connectionDetector
																	.isConnectingToInternet();
															if (!isConnectionActive) {
																initAlertDialogForCheckConnectivity();
															} else {
																Intent intent = new Intent(
																		getBaseContext(),
																		AddAppointmentActivity.class);
																intent.putExtra(
																		"dateTimeString",
																		calenderTextView
																				.getText()
																				.toString());
																intent.putExtra(
																		"doctAppointmentTimeString",
																		timeSlotTextView
																				.getText()
																				.toString());

																intent.putExtra(
																		"doctProfileModelString",
																		doctProfileModelString);
																intent.putExtra(
																		"catIdInt",
																		catIdInt);
																intent.putExtra(
																		"subCatId",
																		subCatId);
																startActivityForResult(
																		intent,
																		22);
																overridePendingTransition(
																		R.anim.fadein,
																		R.anim.exit_to_left);
															}

														}
													});

											timeSlotLinear.addView(startTimeTextView);
											timeSlotLinear.addView(endTimeTextView);
											timeSlotLinear
													.addView(timeSlotTextView);

										} catch (ParseException e) {
											e.printStackTrace();
										}
									}
								} else {

									final TextView timeSlotTextView = new TextView(
											getApplicationContext());
									timeSlotTextView.setGravity(Gravity.CENTER);
									timeSlotTextView
											.setText("Doctor Not Available");
									timeSlotTextView
											.setTextColor(getResources()
													.getColor(R.color.colorPrimary));
									timeSlotTextView.setTextSize(16);
									timeSlotTextView.setPadding(3, 3, 3, 3);
									timeSlotLinear.addView(timeSlotTextView);

								}
							}
						}
					}
					if (mProgressDialog.isShowing())
						mProgressDialog.dismiss();
				}
			});

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public class CustomTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((TextView) view).setTextColor(0xFF03A9F4);
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				((TextView) view).setTextColor(0xFF546E7A);
				break;
			}
			return false;
		}
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

	private void initAlertDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		alertDialog.setTitle(getResources().getString(
				R.string.internet_error_header));
		alertDialog.setMessage(getResources().getString(
				R.string.internet_error_string));
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
				isConnectionActive = false;

			}
		});

		alertDialog.show();
	}

}
