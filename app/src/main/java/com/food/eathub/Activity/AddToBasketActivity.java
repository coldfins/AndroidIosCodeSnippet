package com.food.eathub.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.food.eathub.Adapter.MyAdapter;
import com.food.eathub.Adapter.RestBasketAdapter;
import com.food.eathub.Interface.RestInterface;
import com.food.eathub.Model.AddToBasketModel;
import com.food.eathub.Model.AddToBasketRestDishesModel;
import com.food.eathub.Model.AllDishbycategoryModel;
import com.food.eathub.Model.BasketModel;
import com.food.eathub.Model.GetAllRestaurantsModel;
import com.food.eathub.Model.GetUrlofApi;
import com.food.eathub.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddToBasketActivity extends ActionBarActivity {

    Toolbar toolbar;
    RecyclerView mRecyclerView;
    @SuppressWarnings("rawtypes")
    RecyclerView.Adapter mAdapter;
    com.food.eathub.Utils.LinearLayoutManager mLayoutManager;
    ArrayList<String> s = new ArrayList<String>();
    public TextView tvTotalAmountwithTax, tvTotalAmountRupeeIcon3;
    Typeface font;
    ArrayList<BasketModel> basketdetails;
    BasketModel basket;
    int restid;
    float servicetax;
    String catname;
    int deliveryfee;
    Button btnAddToCart;
    SharedPreferences spbasket;
    AllDishbycategoryModel dishes;
    ArrayList<AddToBasketModel> baskets = new ArrayList<AddToBasketModel>();
    AddToBasketModel basketdetail = new AddToBasketModel();
    ArrayList<AddToBasketModel> arr;
    int flag = 0;
    int ttlqty = 0;
    Editor ed;
    Gson gson;
    int qty;
    ArrayList<AddToBasketRestDishesModel> dishesarray = new ArrayList<AddToBasketRestDishesModel>();
    RestAdapter adapter;
    GetUrlofApi url;
    RestInterface inface;
    GetAllRestaurantsModel restModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_to_basket);
        Intent i = getIntent();
        dishes = (AllDishbycategoryModel) i.getSerializableExtra("dishes");
        catname = i.getStringExtra("catname");
        restid = i.getIntExtra("restid", 0);

        servicetax = RestaurantCategoryActivity.servicetax;
        deliveryfee = RestaurantCategoryActivity.deliveryfee;

        basketdetails = new ArrayList<BasketModel>();
        basket = new BasketModel();

        basket.setCatname(catname);
        basket.setServicetax(servicetax);
        basket.setDeliveryfee(deliveryfee);
        basket.setDishes(dishes);
        basketdetails.add(basket);
        setupToolbar();
        initControl();

    }

    /**
     * Initialize all UI components
     */
    void initControl() {
        restModel = new GetAllRestaurantsModel();
        url = new GetUrlofApi();
        adapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).setLogLevel(LogLevel.FULL).build();

        inface = adapter.create(RestInterface.class);
        try {
            getRestById();
        } catch (Exception e) {
            e.printStackTrace();
        }

        spbasket = getSharedPreferences("basketData", MODE_PRIVATE);
        arr = new ArrayList<AddToBasketModel>();
        ed = spbasket.edit();
        gson = new Gson();
        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(addToCartClick);

        font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        getRestById();
        tvTotalAmountRupeeIcon3 = (TextView) findViewById(R.id.tvTotalAmountRupeeIcon3);
        tvTotalAmountRupeeIcon3.setText("\uf156");
        tvTotalAmountRupeeIcon3.setTypeface(font);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvBasket);

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RestBasketAdapter(basketdetails, AddToBasketActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new com.food.eathub.Utils.LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    /**
     * Find restaurant by ID
     */
    public void getRestById() {
        inface.getRestaurantById(restid, new Callback<GetAllRestaurantsModel>() {
            @Override
            public void success(GetAllRestaurantsModel model, Response arg1) {
                try {
                    restModel = model;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError arg0) {

            }
        });
    }

    /**
     * handling click event of AddToCard button
     */
    OnClickListener addToCartClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String jsonbaskets = spbasket.getString("basketData", null);
            java.lang.reflect.Type type = new TypeToken<List<AddToBasketModel>>() {
            }.getType();
            arr = gson.fromJson(jsonbaskets, type);

            if (arr != null) {
                for (int i = 0; i < arr.size(); i++) {
                    if (arr.get(i).getRestid() == restid) {
                        for (int j = 0; j < arr.get(i).getDishes().size(); j++) {
                            if (arr.get(i).getDishes().get(j).getDish().getDishId().equals(dishes.getDishId())) {
                                int ttlqty = arr.get(i).getDishes().get(j).getQty();
                                int aaa = (ttlqty + qty);
                                flag = 1;
                                arr.get(i).getDishes().get(j).setQty(aaa);
                                break;
                            }
                        }
                        if (!arr.get(i).getDishes().contains(dishes) && flag == 0) {
                            AddToBasketRestDishesModel dish = new AddToBasketRestDishesModel();
                            dish.setCatname(catname);
                            dish.setQty(qty);
                            dish.setDish(dishes);
                            flag = 1;
                            arr.get(i).getDishes().add(dish);
                        }
                    }
                }

                baskets.addAll(arr);
                if (flag == 0) {
                    basketdetail.setRestid(restid);
                    basketdetail.setRestdetail(restModel);
                    AddToBasketRestDishesModel dish = new AddToBasketRestDishesModel();
                    dish.setCatname(catname);
                    dish.setQty(qty);
                    dish.setDish(dishes);
                    dishesarray.add(dish);

                    basketdetail.setDishes(dishesarray);
                    basketdetail.setServicetax(RestaurantCategoryActivity.servicetax);
                    basketdetail.setDeliveryfee(RestaurantCategoryActivity.deliveryfee);
                    baskets.add(basketdetail);

                }

                String jsondd = gson.toJson(baskets);
                ttlqty = baskets.size();
                MyAdapter.basketitem = ttlqty;
                UserHomeActivity.mAdapter.notifyDataSetChanged();
                ed.putString("basketData", jsondd);
                ed.commit();

                onBackPressed();
                overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);
            } else {

                basketdetail.setRestid(restid);
                basketdetail.setRestdetail(restModel);
                AddToBasketRestDishesModel dish = new AddToBasketRestDishesModel();
                dish.setCatname(catname);
                dish.setQty(qty);
                dish.setDish(dishes);
                dishesarray.add(dish);

                basketdetail.setDishes(dishesarray);
                basketdetail.setServicetax(RestaurantCategoryActivity.servicetax);
                basketdetail.setDeliveryfee(RestaurantCategoryActivity.deliveryfee);
                baskets.add(basketdetail);

                String jsondd = gson.toJson(baskets);

                ttlqty = baskets.size();
                MyAdapter.basketitem = ttlqty;
                UserHomeActivity.mAdapter.notifyDataSetChanged();
                ed.putString("basketData", jsondd);
                ed.commit();
                onBackPressed();
            }

        }
    };

    /**
     * Set total amount
     * @param vall Dish price
     * @param qty number of dish
     */
    public void settextintextview(String vall, int qty) {
        tvTotalAmountwithTax = (TextView) findViewById(R.id.tvTotalAmountwithTax);
        tvTotalAmountwithTax.setText(vall);
        this.qty = qty;
    }

    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(catname);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_to_basket, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.animation_leave, R.anim.animation_enter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        return super.onOptionsItemSelected(item);
    }

}
