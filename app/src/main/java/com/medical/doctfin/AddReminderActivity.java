package com.medical.doctfin;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.medical.adapter.PersonSpinnerAdapter;
import com.medical.model.PatientAppointmentModel;
import com.medical.notification.AppointmentReminderReciver;
import com.medical.utils.TagClass;
import com.medical.utils.UserSharedPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

@SuppressLint("NewApi")
public class AddReminderActivity extends ActionBarActivity {

	private Toolbar displayToolbar;
	private String[] personStringArray = new String[] { "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "10" };
	private Spinner noOfHourSpinner;
	private ArrayList<String> noOfHourArrayList;
	private long notifyRemindTime;
	private Button setReminderButton;
	private String appointmentDateString, docNameString, doctAddressString,
			doctAppDateTimeString;
	private EditText reminderMessageEditText;
	private int reminderHour;
	private UserSharedPreference userSharedPreference;
	private String userModelString;
	private Gson doctProfileGson;
	private PatientAppointmentModel patientAppointmentModel = new PatientAppointmentModel();;
//	private UserRegistrationModel userRegistrationModel;
	private SharedPreferences sharedpreferences;
	private String reminderMessageString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_reminder);
		getIntentIfAvailable();
		setToolBar();
		setResources();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_reminder, menu);
		return true;
	}

	private void setResources() {

		userSharedPreference = new UserSharedPreference(this);
		sharedpreferences = this.getSharedPreferences(TagClass.UserArrayList,
				this.MODE_PRIVATE);
		setReminderButton = (Button) findViewById(R.id.setReminderButton);
		reminderMessageEditText = (EditText) findViewById(R.id.reminderMessageEditText);
		noOfHourSpinner = (Spinner) findViewById(R.id.selectHourSpinner);
		noOfHourArrayList = new ArrayList<String>();
		setNoOfHourSpinner();
		noOfHourSpinner.setAdapter(new PersonSpinnerAdapter(noOfHourArrayList,
				this));
		setReminderButton.setOnClickListener(setReminderClick);
		noOfHourSpinner.setOnItemSelectedListener(subCatItemClick);

	}

	OnItemSelectedListener subCatItemClick = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int i,
				long position) {
			// TODO Auto-generated method stub
			TextView reminderHourTextView = (TextView) view
					.findViewById(R.id.noOfPersonTextView);
			reminderHour = Integer.parseInt(reminderHourTextView.getText()
					.toString());
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};

	OnClickListener setReminderClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Calendar cal = Calendar.getInstance();
			Log.i("CurrentTime", " " + cal.getTime());
			Log.i("CurrentTime in Mili", " " + cal.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE,MMM dd,yyyy hh:mm a", Locale.US);

			try {
				cal.setTime(sdf.parse(doctAppDateTimeString));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			cal.add(Calendar.HOUR, (0 - reminderHour));// Reduce 1 Hour From
			// Date
			notifyRemindTime = cal.getTimeInMillis();
			setAlaram();
			finish();
			Log.i("Nowwwwwww", " " + cal.getTime());
			Log.i("Nowinmili", " " + cal.getTimeInMillis());
		}
	};

	private void getIntentIfAvailable() {
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			docNameString = intent.getStringExtra("docNameString");
			doctAddressString = intent.getStringExtra("doctAddressString");
			doctAppDateTimeString = intent
					.getStringExtra("doctAppDateTimeString");
			patientAppointmentModel = (PatientAppointmentModel) intent
					.getSerializableExtra("patientAppointmetDetail");

		}
	}

	private void setNoOfHourSpinner() {
		for (int i = 0; i < personStringArray.length; i++) {
			noOfHourArrayList.add(personStringArray[i]);
		}
	}

	public void setAlaram() {
		Random r = new Random();
		Intent intent = new Intent(this, AppointmentReminderReciver.class);
		// Toast.makeText(getApplicationContext(), " "+notifyRemindTime,
		// Toast.LENGTH_SHORT).show();
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		reminderMessageString = reminderMessageEditText.getText().toString();
		Log.i("In SetAlaram", "In SetAlaram");
		intent.putExtra("docNameString", docNameString);
		intent.putExtra("doctAddressString", reminderMessageString);
		intent.putExtra("doctAppDateTimeString", doctAppDateTimeString);

		// Toast.makeText(getApplicationContext(),
		// ""+System.currentTimeMillis(),
		// Toast.LENGTH_LONG).show();
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
				r.nextInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) this
				.getSystemService(this.ALARM_SERVICE);
		Log.i("NotifyTimesetalaram", " " + notifyRemindTime);
		alarmManager.setExact(AlarmManager.RTC_WAKEUP, notifyRemindTime,
				pendingIntent);
	}

	private void setToolBar() {
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setTitle("Add Reminder");
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
}
