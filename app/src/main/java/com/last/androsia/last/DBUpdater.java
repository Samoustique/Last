package com.last.androsia.last;

import android.os.AsyncTask;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SPhilipps on 1/20/2017.
 */
public class DBUpdater extends AsyncTask<DBItem, Void, Boolean> {
    private TagsActivity m_tagsActivity;
    private ArrayList<TagsListItem> m_tagsList;
    private DynamoDBMapper m_mapper;

    public DBUpdater(DynamoDBMapper mapper){
        m_mapper = mapper;
    }

    @Override
    protected Boolean doInBackground(DBItem... params) {
        if(params.length != 1){
            return false;
        }

        DBItem item = params[0];
        item.setDate(Long.toString(new Date().getTime()));

        m_mapper.save(params[0]);

        return true;
    }
}