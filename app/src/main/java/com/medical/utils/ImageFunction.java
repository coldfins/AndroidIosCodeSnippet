package com.medical.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageFunction {
	public Bitmap getPhoto(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
		// return BitmapFactory.decodeByteArray(image, offset, length)

	}

	public String encode(byte[] b) {
		String img = Base64.encodeToString(b, 0);
		return img;
	}
	
	public Bitmap decode(String str) {
		byte[] b = Base64.decode(str, 0);
		Bitmap bm = getPhoto(b);
		return bm;
	}
}
