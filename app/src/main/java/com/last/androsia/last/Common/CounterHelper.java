package com.last.androsia.last.Common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Samoustique on 07/01/2017.
 */

public class CounterHelper {
    static public SpannableString formatCounter(TagItem item){
        switch(item.getType()){
            case BOOK:
                return new SpannableString(String.valueOf((int) item.getCtrSeen()));
            case SCREEN:
                return formatScreenCounter(item, 0.65f);
        }
        return new SpannableString("");
    }

    static private SpannableString formatScreenCounter(TagItem item, float fontSize){
        int real = (int) item.getCtrSeen();
        int decimal = (int) (item.getCtrSeen() * 100 - real * 100);

        if(decimal == 0){
            // This is a movie
            String strCounter = String.valueOf(real);
            SpannableString counter = new SpannableString(strCounter);
            //counter.setSpan(new RelativeSizeSpan(fontSize), 0, strCounter.length(), 0);
            return counter;
        }

        // This is a Serie or Anim
        int green = Color.rgb(79, 49, 23);

        String strCounter = String.format("S%02dE%02d", real, decimal);
        SpannableString counter = new SpannableString(strCounter);
        counter.setSpan(new ForegroundColorSpan(green), 0, 1, 0);
        counter.setSpan(new ForegroundColorSpan(green), 3, 4, 0);
        counter.setSpan(new RelativeSizeSpan(fontSize), 0, strCounter.length(), 0);

        return counter;
    }

    static public Typeface getFont(Context context) {
        return Typeface.createFromAsset(
                context.getApplicationContext().getAssets(),
                String.format(Locale.US, "fonts/%s", "old_stamper.ttf"));
    }
}
