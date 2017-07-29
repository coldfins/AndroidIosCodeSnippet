package com.housing.housecheap.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.housing.housecheap.R;

import java.util.List;

public class CustomImageAdapter extends PagerAdapter{
	Context context;
    List<String> array;

	public CustomImageAdapter(Context context,List<String> array) {
		super();
		this.context = context;
		this.array=array;
		
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		 LayoutInflater inflater = ((Activity)context).getLayoutInflater();
         
		View viewItem = inflater.inflate(R.layout.image_item, container, false);
		ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
		final ProgressBar progressbar=(ProgressBar)viewItem.findViewById(R.id.progressBar1);

		Picasso.with(context)
		.load(array.get(position))
	   .placeholder(R.drawable.dummy_img)
		.into(imageView, new Callback() {
			@Override
			public void onError() {

			}

			@Override
			public void onSuccess() {
				progressbar.setVisibility(View.GONE);
			}
		});

		((ViewPager)container).addView(viewItem);

		return viewItem;
	}
	
	@Override
	public int getCount() {
		return array.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View)object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}
	
}
