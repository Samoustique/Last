package com.last.androsia.last;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

import java.util.ArrayList;
import java.util.List;

public class TagsActivity extends AppCompatActivity {
    private LastestTrio m_trio;
    private ExpandedGridView m_tagsGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        // 1. Attempt of DB connect
        /*CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                XXXXXX,
                Regions.US_WEST_2 // Region
        );
        AWSSessionCredentials arnCredentials = credentialsProvider.getCredentials();

        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(creds);
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
        //TagsListItem item = mapper.load(TagsListItem.class, "0");
        ListTablesResult list = dynamoDB.listTables();*/




        // 2. or Uncomment here and in TagListItem to use the hardcoded version
        //hardcodedLoading();
    }

    private void hardcodedLoading() {
        String[] titles = new String[]{"San Gohan", "Inspecteur Gadget",
                "Quick and Flupke", "Tom", "dz", "dzd", "errtrt", "hhthth",
                "yuyuj", "kiukiku", "zdz", "zdz", "dzd", "zdz", "dzd"};

        double[] counters = {7.3, 2, 150., 99, 458, 10, 7.3, 103, 96.1, 56.25, 102, 12.2, 555, 34, 1};

        TagsListItem.Type[] types = {
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.SCREEN,
                TagsListItem.Type.BOOK,
                TagsListItem.Type.BOOK,
                TagsListItem.Type.SCREEN,
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
                "http://media.rtl.fr/online/image/2015/0507/7778268024_david-bowie.jpg",
                "http://www.lucky-luke.com/fr-uploads/personnages/vignette_lucky.png",
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

        ArrayList<TagsListItem> myList = new ArrayList<>();

        for (int i = 0; i < titles.length; i++) {
            //myList.add(new TagsListItem(titles[i], counters[i], 0, images[i], types[i]));
        }

        List trioList;
        if (myList.size() > 3) {
            trioList = new ArrayList<>(myList.subList(0, 3));
        } else {
            trioList = new ArrayList<>(myList);
        }
        myList.removeAll(trioList);

        m_trio = new LastestTrio(
                this,
                trioList,
                new ImgCounterView((ImageView) findViewById(R.id.imgUserGold), (TextView) findViewById(R.id.txtCounterGold), (RelativeLayout) findViewById(R.id.layoutGold)),
                new ImgCounterView((ImageView) findViewById(R.id.imgUserSilver), (TextView) findViewById(R.id.txtCounterSilver), (RelativeLayout) findViewById(R.id.layoutSilver)),
                new ImgCounterView((ImageView) findViewById(R.id.imgUserBronze), (TextView) findViewById(R.id.txtCounterBronze), (RelativeLayout) findViewById(R.id.layoutBronze))
        );
        m_trio.setupClickListeners();
        m_trio.display();

        CustomAdapter adapter = new CustomAdapter(this, myList);
        m_tagsGridView = (ExpandedGridView) findViewById(R.id.tagsGridView);
        m_tagsGridView.setAdapter(adapter);
        m_tagsGridView.setExpanded(true);
        m_tagsGridView.setOnItemClickListener(new OnTagClickListener(adapter));
    }
}