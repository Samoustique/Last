package com.last.androsia.last;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SPhilipps on 1/11/2017.
 */

public class TagView {
    private ImgCounterView m_imgCounterView;
    private TagItem m_item;
    private Context m_context;
    private int m_role;

    public TagView(int role, ImgCounterView imgCounterView, TagItem item, Context context){
        m_imgCounterView = imgCounterView;
        m_item = item;
        m_context = context;
        m_role = role;
    }

    public void focus(){
        m_imgCounterView.focus();
    }

    public void setupClickListener() {
        m_imgCounterView.getImg().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_item.incrementCounter();
                display(false);
            }
        });
    }

    public void display(boolean doCenter) {
        // Img
        try {
            m_imgCounterView.getImg().setImageBitmap(BitmapUtility.getImage(m_item.getImage()));
        } catch(Error e){

        }

        // Txt
        SpannableString counter = CounterHelper.formatCounter(m_item);
        m_imgCounterView.getTxt().setText(counter, TextView.BufferType.SPANNABLE);
        //m_txtGold.setTextSize(fontSize + 0.3f);
        m_imgCounterView.getTxt().setTypeface(CounterHelper.getFont(m_context));
        if(doCenter) {
            CounterHelper.centerTrioCounter(m_role, m_imgCounterView.getTxt());
        }
    }
}
