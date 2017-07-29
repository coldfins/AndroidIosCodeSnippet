package com.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.medical.doctfin.AddAppointmentActivity;
import com.medical.doctfin.R;
import com.medical.utils.CustomGallery;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.util.ArrayList;

public class GalleryAdapterList extends BaseAdapter {
	private LayoutInflater infalter;
	private ArrayList<CustomGallery> data = new ArrayList<CustomGallery>();
	ImageLoader imageLoader;
	CustomGallery customeGallery = new CustomGallery();
	private boolean isActionMultiplePick;

	public GalleryAdapterList(Context c, ImageLoader imageLoader) {
		infalter = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public CustomGallery getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setMultiplePick(boolean isMultiplePick) {
		this.isActionMultiplePick = isMultiplePick;
	}

	public void addAll(ArrayList<CustomGallery> files) {

		try {
			this.data.clear();
			this.data.addAll(files);
			View v;
			// ((ViewHolder) v.getTag()).deleteImageView
			// .setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = infalter.inflate(R.layout.gallery_item_list, null);
			holder = new ViewHolder();
			holder.imgQueue = (ImageView) convertView.findViewById(R.id.imgQueue);
			holder.imgQueueMultiSelected = (ImageView) convertView.findViewById(R.id.imgQueueMultiSelected);
			holder.deleteImageView = (ImageView) convertView.findViewById(R.id.deleteImageView);

			holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customeGallery = data.get(position);
                    data.remove(customeGallery);
                    AddAppointmentActivity.dataT.remove(customeGallery);

                    if (AddAppointmentActivity.dataT.size() == 0) {
                        AddAppointmentActivity.diseaseLinear.setVisibility(View.GONE);
                    }
                    notifyDataSetChanged();

                }
            });

			if (isActionMultiplePick) {
				holder.imgQueueMultiSelected.setVisibility(View.VISIBLE);
			} else {
				holder.imgQueueMultiSelected.setVisibility(View.GONE);
			}

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imgQueue.setTag(position);

		try {
			imageLoader.displayImage("file://" + data.get(position).sdcardPath,
                holder.imgQueue, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.imgQueue.setImageResource(R.drawable.no_media);
                        super.onLoadingStarted(imageUri, view);
                    }
                });

			if (isActionMultiplePick) {
				holder.imgQueueMultiSelected.setSelected(data.get(position).isSeleted);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}

	public class ViewHolder {
		ImageView imgQueue;
		ImageView imgQueueMultiSelected;
		ImageView deleteImageView;
	}

	public void clear() {
		data.clear();
		notifyDataSetChanged();
	}
}
