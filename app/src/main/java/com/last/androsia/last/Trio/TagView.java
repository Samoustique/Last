package com.last.androsia.last.Trio;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.last.androsia.last.Activities.TagsActivity;
import com.last.androsia.last.Common.CounterHelper;
import com.last.androsia.last.Common.FilesUtility;
import com.last.androsia.last.Common.ImgCounterView;
import com.last.androsia.last.Common.TagItem;

/**
 * Created by SPhilipps on 1/11/2017.
 */

public class TagView {
    private ImgCounterView m_imgCounterView;
    private TagItem m_item;
    private Context m_context;

    public TagView(ImgCounterView imgCounterView, TagItem item, Context context){
        m_imgCounterView = imgCounterView;
        m_item = item;
        m_context = context;
    }

    public void focus(){
        m_imgCounterView.focus();
    }

    public void setupClickListeners(final TagsActivity tagsActivity) {
        m_imgCounterView.getImg().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_item.incrementCounter();
                display();
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

    public void display() {
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
    }
}