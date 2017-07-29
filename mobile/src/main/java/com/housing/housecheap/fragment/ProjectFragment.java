package com.housing.housecheap.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.housing.housecheap.MainActivity;
import com.housing.housecheap.PropertyDetailsActivity;
import com.housing.housecheap.R;
import com.housing.housecheap.SearchPropertyActivity;
import com.housing.housecheap.adapter.GetProjectAdapter;
import com.housing.housecheap.model.GetAllPropertiList;
import com.housing.housecheap.model.PropertiesList;
import com.housing.housecheap.utility.CheckNetworkConnection;
import com.housing.housecheap.utility.CustomAlertBox;
import com.housing.housecheap.utility.GetUrl;
import com.housing.housecheap.utility.RetrofitPathJavaClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ProjectFragment extends Fragment implements RecyclerView.OnItemTouchListener, AnimationListener {

	RecyclerView propertyRecyclerView;
	GridLayoutManager propertyLayoutManager;
	@SuppressWarnings("rawtypes")
	public static RecyclerView.Adapter propertyAdapter;
	public static ArrayList<PropertiesList> PropertyList = new ArrayList<PropertiesList>();
	GetUrl url = new GetUrl();
	public static CardView cvNoData;
	View v;
	private OnItemClickListener mListener;
	private GestureDetector mGestureDetector;
	FloatingActionButton floatingBtn, floatingBtnMap;
	public static FrameLayout frameLayout;
	public static LinearLayout layoutMap;
	Animation animation1;
	private Animation animation2;
	private boolean isBackOfCardShowing = true;
	MapFragment fm;
	GoogleMap googleMap;
	double lal = 0, lon = 0;
	Typeface font;
	ImageView back;
	public static int flagMap = 0;
	ProgressDialog mProgressDialog;
	View rootView;
	CheckNetworkConnection connection;
	CustomAlertBox alert;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(" Projects ");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			((ActionBarActivity) getActivity()).getSupportActionBar().setElevation(8);
		}

		if (rootView != null) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null)
				parent.removeView(rootView);
		}
		try {
			rootView = inflater.inflate(R.layout.project_fragment, container, false);
			setHasOptionsMenu(true);
			setView(rootView);
		} catch (InflateException e) {
			e.printStackTrace();
		}

		return rootView;
	}

	@SuppressWarnings("unchecked")
	public void setView(View rootView) {
		connection = new CheckNetworkConnection();
		alert = new CustomAlertBox();
		v = rootView;
		font = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
		cvNoData = (CardView) rootView.findViewById(R.id.propertyCardNoData);
		propertyRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvProjects);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			propertyLayoutManager = new GridLayoutManager(getActivity(), 2);
		} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			propertyLayoutManager = new GridLayoutManager(getActivity(), 1);
		}
		propertyRecyclerView.setHasFixedSize(true);
		propertyRecyclerView.setLayoutManager(propertyLayoutManager);

		try {
			fm = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapForProperties);
			googleMap = fm.getMap();
			googleMap.getUiSettings().setZoomControlsEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (MainActivity.unloadFragment.containsKey("project")) {
			PropertyList.addAll((ArrayList<PropertiesList>) MainActivity.unloadFragment.get("project"));
			propertyAdapter = new GetProjectAdapter(getActivity(), PropertyList);
			propertyRecyclerView.setAdapter(propertyAdapter);
		} else {
			GetAllProject();
		}

		propertyRecyclerView.addOnItemTouchListener(new ProjectFragment(getActivity(), propertyRecyclerView,
			new OnItemClickListener() {
				@Override
				public void onItemClick(View view, int position) {
					TextView tvid = (TextView) view.findViewById(R.id.txtPropertyId);
					TextView tvcity = (TextView) view.findViewById(R.id.txtCity);
					String id = tvid.getText().toString();
					String city = tvcity.getText().toString();
					Intent i = new Intent(view.getContext(), PropertyDetailsActivity.class);
					i.putExtra("id", id);
					i.putExtra("city", city);
					view.getContext().startActivity(i);
					((Activity) v.getContext()).overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

				}

				@Override
				public void onItemLongClick(View view, int position) {
					// TODO Auto-generated method stub

				}

			}));

		frameLayout = (FrameLayout) rootView.findViewById(R.id.flProperty);
		layoutMap = (LinearLayout) rootView.findViewById(R.id.linearLayoutMapProjects);
		back = (ImageView) rootView.findViewById(R.id.ivBackerrow);
		back.setOnClickListener(backBtn);

		animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.to_middle);
		animation1.setAnimationListener(this);
		animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.from_middle);
		animation2.setAnimationListener(this);
		floatingBtn = (FloatingActionButton) rootView.findViewById(R.id.orang_icon);
		floatingBtn.setOnClickListener(actionFloatingBtn);
		floatingBtnMap = (FloatingActionButton) rootView.findViewById(R.id.orang_IconBackMap);
		floatingBtnMap.setOnClickListener(actionFloatingBtnMap);
	}

	public OnClickListener backBtn = new OnClickListener() {
		@Override
		public void onClick(View v) {
			frameLayout.setVisibility(View.VISIBLE);
			layoutMap.setVisibility(View.GONE);

			frameLayout.clearAnimation();
			frameLayout.setAnimation(animation1);
			frameLayout.startAnimation(animation1);
			((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Projects");
			flagMap = 0;
		}
	};

	public OnClickListener actionFloatingBtnMap = new OnClickListener() {
		@Override
		public void onClick(View v) {
			frameLayout.setVisibility(View.VISIBLE);
			layoutMap.setVisibility(View.GONE);
			frameLayout.clearAnimation();
			frameLayout.setAnimation(animation1);
			frameLayout.startAnimation(animation1);
			((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Projects");
			flagMap = 0;
		}
	};

	public OnClickListener actionFloatingBtn = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (flagMap == 0) {
				frameLayout.setVisibility(View.GONE);
				layoutMap.setVisibility(View.VISIBLE);
				layoutMap.clearAnimation();
				layoutMap.setAnimation(animation1);
				layoutMap.startAnimation(animation1);
				getAllLocations();
				((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Map");
				flagMap = 1;
			}
		}
	};

	public void GetAllProject() {
		mProgressDialog = ProgressDialog.show(getActivity(), "", "", true);
		mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mProgressDialog.setContentView(R.layout.dialog);
		ProgressBar progressBar = (ProgressBar) mProgressDialog.findViewById(R.id.progressWheel);
		Circle circle = new Circle();
		circle.setColor(getResources().getColor(android.R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL).build();

		RetrofitPathJavaClass restInterface = adapter.create(RetrofitPathJavaClass.class);
		restInterface.getAllPropertyReport(new Callback<GetAllPropertiList>() {
			@Override
			public void failure(RetrofitError error) {
				if (mProgressDialog.isShowing())
					mProgressDialog.dismiss();
				String merror = error.getMessage();
				try {
					if (connection.isNetworkAvailable(getActivity()) == false) {
						alert.MyAlertBox(getActivity());
						cvNoData.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void success(GetAllPropertiList model, Response arg1) {
				if (mProgressDialog.isShowing())
					mProgressDialog.dismiss();

				if (model.getCode().equals("0")) {
					if (connection.isNetworkAvailable(getActivity()) == false) {
						alert.MyAlertBox(getActivity());
						cvNoData.setVisibility(View.VISIBLE);
					} else {
						PropertyList.clear();
						for (int i = 0; i < model.getPropertiesLists().size(); i++) {
							PropertiesList pl = new PropertiesList();
							pl.setId(model.getPropertiesLists().get(i).getId());
							pl.setPropertytypeId(model.getPropertiesLists().get(i).getPropertytypeId());
							pl.setBhktype(model.getPropertiesLists().get(i).getBhktype());
							pl.setPropertytypes(model.getPropertiesLists().get(i).getPropertytypes());
							pl.setArea(model.getPropertiesLists().get(i).getArea());
							pl.setCity(model.getPropertiesLists().get(i).getCity());
							pl.setUserContactNo(model.getPropertiesLists().get(i).getUserContactNo());
							pl.setRate(model.getPropertiesLists().get(i).getRate());
							pl.setPropertyFor(model.getPropertiesLists().get(i).getPropertyFor());
							pl.setLatitude(model.getPropertiesLists().get(i).getLatitude());
							pl.setLongitude(model.getPropertiesLists().get(i).getLongitude());
							pl.setPropertyphotoUrl(model.getPropertiesLists().get(i).getPropertyphotoUrl());
							PropertyList.add(pl);
							MainActivity.unloadFragment.put("project", PropertyList);

							if (PropertyList.size() == 0) {
								cvNoData.setVisibility(View.VISIBLE);
							} else {
								cvNoData.setVisibility(View.GONE);
								propertyAdapter = new GetProjectAdapter(getActivity(), PropertyList);
								propertyRecyclerView.setAdapter(propertyAdapter);
							}
						}
						Log.d("size", PropertyList.size() + "");
					}
				} else if (model.getCode().equals("-1")) {
					Toast.makeText(getActivity(), model.getMsg() + "", Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(getActivity(), model.getMsg() + "", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@SuppressWarnings("unused")
	public void getAllLocations() {
		MarkerOptions options;

		for (int i = 0; i < PropertyList.size(); i++) {
			lal = PropertyList.get(i).getLatitude();
			lon = PropertyList.get(i).getLongitude();
			LatLng position = new LatLng(lal, lon);

			getCustomMarker();
			options = new MarkerOptions();
			options.position(position);
			options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

			Marker currentMarker = googleMap.addMarker(options);
			currentMarker.showInfoWindow();
			CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);

			CameraUpdate updateZoom = CameraUpdateFactory.zoomTo(8);
			googleMap.moveCamera(updatePosition);

			googleMap.animateCamera(updateZoom);
		}

	}

	@Override
	public void onResume() {
		if (flagMap == 1) {
			frameLayout.setVisibility(View.GONE);
			layoutMap.setVisibility(View.VISIBLE);

			getAllLocations();
			((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Map");

			flagMap = 1;

		} else if (flagMap == 0) {
			frameLayout.setVisibility(View.VISIBLE);
			layoutMap.setVisibility(View.GONE);
			((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Projects");
			flagMap = 0;
		}

		try {
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				propertyLayoutManager = new GridLayoutManager(getActivity(), 2);
			} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				propertyLayoutManager = new GridLayoutManager(getActivity(), 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onResume();

	}

	public void getCustomMarker() {
		googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			MarkerOptions options;

			@Override
			public View getInfoWindow(Marker arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				View v = getActivity().getLayoutInflater().inflate(R.layout.marker_layout, null);
				LatLng latLngg = marker.getPosition();
				double lal1 = latLngg.latitude, lon1 = latLngg.longitude;
				ImageView markerIcon = (ImageView) v.findViewById(R.id.ivPropertyMarker);
				TextView bhkType = (TextView) v.findViewById(R.id.tvBhktypeMarker);
				TextView pType = (TextView) v.findViewById(R.id.tvAppartmentMarker);
				TextView pArea = (TextView) v.findViewById(R.id.tvAreaMarker);
				TextView pRs = (TextView) v.findViewById(R.id.tvRsMarker);
				TextView pRate = (TextView) v.findViewById(R.id.tvPriceMarker);

				for (int i = 0; i < PropertyList.size(); i++) {
					lal = PropertyList.get(i).getLatitude();
					lon = PropertyList.get(i).getLongitude();
					if ((lal == lal1) && (lon == lon1)) {
						bhkType.setText(PropertyList.get(i).getBhktype());
						pType.setText(PropertyList.get(i).getPropertytypes());
						pArea.setText(PropertyList.get(i).getArea());
						pRs.setTypeface(font);
						pRate.setText(PropertyList.get(i).getRate().toString());
						Picasso.with(getActivity()).load(PropertyList.get(i).getPropertyphotoUrl()).placeholder(R.drawable.dummy_img).into(markerIcon);
					}
				}
				return v;
			}
		});
	}

	public interface OnItemClickListener {

		public void onItemClick(View view, int position);

		public void onItemLongClick(View view, int position);
	}

	public ProjectFragment() {
		super();
	}

	@SuppressLint("ValidFragment")
	public ProjectFragment(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.refresh:
				GetAllProject();
				return true;

			case R.id.search2:
				Intent ii = new Intent(getActivity(), SearchPropertyActivity.class);
				startActivityForResult(ii, 123);
				getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
				return true;
			default:

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		propertyRecyclerView = (RecyclerView) v.findViewById(R.id.rvProjects);
		// Checks the orientation of the screen for landscape and portrait
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

			propertyLayoutManager = new GridLayoutManager(getActivity(), 2);
			propertyRecyclerView.setHasFixedSize(true);
			propertyRecyclerView.setLayoutManager(propertyLayoutManager);

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

			propertyLayoutManager = new GridLayoutManager(getActivity(), 1);
			propertyRecyclerView.setHasFixedSize(true);
			propertyRecyclerView.setLayoutManager(propertyLayoutManager);
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.project_fragment, menu);
	}

	@SuppressLint("DefaultLocale")
	private ArrayList<PropertiesList> filter(List<PropertiesList> models, String query) {
		String text = null, textRate = null, textBhkType = null;
		query = query.toLowerCase();

		ArrayList<PropertiesList> filteredModelList = new ArrayList<PropertiesList>();
		if (query != "" && !query.equals("")) {
			for (PropertiesList model : models) {
				text = model.getCity().toLowerCase();
				textRate = String.valueOf(model.getRate());
				textBhkType = model.getBhktype().toLowerCase();

				if (text.contains(query)) {
					Log.v("AAA", "Add..." + model.getCity());
					filteredModelList.add(model);

				} else if (textRate.contains(query)) {
					filteredModelList.add(model);
				} else if (textBhkType.contains(query)) {
					filteredModelList.add(model);
				} else {
					text = model.getArea().toLowerCase();
					if (text.contains(query)) {
						filteredModelList.add(model);
					}
				}
			}
		}
		else {
			filteredModelList = PropertyList;
		}

		return filteredModelList;
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
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

	}

	@Override
	public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		if (animation == animation1) {
			if (isBackOfCardShowing) {
				// ((ImageView)findViewById(R.id.imageView1)).setImageResource(R.drawable.card_front);

			} else {
				// ((ImageView)findViewById(R.id.imageView1)).setImageResource(R.drawable.card_back);
			}
			frameLayout.clearAnimation();
			frameLayout.setAnimation(animation2);
			frameLayout.startAnimation(animation2);
		} else{
			/*
			 * isBackOfCardShowing=!isBackOfCardShowing;
			 * findViewById(R.id.button1).setEnabled(true);
			 */
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		Fragment fragment = this.getActivity().getSupportFragmentManager().findFragmentById(R.id.mapForProperties);
		if (fragment != null)
			getFragmentManager().beginTransaction().remove(fragment).commit();

	}

}
