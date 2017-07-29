package com.medical.doctfin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.github.alexkolpa.fabtoolbar.FabToolbar;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.medical.adapter.GalleryAdapterList;
import com.medical.model.DisplayDoctProfileModel;
import com.medical.utils.Action;
import com.medical.utils.ConnectionDetector;
import com.medical.utils.CustomGallery;
import com.medical.utils.ExpandableHeightGridView;
import com.medical.utils.GetAllUrl;
import com.medical.utils.TagClass;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddAppointmentActivity extends ActionBarActivity implements
		LocationListener, OnMapClickListener {
	private Toolbar displayToolbar;
	private String doctAppoinmentTimeString, haveVisitedString, dateTimeString, descriptionString;
	private TextView doctAddressTextView, doctAppoinmentDateTimeTextView;
	private RadioGroup haveVisitedRadioGroup;
	private RadioButton oldPatientRadioButton;
	private String[] personStringArray = new String[] { "1", "2", "3", "4", "5" };
	private int catId;
	private GoogleMap googleMap;
	private EditText descriptionEditText;
	private FabToolbar fabToolBar;
	private LinearLayout fabLinearLayout;
	private ExpandableHeightGridView gridGallery;
	private GalleryAdapterList adapter;
	private double latitude, longitude;
	private ProgressDialog pDialog;
	private ViewSwitcher viewSwitcher;
	private ImageLoader imageLoader;
	private SimpleDateFormat dateFormatter;
	private GetAllUrl url;
	private int code, catIdInt;
	private String resultData = null, doctProfileModelString, startTimeString, endTimeString;
	private String[] all_path;
	private String[] timeSlotStringArray;
	private File imgFile;
	private SharedPreferences sharedpreferences;
	private int subCatId;
	private LinearLayout chooseFromGalleryLinearLayout, takePictureLinearLayout;
	public static LinearLayout diseaseLinear;
	private ConnectionDetector connectionDetector;
	private boolean isConnectionActive = false;
	private String newStartTime, newEndTime;

	DisplayDoctProfileModel doctProfileModel;
	public static ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_appointment);
		setToolBar();
		connectionDetector = new ConnectionDetector(this);
		isConnectionActive = connectionDetector.isConnectingToInternet();
		if (!isConnectionActive) {
			initAlertDialog();
		} else {
			getIntentIfAvailable();
			setResources();
			initImageLoader();
			init();
		}
	}

	private void setResources() {
		diseaseLinear = (LinearLayout) findViewById(R.id.diseaseLinear);
		sharedpreferences = getSharedPreferences(TagClass.UserArrayList, this.MODE_PRIVATE);
		doctAddressTextView = (TextView) findViewById(R.id.doctAddressTextView);
		doctAppoinmentDateTimeTextView = (TextView) findViewById(R.id.doctAppoinmentDateTimeTextView);
		fabToolBar = (FabToolbar) findViewById(R.id.fab_toolbar);
		fabLinearLayout = (LinearLayout) findViewById(R.id.container);
		chooseFromGalleryLinearLayout = (LinearLayout) findViewById(R.id.chooseFromGalleryLinearLayout);
		takePictureLinearLayout = (LinearLayout) findViewById(R.id.takePictureLinearLayout);
		chooseFromGalleryLinearLayout.setOnClickListener(chooseImageClick);
		takePictureLinearLayout.setOnClickListener(takePicImageClick);
		haveVisitedRadioGroup = (RadioGroup) findViewById(R.id.haveVisitedRadioGroup);
		oldPatientRadioButton = (RadioButton) findViewById(R.id.oldPatientRadioButton);
		descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
		doctAddressTextView.setText(doctProfileModel.getClinicAddress().toString());
		doctAppoinmentDateTimeTextView.setText(startTimeString);
		url = new GetAllUrl();
		SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
		googleMap = supportMapFragment.getMap();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
				&& ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);//, MY_PERMISSION_LOCATION
		} else {
			googleMap.setMyLocationEnabled(true);
			googleMap.setOnMapClickListener(this);
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String bestProvider = locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(bestProvider);
			if (location != null) {
				onLocationChanged(location);
			}
			locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
		}

		latitude = doctProfileModel.getLatitude();
		longitude = doctProfileModel.getLongitude();
		Log.i("TAG111", latitude + " " + longitude);
		getMapLocation(latitude, longitude);
	}

	private void getMapLocation(double lat, double lon) {
		LatLng latLng = new LatLng(lat, lon);
		MarkerOptions options;
		options = new MarkerOptions();
		options.position(latLng);
		options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_44_white));
		googleMap.addMarker(options);
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
	}

	private void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions).memoryCache(
				new WeakMemoryCache());

		ImageLoaderConfiguration config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	private void init() {
		gridGallery = (ExpandableHeightGridView) findViewById(R.id.gridGallery);
		gridGallery.setFastScrollEnabled(true);
		gridGallery.setExpanded(true);
		adapter = new GalleryAdapterList(getApplicationContext(), imageLoader);
		adapter.setMultiplePick(false);
		gridGallery.setAdapter(adapter);
		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
		viewSwitcher.setDisplayedChild(1);
	}

	OnClickListener takePicImageClick = new OnClickListener() {
		@Override
		public void onClick(View view) {
			fabToolBar.hide();

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
					&& ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
					&& ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
					&& ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
			} else {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 100);
			}

			fabToolBar.hide();
		}
	};
	OnClickListener chooseImageClick = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
			i.putExtra("galleryArrayList", dataT);
			startActivityForResult(i, 200);
			fabToolBar.hide();
		}
	};

	private void setToolBar() {
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setTitle("Appointment Details");
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_appointment, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.addAppointment) {
			isConnectionActive = connectionDetector.isConnectingToInternet();
			if (!isConnectionActive) {
				initAlertDialogForCheckConnectivity();
			} else {
				getDataFromRadio();
				if (!isValidText(descriptionEditText.getText().toString().trim())) {
					Toast.makeText(getApplicationContext(), "Enter Proper Description", Toast.LENGTH_LONG).show();
				} else {
					descriptionString = descriptionEditText.getText().toString();
					Date stDate = null;
					Date edDate = null;
					SimpleDateFormat startdf = new SimpleDateFormat(
							"dd, MMM yyyy hh:mm a"); // Jun 2016 09:20 AM 24
					SimpleDateFormat enddf = new SimpleDateFormat(
							"dd, MMM yyyy hh:mm a");
					SimpleDateFormat finalstartdf = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss");

					SimpleDateFormat finalenddf = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss");
					Log.i("startTime", startTimeString);
					try {

						stDate = startdf.parse(startTimeString);
						edDate = enddf.parse(endTimeString);
						dateFormatter = new SimpleDateFormat("yyyy-MM-dd",
								Locale.US);
						newStartTime = finalstartdf.format(stDate).toString();
						newEndTime = finalenddf.format(edDate).toString();

					} catch (Exception e) {
						e.printStackTrace();
					}

					new addAppointment().execute();
				}

			}
			return true;
		}
		if (id == R.id.homeAppointment) {
			dataT.clear();
			finish();

			Intent homeActivity = new Intent(this, UserHomeActivity.class);
			startActivity(homeActivity);
			overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (fabLinearLayout.getVisibility() == View.VISIBLE) {
			fabToolBar.hide();
		} else {
			dataT.clear();
			finish();
			overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
			super.onBackPressed();
		}
	}

	private void getIntentIfAvailable() {
		Intent intent = getIntent();
		if (intent.getExtras() != null) {
			dateTimeString = intent.getStringExtra("dateTimeString");
			doctAppoinmentTimeString = intent.getStringExtra("doctAppointmentTimeString");
			timeSlotStringArray = doctAppoinmentTimeString.split(" - ");
			doctProfileModelString = intent.getStringExtra("doctProfileModelString");
			Gson doctProfileGson = new Gson();
			doctProfileModel = doctProfileGson.fromJson(doctProfileModelString, DisplayDoctProfileModel.class);
			catIdInt = intent.getIntExtra("catIdInt", 0);
			subCatId = intent.getIntExtra("subCatId", subCatId);
			startTimeString = dateTimeString + " " + timeSlotStringArray[0].toString();
			endTimeString = dateTimeString + " " + timeSlotStringArray[1].toString();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			diseaseLinear.setVisibility(View.VISIBLE);
			adapter.clear();
			viewSwitcher.setDisplayedChild(1);
			Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

			File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
			FileOutputStream fo;
			try {
				destination.createNewFile();
				fo = new FileOutputStream(destination);
				fo.write(bytes.toByteArray());
				fo.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Uri tempuri = getImageUri(this, thumbnail);
			imgFile = new File(getRealPathFromURI(this, tempuri));
			if (dataT.size() <= 5) {
				CustomGallery item = new CustomGallery();
				item.sdcardPath = imgFile.toString();
				dataT.add(item);

				viewSwitcher.setDisplayedChild(0);
				adapter.addAll(dataT);
			} else {
				Toast.makeText(getApplicationContext(), "Only 5 Image Allowed", Toast.LENGTH_SHORT).show();
			}

		} else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
			diseaseLinear.setVisibility(View.VISIBLE);
			all_path = data.getStringArrayExtra("all_path");

			for (String string : all_path) {
				if (dataT.size() <= 5) {
					CustomGallery item = new CustomGallery();
					item.sdcardPath = string;
					dataT.add(item);
				}
			}
			if (dataT.size() <= 5) {
				adapter.addAll(dataT);
			} else {
				Toast.makeText(getApplicationContext(), "Only 5 Image Allowed", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void getDataFromRadio() {
		int selectedId = haveVisitedRadioGroup.getCheckedRadioButtonId();
		if (selectedId == oldPatientRadioButton.getId()) {
			haveVisitedString = "true";
		} else {
			haveVisitedString = "false";
		}
	}

	private class addAppointment extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			Date date = null;
			Date stDate = null;
			Date edDate = null;
			SimpleDateFormat df = new SimpleDateFormat("dd, MMM yyyy");
			SimpleDateFormat startdf = new SimpleDateFormat(
					"dd, MMM yyyy HH:mm a");
			SimpleDateFormat enddf = new SimpleDateFormat(
					"dd, MMM yyyy HH:mm a");
			SimpleDateFormat finalstartdf = new SimpleDateFormat(
					"dd, MMM yyyy hh:mm");

			try {
				date = df.parse(dateTimeString);
				stDate = startdf.parse(startTimeString);
				edDate = enddf.parse(endTimeString);
				dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url.getUrl() + "addAppointment");

				MultipartEntity reqEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				reqEntity.addPart("UserId", new StringBody(String.valueOf(3015)));
				reqEntity.addPart("DocInfoId", new StringBody(doctProfileModel
						.getDocInfoId().toString()));
				reqEntity.addPart("AppointmentDate", new StringBody(
						dateFormatter.format(date).toString()));
				reqEntity.addPart("StartTime", new StringBody(newStartTime));
				reqEntity.addPart("EndTime", new StringBody(newEndTime));
				reqEntity.addPart("SubCategoryId",
						new StringBody(String.valueOf(subCatId)));
				reqEntity.addPart("DoctorSeen", new StringBody(
						haveVisitedString));
				reqEntity.addPart("DiseasesDescription", new StringBody(
						descriptionString));
				if (dataT != null) {
					if (dataT.size() != 0) {
						for (int j = 0; j < dataT.size(); j++) {
							File f = new File(dataT.get(j).sdcardPath.toString());
							reqEntity.addPart("DiseasesImage", new FileBody(f));
						}
					}
				}

				httppost.setEntity(reqEntity);
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				InputStream instream = entity.getContent();
				resultData = streamToString(instream);

			} catch (Exception ex) {
				ex.printStackTrace();
				Log.i("Register User Exception", "reg" + ex.toString());
			}
			try {
				JSONObject json = new JSONObject(resultData);
				code = (json.getInt("error_code"));
				if (code == 0) {
					Log.i("msg", "Appointment Successfully Inserted");
				}
			} catch (Exception ex) {
				Log.i("TAG23", ex.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			if (code == 0) {
				Toast.makeText(getApplicationContext(), "Appoinment Added", Toast.LENGTH_SHORT).show();
				dataT.clear();
				diseaseLinear.removeAllViews();
				Intent intentMessage = new Intent();
				setResult(22, intentMessage);
				finish();
				DisplayDoctorListActivity.displayDoctorActivity.finish();
				overridePendingTransition(R.anim.fadein, R.anim.exit_to_right);
			} else {
				Toast.makeText(getApplicationContext(), "Please Try Again Later", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = ProgressDialog.show(AddAppointmentActivity.this, "", "",  true);
			pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			pDialog.setContentView(R.layout.dialog);
			ProgressBar progressBar = (ProgressBar) pDialog.findViewById(R.id.progressWheel);
			Circle circle = new Circle();

			circle.setColor(getResources().getColor(R.color.white));
			progressBar.setIndeterminateDrawable(circle);
		}

	}

	private String streamToString(final InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw e;
			}
		}
		return sb.toString();
	}

	private Uri getImageUri(Context applicationContext, Bitmap photo) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(applicationContext.getContentResolver(), photo, "Title", null);
		return Uri.parse(path);

	}

	public static String getRealPathFromURI(Activity activity, Uri uri) {
		Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}

	@Override
	public void onMapClick(LatLng arg0) {
		Intent intent = new Intent(getBaseContext(), MapFragmentActivity.class);
		intent.putExtra("Latitude", latitude);
		intent.putExtra("Longitude", longitude);
		startActivityForResult(intent, 25);
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

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

	private boolean isValidText(String str) {
		// String FULLNAME_PATTERN = "[A-Z][a-z]+( [A-Z][a-z]+)*";
		String FULLNAME_PATTERN = "^[a-zA-Z ]*$";
		Pattern pattern = Pattern.compile(FULLNAME_PATTERN);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 1: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults[0] == PackageManager.PERMISSION_DENIED){
					finish();
				}else if(ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
						&& ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
					googleMap.setMyLocationEnabled(true);
					googleMap.setOnMapClickListener(this);
					LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
					Criteria criteria = new Criteria();
					String bestProvider = locationManager.getBestProvider(criteria, true);
					Location location = locationManager.getLastKnownLocation(bestProvider);
					if (location != null) {
						onLocationChanged(location);
					}
					locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
				}
				return;
			}
			case 2: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults[0] == PackageManager.PERMISSION_DENIED){

				}else if(ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
						&& ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
						&& ContextCompat.checkSelfPermission(AddAppointmentActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, 100);
				}
				return;
			}

		}
	}

}
