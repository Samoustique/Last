package com.last.androsia.last;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private ArrayList<TagItem> m_tagList = new ArrayList<>();
    private Context m_context;
    // -1 means we want to update ALL items
    private int m_posToChange = -1;

    public void dataWillChange(int pos) {
        m_posToChange = pos;
    }

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
        if(convertView == null ||
           m_posToChange == -1 ||
           m_posToChange == position) {

            MyViewHolder mViewHolder;
            boolean doCenter = false;
            TagItem tagItem = getItem(position);

            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) m_context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

                convertView = mInflater.inflate(R.layout.tag, parent, false);

                mViewHolder = new MyViewHolder();
                mViewHolder.m_textViewCounter = (TextView) convertView.findViewById(R.id.txtCounter);
                mViewHolder.m_textViewCounter.setTypeface(CounterHelper.getFont(m_context));

                mViewHolder.m_imageView = (ImageView) convertView.findViewById(R.id.imgUser);
                Bitmap img = FilesUtility.decodeSampledBitmapFromResource(tagItem.getImgUrl());
                mViewHolder.m_imageView.setImageBitmap(img);

                convertView.setTag(mViewHolder);
                doCenter = true;
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }
            //Toast.makeText(m_context, String.valueOf(position), Toast.LENGTH_SHORT).show();

            try {
                /*Bitmap img = FilesUtility.decodeSampledBitmapFromResource(tagItem.getImgUrl());
                mViewHolder.m_imageView.setImageBitmap(img);*/
                SpannableString counter = CounterHelper.formatCounter(tagItem);
                mViewHolder.m_textViewCounter.setText(counter, TextView.BufferType.SPANNABLE);

                if (doCenter) {
                    CounterHelper.centerCounter(mViewHolder.m_textViewCounter);
                }
            } catch (Error e) { }
            m_posToChange = -1;
        }
        return convertView;
    }
}
