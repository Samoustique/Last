package com.last.androsia.last;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SPhilipps on 1/11/2017.
 */

public class TagView {
    private ImageView m_img;
    private TextView m_txt;
    private TagsListItem m_item;

    private Context m_context;
    private ImageLoader m_imageLoader;

    public TagView(ImageView img, TextView txt, TagsListItem item, Context context){
        m_img = img;
        m_txt = txt;
        m_item = item;
        m_context = context;
        m_imageLoader = new ImageLoader();
    }

    public void focus(){
        m_img.setFocusableInTouchMode(true);
        m_img.requestFocus();
    }

    public void setupClickListener() {
        m_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_item.incrementCounter();
                display(false);
            }
        });
    }

    public void display(boolean doCenter) {
        // Img
        m_imageLoader.loadImage(m_item.getImageUrl(), m_context, m_img);
        // Txt
        SpannableString counter = CounterHelper.formatCounter(m_item);
        m_txt.setText(counter, TextView.BufferType.SPANNABLE);
        //float fontSize = m_txtGold.getTextSize();
        //m_txtGold.setTextSize(fontSize + 0.3f);
        m_txt.setTypeface(CounterHelper.getFont(m_context));
        if(doCenter) {
            CounterHelper.centerCounter(m_txt);
        }
    }
}
