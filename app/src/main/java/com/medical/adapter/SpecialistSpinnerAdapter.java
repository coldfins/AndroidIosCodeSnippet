package com.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medical.doctfin.R;
import com.medical.model.SpecialistCategoryListModel;

import java.util.ArrayList;

public class SpecialistSpinnerAdapter extends BaseAdapter{
	private ArrayList<SpecialistCategoryListModel> specialistArrayList;
	private LayoutInflater inflater;
	private Context context;

	public SpecialistSpinnerAdapter(ArrayList<SpecialistCategoryListModel> specialistArrayList, Context context) {
		super();
		this.specialistArrayList = specialistArrayList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return specialistArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return specialistArrayList.get(position);
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
				convertView = inflater.inflate(R.layout.specialist_type_cardlist, null);
				mViewHolder = new ViewHolder(convertView);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			mViewHolder.selectSpecialistTextView.setText(specialistArrayList.get(position).getCategoryName());
			mViewHolder.selectSpecialistIdTextView.setText(specialistArrayList.get(position).getCategoryId().toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return convertView;
	}
	
	private class ViewHolder {
		TextView selectSpecialistTextView, selectSpecialistIdTextView;

		public ViewHolder(View itemView) {
			selectSpecialistTextView = (TextView) itemView.findViewById(R.id.specialistTypeTextView);
			selectSpecialistIdTextView = (TextView) itemView.findViewById(R.id.specialistTypeIdTextView);
		}
	}

}
