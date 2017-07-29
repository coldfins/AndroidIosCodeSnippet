package com.post.wall.wallpostapp.adapter;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.post.wall.wallpostapp.CommentReplyListActivity;
import com.post.wall.wallpostapp.GalleryPostScrollActivity;
import com.post.wall.wallpostapp.HomeActivity;
import com.post.wall.wallpostapp.LikeListActivity;
import com.post.wall.wallpostapp.PostCommentListActivity;
import com.post.wall.wallpostapp.R;
import com.post.wall.wallpostapp.ShareActivity;
import com.post.wall.wallpostapp.UserProfileActivity;
import com.post.wall.wallpostapp.model.AlbumImages;
import com.post.wall.wallpostapp.model.PostLikeModel;
import com.post.wall.wallpostapp.model.PostListModel;
import com.post.wall.wallpostapp.model.User;
import com.post.wall.wallpostapp.utility.CircleImageView;
import com.post.wall.wallpostapp.utility.Constants;
//import com.post.wall.wallpostapp.utility.FeedImageView;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.ImageGridAdapter;
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
import java.util.Random;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder>{
//
    PostListModel postListModel;
    Activity ctx;
    DisplayMetrics displayMetrics;
    int width, height;

    DisplayImageOptions doption1 = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.unknown_image)
            .showImageOnFail(R.drawable.unknown_image)
            .showStubImage(R.drawable.unknown_image)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    DisplayImageOptions doptionVideo = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.unknown_video)
            .showImageOnFail(R.drawable.unknown_video)
            .showStubImage(R.drawable.unknown_video)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    DisplayImageOptions doption = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.img_profile)
            .showImageOnFail(R.drawable.img_profile)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();


    com.android.volley.toolbox.ImageLoader imageLoader = UILApplication.getInstance().getImageLoader();


    RestInterface restInterface;
