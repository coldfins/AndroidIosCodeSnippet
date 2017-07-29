package com.post.wall.wallpostapp;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.post.wall.wallpostapp.adapter.PostListAdapter;
import com.post.wall.wallpostapp.model.Post;
import com.post.wall.wallpostapp.model.PostListModel;
import com.post.wall.wallpostapp.model.UserModel;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.PreferenceManager;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by ved_pc on 2/18/2017.
 */

public class HomeActivity extends ActionBarActivity implements View.OnClickListener, SwipyRefreshLayout.OnRefreshListener{
//    Button btnPost;
    public static UserModel user;

    private SwipyRefreshLayout swipyRefreshLayoutChatConversion;
    RecyclerView rvPostList;
    PostListAdapter postListAdapter;

    RestInterface restInterface;
//    GetUrlClass url;
//    RestAdapter adapter;

    RelativeLayout layoutProgress;
    LinearLayout home_feed_form;
    PostListModel postArrayListModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TTT", "HOME ACTIVITY");
        setContentView(R.layout.home_view);
        String json = PreferenceManager.getLoggedUser(getApplicationContext());
        user = new Gson().fromJson(json, UserModel.class);
        Log.v("TTT", "MainActivity..User..." + new Gson().toJson(user));

        int permission = Utility.checkExternalPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_REQUEST_CODE);
        }

        restInterface = Utility.getRetrofitInterface();

