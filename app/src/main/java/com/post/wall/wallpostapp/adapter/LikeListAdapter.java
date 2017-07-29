package com.post.wall.wallpostapp.adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.post.wall.wallpostapp.AlbumPhotosSlideShowActivity;
import com.post.wall.wallpostapp.GalleryPostScrollActivity;
import com.post.wall.wallpostapp.HomeActivity;
import com.post.wall.wallpostapp.R;
import com.post.wall.wallpostapp.ShareActivity;
import com.post.wall.wallpostapp.model.LikePostUserListModel;
import com.post.wall.wallpostapp.model.PostLikeModel;
import com.post.wall.wallpostapp.utility.CircleImageView;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.UILApplication;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;

import org.apache.james.mime4j.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

//import com.post.wall.wallpostapp.utility.FeedImageView;

public class LikeListAdapter extends RecyclerView.Adapter<LikeListAdapter.ViewHolder>{
//
LikePostUserListModel likePostUserListModel;
    Activity ctx;

    DisplayImageOptions doption = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.img_profile)
            .showImageOnFail(R.drawable.img_profile)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    com.android.volley.toolbox.ImageLoader imageLoader = UILApplication.getInstance().getImageLoader();

    public LikeListAdapter(Activity ctx, LikePostUserListModel likePostUserListModel) {
        super();
        this.ctx = ctx;
        this.likePostUserListModel = likePostUserListModel;

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgUserImage;
        TextView txtUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgUserImage = (CircleImageView) itemView.findViewById(R.id.imgUserImage);
            txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
        }
    }

    @Override
    public int getItemCount() {
        return likePostUserListModel.getLikeusers().size();
    }

    @Override
    public long getItemId(int position) {
        return likePostUserListModel.getLikeusers().indexOf(getItemId(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.like_list_row, viewGroup, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        try {
            com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            imageLoader.displayImage(likePostUserListModel.getLikeusers().get(position).getProfilePic(), viewHolder.imgUserImage, doption);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.txtUserName.setText(likePostUserListModel.getLikeusers().get(position).getFirstName() + " " + likePostUserListModel.getLikeusers().get(position).getLastName());


    }



}
