package com.post.wall.wallpostapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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

public class ForgotPasswordActivity extends AppCompatActivity {

	RelativeLayout layoutProgress;
	EditText edtEmailid;

	RestInterface restInterface;
//	GetUrlClass url;
//	RestAdapter adapter;

	private Toolbar mToolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}

		setContentView(R.layout.forgotpassword_view);


		Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
		toolBar.setTitle("Forgot Password");
		toolBar.setTitleTextColor(getResources().getColor(android.R.color.black));

		setSupportActionBar(toolBar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

		Utility.setWhiteToolBar(toolBar, this, getResources().getColor(android.R.color.black));


		restInterface = Utility.getRetrofitInterface();
//		url = new GetUrlClass();
//		adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//		restInterface = adapter.create(RestInterface.class);

		layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);
		edtEmailid = (EditText) findViewById(R.id.edtEmailid);

		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.forgot_password_menu, menu);
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

	public void validateData() {
		boolean cancel = false;
		View focusView = null;


		edtEmailid.setError(null);
		if (TextUtils.isEmpty(edtEmailid.getText().toString())) {
			edtEmailid.setError(getString(R.string.error_field_required));
			focusView = edtEmailid;
			cancel = true;
		} else if (!Utility.isEmailValid(edtEmailid.getText().toString())) {
			edtEmailid.setError(getString(R.string.error_invalid_email));
			focusView = edtEmailid;
			cancel = true;
		}


		Log.v("TTT", focusView + "..........focusView....Cancel.........." + cancel);
		if (cancel) {
			if (focusView != null)
				focusView.requestFocus();
		} else {
			changePassword(edtEmailid.getText().toString());
		}
	}

	public void changePassword(String emailID) {
		showProgress(true);
		restInterface.forgotPassword(emailID, new Callback<PostLikeModel>() {
			@Override
			public void failure(RetrofitError error) {
				showProgress(false);
				String message = error.getMessage();
				try {
					boolean IsInternetConnected = checkInternetisAvailable.getInstance(ForgotPasswordActivity.this).check_internet();

					if (IsInternetConnected) {
						String msg = "";
						if (message != null) {
							msg = message;
						} else {
							msg = "Server Problem.";
						}

						if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
							Utility.showAlert(ForgotPasswordActivity.this, "Error: " + msg);
						}
					} else {
						Utility.showAlert(ForgotPasswordActivity.this, Constants.NO_INTERNET_MESSAGE);
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

					Intent intent = new Intent(getApplicationContext(), ForgotPasswordSuccessActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					finish();
				}else {
					Toast.makeText(ForgotPasswordActivity.this, postLikeModel.getMsg(), Toast.LENGTH_SHORT).show();
				}

			}
		});

	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
		finish();
		super.onBackPressed();
	}
}
