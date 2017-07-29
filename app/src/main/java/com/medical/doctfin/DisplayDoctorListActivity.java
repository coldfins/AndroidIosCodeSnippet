package com.medical.doctfin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.Circle;
import com.medical.adapter.DisplayDoctorAdapter;
import com.medical.model.SearchDoctResponse;
import com.medical.model.SearchLocationDeatilModel;
import com.medical.ratrofitinterface.DoctFin;
import com.medical.utils.ConnectionDetector;
import com.medical.utils.GetAllUrl;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DisplayDoctorListActivity extends ActionBarActivity {
	private Toolbar displayToolbar;
	private String catIdString, subCatIdString, appoinmentDate;
	private Double latitude, longitude;
	private GetAllUrl url;
	private RecyclerView doctorListRecycleView;
	private ArrayList<SearchLocationDeatilModel> restSearchLocationListModel;
	public static Activity displayDoctorActivity;
	private LinearLayout loginLinearLayout;
	private ConnectionDetector connectionDetector;
	private boolean isConnectionActive = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_doctor_list);
		displayDoctorActivity = this;
		setToolBar();
		connectionDetector = new ConnectionDetector(this);
		isConnectionActive = connectionDetector.isConnectingToInternet();
		if (!isConnectionActive) {
			initAlertDialog();
		} else {
			setResources();
			getIntentIfAvailable();
			setRestAdapter();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_doctor_list, menu);
		return true;
	}

	private void setToolBar() {
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setTitle("Search Result");
		setSupportActionBar(displayToolbar);
		displayToolbar.setNavigationIcon(R.drawable.back);
		displayToolbar.setTitleTextColor(Color.WHITE);

		displayToolbar.setNavigationOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}

	private void setResources() {
		loginLinearLayout = (LinearLayout) findViewById(R.id.loginLinearLayout);

		url = new GetAllUrl();
		doctorListRecycleView = (RecyclerView) findViewById(R.id.doctorListRecyleView);
		doctorListRecycleView.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		doctorListRecycleView.setLayoutManager(llm);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button,

		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_map) {
			isConnectionActive = connectionDetector.isConnectingToInternet();
			if (!isConnectionActive) {
				initAlertDialogForCheckConnectivity();
			} else {
				if (restSearchLocationListModel != null) {
					Intent intent = new Intent(this, DoctorListMapView.class);
					intent.putExtra("doctArrayList",
							restSearchLocationListModel);
					startActivity(intent);
					overridePendingTransition(R.anim.fadein,
							R.anim.exit_to_left);
				} else {
					Toast.makeText(getApplicationContext(), "No Doctor Available", Toast.LENGTH_LONG).show();
				}

			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public DisplayDoctorListActivity() {
		super();
	}

	private void getIntentIfAvailable() {
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			catIdString = intent.getStringExtra("catId");
			subCatIdString = intent.getStringExtra("subCatId");
			latitude = intent.getDoubleExtra("latitude", 0.0);
			longitude = intent.getDoubleExtra("longitude", 0.0);
			appoinmentDate = intent.getStringExtra("appoinmentDate");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setRestAdapter() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).build();
		DoctFin doctFinInterFace = restAdapter.create(DoctFin.class);
		final ProgressDialog mProgressDialog = ProgressDialog.show(this, "", "", true);

		mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		mProgressDialog.setContentView(R.layout.dialog);
		ProgressBar progressBar = (ProgressBar) mProgressDialog.findViewById(R.id.progressWheel);
		Circle circle = new Circle();
		circle.setColor(getResources().getColor(R.color.white));
		progressBar.setIndeterminateDrawable(circle);

		doctFinInterFace.getDoctorList(latitude, longitude, appoinmentDate,
				Integer.parseInt(catIdString),
				Integer.parseInt(subCatIdString),
				new Callback<SearchDoctResponse>() {
					@Override
					public void failure(RetrofitError error) {
						Log.i("error", "Error: "+error.getMessage());
					}

					@SuppressLint({ "NewApi", "SimpleDateFormat" })
					@Override
					public void success(SearchDoctResponse model, Response response) {
						Log.i("TAB1", model.getErrorCode() + " " + model.getMsg());
						if (model.getErrorCode() == 0) {
							if (model.getSearchLocationDetails().size() != 0) {
								restSearchLocationListModel = new ArrayList<SearchLocationDeatilModel>();
								for (int i = 0; i < model
										.getSearchLocationDetails().size(); i++) {
									if (model.getSearchLocationDetails().get(i)
											.getAppointmentStartTimes().size() != 0) {
										restSearchLocationListModel.add(model
												.getSearchLocationDetails()
												.get(i));
									}
								}
								loginLinearLayout.setVisibility(View.GONE);
								doctorListRecycleView
										.setVisibility(View.VISIBLE);
								DisplayDoctorAdapter adapter = new DisplayDoctorAdapter(
										restSearchLocationListModel,
										DisplayDoctorListActivity.this, Integer
												.parseInt(catIdString), Integer
												.parseInt(subCatIdString),
										appoinmentDate);
								doctorListRecycleView.setAdapter(adapter);

								if (mProgressDialog.isShowing())
									mProgressDialog.dismiss();
							}

						} else {
							loginLinearLayout.setVisibility(View.VISIBLE);
							if (mProgressDialog.isShowing())
								mProgressDialog.dismiss();
						}
						if (model.getErrorCode() == 1) {
							loginLinearLayout.setVisibility(View.VISIBLE);
							if (mProgressDialog.isShowing())
								mProgressDialog.dismiss();
						}
					}
				});
	}

	private void initAlertDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Server Problem");
		alertDialog.setMessage("Server Not Responding");
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

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
		super.onBackPressed();
	}

}
