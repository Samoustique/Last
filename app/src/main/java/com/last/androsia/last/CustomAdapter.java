package com.last.androsia.last;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private ArrayList<TagItem> m_tagList = new ArrayList<TagItem>();
    private Context m_context;

    private class MyViewHolder {
        ImageView m_imageView;
        TextView m_textViewCounter;
    }

    public CustomAdapter(Context context, ArrayList<TagItem> list) {
        m_tagList = list;
        m_context = context;
    }

    @Override
    public int getCount() {
        return m_tagList.size();
    }

    @Override
    public TagItem getItem(int position) {
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
        TagItem tagItem = getItem(position);

        try {
            mViewHolder.m_imageView.setImageBitmap(BitmapUtility.getImage(tagItem.getImage()));
            SpannableString counter = CounterHelper.formatCounter(tagItem);
            mViewHolder.m_textViewCounter.setText(counter, TextView.BufferType.SPANNABLE);

            if (doCenter) {
                CounterHelper.centerCounter(mViewHolder.m_textViewCounter);
            }
        } catch(Error e){}

        return convertView;
    }
}
