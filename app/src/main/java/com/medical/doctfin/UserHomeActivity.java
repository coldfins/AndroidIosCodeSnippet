package com.medical.doctfin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medical.adapter.NavigationDrawerAdapter;
import com.medical.fragment.FindDoctorFragment;
import com.medical.fragment.PastAppoinmentsFragment;
import com.medical.model.UserRegistrationModel;
import com.medical.utils.TagClass;
import com.medical.utils.UserSharedPreference;

import java.util.ArrayList;
import java.util.HashMap;

public class UserHomeActivity extends ActionBarActivity {
	public String[] menuStringArray;
	public static DrawerLayout drawerLayout;
	public Toolbar ToolDrawer;
	private ActionBarDrawerToggle aDrawerToggle;
	private TextView findDoctorTabButton, medicalTeamTabButton,
			pastAppoinmentTabButton, settingsTeamTabButton, findDoctorTextView,
			medicalTeamTextView, pastAppoTextView, settingsTextView;
	private Typeface font;
	private LinearLayout findDoctLinear, medicalLinear, pastAppLinear,
			settingsLinear;

	public String titlesStringArray[] = { "Find a Doctor", "Appointments" };
	public String titlessStringArray[] = { "Find a Doctor", "Medical Team", "Appointments", "Profile", "Login" };
	public int iconIdArray[] = { R.drawable.search_g32, R.drawable.team_g32,
			R.drawable.appointment_g32, R.drawable.reguser,
			R.drawable.sidepanelsignout };
	public int blueIconIdArray[] = { R.drawable.search_b32,
			R.drawable.team_b32, R.drawable.appointment_b32,
			R.drawable.profile_b32, R.drawable.sidepanelsignout };
	public String imageHeader;
	public String emailHeader, userModelString;
	private ActionBarDrawerToggle homeDrawerToggle;
	private RecyclerView dataRecyclerView;
	public static NavigationDrawerAdapter dataAdapter;
	private RecyclerView.LayoutManager recyclerLayoutManager;
	private UserSharedPreference userSharedPreference;
	private View viewOnUserHome;
	private int position, lastPosition;
	private int userId = 0;
	private SharedPreferences sharedpreferences;
	// Fragment mapFragment;
	FindDoctorFragment findDoctFragment;
	PastAppoinmentsFragment pastAppointmentFragment;
	private UserRegistrationModel userRegistrationModel;
	public static HashMap<String, ArrayList<?>> hashval;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home);
		setResources();
	
		hashval = new HashMap<String, ArrayList<?>>();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.contentFrameLayout, findDoctFragment);
		fragmentTransaction.commit();
		dataAdapter.seleteditem = 1;
		position = 1;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_home, menu);
		return true;
	}

	public void setToolBar() {
		ToolDrawer = (Toolbar) findViewById(R.id.displayToolBar);
		ToolDrawer.setTitle("FindDoctor");
		ToolDrawer.setTitleTextColor(Color.WHITE);
		setSupportActionBar(ToolDrawer);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@SuppressLint("NewApi")
	private void setResources() {
		userSharedPreference = new UserSharedPreference(this);
		sharedpreferences = getSharedPreferences(TagClass.UserArrayList,
				this.MODE_PRIVATE);
		pastAppointmentFragment = new PastAppoinmentsFragment();
		findDoctFragment = new FindDoctorFragment();

		final GestureDetector mGestureDetector = new GestureDetector(
				UserHomeActivity.this,
				new GestureDetector.SimpleOnGestureListener() {

					@Override
					public boolean onSingleTapUp(MotionEvent e) {

						return true;
					}

				});

		ToolDrawer = (Toolbar) findViewById(R.id.displayToolBar);
		drawerLayout = (DrawerLayout) findViewById(R.id.userHomeDrawerLayout);
		setSupportActionBar(ToolDrawer);

		dataRecyclerView = (RecyclerView) findViewById(R.id.dataRecyclerView);
		dataRecyclerView.setHasFixedSize(true);
		dataAdapter = new NavigationDrawerAdapter(titlesStringArray, iconIdArray, imageHeader, blueIconIdArray, emailHeader, this);
		dataRecyclerView.setAdapter(dataAdapter);
		recyclerLayoutManager = new LinearLayoutManager(this);
		dataRecyclerView.setLayoutManager(recyclerLayoutManager);

		homeDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
				this, drawerLayout, ToolDrawer, R.string.app_name,
				R.string.app_name) {
			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);

				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
				super.onDrawerOpened(drawerView);
			}
		};

		drawerLayout.setDrawerListener(homeDrawerToggle);
		homeDrawerToggle.syncState();
		dataRecyclerView
				.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
					@SuppressWarnings("static-access")
					@Override
					public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
						View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

						if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
							position = recyclerView.getChildPosition(child);
							lastPosition = dataAdapter.seleteditem;
							recyclerView.getAdapter().notifyDataSetChanged();

							if (position != 0) {
								NavigationDrawerAdapter.seleteditem = position;
							}

							if (position == 1) {
								getSupportFragmentManager().beginTransaction().replace(R.id.contentFrameLayout, findDoctFragment).commit();
								drawerLayout.closeDrawers();
							}else if (position == 2) {
								getSupportFragmentManager().beginTransaction().replace(R.id.contentFrameLayout, pastAppointmentFragment).commit();
								drawerLayout.closeDrawers();
							}
						}
						return false;
					}

					@Override
					public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {

					}

					@Override
					public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

					}
				});

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
