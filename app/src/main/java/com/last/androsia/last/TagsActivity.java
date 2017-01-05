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
                "Quick and Flupke", "Tom"};

        int[] counters = {458, 27, 74, 54};

        String[] images = {
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