package com.last.androsia.last;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class CustomAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    ArrayList<TagsListItem> m_tagList = new ArrayList<TagsListItem>();
    Context m_context;
    ImageLoader m_imageLoader = new ImageLoader();

    // on passe le context afin d'obtenir un LayoutInflater pour utiliser notre
    // row_layout.xml
    // on passe les valeurs de notre à l'adapter
    public CustomAdapter(Context context, ArrayList<TagsListItem> list) {
        this.m_tagList = list;
        this.m_context = context;
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
            /*mViewHolder.textViewName = (TextView) convertView
                    .findViewById(R.id.textViewName);
            mViewHolder.textViewAge = (TextView) convertView
                    .findViewById(R.id.textViewAge);*/
            mViewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageView);

            // nous attribuons comme tag notre MyViewHolder à convertView
            convertView.setTag(mViewHolder);
        } else {
            // convertView n'est pas null, nous récupérons notre objet MyViewHolder
            // et évitons ainsi de devoir retrouver les vues à chaque appel de getView
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        // nous récupérons l'item de la liste demandé par getView
        TagsListItem tagsListItem = (TagsListItem) getItem(position);

        // nous pouvons attribuer à nos vues les valeurs de l'élément de la liste
        /*mViewHolder.textViewName.setText(tagsListItem.getName());
        mViewHolder.textViewAge.setText(String.valueOf(tagsListItem.getAge()) + " ans");*/
        m_imageLoader.loadImage(tagsListItem.getImageUrl(), m_context, mViewHolder.imageView);

        // nous retournos la vue de l'item demandé
        return convertView;
    }

    // MyViewHolder va nous permettre de ne pas devoir rechercher
    // les vues à chaque appel de getView, nous gagnons ainsi en performance
    private class MyViewHolder {
        //TextView textViewName, textViewAge;
        ImageView imageView;
    }

    // nous affichons un Toast à chaque clic sur un item de la liste
    // nous récupérons l'objet grâce à sa position
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast toast = Toast.makeText(m_context, "Item " + (position + 1) + ": "
                + m_tagList.get(position), Toast.LENGTH_SHORT);
        toast.show();
    }
}
