package com.last.androsia.last;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Samoustique on 12/02/2017.
 */

public class BitmapUtility {

    public static byte[] getBytes(Bitmap bitmap, int compression) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, compression, stream);
        return stream.toByteArray();
    }

    public static byte[] getBytes(Bitmap bitmap) {
        return getBytes(bitmap, 50);
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
