package com.post.wall.wallpostapp.utility;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by ved_pc on 2/18/2017.
 */

public class Constants {

    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public static final int REQUEST_CAMERA = 98;
    public static final int SELECT_FILE = 99;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int WRITE_EXTERNAL_REQUEST_CODE = 101;



    public static final int  CAMERA_PERMISSION_REQUEST_CODE = 102;
    public static int SELECT_CAMERA_IMAGE = 2000;
    public static Uri selectedImage;
    public static final int  START_SIMPLE_POST = 3000;
    public static final int  START_GALLERY_POST = 4000;
    public static final int  SHARE_POST = 5000;
    public static final int  REPLY_POST = 6000;
    public static final int  COMMENT_POST = 7000;



//    public static int TAKE_PHOTO = 1;
//    public static int YOUR_PHOTO = 2;
//    public static int ADD_MORE_PHOTO_FLAG = 3000;
//    public static final int  CAMERA_PERMISSION_REQUEST_CODE = 102;
//    public static final int WRITE_EXTERNAL_REQUEST_CODE = 101;
//    public static int SELECT_PICTURE = 1000;
//    public static int SELECT_CAMERA_IMAGE = 2000;
//
//
//
//    public static Uri selectedImage;
//    public static File profileFile=null;
//
    public static String WALL_POST_SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WallPost";
    public static String NO_INTERNET_MESSAGE = "Internet Connection is not available.";
}
