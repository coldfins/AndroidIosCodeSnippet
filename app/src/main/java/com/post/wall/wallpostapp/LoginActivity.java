package com.post.wall.wallpostapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.post.wall.wallpostapp.model.UserModel;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.PreferenceManager;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements OnClickListener {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
//    private View mProgressView;
    RelativeLayout layoutProgress;
    TextView txtForgotPassword;

    RestInterface restInterface;
    GetUrlClass url;
    RestAdapter adapter;
    TextView doSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.v("TTT", "LoginActivity STARTED");

        url = new GetUrlClass();
        adapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
        restInterface = adapter.create(RestInterface.class);


        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
//        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("TTT", "LoginActivity CLICK");
                attemptLogin();
            }
        });

        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        txtForgotPassword.setOnClickListener(this);

        layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);

        doSignUp = (TextView) findViewById(R.id.doSignUp);
        doSignUp.setOnClickListener(this);

        mEmailView.requestFocus();
        mEmailView.setFocusable(true);
        mEmailView.setFocusableInTouchMode(true);

        Log.v("TTT", "LoginActivity intialize");
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!Utility.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            restInterface.Login(email, password, new Callback<UserModel>() {

                        @Override
                        public void failure(RetrofitError error) {
                            showProgress(false);
                            String message = error.getMessage();
                            try {
                                boolean IsInternetConnected = checkInternetisAvailable.getInstance(LoginActivity.this).check_internet();
                                if (IsInternetConnected) {
                                    String msg = "";
                                    if (message != null) {
                                        msg = message;
                                    } else {
                                        msg = "Server Problem.";
                                    }

                                    if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                                        Utility.showAlert(LoginActivity.this, "Error: " + msg);
                                    }
                                } else {
                                    Utility.showAlert(LoginActivity.this, Constants.NO_INTERNET_MESSAGE);
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }

                        @Override
                        public void success(UserModel model, Response arg1) {
                            int code = model.getError_code();
                            Log.v("TTT", "User..." + new Gson().toJson(model));

                            if (code == 0) {
                                showProgress(false);
                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                Gson gson = new Gson();
                                String userdata = gson.toJson(model);
                                PreferenceManager.putLoggedUser(userdata, LoginActivity.this);
                                PreferenceManager.putLogin(true, LoginActivity.this);
                                try {
                                    Log.e("data", userdata);
                                } catch (Exception e) {
                                    Log.e("error", e.toString());
                                }
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                finish();
                            } else {
                                showProgress(false);
                                Toast.makeText(getApplicationContext(), model.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            layoutProgress.setVisibility(show ? View.VISIBLE : View.GONE);
//            layoutProgress.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    layoutProgress.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            layoutProgress.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doSignUp:
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;

            case R.id.txtForgotPassword:
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

}

