package com.last.androsia.last;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TagsActivity extends AppCompatActivity {
    private LastestTrio m_trio;
    private GridView m_tagsGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        String[] titles = new String[]{"San Gohan", "Inspecteur Gadget",
                "Quick and Flupke", "Tom", "dz", "dzd", "errtrt", "hhthth",
                "yuyuj", "kiukiku", "zdz", "zdz", "dzd"};

        double[] counters = {1.01, 2.12, 10.23, 99, 458, 10, 7, 103, 96, 56, 102, 34, 1};

        TagsListItem.Type[] types = {
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.BOOK,
                TagsListItem.Type.BOOK,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.BOOK,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.BOOK,
                TagsListItem.Type.BOOK
        };

        String[] images = {
                "http://vignette2.wikia.nocookie.net/onepiece/images/c/c8/Luffy_Anime_Avant_Ellipse_Infobox.png/revision/latest/scale-to-width-down/250?cb=20161021213623&path-prefix=fr",
                "https://media.planete-starwars.com/news/59010-dark-vador-compressor-169-lg.jpg",
                "http://img1.wikia.nocookie.net/__cb20140506233242/epicrapbattlesofhistory/images/6/63/Luffy.jpg",
                "https://i.ytimg.com/vi/zbCbwP6ibR4/maxresdefault.jpg",
                "http://t0.gstatic.com/images?q=tbn:ANd9GcTkP2Vm4n2lARa5UJJ-kJb-BCW_q2xJRTnbhmij2FiJqAjtxF6bPNyIFWY",
                "http://media.topito.com/wp-content/uploads/2015/04/sangoku-250x250.jpg",
                "http://ichef.bbci.co.uk/news/660/cpsprodpb/153B4/production/_89046968_89046967.jpg",
                "https://www.planwallpaper.com/static/images/ZhGEqAP.jpg",
                "https://s-media-cache-ak0.pinimg.com/236x/59/87/6c/59876c15abd6d705ea8b87033633f009.jpg",
                "http://www.lucky-luke.com/fr-uploads/personnages/vignette_lucky.png",
                "http://a142.idata.over-blog.com/4/93/81/35/nouveau-dossier/chocolat/divers/631_2a5_joe-dalton-by-salevits_300x225_75sasi_300x225_75sas.jpg",
                "http://cdhlemag.com/wp-content/uploads/2016/05/rantanplan.jpg",
                "http://www.lucky-luke.com/fr-uploads/personnages/billy-the-kid.jpg"};

        ArrayList<TagsListItem> myList = new ArrayList<TagsListItem>();

        for (int i = 0; i < titles.length; i++) {
            myList.add(new TagsListItem(titles[i], counters[i], images[i], types[i]));
        }

        CustomAdapter adapter = new CustomAdapter(this, myList);
        OnTagClickListener clickListener = new OnTagClickListener(adapter);

        m_trio = new LastestTrio(
                this,
                (ImageView) findViewById(R.id.imgUserGold),
                (TextView) findViewById(R.id.txtCounterGold));
        m_trio.setAdapter(adapter);
        m_trio.setClickListener(clickListener);
        m_trio.displayImage(myList.get(0));

        myList.remove(0);
        m_tagsGridView = (GridView) findViewById(R.id.tagsGridView);
        m_tagsGridView.setAdapter(adapter);
        m_tagsGridView.setOnItemClickListener(clickListener);
        m_tagsGridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });
    }
}