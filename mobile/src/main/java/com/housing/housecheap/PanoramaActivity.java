package com.housing.housecheap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.housing.housecheap.adapter.PanoramaAdapterforInformation;
import com.housing.housecheap.model.PropertyPanorama;

import java.util.ArrayList;

public class PanoramaActivity extends ActionBarActivity {

	ArrayList<PropertyPanorama> panoramaArray = new ArrayList<PropertyPanorama>();
	ArrayList<String> title;
	ArrayList<String> imgUrl;
	Toolbar toolBar;
	ImageView ivPanorama;
	Context ctx;
	ProgressBar progressbar;
	RecyclerView.Adapter panoramaAdapter;
	RecyclerView panoramaRecyclerView;
	GridLayoutManager panoramaLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_panorama);
		initControl();
		setupToolbar();
		toolBar.setPadding(0, getStatusBarHeight(), 0, 0);

	}

	/**
	 * Initialize all UI components
	 */
	@SuppressWarnings("unchecked")
	public void initControl() {

		ivPanorama = (ImageView) findViewById(R.id.ivPanoramaa);
		title = new ArrayList<String>();
		imgUrl = new ArrayList<String>();

		panoramaArray = (ArrayList<PropertyPanorama>) getIntent().getSerializableExtra("panoramaImg");
		panoramaRecyclerView = (RecyclerView)findViewById(R.id.rvPanoramaImg);
		panoramaLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
		panoramaRecyclerView.setHasFixedSize(true);
		panoramaRecyclerView.setLayoutManager(panoramaLayoutManager);
		panoramaAdapter = new PanoramaAdapterforInformation(panoramaArray,getApplicationContext());
		panoramaRecyclerView.setAdapter(panoramaAdapter);

	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.panorama, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
	}

	public void setupToolbar() {
		toolBar = (Toolbar) findViewById(R.id.toolBarPanoramaActivity);
		toolBar.setTitleTextColor(Color.WHITE);
		toolBar.setTitle("Panorama View");
		final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		upArrow.setColorFilter(getResources().getColor(R.color.WHITE), android.graphics.PorterDuff.Mode.SRC_ATOP);

		 setSupportActionBar(toolBar);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 getSupportActionBar().setDisplayShowHomeEnabled(true);
		 getSupportActionBar().setHomeAsUpIndicator(upArrow);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		onBackPressed();
		finish();
		return super.onOptionsItemSelected(item);

	}
}
