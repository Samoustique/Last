package com.last.androsia.last;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import java.io.File;
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

        TransferUtility transferUtility = new TransferUtility(m_s3Client, m_context);
        TransferObserver transferObserver = transferUtility.upload(m_bucketName, newItemId, m_pictureFile);
        transferObserverListener(transferObserver, newItemId);

        return newItemId;
    }

    private void transferObserverListener(TransferObserver transferObserver, final String newItemId){
        transferObserver.setTransferListener(new TransferListener(){
            @Override
            public void onStateChanged(int id, TransferState state) {
                if(state == TransferState.COMPLETED){
                    m_activity.notifyPictureUploaded(newItemId);
                } else if (state == TransferState.IN_PROGRESS){
                    Toast.makeText(m_context, "Uploading new Tag...", Toast.LENGTH_LONG).show();
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