package com.post.wall.wallpostapp;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

public class GalleryPostScrollActivity extends AppCompatActivity implements SlidingUpPanelLayout.ScrollViewTouchEventListener, SlidingUpPanelLayout.OnScrollChangedListener, SlidingUpPanelLayout.OnScrollStoppedListener {

	private SlidingUpPanelLayout mLayout;

	RelativeLayout layoutHeader;
	LinearLayout layoutMiddle, layoutSubMiddle;
	MyGridView recyclerGalleryView;

	boolean isGalleryVisible = false, needToHideGallery = false, needToShowGallery = false;//, isKeyboardOpen = true;

	public FlowLayout myGallery;
	LinearLayout layoutBottom;
	ImageView imgAddPhoto;

	private List<FileBody>  encodedStringOfPhoto;// = new ArrayList<>();
	List<PostVideoImagesModel> postVideoImagesModels;// = new ArrayList<>();
//	public	List<String>  imagePathList;// = new ArrayList<>();
	public HashMap<String, Boolean> imagePathListMap;
	String comment;//, linkUrl;

	TextView txtUserName, txtDisplayStatus;//, txtDescription  txtTitle,
//	RelativeLayout layoutUrlImage, layoutUrlMain;//, MainLayout;
	ImageView imgPost;//, imgUrlDelete;
	CircleImageView imgDashboardUser, imgDashboardUser1;
	EditText edtComment;
	public ImageGridAdapter imageRecyclerAdapter;
	boolean isChangePostIcon = true, isPostStatus = false, addLink;
	int mPhotoSize, mPhotoSpacing;

	public ArrayList<AlbumImages> selectedList;// = new ArrayList<>();
	ArrayList<AlbumImages> listOfAllImages;// = new ArrayList<>();

	DisplayImageOptions doption1 = null;
	DisplayImageOptions doption = null;

	protected boolean pauseOnScroll = false;
	protected boolean pauseOnFling = false;

//	private View mProgressView;
	RelativeLayout layoutProgress;
	RelativeLayout add_post_form;

	RestInterface restInterface;
//	GetUrlClass url;
//	RestAdapter adapter;

	public static boolean isVideoPicked = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_post_scroll_view);


		selectedList = new ArrayList<>();
		listOfAllImages = new ArrayList<>();
		encodedStringOfPhoto = new ArrayList<>();
		postVideoImagesModels = new ArrayList<>();
		imagePathListMap = new HashMap<>();
		isVideoPicked = false;


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		restInterface = Utility.getRetrofitInterface();
//		url = new GetUrlClass();
//		adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//		restInterface = adapter.create(RestInterface.class);

		isPostStatus = getIntent().getBooleanExtra("poststatus", false);

//		mProgressView = findViewById(R.id.addpost_progress);
		layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);
		add_post_form = (RelativeLayout) findViewById(R.id.add_post_form);

		Gson gson = new Gson();
		String json = getIntent().getStringExtra("list");
		AlbumsModel albumsModel = gson.fromJson(json, AlbumsModel.class);
		if(albumsModel != null) {
			selectedList.addAll(albumsModel.getAlbumsModels());
		}

		layoutHeader = (RelativeLayout) findViewById(R.id.layoutHeader);
		layoutHeader.post(new Runnable() {
			@Override
			public void run() {
				hideHeader();
			}
		});

		overridePendingTransition(R.anim.scale_bottom_right_to_top_left,R.anim.scale_to_corner);

		// get the photo size and spacing
		mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
		mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);

		mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
		mLayout.setScrollViewTouchListener(this);
		mLayout.setOnScrollChangedListener(this);
		mLayout.setOnScrollStoppedListener(this);
		mLayout.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					mLayout.startScrollerTask();
				}
				return false;
			}
		});

		mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
			@Override
			public void onPanelSlide(View panel, float slideOffset) {
//                Log.i(TAG, "onPanelSlide, offset " + slideOffset + "  needToShowGallery = " + needToShowGallery + "   " + mLayout.isSlideUp());
//                if(needToShowGallery && mLayout.isSlideUp()) {
//                	needToShowGallery = false;
//        			showHeader();
//                } else {
//                	needToShowGallery = true;
//                	hideHeader();
//                }
			}

			@Override
			public void onPanelExpanded(View panel) {
//                Log.i(TAG, "onPanelExpanded");

			}

			@Override
			public void onPanelCollapsed(View panel) {
//                Log.i(TAG, "onPanelCollapsed");

			}

			@Override
			public void onPanelAnchored(View panel) {
//                Log.i(TAG, "onPanelAnchored");
			}

			@Override
			public void onPanelHidden(View panel) {
//                Log.i(TAG, "onPanelHidden");
			}
		});

		mLayout.setAnchorPoint(1.0f);

		recyclerGalleryView = (MyGridView) findViewById(R.id.gridAlbums);

		initGalleryRecyclerView();
		galleryHide();

		layoutMiddle = (LinearLayout) findViewById(R.id.layoutMiddle);
		layoutMiddle.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				needToShowGallery = false;
				return false;
			}
		});

		imgAddPhoto = (ImageView) findViewById(R.id.imgAddPhoto);
		imgAddPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int permission = Utility.checkExternalPermission(GalleryPostScrollActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
				if (permission != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(GalleryPostScrollActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_REQUEST_CODE);
				}
				else {
					mLayout.setOverlayed(false);
					Utility.hideKeyboard(v, GalleryPostScrollActivity.this);
					imgAddPhoto.post(new Runnable() {
						@Override
						public void run() {
							if(isGalleryVisible)
								galleryHide();
							else
								galleryShow();
						}
					});
				}
			}
		});

		layoutBottom = (LinearLayout) findViewById(R.id.layoutBottom);
		layoutBottom.setVisibility(View.VISIBLE);

		txtUserName =(TextView) findViewById(R.id.txtUserName);
		imgDashboardUser = (CircleImageView) findViewById(R.id.imgDashboardUser);
		imgDashboardUser.setBorderWidth(0);
		imgDashboardUser1 = (CircleImageView) findViewById(R.id.imgDashboardUser1);
		imgDashboardUser1.setBorderWidth(0);
		edtComment = (EditText) findViewById(R.id.edtComment);
		myGallery = (FlowLayout) findViewById(R.id.mygallery);
		imgPost = (ImageView) findViewById(R.id.imgPost);
//		layoutUrlImage = (RelativeLayout) findViewById(R.id.layoutUrlImage);
//		txtTitle=(TextView) findViewById(R.id.txtTitle);
//		=txtDescription(TextView) findViewById(R.id.txtDescription);
//		imgUrlDelete=(ImageView) findViewById(R.id.imgUrlDelete);
//		layoutUrlMain = (RelativeLayout) findViewById(R.id.layoutUrlMain);
		txtDisplayStatus = (TextView) findViewById(R.id.txtDisplayStatus);

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

		try {
			doption = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.nophoto_album_thumb_normal)
					.showImageOnFail(R.drawable.nophoto_album_thumb_normal)
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

		if(HomeActivity.user != null) {
			txtUserName.setText(HomeActivity.user.getUser().getFirstName() + " " + HomeActivity.user.getUser().getLastName());

			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(HomeActivity.user.getUser().getProfilePic(), imgDashboardUser, doption1);
			imageLoader.displayImage(HomeActivity.user.getUser().getProfilePic(), imgDashboardUser1, doption1);
		}

