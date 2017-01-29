package com.last.androsia.last;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class ImageLoader {
    public void loadImage(String url, Context context, final ImageView imageView) {
        Picasso.with(context).load(url)
                .error(R.drawable.logo)
                .placeholder(android.R.drawable.stat_notify_error)
                .into(imageView);
    }
}
