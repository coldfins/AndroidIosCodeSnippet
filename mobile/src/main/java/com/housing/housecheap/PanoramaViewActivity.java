package com.housing.housecheap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.panoramagl.PLImage;
import com.panoramagl.PLSphericalPanorama;
import com.panoramagl.PLView;

import java.io.InputStream;
import java.net.URL;

public class PanoramaViewActivity extends PLView {

	String url;
	public ProgressDialog progressDialog;
	/** init methods */

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			Intent i = getIntent();
			if (i == null) {
				url = "http://www.pianetacellulare.it/UserFiles/image/Android/Jelly%20Bean/photo_sphere_esempio.jpg";
			} else {
				url = i.getStringExtra("panorama_url");
			}
		} catch (Exception e) {

		}
		new DownloadImageTask(PanoramaViewActivity.this).execute(url);
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    PanoramaViewActivity pan;

	    public DownloadImageTask(PanoramaViewActivity act) {
	        this.pan = act;
	    }
	    @Override
	    protected void onPreExecute() {
	    	super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
	    }
	    
	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        if (progressDialog.isShowing())
	        	progressDialog.dismiss();
	       
	        PLSphericalPanorama panorama = new PLSphericalPanorama();
	        panorama.setImage(new PLImage(getResizedBitmap(result, 1024)));
	        pan.setPanorama(panorama);
	    }
	}

	/**
	 * Resize bitmap to given size
	 * @param image image's bitmap
	 * @param maxSize Max image size
	 * @return return resized bitmap
	 */
	 public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
	        int width = image.getWidth();
	        int height = image.getHeight();

	        float bitmapRatio = (float)width / (float) height;
	        if (bitmapRatio > 0) {
	            width = maxSize;
	            height = (int) (width / bitmapRatio);
	        } else {
	            height = maxSize;
	            width = (int) (height * bitmapRatio);
	        }
	        return Bitmap.createScaledBitmap(image, width, height, true);
	    }
	 
	 @Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
