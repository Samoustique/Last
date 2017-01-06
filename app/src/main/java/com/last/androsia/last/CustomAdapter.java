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
    ArrayList<TagsListItem> m_tagList = new ArrayList<TagsListItem>();
    Context m_context;
    ImageLoader m_imageLoader = new ImageLoader();

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
        MyViewHolder mViewHolder = null;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) m_context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.tag, parent, false);

            mViewHolder = new MyViewHolder();
            mViewHolder.m_imageView = (ImageView) convertView.findViewById(R.id.imgUser);
            mViewHolder.m_textViewCounter = (TextView) convertView.findViewById(R.id.txtCounter);
            AssetManager am = m_context.getApplicationContext().getAssets();
            Typeface custom_font = Typeface.createFromAsset(am,
                    String.format(Locale.US, "fonts/%s", "old_stamper.ttf"));
            mViewHolder.m_textViewCounter.setTypeface(custom_font);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        TagsListItem tagsListItem = (TagsListItem) getItem(position);

        m_imageLoader.loadImage(tagsListItem.getImageUrl(), m_context, mViewHolder.m_imageView);
        formatCounter(tagsListItem, mViewHolder.m_textViewCounter);

        return convertView;
    }

    private void formatCounter(TagsListItem item, TextView txtView){
        SpannableString  counter = new SpannableString("");
        switch(item.getType()){
            case BOOK:
                counter = new SpannableString(String.valueOf((int) item.getCounter()));
                break;
            case SCREEN:
                counter = formatScreenCounter(item);
                break;
            default:
                break;
        }
        txtView.setText(counter, TextView.BufferType.SPANNABLE);
        centerCounter(txtView);
    }

    private void centerCounter(TextView txtView){
        int left;
        int top;
        RelativeLayout.LayoutParams lp =
                (RelativeLayout.LayoutParams) txtView.getLayoutParams();

        switch(txtView.length()){
            case 1:
                left = 220;
                top = 220;
                break;
            case 2:
                left = 190;
                top = 225;
                break;
            case 3:
                left = 160;
                top = 225;
                break;
            case 6:
                left = 140;
                top = 235;
                break;
            default :
                left = 0;
                top = 0;
                break;
        }

        lp.setMargins(left, top, 0, 0);
        txtView.setLayoutParams(lp);
    }

    private SpannableString formatScreenCounter(TagsListItem item){
        int real = (int) item.getCounter();
        int decimal = (int) (100 * (item.getCounter() - real));

        if(decimal == 0){
            // This is a movie
            return new SpannableString(String.valueOf(real));
        }

        // This is a Serie or Anim
        int green = Color.rgb(79, 49, 23);

        String strCounter = String.format("S%02dE%02d", real, decimal);
        SpannableString counter = new SpannableString(strCounter);
        counter.setSpan(new ForegroundColorSpan(green), 0, 1, 0);
        counter.setSpan(new ForegroundColorSpan(green), 3, 4, 0);
        counter.setSpan(new RelativeSizeSpan(0.65f), 0, strCounter.length(), 0);

        return counter;
    }
}
