package com.post.wall.wallpostapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.post.wall.wallpostapp.R;

import java.util.List;

public class PopupDeviceAdapter extends ArrayAdapter<String> {
	List<String> popupList;
	private Context mContext;
	View rowView=null;
	LinearLayout layoutlistItem;
	TextView text1;

	public PopupDeviceAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		this.popupList = objects;
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String listItem = popupList.get(position);
		rowView = inflater.inflate(R.layout.popupmenu_back, parent, false);
		
		text1=(TextView)rowView.findViewById(R.id.text1);
		text1.setText(listItem);
		
		layoutlistItem=(LinearLayout)rowView.findViewById(R.id.layoutlistItem);
		if(popupList.size()==1)
		{
			layoutlistItem.setBackground(mContext.getResources().getDrawable(R.drawable.popup_all_back));
			text1.setPadding(10, 20, 10, 20);
		}
		else
		{
			if(position==0)
			{
				layoutlistItem.setBackground(mContext.getResources().getDrawable(R.drawable.popup_top_back));
				text1.setPadding(10, 20, 10, 10);
			}
			else if(position==popupList.size()-1)
			{
				layoutlistItem.setBackground(mContext.getResources().getDrawable(R.drawable.popup_bottom_back));
				text1.setPadding(10, 10, 10, 20);
			}
			else
			{
				layoutlistItem.setBackground(mContext.getResources().getDrawable(R.drawable.popup_middle_back));
				text1.setPadding(10, 10, 10, 10);
			}
				
		}
		return rowView;
	}
	
}