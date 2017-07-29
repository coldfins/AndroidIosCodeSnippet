package com.medical.doctfin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.medical.model.SearchLocationDeatilModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoctorListMapView extends ActionBarActivity implements
		GoogleMap.OnMarkerClickListener {
	private Toolbar displayToolbar;
	private GoogleMap googleMap;
	private ArrayList<SearchLocationDeatilModel> restSearchLocationListModel;
	MarkerOptions options;
	Double latitude, longitude;
	private TextView doctName, doctAddressTextView, doctMapName,
			doctMapAddress, doctmapDistanceTextView;
	private ImageView doctmapImageView;
	private CardView displayDoctDetailCardView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_list_map_view);
		getIntentIfAvailable();
		setToolBar();

		doctMapName = (TextView) findViewById(R.id.doctmapNameTextView);
		doctMapAddress = (TextView) findViewById(R.id.doctmapAddressTextView);
		doctmapImageView = (ImageView) findViewById(R.id.doctmapImageView);
		doctmapDistanceTextView = (TextView) findViewById(R.id.doctmapDistanceTextView);
		displayDoctDetailCardView=(CardView)findViewById(R.id.displayDoctDetailCardView);

		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapFragment);
		googleMap = fm.getMap();


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
				&& ContextCompat.checkSelfPermission(DoctorListMapView.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(DoctorListMapView.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);//, MY_PERMISSION_LOCATION
		} else {
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String bestProvider = locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(bestProvider);
		}

		getAllLocations();
		googleMap.setOnMarkerClickListener(this);

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
				onBackPressed();
			}
		});

	}
	@Override
	public void onBackPressed() {
	super.onBackPressed();
	finish();
	overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
}

	public void getAllLocations() {
		MarkerOptions options;

		for (int i = 0; i < restSearchLocationListModel.size(); i++) {
			latitude = restSearchLocationListModel.get(i).getLatitude();
			longitude = restSearchLocationListModel.get(i).getLongitude();

			LatLng position = new LatLng(latitude, longitude);
			getCustomMarker();

			options = new MarkerOptions();
			options.position(position);
			options.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.marker_44_white));
			Marker currentMarker = googleMap.addMarker(options);
			currentMarker.hideInfoWindow();
			googleMap.moveCamera(CameraUpdateFactory
					.newLatLngZoom(position, 13));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_list_map_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

	private void getIntentIfAvailable() {
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			restSearchLocationListModel = (ArrayList<SearchLocationDeatilModel>) intent
					.getSerializableExtra("doctArrayList");
		}
	}

	public void getCustomMarker() {
		googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			MarkerOptions options;

			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				View v = getLayoutInflater().inflate(R.layout.marker_layout, null);
				doctName = (TextView) v.findViewById(R.id.doctNameTextView);
				doctAddressTextView = (TextView) v.findViewById(R.id.doctAddressTextView);
				LatLng latLngg = marker.getPosition();
				double lal1 = latLngg.latitude, lon1 = latLngg.longitude;

				for (int i = 0; i < restSearchLocationListModel.size(); i++) {
					latitude = restSearchLocationListModel.get(i).getLatitude();
					longitude = restSearchLocationListModel.get(i)
							.getLongitude();
					if ((latitude == lal1) && (longitude == lon1)) {
						doctName.setText("Dr. " + restSearchLocationListModel.get(i).getName());
						doctAddressTextView.setText(restSearchLocationListModel
								.get(i).getClinicName());

						doctMapName.setText("Dr. "
								+ restSearchLocationListModel.get(i).getName());
						doctMapAddress.setText(restSearchLocationListModel.get(i)
								.getClinicName());

						Picasso.with(getBaseContext())
						.load(restSearchLocationListModel.get(i).getUserImage())
						.placeholder(R.drawable.reguserimage)
						.error(R.drawable.reguserimage)
						.into(doctmapImageView);
						doctmapDistanceTextView.setText(restSearchLocationListModel.get(i).getRange());
					}
				}

				return v;
			}
		});
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		displayDoctDetailCardView.setVisibility(View.VISIBLE);
		LatLng latLngg = marker.getPosition();

		double lal1 = latLngg.latitude, lon1 = latLngg.longitude;

		for (int i = 0; i < restSearchLocationListModel.size(); i++) {

			latitude = restSearchLocationListModel.get(i).getLatitude();
			longitude = restSearchLocationListModel.get(i).getLongitude();
			if ((latitude == lal1) && (longitude == lon1)) {
				doctMapName.setText("Dr. "
						+ restSearchLocationListModel.get(i).getName());
				doctMapAddress.setText(restSearchLocationListModel.get(i)
						.getClinicName());

				Picasso.with(getBaseContext())
				.load(restSearchLocationListModel.get(i).getUserImage())
				.placeholder(R.drawable.reguserimage)
				.error(R.drawable.reguserimage)
				.into(doctmapImageView);
				doctmapDistanceTextView.setText(restSearchLocationListModel.get(i).getRange());
			}
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 1: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults[0] == PackageManager.PERMISSION_DENIED){
					finish();
				}else if(ContextCompat.checkSelfPermission(DoctorListMapView.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
						&& ContextCompat.checkSelfPermission(DoctorListMapView.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
					LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
					Criteria criteria = new Criteria();
					String bestProvider = locationManager.getBestProvider(criteria, true);
					Location location = locationManager.getLastKnownLocation(bestProvider);
				}
				return;
			}

		}
	}

}
