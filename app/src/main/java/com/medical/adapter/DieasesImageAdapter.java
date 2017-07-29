package com.medical.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.medical.doctfin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DieasesImageAdapter extends PagerAdapter {
	private Context context;
	private LayoutInflater layoutInflater;

	ArrayList<String> imageStringArray;

	@Override
	public int getCount() {
		return imageStringArray.size();
	}

	public DieasesImageAdapter(Context context, ArrayList<String> imageStringArray) {
		super();
		this.context = context;
		this.imageStringArray = imageStringArray;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == (LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item_view = layoutInflater.inflate(R.layout.list_image_view, container, false);
		ImageView dieasesImage = (ImageView) item_view.findViewById(R.id.diseaseImageView);

		Picasso.with(context).load(imageStringArray.get(position))
				.placeholder(R.drawable.no_media)
				.error(R.drawable.no_media).into(dieasesImage);

		container.addView(item_view);
		return item_view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((LinearLayout) object);

	}
}
