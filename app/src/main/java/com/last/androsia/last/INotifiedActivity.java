package com.last.androsia.last;

/**
 * Created by Samoustique on 03/02/2017.
 */
public interface INotifiedActivity {
    void notifyIdGenerated(String newItemId);
    void notifyItemCreated(DBItem dbItem);
    void notifyPictureUploaded(String newItemId);
}
