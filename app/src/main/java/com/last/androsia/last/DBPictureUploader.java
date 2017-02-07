package com.last.androsia.last;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by SPhilipps on 1/20/2017.
 */
public class DBPictureUploader extends AsyncTask<String, Void, String> {
    private File m_pictureFile;
    private Context m_context;
    private String m_bucketName;
    private AmazonS3Client m_s3Client;
    private INotifiedActivity m_activity;

    public DBPictureUploader(INotifiedActivity activity,
                             AmazonS3Client s3Client,
                             String bucketName,
                             Context context,
                             File pictureFile) {
        m_activity = activity;
        m_s3Client = s3Client;
        m_bucketName = bucketName;
        m_context = context;
        m_pictureFile = pictureFile;
    }

    @Override
    protected String doInBackground(String... params) {
        if(params.length != 1){
            return "";
        }

        String newItemId = params[0];

        // ---
        try{
            PutObjectRequest object = new PutObjectRequest(m_bucketName, newItemId, m_pictureFile);
            AccessControlList acl = new AccessControlList();
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            object.withAccessControlList(acl);
            m_s3Client.putObject(object);
        } catch (Exception e){
            // TODO delete the DBItem created and delete folder Last
            Toast.makeText(m_context, "Error while uploading...", Toast.LENGTH_LONG).show();
        }
        // ---

        /*TransferUtility transferUtility = new TransferUtility(m_s3Client, m_context);

        TransferObserver transferObserver =
                transferUtility.upload(m_bucketName, newItemId, m_pictureFile, CannedAccessControlList.PublicRead);

        transferObserverListener(transferObserver, newItemId);*/

        return newItemId;
    }

    @Override
    protected void onPostExecute(String newItemId) {
        m_activity.notifyPictureUploaded(newItemId);
    }

    private void transferObserverListener(TransferObserver transferObserver, final String newItemId){
        transferObserver.setTransferListener(new TransferListener(){
            @Override
            public void onStateChanged(int id, TransferState state) {
                if(state == TransferState.COMPLETED){
                    m_activity.notifyPictureUploaded(newItemId);
                } else if (state == TransferState.IN_PROGRESS){
                    Toast.makeText(m_context, "Uploading...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            }

            @Override
            public void onError(int id, Exception ex) {
                // TODO delete the DBItem created and delete folder Last
                Toast.makeText(m_context, "Error while uploading picture", Toast.LENGTH_LONG).show();
            }
        });
    }
}