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

import java.io.Serializable;

/**
 * Created by Samoustique on 13/01/2017.
 */

public class DBConnect extends AsyncTask <Void, Void, DBLastDynamoMapper>  implements Serializable {
    private final String STR_BUCKET = "androsialast";

    private DBLastDynamoMapper m_mapper;
    private Context m_context;
    private TagsActivity m_tagsActivity;

    public DBConnect(TagsActivity tagsActivity, Context context) {
        m_tagsActivity = tagsActivity;
        m_context = context;
    }

    public DBLastDynamoMapper getMapper() {
        return m_mapper;
    }

    @Override
    protected DBLastDynamoMapper doInBackground(Void... params) {
        AWSSessionCredentials arnCredentials = DBCredentialsProvider.get(m_context).getCredentials();

        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(arnCredentials);
        dynamoDB.setRegion(Region.getRegion(Regions.US_WEST_2));
        return new DBLastDynamoMapper(dynamoDB);
    }

    @Override
    protected void onPostExecute(DBLastDynamoMapper mapper) {
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

    public void createNewItem(DBItem item){
        new DBUpdater(m_mapper, m_tagsActivity).execute(item);
    }
}