//        url = new GetUrlClass();
//        adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//        restInterface = adapter.create(RestInterface.class);

        layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);
        home_feed_form = (LinearLayout) findViewById(R.id.home_feed_form);

        swipyRefreshLayoutChatConversion = (SwipyRefreshLayout) findViewById(R.id.swipyRefreshLayoutChatConverstion);
        swipyRefreshLayoutChatConversion.setOnRefreshListener(this);
        rvPostList = (RecyclerView) findViewById(R.id.rvPostList);


        postArrayListModel = new PostListModel();
        postArrayListModel.setPosts(new ArrayList<Post>());
        postListAdapter = new PostListAdapter(HomeActivity.this, postArrayListModel);
        LinearLayoutManager chatLayoutManager = new LinearLayoutManager(HomeActivity.this);
        rvPostList.setLayoutManager(chatLayoutManager);
        rvPostList.setAdapter(postListAdapter);


        showProgress(true);
        getPostList(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    public void getPostList(int offset){
        restInterface.getPostList(offset, 10, HomeActivity.user.getUser().getUserId(), new Callback<PostListModel>() {
            @Override
            public void failure(RetrofitError error) {
                showProgress(false);
                String message = error.getMessage();
                try {
                    boolean IsInternetConnected = checkInternetisAvailable.getInstance(HomeActivity.this).check_internet();

                    Log.v("TTT", "SET ADAPTER");
                    if(postListAdapter == null){
                        Log.v("TTT", "SET ADAPTER null");
                        postListAdapter = new PostListAdapter(HomeActivity.this, postArrayListModel);
                        LinearLayoutManager chatLayoutManager = new LinearLayoutManager(HomeActivity.this);
                        rvPostList.setLayoutManager(chatLayoutManager);
                        rvPostList.setAdapter(postListAdapter);
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
                            Utility.showAlert(HomeActivity.this, "Error: " + msg);
                        }




                    } else {
                        Utility.showAlert(HomeActivity.this, Constants.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                swipyRefreshLayoutChatConversion.setRefreshing(false);

            }

            @Override
            public void success(PostListModel postListModel, Response arg1) {
                int code = postListModel.getErrorCode();

                if (code == 0) {
                    showProgress(false);

                    if(postArrayListModel == null || postArrayListModel.getPosts() == null){
//                        postArrayListModel = postListModel;
                        postArrayListModel = new PostListModel();
//                        postArrayListModel.setPosts(new ArrayList<Post>());
                        postArrayListModel.setPosts(postListModel.getPosts());
                    }else{
                        postArrayListModel.getPosts().addAll(postListModel.getPosts());
                    }

                    Log.v("TTT", "postArrayListModel size..." + postArrayListModel.getPosts().size());

                    if(postListAdapter == null){
                        postListAdapter = new PostListAdapter(HomeActivity.this, postArrayListModel);
                        LinearLayoutManager chatLayoutManager = new LinearLayoutManager(HomeActivity.this);
                        rvPostList.setLayoutManager(chatLayoutManager);
                        rvPostList.setAdapter(postListAdapter);
                    }else{
                        postListAdapter.notifyDataSetChanged();
                    }

                } else {
                    showProgress(false);
                    Toast.makeText(getApplicationContext(), postListModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

                swipyRefreshLayoutChatConversion.setRefreshing(false);
            }
        });

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            home_feed_form.setVisibility(show ? View.GONE : View.VISIBLE);
//            home_feed_form.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    home_feed_form.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            home_progress.setVisibility(show ? View.VISIBLE : View.GONE);
//            home_progress.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    home_progress.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            home_progress.setVisibility(show ? View.VISIBLE : View.GONE);
//            home_feed_form.setVisibility(show ? View.GONE : View.VISIBLE);
//        }

        if(show){
            layoutProgress.setVisibility(View.VISIBLE);
        }else{
            layoutProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        getPostList(postArrayListModel.getPosts().size());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("TTT", " onActivityResult......................");

        if(requestCode == Constants.START_SIMPLE_POST || requestCode == Constants.START_GALLERY_POST){
            if(data != null && data.getExtras().get("PostModel") != null){
                Post postListModel = new Gson().fromJson(data.getExtras().get("PostModel").toString(), Post.class);
                if(postArrayListModel != null){
                    postArrayListModel.getPosts().add(0, postListModel);
                    postListAdapter.notifyDataSetChanged();
                }else{
                    postArrayListModel = new PostListModel();
                    postArrayListModel.setPost(new Post());
                    postArrayListModel.getPosts().add(postListModel);
                    postListAdapter = new PostListAdapter(HomeActivity.this, postArrayListModel);
                    LinearLayoutManager chatLayoutManager = new LinearLayoutManager(HomeActivity.this);
                    rvPostList.setLayoutManager(chatLayoutManager);
                    rvPostList.setAdapter(postListAdapter);
                }
            }
        }else if(requestCode == Constants.SHARE_POST){
            Log.v("TTT", " Constants.SHARE_POST......................" + data);
            if(data != null && data.getExtras().get("PostModel") != null){
                Log.v("TTT", " Constants.SHARE_POST. ADDING.....................");
                Post post = new Gson().fromJson(data.getExtras().get("PostModel").toString(), Post.class);
                int position = Integer.parseInt(data.getExtras().get("Position").toString());

//                postArrayListModel.getPosts()
                postArrayListModel.getPosts().add(0, post);
                postArrayListModel.getPosts().get(position).getPost().setShareCount(postArrayListModel.getPosts().get(position).getPost().getShareCount() + 1);
                postListAdapter.notifyDataSetChanged();
            }

        }else if(requestCode == Constants.COMMENT_POST){
            Log.v("TTT", " Constants.COMMENT_POST......................" + data);
            if(postArrayListModel != null){
                postListAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
//        menuAddItem = menu.findItem(R.id.action_add_contact);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_referesh){
            showProgress(true);
            postArrayListModel.getPosts().clear();
            getPostList(0);
        }else  if(item.getItemId() == R.id.action_setting){
            Intent i = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }else  if(item.getItemId() == R.id.action_exit){
            PreferenceManager.putJoin(false, HomeActivity.this);
            PreferenceManager.putLogin(false, HomeActivity.this);
            PreferenceManager.putLoggedUser(null, getApplicationContext());

            Intent myIntent = new Intent(HomeActivity.this, JoinActivity.class);
            startActivity(myIntent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.WRITE_EXTERNAL_REQUEST_CODE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TTTTTTT", "**************Permission has been denied by user");
                    Utility.toastExternalStorage(HomeActivity.this);

                } else {
                    Log.i("TTTTTT", "***************Permission has been granted by user");

                }
            }
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("TTT", "On Resume................222");
        if(postArrayListModel != null){
            postListAdapter.notifyDataSetChanged();
        }

    }
}
