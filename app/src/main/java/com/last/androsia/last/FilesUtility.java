package com.last.androsia.last;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SPhilipps on 2/13/2017.
 */

public class FilesUtility {
    public static String saveToInternalSorage(Bitmap bitmapImage, Context ctx) throws Exception {
        ContextWrapper cw = new ContextWrapper(ctx);
        File directory = cw.getDir("Last", Context.MODE_PRIVATE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imagePath = new File(directory, timeStamp + ".jpg");

        FileOutputStream fos;
        fos = new FileOutputStream(imagePath);
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.close();

        return imagePath.getPath();
    }

    public static File createImageFile() throws IOException {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
        return new File(storageDir.getAbsolutePath() + "/" + timeStamp);
    }
}
