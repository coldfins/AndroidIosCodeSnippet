package com.housing.housecheap.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.housing.housecheap.fragment.ProjectFragment;

@SuppressLint("ResourceAsColor")
public class MyPagerAdapter extends FragmentPagerAdapter {
	Context mContext;
	Fragment fragemnet = null;

	public MyPagerAdapter(FragmentManager fm,Context context) {
		super(fm);
		this.mContext = context;
	}

	@Override
	public Fragment getItem(int position) {

		switch (position) {
		case 0:
			fragemnet = new ProjectFragment();
			break;
		default:
			fragemnet = new ProjectFragment();
			break;
		}
		return  fragemnet;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public int getCount() {
		return 5;
	}
}
