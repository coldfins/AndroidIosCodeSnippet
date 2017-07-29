package com.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medical.doctfin.R;

import java.util.ArrayList;

public class PersonSpinnerAdapter extends BaseAdapter{
	
	private ArrayList<String> personArraylist;
	private LayoutInflater inflater;
	private Context context;
	public PersonSpinnerAdapter(ArrayList<String> personArrayList,Context context)
	{
		super();
		this.personArraylist=personArrayList;
		this.context=context;
		
	}

	@Override
	public int getCount() {
		return personArraylist.size();
	}

	@Override
	public Object getItem(int position) {
		return personArraylist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			ViewHolder mViewHolder;
			inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_person, null);
				mViewHolder = new ViewHolder(convertView);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			mViewHolder.noOfPersonTextView.setText(personArraylist.get(position).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return convertView;
	}
	private class ViewHolder {
		TextView noOfPersonTextView;

		public ViewHolder(View item) {
			noOfPersonTextView = (TextView) item.findViewById(R.id.noOfPersonTextView);
		}
	}


}
