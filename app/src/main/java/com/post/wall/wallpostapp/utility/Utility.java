package com.post.wall.wallpostapp.utility;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.post.wall.wallpostapp.JoinActivity;
import com.post.wall.wallpostapp.R;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by ved_pc on 2/18/2017.
 */

public class Utility {
    public static LruCache<String, Bitmap> mMemoryCache;


    public static RestInterface getRetrofitInterface(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
		mOkHttpClient.setConnectTimeout(50, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(50, TimeUnit.SECONDS);


        GetUrlClass url = new GetUrlClass();
        RestAdapter adapter = new RestAdapter.Builder().setClient(new OkClient(mOkHttpClient)).setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
        return  adapter.create(RestInterface.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void sendNotification(Context context){
        // Send Notification
        String notificationTitle = "Wall Post WIFI";
        String notificationText = "Join Wifi";
        Intent myIntent = new Intent(context, JoinActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText).setSmallIcon(R.drawable.device_info)
                .setContentIntent(pendingIntent).build();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notification.flags = notification.flags
                | Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);

    }


    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {
                ssid = connectionInfo.getSSID();
            }
            // Get WiFi status MARAKANA
            WifiInfo info = wifiManager.getConnectionInfo();
            String textStatus = "";
            textStatus += "\n\nWiFi Status: " + info.toString();
            String BSSID = info.getBSSID();
            String MAC = info.getMacAddress();

            List<ScanResult> results = wifiManager.getScanResults();
            ScanResult bestSignal = null;
            int count = 1;
            String etWifiList = "";
            for (ScanResult result : results) {
                etWifiList += count++ + ". " + result.SSID + " : " + result.level + "\n" +
                        result.BSSID + "\n" + result.capabilities +"\n" +
                        "\n=======================\n";
            }
            Log.v("TTT", "from SO: \n"+etWifiList);

            // List stored networks
            List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration config : configs) {
                textStatus+= "\n\n" + config.toString();
            }
            Log.v("TTT","from marakana: \n"+textStatus);
        }
        return ssid;
    }