//    GetUrlClass url;
//    RestAdapter adapter;

    public PostListAdapter(Activity ctx, PostListModel newsItem) {
        super();
        this.ctx = ctx;
        this.postListModel = newsItem;

        displayMetrics = ctx.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        restInterface = Utility.getRetrofitInterface();
//        url = new GetUrlClass();
//        adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//        restInterface = adapter.create(RestInterface.class);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgUserImage, imgUserImageProfile;
        TextView txtUsername, txtTime, txtTitle, txtSharePost;//, txtMoreImage
        ImageView feedImageView;
        ImageView feedImage2, feedImage3, imgfeedImage1Thumbnail, imgfeedImage2Thumbnail, imgfeedImage3Thumbnail, imgOverlay;
        LinearLayout layoutTwoImage, layoutHeaderMain, layoutImagePost;
        RelativeLayout layoutOneImage;
        LinearLayout layoutCardMain, layoutTotalLike;

        LinearLayout layoutLike, layoutShare, layoutComment;
        TextView txtLikeCount, txtTotalShare, txtTotalComment, txtLikeUnlike;
        ImageView imgLikeUnlike, imgDummy;
        ProgressBar progress_like, progress_share;


        CircleImageView imgShareUser;
        LinearLayout layoutSharePostRow, SharelayoutImages, SharelayoutTwoImage;
        TextView txtShareUserName, txtSharePostText, txtShareTime, SharetxtMoreImage;
        RelativeLayout layoutShareOneImage, SharelayoutfeedImage2, SharelayoutfeedImage3;

        ImageView SharefeedImage1;
        ImageView ShareimgfeedImage1Thumbnail, ShareimgfeedImage2Thumbnail, SharefeedImage2, SharefeedImage3, ShareimgfeedImage3Thumbnail, imgReportSetting;
        RelativeLayout layoutOverlay;
        TextView txtImageCount;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutCardMain = (LinearLayout) itemView.findViewById(R.id.layoutCardMain);
            layoutTotalLike = (LinearLayout) itemView.findViewById(R.id.layoutTotalLike);
            imgUserImage = (CircleImageView) itemView.findViewById(R.id.imgUserImage);
            imgUserImageProfile = (CircleImageView) itemView.findViewById(R.id.imgUserImageProfile);
            txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
//            txtMoreImage = (TextView) itemView.findViewById(R.id.txtMoreImage);
            feedImageView = (ImageView) itemView.findViewById(R.id.feedImage1);

            feedImage2 = (ImageView) itemView.findViewById(R.id.feedImage2);
            feedImage3 = (ImageView) itemView.findViewById(R.id.feedImage3);

            layoutTwoImage = (LinearLayout) itemView.findViewById(R.id.layoutTwoImage);
            layoutOneImage = (RelativeLayout) itemView.findViewById(R.id.layoutOneImage);

            txtSharePost = (TextView) itemView.findViewById(R.id.txtSharePost);
            layoutHeaderMain = (LinearLayout) itemView.findViewById(R.id.layoutHeaderMain);
            layoutImagePost = (LinearLayout) itemView.findViewById(R.id.layoutImagePost);

            imgfeedImage1Thumbnail = (ImageView) itemView.findViewById(R.id.imgfeedImage1Thumbnail);
            imgfeedImage2Thumbnail = (ImageView) itemView.findViewById(R.id.imgfeedImage2Thumbnail);
            imgfeedImage3Thumbnail = (ImageView) itemView.findViewById(R.id.imgfeedImage3Thumbnail);

            imgDummy = (ImageView) itemView.findViewById(R.id.imgDummy);
            layoutLike = (LinearLayout) itemView.findViewById(R.id.layoutLike);
            layoutShare = (LinearLayout) itemView.findViewById(R.id.layoutShare);
            layoutComment = (LinearLayout) itemView.findViewById(R.id.layoutComment);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
            txtTotalShare = (TextView) itemView.findViewById(R.id.txtTotalShare);
            txtTotalComment = (TextView) itemView.findViewById(R.id.txtTotalComment);
            txtLikeUnlike = (TextView) itemView.findViewById(R.id.txtLikeUnlike);
            imgLikeUnlike = (ImageView) itemView.findViewById(R.id.imgLikeUnlike);
            progress_like = (ProgressBar) itemView.findViewById(R.id.progress_like);
            progress_share = (ProgressBar) itemView.findViewById(R.id.progress_share);




            layoutSharePostRow = (LinearLayout) itemView.findViewById(R.id.layoutSharePostRow);
            imgShareUser = (CircleImageView) itemView.findViewById(R.id.imgShareUser);
            txtShareUserName = (TextView) itemView.findViewById(R.id.txtShareUserName);
            txtShareTime = (TextView) itemView.findViewById(R.id.txtShareTime);
            txtSharePostText = (TextView) itemView.findViewById(R.id.txtSharePostText);
            SharelayoutImages = (LinearLayout) itemView.findViewById(R.id.SharelayoutImages);
            layoutShareOneImage = (RelativeLayout) itemView.findViewById(R.id.layoutShareOneImage);
            SharefeedImage1 = (ImageView) itemView.findViewById(R.id.SharefeedImage1);
            ShareimgfeedImage1Thumbnail = (ImageView) itemView.findViewById(R.id.ShareimgfeedImage1Thumbnail);
            SharelayoutTwoImage = (LinearLayout) itemView.findViewById(R.id.SharelayoutTwoImage);
            SharelayoutfeedImage2 = (RelativeLayout) itemView.findViewById(R.id.SharelayoutfeedImage2);
            SharefeedImage2 = (ImageView) itemView.findViewById(R.id.SharefeedImage2);
            ShareimgfeedImage2Thumbnail = (ImageView) itemView.findViewById(R.id.ShareimgfeedImage2Thumbnail);
            SharelayoutfeedImage3 = (RelativeLayout) itemView.findViewById(R.id.SharelayoutfeedImage3);
            SharefeedImage3 = (ImageView) itemView.findViewById(R.id.SharefeedImage3);
            ShareimgfeedImage3Thumbnail = (ImageView) itemView.findViewById(R.id.ShareimgfeedImage3Thumbnail);
            SharetxtMoreImage = (TextView) itemView.findViewById(R.id.SharetxtMoreImage);

            imgReportSetting = (ImageView) itemView.findViewById(R.id.imgReportSetting);

            layoutOverlay = (RelativeLayout) itemView.findViewById(R.id.layoutOverlay);
            txtImageCount = (TextView) itemView.findViewById(R.id.txtImageCount);
            imgOverlay = (ImageView) itemView.findViewById(R.id.imgOverlay);
        }
    }

    @Override
    public int getItemCount() {
        if(postListModel.getPosts().size() == 0)
            return 1;
        else
            return postListModel.getPosts().size();
    }

    @Override
    public long getItemId(int position) {
        if(postListModel.getPosts().size() == 0)
            return 0;
        else
            return postListModel.getPosts().indexOf(getItemId(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_list_row, viewGroup, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        if(position == 0){
            Log.v("TTT", "Header postCommentsListModel 11111 VISIBLE ..." + position);
            viewHolder.layoutHeaderMain.setVisibility(View.VISIBLE);
            try {
                com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                imageLoader.displayImage(HomeActivity.user.getUser().getProfilePic(), viewHolder.imgUserImageProfile, doption);
            } catch (Exception e) {
                e.printStackTrace();
            }

            viewHolder.txtSharePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, GalleryPostScrollActivity.class);
                    i.putExtra("poststatus", true);
                    ctx.startActivityForResult(i, Constants.START_GALLERY_POST);
                    ctx.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
            });

            viewHolder.layoutImagePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, GalleryPostScrollActivity.class);
                    i.putExtra("poststatus", true);
                    ctx.startActivityForResult(i, Constants.START_GALLERY_POST);
                    ctx.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
            });

        }else{
            Log.v("TTT", "Header postCommentsListModel 11111 GONE ..."+ position);
            viewHolder.layoutHeaderMain.setVisibility(View.GONE);
        }

        viewHolder.layoutCardMain.setVisibility(View.GONE);




        Log.v("TTT", "postCommentsListModel 11111..." + postListModel.getPosts().size() );
        if(postListModel.getPosts().size() == 0){
            Log.v("TTT", "Card GONE postCommentsListModel 11111..."  );
            viewHolder.layoutCardMain.setVisibility(View.GONE);
        }else{
//        if(postCommentsListModel.getPosts().size() > 0){
            Log.v("TTT", "Card VISIBLE postCommentsListModel 11111..."  );
            Log.v("TTT", "postCommentsListModel size..." + postListModel.getPosts().size());


            viewHolder.layoutCardMain.setVisibility(View.VISIBLE);


            viewHolder.txtUsername.setText(postListModel.getPosts().get(position).getUser().getFirstName() + " " + postListModel.getPosts().get(position ).getUser().getLastName());

            if(postListModel.getPosts().get(position ).getPost().getPostText() != null && postListModel.getPosts().get(position ).getPost().getPostText().toString().length() >0){
                viewHolder.txtTitle.setText(postListModel.getPosts().get(position ).getPost().getPostText());
                viewHolder.txtTitle.setVisibility(View.VISIBLE);
            }else
                viewHolder.txtTitle.setVisibility(View.GONE);

            viewHolder.txtTime.setText(Utility.getLeftDayTime(postListModel.getPosts().get(position ).getPost().getPostedDate()));

            try {
                com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                imageLoader.displayImage(postListModel.getPosts().get(position ).getUser().getProfilePic(), viewHolder.imgUserImage, doption);

                viewHolder.imgUserImage.setTag(position);
                viewHolder.imgUserImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int itemPosition = Integer.parseInt(v.getTag().toString());
                        startUserProfile(postListModel.getPosts().get(itemPosition).getUser());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            viewHolder.txtUsername.setTag(position);
            viewHolder.txtUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = Integer.parseInt(v.getTag().toString());
                    startUserProfile(postListModel.getPosts().get(itemPosition).getUser());
                }
            });

            if(postListModel.getPosts().get(position).getPostImageVideos() != null && (postListModel.getPosts().get(position).getPostImageVideos().size() != 0)){
                viewHolder.txtTitle.setTextSize(14);
                viewHolder.txtTitle.setPadding(0, 0, 0, 0);
            }else{
                viewHolder.txtTitle.setTextSize(20);
                viewHolder.txtTitle.setPadding(10, 10, 10, 10);
            }


            viewHolder.layoutOneImage.setVisibility(View.GONE);
            viewHolder.imgfeedImage1Thumbnail.setVisibility(View.GONE);
            viewHolder.layoutTwoImage.setVisibility(View.GONE);
            viewHolder.imgfeedImage2Thumbnail.setVisibility(View.GONE);
            viewHolder.imgfeedImage3Thumbnail.setVisibility(View.GONE);
//            viewHolder.txtMoreImage.setVisibility(View.GONE);
            viewHolder.layoutOverlay.setVisibility(View.GONE);
