package com.medical.doctfin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.squareup.picasso.Picasso;
import com.medical.model.PatientAppointmentModel;
import com.medical.utils.ConnectionDetector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PatientAppointMentDetail extends ActionBarActivity {

	private TextView doctNameTextView, doctDegreeTextView,
			experianceYrTextView, scheduleTextView, appointmentTextView,
			notesTextView, fileTextView, descriptionTextView;
	private Toolbar displayToolbar;
	private ImageView profilePicImageView;
	private PatientAppointmentModel patientAppointmentModel;
	private LinearLayout imageViewLinearLayout, descriptionLinearLayout,
			fileTVLinearLayout;

	private ConnectionDetector connectionDetector;
	private boolean isConnectionActive = false;

	private int cnt = 1;
	private String[] imageNameStringArray;

	ArrayList<String> imageStringArray = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_appoint_ment_detail);
		connectionDetector = new ConnectionDetector(this);
		isConnectionActive = connectionDetector.isConnectingToInternet();
		if (!isConnectionActive) {
			initAlertDialogForCheckConnectivity();
		} else {
			setToolBar();
			setResources();
			getIntentIfAvailable();
			setDataonResources();

		}
	}

	private void setResources() {
		fileTVLinearLayout = (LinearLayout) findViewById(R.id.fileTVLinear);
		descriptionLinearLayout = (LinearLayout) findViewById(R.id.descriptionLinearLayout);
		descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
		imageViewLinearLayout = (LinearLayout) findViewById(R.id.imageViewLinearLayout);
		doctNameTextView = (TextView) findViewById(R.id.doctNameTextView);
		doctDegreeTextView = (TextView) findViewById(R.id.doctDegreeTextView);
		experianceYrTextView = (TextView) findViewById(R.id.experianceYrTextView);
		scheduleTextView = (TextView) findViewById(R.id.scheduleTextView);
		appointmentTextView = (TextView) findViewById(R.id.appointmentTextView);
		notesTextView = (TextView) findViewById(R.id.notesTextView);
		// fileTextView = (TextView) findViewById(R.id.fileTextView);
		profilePicImageView = (ImageView) findViewById(R.id.profilePicImageView);
		patientAppointmentModel = new PatientAppointmentModel();

	}

	private void setDataonResources() {
		Picasso.with(this).load(patientAppointmentModel.getUserImage())
				.placeholder(R.drawable.ic_launcher)
				.error(R.drawable.reguserimage).into(profilePicImageView);
		doctNameTextView.setText(patientAppointmentModel.getFirstName() + " "
				+ patientAppointmentModel.getLastName());
		doctDegreeTextView.setText(patientAppointmentModel.getClinicAddress());
		experianceYrTextView.setText(patientAppointmentModel.getClinicName());
		String appointmentDateString, startTimeString, endTimeString;
		appointmentDateString = patientAppointmentModel.getAppointmentDate();
		startTimeString = patientAppointmentModel.getStartTime().toString();
		endTimeString = patientAppointmentModel.getEndTime().toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			Date date = format.parse(appointmentDateString);
			Date startTimeDate = format.parse(startTimeString);
			Date endTimeDate = format.parse(endTimeString);

			SimpleDateFormat dtFormat = new SimpleDateFormat("dd, MMM yyyy");

			SimpleDateFormat appointmentFormat = new SimpleDateFormat("hh:mm a");
			scheduleTextView.setText(dtFormat.format(date).toString());
			appointmentTextView.setText(appointmentFormat.format(startTimeDate)
					.toString()
					+ " - "
					+ appointmentFormat.format(endTimeDate).toString());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		notesTextView.setText(patientAppointmentModel.getSubCategoryName()
				.toString());

		if (patientAppointmentModel.getDiseasesDescription() != null) {
			descriptionTextView.setText(patientAppointmentModel
					.getDiseasesDescription().toString());
			descriptionLinearLayout.setVisibility(View.VISIBLE);
		} else {

			descriptionLinearLayout.setVisibility(View.GONE);
		}

		if (patientAppointmentModel.getDiseasesImage() != null
				&& !patientAppointmentModel.getDiseasesImage().equals(" ")
				&& patientAppointmentModel.getDiseasesImage().toString()
						.length() != 0) {
			fileTVLinearLayout.setVisibility(View.VISIBLE);
			String imageString = patientAppointmentModel.getDiseasesImage()
					.toString();

			imageNameStringArray = imageString.split(",");

			for (int i = 0; i < imageNameStringArray.length; i++) {
				String imagePathString = patientAppointmentModel.getPath()
						+ imageNameStringArray[i].trim().toString();
				addImageView(imageViewLinearLayout, imagePathString);
			}
		} else {
			fileTVLinearLayout.setVisibility(View.GONE);
		}
	}

	private void addImageView(LinearLayout layout, String imagePathString) {
		final ImageView imageView = new ImageView(this);

		imageView.setId(cnt);

		imageStringArray.add(imagePathString);
		cnt++;
		Picasso.with(this).load(imagePathString)
				.placeholder(R.drawable.no_media).error(R.drawable.no_media)
				.into(imageView);

		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(getBaseContext(),
						DieasesImageActivity.class);
				//
				i.putExtra("Bitmap", imageStringArray);
				startActivity(i);
			}
		});
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				100, 100);
		layoutParams.setMargins(5, 5, 5, 5);
		imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setLayoutParams(layoutParams);
		layout.addView(imageView);
	}

	private void setToolBar() {
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setTitle("Appointment Detail");
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		finish();
		overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
		super.onBackPressed();
	}

	private void getIntentIfAvailable() {
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			patientAppointmentModel = (PatientAppointmentModel) intent
					.getSerializableExtra("patientAppointmetDetail");
			// Toast.makeText(getApplicationContext(),
			// patientAppointmentModel.getAppointmentId()+"",
			// Toast.LENGTH_SHORT).show();
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

}
