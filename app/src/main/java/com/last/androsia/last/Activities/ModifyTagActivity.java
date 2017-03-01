package com.last.androsia.last.Activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.last.androsia.last.Common.DBContract;
import com.last.androsia.last.Common.FilesUtility;
import com.last.androsia.last.Common.TagItem;
import com.last.androsia.last.R;

/**
 * Created by Samoustique on 22/02/2017.
 */

public class ModifyTagActivity extends TagEdit {
    private TagItem m_item;

    private ImageView m_btnSave;
    private ImageView m_btnDelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.modify_tag_activity);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .9),(int)(height * 0.6));

        initGUI();

        // 1. Buttons
        m_btnSave = (ImageView) findViewById(R.id.btnSave);
        m_btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
        m_btnDelete = (ImageView) findViewById(R.id.btnDelete);
        m_btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                delete();
            }
        });

        setTitle("Edit Tag");

        // 2. User Image
        activateSilverImage();

        initItem();
    }

    private void initItem(){
        m_item = m_global.getSelectedItem();

        m_edtTitle.setText(m_item.getTitle());
        double dCtr = m_item.getCtrSeen();
        if(m_item.getType() == TagItem.Type.SCREEN){
            m_radScreen.setChecked(true);
            m_layoutScreen.setVisibility(View.VISIBLE);

            Integer iPart;
            iPart = (int) dCtr;
            double decimal = 100 * dCtr;
            decimal -= 100 * iPart;
            Integer fPart = (int) decimal;
            if (fPart < 1){
                m_edtCounter.setText(iPart.toString());
            } else {
                m_edtScreenEpisode.setText(fPart.toString());
                m_edtScreenSeason.setText(iPart.toString());
            }
        } else {
            m_radBook.setChecked(true);
            Integer ctr = (int) dCtr;
            m_edtCounter.setText(ctr.toString());
        }

        // fill the image
        m_picturePath = m_item.getImgUrl();
        Bitmap img = FilesUtility.decodeSampledBitmapFromResource(m_picturePath);
        m_imagePreview.setImageBitmap(img);
    }

    @Override
    protected void saveTagItem(TagItem tagItem) {
        SQLiteDatabase db = m_global.getDB();

        tagItem.setId(m_item.getId());
        tagItem.setDB(db);

        ContentValues values = new ContentValues();
        values.put(DBContract.TagItem.COLUMN_TITLE, tagItem.getTitle());
        values.put(DBContract.TagItem.COLUMN_IMG_URL, tagItem.getImgUrl());
        values.put(DBContract.TagItem.COLUMN_CTR_SEEN, tagItem.getCtrSeen());
        values.put(DBContract.TagItem.COLUMN_CTR_OWNED, tagItem.getCtrOwned());
        values.put(DBContract.TagItem.COLUMN_TYPE, tagItem.getIType());
        values.put(DBContract.TagItem.COLUMN_DATE, tagItem.getDate());

        db.update(DBContract.TagItem.TABLE_NAME, values, DBContract.TagItem._ID + " = " + tagItem.getId(), null);

        m_global.ModifyItemAndSetBeginning(tagItem);
    }

    @Override
    protected void returnParam() {
        Intent intent = new Intent();
        intent.putExtra(m_global.IS_ITEM_MODIFIED, true);
        setResult(m_global.MODIFY_ACTIVITY, intent);
    }

    private void delete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you really want to delete \"" + m_item.getTitle() + "\" tag ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        doDelete();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void doDelete(){
        SQLiteDatabase db = m_global.getDB();

        db.delete(DBContract.TagItem.TABLE_NAME, DBContract.TagItem._ID + " = " + m_item.getId(), null);

        m_global.deleteItem(m_item);

        returnParam();
        finish();
    }
}
