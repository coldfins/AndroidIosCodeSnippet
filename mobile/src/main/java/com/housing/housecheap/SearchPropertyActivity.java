package com.housing.housecheap;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.github.ybq.android.spinkit.style.Circle;
import com.housing.housecheap.adapter.SerachFragmentPagerAdapter;
import com.housing.housecheap.fragment.ProjectFragment;
import com.housing.housecheap.model.City;
import com.housing.housecheap.model.GetAllCity;
import com.housing.housecheap.model.GetPropertyType;
import com.housing.housecheap.model.PropertiesList;
import com.housing.housecheap.model.Propertytype;
import com.housing.housecheap.model.SearchPropertyPost;
import com.housing.housecheap.model.searchTabItem;
import com.housing.housecheap.utility.GetUrl;
import com.housing.housecheap.utility.RetrofitPathJavaClass;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchPropertyActivity extends ActionBarActivity {

	Toolbar toolBar;
	public static ViewPager pager;
	public PagerSlidingTabStrip tabs;
	SerachFragmentPagerAdapter searchFragment;
	ProgressDialog mProgressDialog;
	String propertyFor = "", getCity, minVal, maxVal,selectCity;
	GetUrl url = new GetUrl();
	int bhk,bhkType=2, bathroom=2,getPTypeId[];
	ArrayList<Propertytype> pType = new ArrayList<Propertytype>();
	ArrayList<PropertiesList> allProperty = new ArrayList<PropertiesList>();
	ArrayList<City> city = new ArrayList<City>();
	LinearLayout ll,lvDynamicChkbox;
	public static int currentPage;
	ArrayList<searchTabItem> item = new ArrayList<searchTabItem>();
	public  int currentPosition;
	TextView tvBedroom, tvBathroom;
	int counterBedroom = 2, counterBathroom = 2;
	LinearLayout lvMinus, lvPlus, lvMinusBathroom, lvPlusBathroom;
	Spinner spinnerCity,spinnerPropertType;
	ArrayList<String> categories = new ArrayList<String>();
	CheckBox cb;
	ArrayList<String> getPTypeName = new ArrayList<String>();
	public  ArrayList<Integer> selectedPropertyId = new ArrayList<Integer>();
	String[] propertFor;
	RadioButton rbForSell,rbForPg,rbForRent;
	RadioGroup propertyGroup;

	// ArrayList<searchTabItem> item = new ArrayList<searchTabItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_property);

		registerControl();

	}

	public void registerControl() {
		setupToolbar();

		toolBar.setPadding(0, getStatusBarHeight(), 0, 0);


		lvMinus = (LinearLayout)findViewById(R.id.lvMinusBedroomsssActivity);
		lvPlus = (LinearLayout)findViewById(R.id.lvPlusBedroomsssActivity);
		lvPlus.setOnClickListener(bedroomPlus);
		lvMinus.setOnClickListener(bedroomMinus);
		tvBedroom = (TextView)findViewById(R.id.tvIncrementBedroomsActivity);

		lvMinusBathroom = (LinearLayout)findViewById(R.id.lvMinusBathroomsActivity);
		lvPlusBathroom = (LinearLayout)findViewById(R.id.lvPlusBathroomsActivity);
		lvPlusBathroom.setOnClickListener(bathroomPlus);
		lvMinusBathroom.setOnClickListener(bathroomMinus);
		tvBathroom = (TextView)findViewById(R.id.tvIncrementBathroomsActivity);

		spinnerCity = (Spinner)findViewById(R.id.spinnerSelectCityActivity);
		spinnerPropertType = (Spinner)findViewById(R.id.spinnerSelectPropertyForActivity);
		spinnerCity.setOnItemSelectedListener(selectSpinnerCity);


		propertyGroup=(RadioGroup)findViewById(R.id.radioGrp);




		lvDynamicChkbox = (LinearLayout)findViewById(R.id.lvCheckBoxSerachActivity);

		propertFor=new String[4];
		propertFor[0] = "Select Property for";
		propertFor[1] = "For sell";
		propertFor[2] = "For rent";
		propertFor[3] = "For pg";

		spinnerPropertType
				.setAdapter(new PropertyTypeAdapter(getApplicationContext(),
						R.layout.search_citylayout, propertFor));

		spinnerPropertType.setOnItemSelectedListener(selectPropertyFor);

//
//		pager = (ViewPager) findViewById(R.id.searchPager);
//		tabs = (PagerSlidingTabStrip) findViewById(R.id.searchTab);
//
//		tabs.setIndicatorColor(getResources().getColor(R.color.WHITE));
//		tabs.setUnderlineColor(getResources().getColor(R.color.toolBarColor));
//
//
		GetPropertyType();

		RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(
				getApplicationContext());
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

		LinearLayout layout = (LinearLayout)findViewById(R.id.seekbar_RangeActivity);
		layout.addView(rangeSeekBar);


		//tabs.setOnPageChangeListener(tabChange);



	}



