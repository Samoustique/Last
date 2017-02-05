package com.last.androsia.last;

import android.os.AsyncTask;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.util.Date;

/**
 * Created by SPhilipps on 1/20/2017.
 */
public class DBUpdater extends AsyncTask<DBItem, Void, String> {
    private INotifiedActivity m_activity;
    private DynamoDBMapper m_mapper;

    public DBUpdater(DynamoDBMapper mapper){
        m_mapper = mapper;
    }

    public DBUpdater(INotifiedActivity activity, DynamoDBMapper mapper) {
        this(mapper);
        m_activity = activity;
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
    protected void onPostExecute(String newItemId) {
        if(m_activity != null) {
            m_activity.notifyIdGenerated(newItemId);
        }
    }
}