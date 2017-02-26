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
    private final static int ROLE_GOLD = 0;
    private final static int ROLE_SILVER = 1;
    private final static int ROLE_BRONZE = 2;

    static public SpannableString formatCounter(TagItem item){
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

    static public void centerTrioCounter(int role, TextView txtView){
        int leftTop[] = new int[2];

        switch (role) {
            case ROLE_GOLD:
                goldMargin(txtView.length(), leftTop);
                break;
            case ROLE_SILVER:
                silverMargin(txtView.length(), leftTop);
                break;
            case ROLE_BRONZE:
                bronzeMargin(txtView.length(),leftTop);
                break;
            default:
                break;
        }

        RelativeLayout.LayoutParams lp =
                (RelativeLayout.LayoutParams) txtView.getLayoutParams();
        lp.setMargins(leftTop[0], leftTop[1], 0, 0);
        txtView.setLayoutParams(lp);
    }

    static private void goldMargin(int txtLength, int leftTop[]){
        switch(txtLength){
            case 1:
                leftTop[0] = 285;
                leftTop[1] = 285;
                break;
            case 2:
                leftTop[0] = 260;
                leftTop[1] = 280;
                break;
            case 3:
                leftTop[0] = 230;
                leftTop[1] = 290;
                break;
            case 6:
                leftTop[0] = 190;
                leftTop[1] = 300;
                break;
            default :
                leftTop[0] = 0;
                leftTop[1] = 0;
                break;
        }
    }

    static private void silverMargin(int txtLength, int leftTop[]){
        switch(txtLength){
            case 1:
                leftTop[0] = 153;
                leftTop[1] = 163;
                break;
            case 2:
                leftTop[0] = 145;
                leftTop[1] = 160;
                break;
            case 3:
                leftTop[0] = 120;
                leftTop[1] = 160;
                break;
            case 6:
                leftTop[0] = 97;
                leftTop[1] = 152;
                break;
            default :
                leftTop[0] = 0;
                leftTop[1] = 0;
                break;
        }
    }

    static private void bronzeMargin(int txtLength, int leftTop[]){
        switch(txtLength){
            case 1:
                leftTop[0] = 153;
                leftTop[1] = 163;
                break;
            case 2:
                leftTop[0] = 145;
                leftTop[1] = 160;
                break;
            case 3:
                leftTop[0] = 115;
                leftTop[1] = 155;
                break;
            case 6:
                leftTop[0] = 97;
                leftTop[1] = 152;
                break;
            default :
                leftTop[0] = 0;
                leftTop[1] = 0;
                break;
        }
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
