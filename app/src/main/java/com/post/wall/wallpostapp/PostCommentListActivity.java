package com.post.wall.wallpostapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.post.wall.wallpostapp.adapter.PostCommentListAdapter;
import com.post.wall.wallpostapp.model.Comment;
import com.post.wall.wallpostapp.model.Post;
import com.post.wall.wallpostapp.model.PostCommentModel;
import com.post.wall.wallpostapp.model.PostCommentsListModel;
import com.post.wall.wallpostapp.utility.BitmapUtils;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;
import com.squareup.okhttp.OkHttpClient;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by ved_pc on 3/8/2017.
 */

public class PostCommentListActivity extends ActionBarActivity implements SwipyRefreshLayout.OnRefreshListener, View.OnClickListener{ //}, SlidingUpPanelLayout.ScrollViewTouchEventListener, SlidingUpPanelLayout.OnScrollChangedListener, SlidingUpPanelLayout.OnScrollStoppedListener{

    public static Post post;

    private SwipyRefreshLayout swipyRefreshLayoutPostComment;
    RecyclerView rvPostComment;
    PostCommentListAdapter postListAdapter;
    RelativeLayout layoutProgress;
    EditText edtComment;
    ImageView imgAddPhoto, imgPostComment, imgSelectedImage, imgImageDelete;
    RelativeLayout layoutImageView;
    boolean isImageSelected = false, isButtonEnable;

    RestInterface restInterface;
    GetUrlClass url;
    RestAdapter adapter;

    public static PostCommentsListModel postCommentsListModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comment_view);

//        post = new Gson().fromJson(getIntent().getExtras().get("Post").toString(), Post.class);
//        Log.v("TTT", "MainActivity..User..." + getIntent().getExtras().get("Post").toString());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comments(" + post.getPost().getCommentCount() + ")");

        postCommentsListModel = null;

        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(50, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(50, TimeUnit.SECONDS);

        url = new GetUrlClass();
        adapter = new RestAdapter.Builder().setClient(new OkClient(mOkHttpClient)).setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
        restInterface = adapter.create(RestInterface.class);

        layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);

        swipyRefreshLayoutPostComment = (SwipyRefreshLayout) findViewById(R.id.swipyRefreshLayoutPostComment);
        swipyRefreshLayoutPostComment.setOnRefreshListener(this);
        rvPostComment = (RecyclerView) findViewById(R.id.rvPostComment);


        edtComment = (EditText) findViewById(R.id.edtComment);
        edtComment.clearFocus();

        edtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.v("TTT", edtComment.getText().toString().trim().length() + "......" + scaledFile + "...onTextChanged.............Text change..." + isButtonEnable);
//                if((edtComment.getText().toString().trim().length() > 0 &&  scaledFile != null) && !isButtonEnable) {
//                    Log.v("TTT", "onTextChanged.............IF");
//                    Utility.AnimationEnableSendPost(imgPostComment, getApplicationContext());
//                    isButtonEnable = true;
//                }
//                else if ((edtComment.getText().toString().trim().length() == 0 ||  scaledFile == null) && isButtonEnable) {
//                    Log.v("TTT", "onTextChanged.............ELSE");
//                    Utility.AnimationDisableSendPost(imgPostComment, getApplicationContext());
//                    isButtonEnable = false;
//                }
                enableDisableSendPost();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });


        imgAddPhoto = (ImageView) findViewById(R.id.imgPhoto);
        imgAddPhoto.setOnClickListener(this);
        imgPostComment = (ImageView) findViewById(R.id.imgPostComment);
        imgPostComment.setOnClickListener(this);

        layoutImageView = (RelativeLayout) findViewById(R.id.layoutImageView);
        layoutImageView.setVisibility(View.GONE);
        imgSelectedImage = (ImageView) findViewById(R.id.imgSelectedImage);
        imgImageDelete = (ImageView) findViewById(R.id.imgImageDelete);
        imgImageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaledFile = null;
                layoutImageView.setVisibility(View.GONE);
//                isImageSelected = false;
//                isButtonEnable = false;

