package com.post.wall.wallpostapp;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.post.wall.wallpostapp.adapter.AlbumPhotosSlideShowAdapter;
import com.post.wall.wallpostapp.model.Post;
import com.post.wall.wallpostapp.utility.CircleImageView;
import com.post.wall.wallpostapp.utility.viewpager.ExtendedViewPager;

public class AlbumPhotosSlideShowActivity extends ActionBarActivity {
	CircleImageView imgAlbumUser;
	public ExtendedViewPager pagerAlbumPhotoShow;
	public AlbumPhotosSlideShowAdapter albumPhotosSlideShowAdapter;

	int position;
	Post post;
	DisplayImageOptions doption = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.img_profile)
			.showImageOnFail(R.drawable.img_profile)
			.cacheInMemory(false)
			.cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.album_photo_slideshow_view);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.color.black));
		getSupportActionBar().setTitle("");

		if (Build.VERSION.SDK_INT >= 21) {
			getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));
		}

		position = getIntent().getIntExtra("position", 0);
		post = new Gson().fromJson(getIntent().getStringExtra("Post"), Post.class);

		imgAlbumUser = (CircleImageView) findViewById(R.id.imgAlbumUser);
		imgAlbumUser.setBorderWidth(0);
		try {
			com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
			imageLoader.displayImage(HomeActivity.user.getUser().getProfilePic(), imgAlbumUser, doption);
		} catch (Exception e) {
			e.printStackTrace();
		}

		pagerAlbumPhotoShow = (ExtendedViewPager) findViewById(R.id.pagerAlbumPhotoShow);
		Log.v("TTT", "comment..............." + post.getPostImageVideos().size());
		albumPhotosSlideShowAdapter = new AlbumPhotosSlideShowAdapter(AlbumPhotosSlideShowActivity.this, post);
		pagerAlbumPhotoShow.setAdapter(albumPhotosSlideShowAdapter);
		pagerAlbumPhotoShow.setCurrentItem(position);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}


}