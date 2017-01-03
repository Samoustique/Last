package com.last.androsia.last;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class ImageLoader {
    public void loadImage(String url, Context context, final ImageView imageView) {
        Picasso.with(context).load(url).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                Bitmap combinedBitmap;
                combinedBitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight() / 3 + source.getHeight(), source.getConfig());

                Canvas combinedCanvas = new Canvas(combinedBitmap);
                combinedCanvas.drawBitmap(source, 0f, 0f, null);

                Matrix matrix = new Matrix();
                matrix.postRotate(180);
                matrix.preScale(-1, 1);
                matrix.postTranslate(0, source.getHeight() * 2);

                combinedCanvas.setMatrix(matrix);
                return combinedBitmap;
            }

            @Override
            public String key() {
                return ImageLoader.class.getName() + ".Transformation";
            }
        }).fit().error(android.R.drawable.sym_contact_card).placeholder(android.R.drawable.sym_contact_card).into(imageView);
    }
}
