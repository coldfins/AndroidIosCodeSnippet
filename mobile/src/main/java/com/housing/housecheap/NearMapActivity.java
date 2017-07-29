package com.housing.housecheap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.housing.housecheap.adapter.MapCustomAdapter;
import com.housing.housecheap.utility.PlaceJSONParser;
import com.meetme.android.horizontallistview.HorizontalListView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NearMapActivity extends ActionBarActivity implements android.location.LocationListener {
	String getId;
	Toolbar toolbar;
	MapFragment fm;
	GoogleMap googleMap;
	List<Address> addresses;
	Geocoder geocoder;
	Double getLat = (double) 0, getLon = (double) 0;

	String[] mPlaceType = null;
	String[] mPlaceTypeName = null;

	MapCustomAdapter adapter;

	int numMarkersInRainbow[] = { R.drawable.marker_airport,

	R.drawable.marker_bank, R.drawable.marker_bus, R.drawable.marker_train,
			R.drawable.marker_hospital, R.drawable.marker_park,
			R.drawable.marker_school, R.drawable.marker_bank,
			R.drawable.marker_restaurant };

	private int[] grayImage = new int[] { R.drawable.airport_gray,
			R.drawable.bank_gray, R.drawable.bus_gray, R.drawable.train_gray,
			R.drawable.hospital_gray, R.drawable.park_gray,
			R.drawable.school_gray, R.drawable.bank_gray,
			R.drawable.gray_resturant };

	private int[] orangImage = new int[] { R.drawable.airport_orang,
			R.drawable.bank_orang, R.drawable.bus_orang,
			R.drawable.train_orang, R.drawable.hospital_orang,
			R.drawable.park_orang, R.drawable.school_orang,
			R.drawable.bank_orang, R.drawable.orang_resturant };

	private HorizontalListView mHlvSimpleList;
	int posForMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_near_map);
		initControl();
	}

	/**
	 * Initialize all UI components
	 */
	public void initControl() {
		try {
			fm = (MapFragment) getFragmentManager().findFragmentById(
					R.id.mapForProperty);
			googleMap = fm.getMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Intent i = getIntent();
			if (i == null) {
				getId = null;
			} else {
				getId = i.getStringExtra("Id");
				getLat = i.getDoubleExtra("Lat", 0);
				getLon = i.getDoubleExtra("Lon", 0);
				getPropertyLocation();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		setupToolbar();
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

		mPlaceType = getResources().getStringArray(R.array.place_type);
		mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

		if (status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, NearMapActivity.this, requestCode);
			dialog.show();
		} else {
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
			googleMap.setMyLocationEnabled(true);
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String provider = locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				onLocationChanged(location);
			}
			locationManager.requestLocationUpdates(provider, 20000, 0, this);
		}
		mHlvSimpleList = (HorizontalListView) findViewById(R.id.hlvForMap);
		mHlvSimpleList.setOnItemClickListener(horizotalItemClick);
		setupSimpleList();

	}

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
		MapCustomAdapter.item = -1;
		adapter.notifyDataSetChanged();
	}

	private void setupSimpleList() {
		adapter = new MapCustomAdapter(mPlaceTypeName, grayImage, orangImage, getApplicationContext());
		mHlvSimpleList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@SuppressLint("ResourceAsColor")
	public OnItemClickListener horizotalItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String type = mPlaceType[position];
			StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
			sb.append("location=" + getLat + "," + getLon);
			sb.append("&radius=5000");
			sb.append("&types=" + type);
			sb.append("&sensor=true");
			sb.append("&key=AIzaSyBTBBGPjqhYQ07WHOKiq06KWMT-vOST-oE");
			PlacesTask placesTask = new PlacesTask();
			placesTask.execute(sb.toString());

			posForMap = position;

			MapCustomAdapter.item = position;
			if (position == 0) {
				MapCustomAdapter.flag = 1;
			}

			adapter.notifyDataSetChanged();
		}
	};

	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();
			br.close();
		} catch (Exception e) {
			Log.d("Exception while url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;
	}

	private class PlacesTask extends AsyncTask<String, Integer, String> {
		String data = null;

		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
				String position = url[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			ParserTask parserTask = new ParserTask();
			parserTask.execute(result);
		}

	}

	private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
		JSONObject jObject;

		@Override
		protected List<HashMap<String, String>> doInBackground(String... jsonData) {
			List<HashMap<String, String>> places = null;
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);

				places = placeJsonParser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		@Override
		protected void onPostExecute(List<HashMap<String, String>> list) {

			// Clears all the existing markers
			googleMap.clear();
			getPropertyLocation();

			for (int i = 0; i < list.size(); i++) {

				// Creating a marker
				MarkerOptions markerOptions = new MarkerOptions();
				HashMap<String, String> hmPlace = list.get(i);
				double lat = Double.parseDouble(hmPlace.get("lat"));
				double lng = Double.parseDouble(hmPlace.get("lng"));
				String name = hmPlace.get("place_name");
				Log.d("TTT","Place: "+name);
				String vicinity = hmPlace.get("vicinity");
				LatLng latLng = new LatLng(lat, lng);
				markerOptions.position(latLng);
				markerOptions.title(name + " : " + vicinity);
				markerOptions.icon(BitmapDescriptorFactory.fromResource(numMarkersInRainbow[posForMap]));
				googleMap.addMarker(markerOptions);
			}
			
			if(list.size()==0)
			{
				Toast.makeText(getApplicationContext(), "No places found", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Add property location marker on map
	 */
	private void getPropertyLocation() {
		try {
			LatLng position = new LatLng(getLat, getLon);
			geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
			try
			{
				addresses = geocoder.getFromLocation(getLat, getLon, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String address = addresses.get(0).getAddressLine(0);
			MarkerOptions options = new MarkerOptions();
			options.position(position);
			options.title(address);
			options.snippet(" ");
			options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

			googleMap.addMarker(options);
			CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);
			googleMap.moveCamera(updatePosition);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.near_map, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.normal) {
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			return false;
		} else if (id == R.id.hybrid) {
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			return false;
		} else if (id == R.id.satellite) {
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			return false;
		}

		onBackPressed();
		return super.onOptionsItemSelected(item);
	}

	public void setupToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolBarMap);
		toolbar.setTitleTextColor(Color.WHITE);
		toolbar.setTitle("Map");
		final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		upArrow.setColorFilter(getResources().getColor(R.color.WHITE), android.graphics.PorterDuff.Mode.SRC_ATOP);
		toolbar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(upArrow);

	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