//	public OnPageChangeListener tabChange = new OnPageChangeListener() {
//		@Override
//		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//		}
//
//		@Override
//		public void onPageSelected(int position) {
//
//			currentPosition=position;
//
//			if(position==0)
//			{
//
//				bathroom=2;
//				bhk=2;
//
//				Toast.makeText(getApplicationContext(),SearchFragment.selectedPropertyId.size() +" //0",Toast.LENGTH_SHORT).show();
//
//			}
//			else if(position==1)
//			{
//
//
//			}
//			else if(position==2)
//			{
//
//				bathroom=2;
//				bhk=2;
//
//				SearchFragment.cb.setChecked(false);
//
//				Toast.makeText(getApplicationContext(),SearchFragment.selectedPropertyId.size() +" //2",Toast.LENGTH_SHORT).show();
//
//			}
//		}
//
//		@Override
//		public void onPageScrollStateChanged(int state) {
//
//		}
//	};



	public void setCurrentItem(int item, boolean smoothScroll) {
		pager.setCurrentItem(item, smoothScroll);
	}

	public static int getItem(int i) {
		// TODO Auto-generated method stub
		return pager.getCurrentItem() + i;
	}


	public AdapterView.OnItemSelectedListener selectSpinnerCity = new AdapterView.OnItemSelectedListener() {

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


	public AdapterView.OnItemSelectedListener selectPropertyFor = new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
								   int position, long id) {
			// TODO Auto-generated method stub

			//selectCity = spinnerCity.getSelectedItem().toString();

			if(spinnerPropertType.getSelectedItemPosition()==0)
			{
				propertyFor="";

			}
			else if(spinnerPropertType.getSelectedItemPosition()==1)
			{
				propertyFor="sell";
			}
			else if(spinnerPropertType.getSelectedItemPosition()==2)
			{
					propertyFor="rent";
			}
			else if(spinnerPropertType.getSelectedItemPosition()==3)
			{
				propertyFor="pg";
			}

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

			LayoutInflater inflater = getLayoutInflater();
			convertView = inflater.inflate(R.layout.search_citylayout, parent,
					false);
			TextView label = (TextView) convertView
					.findViewById(R.id.tvSearchCity);
			label.setText(categories.get(position));

			return convertView;
		}

	}



	public View.OnClickListener bathroomPlus = new View.OnClickListener() {

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

	public View.OnClickListener bathroomMinus = new View.OnClickListener() {

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

	public View.OnClickListener bedroomPlus = new View.OnClickListener() {

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

	public View.OnClickListener bedroomMinus = new View.OnClickListener() {

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

	public class PropertyTypeAdapter extends ArrayAdapter<String> {

		public PropertyTypeAdapter(Context context, int resource,
									 String[] objects) {
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

			LayoutInflater inflater = getLayoutInflater();
			convertView = inflater
					.inflate(R.layout.search_citylayout, parent, false);
			TextView label = (TextView) convertView
					.findViewById(R.id.tvSearchCity);
			label.setText(propertFor[position]);

			return convertView;
		}

	}

	/*
	 * public void setCurrentItem(int item, boolean smoothScroll) {
	 * pager.setCurrentItem(item, smoothScroll); }
	 */
	public void GetPropertyType() {
		
		mProgressDialog = ProgressDialog.show(SearchPropertyActivity.this, "", "", true);
		mProgressDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		mProgressDialog.setContentView(R.layout.dialog);
		ProgressBar progressBar = (ProgressBar) mProgressDialog
				.findViewById(R.id.progressWheel);
		Circle circle = new Circle();
		circle.setColor(getResources().getColor(R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		
//		mProgressDialog = ProgressDialog.show(SearchPropertyActivity.this, "",
//				"Progress", true);

		RestAdapter adapter = new RestAdapter.Builder()
				.setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL).build();

		final RetrofitPathJavaClass restInterface = adapter
				.create(RetrofitPathJavaClass.class);
		restInterface.allPropertyType(new Callback<GetPropertyType>() {

			@Override
			public void success(GetPropertyType model, Response arg1) {
				// TODO Auto-generated method stub
				if (mProgressDialog.isShowing())
					mProgressDialog.dismiss();

				pType.clear();

				pType.addAll(model.getPropertytypes());

				dynamicPropertyType();

				restInterface.getAllCityy(new Callback<GetAllCity>() {

					@Override
					public void failure(RetrofitError error) {
						// TODO Auto-generated method stub
						if (mProgressDialog.isShowing())
							mProgressDialog.dismiss();
						String merror = error.getMessage();
						Log.d("failure", merror);

					}

					@Override
					public void success(GetAllCity model, Response arg1) {
						// TODO Auto-generated method stub
						if (mProgressDialog.isShowing())
							mProgressDialog.dismiss();



						city.clear();
						city.addAll(model.getCities());
						// getCity = new GetAllCityAdapter(city,
						// getApplicationContext());
						// lvCity.setAdapter(getCity);
//
//						searchFragment = new SerachFragmentPagerAdapter(
//								getSupportFragmentManager(), pType, city);
//						pager.setAdapter(searchFragment);
//						tabs.setViewPager(pager);

						categories.clear();
						categories.add("Enter Locality");
						for (int i = 0; i < city.size(); i++) {

							categories.add(city.get(i).getName());
						}

						spinnerCity.setAdapter(new selectCityAdapter(getApplicationContext(),
								R.layout.datetime_requirement, categories));

					}
				});

				// dynamicPropertyType();

			}

			@Override
			public void failure(RetrofitError error) {
				// TODO Auto-generated method stub
				if (mProgressDialog.isShowing())
					mProgressDialog.dismiss();
				String merror = error.getMessage();
				Log.d("failure", merror);
			}
		});
	}

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

			ll = new LinearLayout(getApplicationContext());

			ll.setOrientation(LinearLayout.HORIZONTAL);

			for (int j = 0; j < a; j++) {

				cb = new CheckBox(SearchPropertyActivity.this);
				@SuppressWarnings("deprecation")
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);

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
				cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
												 boolean isChecked) {
						// TODO Auto-generated method stub

						if (isChecked) {

//							selectedPropertyName.add(buttonView.getText()
//									.toString());
							selectedPropertyId.add(buttonView.getId());

							//Toast.makeText(getApplicationContext(),buttonView.getId()+" add",Toast.LENGTH_SHORT).show();
						} else {

							for (int i = 0; i < selectedPropertyId.size(); i++)
							{
								if(selectedPropertyId.get(i)==(buttonView.getId()))
								{
									selectedPropertyId.remove(selectedPropertyId.get(i));

									Toast.makeText(getApplicationContext(),buttonView.getId()+" remove",Toast.LENGTH_SHORT).show();
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



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_property, menu);
		return true;
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.apply) {

			int selectedId = propertyGroup.getCheckedRadioButtonId();
			rbForSell=(RadioButton)findViewById(selectedId);
			String propertyForr=rbForSell.getText().toString();

			//Toast.makeText(getApplicationContext(),propertyFor,Toast.LENGTH_SHORT).show();




//			int getPageId = pager.getCurrentItem();
//
//			if (getPageId == 0) {
//				propertyFor = "sell";
//				getCity = SearchFragment.selectCity;
//				bhk = SearchFragment.bhkType;
//				bathroom = SearchFragment.bathroom;
//				minVal = SearchFragment.minVal;
//				maxVal = SearchFragment.maxVal;
//			//	Toast.makeText(getApplicationContext(),SearchFragment.selectedPropertyId.size()+"",Toast.LENGTH_SHORT).show();
//
//
//
//			} else if (getPageId == 1) {
//				propertyFor = "rent";
//				getCity = SearchFragment.selectCity;
//				bhk = SearchFragment.bhkType;
//				bathroom = SearchFragment.bathroom;
//				minVal = SearchFragment.minVal;
//				maxVal = SearchFragment.maxVal;
//				//Toast.makeText(getApplicationContext(),SearchFragment.selectedPropertyId.size()+"",Toast.LENGTH_SHORT).show();
//			}
//
//			else if (getPageId == 2) {
//				propertyFor = "pg";
//				getCity = SearchFragment.selectCity;
//				bhk = SearchFragment.bhkType;
//				bathroom = SearchFragment.bathroom;
//				minVal = SearchFragment.minVal;
//				maxVal = SearchFragment.maxVal;
//			//	Toast.makeText(getApplicationContext(),SearchFragment.selectedPropertyId.size()+"",Toast.LENGTH_SHORT).show();
//
//			}

			//Toast.makeText(getApplicationContext(),selectedPropertyId.size()+" ",Toast.LENGTH_SHORT).show();





//			Toast.makeText(
//					getApplicationContext(),propertyFor+" "+
//					SearchFragment.selectedPropertyId.size() + "  /" + bhk
//							+ " / " + bathroom+" "+getCity, Toast.LENGTH_SHORT).show();
//
			
			mProgressDialog = ProgressDialog.show(SearchPropertyActivity.this, "", "", true);
			mProgressDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(Color.TRANSPARENT));
			mProgressDialog.setContentView(R.layout.dialog);
			ProgressBar progressBar = (ProgressBar) mProgressDialog
					.findViewById(R.id.progressWheel);
			Circle circle = new Circle();
			circle.setColor(getResources().getColor(R.color.white));
			progressBar.setIndeterminateDrawable(circle);
			
			  //mProgressDialog = ProgressDialog.show(SearchPropertyActivity.this, "", "Progress", true);

			RestAdapter adapter = new RestAdapter.Builder()
					.setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL)
					.build();

			RetrofitPathJavaClass restInterface = adapter
					.create(RetrofitPathJavaClass.class);
			restInterface.searchProperty(getCity, propertyForr.toLowerCase(), bhkType, minVal+" L",
			  maxVal+" Cr", "600 sqft", "15000 sqft",
			  selectedPropertyId, bathroom, new
			  Callback<SearchPropertyPost>() {

			  @Override public void success(SearchPropertyPost model, Response
			  arg1) {


			  if (mProgressDialog.isShowing()) mProgressDialog.dismiss();

			  String code = model.getCode();

				  if (code.equals("0")) {
					  ProjectFragment.PropertyList.clear();
					  ProjectFragment.PropertyList.addAll(model.getPropertiesLists());

					  if (ProjectFragment.PropertyList.size() == 0) {
						  ProjectFragment.cvNoData.setVisibility(View.VISIBLE);
					  } else {

						  ProjectFragment.cvNoData.setVisibility(View.GONE);
						  ProjectFragment.propertyAdapter.notifyDataSetChanged();
					  }

			       		//Toast.makeText(getApplicationContext(), model.getMsg() + " ", Toast.LENGTH_SHORT).show();

					  onBackPressed();

				  }

			  }

			  @Override public void failure(RetrofitError error) {
				  if (mProgressDialog.isShowing())
			  mProgressDialog.dismiss();
				  String merror = error.getMessage();
				  Toast.makeText(getApplicationContext(), merror, Toast.LENGTH_SHORT).show(); }
			 // Log.d("failure", merror); }

			  });


			return false;
		}
		onBackPressed();
		return super.onOptionsItemSelected(item);
	}




	public void setupToolbar() {
		toolBar = (Toolbar) findViewById(R.id.toolBarSearchProperty);
		toolBar.setTitleTextColor(Color.WHITE);
		toolBar.setTitle("Search");
		final Drawable upArrow = getResources().getDrawable(
				R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		upArrow.setColorFilter(getResources().getColor(R.color.WHITE),
				android.graphics.PorterDuff.Mode.SRC_ATOP);
		setSupportActionBar(toolBar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(upArrow);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		this.overridePendingTransition(R.anim.anim_slide_in_right,
				R.anim.anim_slide_out_right);
	//	SearchFragment.selectedPropertyId.clear();


	}

}
