package com.last.androsia.last;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private ArrayList<TagsListItem> m_tagList = new ArrayList<TagsListItem>();
    private Context m_context;
    private ImageLoader m_imageLoader = new ImageLoader();

    private class MyViewHolder {
        ImageView m_imageView;
        TextView m_textViewCounter;
    }

    public CustomAdapter(Context context, ArrayList<TagsListItem> list) {
        m_tagList = list;
        m_context = context;
    }

    @Override
    public int getCount() {
        return m_tagList.size();
    }

    @Override
    public TagsListItem getItem(int position) {
        return m_tagList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return m_tagList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        boolean doCenter = false;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) m_context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.tag, parent, false);

            mViewHolder = new MyViewHolder();
            mViewHolder.m_imageView = (ImageView) convertView.findViewById(R.id.imgUser);
            mViewHolder.m_textViewCounter = (TextView) convertView.findViewById(R.id.txtCounter);
            mViewHolder.m_textViewCounter.setTypeface(CounterHelper.getFont(m_context));

            convertView.setTag(mViewHolder);
            doCenter = true;
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        TagsListItem tagsListItem = getItem(position);

        m_imageLoader.loadImage(tagsListItem.getImageUrl(), m_context, mViewHolder.m_imageView);
        SpannableString counter = CounterHelper.formatCounter(tagsListItem);
        mViewHolder.m_textViewCounter.setText(counter, TextView.BufferType.SPANNABLE);

        if (doCenter) {
            CounterHelper.centerCounter(mViewHolder.m_textViewCounter);
        }

        return convertView;
    }
}
