package com.housing.housecheap.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.housing.housecheap.PanoramaViewActivity;
import com.housing.housecheap.R;

import java.util.ArrayList;

import com.housing.housecheap.model.PropertyPanorama;

public class PanoramaAdapterforInformation extends RecyclerView.Adapter<PanoramaAdapterforInformation.ViewHolder> {
	ArrayList<PropertyPanorama> panoramaItem;
	Context ctx;
	private final static int FADE_DURATION = 1000 ;

	public PanoramaAdapterforInformation(ArrayList<PropertyPanorama> panoramaItem, Context ctx) {
		super();
		this.panoramaItem = panoramaItem;
		this.ctx = ctx;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.panorama_details, viewGroup, false);
		ViewHolder viewHolder = new ViewHolder(v);
		return viewHolder;
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		CardView cvPanoramaDetails;
		TextView txtPanoramaTitle, tvPanoramaUrl;

		public ViewHolder(View itemView) {
			super(itemView);
			
			cvPanoramaDetails = (CardView) itemView.findViewById(R.id.cvPanoramaDetails);
			txtPanoramaTitle = (TextView) itemView.findViewById(R.id.txtPanoramaTitle);
			tvPanoramaUrl = (TextView) itemView.findViewById(R.id.tvPanoramaUrl);
		}
	}

	@Override
	public int getItemCount() {
		return panoramaItem.size();
	}
	
	@Override
	public long getItemId(int position) {
		return panoramaItem.indexOf(getItemId(position));
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, int i) {
		PropertyPanorama p=panoramaItem.get(i);
		viewHolder.txtPanoramaTitle.setText(p.getTitle());
		viewHolder.tvPanoramaUrl.setText(p.getPanoUrl());
		viewHolder.cvPanoramaDetails.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String id = viewHolder.tvPanoramaUrl.getText().toString();
				Intent i = new Intent(v.getContext(), PanoramaViewActivity.class);
				i.putExtra("panorama_url", id);
				v.getContext().startActivity(i);
			}
		});
		
		setScaleAnimation(viewHolder.itemView);
	}
	
	private void setScaleAnimation(View view) {
	    ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	    anim.setDuration(FADE_DURATION);
	    view.startAnimation(anim);
	}

}
