package com.last.androsia.last;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import java.util.ArrayList;
import java.util.List;

public class TagsActivity extends Activity {
    private ArrayList<TagsListItem> m_tagsList;
    private LastestTrio m_trio;
    private ExpandedGridView m_tagsGridView;
    private DBConnect m_dbConnect = new DBConnect(this);
    private DBItemsGetter m_dbItems;
    private ImageView m_btnGoToAddActivity;
    private TextView m_txtConnexionIssue;
    private final int ADD_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.tags_activity);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
        Context context = getApplicationContext();

        m_btnGoToAddActivity = (ImageView) findViewById(R.id.btnGoToAddActivity);
        m_btnGoToAddActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToAddActivity();
            }
        });

        m_txtConnexionIssue = (TextView) findViewById(R.id.txtConnexionIssue);

        if(isNetworkAvailable()) {
            m_dbConnect.execute(context);
            return;
        }

        Toast.makeText(context, "You should try again with internet on", Toast.LENGTH_LONG).show();
    }

    public void notifyMapperReady(DynamoDBMapper mapper) {
        m_dbItems = new DBItemsGetter(this, mapper);
        m_dbItems.execute(mapper);
    }

    public void notifyItemsReady(ArrayList<TagsListItem> tagsList) {
        m_tagsList = tagsList;
        displayTags();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void displayTags(){
        List trioList;
        ArrayList<TagsListItem> clonedList = new ArrayList<>(m_tagsList);
        if (clonedList.size() > 3) {
            trioList = new ArrayList<>(clonedList.subList(0, 3));
        } else {
            trioList = new ArrayList<>(clonedList);
        }
        clonedList.removeAll(trioList);

        m_trio = new LastestTrio(
                this,
                trioList,
                new ImgCounterView((ImageView) findViewById(R.id.imgUserGold), (TextView) findViewById(R.id.txtCounterGold), (RelativeLayout) findViewById(R.id.layoutGold)),
                new ImgCounterView((ImageView) findViewById(R.id.imgUserSilver), (TextView) findViewById(R.id.txtCounterSilver), (RelativeLayout) findViewById(R.id.layoutSilver)),
                new ImgCounterView((ImageView) findViewById(R.id.imgUserBronze), (TextView) findViewById(R.id.txtCounterBronze), (RelativeLayout) findViewById(R.id.layoutBronze))
        );
        m_trio.setupClickListeners();
        m_trio.display();

        CustomAdapter adapter = new CustomAdapter(this, clonedList);
        m_tagsGridView = (ExpandedGridView) findViewById(R.id.tagsGridView);
        m_tagsGridView.setAdapter(adapter);
        m_tagsGridView.setExpanded(true);
        m_tagsGridView.setOnItemClickListener(new OnTagClickListener(adapter));
    }

    public void goToAddActivity(){
        Intent intent = new Intent(this, AddTagActivity.class);
        //intent.putExtra("com.last.androsia.last.dbconnect", m_dbConnect);
        intent.putExtra("com.last.androsia.last.mapper", m_dbConnect.getMapper());
        startActivityForResult(intent, ADD_ACTIVITY);
    }

    public void notifyConnexionIssue() {
        m_txtConnexionIssue.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ACTIVITY && resultCode == RESULT_OK && data != null) {
            TagsListItem item = (TagsListItem) data.getSerializableExtra("item");
            m_tagsList.add(0, item);
        }
    }
}