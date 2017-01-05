package com.last.androsia.last;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

    // on passe le context afin d'obtenir un LayoutInflater pour utiliser notre
    // row_layout.xml
    // on passe les valeurs de notre à l'adapter
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

    // retourne la vue d'un élément de la liste
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder = null;

        // au premier appel ConvertView est null, on inflate notre layout
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) m_context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.tag, parent, false);

            // nous plaçons dans notre MyViewHolder les vues de notre layout
            mViewHolder = new MyViewHolder();
            mViewHolder.m_imageView = (ImageView) convertView.findViewById(R.id.imgUser);
            mViewHolder.m_textViewCounter = (TextView) convertView.findViewById(R.id.txtCounter);
            AssetManager am = m_context.getApplicationContext().getAssets();
            Typeface custom_font = Typeface.createFromAsset(am,
                    String.format(Locale.US, "fonts/%s", "old_stamper.ttf"));
            mViewHolder.m_textViewCounter.setTypeface(custom_font);

            // nous attribuons comme tag notre MyViewHolder à convertView
            convertView.setTag(mViewHolder);
        } else {
            // convertView n'est pas null, nous récupérons notre objet MyViewHolder
            // et évitons ainsi de devoir retrouver les vues à chaque appel de getView
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        // nous récupérons l'item de la liste demandé par getView
        TagsListItem tagsListItem = (TagsListItem) getItem(position);

        m_imageLoader.loadImage(tagsListItem.getImageUrl(), m_context, mViewHolder.m_imageView);
        mViewHolder.m_textViewCounter.setText(String.valueOf(tagsListItem.getCounter()));

        // nous retournos la vue de l'item demandé
        return convertView;
    }

    private class MyViewHolder {
        ImageView m_imageView;
        TextView m_textViewCounter;
    }
}
