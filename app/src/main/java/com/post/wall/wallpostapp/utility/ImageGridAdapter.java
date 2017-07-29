package com.post.wall.wallpostapp.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.post.wall.wallpostapp.GalleryPostScrollActivity;
import com.post.wall.wallpostapp.R;
import com.post.wall.wallpostapp.model.AlbumImages;
import com.squareup.picasso.Picasso;

public class ImageGridAdapter extends BaseAdapter {
	
	private Activity mContext;
	ArrayList<AlbumImages> albumImages;
	DisplayImageOptions doption = null;
	private int mItemHeight = 0;
	public int mNumColumns = 0;
	LayoutParams mImageViewLayoutParams;
	private LayoutInflater mInflater;
	int width;
	public boolean isLimit;
	int layoutHeight, layoutWidth;
	Bitmap dest = null;
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	@SuppressWarnings("deprecation")
	public ImageGridAdapter(Activity context, int textViewResourceId, ArrayList<AlbumImages> albumImages) {
		this.mContext = context;
		this.albumImages = albumImages;
		
		Log.v("TTT", "albumImages = " + albumImages.size());
		
		width = mContext.getWindowManager().getDefaultDisplay().getWidth();
		
		layoutWidth = width / 3;
		layoutHeight = width / 3;
		
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImageViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		try {
			doption = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.nophoto_album_thumb_normal)
			.showImageForEmptyUri(R.drawable.nophoto_album_thumb_normal)
			.showImageOnFail(R.drawable.nophoto_album_thumb_normal)
//			.resetViewBeforeLoading(true)
//			 .imageScaleType(ImageScaleType.EXACTLY)
//			 .bitmapConfig(Bitmap.Config.RGB_565)
			.cacheInMemory(false)
			.cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true)
			.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
	}
	
	// set numcols
	public void setNumColumns(int numColumns) {
		mNumColumns = numColumns;
	}
	 
	public int getNumColumns() {
		return mNumColumns;
	}
	 
	// set photo item height
	public void setItemHeight(int height) {
		Log.v("TTT", "******height = " + height);
		if (height == mItemHeight) {
			return;
		}
		mItemHeight = height;
		mImageViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, mItemHeight);
		notifyDataSetChanged();
	}
	
	public int getItemHeight() {
		return mItemHeight;
	}
	
