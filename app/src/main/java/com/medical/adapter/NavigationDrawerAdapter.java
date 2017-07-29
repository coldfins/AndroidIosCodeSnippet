package com.medical.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medical.doctfin.R;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi")
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {
	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;
	public static String imageHeader;
	public static String emailHeader;
	public static String mNavTitlesStringArray[];
	private int menuIcons[];
	private int bluemenuIcons[];
	static Context context;
	public static int seleteditem = 1;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		private TextView menuItemsTextView;
		private TextView emailTextView;
		private ImageView menuIconImageView;
		private ImageView headerUserImage;

		int Holderid;
		Context contxt;
		public ViewHolder(View itemView, int ViewType, Context c) {
			super(itemView);
			contxt = c;

			if (ViewType == TYPE_ITEM) {
				menuIconImageView = (ImageView) itemView.findViewById(R.id.menuImageView);
				menuItemsTextView = (TextView) itemView.findViewById(R.id.menuFragmentTextView);
				Holderid = 1;
			} else {
				emailTextView = (TextView) itemView.findViewById(R.id.headerEmailTextView);
				headerUserImage = (ImageView) itemView.findViewById(R.id.userImageView);
				Holderid = 0;
			}
		}
	}

	public NavigationDrawerAdapter(String Title[], int icons[], String imageHeader, int blueIcons[], String email, Context getcontext) {
		this.mNavTitlesStringArray = Title;
		this.menuIcons = icons;
		NavigationDrawerAdapter.imageHeader = imageHeader;
		NavigationDrawerAdapter.emailHeader = email;
		this.bluemenuIcons = blueIcons;
		NavigationDrawerAdapter.context = getcontext;
	}

	@Override
	public int getItemCount() {
		return mNavTitlesStringArray.length + 1;
	}

	@Override
	public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder holder, int position) {
		if (holder.Holderid == 1) {
			holder.menuItemsTextView.setText(mNavTitlesStringArray[position - 1]);
			holder.menuIconImageView.setImageResource(menuIcons[position - 1]);

			if (seleteditem == position) {
				holder.menuItemsTextView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
				holder.menuIconImageView.setImageResource(bluemenuIcons[position - 1]);
			} else {
				holder.menuItemsTextView.setTextColor(context.getResources().getColor(R.color.dark_black));
				holder.menuIconImageView.setImageResource(menuIcons[position - 1]);
			}
		} else {
			holder.emailTextView.setText(emailHeader);

			if (imageHeader == null) {
				holder.headerUserImage.setImageResource(R.drawable.dummymale);
			} else {
				Picasso.with(context).load(imageHeader).placeholder(R.drawable.dummymale).error(R.drawable.dummymale).into(holder.headerUserImage);
			}
		}
	}

	@Override
	public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_ITEM) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userhome_drawer_menu, parent, false);
			ViewHolder VhItem = new ViewHolder(view, viewType, context);
			return VhItem;
		} else if (viewType == TYPE_HEADER) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userhome_drawer_header, parent, false);
			ViewHolder VhHeader = new ViewHolder(view, viewType, context);
			return VhHeader;
		}
		return null;
	}

	@Override
	public int getItemViewType(int position) {
		if (isPositionHeader(position)) {
			return TYPE_HEADER;
		} else {
			return TYPE_ITEM;
		}
	}

	private boolean isPositionHeader(int position) {
		return position == 0;
	}

}
