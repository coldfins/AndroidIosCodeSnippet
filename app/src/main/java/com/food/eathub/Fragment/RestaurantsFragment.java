package com.food.eathub.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.food.eathub.Activity.RestInformationActivity;
import com.food.eathub.Activity.RestaurantCategoryActivity;
import com.food.eathub.Activity.UserHomeActivity;
import com.food.eathub.Adapter.HomeRestaurantsAdapter;
import com.food.eathub.Interface.RestInterface;
import com.food.eathub.Model.GetAllRestaurantsModel;
import com.food.eathub.Model.GetRestaurantAndCuisineModel;
import com.food.eathub.Model.GetUrlofApi;
import com.food.eathub.Model.RestCuisineModel;
import com.food.eathub.R;
import com.github.ybq.android.spinkit.style.Circle;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RestaurantsFragment extends Fragment implements OnQueryTextListener {
	public static RecyclerView rvRests;
	@SuppressWarnings("rawtypes")
	public static HomeRestaurantsAdapter rvadapter;

	LinearLayoutManager rvlmanager;
	RestAdapter adapter;
	RestInterface inface;

	GetUrlofApi url;
	Double lati=21.1751266, longi=72.8064762;
	public static ArrayList<GetRestaurantAndCuisineModel> rm;
	ArrayList<RestCuisineModel> cuisines;
	SharedPreferences spLocation;
	static Menu menu;
	CardView cvRestaurantsNODataCard;
	View v;
	public static String city;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_restaurants, container, false);
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Restaurants");
		((ActionBarActivity) getActivity()).getSupportActionBar().setElevation(4);
		initControl(v);
		return v;
	}

	private void initControl(final View v) {
		cvRestaurantsNODataCard = (CardView) v.findViewById(R.id.cvRestaurantsNODataCard);

		spLocation = getActivity().getSharedPreferences("splatlong", v.getContext().MODE_PRIVATE);
		setHasOptionsMenu(true);

		rm = new ArrayList<GetRestaurantAndCuisineModel>();
		cuisines = new ArrayList<RestCuisineModel>();
		rvRests = (RecyclerView) v.findViewById(R.id.rvRestaurants);
		rvRests.setHasFixedSize(true);
		rvlmanager = new LinearLayoutManager(getActivity().getApplicationContext());

		rvRests.setLayoutManager(rvlmanager);

		url = new GetUrlofApi();
		adapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL).build();

		inface = adapter.create(RestInterface.class);
		if (UserHomeActivity.hashval.containsKey("2")) {
			rm = new ArrayList<GetRestaurantAndCuisineModel>();
			rm.addAll((ArrayList<GetRestaurantAndCuisineModel>) UserHomeActivity.hashval.get("2"));

			for (int i = 0; i < rm.size(); i++) {
				cuisines.addAll(rm.get(i).getCuisines());
			}
			city = rm.get(0).getRestaurant().getCity();
			rvadapter = new HomeRestaurantsAdapter(rm, getActivity().getApplicationContext(), lati, longi);
			rvRests.setAdapter(rvadapter);
		} else
		{
			getallrestaurants(v, lati, longi, 1);
		}
		rvRests.addOnItemTouchListener(new UserHomeActivity(getActivity()
				.getApplicationContext(), rvRests,
				new UserHomeActivity.OnItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
						TextView tv = (TextView) view.findViewById(R.id.tvRestaurantId);
						TextView cuisine = (TextView) view.findViewById(R.id.tvRestaurantCuisineDetail);
						TextView openorClosed = (TextView) view.findViewById(R.id.tvRestOpenOrClosed);

						if (openorClosed.getText().toString().equals("Closed now") && rm.get(position).getRestaurant().getPreOrderAvailable()) {
							Intent i = new Intent(getActivity().getApplicationContext(), RestaurantCategoryActivity.class);
							i.putExtra("restId", tv.getText().toString());
							i.putExtra("openornot", openorClosed.getText().toString());
							startActivity(i);

						} else if (openorClosed.getText().toString().equals("Closed now") && !rm.get(position).getRestaurant().getPreOrderAvailable()) {
							Intent i = new Intent(getActivity(), RestInformationActivity.class);
							i.putExtra("restInfo", rm.get(position));
							i.putExtra("openornot", openorClosed.getText().toString());
							startActivity(i);
						}
						else {
							Intent i = new Intent(getActivity().getApplicationContext(), RestaurantCategoryActivity.class);
							i.putExtra("restId", tv.getText().toString());
							i.putExtra("openornot", openorClosed.getText().toString());
							startActivity(i);
						}
						getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					}

					@Override
					public void onItemLongClick(View view, int position) {
					}
				}));
	}

	public void getallrestaurants(View v, final Double lati, final Double longi, int offset) {
		final ProgressDialog dialog = ProgressDialog.show(v.getContext(), "", "", true);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setContentView(R.layout.dialog);
		ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressWheel);
		Circle circle = new Circle();

		circle.setColor(getResources().getColor(R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		inface.getrestaurantsByLocation(lati, longi, offset, 7, new Callback<GetAllRestaurantsModel>() {
			@Override
			public void success(final GetAllRestaurantsModel arg0, Response arg1) {
				try {
					if (arg0.getErrorCode() == 1) {
						dialog.dismiss();
						cvRestaurantsNODataCard.setVisibility(View.VISIBLE);
						rvRests.setVisibility(View.GONE);
					} else if (arg0.getErrorCode() == 0) {
						cvRestaurantsNODataCard.setVisibility(View.GONE);
						rvRests.setVisibility(View.VISIBLE);
						rm.addAll(arg0.getRestaurants());
						UserHomeActivity.hashval.put("2", rm);

						for (int i = 0; i < arg0.getRestaurants().size(); i++) {
							cuisines.addAll(arg0.getRestaurants().get(i).getCuisines());
						}

						rvadapter = new HomeRestaurantsAdapter(rm, getActivity().getApplicationContext(), lati, longi);
						rvRests.setAdapter(rvadapter);
						dialog.dismiss();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void failure(RetrofitError arg0) {
				dialog.dismiss();
				Toast.makeText(getActivity().getApplicationContext(), "Network not available plz,try again later", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		this.menu = menu;
		inflater.inflate(R.menu.main, menu);
		ImageView closebutton;
		final SearchView searchView = (SearchView) menu.findItem(R.id.itemSearch).getActionView();
		searchView.setMaxWidth(Integer.MAX_VALUE);
		searchView.setOnQueryTextListener(this);
		EditText searchEdit = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));

		searchEdit.setHint("Search Restaurant");
		Field f;
		try {
			f = TextView.class.getDeclaredField("mCursorDrawableRes");
			f.setAccessible(true);
			try {
				f.set(searchEdit, R.drawable.searchcursor);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		searchEdit.setHintTextColor(Color.WHITE);
		((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.WHITE);
		((ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn)).setImageResource(R.drawable.cancel);
		((ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button)).setImageResource(R.drawable.restsearch);
		closebutton = (ImageView) searchView.findViewById(R.id.search_close_btn);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onQueryTextChange(String query) {
		ArrayList<GetRestaurantAndCuisineModel> filteredModelList = filter(rm, query);

		if (!query.trim().equals("") && query != null) {
			rvadapter = new HomeRestaurantsAdapter(filteredModelList, getActivity(), lati, longi);
			rvadapter.notifyDataSetChanged();
			rvRests.setAdapter(rvadapter);
			rvRests.scrollToPosition(0);
			cvRestaurantsNODataCard.setVisibility(View.GONE);
			rvRests.setVisibility(View.VISIBLE);
		} else {
			rvadapter = new HomeRestaurantsAdapter(filteredModelList, getActivity(), lati, longi);
			rvadapter.notifyDataSetChanged();
			rvRests.setAdapter(rvadapter);
			rvRests.scrollToPosition(0);
			cvRestaurantsNODataCard.setVisibility(View.GONE);
			rvRests.setVisibility(View.VISIBLE);
		}
		if (filteredModelList.isEmpty()) {
			cvRestaurantsNODataCard.setVisibility(View.VISIBLE);
			rvRests.setVisibility(View.GONE);
		}
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		return false;
	}

	@SuppressLint("DefaultLocale")
	private ArrayList<GetRestaurantAndCuisineModel> filter(ArrayList<GetRestaurantAndCuisineModel> models, String query) {
		String text = null, textrestname = null;
		query = query.toLowerCase();

		ArrayList<GetRestaurantAndCuisineModel> filteredModelList = new ArrayList<GetRestaurantAndCuisineModel>();
		if (query != "" && !query.equals("")) {
			for (GetRestaurantAndCuisineModel model : models) {
				textrestname = model.getRestaurant().getRestaurantName().toLowerCase();
				if (textrestname.contains(query)) {
					filteredModelList.add(model);
				}
				else {
					text = model.getRestaurant().getArea().toLowerCase();
					if (text.contains(query)) {
						filteredModelList.add(model);
					}
				}
			}
		}
		else {
			filteredModelList = rm;
		}
		return filteredModelList;
	}
}