//	- See more at: http://techiedreams.com/android-custom-gridview-scalable-auto-adjusting-col-width/#sthash.C1ovmI0a.dpuf
	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View v = convertView;
		if (convertView == null) {
			v = mInflater.inflate(R.layout.image_view, parent, false);
			holder = new ViewHolder();
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		try {
			holder.imageView = (ImageView) v.findViewById(R.id.imageView);
			holder.imageViewCamera = (ImageView) v.findViewById(R.id.imageViewCamera);
			holder.imgIsVideo = (ImageView) v.findViewById(R.id.imgIsVideo);
			holder.layoutCamera = (RelativeLayout) v.findViewById(R.id.layoutCamera);
			holder.layoutCamera.setVisibility(View.GONE);
	        holder.layoutSelectImage = (RelativeLayout) v.findViewById(R.id.layoutSelectImage);
	        holder.layoutTrasparent = (LinearLayout)v.findViewById(R.id.layoutTrasparent);
	        holder.imageView.setLayoutParams(mImageViewLayoutParams);
	        holder.layoutSelectImage.setLayoutParams(mImageViewLayoutParams);
	        holder.layoutTrasparent.setLayoutParams(mImageViewLayoutParams);
	        holder.imageViewCamera.setLayoutParams(mImageViewLayoutParams);
//			holder.imgIsVideo.setLayoutParams(mImageViewLayoutParams);
	        
//	        Log.v("TTT", "width = " + holder.imageView.getLayoutParams().width + "  " + holder.imageView.getLayoutParams().height + "  " + mItemHeight);
	        if(holder.imageView.getLayoutParams().width != mItemHeight || holder.imageView.getLayoutParams().height != mItemHeight)
	        	holder.imageView.setLayoutParams(mImageViewLayoutParams);

//			if(holder.imgIsVideo.getLayoutParams().width != mItemHeight || holder.imgIsVideo.getLayoutParams().height != mItemHeight)
//				holder.imgIsVideo.setLayoutParams(mImageViewLayoutParams);
	        
	        if(holder.layoutSelectImage.getLayoutParams().width != mItemHeight || holder.layoutSelectImage.getLayoutParams().height != mItemHeight)
	        	holder.layoutSelectImage.setLayoutParams(mImageViewLayoutParams);
//	        
	        if(holder.layoutTrasparent.getLayoutParams().width != mItemHeight || holder.layoutTrasparent.getLayoutParams().height != mItemHeight)
	        	holder.layoutTrasparent.setLayoutParams(mImageViewLayoutParams);
	        
	        if(holder.imageViewCamera.getLayoutParams().width != mItemHeight || holder.imageViewCamera.getLayoutParams().height != mItemHeight)
	        	holder.imageViewCamera.setLayoutParams(mImageViewLayoutParams);
	        
	        holder.layoutSelectImage.setVisibility(View.GONE);
	        
	        holder.imageView.setTag(position);
			if(position == 0) {
				holder.imageViewCamera.setVisibility(View.VISIBLE);
				holder.imageView.setVisibility(View.GONE);
				holder.imgIsVideo.setVisibility(View.GONE);
				
				holder.imageViewCamera.setImageResource(R.drawable.img_gallery_camera);
				
				Bitmap scaledBitmap = ((BitmapDrawable) holder.imageViewCamera.getDrawable()).getBitmap();
				holder.imageViewCamera.setImageBitmap(Bitmap.createScaledBitmap(scaledBitmap, width/3, width/3, false));
			}
			else {
				holder.imageViewCamera.setVisibility(View.GONE);
				holder.imageView.setVisibility(View.VISIBLE);
//				holder.imgIsVideo.setVisibility(View.VISIBLE);

				if(albumImages.get(position-1).getMediaType().contains("video")){
					holder.imgIsVideo.setVisibility(View.VISIBLE);

//					holder.imageView.setImageBitmap(albumImages.get(position-1).getThumbBitmap());

					if(albumImages.get(position-1).getThumbBitmap() == null){
						Uri uri = Uri.parse("file://"+albumImages.get(position-1).getAlbumImages());
						Bitmap bMap = ThumbnailUtils.createVideoThumbnail(uri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
						holder.imageView.setImageBitmap(bMap);
						albumImages.get(position-1).setThumbBitmap(bMap);
					}else{
						holder.imageView.setImageBitmap(albumImages.get(position-1).getThumbBitmap());
					}

				}else{
					holder.imgIsVideo.setVisibility(View.GONE);


					try {
//						Log.v("TTT", "Image PATH...." + albumImages.get(position-1).getAlbumImages());
						Uri uri = Uri.parse("file://"+albumImages.get(position-1).getAlbumImages());
						if(mItemHeight != 0)
							Picasso.with(mContext).load(uri).resize(mItemHeight, mItemHeight).centerCrop().error(R.drawable.nophoto_album_thumb_normal).placeholder(R.drawable.nophoto_album_thumb_normal).into(holder.imageView); // resize(220, 220).
						else
							Picasso.with(mContext).load(uri).resize(150, 150).centerCrop().error(R.drawable.nophoto_album_thumb_normal).placeholder(R.drawable.nophoto_album_thumb_normal).into(holder.imageView); // resize(220, 220).

					} catch (Exception e) {
						e.printStackTrace();
					}
					catch (OutOfMemoryError e) {
						e.printStackTrace();
					}

				}


			}
			
			if(position > 0) {
				if(albumImages.get(position-1).isSelected())
					holder.layoutSelectImage.setVisibility(View.VISIBLE);
				else
					holder.layoutSelectImage.setVisibility(View.GONE);
			}
			
			holder.imageViewCamera.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					Log.v("TTT", "camera select");
					if(!isLimit) {
						int permission = Utility.checkExternalPermission(mContext, Manifest.permission.CAMERA);
						if (permission != PackageManager.PERMISSION_GRANTED) {
							ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, Constants.CAMERA_PERMISSION_REQUEST_CODE);
						}
						else {
							holder.layoutTrasparent.setVisibility(View.GONE);
							((GalleryPostScrollActivity) mContext).mHighQualityImageUri = Utility.generateTimeStampPhotoFileUri(mContext);
							Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
							i.putExtra(MediaStore.EXTRA_OUTPUT, ((GalleryPostScrollActivity) mContext).mHighQualityImageUri);
							 mContext.startActivityForResult(i, Constants.SELECT_CAMERA_IMAGE);





//							Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//							if (intent.resolveActivity(mContext.getPackageManager()) != null) {
//////            fileTemp = ImageUtils.getOutputMediaFile();
////								ContentValues values = new ContentValues(1);
////								values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
////								Uri fileUri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//////            if (fileTemp != null) {
//////            fileUri = Uri.fromFile(fileTemp);
//								intent.putExtra(MediaStore.EXTRA_OUTPUT, ((GalleryPostScrollActivity) mContext).mHighQualityImageUri);
//								intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//								mContext.startActivityForResult(intent, Constants.SELECT_CAMERA_IMAGE);
////            } else {
////                Toast.makeText(this, getString(R.string.error_create_image_file), Toast.LENGTH_LONG).show();
////            }
//							} else {
//								Toast.makeText(mContext, "No Camera", Toast.LENGTH_LONG).show();
//							}




						}
					}
					else
						holder.layoutTrasparent.setVisibility(View.VISIBLE);
				}
			});

			holder.imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					Log.v("TTT", "v = " + v.getTag());
					int pos = (Integer) v.getTag();
					if(pos > 0) {
						Log.v("TTT", "isLimit = " + isLimit);
						if(isLimit && albumImages.get(pos-1).isSelected()) {
							albumImages.get(pos-1).setSelected(false);
							holder.layoutSelectImage.setVisibility(View.GONE);
							((GalleryPostScrollActivity) mContext).addPickedImage(albumImages.get(pos-1), true);
							if(albumImages.get(pos-1).getMediaType().contains("video")) {
								GalleryPostScrollActivity.isVideoPicked = false;
							}
						}
						else if (!isLimit) {
							if(albumImages.get(pos-1).isSelected()) {
								albumImages.get(pos-1).setSelected(false);
								holder.layoutSelectImage.setVisibility(View.GONE);
								((GalleryPostScrollActivity) mContext).addPickedImage(albumImages.get(pos-1), true);
								if(albumImages.get(pos-1).getMediaType().contains("video")) {
									GalleryPostScrollActivity.isVideoPicked = false;
								}
							}
							else {
								Log.v("TTT", albumImages.get(pos-1).getMediaType() + ".......isLimit   isVideoPicked = " + GalleryPostScrollActivity.isVideoPicked);
								if(albumImages.get(pos-1).getMediaType().contains("video")){
									Log.v("TTT", "isLimit   isVideoPicked = contains video" + GalleryPostScrollActivity.isVideoPicked);
									if(!GalleryPostScrollActivity.isVideoPicked){
										albumImages.get(pos-1).setSelected(true);
										holder.layoutSelectImage.setVisibility(View.VISIBLE);
										((GalleryPostScrollActivity) mContext).addPickedImage(albumImages.get(pos-1), false);
										GalleryPostScrollActivity.isVideoPicked = true;
									}else{
										Toast.makeText(mContext, "You can select only 1 video", Toast.LENGTH_SHORT).show();
									}
								}else{
									albumImages.get(pos-1).setSelected(true);
									holder.layoutSelectImage.setVisibility(View.VISIBLE);
									((GalleryPostScrollActivity) mContext).addPickedImage(albumImages.get(pos-1), false);
								}


							}
						}
					}
				}
			});

			if(position > 0) {
				if(isLimit && !albumImages.get(position-1).isSelected()) 
					holder.layoutTrasparent.setVisibility(View.VISIBLE);
				else 
					holder.layoutTrasparent.setVisibility(View.GONE);
			}
			else {
				if(isLimit) 
					holder.layoutTrasparent.setVisibility(View.VISIBLE);
				else 
					holder.layoutTrasparent.setVisibility(View.GONE);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return v;
	}


	String mCurrentPhotoPath;
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

	public class ViewHolder {
		ImageView imageView, imageViewCamera, imgIsVideo;
		RelativeLayout layoutCamera, layoutSelectImage;
		LinearLayout layoutTrasparent;
    }
	
	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getCount() {
		return albumImages.size()+1;
	}

	public void refreshList() {
		for (int i = 0; i < albumImages.size(); i++) {
			albumImages.get(i).setSelected(false);
		}
		notifyDataSetChanged();
	}

	public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	public void updateList(String path) {
//		Log.v("TTT", "updateList...... " + path + "   "+ albumImages.size());
		for (int i = 0; i < albumImages.size(); i++) {
//			Log.v("TTT", "albumImages.get(i).getAlbumImages() = "  + albumImages.get(i).getAlbumImages());
			if(albumImages.get(i).getAlbumImages().toString().equalsIgnoreCase(path)) {
				albumImages.get(i).setSelected(false);
				notifyDataSetChanged();
				break;
			}
		}
	}

}