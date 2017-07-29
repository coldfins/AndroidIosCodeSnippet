package com.housing.housecheap.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.housing.housecheap.R;

import java.util.ArrayList;

import com.housing.housecheap.model.ListData;


public class CustomHorizontalList extends BaseAdapter {
	ArrayList myList = new ArrayList();
	LayoutInflater inflater;
	Context context;
	ArrayList<String> pm;

	public CustomHorizontalList(Context context, ArrayList myList, ArrayList<String> pm) {
		this.myList = myList;
		this.context = context;
		this.pm = pm;
		inflater = LayoutInflater.from(this.context);

	}

	@Override
	public int getCount() {
		return myList.size();
	}

	@Override
	public Object getItem(int position) {
		return myList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyViewHolder mViewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_horizontal_item, parent, false);
			mViewHolder = new MyViewHolder(convertView);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (MyViewHolder) convertView.getTag();
		}

		ListData currentListData = (ListData) getItem(position);

		if (currentListData.getBooleanVal() == "true") {
			mViewHolder.txtTitle.setText(currentListData.getTitle());
			mViewHolder.txtTitle.setTextColor(Color.parseColor("#000000"));
			mViewHolder.ivBlackImage.setImageResource(currentListData.getImgResIdBlack());
		}
		else if(currentListData.getBooleanVal() == "false") 
		{
			mViewHolder.txtTitle.setText(currentListData.getTitle());
			mViewHolder.txtTitle.setTextColor(Color.parseColor("#bdbdbd"));
			mViewHolder.ivBlackImage.setImageResource(currentListData.getImgResIdGray());
		}
		return convertView;
	}

	private class MyViewHolder {
		TextView txtTitle;
		ImageView ivBlackImage;

		public MyViewHolder(View item) {
			txtTitle = (TextView) item.findViewById(R.id.txtTitle);
			ivBlackImage = (ImageView) item.findViewById(R.id.ivBlackImage);
		}
	}
}