//		edtComment.setText(linkUrl);
		edtComment.clearFocus();

		edtComment.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					mLayout.setFirstView(true);
				}
			}
		});

		edtComment.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				galleryHide();
				return false;
			}
		});

		edtComment.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				Log.v("TTT", "count = " + count + "  isChangePostIcon = " + isChangePostIcon + "  text = " + s.length());
//				Log.v("TTT", (layoutUrlMain.getVisibility() == View.GONE)
//						+ "..........."
//						+ (layoutUrlMain.getVisibility() == View.VISIBLE)
//						+ "..........." + (myGallery.getChildCount()));

				if(s.toString().trim().length() > 0 && isChangePostIcon) {
					isChangePostIcon = false;
					Utility.AnimationEnableSendPost(imgPost, getApplicationContext());
				}
				else if (s.toString().trim().length() == 0 && !isChangePostIcon) {// && (layoutUrlMain.getVisibility() == View.GONE) && (myGallery.getChildCount() == 0)
					isChangePostIcon = true;
					Utility.AnimationDisableSendPost(imgPost, getApplicationContext());
				}
				if(s.toString().trim().length() > 0)
					txtDisplayStatus.setText(s.toString());
				else
					txtDisplayStatus.setText("Write something...");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void afterTextChanged(Editable s) {}
		});

		imgPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if(edtComment.getText().toString().matches("")) {
					comment = "";
				}
				else {
					comment = edtComment.getText().toString().trim();
				}

				Log.v("TTT", edtComment.getText().toString().trim().length() + "..."+ (edtComment.toString().trim().length() != 0 ) + "........PostFeed.....IF.............." + (postVideoImagesModels != null && postVideoImagesModels.size() != 0));
				if(edtComment.getText().toString().trim().length() != 0 || (postVideoImagesModels != null && postVideoImagesModels.size() != 0)){
					Log.v("TTT", "PostFeed.....IF..............");
					showProgress(true);
					PostFeed(comment, postVideoImagesModels);
//				new AddPostAsyncTask(GalleryPostScrollActivity.this, comment, postVideoImagesModels).execute();
				}


			}
		});

//		layoutUrlMain.setVisibility(View.GONE);
//		layoutUrlMain.setTag("0," + false);

//		MainLayout = (RelativeLayout) findViewById(R.id.MainLayout);

		layoutSubMiddle = (LinearLayout) findViewById(R.id.layoutSubMiddle);
		layoutSubMiddle.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View v, MotionEvent event) {
				galleryHide();

				mLayout.post(new Runnable() {
					@Override
					public void run() {
//						Utility.showKeyboard(v, getApplicationContext());
//						if(!isKeyboardOpen) {
//							Utility.showKeyboard(v, getApplicationContext());
//							isKeyboardOpen = true;
//						}
//						else {
//							isKeyboardOpen = false;
//						}
					}
				});
				return true;
			}
		});

		layoutHeader.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				hideHeader();
//				Log.v("TTT", "***** header ontouch *****");
//				Utility.showKeyboard(v, getApplicationContext());
				galleryHide();
				return true;
			}
		});

		mLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				findViewById(R.id.sliding_layout).getParent().requestDisallowInterceptTouchEvent(false);
				return false;
			}
		});

		ScrollView sv= (ScrollView) findViewById(R.id.scroll);
		sv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				Log.v("TTT", "on scroll touch.......");
				int action = event.getAction();
				switch (action) {
					case MotionEvent.ACTION_DOWN:
//                	Log.i("TTT", "ACTION_DOWN.......");
						// Disallow ScrollView to intercept touch events.
						hideHeader();
						mLayout.setScrollingEnabled(false);
						break;
					case MotionEvent.ACTION_MOVE:
//                	Log.i("TTT", "ACTION_MOVE.......");
						// Disallow ScrollView to intercept touch events.
						hideHeader();
						mLayout.setScrollingEnabled(false);
						break;

					case MotionEvent.ACTION_UP:
//                	Log.i("TTT", "UP.......");
						// Allow ScrollView to intercept touch events.
						mLayout.setScrollingEnabled(true);
						break;
				}

				// Handle ListView touch events.
				v.onTouchEvent(event);
				return true;
			}
		});

		if(isPostStatus) {
//			edtComment.setFocusable(true);
//			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//			galleryHide();
		}
		else {
//			galleryShow();
		}

		if(addLink) {
//			showAddLinkDialog();
//    		galleryHide();
		}

		if(selectedList.size() != 0) {
			for (int i = 0; i < selectedList.size(); i++) {
				addPickedImage(selectedList.get(i), false);
			}
		}


