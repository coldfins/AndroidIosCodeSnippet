package com.housing.housecheap.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.housing.housecheap.MainActivity;
import com.housing.housecheap.R;
import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;
	private String mNavTitles[];
	TypedArray iconGray, iconOrange;
	private String name;
	public static String profilee;
	public static String email;
	public static int selected_item = 1;
	Context ctx;
	SharedPreferences sp;
	public static int offset;
	ImageView ivSignOut;
	AlertDialog.Builder alertDialogBuilder;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		int Holderid;
		TextView textView;
		ImageView imageView;
		ImageView profile;
		TextView Name;
		TextView email;

		public ViewHolder(View itemView, int ViewType) {
			super(itemView);

			if (ViewType == TYPE_ITEM) {
				textView = (TextView) itemView.findViewById(R.id.tvItemName);
				imageView = (ImageView) itemView.findViewById(R.id.tvItemIcon);
				Holderid = 1;
			} else {
				email = (TextView) itemView.findViewById(R.id.tvUserEmailId);
				profile = (ImageView) itemView.findViewById(R.id.ivUserPic);
				Holderid = 0;
			}
		}

	}

	public MyAdapter(String Titles[], TypedArray iconGray, TypedArray iconOrange, String Email, String Profile, Context ctx) {
		this.mNavTitles = Titles;
		this.iconGray = iconGray;
		this.email = Email;
		this.profilee = Profile;
		this.iconOrange = iconOrange;
		this.ctx = ctx;

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		sp = ctx.getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

		if (viewType == TYPE_ITEM) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_header, parent, false);
			ViewHolder vhItem = new ViewHolder(v, viewType);
			return vhItem;
		} else if (viewType == TYPE_HEADER) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);

			ivSignOut = (ImageView) v.findViewById(R.id.ivSignOut);
			ViewHolder vhHeader = new ViewHolder(v, viewType);
			alertDialogBuilder = new AlertDialog.Builder(ctx);
			alertDialogBuilder.setTitle("Logout");
			alertDialogBuilder.setMessage("Are you sure you want to Sign out.");

			//click of sign out img
			ivSignOut.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (offset == 0) {

					} else if (offset == 1) {
						ivSignOut.setBackgroundResource(R.drawable.sign_out);

						alertDialogBuilder.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										ivSignOut.setBackgroundResource(R.drawable.login_img);

										SharedPreferences.Editor editor = sp.edit();
										editor.putString("Uname", "");
										editor.putString("Ufname", "");
										editor.putString("ULname", "");
										editor.putString("UEmail", "");
										editor.putString("UCno", "");
										editor.putString("Purl", "");
										editor.putInt("userId", 0);
										editor.commit();

										email = "Welcome Guest";
										profilee = "R.drawable.reguserimage";
										offset = 0;

										Log.d("selected position", selected_item + "");
									}
								});

						alertDialogBuilder.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								});

						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
					}
					return false;
				}
			});

			return vhHeader;

		}
		return null;

	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		if (holder.Holderid == 1) {
			holder.textView.setText(mNavTitles[position - 1]);
			holder.imageView.setImageResource(iconGray.getResourceId(position - 1, -1));
			if (position == selected_item) {
				holder.textView.setTextColor(ctx.getResources().getColor(R.color.toolBarColor));
				holder.imageView.setImageResource(iconOrange.getResourceId(position - 1, -1));

				if (MainActivity.flagPosition == 1) {
					holder.textView.setTextColor(ctx.getResources().getColor(R.color.sidePanelGray));
					holder.imageView.setImageResource(iconGray.getResourceId(position - 1, -1));
				}
			} else {
				holder.textView.setTextColor(ctx.getResources().getColor(R.color.sidePanelGray));
				holder.imageView.setImageResource(iconGray.getResourceId(position - 1, -1));
			}
		} else {
			try {
				Log.d("TTT","Profile: "+profilee);
				Picasso.with(ctx).load(profilee).placeholder(R.drawable.reguserimage).into(holder.profile);
			} catch (Exception e) {
				e.printStackTrace();
			}

			holder.email.setText(email);

			String a = sp.getString("UEmail", "");
			if (a.isEmpty()) {
				ivSignOut.setBackgroundResource(R.drawable.login_img);
			} else {
				ivSignOut.setBackgroundResource(R.drawable.sign_out);
			}
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

}
