package com.panoramagl.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.apache.commons.httpclient.util.HttpURLConnection;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.panoramagl.PLIPanorama;
import com.panoramagl.PLImage;
import com.panoramagl.PLSpherical2Panorama;
import com.panoramagl.PLSphericalPanorama;
import com.panoramagl.PLView;
import com.panoramagl.enumerations.PLCubeFaceOrientation;
import com.panoramagl.example.R;
import com.panoramagl.transitions.PLTransitionBlend;
import com.panoramagl.utils.PLUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class PanoramaGLActivity extends PLView
{
	Bitmap image;
	/**init methods*/
	public static final String TAG = "+++++++++++++++";
	ImageLoader imageLoader;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//Load panorama
		
//		URL url = null;
//		try {
//			url = new URL("http://www.pananoias.com/wp-content/gallery/equirectangulares/rocodromo.jpg");
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//		image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		PLSpherical2Panorama panorama = new PLSpherical2Panorama();
	panorama.getCamera().lookAt(30.0f, 90.0f);
       panorama.setImage(new PLImage(PLUtils.getBitmap(this, R.raw.panoramaing),false));
       this.setPanorama(panorama);
//        
		
		
//		URL url = null;
//		try {
//			url = new URL("http://www.pananoias.com/wp-content/gallery/equirectangulares/rocodromo.jpg");
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//		image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//		SaveImage(image);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
		//image=getBitmapFromURL("http://www.pananoias.com/wp-content/gallery/equirectangulares/rocodromo.jpg");
		//SaveImage(image);
		
        
		try
		{
        final PLSphericalPanorama pano=new PLSphericalPanorama();
        
       
      // image=getBitmapFromURL("http://www.pananoias.com/wp-content/gallery/equirectangulares/rocodromo.jpg");
       //SaveImage(image);
        
        imageLoader.loadImage("http://www.pananoias.com/wp-content/gallery/equirectangulares/rocodromo.jpg", new SimpleImageLoadingListener()  
        { 
            @Override 
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
            { 
              //  cubicPanorama.setImage(new PLImage(loadedImage, false), PLCubeFaceOrientation.PLCubeFaceOrientationFront);
                pano.setImage(new PLImage(loadedImage,false));
                Toast.makeText(getApplicationContext(),loadedImage.getHeight()+"",Toast.LENGTH_SHORT).show();
                Log.d("bitmappppppp",loadedImage.getHeight()+"");
                //  SaveImage(loadedImage);
            } 
            
        });
       // this.setPanorama(pano);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//Log.d("logg",e.printStackTrace()+"");
			
		}
		
		
		// new DownloadImageTask(this).execute("http://www.pananoias.com/wp-content/gallery/equirectangulares/rocodromo.jpg");

        
        
	}
//	 private class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {
//
//
//	        @Override
//	        protected Bitmap doInBackground(String... params) {
//
//	            System.out.println("doInBackground");
//
//	            Bitmap bitmap = null;
//
//	            bitmap = AppMethods.downloadImage("");
//	       
//	            return bitmap;
//	        }
//
//	        @Override
//	        protected void onPostExecute(Bitmap bitmap) {
//
//	            System.out.println("bitmap" + bitmap);
//
//	        }
//	    }

	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	private void SaveImage(Bitmap finalBitmap) {

		   String root = Environment.getExternalStorageDirectory().toString();
		   File myDir = new File(root + "/saved_images");    
		   myDir.mkdirs();
		   Random generator = new Random();
		   int n = 10000;
		   n = generator.nextInt(n);
		   String fname = "Image-"+ n +".jpg";
		   File file = new File (myDir, fname);
		   if (file.exists ()) file.delete (); 
		   try {
		       FileOutputStream out = new FileOutputStream(file);
		       finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		       out.flush();
		       out.close();

		   } catch (Exception e) {
		       e.printStackTrace();
		   }
		}

	
	
//	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//
//		PanoramaGLActivity pan;
//
//	    public DownloadImageTask(PanoramaGLActivity act) {
//	        this.pan = act;
//	    }
//
//	    protected Bitmap doInBackground(String... urls) {
//	        String urldisplay = urls[0];
//	        Bitmap mIcon11 = null;
//	        try {
//	            InputStream in = new java.net.URL(urldisplay).openStream();
//	            mIcon11 = BitmapFactory.decodeStream(in);
//	        } catch (Exception e) {
//	            Log.e("Error", e.getMessage());
//	            e.printStackTrace();
//	        }
//	        return mIcon11;
//	    }
//
//	    protected void onPostExecute(Bitmap result) {
//	        Log.d(TAG,"onPostExecute");
//	        PLSpherical2Panorama panorama = new PLSpherical2Panorama();
//	        panorama.setImage(new PLImage(result));
//	        pan.setPanorama(panorama);
//	    }
//	}   
	
