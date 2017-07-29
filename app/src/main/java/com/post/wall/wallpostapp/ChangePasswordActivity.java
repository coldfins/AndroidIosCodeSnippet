package com.post.wall.wallpostapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.post.wall.wallpostapp.model.PostLikeModel;
import com.post.wall.wallpostapp.model.UserModel;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.PreferenceManager;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ved_pc on 3/15/2017.
 */

public class ChangePasswordActivity extends ActionBarActivity{
    RelativeLayout layoutProgress;

    RestInterface restInterface;
//    GetUrlClass url;
//    RestAdapter adapter;

    EditText edtOldPwd, edtNewPwd, edtNewConfirmPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        restInterface = Utility.getRetrofitInterface();
//        GetUrlClass url = new GetUrlClass();
//        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//        restInterface = adapter.create(RestInterface.class);

        layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);
        layoutProgress.setVisibility(View.GONE);

        edtOldPwd = (EditText) findViewById(R.id.edtOldPwd);
        edtNewPwd = (EditText) findViewById(R.id.edtNewPwd);
        edtNewConfirmPwd = (EditText) findViewById(R.id.edtNewConfirmPwd);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_password, menu);
//        menuAddItem = menu.findItem(R.id.action_add_contact);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }else if(item.getItemId() == R.id.action_change_password){
            validateData();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if(show){
            layoutProgress.setVisibility(View.VISIBLE);
        }else{
            layoutProgress.setVisibility(View.GONE);
        }
    }

    public void validateData(){
        boolean cancel = false;
        View focusView = null;


        edtOldPwd.setError(null);
        edtNewPwd.setError(null);
        edtNewConfirmPwd.setError(null);

        if (TextUtils.isEmpty(edtOldPwd.getText().toString()) && !isPasswordValid(edtOldPwd.getText().toString())) {
            edtOldPwd.setError(getString(R.string.error_invalid_old_password));
            focusView = edtOldPwd;
            cancel = true;
        }else if (TextUtils.isEmpty(edtNewPwd.getText().toString()) && !isPasswordValid(edtNewPwd.getText().toString())) {
            edtNewPwd.setError(getString(R.string.error_invalid_new_password));
            focusView = edtNewPwd;
            cancel = true;
        }else if (TextUtils.isEmpty(edtNewConfirmPwd.getText().toString()) && !isPasswordValid(edtNewConfirmPwd.getText().toString())) {
            edtNewConfirmPwd.setError(getString(R.string.error_invalid_confirm_password));
            focusView = edtNewConfirmPwd;
            cancel = true;
        }else if(!edtNewPwd.getText().toString().equals(edtNewConfirmPwd.getText().toString())){
            edtNewConfirmPwd.setError(getString(R.string.error_notmatch_confirm_password));
            focusView = edtNewConfirmPwd;
            cancel = true;
        }else if(!edtOldPwd.getText().toString().equals(HomeActivity.user.getUser().getPassword())){
            Log.v("TTT", edtOldPwd.getText().toString() + "............111............" + HomeActivity.user.getUser().getPassword());
            edtOldPwd.setError(getString(R.string.error_notmatch_old_password));
            focusView = edtOldPwd;
            cancel = true;
        }

        Log.v("TTT", focusView + "..........focusView....Cancel.........." + cancel);
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if(focusView != null)
                focusView.requestFocus();
        } else {
            changePassword(edtOldPwd.getText().toString(), edtNewPwd.getText().toString());
        }

    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void changePassword(String OldPassword, String NewPassword) {
        showProgress(true);
        restInterface.ChangePassword(HomeActivity.user.getUser().getUserId(), OldPassword, NewPassword, new Callback<PostLikeModel>() {
            @Override
            public void failure(RetrofitError error) {
                showProgress(false);
                String message = error.getMessage();
                try {
                    boolean IsInternetConnected = checkInternetisAvailable.getInstance(ChangePasswordActivity.this).check_internet();

                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Utility.showAlert(ChangePasswordActivity.this, "Error: " + msg);
                        }
                    } else {
                        Utility.showAlert(ChangePasswordActivity.this, Constants.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }

            @Override
            public void success(PostLikeModel postLikeModel, Response arg1) {
                int code = postLikeModel.getError_code();
                showProgress(false);

                if (code == 0) {
                    HomeActivity.user.getUser().setPassword(edtNewPwd.getText().toString());
                    String userdata = new Gson().toJson(HomeActivity.user);
                    PreferenceManager.putLoggedUser(userdata, ChangePasswordActivity.this);

                    String json = PreferenceManager.getLoggedUser(getApplicationContext());
                    HomeActivity.user = new Gson().fromJson(json, UserModel.class);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    finish();
                }else {
                    Toast.makeText(ChangePasswordActivity.this, postLikeModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
        super.onBackPressed();
    }



}
