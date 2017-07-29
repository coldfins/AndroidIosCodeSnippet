package com.post.wall.wallpostapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.post.wall.wallpostapp.model.AlbumImages;
import com.post.wall.wallpostapp.model.AlbumsModel;
import com.post.wall.wallpostapp.model.Post;
import com.post.wall.wallpostapp.model.PostVideoImagesModel;
import com.post.wall.wallpostapp.model.SharePostModel;
import com.post.wall.wallpostapp.utility.CircleImageView;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.FlowLayout;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.ImageGridAdapter;
import com.post.wall.wallpostapp.utility.MyGridView;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;
import com.post.wall.wallpostapp.utility.slidinguplayout.SlidingUpPanelLayout;

import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class ShareActivity extends AppCompatActivity {

	RestInterface restInterface;
//	GetUrlClass url;
//	RestAdapter adapter;

	Post post;
	int position;
	LinearLayout layoutSharePostRow;
	CircleImageView imgDashboardUser, imgShareUser;
	TextView txtUserName, txtShareUserName, txtSharePostText, txtShareTime;
	DisplayImageOptions doption1;
	EditText edtComment;
	RelativeLayout layoutProgress;
	LinearLayout SharelayoutImages;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_view);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		try {
			doption1 = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.img_profile)
					.showImageForEmptyUri(R.drawable.img_profile)
					.showImageOnFail(R.drawable.img_profile)
					.cacheInMemory(false)
					.cacheOnDisc(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.considerExifParams(true)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
		}

		restInterface = Utility.getRetrofitInterface();
//		url = new GetUrlClass();
//		adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//		restInterface = adapter.create(RestInterface.class);

		post = new Gson().fromJson(getIntent().getExtras().get("PostModel").toString(), Post.class);
		position = getIntent().getExtras().getInt("position");

		layoutSharePostRow = (LinearLayout) findViewById(R.id.layoutSharePostRow);
		layoutSharePostRow.setVisibility(View.VISIBLE);

		imgDashboardUser = (CircleImageView) findViewById(R.id.imgDashboardUser);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(HomeActivity.user.getUser().getProfilePic(), imgDashboardUser, doption1);
		txtUserName = (TextView) findViewById(R.id.txtUserName);
		txtUserName.setText(HomeActivity.user.getUser().getFirstName() + " " + HomeActivity.user.getUser().getLastName());
		edtComment = (EditText) findViewById(R.id.edtComment);
		layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);
		SharelayoutImages = (LinearLayout) findViewById(R.id.SharelayoutImages);
		SharelayoutImages.setVisibility(View.GONE);

		imgShareUser = (CircleImageView) findViewById(R.id.imgShareUser);
		imageLoader.displayImage(post.getUser().getProfilePic(), imgShareUser, doption1);
		txtShareUserName = (TextView) findViewById(R.id.txtShareUserName);
		txtShareUserName.setText(post.getUser().getFirstName() + " " + post.getUser().getLastName());
		txtShareTime = (TextView) findViewById(R.id.txtShareTime);
		txtShareTime.setText(Utility.getLeftDayTime(post.getPost().getPostedDate()));
		txtSharePostText = (TextView) findViewById(R.id.txtSharePostText);
		txtSharePostText.setText(post.getPost().getPostText());


	}

	public void sharePost(int postId){
		restInterface.sharePost(postId, edtComment.getText().toString(), HomeActivity.user.getUser().getUserId(), new Callback<Post>() {
			@Override
			public void failure(RetrofitError error) {
//                showProgress(false);
				showProgress(false);
				String message = error.getMessage();
				try {
					boolean IsInternetConnected = checkInternetisAvailable.getInstance(ShareActivity.this).check_internet();

					Log.v("TTT", "SET ADAPTER");

					if (IsInternetConnected) {
						String msg = "";
						if (message != null) {
							msg = message;
						} else {
							msg = "Server Problem.";
						}

						if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
							Utility.showAlert(ShareActivity.this, "Error: " + msg);
						}

					} else {
						Utility.showAlert(ShareActivity.this, Constants.NO_INTERNET_MESSAGE);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}


			}

			@Override
			public void success(Post postModel, Response arg1) {
				int code = postModel.getErrorCode();
				showProgress(false);
				if (code == 0) {
					Intent intent = new Intent();
					intent.putExtra("PostModel", new Gson().toJson(postModel));
					intent.putExtra("Position", position);
					setResult(Constants.SHARE_POST, intent);
					finish();
					overridePendingTransition(R.anim.scale_to_corner, R.anim.scale_top_left_to_bottom_right);

				} else {
					Toast.makeText(ShareActivity.this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
				}
//
//                swipyRefreshLayoutChatConversion.setRefreshing(false);
			}
		});

	}
	@Override
	public void onBackPressed() {
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
		finish();
		super.onBackPressed();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.share_post, menu);
//        menuAddItem = menu.findItem(R.id.action_add_contact);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}else if(item.getItemId() == R.id.action_post){
			showProgress(true);
			sharePost(post.getPost().getPostId());
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




}