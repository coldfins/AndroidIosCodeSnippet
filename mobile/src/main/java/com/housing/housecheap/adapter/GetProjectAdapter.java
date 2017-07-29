package com.housing.housecheap.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.housing.housecheap.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import com.housing.housecheap.model.PropertiesList;

public class GetProjectAdapter extends RecyclerView.Adapter<GetProjectAdapter.ViewHolder> {
	ArrayList<PropertiesList> propertyItem;
	Context ctx;
	Typeface font;
	int lastPosition = -1;
	View view;
	private final static int FADE_DURATION = 1000 ;

	public GetProjectAdapter(Context ctx, ArrayList<PropertiesList> propertyItem) {
		super();
		this.ctx = ctx;
		this.propertyItem = propertyItem;
	}

	@Override
	public long getItemId(int position) {
		return propertyItem.indexOf(getItemId(position));
	}

	@Override
	public int getItemCount() {
		return propertyItem.size();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		PropertiesList pl = propertyItem.get(i);
		viewHolder.txtPropertyId.setText(String.valueOf(pl.getId()));
		viewHolder.txtBhktype.setText(pl.getBhktype());
		viewHolder.txtAppartment.setText(pl.getPropertytypes());
		viewHolder.txtArea.setText("- " + pl.getArea());
		viewHolder.tvCity.setText(pl.getCity());
		viewHolder.tvCno.setText(pl.getUserContactNo());
		viewHolder.tvForPType.setText("For "+pl.getPropertyFor());
		viewHolder.tvRs.setTypeface(font);
		viewHolder.tvPrice.setText(String.valueOf(pl.getRate()));

		final ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(ctx));// Get singleton instance
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.no_results)
				.showImageForEmptyUri(R.drawable.no_results)
				.showImageOnLoading(R.drawable.no_results)
				.showImageOnFail(R.drawable.no_results).cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		  imageLoader.displayImage(pl.getPropertyphotoUrl(), viewHolder.ivProperty, options);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_propertydetails, viewGroup, false);
		ViewHolder viewHolder = new ViewHolder(v);
		view=v;
		return viewHolder;
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		CardView cvProperty;
		public TextView txtPropertyId;
		public TextView txtBhktype;
		public TextView txtAppartment;
		public TextView txtArea;
		public TextView tvCity;
		public TextView tvCno;
		public TextView tvRs;
		public TextView tvPrice;
		public TextView tvForPType;
		public ImageView ivProperty;

		public ViewHolder(View itemView) {
			super(itemView);
			font = Typeface.createFromAsset(ctx.getAssets(), "fontawesome-webfont.ttf");
			txtPropertyId = (TextView) itemView.findViewById(R.id.txtPropertyId);
			txtBhktype = (TextView) itemView.findViewById(R.id.txtBhktype);
			txtAppartment = (TextView) itemView.findViewById(R.id.txtAppartment);
			txtArea = (TextView) itemView.findViewById(R.id.txtArea);
			tvCity = (TextView) itemView.findViewById(R.id.txtCity);
			tvCno = (TextView) itemView.findViewById(R.id.txtCno);
			tvRs = (TextView) itemView.findViewById(R.id.txtRs);
			tvForPType =(TextView) itemView.findViewById(R.id.tvForPType);
			tvPrice = (TextView) itemView.findViewById(R.id.txtPrice);
			ivProperty = (ImageView) itemView.findViewById(R.id.ivProperty);
			cvProperty = (CardView) itemView.findViewById(R.id.cvProperty);
		}
	}
}