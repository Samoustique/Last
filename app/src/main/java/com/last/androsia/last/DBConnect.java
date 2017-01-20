package com.last.androsia.last;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.net.URL;

/**
 * Created by Samoustique on 13/01/2017.
 */

public class DBConnect extends AsyncTask <Context, Void, DynamoDBMapper> {
    DynamoDBMapper m_mapper;
    TagsActivity m_tagsActivity;

    public DBConnect(TagsActivity tagsActivity) {
        m_tagsActivity = tagsActivity;
    }

    public DynamoDBMapper getMapper() {
        return m_mapper;
    }

    @Override
    protected DynamoDBMapper doInBackground(Context... params) {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                params[0],
                xxxxxxxx,
                Regions.US_WEST_2 // Region
        );
        AWSSessionCredentials arnCredentials = credentialsProvider.getCredentials();

        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(arnCredentials);
        dynamoDB.setRegion(Region.getRegion(Regions.US_WEST_2));
        return new DynamoDBMapper(dynamoDB);
    }

    @Override
    protected void onPostExecute(DynamoDBMapper mapper) {
        m_mapper = mapper;
        m_tagsActivity.notifyMapperReady(mapper);
    }
}