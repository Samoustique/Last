package com.last.androsia.last;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Created by Samoustique on 13/01/2017.
 */

public class DBConnect extends AsyncTask <Context, Void, DynamoDBMapper> {
    private DynamoDBMapper m_mapper;
    private TagsActivity m_tagsActivity;

    private final String STR_BUCKET = "androsialast";

    public DBConnect() {
    }

    public DBConnect(TagsActivity tagsActivity) {
        m_tagsActivity = tagsActivity;
    }

    public DynamoDBMapper getMapper() {
        return m_mapper;
    }

    @Override
    protected DynamoDBMapper doInBackground(Context... params) {
        AWSSessionCredentials arnCredentials = DBCredentialsProvider.get(params[0]).getCredentials();

        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(arnCredentials);
        dynamoDB.setRegion(Region.getRegion(Regions.US_WEST_2));
        return new DynamoDBMapper(dynamoDB);
    }

    @Override
    protected void onPostExecute(DynamoDBMapper mapper) {
        m_mapper = mapper;
        m_tagsActivity.notifyMapperReady(mapper);
    }

    public AmazonS3Client connectToAmazonS3(Context context){
        CognitoCachingCredentialsProvider credentialsProvider = DBCredentialsProvider.get(context);

        AmazonS3Client s3 = new AmazonS3Client(credentialsProvider);
        s3.setRegion(Region.getRegion(Regions.US_EAST_1));
        return s3;
    }

    public String getBucketName(){
        return STR_BUCKET;
    }
}