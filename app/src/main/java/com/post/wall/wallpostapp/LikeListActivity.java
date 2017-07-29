package com.post.wall.wallpostapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.post.wall.wallpostapp.adapter.LikeListAdapter;
import com.post.wall.wallpostapp.adapter.PostListAdapter;
import com.post.wall.wallpostapp.model.LikePostUserListModel;
import com.post.wall.wallpostapp.model.User;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ved_pc on 3/4/2017.
 */

public class LikeListActivity extends ActionBarActivity implements SwipyRefreshLayout.OnRefreshListener{
    private SwipyRefreshLayout swipyRefreshLayoutLikeList;
    RecyclerView rvLikeList;
    LikeListAdapter likeListAdapter;
    TextView txtTotalLike;

    RestInterface restInterface;
//    GetUrlClass url;
//    RestAdapter adapter;

    ProgressBar likelist_progress;
    LikePostUserListModel likePostUserListModel;

    int postId, likeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.like_list_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        postId = getIntent().getExtras().getInt("PostId");
        likeCount = getIntent().getExtras().getInt("LikeCount");

        restInterface = Utility.getRetrofitInterface();
//        url = new GetUrlClass();
//        adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//        restInterface = adapter.create(RestInterface.class);

        likelist_progress = (ProgressBar) findViewById(R.id.likelist_progress);

        swipyRefreshLayoutLikeList = (SwipyRefreshLayout) findViewById(R.id.swipyRefreshLayoutLikeList);
        swipyRefreshLayoutLikeList.setOnRefreshListener(this);
        rvLikeList = (RecyclerView) findViewById(R.id.rvLikeList);
        txtTotalLike = (TextView) findViewById(R.id.txtTotalLike);
        txtTotalLike.setText("Total like user " + likeCount);

        showProgress(true);
        getLikeList(0);


    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        swipyRefreshLayoutLikeList.setRefreshing(true);
        if (likePostUserListModel == null || likePostUserListModel.getLikeusers() == null) {
            getLikeList(0);
        }else{
            getLikeList(likePostUserListModel.getLikeusers().size());
        }

    }

    public void getLikeList(int offset){
        restInterface.getLikeUsersOfPost(offset, 10, postId, new Callback<LikePostUserListModel>() {
            @Override
            public void failure(RetrofitError error) {
                showProgress(false);
                String message = error.getMessage();
                try {
                    boolean IsInternetConnected = checkInternetisAvailable.getInstance(LikeListActivity.this).check_internet();

                    Log.v("TTT", "SET ADAPTER");
                    if(likeListAdapter == null){
                        Log.v("TTT", "SET ADAPTER null");
                        if(likePostUserListModel == null || likePostUserListModel.getLikeusers() == null){
                            likePostUserListModel = new LikePostUserListModel();
                            likePostUserListModel.setLikeusers(new ArrayList<User>());
                        }
                        likeListAdapter = new LikeListAdapter(LikeListActivity.this, likePostUserListModel);
                        LinearLayoutManager chatLayoutManager = new LinearLayoutManager(LikeListActivity.this);
                        rvLikeList.setLayoutManager(chatLayoutManager);
                        rvLikeList.setAdapter(likeListAdapter);
                    }else{
                        likeListAdapter.notifyDataSetChanged();
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
                            Utility.showAlert(LikeListActivity.this, "Error: " + msg);
                        }
                    } else {
                        Utility.showAlert(LikeListActivity.this, Constants.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                swipyRefreshLayoutLikeList.setRefreshing(false);

            }

            @Override
            public void success(LikePostUserListModel likePostUserModel, Response arg1) {
                int code = likePostUserModel.getError_code();

                if (code == 0) {
                    showProgress(false);

                    Log.v("TTT", "LikePostUserListModel size  11 ..." + likePostUserModel.getLikeusers().size());

                    if(likePostUserListModel == null || likePostUserListModel.getLikeusers() == null){
//                        postArrayListModel = likePostUserListModel;
                        likePostUserListModel = new LikePostUserListModel();
//                        postArrayListModel.setPosts(new ArrayList<Post>());
                        likePostUserListModel.setLikeusers(likePostUserModel.getLikeusers());
                    }else{
                        likePostUserListModel.getLikeusers().addAll(likePostUserModel.getLikeusers());
                    }

                    Log.v("TTT", "likePostUserListModel size  22 ..." + likePostUserListModel.getLikeusers().size());

                    if(likeListAdapter == null){
                        likeListAdapter = new LikeListAdapter(LikeListActivity.this, likePostUserListModel);
                        LinearLayoutManager chatLayoutManager = new LinearLayoutManager(LikeListActivity.this);
                        rvLikeList.setLayoutManager(chatLayoutManager);
                        rvLikeList.setAdapter(likeListAdapter);
                    }else{
                        likeListAdapter.notifyDataSetChanged();
                    }

                } else {
                    showProgress(false);
//                    Toast.makeText(getApplicationContext(), likePostUserListModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

                swipyRefreshLayoutLikeList.setRefreshing(false);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if(show){
            likelist_progress.setVisibility(View.VISIBLE);
        }else{
            likelist_progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