//		if(isPostStatus) {
//			galleryShow();
////			edtComment.setFocusable(false);
//			Utility.hideKeyboard(edtComment, getApplicationContext());
//		}
//		else {
//			edtComment.setFocusable(true);
//			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//			galleryHide();
//		}

	}

	public void PostFeed(String comment, List<PostVideoImagesModel> postVideoImagesModels){

		String jsonKeyValue = null;

		if(postVideoImagesModels != null && postVideoImagesModels.size() > 0){
			jsonKeyValue = "{";
			for(int i=0; i<postVideoImagesModels.size(); i++)
			{
				if(i != 0)
					jsonKeyValue = jsonKeyValue + ",";
				jsonKeyValue = jsonKeyValue + "\""+postVideoImagesModels.get(i).getFileBody().getFile().getName() + "\":\"" + postVideoImagesModels.get(i).getIsVideo() + "\"";
				Log.v("TTT", postVideoImagesModels.get(i).getIsVideo() + "..22.." + postVideoImagesModels.get(i).getFileBody().getFile().toString());
				if(i == postVideoImagesModels.size()-1 ){
					jsonKeyValue = jsonKeyValue + "}";
				}
			}
		}



		MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
		multipartTypedOutput.addPart("UserId",new TypedString(HomeActivity.user.getUser().getUserId()+""));
		multipartTypedOutput.addPart("PostText", new TypedString(comment));
		if(jsonKeyValue != null)
			multipartTypedOutput.addPart("ImagesVideos", new TypedString(jsonKeyValue));

		if(postVideoImagesModels != null && postVideoImagesModels.size() > 0){
			Log.d("TTT", "requestUploadSurvey IF.........");
			for (int index = 0; index < postVideoImagesModels.size(); index++) {
				Log.d("TTT", "requestUploadSurvey: survey image " + index + "  " + postVideoImagesModels.get(index).getFileBody().getFile());
				multipartTypedOutput.addPart("ImageContent", new TypedFile(MediaStore.Files.FileColumns.MIME_TYPE, postVideoImagesModels.get(index).getFileBody().getFile()));

			}
		}

		restInterface.AddPost(multipartTypedOutput, new Callback<Post>() {
			@Override
			public void failure(RetrofitError error) {
				Log.v("TTT", "RetrofitError..............." + error.getMessage());
				showProgress(false);
				String message = error.getMessage();
				try {
					boolean IsInternetConnected = checkInternetisAvailable.getInstance(GalleryPostScrollActivity.this).check_internet();
					if (IsInternetConnected) {
						String msg = "";
						if (message != null) {
							msg = message;
						} else {
							msg = "Server Problem.";
						}

						if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
							Utility.showAlert(GalleryPostScrollActivity.this, "Error: " + msg);
						}
					} else {
						Utility.showAlert(GalleryPostScrollActivity.this, Constants.NO_INTERNET_MESSAGE);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			@Override
			public void success(Post model, Response arg1) {
				int code = model.getErrorCode();
				Log.v("TTT", "model..............." + new Gson().toJson(model));

				if (code == 0) {
					showProgress(false);

					addSuccess(model);

				} else {
					showProgress(false);
					Toast.makeText(getApplicationContext(), model.getMsg(), Toast.LENGTH_SHORT).show();
				}

				showProgress(false);
			}
		});


	}

	public void postAddedSuccess(){
		showProgress(false);
	}

	private void hideHeader() {
		needToShowGallery = true;
		layoutHeader.animate().translationY(-layoutHeader.getHeight()).setInterpolator(new AccelerateInterpolator(2));
	}

	private void showHeader() {
		needToShowGallery = false;
		layoutHeader.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
	}

	private void galleryHide() {
		needToShowGallery = false;
		needToHideGallery = false;
		isGalleryVisible = false;
		mLayout.setGalleryHide(true);
		mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
	}

	private void galleryShow() {
		needToShowGallery = true;
		needToHideGallery = false;
		isGalleryVisible = true;
		recyclerGalleryView.setSelection(0);
		recyclerGalleryView.smoothScrollToPosition(0);
		mLayout.setGalleryHide(false);
		mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
	}

	@Override
	public void onBackPressed() {
		Log.v("TTT", "onBackPressed...............");
		if (mLayout != null && (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
			Log.v("TTT", "onBackPressed.....IF..........");
			mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
			hideHeader();
		}
		else {
			Log.v("TTT", "onBackPressed......ELSE.........");
			int count = 0;
			if(encodedStringOfPhoto != null && encodedStringOfPhoto.size() > 0) {
				Log.v("TTT", "onBackPressed........COUNT 211111......." + encodedStringOfPhoto.size());
				count++;
			}
//			else if (layoutUrlMain.getTag().toString().equalsIgnoreCase("1")) {
//				count++;
//			}
			else if(!edtComment.getText().toString().trim().isEmpty()) {
				Log.v("TTT", "onBackPressed........COUNT 2222.......");
				count++;
			}

			if(count == 0) {
				super.onBackPressed();
				overridePendingTransition(R.anim.scale_to_corner, R.anim.scale_top_left_to_bottom_right);
			}
			else {
				try {
					AlertDialog.Builder builder = new AlertDialog.Builder(GalleryPostScrollActivity.this);
					builder.setCancelable(false);
					builder.setMessage("Do you want to discard this comment?");
					builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
//    						super.onBackPressed();
							GalleryPostScrollActivity.this.finish();
							overridePendingTransition(R.anim.scale_to_corner, R.anim.scale_top_left_to_bottom_right);
						}
					});
					builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.show();
				} catch (Exception e) {
//    				Log.e("TTT", "Exception..." + e.getMessage());
					e.printStackTrace();
				}
			}
		}
//		isKeyboardOpen = false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	private void initGalleryRecyclerView() {
		int permission = Utility.checkExternalPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if (permission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_REQUEST_CODE);
		} else {
			ArrayList<AlbumImages> albumImages = getAllShownImagesPath(GalleryPostScrollActivity.this);
			if (albumImages != null) {
				imageRecyclerAdapter = new ImageGridAdapter(this, R.layout.image_view, getAllShownImagesPath(GalleryPostScrollActivity.this));
				recyclerGalleryView.setAdapter(imageRecyclerAdapter);

				recyclerGalleryView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
					@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
					@Override
					public void onGlobalLayout() {
						if (imageRecyclerAdapter.getNumColumns() == 0) {
							final int numColumns = (int) Math.floor(recyclerGalleryView.getWidth() / (mPhotoSize + mPhotoSpacing));
							if (numColumns > 0) {
								final int columnWidth = (recyclerGalleryView.getWidth() / numColumns) - mPhotoSpacing;
								imageRecyclerAdapter.setNumColumns(numColumns);
								imageRecyclerAdapter.setItemHeight(columnWidth);
							}
						}
						recyclerGalleryView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					}
				});
			}
		}

