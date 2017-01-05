package com.last.androsia.last;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import java.util.ArrayList;

public class TagsActivity extends AppCompatActivity {
    GridView m_tagsGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        String[] titles = new String[]{"San Gohan", "Inspecteur Gadget",
                "Quick and Flupke", "Tom", "dz", "dzd", "zdz", "zdz", "dzd"};

        int[] counters = {1, 458, 27, 99, 54, 56, 102, 34, 1};

        String[] images = {
                "http://vignette2.wikia.nocookie.net/onepiece/images/c/c8/Luffy_Anime_Avant_Ellipse_Infobox.png/revision/latest/scale-to-width-down/250?cb=20161021213623&path-prefix=fr",
                "https://media.planete-starwars.com/news/59010-dark-vador-compressor-169-lg.jpg",
                "http://img1.wikia.nocookie.net/__cb20140506233242/epicrapbattlesofhistory/images/6/63/Luffy.jpg",
                "http://media.topito.com/wp-content/uploads/2015/04/sangoku-250x250.jpg",
                "https://www.planwallpaper.com/static/images/ZhGEqAP.jpg",
                "http://www.lucky-luke.com/fr-uploads/personnages/vignette_lucky.png",
                "http://a142.idata.over-blog.com/4/93/81/35/nouveau-dossier/chocolat/divers/631_2a5_joe-dalton-by-salevits_300x225_75sasi_300x225_75sas.jpg",
                "http://cdhlemag.com/wp-content/uploads/2016/05/rantanplan.jpg",
                "http://www.lucky-luke.com/fr-uploads/personnages/billy-the-kid.jpg"};

        ArrayList<TagsListItem> myList = new ArrayList<TagsListItem>();

        for (int i = 0; i < titles.length; i++) {
            myList.add(new TagsListItem(titles[i], counters[i], images[i]));
        }

        final CustomAdapter adapter = new CustomAdapter(this, myList);

        m_tagsGridView = (GridView) findViewById(R.id.tagsGridView);
        m_tagsGridView.setAdapter(adapter);
        m_tagsGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                TagsListItem item = (TagsListItem) adapterView.getItemAtPosition(pos);

                item.incrementCounter();
                adapter.notifyDataSetChanged();
            }
        });
    }
}