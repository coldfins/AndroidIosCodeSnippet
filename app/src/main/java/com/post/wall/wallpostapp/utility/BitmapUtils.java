package com.post.wall.wallpostapp.utility;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;


import com.post.wall.wallpostapp.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {

    public static byte[] getBytes(Bitmap bitmap) {
        Bitmap bm = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    public static String encode(byte[] b) {
        String img = Base64.encodeToString(b, 0);
        return img;

    }

    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            return null;
        }

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

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static File storeImage(Bitmap imageData, String filename) {
        // get path to external storage (SD card)
        String filePath = "";
        String iconsStoragePath = Environment.getExternalStorageDirectory() + File.separator;
        File sdIconStorageDir = new File(iconsStoragePath);

        // create storage directories, if they don't exist
        if (!sdIconStorageDir.exists())
            sdIconStorageDir.mkdirs();

        try {
            filePath = sdIconStorageDir.toString() + filename;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            BufferedOutputStream bos = new BufferedOutputStream(
                    fileOutputStream);

            // choose another format if PNG doesn't suit you
            imageData.compress(Bitmap.CompressFormat.JPEG, 40, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return sdIconStorageDir;
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return sdIconStorageDir;
        }

        return sdIconStorageDir;
    }

    public static File getPhotoDirectory(Context context) {
        File outputDir = null;
        String externalStorageStagte = Environment.getExternalStorageState();
        if (externalStorageStagte.equals(Environment.MEDIA_MOUNTED)) {
            File photoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            outputDir = new File(photoDir, context.getString(R.string.app_name));
            if (!outputDir.exists())
                if (!outputDir.mkdirs()) {
                    Toast.makeText(
                            context,
                            "Failed to create directory "
                                    + outputDir.getAbsolutePath(),
                            Toast.LENGTH_SHORT).show();
                    outputDir = null;
                }
        }
        return outputDir;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
//				int index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
//				Log.v("TTT","index = " +index + " \n = " +cursor.getString(index));
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public static boolean isNewGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.contentprovider".equals(uri
                .getAuthority());
    }

    public static String getImageUrlWithAuthority(Context context, Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return writeToTempImageAndGetPathUri(context, bmp).toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean isGoogleAutoBackupPhotosUri(Uri uri) {
        return "com.google.android.gallery3d.provider".equals(uri.getAuthority());
    }

    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            Log.v("TTT", "in if isKitKat = " + isKitKat);
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                Log.v("TTT", "in isExternalStorageDocument");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                Log.v("TTT", "in isDownloadsDocument");
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                Log.v("TTT", "in isMediaDocument");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            Log.v("TTT", "in else = " + uri);
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                Log.v("TTT", "is google" + uri.getLastPathSegment());
                return uri.getLastPathSegment();
            }

            // Return the remote address
            if (isNewGooglePhotosUri(uri)) {
                // "content://com.google.android.apps.photos.contentprovider/0/1/mediaKey%3A%2FAF1QipNgWoNETN7iCHV8Gt87-4laJJThhlfjAs-uAb79/ACTUAL/1557269611"
                try {

                    String newUri = getImageUrlWithAuthority(context, uri);
                    Log.v("TTT", "getDataColumn = " + getDataColumn(context, Uri.parse(newUri), null, null) + "  " + newUri);
                    return getDataColumn(context, Uri.parse(newUri), null, null);

                    // } catch (UnsupportedEncodingException e) {
                    // e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Return the remote address
            if (isGoogleAutoBackupPhotosUri(uri)) {
                Log.v("TTT",
                        "is google auto backup photo = "
                                + uri.getLastPathSegment());
                return uri.getLastPathSegment();
            }

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            Log.v("TTT", "it's file");
            return uri.getPath();
        }

        return null;
    }

}