//            if(!postCommentsListModel.getPosts().get(position).getPost().isShare()){
                if (postListModel.getPosts().get(position ).getPostImageVideos() != null) {
                    if (postListModel.getPosts().get(position).getPostImageVideos().size() != 0) {
                        if(postListModel.getPosts().get(position).getPostImageVideos().size() > 3){
                            viewHolder.layoutOverlay.setVisibility(View.VISIBLE);
                            viewHolder.txtImageCount.setText("+" + (postListModel.getPosts().get(position).getPostImageVideos().size() - 3));
                        }

                        Log.v("TTT", "photo image size...." + postListModel.getPosts().get(position).getPostImageVideos().size());
                        if (postListModel.getPosts().get(position).getPostImageVideos().size() == 1) {
                            viewHolder.layoutOneImage.setVisibility(View.VISIBLE);
                            ((View) viewHolder.feedImageView.getParent()).setVisibility(View.VISIBLE);

                            viewHolder.feedImageView.setVisibility(View.VISIBLE);
                            if(postListModel.getPosts().get(position).getPostImageVideos().get(0).getIsImageVideo() == 1){
//                                viewHolder.feedImageView.setImageUrl(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), imageLoader);
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), viewHolder.feedImageView, doption1
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), loadedImage);
                                            }
                                        });
                                viewHolder.feedImageView.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
                            }else{
//                                viewHolder.feedImageView.setImageUrl(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), imageLoader);
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), viewHolder.feedImageView, doption1
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), loadedImage);
                                            }
                                        });
                                viewHolder.feedImageView.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
                                viewHolder.imgfeedImage1Thumbnail.setVisibility(View.VISIBLE);
                            }

                            viewHolder.feedImageView.setAdjustViewBounds(true);

                            viewHolder.feedImageView.setTag(0);
                            viewHolder.feedImageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.v("TTT", "feedImage1 click111222");
                                    int itemPosition = (Integer) v.getTag();
                                    showPhotoSlideShow(itemPosition, position);

                                }
                            });
                        } else if (postListModel.getPosts().get(position).getPostImageVideos().size() == 2) {
                            viewHolder.layoutTwoImage.setVisibility(View.VISIBLE);
                            viewHolder.feedImage2.setVisibility(View.VISIBLE);
                            viewHolder.feedImage3.setVisibility(View.VISIBLE);

                            viewHolder.feedImage2.setTag(0);
                            if(postListModel.getPosts().get(position).getPostImageVideos().get(0).getIsImageVideo() == 1){
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), viewHolder.feedImage2, doption1
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), loadedImage);
                                            }
                                        });

                                viewHolder.feedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
                            }else{
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), viewHolder.feedImage2, doptionVideo
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), loadedImage);
                                            }
                                        });
                                viewHolder.feedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
                                viewHolder.imgfeedImage2Thumbnail.setVisibility(View.VISIBLE);
                            }


                            viewHolder.feedImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int itemPosition = (Integer) v.getTag();
                                    showPhotoSlideShow(itemPosition, position);
                                }
                            });

                            viewHolder.feedImage3.setTag(1);
                            if(postListModel.getPosts().get(position).getPostImageVideos().get(1).getIsImageVideo() == 1){
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(1).getPostContent(), viewHolder.feedImage3, doption1
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(1).getPostContent(), loadedImage);
                                            }
                                        });
                                viewHolder.feedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
                            }else{
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(1).getVideoThumbnail(), viewHolder.feedImage3, doptionVideo
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(1).getVideoThumbnail(), loadedImage);
                                            }
                                        });
                                viewHolder.feedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
                                viewHolder.imgfeedImage3Thumbnail.setVisibility(View.VISIBLE);
                            }

                            viewHolder.feedImage3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int itemPosition = (Integer) v.getTag();
                                    showPhotoSlideShow(itemPosition, position);
                                }
                            });

                            viewHolder.feedImage3.getLayoutParams().height = width / 2;
                            viewHolder.feedImage3.setMinimumHeight(width / 2);
                            viewHolder.feedImage3.setMaxHeight(width / 2);

                            viewHolder.imgOverlay.getLayoutParams().height = width / 2;
                            viewHolder.imgOverlay.setMinimumHeight(width / 2);
                            viewHolder.imgOverlay.setMaxHeight(width / 2);

                            viewHolder.feedImage2.getLayoutParams().height = width / 2;
                            viewHolder.feedImage2.setMinimumHeight(width / 2);
                            viewHolder.feedImage2.setMaxHeight(width / 2);

                        }

                        else if(postListModel.getPosts().get(position).getPostImageVideos().size() >= 3){
                            Log.v("TTT", "p3333....");
                            viewHolder.layoutOneImage.setVisibility(View.VISIBLE);
                            viewHolder.layoutTwoImage.setVisibility(View.VISIBLE);
                            viewHolder.feedImage2.setVisibility(View.VISIBLE);
                            viewHolder.feedImage3.setVisibility(View.VISIBLE);

                            viewHolder.feedImageView.setTag(0);

                            if(postListModel.getPosts().get(position).getPostImageVideos().get(0).getIsImageVideo() == 1){
                                viewHolder.feedImageView.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
//                                viewHolder.feedImageView.setImageUrl(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), imageLoader);
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), viewHolder.feedImageView, doption1
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), loadedImage);
                                            }
                                        });
                            }else{
                                viewHolder.feedImageView.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
//                                viewHolder.feedImageView.setImageUrl(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), imageLoader);
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), viewHolder.feedImageView, doption1
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), loadedImage);
                                            }
                                        });
                                viewHolder.imgfeedImage1Thumbnail.setVisibility(View.VISIBLE);
                            }
                            viewHolder.feedImageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.v("TTT", "feedImage1 click111222");
                                    int itemPosition = (Integer) v.getTag();
                                    showPhotoSlideShow(itemPosition, position);

                                }
                            });

                            viewHolder.feedImage2.setTag(1);
                            if(postListModel.getPosts().get(position).getPostImageVideos().get(1).getIsImageVideo() == 1){
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(1).getPostContent(), viewHolder.feedImage2, doption1
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(1).getPostContent(), loadedImage);
                                            }
                                        });
                                viewHolder.feedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
                            }else{
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(1).getVideoThumbnail(), viewHolder.feedImage2, doptionVideo
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(1).getVideoThumbnail(), loadedImage);
                                            }
                                        });
                                viewHolder.feedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
                                viewHolder.imgfeedImage2Thumbnail.setVisibility(View.VISIBLE);
                            }

                            viewHolder.feedImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int itemPosition = (Integer) v.getTag();
                                    showPhotoSlideShow(itemPosition, position);
                                }
                            });

                            viewHolder.feedImage3.setTag(2);
                            if(postListModel.getPosts().get(position).getPostImageVideos().get(2).getIsImageVideo() == 1){
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(2).getPostContent(), viewHolder.feedImage3, doption1
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(2).getPostContent(), loadedImage);
                                            }
                                        });
                                viewHolder.feedImage3.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
                            }else{
                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(2).getVideoThumbnail(), viewHolder.feedImage3, doptionVideo
                                        , new SimpleImageLoadingListener() {
                                            @Override
                                            public void onLoadingStarted(String imageUri, View view) {
                                            }

                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(2).getVideoThumbnail(), loadedImage);
                                            }
                                        });
                                viewHolder.feedImage3.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
                                viewHolder.imgfeedImage3Thumbnail.setVisibility(View.VISIBLE);
                            }

                            viewHolder.feedImage3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int itemPosition = (Integer) v.getTag();
                                    showPhotoSlideShow(itemPosition, position);
                                }
                            });

                            viewHolder.feedImage3.getLayoutParams().height = width / 2;
                            viewHolder.feedImage3.setMinimumHeight(width / 2);
                            viewHolder.feedImage3.setMaxHeight(width / 2);

                            viewHolder.imgOverlay.getLayoutParams().height = width / 2;
                            viewHolder.imgOverlay.setMinimumHeight(width / 2);
                            viewHolder.imgOverlay.setMaxHeight(width / 2);

                            viewHolder.feedImage2.getLayoutParams().height = width / 2;
                            viewHolder.feedImage2.setMinimumHeight(width / 2);
                            viewHolder.feedImage2.setMaxHeight(width / 2);


                        }

