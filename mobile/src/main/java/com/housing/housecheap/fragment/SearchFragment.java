package com.housing.housecheap.fragment;

import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.housing.housecheap.R;
import com.housing.housecheap.SearchPropertyActivity;
import com.housing.housecheap.model.City;
import com.housing.housecheap.model.Propertytype;
import com.housing.housecheap.utility.GetUrl;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

	int pos;
	ProgressDialog mProgressDialog;
	GetUrl url = new GetUrl();
	ArrayList<Propertytype> pType = new ArrayList<Propertytype>();
	ArrayList<City> city = new ArrayList<City>();
	ArrayList<String> getPTypeName = new ArrayList<String>();
	public static CheckBox cb;
	LinearLayout ll, lvDynamicChkbox;
	public ArrayList<String> selectedPropertyName = new ArrayList<String>();
	public static ArrayList<Integer> selectedPropertyId = new ArrayList<Integer>();
	ArrayList<String> categories = new ArrayList<String>();
	Spinner spinnerCity;
	Context context;
	public static String minVal = "", maxVal = "";
	public static String selectCity;
	TextView tvBedroom, tvBathroom;
	public static int bhkType=2, bathroom=2;
	int counter = 0;
	int counterBedroom = 2, counterBathroom = 2;
	LinearLayout lvMinus, lvPlus, lvMinusBathroom, lvPlusBathroom;
	SearchFragment searchFrag;
	public static final String ARG_PAGE = "ARG_PAGE";
	String tabTitle;
	int page,cPosition;

	public SearchFragment() {

	}

	public static SearchFragment newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		SearchFragment fragment = new SearchFragment();
		fragment.setArguments(args);
		// Toast.makeText(ctx, page+" ", Toast.LENGTH_SHORT).show();
		return fragment;
	}

	/*
	 * @Override public void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); page =
	 * getArguments().getInt("someInt", 0);
	 * 
	 * Log.d("position", page + ""); // Toast.makeText(getActivity(), page,
	 * Toast.LENGTH_SHORT).show();
	 * 
	 * }
	 */

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.searchproperty_fragment,
				container, false);

		setView(view);
		return view;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	public void setView(View rootView) {
		// pagePos = new DetailOnPageChangeListener();

		int id = SearchPropertyActivity.pager.getCurrentItem();
		// int idd = SearchPropertyActivity.pager.ge

		// int ii = 0;
		// int id = SearchPropertyActivity.getItem(ii);

		//cPosition=SearchPropertyActivity.currentPosition;

		//Toast.makeText(getActivity(),SearchPropertyActivity.currentPosition+" //",Toast.LENGTH_SHORT).show();

		lvMinus = (LinearLayout) rootView.findViewById(R.id.lvMinusBedroomsss);
		lvPlus = (LinearLayout) rootView.findViewById(R.id.lvPlusBedroomsss);
		lvPlus.setOnClickListener(bedroomPlus);
		lvMinus.setOnClickListener(bedroomMinus);
		tvBedroom = (TextView) rootView.findViewById(R.id.tvIncrementBedrooms);

		lvMinusBathroom = (LinearLayout) rootView
				.findViewById(R.id.lvMinusBathrooms);
		lvPlusBathroom = (LinearLayout) rootView
				.findViewById(R.id.lvPlusBathrooms);
		lvPlusBathroom.setOnClickListener(bathroomPlus);
		lvMinusBathroom.setOnClickListener(bathroomMinus);
		tvBathroom = (TextView) rootView
				.findViewById(R.id.tvIncrementBathrooms);

		pos = getArguments().getInt("position");
		// Toast.makeText(getActivity(), pos + " /", Toast.LENGTH_SHORT).show();

		// tabTitle = getArguments().getString("itemname");
		/*
		 * Toast.makeText(getActivity(), tabTitle + " /", Toast.LENGTH_SHORT)
		 * .show();
		 */

		/*
		 * tabTitle = getArguments().getString("title");
		 * Toast.makeText(getActivity(), tabTitle + " /", Toast.LENGTH_SHORT)
		 * .show();
		 */

		lvDynamicChkbox = (LinearLayout) rootView
				.findViewById(R.id.lvCheckBoxSerach);

		spinnerCity = (Spinner) rootView.findViewById(R.id.spinnerSelectCity);
		spinnerCity.setOnItemSelectedListener(selectSpinnerCity);

		pType = (ArrayList<Propertytype>) getArguments().getSerializable(
				"propertyType");

		city = (ArrayList<City>) getArguments().getSerializable("getCity");
		/*
		 * categories = (ArrayList<String>) getArguments().getSerializable(
		 * "getCity1");
		 */

		categories.clear();
		categories.add("Enter Locality");
		for (int i = 0; i < city.size(); i++) {

			categories.add(city.get(i).getName());
		}

		spinnerCity.setAdapter(new selectCityAdapter(rootView.getContext(),
				R.layout.datetime_requirement, categories));

		// Log.d("city", categories + "");

		// GetPropertyType();

		// Toast.makeText(getActivity(), pos + " ", Toast.LENGTH_SHORT).show();
		dynamicPropertyType();

		RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(
				getActivity());
		// Set the range
		rangeSeekBar.setRangeValues(100000, 60000000);

		rangeSeekBar.setSelectedMinValue(500000);
		rangeSeekBar.setSelectedMaxValue(60000000);

		// Add to layout
		//
		// Toast.makeText(getApplicationContext(),
		// RangeSeekBar.minText + " " + RangeSeekBar.maxText,
		// Toast.LENGTH_SHORT).show();

		rangeSeekBar
				.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

					@Override
					public void onRangeSeekBarValuesChanged(
							RangeSeekBar<?> bar, Integer minValue,
							Integer maxValue) {

						minVal = String.valueOf(minValue);
						maxVal = String.valueOf(maxValue);

						maxVal = maxVal.substring(0, maxVal.length() - 7);
						minVal = minVal.substring(0, minVal.length() - 5);

					}
				});

		LinearLayout layout = (LinearLayout) rootView
				.findViewById(R.id.seekbar_Range);
		layout.addView(rangeSeekBar);



	}

	public OnItemSelectedListener selectSpinnerCity = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub

			selectCity = spinnerCity.getSelectedItem().toString();

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}
	};

	public class selectCityAdapter extends ArrayAdapter<String> {

		public selectCityAdapter(Context context, int resource,
				List<String> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub

		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = getActivity().getLayoutInflater();
			convertView = inflater.inflate(R.layout.search_citylayout, parent,
					false);
			TextView label = (TextView) convertView
					.findViewById(R.id.tvSearchCity);
			label.setText(categories.get(position));

			return convertView;
		}

	}





	public OnClickListener bedroomPlus = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// for (int i = 2; i <= 6; i++) {

			if (counterBedroom != 6) {
				counterBedroom++;
				tvBedroom.setText(counterBedroom + "");
			} else {
				tvBedroom.setText(counterBedroom + "");
			}

			bhkType = Integer.parseInt(tvBedroom.getText().toString());
			// }
		}
	};

	public OnClickListener bedroomMinus = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (counterBedroom == 2) {

				tvBedroom.setText(counterBedroom + "");
			} else {

				counterBedroom--;
				tvBedroom.setText(counterBedroom + "");

			}
			bhkType = Integer.parseInt(tvBedroom.getText().toString());

		}
	};

	public OnClickListener bathroomPlus = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// for (int i = 2; i <= 6; i++) {

			if (counterBathroom != 6) {
				counterBathroom++;
				tvBathroom.setText(counterBathroom + "");
			} else {
				tvBathroom.setText(counterBathroom + "");
			}

			bathroom = Integer.parseInt(tvBathroom.getText().toString());
			// }
		}
	};

	public OnClickListener bathroomMinus = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (counterBathroom == 2) {

				tvBathroom.setText(counterBathroom + "");
			} else {

				counterBathroom--;
				tvBathroom.setText(counterBathroom + "");

			}
			bathroom = Integer.parseInt(tvBathroom.getText().toString());

		}
	};
