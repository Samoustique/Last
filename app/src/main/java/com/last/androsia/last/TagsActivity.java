package com.last.androsia.last;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import java.util.ArrayList;
import java.util.List;

public class TagsActivity extends AppCompatActivity {
    private LastestTrio m_trio;
    private ExpandedGridView m_tagsGridView;
    private DBConnect m_dbConnect = new DBConnect(this);
    private DBItemsAccessor m_dbItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tags_activity);
        ActionBar bar = getSupportActionBar();

        Context context = getApplicationContext();

        if(isNetworkAvailable()) {
            m_dbConnect.execute(context);
            return;
        }

        Toast.makeText(context, "You should try again with internet on", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tags_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.add_tag:
                startActivity(new Intent(this, AddTagActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void notifyMapperReady(DynamoDBMapper mapper) {
        m_dbItems = new DBItemsAccessor(this, mapper);
        m_dbItems.execute(mapper);
    }

    public void notifyItemsReady(ArrayList<TagsListItem> tagsList) {
        displayTags(tagsList);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void displayTags(ArrayList<TagsListItem> myList){
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