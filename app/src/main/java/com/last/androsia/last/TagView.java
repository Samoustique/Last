package com.last.androsia.last;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.last.androsia.last.Activities.TagsActivity;

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

    public void setupClickListeners(final TagsActivity tagsActivity) {
        m_imgCounterView.getImg().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_item.incrementCounter();
                display(false);
            }
        });
        m_imgCounterView.getImg().setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                tagsActivity.createPopUp(m_item);
                return true;
            }
        });
    }

    public void display(boolean doCenter) {
        // Img
        try {
            Bitmap img = FilesUtility.decodeSampledBitmapFromResource(m_item.getImgUrl());
            m_imgCounterView.getImg().setImageBitmap(img);
        } catch(Error e){}

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
