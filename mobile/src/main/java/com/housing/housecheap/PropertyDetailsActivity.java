package com.housing.housecheap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.housing.housecheap.adapter.CustomHorizontalList;
import com.housing.housecheap.adapter.CustomImageAdapter;
import com.housing.housecheap.fragment.PageFragment;
import com.housing.housecheap.model.GetPropertyByIdUser;
import com.housing.housecheap.model.GetWishList;
import com.housing.housecheap.model.ListData;
import com.housing.housecheap.model.PropertiesList;
import com.housing.housecheap.model.PropertyPanorama;
import com.housing.housecheap.model.PropertyamenityUser;
import com.housing.housecheap.utility.GetUrl;
import com.housing.housecheap.utility.RetrofitPathJavaClass;
import com.meetme.android.horizontallistview.HorizontalListView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PropertyDetailsActivity extends ActionBarActivity implements LocationListener {

	TextView tvId, tvBhkType, tvPropertyType, tvArea, tvCity, tvRate, tvRs,
			tvBuildSqft, tvDescription, tvRightIcon, tvBulidArea, tvFloor,
			tvFacing, tvBathroom, tvConstruction, tvParking,
			tvLocation, tvCloseBtn, tvMoreDetails;
	String getId, getCity, uContactFname, uContactLname, uContactCno, uContactAddress,
			uContactBhktype, uContactPType, uContactArea;
	ArrayList myList = new ArrayList();
	ViewPager viewPager;
	GetUrl url;
	GetPropertyByIdUser arrayOfProperty;
	CardView fstCard;
	Typeface font;
	ArrayList<PropertyPanorama> panorma=new ArrayList<PropertyPanorama>();
	PropertyamenityUser amenity;
	ArrayList<String> itemsAmenities;
	GoogleMap googleMap;
	Double getLat, getLon;
	MapFragment fm;
	LinearLayout vDescription, mainLinearLayout, lvMap;
	ScrollView scrollBar;
	Toolbar toolBar;
	int flagDetail = 0, userId = 0, getPShortlist, getPropertyId;
	MenuItem ivWhite, ivFillWhite,ivPanorma;
	SharedPreferences sp;
	boolean wishListVal;
	ProgressDialog mProgressDialog;
	Boolean chkPnorma;
	FloatingActionButton floatingBtn;

	public static ArrayList<PropertiesList> propertyListWish = new ArrayList<PropertiesList>();
	// TwoWayView lvTest;

	private HorizontalListView mHlvSimpleList;


	private ArrayList<PropertyamenityUser> amenitiesItem;

	private String[] listTitle = new String[] { "Servent Room", "Kitchen",
			"Mess", "Cooking Allowed", "Hot Water", "Waterpurifier",
			"Internet", "Breakfast", "Lunch", "Dinner", "Housekeeping",
			"Parking", "Laundry", "Late Nightentry", "Girl Entry", "Drinking",
			"Smoking", "Non veg.", "Lift", "Gas Pipeline", "Gym",
			"Swimming Pool" };

	private int[] blackImage = new int[] { R.drawable.servantroom_b,
			R.drawable.kitchen_b, R.drawable.mess_b,
			R.drawable.cookingallowed_b, R.drawable.hotwater_b,
			R.drawable.waterpurifier_b, R.drawable.internet_b,
			R.drawable.breakfast_b, R.drawable.lunch_b, R.drawable.dinner_b,
			R.drawable.housekeeping_b, R.drawable.parking_b,
			R.drawable.laundry_b, R.drawable.latenightentry_b,
			R.drawable.girlsentry_bb, R.drawable.drinking_b,
			R.drawable.smoking_b, R.drawable.nonveg_b, R.drawable.lift_b,
			R.drawable.gas_b, R.drawable.gym_b, R.drawable.swimmingpool_b };

	private int[] grayImage = new int[] { R.drawable.servantroom_g,
			R.drawable.kitchen_g, R.drawable.mess_g,
			R.drawable.cookingallowed_g, R.drawable.hotwater_g,
			R.drawable.waterpurifier_g, R.drawable.internet_g,
			R.drawable.breakfast_g, R.drawable.lunch_g, R.drawable.dinner_g,
			R.drawable.housekeeping_g, R.drawable.parking_g,
			R.drawable.lunch_g, R.drawable.latenightentry_g,
			R.drawable.girlsentry_g, R.drawable.drinking_g,
			R.drawable.smoking_g, R.drawable.nonveg_g, R.drawable.lift_g,
			R.drawable.gas_g, R.drawable.gym_g, R.drawable.swimmingpool_g };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_details);
		initControl();
		setupToolbar();
		toolBar.setPadding(0, getStatusBarHeight(), 0, 0);

	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * Initialize all UI components
	 */
	public void initControl() {

		mHlvSimpleList = (HorizontalListView) findViewById(R.id.hlvSimpleList);

		font = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fontawesome-webfont.ttf");

		try {
			fm = (MapFragment) getFragmentManager().findFragmentById(R.id.editmap);
			googleMap = fm.getMap();
			googleMap.setMyLocationEnabled(false);
			googleMap.getUiSettings().setZoomControlsEnabled(false);
			googleMap.setOnMapClickListener(mapTouch);
		} catch (Exception e) {

		}

		floatingBtn = (FloatingActionButton) findViewById(R.id.CallAgenticon);
		floatingBtn.setOnClickListener(actionFloatingBtn);
		url = new GetUrl();
		scrollBar = (ScrollView) findViewById(R.id.scrollBar);

		tvId = (TextView) findViewById(R.id.txtId);
		tvBhkType = (TextView) findViewById(R.id.txtBhktype);
		tvPropertyType = (TextView) findViewById(R.id.txtAppartment);
		tvArea = (TextView) findViewById(R.id.txtArea);
		tvCity = (TextView) findViewById(R.id.txtCity);
		tvRate = (TextView) findViewById(R.id.txtRate);
		tvDescription = (TextView) findViewById(R.id.txtDescription);
		tvRs = (TextView) findViewById(R.id.txtRs);
		tvRightIcon = (TextView) findViewById(R.id.txtRightIcon);
		tvBulidArea = (TextView) findViewById(R.id.txtBuiltUpArea);
		tvFloor = (TextView) findViewById(R.id.txtFloor);
		tvFacing = (TextView) findViewById(R.id.txtFacing);
		tvBathroom = (TextView) findViewById(R.id.txtBathroom);
		tvConstruction = (TextView) findViewById(R.id.txtConstruction);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		tvBuildSqft = (TextView) findViewById(R.id.txtBiuldareaSqft);
		tvParking = (TextView) findViewById(R.id.txtParking);
		fstCard = (CardView) findViewById(R.id.firstCv);
		tvLocation = (TextView) findViewById(R.id.tvLocation);
		tvCloseBtn = (TextView) findViewById(R.id.tvcloseBtn);
		tvMoreDetails = (TextView) findViewById(R.id.tvMoreDetails);
		vDescription = (LinearLayout) findViewById(R.id.viewDescription);
		mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinear);
		lvMap = (LinearLayout) findViewById(R.id.lvNearMapArea);
		tvCloseBtn.setTypeface(font);
		tvRs.setTypeface(font);
		tvRightIcon.setOnClickListener(rightIcon);
		tvCloseBtn.setOnClickListener(closeBtn);
		lvMap.setOnClickListener(map);

		try {
			Intent i = getIntent();
			if (i == null) {
				getId = null;
				getCity = null;
			} else {
				getId = i.getStringExtra("id");
				getCity = i.getStringExtra("city");
				tvId.setText(getId);
				tvCity.setText(getCity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

		try {
			userId = sp.getInt("userId", 0);
			Log.d("Create uuuuId", userId + "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		GetPropertyByUser();
	}

	public OnClickListener actionFloatingBtn = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), UserContactDetailActivity.class);
			i.putExtra("Id", getId);
			i.putExtra("fname", uContactFname);
			i.putExtra("lname", uContactLname);
			i.putExtra("number", uContactCno);
			i.putExtra("address", uContactAddress);
			i.putExtra("bhktype", uContactBhktype);
			i.putExtra("ptype", uContactPType);
			i.putExtra("area", uContactArea);
			startActivity(i);

			PropertyDetailsActivity.this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
		}
	};

	public OnClickListener rightIcon = new OnClickListener() {
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_up);
			vDescription.startAnimation(bottomUp);
			if (flagDetail == 0) {
				vDescription.setVisibility(View.VISIBLE);
				flagDetail = 1;
			}
		}
	};

	public OnMapClickListener mapTouch = new OnMapClickListener() {
		@Override
		public void onMapClick(LatLng arg0) {
			Intent i = new Intent(getApplicationContext(), NearMapActivity.class);
			i.putExtra("Id", getId);
			i.putExtra("Lat", getLat);
			i.putExtra("Lon", getLon);
			startActivity(i);
			PropertyDetailsActivity.this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
		}
	};

	public OnClickListener map = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), NearMapActivity.class);
			i.putExtra("Id", getId);
			i.putExtra("Lat", getLat);
			i.putExtra("Lon", getLon);
			startActivity(i);
			PropertyDetailsActivity.this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
		}
	};

	public OnClickListener closeBtn = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Animation bottomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_up);
			vDescription.startAnimation(bottomIn);
			scrollBar.setVisibility(View.VISIBLE);
			vDescription.setVisibility(View.GONE);
			flagDetail = 0;
		}
	};

	public void GetPropertyByUser() {
		mProgressDialog = ProgressDialog.show(PropertyDetailsActivity.this, "", "", true);
		mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mProgressDialog.setContentView(R.layout.dialog);
		ProgressBar progressBar = (ProgressBar) mProgressDialog.findViewById(R.id.progressWheel);
		Circle circle = new Circle();
		circle.setColor(getResources().getColor(android.R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

		try {
			userId = sp.getInt("userId", 0);
			Log.d("uId", userId + "");
			getPropertyId = Integer.parseInt(getId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL).build();
		RetrofitPathJavaClass restInterface = adapter.create(RetrofitPathJavaClass.class);
		restInterface.getPropertyByIdUser(getPropertyId, userId,
				new Callback<GetPropertyByIdUser>() {
					@Override
					public void success(GetPropertyByIdUser model, Response arg1) {
						amenitiesItem = new ArrayList<PropertyamenityUser>();
						amenity = new PropertyamenityUser();
						itemsAmenities = new ArrayList<String>();
						itemsAmenities.add(model.getPropertyamenity().getKitchen().toString());

						itemsAmenities.add(model.getPropertyamenity().getServentroom().toString());

						itemsAmenities.add(model.getPropertyamenity().getMess().toString());
						itemsAmenities.add(model.getPropertyamenity().getCookingallow().toString());
						itemsAmenities.add(model.getPropertyamenity().getHotwater().toString());
						itemsAmenities.add(model.getPropertyamenity().getWaterpurifier().toString());
						itemsAmenities.add(model.getPropertyamenity().getInternet().toString());
						itemsAmenities.add(model.getPropertyamenity().getBreakfast().toString());
						itemsAmenities.add(model.getPropertyamenity().getLunch().toString());
						itemsAmenities.add(model.getPropertyamenity().getDinner().toString());
						itemsAmenities.add(model.getPropertyamenity().getHousekeeping().toString());
						itemsAmenities.add(model.getPropertyamenity().getParking().toString());
						itemsAmenities.add(model.getPropertyamenity().getLaundry().toString());
						itemsAmenities.add(model.getPropertyamenity().getEntrytill().toString());
						itemsAmenities.add(model.getPropertyamenity().getGirlentry().toString());
						itemsAmenities.add(model.getPropertyamenity().getDrinking().toString());
						itemsAmenities.add(model.getPropertyamenity().getSmoking().toString());
						itemsAmenities.add(model.getPropertyamenity().getNonveg().toString());
						itemsAmenities.add(model.getPropertyamenity().getLift().toString());
						itemsAmenities.add(model.getPropertyamenity().getGaspipeline().toString());
						itemsAmenities.add(model.getPropertyamenity().getGym().toString());
						itemsAmenities.add(model.getPropertyamenity().getSwimmingpool().toString());
						arrayOfProperty = new GetPropertyByIdUser();
						arrayOfProperty = model;
						panorma.clear();
						panorma.addAll(arrayOfProperty.getPropertyPanorama());
						try {
							Log.d("Data", arrayOfProperty.getMsg());
						} catch (Exception e) {
							e.printStackTrace();
						}

						getPShortlist = arrayOfProperty.getPropertyDetailsUser().get(0).getPShortlist();
						if (getPShortlist == 1) {
							ivWhite.setVisible(false);
							ivFillWhite.setVisible(true);
						} else if (getPShortlist == 0) {
							ivWhite.setVisible(true);
							ivFillWhite.setVisible(false);
						}

						int rate = arrayOfProperty.getPropertyDetailsUser().get(0).getRate();
						tvBhkType.setText(arrayOfProperty.getPropertyDetailsUser().get(0).getBhkType());

						tvRightIcon.setTypeface(font);
						tvArea.setText(" - " + arrayOfProperty.getPropertyDetailsUser().get(0).getArea());
						tvPropertyType.setText(arrayOfProperty.getPropertyDetailsUser().get(0).getPropertyType());
						tvRate.setText(String.valueOf(rate));

						// 2nd cardview
						tvDescription.setText(arrayOfProperty.getPropertyDetailsUser().get(0).getDescription());
						tvMoreDetails.setText(arrayOfProperty.getPropertyDetailsUser().get(0).getDescription());

						// 3rd cardview
						Calendar calendar = Calendar.getInstance();
						int year = calendar.get(Calendar.YEAR);
						int getYear = Integer.parseInt(arrayOfProperty.getPropertyDetailsUser().get(0).getYearOfConstruction());
						int bulidArea = arrayOfProperty.getPropertyDetailsUser().get(0).getBuildarea();
						int yearOfConstruction = year - getYear;

						float getkPersqft = (float) rate / bulidArea;
						tvBulidArea.setText(String.valueOf(bulidArea) + " sq.ft");
						tvFacing.setText(arrayOfProperty.getPropertyDetailsUser().get(0).getFacing());
						String floor = arrayOfProperty.getPropertyDetailsUser().get(0).getFloorno();
						if (floor.equals("")) {
							tvFloor.setText("0");
						} else {
							tvFloor.setText(floor);
						}
						tvBathroom.setText(String.valueOf(arrayOfProperty.getPropertyDetailsUser().get(0).getBathroom()));
						tvConstruction.setText(String.valueOf(yearOfConstruction));
						boolean getPartking = arrayOfProperty.getPropertyamenity().getParking();
						if (getPartking == true) {
							tvParking.setText("Yes");
						} else {
							tvParking.setText("No");
						}

						// 1st card
						tvBuildSqft.setText(getkPersqft + " k/sq.ft");
						// 4th card
						setupSimpleList();

						// 5th card
						tvLocation.setText(arrayOfProperty.getPropertyDetailsUser().get(0).getAddress());
						getLat = arrayOfProperty.getPropertyDetailsUser().get(0).getLatitude();
						getLon = arrayOfProperty.getPropertyDetailsUser().get(0).getLongitude();

						getPropertyLocation(getLat ,getLon);
						uContactBhktype = arrayOfProperty.getPropertyDetailsUser().get(0).getBhkType();
						uContactPType = arrayOfProperty.getPropertyDetailsUser().get(0).getPropertyType();
						uContactArea = " -" + arrayOfProperty.getPropertyDetailsUser().get(0).getArea();
						uContactAddress = arrayOfProperty.getPropertyDetailsUser().get(0).getAddress();
						uContactFname = arrayOfProperty.getPropertyDetailsUser().get(0).getUserFirstName();
						uContactLname = arrayOfProperty.getPropertyDetailsUser().get(0).getUserLastName();
						uContactCno = arrayOfProperty.getPropertyDetailsUser().get(0).getUserContact();
						chkPnorma=arrayOfProperty.getPropertyDetailsUser().get(0).getIsPanorama();
						if(ivPanorma!=null)
						{
							if(chkPnorma==true)
							{
								ivPanorma.setVisible(true);
							}
							else
							{
								ivPanorma.setVisible(false);
							}
						}
						
						if (mProgressDialog.isShowing())
							mProgressDialog.dismiss();

						PagerAdapter adapter = new CustomImageAdapter(PropertyDetailsActivity.this, arrayOfProperty.getPropertyphotos());
						viewPager.setAdapter(adapter);
						CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
						indicator.setViewPager(viewPager);
						
					}

					@Override
					public void failure(RetrofitError error) {
						if (mProgressDialog.isShowing())
							mProgressDialog.dismiss();
						String merror = error.getMessage();
					}
				});
	}

	private void getPropertyLocation(double getLat1,double getLon1) {
		try {
			LatLng position = new LatLng(getLat1, getLon1);
			MarkerOptions options = new MarkerOptions();
			options.position(position);
			options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
			googleMap.addMarker(options);
			CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);
			googleMap.moveCamera(updatePosition);
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupSimpleList() {
		getDataInList();
		mHlvSimpleList.setAdapter(new CustomHorizontalList(getApplicationContext(), myList, itemsAmenities));
	}

	@SuppressWarnings("unchecked")
	private void getDataInList() {
		for (int i = 0; i < listTitle.length; i++) {
			ListData ld = new ListData();
			ld.setTitle(listTitle[i]);
			ld.setBooleanVal(itemsAmenities.get(i));
			ld.setImgResIdBlack(blackImage[i]);
			ld.setImgResIdGray(grayImage[i]);
			myList.add(ld);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.property_details, menu);
		ivWhite = menu.findItem(R.id.ivWhite);
		ivFillWhite = menu.findItem(R.id.ivFillWhite);
		ivPanorma=menu.findItem(R.id.iv360Panorma);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			Intent i = getIntent();
			if (i == null) {
				getId = null;
				getCity = null;
			} else {
				getId = i.getStringExtra("id");
				getCity = i.getStringExtra("city");
				tvId.setText(getId);
				tvCity.setText(getCity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * get list of favorites property
	 */
	public void GetWishListDetails() {
		mProgressDialog = ProgressDialog.show(PropertyDetailsActivity.this, "", "", true);
		mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mProgressDialog.setContentView(R.layout.dialog);
		ProgressBar progressBar = (ProgressBar) mProgressDialog.findViewById(R.id.progressWheel);
		Circle circle = new Circle();
		circle.setColor(getResources().getColor(android.R.color.white));
		progressBar.setIndeterminateDrawable(circle);

		Log.d("wishUserId", userId + "");
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL).build();
		RetrofitPathJavaClass restInterface = adapter.create(RetrofitPathJavaClass.class);
		restInterface.getWhishlistById(userId, new Callback<GetWishList>() {
			@Override
			public void failure(RetrofitError error) {
				if (mProgressDialog.isShowing())
					mProgressDialog.dismiss();

				String merror = error.getMessage();
				try {
					Log.d("failure", merror);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@SuppressLint("DefaultLocale")
			@Override
			public void success(GetWishList model, Response arg1) {
				if (mProgressDialog.isShowing())
					mProgressDialog.dismiss();
				PageFragment.getFlag = 1;
			}

		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

		try {
			userId = sp.getInt("userId", 0);
			Log.d("Craete uuuuId", userId + "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (id == R.id.ivWhite) {
			if (userId == 0) {

			} else {
				wishListVal = false;
				ivWhite.setVisible(false);
				ivFillWhite.setVisible(true);
				 GetWishListDetails();
			}
			return false;
		}
		else if (id == R.id.ivFillWhite) {
			if (userId == 0) {

			} else {
				wishListVal = true;
				ivWhite.setVisible(true);
				ivFillWhite.setVisible(false);
				 GetWishListDetails();
			}
			return false;
		}
		else if(id==R.id.iv360Panorma)
		{
			Intent i1=new Intent(PropertyDetailsActivity.this,PanoramaActivity.class);			
			i1.putExtra("panoramaImg",panorma);
			startActivity(i1);			
			PropertyDetailsActivity.this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
			return false;
		}
		
		onBackPressed();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	public void setupToolbar() {
		toolBar = (Toolbar) findViewById(R.id.toolBarPropertyDetail);
		toolBar.setTitleTextColor(Color.WHITE);
		toolBar.setTitle("");
		final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		upArrow.setColorFilter(getResources().getColor(R.color.WHITE), android.graphics.PorterDuff.Mode.SRC_ATOP);

		// setupDrawerToggle(title);
		setSupportActionBar(toolBar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(upArrow);
	}

	@Override
	public void onBackPressed() {
		if (flagDetail == 1) {
			Animation bottomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_up);
			vDescription.startAnimation(bottomIn);
			scrollBar.setVisibility(View.VISIBLE);
			vDescription.setVisibility(View.GONE);
			flagDetail = 0;
		}
		else {
			finish();
			this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
		}
	}

}
