package com.housing.housecheap.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.housing.housecheap.fragment.SearchFragment;
import com.housing.housecheap.model.City;
import com.housing.housecheap.model.Propertytype;
import com.housing.housecheap.model.searchTabItem;

import java.util.ArrayList;


public class SerachFragmentPagerAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 3;
	Context ctx;
	private String tabTitles[] = new String[] { "For sell", "For Rent",
			"For PG" };
	ArrayList<Propertytype> pType = new ArrayList<Propertytype>();
	ArrayList<City> city = new ArrayList<City>();
	ArrayList<String> city1 = new ArrayList<String>();
	SearchFragment f;

	ArrayList<searchTabItem> item = new ArrayList<searchTabItem>();

	public SerachFragmentPagerAdapter(FragmentManager fm,
                                      ArrayList<Propertytype> pType, ArrayList<City> city) {
		super(fm);
		this.pType = pType;
		this.city = city;

		// searchTabItem ii = new searchTabItem();
		// ii.setId(1);
		// ii.setItemName("sell");
		// item.add(ii);
		// ii.setId(2);
		// ii.setItemName("rent");
		// item.add(ii);
		// ii.setId(3);
		// ii.setItemName("pg");
		// item.add(ii);

		// this.city1 = city1;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub

		switch (position) {
		case 0:

			f = new SearchFragment();
			// f.newInstance(0);

			Bundle b = new Bundle();
			b.putInt("position", 0);
			b.putString("title", tabTitles[position]);
			// b.putString("itemname", item.get(position).getItemName());
			b.putSerializable("propertyType", pType);
			b.putSerializable("getCity", city);
			f.setArguments(b);

			// return f;

			break;
		case 1:
			f = new SearchFragment();
			// f.newInstance(1);
			Bundle b1 = new Bundle();
			b1.putInt("position", 1);
			b1.putString("title", tabTitles[position]);
			// b.putString("itemname", item.get(position).getItemName());
			b1.putSerializable("propertyType", pType);
			b1.putSerializable("getCity", city);
			f.setArguments(b1);

			// return f;
			break;
		case 2:
			f = new SearchFragment();
			// f.newInstance(2);
			Bundle b2 = new Bundle();
			b2.putInt("position", 2);
			b2.putString("title", tabTitles[position]);
			// b.putString("itemname", item.get(position).getItemName());
			b2.putSerializable("propertyType", pType);
			b2.putSerializable("getCity", city);
			f.setArguments(b2);

			// return f;

			break;

		default:
			break;
		}
		return f;

		// return f;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGE_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// Generate title based on item position
		return tabTitles[position];
	}

}