//                Log.v("TTT", edtComment.getText().toString().trim().length() + "......" + scaledFile + "...onTextChanged.............DELETE..." + isButtonEnable);
//                if((edtComment.getText().toString().trim().length() > 0 &&  scaledFile != null) && !isButtonEnable) {
//                    Log.v("TTT", "onTextChanged.............IF");
//                    Utility.AnimationEnableSendPost(imgPostComment, getApplicationContext());
//                    isButtonEnable = true;
//                }
//                else if ((edtComment.getText().toString().trim().length() == 0 ||  scaledFile == null) && isButtonEnable) {
//                    Log.v("TTT", "onTextChanged.............ELSE");
//                    Utility.AnimationDisableSendPost(imgPostComment, getApplicationContext());
//                    isButtonEnable = false;
//                }
                enableDisableSendPost();
            }
        });

        showProgress(true);
        getPostCommentList(0);



    }

    public void getPostCommentList(int offset){
        restInterface.getPostCommentList(offset, 10, post.getPost().getPostId(), HomeActivity.user.getUser().getUserId(), new Callback<PostCommentsListModel>() {
            @Override
            public void failure(RetrofitError error) {
                showProgress(false);
                String message = error.getMessage();
                try {
                    boolean IsInternetConnected = checkInternetisAvailable.getInstance(PostCommentListActivity.this).check_internet();

                    Log.v("TTT", "SET ADAPTER");
                    if(postListAdapter == null){
                        Log.v("TTT", "SET ADAPTER null");
                        postListAdapter = new PostCommentListAdapter(PostCommentListActivity.this, postCommentsListModel);
                        LinearLayoutManager chatLayoutManager = new LinearLayoutManager(PostCommentListActivity.this);
                        rvPostComment.setLayoutManager(chatLayoutManager);
                        rvPostComment.setAdapter(postListAdapter);
                    }else{
                        postListAdapter.notifyDataSetChanged();
                        Log.v("TTT", "SET ADAPTER not null");
                    }

                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Utility.showAlert(PostCommentListActivity.this, "Error: " + msg);
                        }

                    } else {
                        Utility.showAlert(PostCommentListActivity.this, Constants.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                swipyRefreshLayoutPostComment.setRefreshing(false);

            }

            @Override
            public void success(PostCommentsListModel postListModel, Response arg1) {
                Log.v("TTT", "Post Comment List............" + new Gson().toJson(postListModel));
                int code = postListModel.getErrorCode();

                if (code == 0) {
                    showProgress(false);

                    if(postCommentsListModel == null || postCommentsListModel.getComments() == null){
                        postCommentsListModel = new PostCommentsListModel();
                        postCommentsListModel.setComments(postListModel.getComments());
                    }else{
                        postCommentsListModel.getComments().addAll(0, postListModel.getComments());
                    }

                    Log.v("TTT", "postArrayListModel size..." + postCommentsListModel.getComments().size());

                    if(postListAdapter == null || (postCommentsListModel.getComments().size() == postListModel.getComments().size())){
                        postListAdapter = new PostCommentListAdapter(PostCommentListActivity.this, postCommentsListModel);
                        LinearLayoutManager chatLayoutManager = new LinearLayoutManager(PostCommentListActivity.this);
                        rvPostComment.setLayoutManager(chatLayoutManager);
                        rvPostComment.setAdapter(postListAdapter);
                        rvPostComment.scrollToPosition(postCommentsListModel.getComments().size());
                    }else{
                        postListAdapter.notifyDataSetChanged();
                        rvPostComment.scrollToPosition(postCommentsListModel.getComments().size() - postListModel.getComments().size());
                    }

                } else {
                    showProgress(false);
                    Toast.makeText(getApplicationContext(), postListModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

                swipyRefreshLayoutPostComment.setRefreshing(false);
            }
        });

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        getPostCommentList((postCommentsListModel == null || postCommentsListModel.getComments() == null) ? 0 : postCommentsListModel.getComments().size());

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgPhoto:

                selectImage();
                
//                int permission = Utility.checkExternalPermission(PostCommentListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                if (permission != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(PostCommentListActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_REQUEST_CODE);
//                }
//                else {
//                    mLayout.setOverlayed(false);
//                    Utility.hideKeyboard(v, PostCommentListActivity.this);
//                    imgAddPhoto.comment(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(isGalleryVisible)
//                                galleryHide();
//                            else
//                                galleryShow();
//                        }
//                    });
//                }
                break;

            case R.id.imgPostComment:
                attemptPostComment();
                break;
        }

    }

    private void attemptPostComment() {

        if(edtComment.getText().length() > 0 || scaledFile != null){
            showProgress(true);

            TypedFile photoTypedFile = null;
            if (scaledFile != null)
                photoTypedFile = new TypedFile("image/*", scaledFile);

            restInterface.postComment(post.getPost().getPostId(), HomeActivity.user.getUser().getUserId(), edtComment.getText().toString(), photoTypedFile, new Callback<PostCommentModel>() {

                @Override
                public void failure(RetrofitError error) {
                    Log.v("TTT", "Post Comment added failure...................." + error);
                    showProgress(false);
                    String message = error.getMessage();
                    try {
                        boolean IsInternetConnected = checkInternetisAvailable.getInstance(PostCommentListActivity.this).check_internet();
                        if (IsInternetConnected) {
                            String msg = "";
                            if (message != null) {
                                msg = message;
                            } else {
                                msg = "Server Problem.";
                            }

                            if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                                Utility.showAlert(PostCommentListActivity.this, "Error: " + msg);
                            }
                        } else {
                            Utility.showAlert(PostCommentListActivity.this, Constants.NO_INTERNET_MESSAGE);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override
                public void success(PostCommentModel postCommentModel, Response arg1) {

                    Log.v("TTT", "Post Comment added...................." + new Gson().toJson(postCommentModel));
                    int code = postCommentModel.getErrorCode();

                    if (code == 0) {
                        showProgress(false);

                        if(postCommentsListModel == null || postCommentsListModel.getComments() == null){
                            postCommentsListModel = new PostCommentsListModel();
                            postCommentsListModel.setComments(new ArrayList<Comment>());
                            Comment comment = new Comment();
                            comment.setPostcomment(postCommentModel.getPostComment());
                            postCommentsListModel.getComments().add(comment);
                            Log.v("TTT", "postArrayListModel GSON22 comment..." + new Gson().toJson(comment));
                        }else{
                            Comment comment = new Comment();
                            comment.setPostcomment(postCommentModel.getPostComment());
                            postCommentsListModel.getComments().add(comment);

                            Log.v("TTT", "postArrayListModel GSON11 comment..." + new Gson().toJson(comment));
                            Log.v("TTT", "postArrayListModel GSON..." + new Gson().toJson(postCommentsListModel));
                        }

                        Log.v("TTT", "postArrayListModel size..22." + postCommentsListModel.getComments().size());

                        if(postListAdapter == null || (postCommentsListModel.getComments().size() == 1)){
                            postListAdapter = new PostCommentListAdapter(PostCommentListActivity.this, postCommentsListModel);
                            LinearLayoutManager chatLayoutManager = new LinearLayoutManager(PostCommentListActivity.this);
                            rvPostComment.setLayoutManager(chatLayoutManager);
                            rvPostComment.setAdapter(postListAdapter);
                        }else{
                            postListAdapter.notifyDataSetChanged();
                        }

                        edtComment.setText("");
                        scaledFile = null;
                        layoutImageView.setVisibility(View.GONE);
                        Utility.AnimationDisableSendPost(imgPostComment, getApplicationContext());

                        post.getPost().setCommentCount(post.getPost().getCommentCount() + 1);
                        getSupportActionBar().setTitle("Comments(" + post.getPost().getCommentCount() + ")");
                    } else {
                        showProgress(false);
                        Toast.makeText(getApplicationContext(), postCommentModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    int flag = 0;
    private String userChooseTask, strUserProfileImage;
    Uri selectedImageUri;
    File scaledFile;
    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PostCommentListActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.CheckAllMarshmallowPermissionForActivityCamera(getApplicationContext(), PostCommentListActivity.this);
                Log.d("result", "onClick: " + result);
                if (items[item].equals("Take Photo")) {
                    flag = 1;
                    userChooseTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    flag = 1;
                    userChooseTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.REQUEST_CAMERA);
    }

    public void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, Constants.SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("TTT", "onActivityResult");
        if (resultCode == Activity.RESULT_OK) {
            Log.v("TTT", "onActivityResult   RESULT_OK..........." + requestCode);
            if (requestCode == Constants.SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == Constants.REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

        try{
            Log.v("TTT", "postListAdapter.........." + postListAdapter + "/................" + postCommentsListModel.getComments().get(0).getPostcomment().getReplyCount());
            if(postListAdapter != null){
                postListAdapter.notifyDataSetChanged();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    private void onSelectFromGalleryResult(Intent data) {
        Log.v("TTT", "onSelectFromGalleryResult..........." + data);
        layoutImageView.setVisibility(View.VISIBLE);
        isImageSelected = true;
        try {
            selectedImageUri = data.getData();
            Log.v("TTT", "onSelectFromGalleryResult....selectedImageUri......." + selectedImageUri);
            strUserProfileImage = getRealPathFromURI(selectedImageUri);
            Log.v("TTT", "onSelectFromGalleryResult....PATH......." + strUserProfileImage);
            if (strUserProfileImage != null) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                Utility.initImageLoader(PostCommentListActivity.this);
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(true)
                        .showImageForEmptyUri(R.drawable.img_profile)
                        .showImageOnFail(R.drawable.img_profile)
                        .showImageOnLoading(R.drawable.img_profile).build();
                imageLoader.displayImage("file://" + strUserProfileImage, imgSelectedImage, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        Log.v("TTT", "onSelectFromGalleryResult onLoadingFailed");
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.v("TTT", "onSelectFromGalleryResult onLoadingComplete........" + imageUri);

                        loadedImage = Utility.modifyOrientation(loadedImage, strUserProfileImage);
                        Uri uri = getImageUri(getApplicationContext(), loadedImage);
                        Log.i("TTT", uri + "  **Uri   " + imageUri);
                        scaledFile = new File(getRealPathFromURI(uri));
                        imgSelectedImage.setImageBitmap(loadedImage);

//                        Log.v("TTT", edtComment.getText().toString().trim().length() + "......" + scaledFile + "...onTextChanged.............Gallery 111..." + isButtonEnable);
//                        if((edtComment.getText().toString().trim().length() > 0 &&  scaledFile != null) && !isButtonEnable) {
//                            Log.v("TTT", "onTextChanged.............IF");
//                            Utility.AnimationEnableSendPost(imgPostComment, getApplicationContext());
//                            isButtonEnable = true;
//                        }
//                        else if ((edtComment.getText().toString().trim().length() == 0 ||  scaledFile == null) && isButtonEnable) {
//                            Log.v("TTT", "onTextChanged.............ELSE");
//                            Utility.AnimationDisableSendPost(imgPostComment, getApplicationContext());
//                            isButtonEnable = false;
//                        }
                        enableDisableSendPost();

                    }

                });
            } else {
                strUserProfileImage = BitmapUtils.getPath(PostCommentListActivity.this, selectedImageUri);
                ImageLoader imageLoader = ImageLoader.getInstance();
                Utility.initImageLoader(PostCommentListActivity.this);
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(true)
                        .showImageForEmptyUri(R.drawable.img_profile)
                        .showImageOnFail(R.drawable.img_profile)
                        .showImageOnLoading(R.drawable.img_profile).build();
                imageLoader.displayImage("file://" + strUserProfileImage, imgSelectedImage, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        loadedImage = Utility.modifyOrientation(loadedImage, strUserProfileImage);
                        Uri uri = getImageUri(getApplicationContext(), loadedImage);
                        Log.i("**Uri", uri + " " + imageUri);
                        scaledFile = new File(getRealPathFromURI(uri));
                        imgSelectedImage.setImageBitmap(loadedImage);

//                        Log.v("TTT", edtComment.getText().toString().trim().length() + "......" + scaledFile + "...onTextChanged.............Gallery 222..." + isButtonEnable);
//                        if((edtComment.getText().toString().trim().length() > 0 &&  scaledFile != null) && !isButtonEnable) {
//                            Log.v("TTT", "onTextChanged.............IF");
//                            Utility.AnimationEnableSendPost(imgPostComment, getApplicationContext());
//                            isButtonEnable = true;
//                        }
//                        else if ((edtComment.getText().toString().trim().length() == 0 ||  scaledFile == null) && isButtonEnable) {
//                            Log.v("TTT", "onTextChanged.............ELSE");
//                            Utility.AnimationDisableSendPost(imgPostComment, getApplicationContext());
//                            isButtonEnable = false;
//                        }
                        enableDisableSendPost();

                    }

                });
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResult(Intent data) {
        try {
            if (data != null) {

                layoutImageView.setVisibility(View.VISIBLE);
                isImageSelected = true;

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(PostCommentListActivity.this, thumbnail);
                strUserProfileImage = getRealPathFromURI(tempUri);
                if (strUserProfileImage != null)
                    Log.v("path", "" + strUserProfileImage);
                ImageLoader imageLoader = ImageLoader.getInstance();
                Utility.initImageLoader(PostCommentListActivity.this);
                int fallback = 0;
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(true)
                        .showImageForEmptyUri(fallback)
                        .showImageOnFail(fallback)
                        .showImageOnLoading(R.drawable.img_profile).build();
                imageLoader.displayImage("file://" + strUserProfileImage, imgSelectedImage, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        loadedImage = Utility.modifyOrientation(loadedImage, strUserProfileImage);
                        Uri uri = getImageUri(getApplicationContext(), loadedImage);
                        Log.i("**Uri", uri + " " + imageUri);
                        scaledFile = new File(getRealPathFromURI(uri));
                        imgSelectedImage.setImageBitmap(loadedImage);

//                        Log.v("TTT", edtComment.getText().toString().trim().length() + "......" + scaledFile + "...onTextChanged.............Camera..." + isButtonEnable);
//                        if((edtComment.getText().toString().trim().length() > 0 &&  scaledFile != null) && !isButtonEnable) {
//                            Log.v("TTT", "onTextChanged.............IF");
//                            Utility.AnimationEnableSendPost(imgPostComment, getApplicationContext());
//                            isButtonEnable = true;
//                        }
//                        else if ((edtComment.getText().toString().trim().length() == 0 ||  scaledFile == null) && isButtonEnable) {
//                            Log.v("TTT", "onTextChanged.............ELSE");
//                            Utility.AnimationDisableSendPost(imgPostComment, getApplicationContext());
//                            isButtonEnable = false;
//                        }
                        enableDisableSendPost();

                    }

                });


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean Read = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0) {
                    if (cameraAccepted && Read) {
                        if (userChooseTask.equals("Take Photo"))
                            cameraIntent();
                        else if (userChooseTask.equals("Choose from Library"))
                            galleryIntent();
                    }
                }
                break;
            case Constants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0) {
                    if (grantResults.length > 1) {
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                            if (userChooseTask.equals("Take Photo"))
                                cameraIntent();
                            else if (userChooseTask.equals("Choose from Library"))
                                galleryIntent();
                        }
                    } else {
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            if (userChooseTask.equals("Take Photo"))
                                cameraIntent();
                            else if (userChooseTask.equals("Choose from Library"))
                                galleryIntent();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void enableDisableSendPost(){
//        Log.v("TTT", edtComment.getText().toString().trim().length() + "......" + scaledFile + "...onTextChanged.............Text change..." + isButtonEnable);
        if((edtComment.getText().toString().trim().length() > 0 ||  scaledFile != null) && !isButtonEnable) {
            Log.v("TTT", "onTextChanged.............IF");
            Utility.AnimationEnableSendPost(imgPostComment, getApplicationContext());
            isButtonEnable = true;
        }
        else if ((edtComment.getText().toString().trim().length() == 0 &&  scaledFile == null) && isButtonEnable) {
            Log.v("TTT", "onTextChanged.............ELSE");
            Utility.AnimationDisableSendPost(imgPostComment, getApplicationContext());
            isButtonEnable = false;
        }
    }

//    @Override
//    protected void onResume() {
//
//        try{
//            if(postListAdapter != null){
//                postListAdapter.notifyDataSetChanged();
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        super.onResume();
//
//    }


}
