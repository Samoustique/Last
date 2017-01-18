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
/**
 * Created by Samoustique on 13/01/2017.
 */

public class DBConnect implements Runnable {
    private Context m_context;
    private DynamoDBMapper m_mapper;
    private Thread m_thread = new Thread();

    public DBConnect(Context context){
        m_context = context;
    }

    public void start(){
        m_thread.start();
    }

    @Override
    public void run() {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                m_context,
                XXXXXXXXXXXXXX,
                Regions.US_WEST_2 // Region
        );
        AWSSessionCredentials arnCredentials = credentialsProvider.getCredentials();

        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(arnCredentials);
        dynamoDB.setRegion(Region.getRegion(Regions.US_WEST_2));
        m_mapper = new DynamoDBMapper(dynamoDB);
    }

    public TagsListItem getItem() {
        try {
            m_thread.join();
            return m_mapper.load(TagsListItem.class, "0");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void join() throws Exception {
        m_thread.join();
    }
}