//                        if (postListModel.getPosts().get(position).getPostImageVideos().size() >= 4) {
//                            viewHolder.feedImage2.setVisibility(View.VISIBLE);
//                            viewHolder.feedImage3.setVisibility(View.VISIBLE);
//                            viewHolder.txtMoreImage.setVisibility(View.VISIBLE);
//                            viewHolder.txtMoreImage.setTag(position);
//                            viewHolder.txtMoreImage.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int itemPosition = (Integer) v.getTag();
//                                    showPhotoSlideShow(0, position);
//                                }
//                            });
//                        } else {
//                            viewHolder.txtMoreImage.setVisibility(View.GONE);
//                        }
                    }
                }
//            }



            viewHolder.imgLikeUnlike.setImageDrawable(postListModel.getPosts().get(position).getPost().isLike() ? ctx.getResources().getDrawable(R.drawable.like_active) : ctx.getResources().getDrawable(R.drawable.like));
            viewHolder.txtLikeUnlike.setTextColor(postListModel.getPosts().get(position).getPost().isLike() ? ctx.getResources().getColor(R.color.colorPrimary) : ctx.getResources().getColor(R.color.grey_time));
            viewHolder.txtLikeCount.setText(postListModel.getPosts().get(position).getPost().getLikeCount()+"");

            if(postListModel.getPosts().get(position).getPost().getShareCount() > 0) {
                viewHolder.txtTotalShare.setText(postListModel.getPosts().get(position).getPost().getShareCount()+" Shares");
            }

            if(postListModel.getPosts().get(position).getPost().getCommentCount() > 0) {
                viewHolder.txtTotalComment.setText(postListModel.getPosts().get(position).getPost().getCommentCount() + " Comments");
            }

            Log.v("TTTT", "Post id...." + postListModel.getPosts().get(position).getPost().getPostId());
            viewHolder.layoutTotalLike.setTag(position);
            viewHolder.layoutTotalLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = Integer.parseInt(v.getTag().toString());
                    Intent intent = new Intent(ctx, LikeListActivity.class);
                    Log.v("TTTT", "Post id...." + postListModel.getPosts().get(itemPosition).getPost().getPostId());
                    intent.putExtra("PostId", postListModel.getPosts().get(itemPosition).getPost().getPostId());
                    Log.v("TTTT", "LikeCount...." + postListModel.getPosts().get(itemPosition).getPost().getLikeCount());
                    intent.putExtra("LikeCount", postListModel.getPosts().get(itemPosition).getPost().getLikeCount());
                    ctx.startActivity(intent);
                    ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            });

            viewHolder.layoutLike.setTag(position);
            viewHolder.layoutLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = Integer.parseInt(v.getTag().toString());
                    viewHolder.progress_like.setVisibility(View.VISIBLE);
                    likePost(postListModel.getPosts().get(position).getPost().getPostId(), position, viewHolder);
                }
            });

            viewHolder.layoutComment.setTag(position);
            viewHolder.layoutComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int position = Integer.parseInt(v.getTag().toString());
//                    Intent intent = new Intent(ctx, PostCommentListActivity.class);
//                    intent.putExtra("Post", new Gson().toJson(postListModel.getPosts().get(position)));
//                    ctx.startActivity(intent);
//                    ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                    Intent intent = new Intent(ctx, PostCommentListActivity.class);
                    PostCommentListActivity.post = postListModel.getPosts().get(position);
                    ctx.startActivityForResult(intent, Constants.COMMENT_POST);
                    ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                }
            });

            viewHolder.progress_share.setVisibility(View.GONE);
            viewHolder.layoutShare.setTag(position);
            viewHolder.layoutShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int permission = Utility.checkExternalPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_REQUEST_CODE);
                    } else {
                        viewHolder.progress_share.setVisibility(View.VISIBLE);

                        uris = new ArrayList<>();
                        for(int i=0; i<postListModel.getPosts().get(position).getPostImageVideos().size(); i++){

                            Bitmap loadedImage = Utility.getBitmapFromMemCache(postListModel.getPosts().get(position).getPostImageVideos().get(i).getPostContent());
                            if(loadedImage == null){
                                new downloadAsync(viewHolder, position, i).execute();
                            }else{
                                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                loadedImage.compress(Bitmap.CompressFormat.WEBP, 100, bytes);
                                String path = MediaStore.Images.Media.insertImage(ctx.getContentResolver(), loadedImage, "Title", null);
                                if(path != null){
                                    Uri uri = Uri.parse(path);
                                    uris.add(uri);
                                }

                            }
                        }

                        Log.v("TTT", postListModel.getPosts().get(position).getPostImageVideos().size() + ".....MAiN uris size.............." + uris.size());
                        if(uris.size() == postListModel.getPosts().get(position).getPostImageVideos().size()){
                            viewHolder.progress_share.setVisibility(View.GONE);
                            sharePost(postListModel.getPosts().get(position).getPost().getPostText());
                        }
                    }

                }
            });

            //Share card.................................................................

            //TODO
            //TODO
            //TODO
            //TODO

