package com.last.androsia.last;

import android.os.AsyncTask;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
/**
 * Created by Samoustique on 13/01/2017.
 */

public class DBConnect extends AsyncTask<String, Void, String> {
    private CognitoCachingCredentialsProvider m_credentialsProvider;

    public DBConnect(CognitoCachingCredentialsProvider credentialsProvider){
        m_credentialsProvider = credentialsProvider;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(m_credentialsProvider);
            DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
            // try to get the first item of the "Item" table (index 0)
            TagsListItem itemTest = mapper.load(TagsListItem.class, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(String page) {
        // onPostExecute
    }
}