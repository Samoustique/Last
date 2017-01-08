package com.last.androsia.last;

import android.content.Context;
import android.text.SpannableString;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Samoustique on 07/01/2017.
 */

public class LastestTrio {
    private Context m_context;

    private ImageView m_imgGold;
    private TextView m_txtGold;

    /*private ImageView m_imgSilver;
    private TextView m_txtSilver;
    private ImageView m_imgBronze;
    private TextView m_txtBronze;*/
    private CustomAdapter m_adapter;
    private OnTagClickListener m_clickListener;
    private ImageLoader m_imageLoader = new ImageLoader();

    public LastestTrio(Context context, ImageView imgGold, TextView txtGold) {
        m_context = context;
        m_imgGold = imgGold;
        m_txtGold = txtGold;
    }

    public void setAdapter(CustomAdapter adapter){
        m_adapter = adapter;
    }

    public void setClickListener(OnTagClickListener listener) {
        m_clickListener = listener;
    }

    public void displayImage(TagsListItem item) {
        // Gold Img
        m_imageLoader.loadImage(item.getImageUrl(), m_context, m_imgGold);
        // Gold Txt
        SpannableString counter = CounterHelper.formatCounter(item);
        m_txtGold.setText(counter, TextView.BufferType.SPANNABLE);
        m_txtGold.setTypeface(CounterHelper.getFont(m_context));
        CounterHelper.centerOffsetCounter(m_txtGold, 0, 0);
    }
}
