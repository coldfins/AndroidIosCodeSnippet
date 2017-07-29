package com.food.eathub.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.food.eathub.Interface.RestInterface;
import com.food.eathub.Model.GetRestaurantAndCuisineModel;
import com.food.eathub.Model.GetUrlofApi;
import com.food.eathub.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

@SuppressLint("SimpleDateFormat")
public class RestInformationActivity extends ActionBarActivity {

    Gson gson;
    RestAdapter adapter;
    RestInterface inface;
    GetUrlofApi url;
    GetRestaurantAndCuisineModel restdetail;
    ImageView ivRestaurantInfoImage, ivOverflowButtomMenu;
    TextView tvivRestaurantInfoName, ivRestaurantInfoCuisineName,
            tvCatRestaurantInfoReview, tvCatRestaurantInfoAddress, tvOPenNow;
    RatingBar rbCatRestaurantInfoFavoriteRating;
    Toolbar toolbar;
    static Menu menu;
    CardView cvRestaurantOpeningHour, cvRestaurantpaymethods,
            cvRestaurantreview, cvRestaurantlocation;
    TextView tvExpandArrowOpeningHour, tvExpandArrowpaymth,
            tvExpandArrowreview, tvExpandArrowlocation, tvSeeMoreReview;
    Typeface face;
    LinearLayout layoutOpeningHr, layoutPaymentInfo, layoutCashPayment,
            layoutOnlinePayment, layoutReview, layoutLocation,
            layoutUserReview;
    double lati, longi;
    int flagoh = 0, flagpm = 0, flagrv = 0, flagloc = 0;
    GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    ImageView ivopeninghr, ivinfopayment, ivreview, ivlocation;
    TextView tvOeningHourText, tvPaymentMethodsText, tvReviewsText,
            tvLocationText;
    String openornot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_inforamtion);
        initControl();
        setupToolbar();
    }

    /**
     * Initialize all UI components
     */
    public void initControl() {

        gson = new Gson();
        url = new GetUrlofApi();
        adapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL).build();
        inface = adapter.create(RestInterface.class);
        layoutUserReview = (LinearLayout) findViewById(R.id.layoutUserReview);
        tvSeeMoreReview = (TextView) findViewById(R.id.tvSeeMoreReview);
        ivOverflowButtomMenu = (ImageView) findViewById(R.id.ivOverflowButtonmenu);
        ivopeninghr = (ImageView) findViewById(R.id.ivopeninghr);
        ivinfopayment = (ImageView) findViewById(R.id.ivinfopayment);
        ivreview = (ImageView) findViewById(R.id.ivreview);
        ivlocation = (ImageView) findViewById(R.id.ivlocation);
        tvOeningHourText = (TextView) findViewById(R.id.tvOeningHourText);
        tvPaymentMethodsText = (TextView) findViewById(R.id.tvPaymentMethodsText);
        tvReviewsText = (TextView) findViewById(R.id.tvReviewsText);
        tvLocationText = (TextView) findViewById(R.id.tvLocationText);
        FragmentManager fManager = getSupportFragmentManager();
        mapFragment = (SupportMapFragment) fManager.findFragmentById(R.id.mapView);

        restdetail = new GetRestaurantAndCuisineModel();
        Intent i = getIntent();
        restdetail = (GetRestaurantAndCuisineModel) i.getSerializableExtra("restInfo");
        openornot = i.getStringExtra("openornot");

        googleMap = mapFragment.getMap();

        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(restdetail.getRestaurant().getLatitude(), restdetail.getRestaurant().getLongitude()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarekerpink))
                .title(restdetail.getRestaurant().getRestaurantName())
                .snippet(restdetail.getRestaurant().getAddress());
        googleMap.addMarker(marker);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                restdetail.getRestaurant().getLatitude(), restdetail
                .getRestaurant().getLongitude()), 14.0f));
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);

        face = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        ivRestaurantInfoImage = (ImageView) findViewById(R.id.ivRestaurantInfoImage);
        tvivRestaurantInfoName = (TextView) findViewById(R.id.tvivRestaurantInfoName);
        ivRestaurantInfoCuisineName = (TextView) findViewById(R.id.ivRestaurantInfoCuisineName);
        tvCatRestaurantInfoReview = (TextView) findViewById(R.id.tvCatRestaurantInfoReview);
        tvCatRestaurantInfoAddress = (TextView) findViewById(R.id.tvCatRestaurantInfoAddress);
        tvOPenNow = (TextView) findViewById(R.id.tvOPenNow);
        rbCatRestaurantInfoFavoriteRating = (RatingBar) findViewById(R.id.rbCatRestaurantInfoFavoriteRating);

        cvRestaurantOpeningHour = (CardView) findViewById(R.id.cvRestaurantOpeningHour);
        cvRestaurantpaymethods = (CardView) findViewById(R.id.cvRestaurantPaymentMethods);
        cvRestaurantreview = (CardView) findViewById(R.id.cvRestaurantReviews);
        cvRestaurantlocation = (CardView) findViewById(R.id.cvRestaurantLocation);

        tvExpandArrowOpeningHour = (TextView) findViewById(R.id.tvExpandArrowOpeningHour);
        tvExpandArrowpaymth = (TextView) findViewById(R.id.tvExpandArrowPaymentMethods);
        tvExpandArrowreview = (TextView) findViewById(R.id.tvExpandArrowreviews);
        tvExpandArrowlocation = (TextView) findViewById(R.id.tvExpandArrowLocation);

        layoutOpeningHr = (LinearLayout) findViewById(R.id.layoutOpeningHr);
        layoutPaymentInfo = (LinearLayout) findViewById(R.id.layoutPaymentInfo);
        layoutCashPayment = (LinearLayout) findViewById(R.id.layoutCashPayment);
        layoutOnlinePayment = (LinearLayout) findViewById(R.id.layoutOnlinePayment);
        layoutReview = (LinearLayout) findViewById(R.id.layoutReview);
        layoutLocation = (LinearLayout) findViewById(R.id.layoutLocation);

        tvExpandArrowOpeningHour.setText("\uf107");
        tvExpandArrowOpeningHour.setTypeface(face);
        tvExpandArrowpaymth.setText("\uf107");
        tvExpandArrowpaymth.setTypeface(face);
        tvExpandArrowreview.setText("\uf107");
        tvExpandArrowreview.setTypeface(face);
        tvExpandArrowlocation.setText("\uf107");
        tvExpandArrowlocation.setTypeface(face);

        cvRestaurantOpeningHour.setOnClickListener(openinghrClick);
        cvRestaurantpaymethods.setOnClickListener(paymthClick);
        cvRestaurantreview.setOnClickListener(reviewClick);
        cvRestaurantlocation.setOnClickListener(locationClick);
        tvSeeMoreReview.setOnClickListener(seemorereviewClick);
        addAllDataofopeninghr();
        //addAlldataOftreview();
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(restdetail.getRestaurant().getRestaurantImage(), ivRestaurantInfoImage);
        tvivRestaurantInfoName.setText(restdetail.getRestaurant().getRestaurantName());
        rbCatRestaurantInfoFavoriteRating.setRating(restdetail.getRestaurant().getFavouriteRate());
        tvCatRestaurantInfoAddress.setText(restdetail.getRestaurant().getAddress());
        tvOPenNow.setText(" - " + openornot);
        String cuisine = null;
        for (int j = 0; j < restdetail.getCuisines().size(); j++) {
            if (cuisine != null) {
                cuisine = cuisine + "," + restdetail.getCuisines().get(j).getCuisineName();
            } else {
                cuisine = restdetail.getCuisines().get(j).getCuisineName();
            }
        }
        ivRestaurantInfoCuisineName.setText(cuisine);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    OnClickListener seemorereviewClick = new OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    @SuppressLint("SimpleDateFormat")
    public void addAllDataofopeninghr() {
        for (int i = 0; i < restdetail.getWorkingHours().size(); i++) {
            TableRow tbrow0 = new TableRow(RestInformationActivity.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tbrow0.setLayoutParams(lp);
            Date d = new Date();
            SimpleDateFormat current = new SimpleDateFormat("hh:mm a");
            TextView tv0 = new TextView(RestInformationActivity.this);

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date startDate = format.parse(restdetail.getWorkingHours().get(i).getOpenTime());
                Date endDate = format.parse(restdetail.getWorkingHours().get(i).getCloseTime());
                SimpleDateFormat stdtFormat = new SimpleDateFormat("hh:mm a");
                SimpleDateFormat enddtFormat = new SimpleDateFormat("hh:mm a");

                tv0.setText(restdetail.getWorkingHours().get(i).getWorkingDay() + " " + stdtFormat.format(startDate).toString() + " - " + enddtFormat.format(endDate).toString());
                tv0.setTextSize(14);
                tv0.setTextColor(Color.parseColor("#77000000"));
                tv0.setPadding(90, 5, 5, 5);
                tbrow0.addView(tv0);
                layoutOpeningHr.addView(tbrow0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    OnClickListener openinghrClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (flagoh == 0) {
                tvExpandArrowOpeningHour.setText("\uf106");
                tvExpandArrowOpeningHour.setTypeface(face);
                layoutOpeningHr.setVisibility(View.VISIBLE);
                ivopeninghr.setImageResource(R.drawable.infoopeninghrpink);
                tvOeningHourText.setTextColor(getResources().getColor(R.color.toolBarColor));

                flagoh = 1;
            } else {
                tvExpandArrowOpeningHour.setText("\uf107");
                tvExpandArrowOpeningHour.setTypeface(face);
                layoutOpeningHr.setVisibility(View.GONE);
                ivopeninghr.setImageResource(R.drawable.infoopeninghr);
                tvOeningHourText.setTextColor(Color.parseColor("#99000000"));
                flagoh = 0;
            }

        }
    };
    OnClickListener paymthClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (flagpm == 0) {
                tvExpandArrowpaymth.setText("\uf106");
                tvExpandArrowpaymth.setTypeface(face);
                layoutPaymentInfo.setVisibility(View.VISIBLE);
                ivinfopayment.setImageResource(R.drawable.infopaymentpink);
                tvPaymentMethodsText.setTextColor(getResources().getColor(R.color.toolBarColor));
                if (!restdetail.getRestaurant().getOnlinePaymentAvailable()) {
                    layoutOnlinePayment.setVisibility(View.GONE);
                }
                flagpm = 1;
            } else {
                tvExpandArrowpaymth.setText("\uf107");
                tvExpandArrowpaymth.setTypeface(face);
                layoutPaymentInfo.setVisibility(View.GONE);
                ivinfopayment.setImageResource(R.drawable.infopayment);
                tvPaymentMethodsText
                        .setTextColor(Color.parseColor("#99000000"));
                flagpm = 0;
            }
        }
    };

    OnClickListener reviewClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (flagrv == 0) {
                tvExpandArrowreview.setText("\uf106");
                tvExpandArrowreview.setTypeface(face);
                layoutReview.setVisibility(View.VISIBLE);
                ivreview.setImageResource(R.drawable.inforeviewpink);
                tvReviewsText.setTextColor(getResources().getColor(R.color.toolBarColor));
                flagrv = 1;
            } else {
                tvExpandArrowreview.setText("\uf107");
                tvExpandArrowreview.setTypeface(face);
                layoutReview.setVisibility(View.GONE);
                ivreview.setImageResource(R.drawable.inforeview);
                tvReviewsText.setTextColor(Color.parseColor("#99000000"));
                flagrv = 0;
            }
        }
    };

    OnClickListener locationClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (flagloc == 0) {
                tvExpandArrowlocation.setText("\uf106");
                tvExpandArrowlocation.setTypeface(face);
                layoutLocation.setVisibility(View.VISIBLE);
                ivlocation.setImageResource(R.drawable.infolocationpink);
                tvLocationText.setTextColor(getResources().getColor(
                        R.color.toolBarColor));
                flagloc = 1;
            } else {
                tvExpandArrowlocation.setText("\uf107");
                tvExpandArrowlocation.setTypeface(face);
                layoutLocation.setVisibility(View.GONE);
                ivlocation.setImageResource(R.drawable.infolocation);
                tvLocationText.setTextColor(Color.parseColor("#99000000"));
                flagloc = 0;
            }
        }
    };

    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Info");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.rest_inforamtion, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        return super.onOptionsItemSelected(item);
    }

}
