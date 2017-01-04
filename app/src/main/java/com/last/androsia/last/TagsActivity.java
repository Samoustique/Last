package com.last.androsia.last;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import java.util.ArrayList;

public class TagsActivity extends AppCompatActivity {
    GridView m_tagsGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        String[] names = new String[]{"San Gohan", "Inspecteur Gadget",
                "Quick and Flupke", "Tom"};

        int[] ages = {30, 27, 74, 54};

        String[] images = {
                "http://www.lucky-luke.com/fr-uploads/personnages/vignette_lucky.png",
                "http://a142.idata.over-blog.com/4/93/81/35/nouveau-dossier/chocolat/divers/631_2a5_joe-dalton-by-salevits_300x225_75sasi_300x225_75sas.jpg",
                "http://cdhlemag.com/wp-content/uploads/2016/05/rantanplan.jpg",
                "http://www.lucky-luke.com/fr-uploads/personnages/billy-the-kid.jpg"};

        ArrayList<TagsListItem> myList = new ArrayList<TagsListItem>();

        for (int i = 0; i < names.length; i++) {
            myList.add(new TagsListItem(names[i], ages[i], images[i]));
        }

        CustomAdapter adapter = new CustomAdapter(this, myList);

        m_tagsGridView = (GridView) findViewById(R.id.tagsGridView);
        m_tagsGridView.setAdapter(new CustomAdapter(this, myList));
        m_tagsGridView.setOnItemClickListener(adapter);
    }
}