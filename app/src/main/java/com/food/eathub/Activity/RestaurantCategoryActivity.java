package com.food.eathub.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.food.eathub.Fragment.RestCategoryDishFragment;
import com.food.eathub.Interface.RestInterface;
import com.food.eathub.Model.AllCategorybymenuModel;
import com.food.eathub.Model.AllMenuOfRestModel;
import com.food.eathub.Model.FeedBackDetailModel;
import com.food.eathub.Model.GetAllRestaurantsModel;
import com.food.eathub.Model.GetRestaurantAndCuisineModel;
import com.food.eathub.Model.GetRestaurantDetailModel;
import com.food.eathub.Model.GetUrlofApi;
import com.food.eathub.Model.feedback.FeedBackswithrest;
import com.food.eathub.Model.getAllmenuDetailByrestModel;
import com.food.eathub.R;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RestaurantCategoryActivity extends ActionBarActivity implements OnQueryTextListener {
    Toolbar toolbar;
    RestAdapter adapter;
    GetUrlofApi url;
    RestInterface inface;
    int id;
    GetRestaurantDetailModel restModel;
    ViewPager pager;
    PagerSlidingTabStrip tabs;
    String[] TITLES = new String[20];
    ArrayList<AllCategorybymenuModel> cat = new ArrayList<AllCategorybymenuModel>();
    ArrayList<AllMenuOfRestModel> menu = new ArrayList<AllMenuOfRestModel>();
    GetRestaurantAndCuisineModel restInfo;
    PagerSlidingTabStrip pagersliding;
    ImageView ivCatRestImage;
    TextView tvCatRestname, tvCatRestCuisine, tvCatRestReview;
    RatingBar rbCatRestRating;
    String cuisineDetail;
    public static int deliveryfee;
    public static float servicetax;
    ImageView ivOverflowButtonmenu;
    Gson gson;
    ArrayList<FeedBackswithrest> feedbacks;
    FeedBackDetailModel feedback;
    CardView cvDisheeeeNODataCard;
    String openornot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_category);

        Intent i = getIntent();
        id = Integer.parseInt(i.getStringExtra("restId"));
        openornot = i.getStringExtra("openornot");
        initControl();
    }

    public RestaurantCategoryActivity() {

    }

    /**
     * Adapter for display pager view for restaurant category
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return cat.get(position).getHeaderName();
        }

        @Override
        public int getCount() {
            return cat.size();
        }

        @Override
        public Fragment getItem(int position) {
            try {
                getRestById();
            } catch (Exception e) {
                e.printStackTrace();
            }
            RestCategoryDishFragment f = new RestCategoryDishFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putInt("catId", cat.get(position).getMenuCategoryId());
            bundle.putInt("restId", id);
            bundle.putSerializable("menu", menu);
            f.setArguments(bundle);
            return f;
        }
    }

    /**
     * Initialize all UI components
     */
    public void initControl() {
        cvDisheeeeNODataCard = (CardView) findViewById(R.id.cvDisheeeeNODataCard);
        feedback = new FeedBackDetailModel();
        feedbacks = new ArrayList<FeedBackswithrest>();
        gson = new Gson();
        ivOverflowButtonmenu = (ImageView) findViewById(R.id.ivOverflowButtonmenu);
        restInfo = new GetRestaurantAndCuisineModel();
        ivCatRestImage = (ImageView) findViewById(R.id.ivCatRestImage);
        tvCatRestname = (TextView) findViewById(R.id.tvCatRestName);
        tvCatRestCuisine = (TextView) findViewById(R.id.tvCatRestCuisineName);
        tvCatRestReview = (TextView) findViewById(R.id.tvCatRestReview);
        rbCatRestRating = (RatingBar) findViewById(R.id.rbCatRestFavoriteRating);
        pager = (ViewPager) findViewById(R.id.pager);
        pagersliding = new PagerSlidingTabStrip(getApplicationContext());
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        restModel = new GetRestaurantDetailModel();
        url = new GetUrlofApi();
        adapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL).build();

        inface = adapter.create(RestInterface.class);
        try {
            getRestById();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ProgressDialog dialog = ProgressDialog.show(RestaurantCategoryActivity.this, "", "", true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog);
        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressWheel);
        Circle circle = new Circle();
        circle.setColor(getResources().getColor(R.color.white));
        progressBar.setIndeterminateDrawable(circle);
        inface.getRestaurantDishMenu(id, new Callback<getAllmenuDetailByrestModel>() {
            @Override
            public void success(getAllmenuDetailByrestModel model, Response arg1) {
                if (model.getErrorCode() == 1) {
                    cvDisheeeeNODataCard.setVisibility(View.VISIBLE);
                    pager.setVisibility(View.GONE);
                    tabs.setVisibility(View.GONE);
                } else {
                    cvDisheeeeNODataCard.setVisibility(View.GONE);
                    pager.setVisibility(View.VISIBLE);
                    tabs.setVisibility(View.VISIBLE);
                }
                try {
                    menu.addAll(model.getMenu());
                    for (int i = 0; i < model.getMenu().size(); i++) {
                        AllCategorybymenuModel cat1 = new AllCategorybymenuModel();
                        cat1 = model.getMenu().get(i).getCategory();
                        cat.add(cat1);
                    }
                    pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                    pager.setOnPageChangeListener(page);
                    tabs.setBackgroundColor(getResources().getColor(R.color.white));
                    tabs.setIndicatorColor(getResources().getColor(R.color.toolBarColor));
                    tabs.setDividerColor(getResources().getColor(R.color.white));
                    tabs.setTextSize(25);
                    tabs.setAllCaps(true);
                    tabs.setViewPager(pager);
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void failure(RetrofitError arg0) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), arg0.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        setupToolbar();
    }

    public ViewPager.OnPageChangeListener page = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * Find restaurant by ID
     */
    public void getRestById() {
        inface.getRestaurantById(id, new Callback<GetAllRestaurantsModel>() {
            @Override
            public void success(GetAllRestaurantsModel model, Response arg1) {
                try {
                    restInfo = model.getRestaurant();
                    restModel = model.getRestaurant().getRestaurant();
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.displayImage(restModel.getRestaurantImage(), ivCatRestImage);
                    tvCatRestname.setText(restModel.getRestaurantName());
                    cuisineDetail = null;
                    for (int i = 0; i < model.getRestaurant().getCuisines().size(); i++) {
                        if (cuisineDetail != null) {
                            cuisineDetail = cuisineDetail + "," + model.getRestaurant().getCuisines().get(i).getCuisineName();
                        } else {
                            cuisineDetail = model.getRestaurant().getCuisines().get(i).getCuisineName();
                        }
                    }

                    tvCatRestCuisine.setText(cuisineDetail);
                    tvCatRestReview.setText(model.getRestaurant().getTotalReviews() + " Review");
                    rbCatRestRating.setStepSize((float) 0.5);
                    rbCatRestRating.setRating((float) restModel.getFavouriteRate());
                    deliveryfee = restModel.getDeliveryFee();
                    servicetax = Float.parseFloat(restModel.getServiceTax().toString());
                    toolbar.setTitle(restModel.getRestaurantName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError arg0) {
                Toast.makeText(getApplicationContext(), arg0.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.restaurant_categoty, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.itemCatSearch).getActionView();
        searchView.setOnQueryTextListener(RestaurantCategoryActivity.this);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        EditText searchEdit = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));

        searchEdit.setHint("Search Dish");
        searchEdit.setHintTextColor(Color.WHITE);
        searchEdit.setTextSize(18);
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
        ((EditText) searchView
                .findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(Color.WHITE);
        ((ImageView) searchView
                .findViewById(android.support.v7.appcompat.R.id.search_close_btn))
                .setImageResource(R.drawable.cancel);
        ((ImageView) searchView
                .findViewById(android.support.v7.appcompat.R.id.search_button))
                .setImageResource(R.drawable.restsearch);
        ImageView closebutton = (ImageView) searchView
                .findViewById(R.id.search_close_btn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemrestInfo) {
            Intent i = new Intent(RestaurantCategoryActivity.this, RestInformationActivity.class);
            i.putExtra("restInfo", restInfo);
            i.putExtra("openornot", openornot);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String arg0) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        return false;
    }

}