//            if(postCommentsListModel.getPosts().get(position).getPost().isShare()){
//                viewHolder.layoutSharePostRow.setVisibility(View.VISIBLE);
//
//
//                if(postCommentsListModel.getPosts().get(position).getShareUser() != null){
//                    viewHolder.txtShareUserName.setText(postCommentsListModel.getPosts().get(position).getShareUser().getFirstName() + " " + postCommentsListModel.getPosts().get(position).getShareUser().getLastName());
//                    viewHolder.txtSharePostText.setText(postCommentsListModel.getPosts().get(position).getSharedPost().getPostText());
//                    viewHolder.txtShareTime.setText(Utility.getLeftDayTime(postCommentsListModel.getPosts().get(position).getSharedPost().getPostedDate()));
//
//                    try {
//                        com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
//                        imageLoader.displayImage(postCommentsListModel.getPosts().get(position).getShareUser().getProfilePic(), viewHolder.imgShareUser, doption);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if(postCommentsListModel.getPosts().get(position).getPostImageVideos() != null && (postCommentsListModel.getPosts().get(position).getPostImageVideos().size() != 0)){
//                    viewHolder.txtSharePostText.setTextSize(14);
//                    viewHolder.txtSharePostText.setPadding(0, 0, 0, 0);
//                }else{
//                    viewHolder.txtSharePostText.setTextSize(20);
//                    viewHolder.txtSharePostText.setPadding(10, 10, 10, 10);
//                }
//
//
//
//                viewHolder.layoutShareOneImage.setVisibility(View.GONE);
//                viewHolder.ShareimgfeedImage1Thumbnail.setVisibility(View.GONE);
//                viewHolder.SharelayoutTwoImage.setVisibility(View.GONE);
//                viewHolder.ShareimgfeedImage2Thumbnail.setVisibility(View.GONE);
//                viewHolder.ShareimgfeedImage3Thumbnail.setVisibility(View.GONE);
//                viewHolder.SharetxtMoreImage.setVisibility(View.GONE);
//                Log.v("TTT", position + "....position.." + postCommentsListModel.getPosts().get(position).getPostImageVideos().size());
//                if (postCommentsListModel.getPosts().get(position).getPostImageVideos() != null) {
//                    if (postCommentsListModel.getPosts().get(position).getPostImageVideos().size() != 0) {
//                        viewHolder.SharelayoutImages.setVisibility(View.VISIBLE);
//
//                        Log.v("TTT", "photo image size...." + postCommentsListModel.getPosts().get(position).getPostImageVideos().size());
//                        if (postCommentsListModel.getPosts().get(position).getPostImageVideos().size() == 1) {
//                            viewHolder.layoutShareOneImage.setVisibility(View.VISIBLE);
//                            ((View) viewHolder.SharefeedImage1.getParent()).setVisibility(View.VISIBLE);
//
//                            viewHolder.SharefeedImage1.setVisibility(View.VISIBLE);
//                            if(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getIsImageVideo() == 1){
//                                viewHolder.SharefeedImage1.setImageUrl(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), imageLoader);
//                                viewHolder.SharefeedImage1.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
//                            }else{
//                                viewHolder.SharefeedImage1.setImageUrl(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), imageLoader);
//                                viewHolder.SharefeedImage1.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
//                                viewHolder.ShareimgfeedImage1Thumbnail.setVisibility(View.VISIBLE);
//                            }
//
//                            viewHolder.SharefeedImage1.setAdjustViewBounds(true);
//
//                            viewHolder.SharefeedImage1.setTag(0);
//                            viewHolder.SharefeedImage1.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Log.v("TTT", "feedImage1 click111222");
//                                    int itemPosition = (Integer) v.getTag();
//                                    showPhotoSlideShow(itemPosition, position);
//
//                                }
//                            });
//                        } else if (postCommentsListModel.getPosts().get(position).getPostImageVideos().size() == 2) {
//                            viewHolder.SharelayoutTwoImage.setVisibility(View.VISIBLE);
//                            viewHolder.SharefeedImage2.setVisibility(View.VISIBLE);
//                            viewHolder.SharefeedImage3.setVisibility(View.VISIBLE);
//
//                            viewHolder.SharefeedImage2.setTag(0);
//                            if(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getIsImageVideo() == 1){
//                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), viewHolder.SharefeedImage2, doption1);
//                                viewHolder.SharefeedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
//                            }else{
//                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), viewHolder.SharefeedImage2, doptionVideo);
//                                viewHolder.SharefeedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
//                                viewHolder.ShareimgfeedImage2Thumbnail.setVisibility(View.VISIBLE);
//                            }
//
//
//                            viewHolder.SharefeedImage2.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int itemPosition = (Integer) v.getTag();
//                                    showPhotoSlideShow(itemPosition, position);
//                                }
//                            });
//
//                            viewHolder.SharefeedImage3.setTag(1);
//                            if(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(1).getIsImageVideo() == 1){
//                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(1).getPostContent(), viewHolder.SharefeedImage3, doption1);
//                                viewHolder.SharefeedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
//                            }else{
//                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(1).getVideoThumbnail(), viewHolder.SharefeedImage3, doptionVideo);
//                                viewHolder.SharefeedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
//                                viewHolder.ShareimgfeedImage3Thumbnail.setVisibility(View.VISIBLE);
//                            }
//
//                            viewHolder.SharefeedImage3.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int itemPosition = (Integer) v.getTag();
//                                    showPhotoSlideShow(itemPosition, position);
//                                }
//                            });
//
//                            viewHolder.SharefeedImage3.getLayoutParams().height = width / 2;
//                            viewHolder.SharefeedImage3.setMinimumHeight(width / 2);
//                            viewHolder.SharefeedImage3.setMaxHeight(width / 2);
//
//                            viewHolder.SharefeedImage2.getLayoutParams().height = width / 2;
//                            viewHolder.SharefeedImage2.setMinimumHeight(width / 2);
//                            viewHolder.SharefeedImage2.setMaxHeight(width / 2);
//
//                        }
//
//                        else if(postCommentsListModel.getPosts().get(position).getPostImageVideos().size() >= 3){
//                            Log.v("TTT", "p3333....");
//                            viewHolder.layoutShareOneImage.setVisibility(View.VISIBLE);
//                            viewHolder.SharelayoutTwoImage.setVisibility(View.VISIBLE);
//                            viewHolder.SharefeedImage2.setVisibility(View.VISIBLE);
//                            viewHolder.SharefeedImage3.setVisibility(View.VISIBLE);
//
//                            viewHolder.SharefeedImage1.setTag(0);
//
//                            if(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getIsImageVideo() == 1){
//                                viewHolder.SharefeedImage1.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
//                                viewHolder.SharefeedImage1.setImageUrl(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getPostContent(), imageLoader);
//                            }else{
//                                viewHolder.SharefeedImage1.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
//                                viewHolder.SharefeedImage1.setImageUrl(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(0).getVideoThumbnail(), imageLoader);
//                                viewHolder.ShareimgfeedImage1Thumbnail.setVisibility(View.VISIBLE);
//                            }
//                            viewHolder.SharefeedImage1.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Log.v("TTT", "feedImage1 click111222");
//                                    int itemPosition = (Integer) v.getTag();
//                                    showPhotoSlideShow(itemPosition, position);
//
//                                }
//                            });
//
//                            viewHolder.SharefeedImage2.setTag(1);
//                            if(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(1).getIsImageVideo() == 1){
//                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(1).getPostContent(), viewHolder.SharefeedImage2, doption1);
//                                viewHolder.SharefeedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
//                            }else{
//                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(1).getVideoThumbnail(), viewHolder.SharefeedImage2, doptionVideo);
//                                viewHolder.SharefeedImage2.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
//                                viewHolder.ShareimgfeedImage2Thumbnail.setVisibility(View.VISIBLE);
//                            }
//
//                            viewHolder.SharefeedImage2.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int itemPosition = (Integer) v.getTag();
//                                    showPhotoSlideShow(itemPosition, position);
//                                }
//                            });
//
//                            viewHolder.SharefeedImage3.setTag(2);
//                            if(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(2).getIsImageVideo() == 1){
//                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(2).getPostContent(), viewHolder.SharefeedImage3, doption1);
//                                viewHolder.SharefeedImage3.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_image));
//                            }else{
//                                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(2).getVideoThumbnail(), viewHolder.SharefeedImage3, doptionVideo);
//                                viewHolder.SharefeedImage3.setImageDrawable(ctx.getResources().getDrawable(R.drawable.unknown_video));
//                                viewHolder.ShareimgfeedImage3Thumbnail.setVisibility(View.VISIBLE);
//                            }
//
//                            viewHolder.SharefeedImage3.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int itemPosition = (Integer) v.getTag();
//                                    showPhotoSlideShow(itemPosition, position);
//                                }
//                            });
//
//                            viewHolder.SharefeedImage3.getLayoutParams().height = width / 2;
//                            viewHolder.SharefeedImage3.setMinimumHeight(width / 2);
//                            viewHolder.SharefeedImage3.setMaxHeight(width / 2);
//
//                            viewHolder.SharefeedImage2.getLayoutParams().height = width / 2;
//                            viewHolder.SharefeedImage2.setMinimumHeight(width / 2);
//                            viewHolder.SharefeedImage2.setMaxHeight(width / 2);
//
//
//                        }
//
//                        if (postCommentsListModel.getPosts().get(position).getPostImageVideos().size() >= 4) {
//                            viewHolder.SharefeedImage2.setVisibility(View.VISIBLE);
//                            viewHolder.SharefeedImage3.setVisibility(View.VISIBLE);
//                            viewHolder.SharetxtMoreImage.setVisibility(View.VISIBLE);
//                            viewHolder.SharetxtMoreImage.setTag(position);
//                            viewHolder.SharetxtMoreImage.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int itemPosition = (Integer) v.getTag();
//                                    showPhotoSlideShow(0, position);
//                                }
//                            });
//                        } else {
//                            viewHolder.SharetxtMoreImage.setVisibility(View.GONE);
//                        }
//                    }
//                }
//
//
//
//
//            }
//            else
                viewHolder.layoutSharePostRow.setVisibility(View.GONE);


            if(postListModel.getPosts().get(position).getPost().isReport()){
                viewHolder.imgReportSetting.setVisibility(View.GONE);
            }else{
                viewHolder.imgReportSetting.setVisibility(View.VISIBLE);
            }
            viewHolder.imgReportSetting.setTag(position);
            viewHolder.imgReportSetting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int itemPosition = (Integer) v.getTag();
                            ArrayList<String> menuFilterList;
                            menuFilterList = new ArrayList<>();
                            menuFilterList.add("Report");

                            dropdownPopupWindow(v, menuFilterList, itemPosition);
                        }
                    });

        }

    }


    ArrayList<Uri> uris;
    public void downloadImageForShare(final ViewHolder viewHolder, final int position, final int itemPosition){
        Log.v("TTT", "downloadImageForShare...............................................................................");
            try{
            Log.v("TTT", postListModel.getPosts().get(position).getPost().getPostText() + ".....111Share dwn.  downloadImageForShare........." + postListModel.getPosts().get(position).getPostImageVideos().get(itemPosition).getPostContent());
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(postListModel.getPosts().get(position).getPostImageVideos().get(itemPosition).getPostContent(),
                    viewHolder.imgDummy , doption1,
                    new SimpleImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            Log.v("TTT", "Share dwn........onLoadingStarted..");
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            Log.v("TTT", "Share dwn......onLoadingComplete...." + postListModel.getPosts().get(position).getPost().getPostText());
//                            viewHolder.progress_share.setVisibility(View.GONE);
                            Utility.addBitmapToMemoryCache(postListModel.getPosts().get(position).getPostImageVideos().get(itemPosition).getPostContent(), loadedImage);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            loadedImage.compress(Bitmap.CompressFormat.WEBP, 100, bytes);
                            String path = MediaStore.Images.Media.insertImage(ctx.getContentResolver(), loadedImage, "Title", null);
                            Uri uri = Uri.parse(path);
                            uris.add(uri);

                            Log.v("TTT", "downloadImageForShare uris size.............." + uris.size());
                            if(uris.size() == postListModel.getPosts().get(position).getPostImageVideos().size()){
                                viewHolder.progress_share.setVisibility(View.GONE);
                                sharePost(postListModel.getPosts().get(position).getPost().getPostText());
                            }


                        }
                    });

        }catch (Exception e){

        }
    }


    public class downloadAsync extends AsyncTask<Object, Object, String> {
        ViewHolder viewHolder;
        int position, itemPosition;

        public downloadAsync(ViewHolder viewHolder, int position, int itemPosition) {
            this.viewHolder = viewHolder;
            this.position = position;
            this.itemPosition = itemPosition;
        }

        @Override
        protected void onPreExecute() {
            Log.v("TTT", "downloadImageForShare...............................................................................");
        }

        @Override
        protected String doInBackground(Object... params) {
            Log.v("TTT", "Share dwn........onLoadingStarted......." + itemPosition);
            if(postListModel.getPosts().get(position).getPostImageVideos().get(itemPosition).getIsImageVideo() == 1)
                return getBitmapFromImageURL(postListModel.getPosts().get(position).getPostImageVideos().get(itemPosition).getPostContent());
            else
                return getBitmapFromVideoURL(postListModel.getPosts().get(position).getPostImageVideos().get(itemPosition).getPostContent());
        }

        @Override
        protected void onPostExecute(String path) {
            Log.v("TTT", "Share dwn......onLoadingComplete...." + postListModel.getPosts().get(position).getPost().getPostText());

//            if(Utility.getBitmapFromMemCache(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(itemPosition).getPostContent()) == null)
//                Utility.addBitmapToMemoryCache(postCommentsListModel.getPosts().get(position).getPostImageVideos().get(itemPosition).getPostContent(), loadedImage);
            try{
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                loadedImage.compress(Bitmap.CompressFormat.WEBP, 100, bytes);
//                String path = MediaStore.Images.Media.insertImage(ctx.getContentResolver(), loadedImage, "Title", null);
                Log.v("TTT", "PATH...." + path);

                if(path != null){
                    Uri uri = Uri.parse(path);

                    Log.v("TTT", uri + "...uri Share click.....................111...videoUri.." + path);
                    if(postListModel.getPosts().get(position).getPostImageVideos().get(itemPosition).getIsImageVideo() == 2){

//                String path = "/storage/emulated/0/DCIM/Camera/VID_20170111_095109.mp4";
                        MediaScannerConnection.scanFile(ctx, new String[] { path },
                                null, new MediaScannerConnection.OnScanCompletedListener() {
                                    public void onScanCompleted(String path, Uri videoUri) {
                                        Log.v("TTT", path + " ...Share click..........Video...........222.. " + videoUri);


                                        uris.add(videoUri);

                                        Log.v("TTT", postListModel.getPosts().get(position).getPostImageVideos().size() + "....downloadImageForShare uris size....Video.........." + uris.size());
                                        if(uris.size() == postListModel.getPosts().get(position).getPostImageVideos().size()){

                                            ctx.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    viewHolder.progress_share.setVisibility(View.GONE);
                                                }
                                            });

                                            sharePost(postListModel.getPosts().get(position).getPost().getPostText());
                                        }


                                    }
                                });

                    }else{

                        uris.add(uri);

                        Log.v("TTT", postListModel.getPosts().get(position).getPostImageVideos().size() + "....downloadImageForShare uris size.............." + uris.size());
                        if(uris.size() == postListModel.getPosts().get(position).getPostImageVideos().size()){
                            viewHolder.progress_share.setVisibility(View.GONE);
                            sharePost(postListModel.getPosts().get(position).getPost().getPostText());
                        }
                    }
                }else{
                    Toast.makeText(ctx, "Error while sharing", Toast.LENGTH_SHORT).show();
                    viewHolder.progress_share.setVisibility(View.GONE);
                }




            }catch(Exception e){
                e.printStackTrace();
            }

