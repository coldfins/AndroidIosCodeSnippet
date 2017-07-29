package com.housing.housecheap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;


public class UserContactDetailActivity extends ActionBarActivity {
	Toolbar toolBar;
	TextView fname, lname, mobile, address, bhktype, pType, area;
	String id;
	LinearLayout lvRequestCallBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_contact_detail);

		initControl();

	}

	/**
	 * Initialize all UI components
	 */
	public void initControl() {
		setupToolbar();
		toolBar.setPadding(0, getStatusBarHeight(), 0, 0);
		fname = (TextView) findViewById(R.id.tvUCFname);
		lname = (TextView) findViewById(R.id.tvUCLname);
		mobile = (TextView) findViewById(R.id.tvUCno);
		address = (TextView) findViewById(R.id.tvUCAddress);
		bhktype = (TextView) findViewById(R.id.tvUCBhkType);
		pType = (TextView) findViewById(R.id.tvUCPropertyFor);
		area = (TextView) findViewById(R.id.tvUCArea);
		lvRequestCallBack = (LinearLayout) findViewById(R.id.lvCallAgent);

		try {
			Intent i = getIntent();
			if (i == null) {

			} else {
				id = i.getStringExtra("Id");
				fname.setText(i.getStringExtra("fname"));
				lname.setText(i.getStringExtra("lname"));
				mobile.setText(i.getStringExtra("number"));
				address.setText(i.getStringExtra("address"));
				bhktype.setText(i.getStringExtra("bhktype"));
				pType.setText(i.getStringExtra("ptype"));
				area.setText(i.getStringExtra("area"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.user_contact_detail, menu);
		return false;
	}

	public void setupToolbar() {
		toolBar = (Toolbar) findViewById(R.id.toolBarUserContactDetail);
		toolBar.setTitleTextColor(Color.WHITE);
		toolBar.setTitle(" Contact ");
		final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		upArrow.setColorFilter(getResources().getColor(R.color.WHITE), android.graphics.PorterDuff.Mode.SRC_ATOP);
		setSupportActionBar(toolBar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(upArrow);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		onBackPressed();
		return super.onOptionsItemSelected(item);
	}
}
