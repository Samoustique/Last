package com.last.androsia.last;

import android.os.AsyncTask;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPhilipps on 1/19/2017.
 */

public class DBItemsAccessor extends AsyncTask<DynamoDBMapper, Void, List<DBItem>> {
    private TagsActivity m_tagsActivity;
    private ArrayList<TagsListItem> m_tagsList;
    private DBUpdater m_dbUpdater;
    private DynamoDBMapper m_mapper;

    public DBItemsAccessor(TagsActivity tagsActivity, DynamoDBMapper mapper) {
        m_mapper = mapper;
        m_tagsActivity = tagsActivity;
    }

    @Override
    protected List<DBItem> doInBackground(DynamoDBMapper... params) {
        if(params.length != 1){
            return new ArrayList<>();
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return params[0].parallelScan(DBItem.class, scanExpression, 4);
    }

    @Override
    protected void onPostExecute(List<DBItem> dbItems) {
        m_tagsList = new ArrayList<>();
        for (DBItem item : dbItems) {
            TagsListItem tag = new TagsListItem(item, m_mapper);
            m_tagsList.add(tag);
        }
        m_tagsActivity.notifyItemsReady(m_tagsList);
    }
}