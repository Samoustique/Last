package com.last.androsia.last.Common;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SPhilipps on 2/13/2017.
 */

public class FilesUtility {
    public static String saveToInternalSorage(String url, Context ctx) throws Exception {
        ContextWrapper cw = new ContextWrapper(ctx);
        File directory = cw.getDir("Last", Context.MODE_PRIVATE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imagePath = new File(directory, timeStamp + ".jpg");

        FileOutputStream output;
        output = new FileOutputStream(imagePath);

        File source = new File(url);
        if (source.exists()) {
            FileChannel src = new FileInputStream(source).getChannel();
            FileChannel dst = output.getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        } else{
            return "";
        }

        output.close();

        return imagePath.getPath();
    }

    public static File createImageFile() throws IOException {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
        return new File(storageDir.getAbsolutePath() + "/" + timeStamp);
    }

    public static Bitmap decodeSampledBitmapFromResource(String path) {
        int reqWidth,reqHeight;
        reqWidth = 400;
        reqWidth = (reqWidth/5)*2;
        reqHeight = reqWidth;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options,
            int reqWidth,
            int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
}
