package com.housing.housecheap.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.housing.housecheap.R;

public class MapCustomAdapter extends BaseAdapter {
	LayoutInflater inflater;
	Context context;
	String[] mPlaceTypeName;
	int[] grayImage,orangImage;
	public  static int item=-1;
	public  static int flag=0;

	public MapCustomAdapter(String[] mPlaceTypeName,int[] grayImage,int[] orangImage, Context context) {
		super();
		this.mPlaceTypeName = mPlaceTypeName;
		this.grayImage=grayImage;
		this.orangImage=orangImage;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return mPlaceTypeName.length;
	}

	@Override
	public Object getItem(int position) {
		return mPlaceTypeName[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyViewHolder mViewHolder;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.horizontallv_map, parent, false);
			mViewHolder = new MyViewHolder(convertView);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (MyViewHolder) convertView.getTag();
		}
		
		mViewHolder.txtPlace.setText(mPlaceTypeName[position]);

		if(position==item)
		{
			Log.d("pos item", position+" "+item);

			mViewHolder.txtPlace.setTextColor(context.getResources().getColor(R.color.toolBarColor));
			mViewHolder.ivGrayImage.setImageResource(orangImage[position]);
		}
		else
		{
			mViewHolder.txtPlace.setTextColor(context.getResources().getColor(R.color.tvGray));
			mViewHolder.ivGrayImage.setImageResource(grayImage[position]);
		}
        
		return convertView;
	}

	private class MyViewHolder {
		TextView txtPlace;
		ImageView ivGrayImage;

		public MyViewHolder(View item) {
			txtPlace = (TextView) item.findViewById(R.id.txtPlace);
			ivGrayImage = (ImageView) item.findViewById(R.id.ivGrayImage);
		}
	}

}
