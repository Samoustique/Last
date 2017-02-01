package com.last.androsia.last;

import android.os.AsyncTask;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by SPhilipps on 1/20/2017.
 */
public class DBUpdater extends AsyncTask<DBItem, Void, String> {
    private TagsActivity m_tagsActivity;
    private AddTagActivity m_addTagActivity;
    private ArrayList<TagsListItem> m_tagsList;
    private DynamoDBMapper m_mapper;

    public DBUpdater(DynamoDBMapper mapper){
        m_mapper = mapper;
    }

    public DBUpdater(DynamoDBMapper mapper, AddTagActivity addTagActivity) {
        this(mapper);
        m_addTagActivity = addTagActivity;
    }

    @Override
    protected String doInBackground(DBItem... params) {
        if(params.length != 1){
            return "";
        }

        DBItem item = params[0];
        item.setDate(Long.toString(new Date().getTime()));

        m_mapper.save(item);

        return item.getId();
    }

    @Override
    protected void onPostExecute(String id) {
        if(m_addTagActivity != null) {
            m_addTagActivity.notifyItemCreated(id);
        }
    }
}