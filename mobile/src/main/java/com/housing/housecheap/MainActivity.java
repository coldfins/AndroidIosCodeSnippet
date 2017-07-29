package com.housing.housecheap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;

import com.housing.housecheap.adapter.MyAdapter;
import com.housing.housecheap.adapter.MyPagerAdapter;
import com.housing.housecheap.fragment.ProjectFragment;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint({ "NewApi", "ResourceAsColor", "Recycle" })
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity implements RecyclerView.OnItemTouchListener, AnimationListener {
	android.support.v7.widget.Toolbar toolbar;
	public static MyPagerAdapter adapter;
	public static int flagPosition;
	public static int position;
	ProjectFragment fragmentProject;
	public static DrawerLayout drawerLayout;
	RecyclerView recyclerView;
	String navTitles[];
	TypedArray navIconsGray, navIconsOrange;
	@SuppressWarnings("rawtypes")
	public static RecyclerView.Adapter recyclerViewAdapter;
	RecyclerView.LayoutManager layoutManager;
	ActionBarDrawerToggle mDrawerToggle;
	String EMAIL = "vcd";
	String PROFILE;

	SharedPreferences sp;
	private boolean isBackOfCardShowing = true;
	Animation animation1, animation2;
	public static HashMap<String,ArrayList<?>> unloadFragment;

	public MainActivity() {
	}

	private OnItemClickListener mListener;
	private GestureDetector mGestureDetector;

	public MainActivity(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
		mListener = listener;
		mGestureDetector = new GestureDetector(context,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						return true;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

						if (childView != null && mListener != null) {
							mListener.onItemLongClick(childView, recyclerView.getChildPosition(childView));
						}
					}
				});
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initControl();
	}


	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

	}

	/**
     * Initialize all UI components
     */
	public void initControl() {

		unloadFragment=new HashMap<>();
		sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

		fragmentProject = new ProjectFragment();

		setupToolbar();
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerMainActivity);

		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			}
		};
		drawerLayout.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
		mDrawerToggle.syncState();

		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);

		navTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navIconsOrange = getResources().obtainTypedArray(R.array.nav_drawer_iconsOrange);
		navIconsGray = getResources().obtainTypedArray(R.array.nav_drawer_iconsGray);

		String a = sp.getString("UEmail", "");
		String b = sp.getString("Purl", "");

		if (a.isEmpty()) {
			EMAIL = "Welcome guest !!!";
			PROFILE = "R.drawable.signup_user";
			MyAdapter.offset=0;
		} else {
			EMAIL = a;
			PROFILE = b;
			MyAdapter.offset=1;
		}

		recyclerViewAdapter = new MyAdapter(navTitles, navIconsGray, navIconsOrange, EMAIL, PROFILE, MainActivity.this);
		recyclerView.setAdapter(recyclerViewAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this,
			new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					return true;
				}
			});

		recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
				@Override
				public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
					View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
					if (child != null && mGestureDetector.onTouchEvent(motionEvent))
					{
						position = recyclerView.getChildPosition(child);
						recyclerView.getAdapter().notifyDataSetChanged();
						if (position != 0) {
							flagPosition = 0;
							MyAdapter.selected_item = position;
						}
						if (position == 1) {
							drawerLayout.closeDrawers();
							flagPosition = 0;
							getSupportFragmentManager().beginTransaction().replace(R.id.containerView, fragmentProject).commit();
						}
						return true;
					}
					return false;
				}

				@Override
				public void onTouchEvent(RecyclerView rv, MotionEvent e) {

				}

				@Override
				public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

				}
			});

		if (MyAdapter.selected_item == 1) {
			getSupportFragmentManager().beginTransaction().replace(R.id.containerView, fragmentProject).commit();
		}
	}

	/**
	 * Return status bar height
	 * @return SatusBar height
	 */
	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public static interface OnItemClickListener {
		public void onItemClick(View view, int position);
		public void onItemLongClick(View view, int position);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	public void setupToolbar() {
		toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolBar);
		toolbar.setTitleTextColor(Color.WHITE);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		MyAdapter.selected_item = 1;
	}

	@Override
	public void onBackPressed() {
		int a = ProjectFragment.flagMap;
		if (a == 1) {
			ProjectFragment.layoutMap.setVisibility(View.GONE);
			ProjectFragment.frameLayout.setVisibility(View.VISIBLE);
			toolbar.setVisibility(View.VISIBLE);
			ProjectFragment.flagMap = 0;
		} else {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setTitle(Html.fromHtml("<font color='#6cc54b'> Warning </font>"));
			alertbox.setMessage("Do you really want to exit ?");
			alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					MainActivity.this.finish();
					System.exit(0);
					// kill the application
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
			alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {

				}
			});
			alertbox.show();
		}
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
		View childView = view.findChildViewUnder(e.getX(), e.getY());

		if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
			mListener.onItemClick(childView, view.getChildPosition(childView));
		}

		return false;
	}

	@Override
	public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation == animation1) {
			if (isBackOfCardShowing) {
			}
			ProjectFragment.frameLayout.clearAnimation();
			ProjectFragment.frameLayout.setAnimation(animation2);
			ProjectFragment.frameLayout.startAnimation(animation2);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

}
