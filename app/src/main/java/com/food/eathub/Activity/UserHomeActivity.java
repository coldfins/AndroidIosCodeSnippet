package com.food.eathub.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.food.eathub.Adapter.MyAdapter;
import com.food.eathub.Fragment.RestaurantsFragment;
import com.food.eathub.Model.AddToBasketModel;
import com.food.eathub.Model.GetRestaurantAndCuisineModel;
import com.food.eathub.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressLint("DefaultLocale")
public class UserHomeActivity extends ActionBarActivity implements
        RecyclerView.OnItemTouchListener, OnQueryTextListener {

    public static String TITLES[] = {"Restaurants"};
    int ICONS[] = {R.drawable.sidepanelrest};
    int ICONSPINK[] = {R.drawable.sidepanelrestpink};
    int positionoffragment;
    String NAME = "you";
    String EMAIL = "user@gmail.com";
    String PROFILE;
    public static Menu menu;
    public static Toolbar toolbar;
    public static RecyclerView mRecyclerView;
    @SuppressWarnings("rawtypes")
    public static RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    public static Double lati, longi;
    ArrayList<GetRestaurantAndCuisineModel> rm;
    SharedPreferences spLocation;
    RestaurantsFragment rests;
    SharedPreferences spbasket;
    public static HashMap<String, ArrayList<?>> hashval;
    public static int oldpos;
    Gson gson;
    int ttlqty;
    Boolean val = false;
    HttpURLConnection httpUrlConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);

        initcontrol();
        hashval = new HashMap<String, ArrayList<?>>();
        Resources res = getResources();
        int id = R.drawable.reguserimage;
        Bitmap bb = BitmapFactory.decodeResource(res, id);
        PROFILE = encode((getBytes(bb)));
        Log.d("TTT", "photooo: " + PROFILE);
    }

    public Boolean CheckUrl(final String url) {

        UserHomeActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    httpUrlConn = (HttpURLConnection) new URL(url).openConnection();
                    HttpURLConnection.setFollowRedirects(false);
                    httpUrlConn.setRequestMethod("HEAD");
                    httpUrlConn.connect();

                    if (httpUrlConn.getResponseMessage().equals("Found")) {
                        val = true;
                    } else {
                        val = (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK);
                    }
                    httpUrlConn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    val = false;
                }
            }
        });

        return val;

    }

    public Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public String encode(byte[] b)
    {
        String img = Base64.encodeToString(b, 0);
        return img;

    }

    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Fetch basket data
     */
    public void getbasketData() {
        String jsonbaskets = spbasket.getString("basketData", null);
        java.lang.reflect.Type type = new TypeToken<List<AddToBasketModel>>() {
        }.getType();

        try {
            ArrayList<AddToBasketModel> bsktList = gson.fromJson(jsonbaskets,
                    type);
            if (!bsktList.isEmpty()) {

                ttlqty = ttlqty + bsktList.size();

            }
            MyAdapter.basketitem = ttlqty;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void newmethodtoStartactivity() {
        MyAdapter.selected_item = 1;
        mRecyclerView.getAdapter().notifyDataSetChanged();
        rests = new RestaurantsFragment();
        Bundle b = new Bundle();
        b.putDouble("lati", lati);
        b.putDouble("longi", longi);
        rests.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, rests).commit();
    }

    /**
     * Initialize all UI components
     */
    public void initcontrol() {
        gson = new Gson();
        spbasket = getSharedPreferences("basketData", MODE_PRIVATE);
        getbasketData();
        rests = new RestaurantsFragment();
        spLocation = getSharedPreferences("splatlong", MODE_PRIVATE);
        try {
            Intent i = getIntent();
            int cLocation = i.getIntExtra("flagCL", 0);
            if (cLocation == 1) {
                lati = i.getDoubleExtra("latVal", 0);
                longi = i.getDoubleExtra("longVal", 0);
            } else if (cLocation == 2) {
                lati = i.getDoubleExtra("latVal", 0);
                longi = i.getDoubleExtra("longVal", 0);
            }

            Editor ed = spLocation.edit();
            ed.putFloat("lati", Float.parseFloat(lati.toString()));
            ed.putFloat("longi", Float.parseFloat(longi.toString()));
            ed.commit();

        } catch (Exception e) {
            lati = (double) spLocation.getFloat("lati", 0);
            longi = (double) spLocation.getFloat("longi", 0);
        }
        setupToolbar();
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(TITLES, ICONS, NAME, EMAIL, ICONSPINK, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        float width = (float) (getResources().getDisplayMetrics().widthPixels / 1.2);
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mRecyclerView.getLayoutParams();
        params.width = (int) width;
        mRecyclerView.setLayoutParams(params);

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

        };
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the
        mDrawerToggle.syncState(); // Finally we set the drawer toggle sync

        final GestureDetector mGestureDetector = new GestureDetector(UserHomeActivity.this,
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                            oldpos = MyAdapter.selected_item;
                            int position = recyclerView.getChildPosition(child);
                            positionoffragment = position;
                            recyclerView.getAdapter().notifyDataSetChanged();

                            if (position != 0) {
                                MyAdapter.selected_item = position;
                            }
                            if (position == 1) {
                                Drawer.closeDrawers();
                                try {
                                    Bundle b = new Bundle();
                                    rests.setArguments(b);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, rests).commit();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                }); // State
        try {
            newmethodtoStartactivity();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        if (positionoffragment != 2) {
            try {
                MyAdapter.selected_item = 2;
                Bundle bb = new Bundle();
                bb.putDouble("lati", lati);
                bb.putDouble("longi", longi);
                rests.setArguments(bb);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, rests).commit();
                mRecyclerView.getAdapter().notifyDataSetChanged();
            } catch (Exception e) {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Info")
                        .setContentText("Are you sure you want to exit?")
                        .setConfirmText("Yes, exit!")
                        .setCancelText("Cancel")
                        .setCancelClickListener(
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(
                                            SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                        .setConfirmClickListener(
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        UserHomeActivity.this.finish();
                                        System.exit(0);
                                        android.os.Process.killProcess(android.os.Process.myPid());

                                    }
                                }).show();
            }

        } else {
            if (positionoffragment == 2) {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Info")
                        .setContentText("Are you sure you want to exit?")
                        .setConfirmText("Yes, exit!")
                        .setCancelText("Cancel")
                        .setCancelClickListener(
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(
                                            SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                        .setConfirmClickListener(
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        UserHomeActivity.this.finish();
                                        System.exit(0);
                                        android.os.Process.killProcess(android.os.Process.myPid());

                                    }
                                }).show();
            }
        }
    }

    public UserHomeActivity() {
    }

    public static interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;
    public UserHomeActivity(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;

        mGestureDetector = new GestureDetector(context,
        new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (childView != null && mListener != null) {
                    mListener.onItemLongClick(childView, recyclerView.getChildPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public boolean onQueryTextChange(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        @SuppressWarnings("unused")
        ImageView closebutton;

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;

    }

    @SuppressLint("DefaultLocale")
    private ArrayList<GetRestaurantAndCuisineModel> filter(ArrayList<GetRestaurantAndCuisineModel> models, String query) {
        String text = null, textRate = null;
        query = query.toLowerCase();

        ArrayList<GetRestaurantAndCuisineModel> filteredModelList = new ArrayList<GetRestaurantAndCuisineModel>();

        if (query != "" && !query.equals("")) {
            for (GetRestaurantAndCuisineModel model : models) {
                textRate = model.getRestaurant().getRestaurantName().toLowerCase();

                if (textRate.contains(query)) {
                    filteredModelList.add(model);
                } else {
                    text = model.getRestaurant().getArea().toLowerCase();
                    if (text.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            }
        } else {
            filteredModelList = rm;
        }
        return filteredModelList;
    }

}
