package com.last.androsia.last;

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
    static public SpannableString formatCounter(TagsListItem item){
        switch(item.getType()){
            case BOOK:
                return new SpannableString(String.valueOf((int) item.getCtrSeen()));
            case SCREEN:
                return formatScreenCounter(item, 0.65f);
        }
        return new SpannableString("");
    }

    static public void centerCounter(TextView txtView){
    RelativeLayout.LayoutParams lp =
            (RelativeLayout.LayoutParams) txtView.getLayoutParams();
    int top = lp.topMargin;
    int left = lp.leftMargin;

    switch(txtView.length()){
        case 1:
            left += 55;
            break;
        case 2:
            left += 20;
            top += 5;
            break;
        case 3:
            left += 10;
            top += 10;
            break;
        case 6:
            left -= 30;
            top += 15;
            break;
        default :
            left = 0;
            top = 0;
            break;
    }

    lp.setMargins(left, top, 0, 0);
    txtView.setLayoutParams(lp);
    }

    static private SpannableString formatScreenCounter(TagsListItem item, float fontSize){
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
