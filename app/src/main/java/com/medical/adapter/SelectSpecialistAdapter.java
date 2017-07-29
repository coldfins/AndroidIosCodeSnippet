package com.medical.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medical.doctfin.R;
import com.medical.model.SpecialistCategoryListModel;

import java.util.ArrayList;

public class SelectSpecialistAdapter extends RecyclerView.Adapter<SelectSpecialistAdapter.SelectSpecialistViewHolder> {
	private ArrayList<SpecialistCategoryListModel> specialistCatModelArrayList;

	public static class SelectSpecialistViewHolder extends RecyclerView.ViewHolder {
		private TextView selectSpecialistTextView, selectSpecialistIdTextView;

		public SelectSpecialistViewHolder(View itemView) {
			super(itemView);
			selectSpecialistTextView = (TextView) itemView.findViewById(R.id.specialistTypeTextView);
			selectSpecialistIdTextView = (TextView) itemView.findViewById(R.id.specialistTypeIdTextView);
		}
	}

	public SelectSpecialistAdapter(ArrayList<SpecialistCategoryListModel> specialistCatModel) {
		super();
		this.specialistCatModelArrayList = specialistCatModel;
	}

	@Override
	public int getItemCount() {
		return specialistCatModelArrayList.size();

	}

	@Override
	public void onBindViewHolder(SelectSpecialistViewHolder specialistCatViewHolder, int i) {
		specialistCatViewHolder.selectSpecialistTextView.setText(specialistCatModelArrayList.get(i).getCategoryName());
		specialistCatViewHolder.selectSpecialistIdTextView.setText(specialistCatModelArrayList.get(i).getCategoryId().toString());
	}

	@Override
	public SelectSpecialistViewHolder onCreateViewHolder(ViewGroup parent, int i) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialist_type_cardlist, parent, false);
		SelectSpecialistViewHolder specialistViewHolder = new SelectSpecialistViewHolder(v);
		return specialistViewHolder;
	}
}
