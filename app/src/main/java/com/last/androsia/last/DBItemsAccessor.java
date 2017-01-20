package com.last.androsia.last;

import android.os.AsyncTask;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPhilipps on 1/19/2017.
 */

public class DBItemsAccessor extends AsyncTask<DynamoDBMapper, Void, ArrayList<TagsListItem>> {
    private TagsActivity m_tagsActivity;
    private ArrayList<TagsListItem> m_tagsList;
    private DBUpdater m_dbUpdater;
    private DynamoDBMapper m_mapper;

    public DBItemsAccessor(TagsActivity tagsActivity, DynamoDBMapper mapper) {
        m_mapper = mapper;
        m_tagsActivity = tagsActivity;
    }

    @Override
    protected ArrayList<TagsListItem> doInBackground(DynamoDBMapper... params) {
        if(params.length != 1){
            return new ArrayList<>();
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<TagsListItem> scanResult = params[0].parallelScan(TagsListItem.class, scanExpression, 4);
        return new ArrayList<>(scanResult);
    }

    @Override
    protected void onPostExecute(ArrayList<TagsListItem> tagsList) {
        m_dbUpdater = new DBUpdater(m_mapper);
        // allow each item to update the db
        for (TagsListItem item : tagsList) {
            item.setDBUpdater(m_dbUpdater);
        }
        m_tagsList = tagsList;
        m_tagsActivity.notifyItemsReady(tagsList);
    }
}