//		MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
//		if (!marshMallowPermission.checkPermissionForExternalReadStorage()) {
//			marshMallowPermission.requestPermissionForExternalReadStorage();
//
//		} else {
//			if (!marshMallowPermission.checkPermissionForExternalReadStorage()) {
//				marshMallowPermission.requestPermissionForExternalReadStorage();
//
//			} else {
//				ArrayList<AlbumImages> albumImages = getAllShownImagesPath(GalleryPostScrollActivity.this);
//				if(albumImages != null){
//					imageRecyclerAdapter = new ImageGridAdapter(this, R.layout.image_view, getAllShownImagesPath(GalleryPostScrollActivity.this));
//					recyclerGalleryView.setAdapter(imageRecyclerAdapter);
//
//					recyclerGalleryView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//						@Override
//						public void onGlobalLayout() {
//							if (imageRecyclerAdapter.getNumColumns() == 0) {
//								final int numColumns = (int) Math.floor(recyclerGalleryView.getWidth() / (mPhotoSize + mPhotoSpacing));
//								if (numColumns > 0) {
//									final int columnWidth = (recyclerGalleryView.getWidth() / numColumns) - mPhotoSpacing;
//									imageRecyclerAdapter.setNumColumns(numColumns);
//									imageRecyclerAdapter.setItemHeight(columnWidth);
//								}
//							}
//							recyclerGalleryView.getViewTreeObserver().removeOnGlobalLayoutListener( this );
//						}
//					});
//				}
//			}
//		}
	}

	@SuppressWarnings("deprecation")
	public ArrayList<AlbumImages> getAllShownImagesPath(Activity activity) {

		final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
//		MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
//		if (!marshMallowPermission.checkPermissionForExternalReadStorage()) {
//			marshMallowPermission.requestPermissionForExternalReadStorage();
//
//			return readAllImagesData();
//		} else {
//			if (!marshMallowPermission.checkPermissionForExternalReadStorage()) {
//				marshMallowPermission.requestPermissionForExternalReadStorage();
//
//				return  readAllImagesData();
//			} else {
////				Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");
////
////				listOfAllImages = new ArrayList<AlbumImages>();
////
////				for (int i = 0; i < imagecursor.getCount(); i++) {
////					imagecursor.moveToPosition(i);
////					int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
////
////					if (fileIsExist(imagecursor.getString(dataColumnIndex))) {
////						AlbumImages albumImages = new AlbumImages();
////						albumImages.setAlbumImages(imagecursor.getString(dataColumnIndex));
////
////						for (int j = 0; j < selectedList.size(); j++) {
////							if(selectedList.get(j).getAlbumImages().equalsIgnoreCase(imagecursor.getString(dataColumnIndex))) {
////								albumImages.setSelected(true);
////								break;
////							}
////						}
////
////						listOfAllImages.add(albumImages);
////					}
////				}
////				return listOfAllImages;
//			}
//
//			return readAllImagesData();
//		}

//		Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");


		// Get relevant columns for use later.
		String[] projection = {
				MediaStore.Files.FileColumns._ID,
				MediaStore.Files.FileColumns.DATA,
				MediaStore.Files.FileColumns.DATE_ADDED,
				MediaStore.Files.FileColumns.MEDIA_TYPE,
				MediaStore.Files.FileColumns.MIME_TYPE,
				MediaStore.Files.FileColumns.TITLE
		};

// Return only video and image metadata.
		String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
				+ MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
				+ " OR "
				+ MediaStore.Files.FileColumns.MEDIA_TYPE + "="
				+ MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

		Uri queryUri = MediaStore.Files.getContentUri("external");

		CursorLoader cursorLoader = new CursorLoader(
				this,
				queryUri,
				projection,
				selection,
				null, // Selection args (none).
				MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
		);

		Cursor imagecursor = cursorLoader.loadInBackground();







		listOfAllImages = new ArrayList<AlbumImages>();

		for (int i = 0; i < imagecursor.getCount(); i++) {
			imagecursor.moveToPosition(i);
			int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
			int dataColumnMeditType = imagecursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE);
//			String dataColumnMediaThumb = imagecursor.getColumnIndex(MediaStore.Video.Thumbnails.MICRO_KIND);

//            BitmapFactory.Options options=new BitmapFactory.Options();
//            options.inSampleSize = 1;
//			Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(imagecursor, id, MediaStore.Video.Thumbnails.MICRO_KIND, options);


			if (fileIsExist(imagecursor.getString(dataColumnIndex))) {
				AlbumImages albumImages = new AlbumImages();
				albumImages.setAlbumImages(imagecursor.getString(dataColumnIndex));
				Log.v("TTT", "MEDIA TYPE......IMAGE....." + imagecursor.getString(dataColumnIndex));
//				Log.v("TTT", "MEDIA TYPE1111..........." + imagecursor.getInt(dataColumnMeditType));
				albumImages.setMediaType(imagecursor.getString(dataColumnMeditType));

//                if(imagecursor.getString(dataColumnMeditType).contains("video")){
//                    Uri uri = Uri.parse("file://"+imagecursor.getString(dataColumnIndex));
//                    Bitmap bMap = ThumbnailUtils.createVideoThumbnail(uri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
//                    albumImages.setThumbBitmap(bMap);
//                }



				for (int j = 0; j < selectedList.size(); j++) {
					if(selectedList.get(j).getAlbumImages().equalsIgnoreCase(imagecursor.getString(dataColumnIndex))) {
						albumImages.setSelected(true);
						break;
					}
				}

				listOfAllImages.add(albumImages);
			}
		}
		return listOfAllImages;

	}

	//	public static boolean isCamera;
	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case Constants.WRITE_EXTERNAL_REQUEST_CODE: {
				if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
					Log.i("TTTTTTT", "**************Permission has been denied by user");
					Utility.toastExternalStorage(GalleryPostScrollActivity.this);

				} else {
					Log.i("TTTTTT", "***************Permission has been granted by user");
					// permission was granted, yay! Do the contacts-related task you need to do.
//					if(isCamera){
//						// permission was granted, yay! Do the
//						Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//						takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
//						startActivityForResult(takePictureIntent, Constants.SELECT_CAMERA_IMAGE);
//					}else {
					ArrayList<AlbumImages> albumImages = getAllShownImagesPath(GalleryPostScrollActivity.this);
					if (albumImages != null) {
						imageRecyclerAdapter = new ImageGridAdapter(this, R.layout.image_view, getAllShownImagesPath(GalleryPostScrollActivity.this));
						recyclerGalleryView.setAdapter(imageRecyclerAdapter);

						recyclerGalleryView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
							@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
							@Override
							public void onGlobalLayout() {
								if (imageRecyclerAdapter.getNumColumns() == 0) {
									final int numColumns = (int) Math.floor(recyclerGalleryView.getWidth() / (mPhotoSize + mPhotoSpacing));
									if (numColumns > 0) {
										final int columnWidth = (recyclerGalleryView.getWidth() / numColumns) - mPhotoSpacing;
										imageRecyclerAdapter.setNumColumns(numColumns);
										imageRecyclerAdapter.setItemHeight(columnWidth);
									}
								}
								recyclerGalleryView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
							}
						});
					}
//					}
				}
			}
			return;
			case Constants.CAMERA_PERMISSION_REQUEST_CODE: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
					Log.i("TTTTTTT", "**************Permission has been denied by user");
					Utility.toastCameraPermission(GalleryPostScrollActivity.this);
				} else {
					Log.i("TTTTTT", "***************Permission has been granted by user");
					mHighQualityImageUri = Utility.generateTimeStampPhotoFileUri(GalleryPostScrollActivity.this);
					Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
					i.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
					startActivityForResult(i, Constants.SELECT_CAMERA_IMAGE);

//					Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//					takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
//					startActivityForResult(takePictureIntent, Constants.SELECT_CAMERA_IMAGE);
				}
				return;
			}

//			case MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE: {
//				// If request is cancelled, the result arrays are empty.
//				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//					// permission was granted, yay! Do the contacts-related task you need to do.
//					if(isCamera){
//						// permission was granted, yay! Do the
//						Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//						takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
//						startActivityForResult(takePictureIntent, Constants.SELECT_CAMERA_IMAGE);
//					}else {
//						ArrayList<AlbumImages> albumImages = getAllShownImagesPath(GalleryPostScrollActivity.this);
//						if (albumImages != null) {
//							imageRecyclerAdapter = new ImageGridAdapter(this, R.layout.image_view, getAllShownImagesPath(GalleryPostScrollActivity.this));
//							recyclerGalleryView.setAdapter(imageRecyclerAdapter);
//
//							recyclerGalleryView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//								@Override
//								public void onGlobalLayout() {
//									if (imageRecyclerAdapter.getNumColumns() == 0) {
//										final int numColumns = (int) Math.floor(recyclerGalleryView.getWidth() / (mPhotoSize + mPhotoSpacing));
//										if (numColumns > 0) {
//											final int columnWidth = (recyclerGalleryView.getWidth() / numColumns) - mPhotoSpacing;
//											imageRecyclerAdapter.setNumColumns(numColumns);
//											imageRecyclerAdapter.setItemHeight(columnWidth);
//										}
//									}
//									recyclerGalleryView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//								}
//							});
//						}
//					}
//
//				} else {
//					// permission denied, boo! Disable the
//					// functionality that depends on this permission.
//				}
//				return;
//			}
//			case MarshMallowPermission.CAMERA_PERMISSION_REQUEST_CODE: {
//
//				// If request is cancelled, the result arrays are empty.
//				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
////					// permission was granted, yay! Do the
////					Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////					takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
////					startActivityForResult(takePictureIntent, Constants.SELECT_CAMERA_IMAGE);
//
//					MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
//					if (!marshMallowPermission.checkPermissionForExternalStorage()) {
//						marshMallowPermission.requestPermissionForExternalStorage();
//					} else {
//						Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//						takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
//						startActivityForResult(takePictureIntent, Constants.SELECT_CAMERA_IMAGE);
//					}
//
//				} else {
//
//					// permission denied, boo! Disable the
//					// functionality that depends on this permission.
//				}
//				return;
//			}

			// other 'case' lines to check for other permissions this app might request
		}
	}

