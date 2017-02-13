package com.last.androsia.last;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SPhilipps on 2/13/2017.
 */

public class FilesUtility {
    public static void copyToLast(File src) throws IOException {
        InputStream in = new FileInputStream(src);

        File dstFile = FilesUtility.createNewImageInLast();
        OutputStream out = new FileOutputStream(dstFile);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static File createNewImageInLast(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        timeStamp += ".jpg";
        String pictureImagePath = FilesUtility.getLastFolderPath() + timeStamp;
        return new File(pictureImagePath);
    }

    public static void createLastFolder() {
        final File lastDir = new File(getLastFolderPath());
        lastDir.mkdir();
    }

    public static String getLastFolderPath(){
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return storageDir.getAbsolutePath() + "/Last/";
    }
}
