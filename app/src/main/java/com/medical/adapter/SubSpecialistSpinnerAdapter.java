package com.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medical.doctfin.R;
import com.medical.model.SpecialistSubCategoryModel;

import java.util.ArrayList;

public class SubSpecialistSpinnerAdapter extends BaseAdapter {
	private ArrayList<SpecialistSubCategoryModel> subSpecialistArrayList;
	private LayoutInflater inflater;
	private Context context;

	public SubSpecialistSpinnerAdapter(ArrayList<SpecialistSubCategoryModel> subSpecialistArrayList, Context context) {
		super();
		this.subSpecialistArrayList = subSpecialistArrayList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return subSpecialistArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return subSpecialistArrayList.get(position);
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
				convertView = inflater.inflate(R.layout.specialist_sub_type_cardlist, null);
				mViewHolder = new ViewHolder(convertView);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			mViewHolder.selectSubSpecialistTextView.setText(subSpecialistArrayList.get(position).getSubCategoryName());
			mViewHolder.selectSubSpecialistIdTextView.setText(subSpecialistArrayList.get(position).getSubCategoryId().toString());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return convertView;
	}
	private class ViewHolder {
		TextView selectSubSpecialistTextView, selectSubSpecialistIdTextView;

		public ViewHolder(View itemView) {
			selectSubSpecialistTextView = (TextView) itemView.findViewById(R.id.specialistSubTypeTextView);
			selectSubSpecialistIdTextView = (TextView) itemView.findViewById(R.id.specialistSubTypeIdTextView);
		}
	}

}
