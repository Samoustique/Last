package com.last.androsia.last;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samoustique on 07/01/2017.
 */

public class LastestTrio {
    private Context m_context;
    private List<TagsListItem> m_tags;
    private ImageLoader m_imageLoader;
    private boolean m_doCenter;

    // --- GOLD
    private static int GOLD = 0;
    private ImageView m_imgGold;
    private TextView m_txtGold;

    /*private ImageView m_imgSilver;
    private TextView m_txtSilver;
    private ImageView m_imgBronze;
    private TextView m_txtBronze;*/

    public LastestTrio(Context context,
                       List<TagsListItem> tags,
                       ImageView imgGold,
                       TextView txtGold) {
        m_context = context;
        m_tags = new ArrayList<>(tags);
        m_imageLoader = new ImageLoader();
        m_doCenter = false;

        m_imgGold = imgGold;
        m_txtGold = txtGold;

        m_imgGold.setFocusableInTouchMode(true);
        m_imgGold.requestFocus();
    }

    public void setupClickListeners() {
        m_imgGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_tags.get(GOLD).incrementCounter();
                display(GOLD);
            }
        });
        /*m_imgSilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_tags.get(SILVER).incrementCounter();
            }
        });
        m_imgBronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_tags.get(BRONZE).incrementCounter();
            }
        });*/
    }

    public void display() {
        m_doCenter = true;
        display(GOLD);
        /*display(SILVER);
        display(BRONZE);*/
        m_doCenter = false;
    }

    private void display(int id) {
        TagsListItem goldItem = m_tags.get(id);
        // Img
        m_imageLoader.loadImage(goldItem.getImageUrl(), m_context, m_imgGold);
        // Txt
        SpannableString counter = CounterHelper.formatCounter(goldItem);
        m_txtGold.setText(counter, TextView.BufferType.SPANNABLE);
        //float fontSize = m_txtGold.getTextSize();
        //m_txtGold.setTextSize(fontSize + 0.3f);
        m_txtGold.setTypeface(CounterHelper.getFont(m_context));
        if(m_doCenter) {
            CounterHelper.centerCounter(m_txtGold);
        }
    }
}
