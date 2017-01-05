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
                .fit()
                //.resize(200,200)
                .error(android.R.drawable.sym_contact_card)
                .placeholder(android.R.drawable.sym_contact_card)
                .into(imageView);
    }
}
