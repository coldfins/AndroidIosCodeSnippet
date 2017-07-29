package com.medical.doctfin;

import java.util.ArrayList;

import com.medical.adapter.DieasesImageAdapter;
import com.medical.utils.ImageFunction;
import com.viewpagerindicator.CirclePageIndicator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

@SuppressLint("ResourceAsColor")
public class DieasesImageActivity extends ActionBarActivity {
	private ImageFunction imageFunction;
	private ImageView diseaseImageView;
	private Bitmap bitmap;
	private ViewPager imageViewPager;
	private DieasesImageAdapter adapter;
	private Toolbar displayToolbar;
	private CirclePageIndicator circleIndicator;
	private ArrayList<String> imageStringArray = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dieases_image);
		getIntentIfAvailable();
		setToolBar();
		circleIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

		imageViewPager = (ViewPager) findViewById(R.id.imageViewPager);
		adapter = new DieasesImageAdapter(DieasesImageActivity.this,
				imageStringArray);
		imageViewPager.setAdapter(adapter);
		// circleIndicator.setBackgroundColor(getResources().getColor(R.color.blue));
		circleIndicator
				.setPageColor(getResources().getColor(R.color.lightBlue));
		circleIndicator.setFillColor(getResources().getColor(R.color.colorPrimary));
		circleIndicator.setViewPager(imageViewPager);
		// setResources();
	}

	private void setToolBar() {
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setTitle("Dieases Image");
		setSupportActionBar(displayToolbar);
		displayToolbar.setNavigationIcon(R.drawable.back);
		displayToolbar.setTitleTextColor(Color.WHITE);

		displayToolbar.setNavigationOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dieases_image, menu);
		return true;
	}

	private void getIntentIfAvailable() {
		Intent i = getIntent();
		if (i.getExtras() != null) {
			imageStringArray = i.getStringArrayListExtra("Bitmap");
		}
	}

	// private void setResources() {
	// imageFunction = new ImageFunction();
	// diseaseImageView = (ImageView) findViewById(R.id.diseaseImageView);
	// diseaseImageView.setImageBitmap(bitmap);
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
