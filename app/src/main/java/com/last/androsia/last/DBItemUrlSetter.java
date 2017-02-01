package com.last.androsia.last;

import android.os.AsyncTask;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by SPhilipps on 1/19/2017.
 */

public class DBItemUrlSetter extends AsyncTask<String, Void, DBItem> {
    private TagsActivity m_tagsActivity;
    private ArrayList<TagsListItem> m_tagsList;
    private DynamoDBMapper m_mapper;

    public DBItemUrlSetter(TagsActivity tagsActivity, DynamoDBMapper mapper) {
        m_mapper = mapper;
        m_tagsActivity = tagsActivity;
    }

    @Override
    protected DBItem doInBackground(String... params) {
        if(params.length != 1){
            return null;
        }

        String url = params[0];

        m_mapper.load(item);

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<DBItem> toReturn;
        try {
            toReturn = params[0].parallelScan(DBItem.class, scanExpression, 4);
        }catch(Exception e){
            return null;
        }
        return toReturn;
    }

    @Override
    protected void onPostExecute(DBItem dbItems) {
        if(dbItems == null) {
            m_tagsActivity.notifyConnexionIssue();
            return;
        }

        m_tagsList = new ArrayList<>();
        for (DBItem item : dbItems) {
            TagsListItem tag = new TagsListItem(item, m_mapper);
            m_tagsList.add(tag);
        }
        Collections.sort(m_tagsList);
        m_tagsActivity.notifyItemsReady(m_tagsList);
    }
}