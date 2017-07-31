package adapter.Home;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dating.coolmatch.R;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

	private   int TYPE_HEADER = 0;
	private static int TYPE_ITEM = 1;
	private String mNavTitles[];
	private TypedArray iconGray;
	public static int selected_item = 1;
	Context ctx;
	SharedPreferences sp;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		int Holderid;

		TextView textView;
		ImageView imageView;
		ImageView profile;
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

	public DrawerAdapter(String Titles[], TypedArray iconGray,Context ctx) {

		this.mNavTitles = Titles;
		this.iconGray = iconGray;
		this.ctx = ctx;

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		sp = ctx.getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

		if (viewType == TYPE_ITEM) {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.side_panel_items, parent, false);
			ViewHolder vhItem = new ViewHolder(v, viewType);

			return vhItem;

		} else if (viewType == TYPE_HEADER) {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.header_layout, parent, false);
			ViewHolder vhHeader = new ViewHolder(v, viewType);
			return vhHeader;

		}
		return null;

	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		if (holder.Holderid == 1) {
			holder.textView.setText(mNavTitles[position - 1]);
			holder.imageView.setImageResource(iconGray.getResourceId(
					position - 1, -1));
			if (position == selected_item) {
				holder.textView.setTextColor(ctx.getResources().getColor(
						R.color.white));
			} else {
				holder.textView.setTextColor(ctx.getResources().getColor(
						R.color.white));
				holder.imageView.setImageResource(iconGray.getResourceId(
						position - 1, -1));
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