//	private void loadPanorama()
//    {
//        try
//        {
//            final Context context = this.getApplicationContext();
//            PLIPanorama panorama = null;
//            //Lock panoramic view
//            this.setLocked(true);
//            
//
//            Toast.makeText(context, "image loader start", Toast.LENGTH_LONG).show();
//            imageLoader.loadImage("http://104.245.38.221/www/200/000/005/2000000057042/f/z_0/00.jpg", new SimpleImageLoadingListener()  
//            { 
//                @Override 
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
//                { 
//                    cubicPanorama.setImage(new PLImage(loadedImage, false), PLCubeFaceOrientation.PLCubeFaceOrientationFront);
//                } 
//            }); 
//            imageLoader.loadImage("http://104.245.38.221/www/200/000/005/2000000057042/b/z_0/00.jpg", new SimpleImageLoadingListener()  
//            { 
//                @Override 
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
//                { 
//                    cubicPanorama.setImage(new PLImage(loadedImage, false), PLCubeFaceOrientation.PLCubeFaceOrientationBack);
//                } 
//            }); 
//            imageLoader.loadImage("http://104.245.38.221/www/200/000/005/2000000057042/l/z_0/00.jpg", new SimpleImageLoadingListener()  
//            { 
//                @Override 
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
//                { 
//                    cubicPanorama.setImage(new PLImage(loadedImage, false), PLCubeFaceOrientation.PLCubeFaceOrientationLeft);
//                } 
//            }); 
//            imageLoader.loadImage("http://104.245.38.221/www/200/000/005/2000000057042/r/z_0/00.jpg", new SimpleImageLoadingListener()  
//            { 
//                @Override 
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
//                { 
//                    cubicPanorama.setImage(new PLImage(loadedImage, false), PLCubeFaceOrientation.PLCubeFaceOrientationRight);
//                } 
//            }); 
//            imageLoader.loadImage("http://104.245.38.221/www/200/000/005/2000000057042/u/z_0/00.jpg", new SimpleImageLoadingListener()  
//            { 
//                @Override 
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
//                { 
//                    cubicPanorama.setImage(new PLImage(loadedImage, false), PLCubeFaceOrientation.PLCubeFaceOrientationUp);
//                } 
//            }); 
//            imageLoader.loadImage("http://104.245.38.221/www/200/000/005/2000000057042/d/z_0/00.jpg", new SimpleImageLoadingListener()  
//            { 
//                @Override 
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) 
//                { 
//                    cubicPanorama.setImage(new PLImage(loadedImage, false), PLCubeFaceOrientation.PLCubeFaceOrientationDown);
//                } 
//            }); 
//
//            panorama = cubicPanorama;
//            Toast.makeText(context, "image loader end", Toast.LENGTH_LONG).show();
//
//            if(panorama != null)
//            {
//                //Set camera rotation
//                panorama.getCamera().lookAt(0.0f, 170.0f);
//                //Add a hotspot
//                //panorama.addHotspot(new PLHotspot(1, new PLImage(PLUtils.getBitmap(context, R.raw.hotspot), false), 0.0f, 170.0f, 0.05f, 0.05f));
//                //Reset view
//                this.reset();
//                //Load panorama
//                this.startTransition(new PLTransitionBlend(2.0f), panorama); //or use this.setPanorama(panorama);
//            }
//            //Unlock panoramic view
//            this.setLocked(false);
//        }
//        catch(Throwable e)
//        {
//            Toast.makeText(this.getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
//        }
//    }

	
	/**
     * This event is fired when root content view is created
     * @param contentView current root content view
     * @return root content view that Activity will use
     */
	@Override
	protected View onContentViewCreated(View contentView)
	{
		//Load layout
		ViewGroup mainView = (ViewGroup)this.getLayoutInflater().inflate(R.layout.activity_main, null);
		//Add 360 view
    	mainView.addView(contentView, 0);
    	//Return root content view
		return super.onContentViewCreated(mainView);
	}
}