//	public ArrayList<AlbumImages> readAllImagesData(){
//		final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
//		final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
//
//		Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");
//
//		listOfAllImages = new ArrayList<AlbumImages>();
//
//		for (int i = 0; i < imagecursor.getCount(); i++) {
//			imagecursor.moveToPosition(i);
//			int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
//
//			if (fileIsExist(imagecursor.getString(dataColumnIndex))) {
//				AlbumImages albumImages = new AlbumImages();
//				albumImages.setAlbumImages(imagecursor.getString(dataColumnIndex));
//
//				for (int j = 0; j < selectedList.size(); j++) {
//					if(selectedList.get(j).getAlbumImages().equalsIgnoreCase(imagecursor.getString(dataColumnIndex))) {
//						albumImages.setSelected(true);
//						break;
//					}
//				}
//
//				listOfAllImages.add(albumImages);
//			}
//		}
//		return listOfAllImages;
//	}

	private boolean fileIsExist(String path) {
		try{
			File file = new File(path);
			if (file.exists()) {
				// Do something
				return true;
			}
			return false;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}

	}

	public Uri mHighQualityImageUri;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			Log.v("TTT", "onActivityResult HomeActivity requestCode...." + requestCode);
			super.onActivityResult(requestCode, resultCode, data);
//			if (requestCode == Constants.ADD_MORE_PHOTO_FLAG) {
//				int message=data.getIntExtra("result", 0);
//	            if(message == Constants.TAKE_PHOTO) {
//	            	mHighQualityImageUri = Utility.generateTimeStampPhotoFileUri(GalleryPostScrollActivity.this);
////	                Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
////	                i.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
////	                startActivityForResult(i, Constants.SELECT_CAMERA_IMAGE);
//
//					MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
//					if (!marshMallowPermission.checkPermissionForCamera()) {
//						marshMallowPermission.requestPermissionForCamera();
//					} else {
//						if (!marshMallowPermission.checkPermissionForExternalStorage()) {
//							marshMallowPermission.requestPermissionForExternalStorage();
//						} else {
//							Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//							takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
//							startActivityForResult(takePictureIntent, Constants.SELECT_CAMERA_IMAGE);
//
//						}
//					}
//
//	            }
//			}
//			else
			if (requestCode == Constants.SELECT_CAMERA_IMAGE) {
				Log.v("RRR", "In else...3 = " + mHighQualityImageUri.getPath());
				try {
					if (resultCode == Activity.RESULT_OK) {
						Constants.selectedImage = mHighQualityImageUri;
//						Log.v("TTT"," outputFileUri = " + Constants.selectedImage);
//						addPickedImage(mHighQualityImageUri.getPath()+"", false);
						AlbumImages albumImages = new AlbumImages();
						albumImages.setAlbumImages(mHighQualityImageUri.getPath()+"");
						albumImages.setMediaType("image");
						albumImages.setCameraImage(true);
						addPickedImage(albumImages, false);


					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPickedImage(AlbumImages albumImages, boolean isRemove) {
		Log.v("TTT", "isSelected isCameraImage..... remove unwanted........... add Picked 11...." + myGallery.getChildCount());
		removeUnwantedImages();
		Log.v("TTT", "isSelected isCameraImage..... remove unwanted........... add Picked 22...." + myGallery.getChildCount());

		if(isRemove) {
			if(myGallery.getChildCount() == 1 && !isChangePostIcon) {
				Utility.AnimationDisableSendPost(imgPost, getApplicationContext());
				isChangePostIcon = true;
			}
			insertPhoto(albumImages, true);
		}
		else {
			myGallery.setTag(albumImages.getAlbumImages() + "," + albumImages.isCameraImage());
			myGallery.addView(insertPhoto(albumImages, false));
			if(isChangePostIcon) {
				Utility.AnimationEnableSendPost(imgPost, getApplicationContext());
				isChangePostIcon = false;
			}
		}



	}

	public void removeUnwantedImages(){
		Log.v("TTT", "isCameraImage..... remove unwanted1111.........." );
//		for(int j=0; j<myGallery.getChildCount(); j++){
//			String imagepath = myGallery.getChildAt(j).getTag().toString();
////			Log.v("TTT", "imagepath....GALLERY..." + imagepath);
//
//			boolean isSelected = false;
//			for (int i = 0; i < imagePathList.size(); i++) {
//				Log.v("TTT", imagePathList.get(i) + ".....imagepath....GALLERY..." + imagepath);
//				if(imagePathList.get(i).equalsIgnoreCase(imagepath)) {
//					isSelected = true;
//				}
//			}
//
//			if(!isSelected){
//				final int pos = j;
//				Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
//				animZoomOut.setDuration(300);
//				animZoomOut.setAnimationListener(new AnimationListener() {
//					@Override
//					public void onAnimationStart(Animation animation) {	}
//
//					@Override
//					public void onAnimationRepeat(Animation animation) { }
//
//					@Override
//					public void onAnimationEnd(Animation animation) {
//						Log.v("TTT", "imagepath....GALLERY...removing");
//						try {
//							if(myGallery != null)
//								myGallery.removeViewAt(pos);
//
//							new Handler().postDelayed(new Runnable() {
//								@Override
//								public void run() {
//									Display mDisplay = getWindowManager().getDefaultDisplay();
//									final int widthFull, heightFull;
//									widthFull  = (int) (mDisplay.getWidth() / 1.15);
//									heightFull = mDisplay.getHeight() / 2;
//
//									if(myGallery != null && myGallery.getChildCount()  == 1) {
//										View viewFirstItem = myGallery.getChildAt(0);
//										RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthFull, heightFull);
//										params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//										final ImageView layoutImage = (ImageView) viewFirstItem.findViewById(R.id.layoutImage);
//										layoutImage.setLayoutParams(params);
//									}
//								}
//							}, 100);
//
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						updateBottomLayout();
//					}
//				});
//				myGallery.getChildAt(j).startAnimation(animZoomOut);
//			}
//
//		}

		if(listOfAllImages != null){
			for(int j=0; j<myGallery.getChildCount(); j++) {
				String getTag = myGallery.getChildAt(j).getTag().toString();
				String[] s = getTag.split(",");
				String imageGallerypath = s[0];
				boolean isCameraImage = Boolean.valueOf(s[1]);

				boolean isSelected = false;
//				boolean isCameraImage = false;
//				int CurrentPosition = -1;
				Log.v("TTT", "isCameraImage..... remove unwanted...listOfAllImages.size()......." + listOfAllImages.size());
				for(int i=0; i<listOfAllImages.size(); i++){
					Log.v("TTT", "isCameraImage..... remove unwanted...isSelected......." + listOfAllImages.get(i).isSelected());


					if(listOfAllImages.get(i).isSelected()){
						String imagepath = listOfAllImages.get(i).getAlbumImages();
						Log.v("TTT", imageGallerypath + " isCameraImage..... remove unwanted...isSelected......." + imagepath);
						if (imageGallerypath.equalsIgnoreCase(imagepath)) {
							isSelected = true;


//							Log.v("TTT", "isCameraImage..... remove unwanted.........." + isCameraImage);
						}
					}
				}

//				isCameraImage = postVideoImagesModels.get(CurrentPosition).isCameraImage();
//				isCameraImage = postVideoImagesModels.get(j).isCameraImage();
				Log.v("TTT", isSelected + " isSelected isCameraImage..... remove unwanted...isSelected..isCameraImage....." + isCameraImage);
				if(!isSelected && !isCameraImage){
					Log.v("TTT", "isCameraImage..... remove unwanted imagepath....GALLERY...removing");
					try {
						if(myGallery != null)
							myGallery.removeViewAt(j);

						if(myGallery.getChildCount() == 0){
							isChangePostIcon = true;
							Utility.AnimationDisableSendPost(imgPost, getApplicationContext());
						}

						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Display mDisplay = getWindowManager().getDefaultDisplay();
								final int widthFull, heightFull;
//								widthFull  = (int) (mDisplay.getWidth() / 1.15);
//								heightFull = mDisplay.getHeight() / 2;
								widthFull  = (int) (mDisplay.getWidth() / 2.3);//(int) (mDisplay.getWidth() / 1.15);
								heightFull = widthFull;//mDisplay.getHeight() / 2;

								if(myGallery != null && myGallery.getChildCount()  == 1) {
									View viewFirstItem = myGallery.getChildAt(0);
									RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthFull, heightFull);
									params.addRule(RelativeLayout.CENTER_HORIZONTAL);
									final ImageView imgImage = (ImageView) viewFirstItem.findViewById(R.id.imgImage);
									imgImage.setLayoutParams(params);
								}
							}
						}, 100);

					} catch (Exception e) {
						e.printStackTrace();
					}
//					updateBottomLayout();
				}
			}





//			for (Map.Entry<String, Boolean> e : imagePathListMap.entrySet()) {
//				Object key = e.getKey();
//				Object value = e.getValue();
//			}


			Log.v("TTT", "imagepath....GALLERY...removing........imagePathList...." + imagePathListMap.size());

			try {
//			for(int j=0; j<imagePathListMap.size(); j++) {

				for (Map.Entry<String, Boolean> hashValue : imagePathListMap.entrySet()) {
					Log.v("TTT", hashValue.getKey() + "/////..........HASH MAP...." + hashValue.getValue());

//				String imageGallerypath = hashValue.getKey();
//
					boolean isSelected = false;
					for (int i = 0; i < listOfAllImages.size(); i++) {
						if (listOfAllImages.get(i).isSelected()) {
							String imagepath = listOfAllImages.get(i).getAlbumImages();
							if (hashValue.getKey().equalsIgnoreCase(imagepath)) {
								isSelected = true;
							}
						}
					}
//
					Log.v("TTT", "imagepath....GALLERY...removing......isSelected......" + isSelected);
					if (!isSelected && !hashValue.getValue()) {
						Log.v("TTT", "imagepath....GALLERY...removing");
						try {
							if (imagePathListMap != null)
								imagePathListMap.remove(hashValue.getKey());

							Log.v("TTT", imagePathListMap.size() + "....imageRecyclerAdapter.isLimit 111= 111" + imageRecyclerAdapter.isLimit);
							if (imagePathListMap.size() >= 5) {
								imageRecyclerAdapter.isLimit = true;
								imageRecyclerAdapter.notifyDataSetChanged();
							} else if (imageRecyclerAdapter.isLimit) {
								imageRecyclerAdapter.isLimit = false;
								imageRecyclerAdapter.notifyDataSetChanged();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
////					updateBottomLayout();
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}



			if(imagePathListMap.size() >= 5) {
				imageRecyclerAdapter.isLimit = true;
				imageRecyclerAdapter.notifyDataSetChanged();
			}
			else if(imageRecyclerAdapter.isLimit) {
				imageRecyclerAdapter.isLimit = false;
				imageRecyclerAdapter.notifyDataSetChanged();
			}

		}
	}

	@SuppressWarnings("deprecation")
	File file;
	public View insertPhoto(final AlbumImages albumImages, boolean isRemove) {
		Log.v("TTT", "Is remove.........." + isRemove + "  .....  isSelected isCameraImage..... remove unwanted........." + albumImages.isCameraImage());
		try {
			Display mDisplay = getWindowManager().getDefaultDisplay();
			//			final int width  = (int) (mDisplay.getWidth() / 1.15);
//			final int height = mDisplay.getHeight() / 2;

			final int width, height, widthFull, heightFull;
			widthFull  = (int) (mDisplay.getWidth() / 2.3);//(int) (mDisplay.getWidth() / 1.15);
			heightFull = widthFull;//mDisplay.getHeight() / 2;
//			widthFull  = (int) (mDisplay.getWidth() / 1.15);
//			heightFull = mDisplay.getHeight() / 2;

			if(encodedStringOfPhoto != null && encodedStringOfPhoto.size() > 0) {
				width  = (int) (mDisplay.getWidth() / 2.3);//1.35
				height = width;//(int) (mDisplay.getHeight() / 2.4);//2.25

				if(myGallery != null && myGallery.getChildCount() > 0) {
					View viewFirstItem = myGallery.getChildAt(0);
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
					params.addRule(RelativeLayout.CENTER_HORIZONTAL);
					final ImageView imgImage = (ImageView) viewFirstItem.findViewById(R.id.imgImage);
					imgImage.setLayoutParams(params);
				}

			}
			else {
				width  = (int) (mDisplay.getWidth() / 2.3);//1.15
				height = width;//mDisplay.getHeight() / 2;
			}
			Log.d("TTT", "photo add ...... width = " + width + " ... height = " + height);

			LayoutInflater inflater;
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			final View layout =  inflater.inflate(R.layout.picked_image_view1, null);
			layout.setTag(albumImages.getAlbumImages() + "," + albumImages.isCameraImage());
			Log.v("TTT", albumImages.isCameraImage() + "....isCameraImage..... remove unwanted...isSelected........ tag........" + albumImages.getAlbumImages());

			final ImageView imgImage = (ImageView) layout.findViewById(R.id.imgImage);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			imgImage.setLayoutParams(params);

			ImageView imgImagePlay = (ImageView) layout.findViewById(R.id.imgImagePlay);
			if(albumImages.getMediaType().contains("image"))
				imgImagePlay.setVisibility(View.GONE);
			else
				imgImagePlay.setVisibility(View.VISIBLE);

			final ImageView imgDelete = (ImageView) layout.findViewById(R.id.imgQuestionDelete);
			imgDelete.setTag(albumImages.getAlbumImages() + "," + albumImages.isCameraImage());
			imgDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
					animZoomOut.setDuration(300);
					animZoomOut.setAnimationListener(new AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {	}

						@Override
						public void onAnimationRepeat(Animation animation) { }

						@Override
						public void onAnimationEnd(Animation animation) {
							((FlowLayout)layout.getParent()).removeView(layout);

							if(myGallery.getChildCount() == 0){
								isChangePostIcon = true;
								Utility.AnimationDisableSendPost(imgPost, getApplicationContext());
							}


//							updateBottomLayout();
						}
					});
					layout.startAnimation(animZoomOut);

					if(imageRecyclerAdapter != null) {
						if(imageRecyclerAdapter.isLimit) {
							imageRecyclerAdapter.isLimit = false;
							imageRecyclerAdapter.notifyDataSetChanged();
						}
						imageRecyclerAdapter.updateList((imgDelete.getTag().toString().split(",")[0]));

					}

					int j=0;
//					for (int i = 0; i < imagePathListMap.size(); i++) {
					for (Map.Entry<String, Boolean> hashValue : imagePathListMap.entrySet()) {
						Log.v("TTT", (imgDelete.getTag().toString().split(",")[0]) + "...REMOVE USING KEY1...." + hashValue.getKey());
//						String getTag = myGallery.getChildAt(j).getTag().toString();
//						String[] s = getTag.split(",");
//						String imageGallerypath = s[0];
//						boolean isCameraImage = Boolean.valueOf(s[1]);

						if((imgDelete.getTag().toString().split(",")[0]).equalsIgnoreCase(hashValue.getKey())) {
							Log.v("TTT", encodedStringOfPhoto.size() + "...REMOVE USING KEY....111..." + postVideoImagesModels.size() + "..." + imagePathListMap.size());
 							encodedStringOfPhoto.remove(j);
							postVideoImagesModels.remove(j);
							imagePathListMap.remove(hashValue.getKey());
							Log.v("TTT", encodedStringOfPhoto.size() + "...REMOVE USING KEY....222..." + postVideoImagesModels.size() + "..." + imagePathListMap.size());
							break;
						}
						j++;
					}

				}
			});

			if(isRemove) {
				Log.v("TTT", "in remove image");
				for (int i = 0; i < myGallery.getChildCount(); i++) {

					Log.v("TTT", ((myGallery.getChildAt(i).getTag().toString().split(",")[0]) + "....in remove image...." + albumImages.getAlbumImages()));
					if((myGallery.getChildAt(i).getTag().toString().split(",")[0]).equalsIgnoreCase(albumImages.getAlbumImages())) {
						final int pos = i;
						Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
						animZoomOut.setDuration(300);
						animZoomOut.setAnimationListener(new AnimationListener() {
							@Override
							public void onAnimationStart(Animation animation) {	}

							@Override
							public void onAnimationRepeat(Animation animation) { }

							@Override
							public void onAnimationEnd(Animation animation) {
								try {
									if(myGallery != null && pos > 0)
										myGallery.removeViewAt(pos);

									if(myGallery.getChildCount() == 0){
										isChangePostIcon = true;
										Utility.AnimationDisableSendPost(imgPost, getApplicationContext());
									}

									new Handler().postDelayed(new Runnable() {
										@Override
										public void run() {
											if(myGallery != null && myGallery.getChildCount()  == 1) {
												View viewFirstItem = myGallery.getChildAt(0);
												RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthFull, heightFull);
												params.addRule(RelativeLayout.CENTER_HORIZONTAL);
												final ImageView imgImage = (ImageView) viewFirstItem.findViewById(R.id.imgImage);
												imgImage.setLayoutParams(params);
											}
										}
									}, 100);

								} catch (Exception e) {
									e.printStackTrace();
								}
//								updateBottomLayout();
							}
						});
						myGallery.getChildAt(i).startAnimation(animZoomOut);
						break;
					}
				}

				for (int i = 0; i < imagePathListMap.size(); i++) {
					if(albumImages.getAlbumImages().equalsIgnoreCase(imagePathListMap.get(i).toString())) {
						Log.v("TTT", (albumImages.getAlbumImages() + "....in remove image.1111..." + imagePathListMap.get(i).toString()));
						Log.v("TTT", "in remove imageiiiiiiiiiiiiiiiiiiiiiii......" + i);
						if(albumImages.getMediaType().contains("video"))
							isVideoPicked = false;
						encodedStringOfPhoto.remove(i);
						postVideoImagesModels.remove(i);
						imagePathListMap.remove(i);

						break;
					}
				}
			}
			else {

				Log.v("TTT", "MEDIA TYPE...." + albumImages.getMediaType());
				if(albumImages.getMediaType().contains("video")){
					Log.v("TTT", "MEDIA TYPE1111 ISMEDIA....");

					if(albumImages.getThumbBitmap() == null){
						Uri uri = Uri.parse("file://"+albumImages.getAlbumImages());
						Bitmap bMap = ThumbnailUtils.createVideoThumbnail(uri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
						imgImage.setImageBitmap(bMap);
						albumImages.setThumbBitmap(bMap);
					}else{
						imgImage.setImageBitmap(albumImages.getThumbBitmap());
					}

					File file = new File(albumImages.getAlbumImages());
//					pathFileTemp = albumImages.getAlbumImages();
//					Log.v("TTT", "pathFileTemp=  " + pathFileTemp);

//					FileBody fileBody = new FileBody(Utility.SaveAndCompressImage(albumImages.getThumbBitmap()));
					FileBody fileBody = new FileBody(file);
					Log.v("RRRRR", "file = " + file + "  fileBody =  " + fileBody.getFilename());
					if (encodedStringOfPhoto == null)
						encodedStringOfPhoto = new ArrayList<FileBody>();
					encodedStringOfPhoto.add(fileBody);

					if(postVideoImagesModels == null)
						postVideoImagesModels = new ArrayList<>();
					if(albumImages.getMediaType().contains("image"))
						postVideoImagesModels.add(new PostVideoImagesModel(fileBody, 1, albumImages.isCameraImage()));
					else
						postVideoImagesModels.add(new PostVideoImagesModel(fileBody, 2, albumImages.isCameraImage()));

				}else{
					Log.v("TTT", "MEDIA TYPE NOT MEDIA...." + albumImages.getMediaType());

					ImageLoader.getInstance().displayImage("file://" + albumImages.getAlbumImages(), imgImage, doption, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String arg0, View arg1) {
						}

						@Override
						public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
							Log.v("TTT", "string    =   " + arg0);

							int rotate = Utility.getCameraPhotoOrientation(getApplicationContext(), null, Uri.fromFile(new File(albumImages.getAlbumImages())).getPath());

							Log.i("TTTTTT..........", rotate + "");
							if (rotate != 0) {
								Matrix matrix = new Matrix();
								matrix.postRotate(rotate);
								try {
//


									File imageFile = new File(albumImages.getAlbumImages());
									ExifInterface exif = null;
									try {
										exif = new ExifInterface(imageFile.getAbsolutePath());
									} catch (IOException e) {
										e.printStackTrace();
									}
									int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
									switch (orientation) {
										case ExifInterface.ORIENTATION_ROTATE_90:


											imgImage.setImageBitmap(Utility.rotateImage(bitmap, 0));
											//Constants.profileFile = Utility.saveInSdcard(bmRotated);

											File file = Utility.saveInSdcard(Utility.rotateImage(bitmap, 0));
//											pathFileTemp = file.getPath();
//											Log.v("TTT", "pathFileTemp=  " + pathFileTemp);

//											FileBody fileBody = new FileBody(Utility.SaveAndCompressImage(Utility.rotateImage(bitmap, 0)));
											FileBody fileBody = new FileBody(file);
											Log.v("RRRRR", "file = " + file + "  fileBody =  " + fileBody);
											if (encodedStringOfPhoto == null)
												encodedStringOfPhoto = new ArrayList<FileBody>();
											encodedStringOfPhoto.add(fileBody);

											if(postVideoImagesModels == null)
												postVideoImagesModels = new ArrayList<>();
//											postVideoImagesModels.add(new PostVideoImagesModel(fileBody, 1));
											if(albumImages.getMediaType().contains("image"))
												postVideoImagesModels.add(new PostVideoImagesModel(fileBody, 1, albumImages.isCameraImage()));
											else
												postVideoImagesModels.add(new PostVideoImagesModel(fileBody, 2, albumImages.isCameraImage()));

											break;
										case ExifInterface.ORIENTATION_ROTATE_180:
											//rotateImage(bitmap, 180);

											imgImage.setImageBitmap(Utility.rotateImage(bitmap, 180));
											//Constants.profileFile = Utility.saveInSdcard(bmRotated);

											File file1 = Utility.saveInSdcard(Utility.rotateImage(bitmap, 180));
//											pathFileTemp = file1.getPath();
//											Log.v("TTT", "pathFileTemp=  " + pathFileTemp);



//											FileBody fileBody1 = new FileBody(Utility.SaveAndCompressImage(Utility.rotateImage(bitmap, 180)));
											FileBody fileBody1 = new FileBody(file1);
											Log.v("RRRRR", "file = " + file1 + "  fileBody =  " + fileBody1);
											if (encodedStringOfPhoto == null)
												encodedStringOfPhoto = new ArrayList<FileBody>();
											encodedStringOfPhoto.add(fileBody1);

											if(postVideoImagesModels == null)
												postVideoImagesModels = new ArrayList<>();
//											postVideoImagesModels.add(new PostVideoImagesModel(fileBody1, 1));
											if(albumImages.getMediaType().contains("image"))
												postVideoImagesModels.add(new PostVideoImagesModel(fileBody1, 1, albumImages.isCameraImage()));
											else
												postVideoImagesModels.add(new PostVideoImagesModel(fileBody1, 2, albumImages.isCameraImage()));

											break;
										default:
											File file2 = new File(albumImages.getAlbumImages().toString());
//											pathFileTemp = file2.getPath();
//											Log.v("TTT", "pathFileTemp=  " + pathFileTemp);
//											FileBody fileBody2 = new FileBody(Utility.SaveAndCompressImage(albumImages.getThumbBitmap()));
											FileBody fileBody2 = new FileBody(file2);
											Log.v("RRRRR", "file = " + file2 + "  fileBody =  " + fileBody2);
											if (encodedStringOfPhoto == null)
												encodedStringOfPhoto = new ArrayList<FileBody>();
											encodedStringOfPhoto.add(fileBody2);

											if(postVideoImagesModels == null)
												postVideoImagesModels = new ArrayList<>();
//											postVideoImagesModels.add(new PostVideoImagesModel(fileBody2, 2));
											if(albumImages.getMediaType().contains("image"))
												postVideoImagesModels.add(new PostVideoImagesModel(fileBody2, 1, albumImages.isCameraImage()));
											else
												postVideoImagesModels.add(new PostVideoImagesModel(fileBody2, 2, albumImages.isCameraImage()));


											// etc.
									}


								} catch (OutOfMemoryError e) {
									e.printStackTrace();
								}
							} else {
								file = new File(albumImages.getAlbumImages().toString());
//								pathFileTemp = albumImages.getAlbumImages().toString();
//								Log.v("TTT", "pathFileTemp=  " + pathFileTemp);
//								FileBody fileBody = new FileBody(Utility.SaveAndCompressImage(albumImages.getThumbBitmap()));
								FileBody fileBody = new FileBody(file);
								Log.v("RRRRR", "file = " + file + "  fileBody =  " + fileBody);
								if (encodedStringOfPhoto == null)
									encodedStringOfPhoto = new ArrayList<FileBody>();
								encodedStringOfPhoto.add(fileBody);

								if(postVideoImagesModels == null)
									postVideoImagesModels = new ArrayList<>();
//								postVideoImagesModels.add(new PostVideoImagesModel(fileBody, 1));
								if(albumImages.getMediaType().contains("image"))
									postVideoImagesModels.add(new PostVideoImagesModel(fileBody, 1, albumImages.isCameraImage()));
								else
									postVideoImagesModels.add(new PostVideoImagesModel(fileBody, 2, albumImages.isCameraImage()));
							}
						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
						}

						@Override
						public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
						}
					});
				}

				if(imagePathListMap == null)
					imagePathListMap = new HashMap<>();
				Log.v("TTT", "imageRecyclerAdapter.isLimit = ............111.");
				imagePathListMap.put(albumImages.getAlbumImages(), albumImages.isCameraImage());
			}

			Log.v("TTT", imagePathListMap.size() + "....imageRecyclerAdapter.isLimit = " + imageRecyclerAdapter.isLimit);
			if(imagePathListMap.size() >= 5) {
				imageRecyclerAdapter.isLimit = true;
				imageRecyclerAdapter.notifyDataSetChanged();
			}
			else{
				imageRecyclerAdapter.isLimit = false;
				imageRecyclerAdapter.notifyDataSetChanged();
			}

			return layout;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onScrollViewPulledDown(float y) {
		hideHeader();
		if(y == 0 && mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
			galleryHide();
		}
	}

	@Override
	public void onScrollViewPulledUp(float y) {
		if(needToShowGallery && isGalleryVisible)
			showHeader();
	}

	@Override
	public void onScrollChanged(int deltaX, int deltaY) {
		needToHideGallery = false;
	}

	@Override
	public void onScrollStopped() {
		needToHideGallery = true;
	}

	@Override
	public void onResume() {
		super.onResume();
		applyScrollListener();
	}

	private void applyScrollListener() {
		recyclerGalleryView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ImageGridAdapter.AnimateFirstDisplayListener.displayedImages.clear();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mLayout.setOverlayed(true);
			mLayout.invalidate();
			if (mLayout != null && (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED)) {
				mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
				showHeader();
			}
			else {
				hideHeader();
				mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
			}

		}
		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			hideHeader();
			mLayout.setOverlayed(false);
			mLayout.invalidate();
			if (mLayout != null && (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED)) {
				galleryShow();
			}
			else {
				mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
			}

		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		if(show){
			layoutProgress.setVisibility(View.VISIBLE);
		}else{
			layoutProgress.setVisibility(View.GONE);
		}


		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//			add_post_form.setVisibility(show ? View.GONE : View.VISIBLE);
//			add_post_form.animate().setDuration(shortAnimTime).alpha(
//					show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//				@Override
//				public void onAnimationEnd(Animator animation) {
//					add_post_form.setVisibility(show ? View.GONE : View.VISIBLE);
//				}
//			});
//
//			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//			mProgressView.animate().setDuration(shortAnimTime).alpha(
//					show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//				@Override
//				public void onAnimationEnd(Animator animation) {
//					mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//				}
//			});
//		} else {
//			// The ViewPropertyAnimator APIs are not available, so simply show
//			// and hide the relevant UI components.
//			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//			add_post_form.setVisibility(show ? View.GONE : View.VISIBLE);
//		}

//        if(!show)
//            onBackPressed();

	}

	public void addSuccess(Post postListModel) {
		Intent intent = new Intent();
		intent.putExtra("PostModel", new Gson().toJson(postListModel));

		setResult(Constants.START_SIMPLE_POST, intent);

		overridePendingTransition(R.anim.scale_to_corner, R.anim.scale_top_left_to_bottom_right);
		finish();

	}


}