//            super.onPostExecute(loadedImage);
        }
    }

    public String getBitmapFromImageURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);


            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.WEBP, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(ctx.getContentResolver(), myBitmap, "Title", null);
            Log.v("TTT", "PATH...." + path);

            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getBitmapFromVideoURL(String url) {

        String rootDir = Constants.WALL_POST_SDCARD_PATH;
        String fname = "Video111.mp4";
        File rootFile = new File(rootDir);
        if (rootFile.exists()) rootFile.delete();
        rootFile.mkdir();
        File mainFile = new File(rootFile, fname);
        if (mainFile.exists()) mainFile.delete();


        try {
//            URL url = new URL("http://t3.gstatic.com/images?q=tbn:ANd9GcQs0EPegqi56Alq4vCgC_lVDbZvJtk51RhER7AyDEVA3nUkzjMVK-yDHY3V-w"); //you can write here any link
//            File file = new File(fileName);


//            URL url = new URL(url);
            long startTime = System.currentTimeMillis();
//            tv.setText("Starting download......from " + url);
            URLConnection ucon = new URL(url).openConnection();
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
                    /*
                     * Read bytes to the Buffer until there is nothing more to read(-1).
                     */
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            FileOutputStream fos = new FileOutputStream(mainFile);
            fos.write(baf.toByteArray());
            fos.close();
//            tv.setText("Download Completed in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
        } catch (IOException e) {
//            tv.setText("Error: " + e);
        }

        return mainFile.getPath();
    }