//    public static void unregisterReceiverFromManifest(BootUpReceiver bootUpReceiver, final Context context) {
//        final ComponentName component = new ComponentName(context, String.valueOf(bootUpReceiver));
//        final int status = context.getPackageManager().getComponentEnabledSetting(component);
//        if(status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
//            context.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
//        }
//    }
//
//    public static Uri generateTimeStampPhotoFileUri(Context context) {
//        Log.v("TTT", "generateTimeStampPhotoFileUri Make file11");
//        Uri photoFileUri = null;
//        File outputDir = getPhotoDirectory(context);
//        if (outputDir != null) {
//            Log.v("TTT", "generateTimeStampPhotoFileUri Make file22");
//            Time t = new Time();
//            t.setToNow();
//            File photoFile = new File(outputDir, System.currentTimeMillis() + ".jpg");
//            photoFileUri = Uri.fromFile(photoFile);
//        }
//        return photoFileUri;
//    }
//
//    public static File getPhotoDirectory(Context context) {
//        File outputDir = null;
//        String externalStorageStagte = Environment.getExternalStorageState();
//        if (externalStorageStagte.equals(Environment.MEDIA_MOUNTED)) {
//            File photoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//            outputDir = new File(photoDir, context.getString(R.string.app_name));
//            if (!outputDir.exists())
//                if (!outputDir.mkdirs()) {
//                    Toast.makeText(context, "Failed to create directory " + outputDir.getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                    outputDir = null;
//                }
//        }
//        return outputDir;
//    }

    public static int checkExternalPermission(Context context, String permissionName) {
        int permission = 0;
        try {
            permission = ContextCompat.checkSelfPermission(context, permissionName);//Manifest.permission.WRITE_EXTERNAL_STORAGE
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return permission;
    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static Uri generateTimeStampPhotoFileUri(Context context) {
        Log.v("TTT", "generateTimeStampPhotoFileUri Make file11");
        Uri photoFileUri = null;
        File outputDir = getPhotoDirectory(context);
        if (outputDir != null) {
            Log.v("TTT", "generateTimeStampPhotoFileUri Make file22");
            Time t = new Time();
            t.setToNow();
            File photoFile = new File(outputDir, System.currentTimeMillis() + ".jpg");
            photoFileUri = Uri.fromFile(photoFile);
        }
        return photoFileUri;
    }

    public static File getPhotoDirectory(Context context) {
        File outputDir = null;
        String externalStorageStagte = Environment.getExternalStorageState();
        if (externalStorageStagte.equals(Environment.MEDIA_MOUNTED)) {
            File photoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            outputDir = new File(photoDir, context.getString(R.string.app_name));
            if (!outputDir.exists())
                if (!outputDir.mkdirs()) {
                    Toast.makeText(context, "Failed to create directory " + outputDir.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    outputDir = null;
                }
        }
        return outputDir;
    }

    public static void AnimationEnableSendPost(final ImageView view, final Context context) {
        // load the animation
        Animation animZoomOut = AnimationUtils.loadAnimation(context, R.anim.zoomout);
        animZoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation animZoomIn = AnimationUtils.loadAnimation(context, R.anim.zoomin);
                animZoomIn.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setImageResource(R.drawable.img_post_comment_social);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                view.startAnimation(animZoomIn);
            }
        });
        view.startAnimation(animZoomOut);
    }

    public static void AnimationDisableSendPost(final ImageView view, final Context context) {
        // load the animation
        Animation animZoomOut = AnimationUtils.loadAnimation(context,
                R.anim.zoomout);
        animZoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation animZoomIn = AnimationUtils.loadAnimation(context,
                        R.anim.zoomin);
                animZoomIn.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setImageResource(R.drawable.comment_social);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                view.startAnimation(animZoomIn);
            }
        });
        view.startAnimation(animZoomOut);
    }

    public static void showKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    public static void toastExternalStorage(Context context) {
        Toast.makeText(context, "You've turned off permission for the storage. To edit, go to Apps settings.", Toast.LENGTH_LONG).show();
    }

    public static void toastCameraPermission(Context context) {
        Toast.makeText(context, "You've turned off permission for the camera. To edit, go to Apps settings.", Toast.LENGTH_LONG).show();
    }

    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
