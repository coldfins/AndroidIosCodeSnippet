package com.medical.doctfin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.Circle;
import com.medical.model.DisplayDoctProfileModel;
import com.medical.model.DisplayDoctProfileResponse;
import com.medical.ratrofitinterface.DoctFin;
import com.medical.utils.GetAllUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DoctorInfoProfileActivity extends ActionBarActivity {
	private TextView doctNameTextView, doctDegreeTextView, experianceTextView,
			doctAddressTextView, universityTextView, graduateTextView,
			detailTextView;
	private ImageView detailImageView;
	private ArrayList<DisplayDoctProfileModel> displayDoctProfileArrayList;
	private Toolbar displayToolbar;
	private GetAllUrl url;
	private String doctIdString;
	private ImageView profilePicImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_info_profile);
		setToolBar();
		setResources();
		getIntentIfAvailable();
		setRestAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_info_profile, menu);
		return true;
	}

	private void setToolBar() {
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setTitle("Doctor Profile");
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
		detailImageView=(ImageView)findViewById(R.id.detailImageView);
		doctNameTextView = (TextView) findViewById(R.id.doctNameTextView);
		doctDegreeTextView = (TextView) findViewById(R.id.doctDegreeTextView);
		experianceTextView = (TextView) findViewById(R.id.experianceYrTextView);
		doctAddressTextView = (TextView) findViewById(R.id.doctAddressTextView);
		universityTextView = (TextView) findViewById(R.id.universityTextView);
		graduateTextView = (TextView) findViewById(R.id.graduateTextView);
		profilePicImageView = (ImageView) findViewById(R.id.profilePicImageView);
		detailTextView = (TextView) findViewById(R.id.detailTextView);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getIntentIfAvailable() {
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			doctIdString = intent.getStringExtra("doctIdString");

		}
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

		ProgressBar progressBar = (ProgressBar) mProgressDialog.findViewById(R.id.progressWheel);
		Circle circle = new Circle();

		circle.setColor(getResources().getColor(R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		doctFinInterFace.getDoctorProfile(Integer.parseInt(doctIdString),
				new Callback<DisplayDoctProfileResponse>() {
					@Override
					public void success(DisplayDoctProfileResponse model, Response response) {
						if (model.getErrorCode() == 0) {
							if (model.getDoctordetails() != null) {
								Picasso.with(getBaseContext())
										.load(model.getDoctordetails()
												.getUserImage())
										.placeholder(R.drawable.reguserimage)
										.error(R.drawable.reguserimage)
										.into(profilePicImageView);

								doctNameTextView.setText("Dr. "
										+ model.getDoctordetails()
												.getUserName().toString());
								doctDegreeTextView.setText(model
										.getDoctordetails().getDegreeName()
										.toString());
								universityTextView.setText(model
										.getDoctordetails().getCollegeName()
										.toString());
								graduateTextView.setText("Graduated: "
										+ model.getDoctordetails()
												.getDegreeYear().toString());
								experianceTextView.setText(model
										.getDoctordetails().getExperienceYear()
										.toString()
										+ " Year Experience");
								doctAddressTextView.setText(model
										.getDoctordetails().getClinicAddress()
										.toString());
								if (model.getDoctordetails().getDescription() != null) {
									detailTextView.setText(model
											.getDoctordetails()
											.getDescription().toString());
									detailImageView.setVisibility(View.VISIBLE);
									detailTextView.setVisibility(View.VISIBLE);
								} else {
									detailTextView.setVisibility(View.GONE);	
									detailImageView.setVisibility(View.GONE);
								}
								if (mProgressDialog.isShowing())
									mProgressDialog.dismiss();
							}
						} else {

						}
					}

					@Override
					public void failure(RetrofitError error) {
						if (mProgressDialog.isShowing())
							mProgressDialog.dismiss();
					}
				});
	}
}
