package com.last.androsia.last;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3Client;

import java.util.ArrayList;

/**
 * Created by Samoustique on 03/02/2017.
 */

public class GlobalUtilities extends Application {
    private DBConnect m_dbConnect;
    private AmazonS3Client m_s3Client;
    private ArrayList<TagsListItem> m_tagsList;

    public Boolean connectToDB(TagsActivity activity){
        Boolean isConnectionOK = false;
        if(isNetworkAvailable()) {
            Context context = getApplicationContext();
            m_dbConnect = new DBConnect(activity, context);
            m_dbConnect.execute();
            m_s3Client = m_dbConnect.connectToAmazonS3(context);
            isConnectionOK = true;
        }
        return isConnectionOK;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public AmazonS3Client getS3Client() { return m_s3Client; }

    public String getBucketName() {
        return m_dbConnect.getBucketName();
    }

    public DynamoDBMapper getDynamoDBMapper() {
        return m_dbConnect.getMapper();
    }

    public void setTagsList(ArrayList<TagsListItem> tagsList) {
        m_tagsList = tagsList;
    }

    public ArrayList<TagsListItem> getTagsList() {
        return m_tagsList;
    }

    public void addTagItemBeginning(TagsListItem tagItem) {
        m_tagsList.add(0, tagItem);
    }

    public String getResourceUrl(String newItemId) {
        return m_s3Client.getResourceUrl(m_dbConnect.getBucketName(), newItemId);
    }
}