//			 context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            Log.v("TTT", "imageFile = " + imageFile + " " + imageFile.getAbsolutePath());
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
            Log.v("TAG", "Exif orientation: " + orientation + "  " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static void Recycle(Bitmap Temp){
        if(Temp!=null){
            Temp.recycle();
            Temp=null;
            System.gc();
        }
    }

    public static boolean CheckAllMarshmallowPermissionForActivityCamera(Context ctx, Activity activity) {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA, ctx, activity))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE, ctx, activity))
            permissionsNeeded.add("Read Storage");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, ctx, activity))
            permissionsNeeded.add("Write Storage");

        Log.i("****message", permissionsNeeded.size() + " ");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);

                Log.i("****message", message);
                ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                        Constants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

                return false;
            }
           /* requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);*/

        }

        return true;


    }

    private static boolean addPermission(List<String> permissionsList, String permission, Context ctx, Activity activity) {
        if (ContextCompat.checkSelfPermission(ctx, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            Log.i("****message", "add" + permission);
            // Check for Rationale Option
           /* if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                return false;
            else*/
            return false;
        }
        return true;
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public static Bitmap modifyOrientation(Bitmap yourBitmap, String image_absolute_path) {

        try {
            ExifInterface ei = new ExifInterface(image_absolute_path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return yourBitmap = rotateImage(yourBitmap, 90);

                case ExifInterface.ORIENTATION_ROTATE_180:
                    return yourBitmap = rotateImage(yourBitmap, 180);

                case ExifInterface.ORIENTATION_ROTATE_270:
                    return yourBitmap = rotateImage(yourBitmap, 270);

                case ExifInterface.ORIENTATION_NORMAL:
                    return yourBitmap;

                default:
                    return yourBitmap;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return yourBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static void showAlert(Context context, String message) {
        try {
//            if(message.contains("timeout") || message.contains("Unable to resolve host") || message.contains("failed to connect")
//                    || message.contains("Handshake") || message.contains("handshake") || message.equalsIgnoreCase("null") || message == null){
//            }else{
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            android.support.v7.app.AlertDialog alert = builder.create();
            alert.show();
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//            pbutton.setTextColor(context.getResources().getColor());
            //}
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static File saveInSdcard(Bitmap finalBitmap) {

        File myDir = new File(Constants.WALL_POST_SDCARD_PATH);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Recycle(finalBitmap);
        }catch (OutOfMemoryError e2) {
            e2.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("TTT", "Images....." + file.getPath());
        return file;
    }

    public static String getLeftDayTime(String challangeDateTime) {
        String str = null;
        try {
             Log.v("TTT", "challangeDateTime ========= " + challangeDateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(challangeDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Log.v("TTT", "convertedDate = " + convertedDate);

            // DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            // cal.setTimeZone(TimeZone.getTimeZone("UTC"));
            cal.setTime(dateFormat.parse(challangeDateTime));
            // Log.v("TTT", "cal = " + cal);

            Calendar currentTime = Calendar.getInstance();
            currentTime.setTimeZone(TimeZone.getTimeZone("UTC"));
            // Log.v("TTT", "currentTime = " + currentTime);

            // Log.v("TTT", "mills = " + currentTime.getTimeInMillis() + "  " +
            // cal.getTimeInMillis());
            long diff = currentTime.getTimeInMillis() - cal.getTimeInMillis();// convertedDate.getTime();
            // Log.v("TTT", "diff = " + diff);
            long second = (long) diff / 1000;
            long minute = (long) second / 60;
            long hours = (long) minute / 60;
            long days = hours / 24;

            // Log.v("TTT", "hours = " + hours + "  minute = " + minute +
            // "  sec = " + second + " days = " + days);
            if (days < 1) {
                str = new SimpleDateFormat("hh:mm a").format(convertedDate);
                //
            } else {
                str = new SimpleDateFormat("MMM dd").format(convertedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String formatDate(String challangeDateTime) {
        String str = null;
        try {
            Log.v("TTT", "challangeDateTime ========= " + challangeDateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(challangeDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(convertedDate);

            str =new SimpleDateFormat("MMM").format(calendar.getTime()) + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static File SaveAndCompressImage(Bitmap finalBitmap) {
        String root = Constants.WALL_POST_SDCARD_PATH;
        File myDir = new File(root);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            long length = file.length();
            length = length / 1024;
            if (length < 5000) {
                out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } else if (length > 5000 && length < 10000) {
                out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 95, out);
                out.flush();
                out.close();
            } else {
                out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }




    /**
     * Saving image bitmap into memory
     *
     * @param key
     * @param bitmap
     */
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache == null) {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            Utility.mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
            };
        }
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * Getting image from Memory
     *
     * @param key
     * @return
     */
    public static Bitmap getBitmapFromMemCache(String key) {
        if (mMemoryCache == null) {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            Utility.mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
            };
        }
        return mMemoryCache.get(key);
    }

    /**
     * Return Larger item size from adapter
     *
     * @param context
     * @param adapter
     * @return int to get widest view
     */
    public static int getWidestView(Context context, Adapter adapter) {
        int maxWidth = 0;
        View view = null;
        FrameLayout fakeParent = new FrameLayout(context);
        for (int i = 0, count = adapter.getCount(); i < count; i++) {
            view = adapter.getView(i, view, fakeParent);
            view.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            int width = view.getMeasuredWidth();
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static void setWhiteToolBar(Toolbar toolbar, Context ctx, int color) {
        // Drawable upArrow = ctx.getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        Drawable upArrow= ContextCompat.getDrawable(ctx, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
    }



}
