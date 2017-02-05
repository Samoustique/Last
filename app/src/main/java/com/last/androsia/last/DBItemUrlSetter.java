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

public class DBItemUrlSetter extends AsyncTask<Void, Void, DBItem> {
    private INotifiedActivity m_activity;
    private ArrayList<TagsListItem> m_tagsList;
    private DynamoDBMapper m_mapper;
    private String m_url;
    private String m_newItemId;

    public DBItemUrlSetter(INotifiedActivity activity, DynamoDBMapper mapper, String url, String newItemId) {
        m_mapper = mapper;
        m_activity = activity;
        m_url = url;
        m_newItemId = newItemId;
    }

    @Override
    protected DBItem doInBackground(Void... params) {
        DBItem item = m_mapper.load(DBItem.class, m_newItemId);
        item.setImageUrl(m_url);
        m_mapper.save(item);

        return item;
    }

    @Override
    protected void onPostExecute(DBItem dbItem) {
        m_activity.notifyItemCreated(dbItem);
    }
}