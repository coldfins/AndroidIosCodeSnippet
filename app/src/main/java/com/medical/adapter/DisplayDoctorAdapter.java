package com.medical.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.medical.doctfin.DoctorProfileActivity;
import com.medical.doctfin.R;
import com.medical.model.SearchLocationDeatilModel;
import com.medical.utils.ConnectionDetector;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DisplayDoctorAdapter extends RecyclerView.Adapter<DisplayDoctorAdapter.DisplayDoctorViewHolder> {

	private ArrayList<SearchLocationDeatilModel> searchDoctArrayList;
	private static Context context;
	private int lastPosition=0;
	private int catId,subCatId;
	private String appoinmentDate;
	private ConnectionDetector connectionDetector;
	private boolean isConnectionActive = false;

	public static class DisplayDoctorViewHolder extends RecyclerView.ViewHolder {
		private CardView displayDoctListCardView;
		private ImageView doctImage;
		private TextView doctNameTextView, doctAddressTextView, doctIdTextView, doctDegreeTextView, doctFeesTextView;

		public DisplayDoctorViewHolder(View itemView) {
			super(itemView);

			displayDoctListCardView = (CardView) itemView.findViewById(R.id.displayDoctListCardView);
			doctImage = (ImageView) itemView.findViewById(R.id.doctImageView);
			doctNameTextView = (TextView) itemView.findViewById(R.id.doctNameTextView);

			doctAddressTextView = (TextView) itemView.findViewById(R.id.doctAddressTextView);
			doctDegreeTextView = (TextView) itemView.findViewById(R.id.doctDegreeTextView);
			doctIdTextView = (TextView) itemView.findViewById(R.id.doctIdTextView);
			doctFeesTextView = (TextView) itemView.findViewById(R.id.doctFeesTextView);
		}

	}

	public DisplayDoctorAdapter(ArrayList<SearchLocationDeatilModel> searchDoctArrayList, Context context, int catId,int subCatId, String appoinmentDate) {
		super();
		this.searchDoctArrayList = searchDoctArrayList;
		this.context = context;
		this.catId = catId;
		this.subCatId=subCatId;
		this.appoinmentDate = appoinmentDate;
	}

	@Override
	public int getItemCount() {
		return searchDoctArrayList.size();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public void onBindViewHolder(final DisplayDoctorViewHolder displayDoctViewHolder, final int i) {
		try {
			Picasso.with(context)
					.load(searchDoctArrayList.get(i).getUserImage())
					.placeholder(R.drawable.reguserimage)
					.error(R.drawable.reguserimage)
					.into(displayDoctViewHolder.doctImage);
			displayDoctViewHolder.doctNameTextView.setText("Dr. " + searchDoctArrayList.get(i).getName().toString());
			displayDoctViewHolder.doctDegreeTextView.setText(searchDoctArrayList.get(i).getDegreeName());
			displayDoctViewHolder.doctAddressTextView.setText(searchDoctArrayList.get(i).getClinicAddress());
			displayDoctViewHolder.doctIdTextView.setText(searchDoctArrayList.get(i).getDocInfoId().toString());
			displayDoctViewHolder.doctAddressTextView.setText(searchDoctArrayList.get(i).getClinicAddress().toString());
			displayDoctViewHolder.doctFeesTextView.setText("$ " + searchDoctArrayList.get(i).getConsultationFees().toString());

			displayDoctViewHolder.displayDoctListCardView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String doctIdString;
					doctIdString = displayDoctViewHolder.doctIdTextView.getText().toString();
					connectionDetector = new ConnectionDetector(context);
					isConnectionActive = connectionDetector.isConnectingToInternet();
					if (!isConnectionActive) {
						initAlertDialogForCheckConnectivity();
					} else {
						Intent intent = new Intent(context, DoctorProfileActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("doctIdString", doctIdString);
						intent.putExtra("catId", catId);
						intent.putExtra("subCatId", subCatId);
						intent.putExtra("appoinmentDate", appoinmentDate);
						context.startActivity(intent);
						((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.exit_to_left);
					}
				}
			});
			setAnimation(displayDoctViewHolder.itemView,i);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	void setAnimation(View viewToAnimate, int pos) {
		if (pos > lastPosition) {
			Animation animation = AnimationUtils.loadAnimation(context, R.anim.slid_in_bottom);
			viewToAnimate.startAnimation(animation);
			lastPosition = pos;
		}
	}

	@Override
	public DisplayDoctorViewHolder onCreateViewHolder(ViewGroup parent, int i) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchdoctor_cardlist, parent, false);
		DisplayDoctorViewHolder displayDoctViewHolder = new DisplayDoctorViewHolder(v);
		return displayDoctViewHolder;
	}

	private void initAlertDialogForCheckConnectivity() {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(context.getResources().getString(R.string.internet_error_header));
		alertDialog.setMessage(context.getResources().getString(R.string.internet_error_string));
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				isConnectionActive = false;
			}
		});
		alertDialog.show();
	}

}
