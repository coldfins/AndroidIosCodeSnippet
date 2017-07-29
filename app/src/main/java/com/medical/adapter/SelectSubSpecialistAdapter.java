package com.medical.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medical.doctfin.R;
import com.medical.model.SpecialistSubCategoryModel;

import java.util.ArrayList;

public class SelectSubSpecialistAdapter extends RecyclerView.Adapter<SelectSubSpecialistAdapter.SelectSubSpecialistViewHolder> {
	private ArrayList<SpecialistSubCategoryModel> specialistSubCatModelArrayList;

	public static class SelectSubSpecialistViewHolder extends RecyclerView.ViewHolder {
		private TextView selectSubSpecialistTextView, selectSubTypeIdtextView;

		public SelectSubSpecialistViewHolder(View itemView) {
			super(itemView);
			selectSubSpecialistTextView = (TextView) itemView.findViewById(R.id.specialistSubTypeTextView);
			selectSubTypeIdtextView = (TextView) itemView.findViewById(R.id.specialistSubTypeIdTextView);
		}
	}

	public SelectSubSpecialistAdapter(ArrayList<SpecialistSubCategoryModel> specialistSubCatModelArrayList) {
		super();
		this.specialistSubCatModelArrayList = specialistSubCatModelArrayList;
	}

	@Override
	public int getItemCount() {
		return specialistSubCatModelArrayList.size();
	}

	@Override
	public void onBindViewHolder(SelectSubSpecialistViewHolder specialistCatViewHolder, int i) {
		specialistCatViewHolder.selectSubSpecialistTextView.setText(specialistSubCatModelArrayList.get(i).getSubCategoryName());
		specialistCatViewHolder.selectSubTypeIdtextView.setText(specialistSubCatModelArrayList.get(i).getSubCategoryId().toString());
	}

	@Override
	public SelectSubSpecialistViewHolder onCreateViewHolder(ViewGroup parent, int i) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialist_sub_type_cardlist, parent, false);
		SelectSubSpecialistViewHolder viewHolder = new SelectSubSpecialistViewHolder(v);
		return viewHolder;
	}

}
