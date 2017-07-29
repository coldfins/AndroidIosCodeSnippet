package com.food.eathub.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.food.eathub.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;
	private String mNavTitles[];
	private int mIcons[];
	private int mIconsPink[];
	public static String name;
	public static String profile;
	public static String email;
	public static int selected_item = 2;
	public static int basketitem = 0;
	Context ctx;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		int Holderid;
		TextView textView;
		ImageView imageView;
		TextView baskettotal;
		ImageView profile;
		public static TextView Name;
		TextView email;

		public ViewHolder(View itemView, int ViewType) {
			super(itemView);
			if (ViewType == TYPE_ITEM) {
				textView = (TextView) itemView.findViewById(R.id.rowText);
				imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
				baskettotal = (TextView) itemView.findViewById(R.id.rowBasketTotal);
				Holderid = 1;
			} else {
				Name = (TextView) itemView.findViewById(R.id.name);
				email = (TextView) itemView.findViewById(R.id.email);
				profile = (ImageView) itemView.findViewById(R.id.circleView);
				Holderid = 0;
			}
		}
	}

	public MyAdapter(String Titles[], int Icons[], String Name, String Email, int iconspink[], Context ctx) {
		mNavTitles = Titles;
		mIcons = Icons;
		name = Name;
		email = Email;
		mIconsPink = iconspink;
		this.ctx = ctx;
	}

	@Override
	public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_ITEM) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_layout, parent, false);
			ViewHolder vhItem = new ViewHolder(v, viewType);
			return vhItem;
		} else if (viewType == TYPE_HEADER) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header_layout, parent, false);
			ViewHolder vhHeader = new ViewHolder(v, viewType);
			return vhHeader;
		}
		return null;
	}

	@Override
	public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
		if (holder.Holderid == 1) {
			holder.textView.setText(mNavTitles[position - 1]);
			holder.imageView.setImageResource(mIcons[position - 1]);

			if (position == selected_item) {
				holder.textView.setTextColor(ctx.getResources().getColor(R.color.toolBarColor));
				holder.imageView.setImageResource(mIconsPink[position - 1]);
			}
			else
				{
				holder.textView.setTextColor(ctx.getResources().getColor(R.color.drawerColor));
				holder.imageView.setImageResource(mIcons[position - 1]);
			}
		}
		else {
			holder.Name.setText(name);
			holder.email.setText(email);
		}
	}

	@Override
	public int getItemCount() {
		return mNavTitles.length + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (isPositionHeader(position))
			return TYPE_HEADER;
		return TYPE_ITEM;
	}

	private boolean isPositionHeader(int position) {
		return position == 0;
	}

	public Bitmap getPhoto(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

	public String encode(byte[] b)
	{
		String img = Base64.encodeToString(b, 0);
		return img;

	}

	public Bitmap decode(String str) {
		byte[] b = Base64.decode(str, 0);
		Bitmap bm = getPhoto(b);
		return bm;
	}

}