//    private String getBitmapFromVideoURL(String fileURL) {
//        String rootDir = Environment.getExternalStorageDirectory() + File.separator + "Video";
//        File rootFile = new File(rootDir);
//        rootFile.mkdir();
//
//        File file = new File(rootFile, "Sample.mp4");
//        try {
//
//
//            URL url = new URL(fileURL);
//            HttpURLConnection c = (HttpURLConnection) url.openConnection();
//            c.setRequestMethod("GET");
//            c.setDoOutput(true);
//            c.connect();
//            FileOutputStream f = new FileOutputStream(new File(rootFile, "Sample.mp4"));
//            InputStream in = c.getInputStream();
//            byte[] buffer = new byte[1024];
//            int len1 = 0;
//            while ((len1 = in.read(buffer)) > 0) {
//                f.write(buffer, 0, len1);
//            }
//            f.close();
//        } catch (IOException e) {
//            Log.d("Error....", e.toString());
//        }
//        return file.getPath();
//    }


    public void sharePost(String postText){
        Log.v("TTT", "COMPLETE onLoadingComplete... uris.size()...........///////////." + uris.size());

        Intent shareIntent;
        if(uris.size() <= 1){
            shareIntent = new Intent(Intent.ACTION_SEND);
            if(uris.size() > 0)
                shareIntent.putExtra(Intent.EXTRA_STREAM, uris.get(0));
        }else{
            shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            if(uris.size() > 0)
                shareIntent.putExtra(Intent.EXTRA_STREAM, uris);
        }
//        final Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("*/*");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Wall Post");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, postText);

        ctx.startActivity(Intent.createChooser(shareIntent, "Share Deal"));

    }

    public void likePost(int postId, final int position, final ViewHolder viewHolder) {
        restInterface.LikeUnLikePost(HomeActivity.user.getUser().getUserId(), postId, new Callback<PostLikeModel>() {
            @Override
            public void failure(RetrofitError error) {
                viewHolder.progress_like.setVisibility(View.GONE);
                String message = error.getMessage();
                try {
                    boolean IsInternetConnected = checkInternetisAvailable.getInstance(ctx).check_internet();

                    Log.v("TTT", "SET ADAPTER");

                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Utility.showAlert(ctx, "Error: " + msg);
                        }
                    } else {
                        Utility.showAlert(ctx, Constants.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

//                swipyRefreshLayoutChatConversion.setRefreshing(false);

            }

            @Override
            public void success(PostLikeModel postLikeModel, Response arg1) {
                int code = postLikeModel.getError_code();
                viewHolder.progress_like.setVisibility(View.GONE);

                if (code == 0) {
                    postListModel.getPosts().get(position).getPost().setLike(true);
                    postListModel.getPosts().get(position).getPost().setLikeCount(postListModel.getPosts().get(position).getPost().getLikeCount() + 1);
                    notifyDataSetChanged();
                } if (code == 1) {
                    postListModel.getPosts().get(position).getPost().setLike(false);
                    postListModel.getPosts().get(position).getPost().setLikeCount(postListModel.getPosts().get(position).getPost().getLikeCount() - 1);
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(ctx, postLikeModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    protected void showPhotoSlideShow(int itemPosition, int postPosition) {
        Log.v("TTT", "showPhotoSlideShow........" + itemPosition);
        try {
            Intent intent = new Intent(ctx, AlbumPhotosSlideShowActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("Post", new Gson().toJson(postListModel.getPosts().get(postPosition)));
            ctx.startActivity(intent);
            ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startUserProfile(User user){
        Intent intent = new Intent(ctx, UserProfileActivity.class);
        intent.putExtra("User", new Gson().toJson(user));
        ctx.startActivity(intent);
        ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    ListPopupWindow dropDownPopup;
    public void dropdownPopupWindow(View anchor, final ArrayList<String> STRINGS, final int itemPosition) {
        dropDownPopup = new ListPopupWindow(ctx);
        dropDownPopup.setVerticalOffset(1);
        dropDownPopup.setHorizontalOffset(1);
        dropDownPopup.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.popup_back));

        PopupDeviceAdapter adapter = new PopupDeviceAdapter(ctx, R.layout.popupmenu_back, STRINGS);
        dropDownPopup.setAdapter(adapter);
        dropDownPopup.setAnchorView(anchor);

        FrameLayout fakeParent = new FrameLayout(ctx);
        View item = adapter.getView(0, null, fakeParent);
        item.measure(0, 0);
        dropDownPopup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        dropDownPopup.setWidth(300);

        try {
            if (((anchor.getRight() - anchor.getLeft()) != 0) && (Utility.getWidestView(ctx, adapter) > (anchor.getRight() - anchor.getLeft()))) {
                dropDownPopup.setWidth(Utility.getWidestView(ctx, adapter) + 100);
            } else {
                dropDownPopup.setWidth(anchor.getRight() - anchor.getLeft());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dropDownPopup.setWidth(300);
        }

        dropDownPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (STRINGS.get(position).equals("Report")) {
                    Log.v("TTT", "Report");
                    showReportDialog(itemPosition);
                }
                dropDownPopup.dismiss();
            }
        });
        dropDownPopup.show();

    }

    LinearLayout layoutMain;
    public void showReportDialog(final int itemPosition) {
        final Dialog dialog = new Dialog(ctx){
            public boolean dispatchTouchEvent(MotionEvent event) {
                View view = getCurrentFocus();
                boolean ret = super.dispatchTouchEvent(event);

                if (view instanceof EditText || view instanceof TextInputEditText) {
                    View w = getCurrentFocus();
                    int scrcoords[] = new int[2];
                    w.getLocationOnScreen(scrcoords);
                    float x = event.getRawX() + w.getLeft() - scrcoords[0];
                    float y = event.getRawY() + w.getTop() - scrcoords[1];

                    if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                        InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                        Log.v("isActive", "..." + imm.isActive());
                        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                        if(layoutMain != null)
                            layoutMain.requestFocus();
                    }
                }
                return ret;
            };
        };
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout2 = layoutInflater.inflate(R.layout.post_report_view, null);
        dialog.setContentView(layout2);
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setGravity(Gravity.TOP | Gravity.LEFT);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.copyFrom(window.getAttributes());
        window.setAttributes(params);

        layoutMain = (LinearLayout) dialog.findViewById(R.id.layoutMain);

//        final MaterialSpinner spinReportType = (MaterialSpinner) dialog.findViewById(R.id.spinReportType);
//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(activity, R.array.reportType,	R.layout.selected_spinner_item_dropdown);
//        adapter1.setDropDownViewResource(R.layout.social_photos_spinner_item);
//        spinReportType.setAdapter(adapter1);
        final TextInputEditText edtDescritpion = (TextInputEditText) dialog.findViewById(R.id.edtDescritpion);
        final TextInputLayout inputLayoutDescritpion = (TextInputLayout) dialog.findViewById(R.id.inputLayoutDescritpion);
        TextView txtSubmitReport = (TextView) dialog.findViewById(R.id.txtSubmitReport);

        txtSubmitReport.setTag(itemPosition);
        txtSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
//                if(spinReportType.getSelectedItemPosition() == 0) {
////							report(itemPosition, edtDescritpion.getText().toString(), "");
//                    Log.v("TTT", "spinCategory........." + spinReportType.getSelectedItemPosition() + "  " + spinReportType.getAdapter().getCount());
//                    spinReportType.setError(activity.getString(R.string.validation_question_category));
//                    count++;
//                }
                if (edtDescritpion.getText().toString().trim().length() <= 0) {
                    inputLayoutDescritpion.setErrorEnabled(true);
                    inputLayoutDescritpion.setError(ctx.getString(R.string.validation_report_description));
                    count++;
                }

                if(count == 0){
                    dialog.dismiss();
//                    spinReportType.setError(null);
                    inputLayoutDescritpion.setErrorEnabled(false);
                    addPostToReport(postListModel.getPosts().get(itemPosition).getPost().getPostId(), edtDescritpion.getText().toString(), itemPosition);

                }

            }
        });
        TextView txtReportCancel = (TextView) dialog.findViewById(R.id.txtReportCancel);
        txtReportCancel.setTextColor(ctx.getResources().getColor(R.color.black_header));
        txtReportCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    public void addPostToReport(int postId, String description, final int itemPosition) {
        if(ctx instanceof HomeActivity){
            ((HomeActivity)ctx).showProgress(true);
        }
        restInterface.addPostToReport(HomeActivity.user.getUser().getUserId(), postId, description, new Callback<PostLikeModel>() {
            @Override
            public void failure(RetrofitError error) {
                ((HomeActivity)ctx).showProgress(false);
                String message = error.getMessage();
                try {
                    boolean IsInternetConnected = checkInternetisAvailable.getInstance(ctx).check_internet();

                    Log.v("TTT", "SET ADAPTER");

                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Utility.showAlert(ctx, "Error: " + msg);
                        }
                    } else {
                        Utility.showAlert(ctx, Constants.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }

            @Override
            public void success(PostLikeModel postLikeModel, Response arg1) {
                int code = postLikeModel.getError_code();
                ((HomeActivity)ctx).showProgress(false);

                if (code == 0) {
                    postListModel.getPosts().get(itemPosition).getPost().setReport(true);
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(ctx, postLikeModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
