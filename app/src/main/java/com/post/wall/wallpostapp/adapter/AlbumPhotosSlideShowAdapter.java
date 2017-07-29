package com.post.wall.wallpostapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.post.wall.wallpostapp.R;
import com.post.wall.wallpostapp.VideoViewActivity;
import com.post.wall.wallpostapp.model.Post;
import com.post.wall.wallpostapp.utility.viewpager.TouchImageView;


public class AlbumPhotosSlideShowAdapter extends PagerAdapter {

    Activity context;
    public Post post;
    DisplayImageOptions doption = null;
    DisplayImageOptions doptionVideo = null;

    public AlbumPhotosSlideShowAdapter(Activity activity, Post albumPhotosModel) {
        this.context = activity;
        this.post = albumPhotosModel;

        doption = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.unknown_image_white)
                .showImageOnFail(R.drawable.unknown_image_white)
                .showStubImage(R.drawable.unknown_image_white)
                .resetViewBeforeLoading(true).cacheOnDisk(true)
                .cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        doptionVideo = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.unknown_video_white)
                .showImageOnFail(R.drawable.unknown_video_white)
                .showStubImage(R.drawable.unknown_video_white)
                .cacheInMemory(false)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return post.getPostImageVideos().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View item = inflater.inflate(R.layout.album_photos_slide_show_activity2, container, false);
        final TouchImageView crop_image = (TouchImageView) item.findViewById(R.id.crop_image);
        ImageView imgThumbnail = (ImageView) item.findViewById(R.id.imgThumbnail);
        imgThumbnail.setVisibility(View.GONE);
        RelativeLayout layoutImageView = (RelativeLayout) item.findViewById(R.id.layoutImageView);

        item.setTag(post.getPostImageVideos().get(position).getPostContent());

        Log.v("TTT", "ITEM..............." + post.getPostImageVideos().get(position).getPostContent());

        try {
            if(post.getPostImageVideos().get(position).getIsImageVideo() == 1){
                crop_image.setTag(post.getPostImageVideos().get(position).getPostContent());
                crop_image.setImageDrawable(context.getResources().getDrawable(R.drawable.unknown_image));
                com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                imageLoader.displayImage(post.getPostImageVideos().get(position).getPostContent(), crop_image, doption);
            }else{
                crop_image.setImageDrawable(context.getResources().getDrawable(R.drawable.unknown_video));
                com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                imageLoader.displayImage(post.getPostImageVideos().get(position).getVideoThumbnail(), crop_image, doptionVideo);
                imgThumbnail.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        crop_image.setTag(post.getPostImageVideos().get(position).getPostContent());
        crop_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(post.getPostImageVideos().get(position).getIsImageVideo() == 2){
                    Intent intent = new Intent(context, VideoViewActivity.class);
                    intent.putExtra("URL", view.getTag().toString());
                    context.startActivity(intent);
                    context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            }
        });

        container.addView(item, 0);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



}
