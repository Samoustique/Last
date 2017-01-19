package com.last.androsia.last;

import android.os.AsyncTask;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.util.ArrayList;

/**
 * Created by SPhilipps on 1/19/2017.
 */

public class DBItemsAccessor extends AsyncTask<DynamoDBMapper, Void, ArrayList<TagsListItem>> {
    TagsActivity m_tagsActivity;
    ArrayList<TagsListItem> m_tagsList;

    public DBItemsAccessor(TagsActivity tagsActivity) {
        m_tagsActivity = tagsActivity;
    }

    @Override
    protected ArrayList<TagsListItem> doInBackground(DynamoDBMapper... params) {
        ArrayList<TagsListItem> tagsList = new ArrayList<>();
        tagsList.add(params[0].load(TagsListItem.class, "0"));
        tagsList.add(params[0].load(TagsListItem.class, "1"));
        tagsList.add(params[0].load(TagsListItem.class, "2"));
        tagsList.add(params[0].load(TagsListItem.class, "3"));

        return tagsList;
    }

    @Override
    protected void onPostExecute(ArrayList<TagsListItem> tagsList) {
        m_tagsList = tagsList;
        m_tagsActivity.notifyItemsReady(tagsList);
    }
}