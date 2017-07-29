package com.housing.housecheap.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.housing.housecheap.PropertyDetailsActivity;
import com.housing.housecheap.R;

import java.util.ArrayList;

import com.housing.housecheap.model.PropertiesList;

public class PageFragmentWishList extends
		RecyclerView.Adapter<PageFragmentWishList.ViewHolder> {

	private LayoutInflater mInflater;
	ArrayList<PropertiesList> propertyItem;
	Context ctx;
	Typeface font;

	public PageFragmentWishList(Context ctx,
			ArrayList<PropertiesList> propertyItem) {
		super();
		this.ctx = ctx;
		this.propertyItem = propertyItem;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return propertyItem.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return propertyItem.indexOf(getItemId(position));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, int i) {

		PropertiesList pl = propertyItem.get(i);
		viewHolder.tvId.setText(String.valueOf(pl.getId()));
		viewHolder.tvPType.setText(pl.getBhktype());
		viewHolder.tvType.setText(pl.getPropertytypes());
		viewHolder.tvArea.setText("- " + pl.getArea());
		viewHolder.tvRate.setText(String.valueOf(pl.getRate()));
		viewHolder.tvCity.setText(pl.getCity());
		viewHolder.tvOwner.setText("onwards");	
		int rate=pl.getRate();
		int buildArea=pl.getBuildarea();
		float getkPersqft = (float) rate / buildArea;
		viewHolder.tvCalculateArea.setText(String.valueOf(getkPersqft)+" k/sq.ft");
		viewHolder.tvFname.setText(pl.getUserFirstName());
		viewHolder.tvLname.setText(pl.getUserLastName());
		viewHolder.tvAType.setText(pl.getAddedby());
		// viewHolder.tvForPType.setText("For "+pl.getPropertyFor());
		viewHolder.tvRs.setTypeface(font);

		// viewHolder.ivProperty.setImageURI(Uri.parse(pl.getPropertyphotoUrl()));

		/*
		 * Bitmap myImage = getBitmapFromURL(pl.getPropertyphotoUrl());
		 * //BitmapDrawable(obj) convert Bitmap object into drawable object.
		 * Drawable dr = new BitmapDrawable(myImage);
		 * viewHolder.ivProperty.setBackgroundDrawable(dr);
		 */

		// viewHolder.ivProperty.setBackground
		// Picasso.with(ctx).load(pl.getPropertyphotoUrl()).into(viewHolder.ivProperty);
		final ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(ctx));// Get
																		// singleton
		// instance
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.dummy_image)
				.showImageForEmptyUri(R.drawable.dummy_image)
				.showImageOnLoading(R.drawable.dummy_image)
				.showImageOnFail(R.drawable.dummy_image).cacheOnDisc()
				// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		imageLoader.displayImage(pl.getPropertyphotoUrl(),
				viewHolder.ivPropertyImg, options);

		// final String cno=pl.getUserContactNo();
		viewHolder.tvCNo.setText(pl.getUserContactNo());
		viewHolder.tvmsg.setTypeface(font);
		viewHolder.tvCall.setTypeface(font);

		viewHolder.ivRemoveWishList
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(v.getContext(),
								viewHolder.tvId.getText().toString(),
								Toast.LENGTH_SHORT).show();
					}
				});

		viewHolder.tvCall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(v.getContext(),viewHolder.tvCNo.getText().toString()
				// +"" , Toast.LENGTH_SHORT).show();
				String phnum = viewHolder.tvCNo.getText().toString();
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + phnum));
				v.getContext().startActivity(callIntent);

			}
		});

		viewHolder.cv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(v.getContext(),
						PropertyDetailsActivity.class);
				i.putExtra("id", viewHolder.tvId.getText().toString());
				i.putExtra("city", viewHolder.tvCity.getText().toString());
				v.getContext().startActivity(i);

			}
		});

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.fragment_card, viewGroup, false);

		ViewHolder viewHolder = new ViewHolder(v);

		return viewHolder;
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		// public ImageView imgThumbnail;
		CardView cv;
		TextView tvId, tvPType, tvType, tvArea, tvRs, tvRate, tvCity, tvOwner,
				tvCalculateArea, tvFname, tvLname, tvAType, tvmsg, tvCall,
				tvCNo;
		ImageView ivPropertyImg, ivRemoveWishList;

		// LinearLayout ll;

		// public LinearLayout l;

		public ViewHolder(View itemView) {
			super(itemView);
			font = Typeface.createFromAsset(ctx.getAssets(),
					"fontawesome-webfont.ttf");
			cv = (CardView) itemView.findViewById(R.id.cvWishListProperty);
			ivPropertyImg = (ImageView) itemView
					.findViewById(R.id.ivWishProperty);
			tvId = (TextView) itemView.findViewById(R.id.tvWishPropertyId);
			tvPType = (TextView) itemView.findViewById(R.id.tvWishBhktype);
			tvType = (TextView) itemView.findViewById(R.id.tvWishAppartment);
			tvArea = (TextView) itemView.findViewById(R.id.tvWishArea);
			tvRate = (TextView) itemView.findViewById(R.id.tvWishPrice);
			tvCity = (TextView) itemView.findViewById(R.id.tvWishAddress);
			tvOwner = (TextView) itemView.findViewById(R.id.tvWishOnwards);
			tvCalculateArea = (TextView) itemView
					.findViewById(R.id.tvWishAreaCounting);
			tvFname = (TextView) itemView.findViewById(R.id.tvWishFname);
			tvLname = (TextView) itemView.findViewById(R.id.tvWishLname);
			tvAType = (TextView) itemView.findViewById(R.id.tvWishAType);
			tvRs = (TextView) itemView.findViewById(R.id.tvWishRs);
			ivRemoveWishList = (ImageView) itemView
					.findViewById(R.id.ivWishList);
			tvmsg = (TextView) itemView.findViewById(R.id.tvWishMsgIcon);
			tvCall = (TextView) itemView.findViewById(R.id.tvWishCallIcon);
			tvCNo = (TextView) itemView.findViewById(R.id.tvWishCNo);
			/*
			 * tvmsg=(TextView) itemView.findViewById(R.id.tvWishMsgIcon);
			 * tvCall=(TextView) itemView.findViewById(R.id.tvWishCallIcon);
			 */

			/*
			 * cvPanoramaDetails = (CardView) itemView.findViewById(R.id.cvProperty);
			 * cvPanoramaDetails.setOnClickListener(new View.OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { // TODO Auto-generated
			 * method stub
			 * ivRemoveWishList=(ImageView)v.findViewById(R.id.ivWishList);
			 * ivRemoveWishList.setOnClickListener(new View.OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { // TODO Auto-generated
			 * method stub txtPropertyId = (TextView)
			 * v.findViewById(R.id.tvWishPropertyId);
			 * Toast.makeText(v.getContext(), txtPropertyId.getText().toString()+"",
			 * Toast.LENGTH_SHORT).show();
			 * 
			 * } });
			 * 
			 * } });
			 */

		}

	}

}
