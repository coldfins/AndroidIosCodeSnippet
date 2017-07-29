package com.medical.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.medical.adapter.GooglePlacesAutocompleteAdapter;
import com.medical.adapter.SpecialistSpinnerAdapter;
import com.medical.adapter.SubSpecialistSpinnerAdapter;
import com.medical.doctfin.DisplayDoctorListActivity;
import com.medical.doctfin.R;
import com.medical.doctfin.UserHomeActivity;
import com.medical.model.SpecialistCategoryListModel;
import com.medical.model.SpecialistCategoryResponse;
import com.medical.model.SpecialistSubCategoryModel;
import com.medical.model.SpecialistSubcatResponse;
import com.medical.ratrofitinterface.DoctFin;
import com.medical.utils.ConnectionDetector;
import com.medical.utils.GetAllUrl;
import com.medical.utils.TagClass;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FindDoctorFragment extends Fragment {
	private View view;
	private TextView selectDateTextView, selectSpecialistTextView,
			categoryTextView, subCatTextView;

	private Button findDoctorButton;
	private Calendar newCalander;
	private AutoCompleteTextView autocompleteView;
	private SimpleDateFormat dateFormatter;
	private DatePickerDialog datePickerDialog;
	private String dateStringFormat, catId = "1",
			subCatId;
	private ArrayList<SpecialistCategoryListModel> restSpecialistCategoryListModel = new ArrayList<SpecialistCategoryListModel>();
	private ArrayList<SpecialistSubCategoryModel> restSpecialistSubCatArrayList = new ArrayList<SpecialistSubCategoryModel>();
	private double latitude, longitude;
	public Address address;
	private String addressText;
	public LatLng latLng;
	private String srcString, tempDateString;
	public String description;
	private Spinner specialistSpinner, subSpecialistSpinner;
	private GetAllUrl url;
	private SpecialistCategoryListModel specialistModel = new SpecialistCategoryListModel();
	private SpecialistSubCategoryModel subSpeciaListModel = new SpecialistSubCategoryModel();
	private ConnectionDetector connectionDetector;
	private boolean isConnectionActive = false;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.find_doctor_fragment, container, false);
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
				"Find Doctor");
		((ActionBarActivity) getActivity()).getSupportActionBar().setElevation(
				8);
		connectionDetector = new ConnectionDetector(getActivity());
		isConnectionActive = connectionDetector.isConnectingToInternet();
		if (!isConnectionActive) {
			initAlertDialog();
		} else {
			setResources();
			setDatePicker();
			setDateTimeField();
		}

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void setResources() {
		selectDateTextView = (TextView) view.findViewById(R.id.selectDateTextView);
		url = new GetAllUrl();

		findDoctorButton = (Button) view.findViewById(R.id.findDoctorButton);
		findDoctorButton.setOnClickListener(findDoctorClick);
		newCalander = Calendar.getInstance();
		selectDateTextView.setOnClickListener(selectDateOnClick);
		specialistSpinner = (Spinner) view.findViewById(R.id.specialistSpinner);

		subSpecialistSpinner = (Spinner) view
				.findViewById(R.id.subSpecialistSpinner);

		autocompleteView = (AutoCompleteTextView) view
				.findViewById(R.id.selectLocationEditText);
		autocompleteView.setAdapter(new GooglePlacesAutocompleteAdapter(
				getActivity(), R.layout.autocompletelocation_list_item));

		autocompleteView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				description = (String) parent.getItemAtPosition(position);
			}
		});

		if (UserHomeActivity.hashval.containsKey("two")) {
			restSpecialistCategoryListModel = new ArrayList<SpecialistCategoryListModel>();
			restSpecialistCategoryListModel
					.addAll((ArrayList<SpecialistCategoryListModel>) UserHomeActivity.hashval
							.get("two"));
			specialistSpinner.setAdapter(new SpecialistSpinnerAdapter(
					restSpecialistCategoryListModel, getActivity()));
		} else {
			setRestAdapter();
		}

		if (UserHomeActivity.hashval.containsKey(TagClass.onePosition)) {
			restSpecialistSubCatArrayList = new ArrayList<SpecialistSubCategoryModel>();
			restSpecialistSubCatArrayList
					.addAll((ArrayList<SpecialistSubCategoryModel>) UserHomeActivity.hashval
							.get(TagClass.onePosition));
			subSpecialistSpinner
					.setOnItemSelectedListener(subSpecialistItemSelect);
		} else {

			setSubCatRestAdapter();

		}

		specialistSpinner.setOnItemSelectedListener(specialistItemSelect);
		subSpecialistSpinner.setOnItemSelectedListener(subSpecialistItemSelect);

	}

	OnItemSelectedListener specialistItemSelect = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View spinnerview, int position, long id) {
			specialistModel = (SpecialistCategoryListModel) parent.getItemAtPosition(position);
			catId = specialistModel.getCategoryId().toString();
			setSubCatRestAdapter();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	OnItemSelectedListener subSpecialistItemSelect = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View spinnerview,
				int position, long id) {
			subSpeciaListModel = (SpecialistSubCategoryModel) parent
					.getItemAtPosition(position);
			subCatId = subSpeciaListModel.getSubCategoryId().toString();

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	OnClickListener selectDateOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			datePickerDialog.show();
		}
	};

	OnClickListener findDoctorClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
					.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getActivity()
					.getCurrentFocus().getWindowToken(), 0);
			if (description != null) {
				dateStringFormat = selectDateTextView.getText().toString();
				isConnectionActive = connectionDetector
						.isConnectingToInternet();
				if (!isConnectionActive) {
					initAlertDialog();
				} else {
					new GeocoderTask().execute(description, catId, subCatId,
							dateStringFormat);
				}
			}
		}
	};

	private void setDatePicker() {
		selectDateTextView = (TextView) view
				.findViewById(R.id.selectDateTextView);
		selectDateTextView.setInputType(InputType.TYPE_NULL);
		selectDateTextView.requestFocus();

	}

	private void setDateTimeField() {
		try {
			dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

			final long date = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy",
					Locale.US);
			String dateString = sdf.format(date);
			final Date currentDate = sdf.parse(dateString);
			selectDateTextView.setText(dateString);
			datePickerDialog = new DatePickerDialog(getActivity(),
					new OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {

							try {
								Date sdate = null;
								tempDateString = String.valueOf(year) + "-"
										+ String.valueOf(monthOfYear + 1) + "-"
										+ String.valueOf(dayOfMonth);
								SimpleDateFormat df = new SimpleDateFormat(
										"yyyy-MM-dd");

								sdate = df.parse(tempDateString.toString());

								if ((sdate.after(currentDate) || isSameDay(
										currentDate, sdate))) {
									dateFormatter = new SimpleDateFormat(
											"dd MMM, yyyy", Locale.US);
									selectDateTextView.setText(dateFormatter
											.format(sdate).toString());
								} else {
									Toast.makeText(getActivity(),
											"Select Appropriate Date",
											Toast.LENGTH_SHORT).show();
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}, newCalander.get(Calendar.YEAR),
					newCalander.get(Calendar.MONTH),
					newCalander.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.getDatePicker().setMinDate(date);
		} catch (Exception ex) {
			Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
		}
	}

	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
					.get(Calendar.DAY_OF_YEAR) == cal2
				.get(Calendar.DAY_OF_YEAR));
	}

	public class GeocoderTask extends AsyncTask<String, Void, List<Address>> {
		private String tempcatId, tempsubCatid, tempDate;

		@Override
		protected List<Address> doInBackground(String... locationName) {
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getActivity().getBaseContext());
			List<Address> addresses = null;
			tempcatId = locationName[1];
			tempsubCatid = locationName[2];
			tempDate = locationName[3];

			try {
				addresses = geocoder.getFromLocationName(locationName[0], 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return addresses;
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {
			try {
				if (addresses == null || addresses.size() == 0) {
					Toast.makeText(getActivity(), "No Location found",
							Toast.LENGTH_SHORT).show();
				}

				// Adding Markers on Google Map for each matching address
				for (int i = 0; i < addresses.size(); i++) {
					Log.i("TAG11", "Post Execute");
					address = (Address) addresses.get(i);

					latLng = new LatLng(address.getLatitude(), address.getLongitude());

					addressText = String.format(
							"%s, %s",
							address.getMaxAddressLineIndex() > 0 ? address
									.getAddressLine(0) : "", address
									.getCountryName());
				}

				if (latLng != null) {
					Intent startDoctList = new Intent(getActivity(),
							DisplayDoctorListActivity.class);

					startDoctList
							.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startDoctList.putExtra("catId", tempcatId);
					startDoctList.putExtra("subCatId", tempsubCatid);
					startDoctList.putExtra("latitude", latLng.latitude);
					startDoctList.putExtra("longitude", latLng.longitude);
					startDoctList.putExtra("appoinmentDate", tempDate);
					startActivityForResult(startDoctList, 22);
					getActivity().overridePendingTransition(R.anim.fadein,
							R.anim.exit_to_left);
				}
			} catch (Exception e) {
				Log.i("finDoctException", e.toString());
			}
		}
	}

	private void setRestAdapter() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				url.getUrl()).build();
		DoctFin doctFinInterFace = restAdapter.create(DoctFin.class);

		final ProgressDialog mProgressDialog = ProgressDialog.show(view.getContext(), "", "",
				true);

		mProgressDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		mProgressDialog.setContentView(R.layout.dialog);

		ProgressBar progressBar = (ProgressBar) mProgressDialog
				.findViewById(R.id.progressWheel);
		Circle circle = new Circle();

		circle.setColor(getResources().getColor(R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		doctFinInterFace
				.getSpecialistCategoryList(new Callback<SpecialistCategoryResponse>() {
					@Override
					public void success(SpecialistCategoryResponse model,
							Response response) {
						Log.i("TAB1",
								model.getErrorCode() + " " + model.getMsg());
						if (model.getErrorCode() == 0) {
							if (model.getCategoryList().size() != 0) {
								restSpecialistCategoryListModel = new ArrayList<SpecialistCategoryListModel>();

								restSpecialistCategoryListModel.addAll(model
										.getCategoryList());

								specialistSpinner
										.setAdapter(new SpecialistSpinnerAdapter(
												restSpecialistCategoryListModel,
												getActivity()));

								UserHomeActivity.hashval.put("two",
										restSpecialistCategoryListModel);

							}
						}
						if (mProgressDialog.isShowing())
							mProgressDialog.dismiss();
					}

					@Override
					public void failure(RetrofitError error) {
						Log.i("error", error.toString());
					}
				});
	}

	private void setSubCatRestAdapter() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				url.getUrl()).build();
		DoctFin doctFinInterFace = restAdapter.create(DoctFin.class);
		final ProgressDialog mProgressDialog = ProgressDialog.show(view.getContext(), "", "",
				true);

		mProgressDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		mProgressDialog.setContentView(R.layout.dialog);

		ProgressBar progressBar = (ProgressBar) mProgressDialog
				.findViewById(R.id.progressWheel);
		Circle circle = new Circle();

		circle.setColor(getResources().getColor(R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		
		
		doctFinInterFace.getSubSpecialistSubCategoryList(
				Integer.parseInt(catId),
				new Callback<SpecialistSubcatResponse>() {
					@Override
					public void success(SpecialistSubcatResponse model,
							Response response) {
						if (model.getErrorCode() == 0) {
							if (model.getSubCategoryList().size() != 0) {
								restSpecialistSubCatArrayList.clear();
								restSpecialistSubCatArrayList.addAll(model
										.getSubCategoryList());
								subSpecialistSpinner
										.setAdapter(new SubSpecialistSpinnerAdapter(
												restSpecialistSubCatArrayList,
												getActivity()));
								UserHomeActivity.hashval.put(
										TagClass.onePosition,
										restSpecialistSubCatArrayList);
							} else {

							}
							if (mProgressDialog.isShowing())
								mProgressDialog.dismiss();
						}
					}

					@Override
					public void failure(RetrofitError error) {
						Log.i("TAG4", "Error" + error.toString());
					}
				});
	}

	private void initAlertDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		alertDialog.setTitle(getResources().getString(
				R.string.internet_error_header));
		alertDialog.setMessage(getResources().getString(
				R.string.internet_error_string));
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				java.lang.System.exit(0);
				isConnectionActive = false;

			}
		});

		alertDialog.show();
	}

}