package com.dating.coolmatch;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import Model.Chat.GetAllConveration;
import fragment.Home.CommunicationFragment;
import fragment.Home.QuickMatchFragment;
import interfaces.RestInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.CheckInternetisAvailable;
import utils.GetUrl;
import utils.Util;

public class HomeActivity extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolBar;
    private QuickMatchFragment quickMatchesFragment;
    private CommunicationFragment communicationFragment;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private View navHeader;
    public static GetUrl url = new GetUrl();
    private ProgressDialog mProgressDialog;
    public TextView txtEmail;
    public  ImageView imgProfile;
    private String profile = "",userName="";
    public static String fDate;
    public static  int counter=0;
    private int offset=0,limit=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.content_home);
        setView();
    }

    private void setView() {
        Date cDate = new Date();
        fDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(cDate);

        //Toolbar
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        toolBar.setTitle("Home");
        setSupportActionBar(toolBar);

        //all fragmnet object
        quickMatchesFragment = new QuickMatchFragment();
        communicationFragment = new CommunicationFragment();

        //drawer item
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigation view
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtEmail = (TextView) navHeader.findViewById(R.id.tvUserEmailId);
        imgProfile = (ImageView) navHeader.findViewById(R.id.ivUserPic);
        //load navigation header
        loadNavHeader();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,
                        quickMatchesFragment).commit();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadNavHeader() {

        if(userName.isEmpty() || userName.equals(""))
        {
            txtEmail.setText("Rose@gmail.com");
        }
        else
        {
            txtEmail.setText(userName);
        }

        if(!profile.isEmpty() || !profile.equals(""))
        {
            Log.d("TTT",profile+" ///");
            Picasso.with(getApplicationContext()).load(profile)
                    .placeholder(R.mipmap.male_dafault)
                    .into(imgProfile);

        }

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
            alertbox.setTitle(Html
                    .fromHtml("<font color='#db2c57'> Exit </font>"));
            alertbox.setMessage("Do you really want to exit ?");
            alertbox.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                LoginManager.getInstance().logOut();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            finish();
                            System.exit(0);
                            android.os.Process.killProcess(android.os.Process.myPid());

                        }
                    });
            alertbox.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
            AlertDialog alert = alertbox.create();
            alert.show();
            Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(getResources().getColor(R.color.colorPrimary));
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.quickMatch) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame,
                            quickMatchesFragment).commit();

        }
        else if (id == R.id.communication) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame,
                            communicationFragment).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123)
        {
                offset=0;
                getConversation(limit,offset);
        }
    }
    private void getConversation(int limit,int offsett)
    {
        mProgressDialog = ProgressDialog.show(HomeActivity.this, "", "", true);
        mProgressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setContentView(R.layout.dialog);
        ProgressBar progressBar = (ProgressBar) mProgressDialog
                .findViewById(R.id.progressWheel);
        ThreeBounce circle = new ThreeBounce();
        circle.setColor(getResources().getColor(R.color.white));
        progressBar.setIndeterminateDrawable(circle);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url.getUrl()).setLogLevel(RestAdapter.LogLevel.FULL).build();

        RestInterface restInterface = adapter
                .create(RestInterface.class);
        restInterface.getAllConversation(66, limit, offsett, new Callback<GetAllConveration>() {
            @Override
            public void success(GetAllConveration getMatchesModel, Response response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (getMatchesModel.getErrorCode() == 0) {
                        CommunicationFragment.allMsgList.clear();
                        CommunicationFragment.allMsgList.addAll(getMatchesModel.getMessages());
                        if (CommunicationFragment.allMsgList.size() > 0) {
                            offset = CommunicationFragment.allMsgList.size();
                            Log.d("TTT", CommunicationFragment.allMsgList.size() + " --- " + offset);
                            if(CommunicationFragment.chatAdapter!=null) {
                                Log.d("TTT","Notify dataaa");
                                CommunicationFragment.chatAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                else
                {
                    Util.showAlert(HomeActivity.this,getMatchesModel.getMsg());
                }

                }

            @Override
            public void failure(RetrofitError error) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                try {
                    boolean IsInternetConnected = CheckInternetisAvailable.getInstance(getApplicationContext()).check_internet();
                    String message = error.getMessage();
                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Util.showAlert(HomeActivity.this, "Error: " + msg);
                        }
                    } else {
                        Util.showAlert(HomeActivity.this, Util.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

}