//
//	public void postSearchProperty() {
//
//		mProgressDialog = ProgressDialog.show(getActivity(), "", "Progress",
//				true);
//
//		RestAdapter adapter = new RestAdapter.Builder()
//				.setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL).build();
//
//		RetrofitPathJavaClass restInterface = adapter
//				.create(RetrofitPathJavaClass.class);
//
//		restInterface.searchProperty(selectCity, "sell", bhkType, minVal,
//				maxVal, "600 sqft", "15000 sqft", selectedPropertyId, bathroom,
//				new retrofit.Callback<SearchPropertyPost>() {
//
//					@Override
//					public void success(SearchPropertyPost model, Response arg1) {
//						// TODO Auto-generated method stub
//						if (mProgressDialog.isShowing())
//							mProgressDialog.dismiss();
//					}
//
//					@Override
//					public void failure(RetrofitError error) {
//						// TODO Auto-generated method stub
//						if (mProgressDialog.isShowing())
//							mProgressDialog.dismiss();
//						String merror = error.getMessage();
//						Log.d("failure", merror);
//					}
//
//				});
//
//	}

	public void dynamicPropertyType() {
		int counter = 0;
		getPTypeName.clear();
		for (int i = 0; i < pType.size(); i++) {

			getPTypeName.add(pType.get(i).getName());

		}

		int a = getPTypeName.size() / 3;

		int xx = (int) Math.ceil((getPTypeName.size() + 1) / 3);
		for (int i = 1; i < 20; i += 3) {

			if ((getPTypeName.size()) == i) {
				xx = xx + 1;
			}
		}

		for (int i = 0; i < xx; i++) {

			ll = new LinearLayout(getActivity());

			ll.setOrientation(LinearLayout.HORIZONTAL);

			for (int j = 0; j < a; j++) {

				cb = new CheckBox(getActivity());
				@SuppressWarnings("deprecation")
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

				lp.weight = (float) 1.6;
				cb.setLayoutParams(lp);
				cb.setTextSize(12);
				if (counter >= getPTypeName.size()) {
					cb.setText("");
					cb.setVisibility(View.INVISIBLE);

				} else {
					//cb.setChecked(false);
					cb.setText(getPTypeName.get(counter));
					cb.setId(pType.get(counter).getId());

				}
				cb.setTextColor(getResources()
						.getColor(R.color.sign_in_tvcolor));
				ll.addView(cb);
				counter++;

			//	selectedPropertyId.clear();
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub

						if (isChecked) {

//							selectedPropertyName.add(buttonView.getText()
//									.toString());
							selectedPropertyId.add(buttonView.getId());

							//Toast.makeText(getActivity(),buttonView.getId()+" add",Toast.LENGTH_SHORT).show();
						} else {
							for (int i = 0; i < selectedPropertyId.size(); i++)
		 					 {
			  					if(selectedPropertyId.get(i)==(buttonView.getId()))
								{
									selectedPropertyId.remove(buttonView.getText()
											.toString());

									//Toast.makeText(getActivity(),buttonView.getId()+" remove",Toast.LENGTH_SHORT).show();
								}

		 					  }

							//selectedPropertyId.remove(buttonView.getId());



						}

					}
				});
			}

			lvDynamicChkbox.addView(ll);

		}


//		  String idd = "";
//
//		  for (int i = 0; i < selectedPropertyId.size(); i++)
//		  {
//
//		  idd = idd + selectedPropertyId.get(i) + " ";
//
//		  }
//
//		  Toast.makeText(getActivity(), idd, Toast.LENGTH_SHORT).show();

	}

}
