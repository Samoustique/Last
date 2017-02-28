package com.last.androsia.last.Activities;

import android.app.AlertDialog;
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
        Bitmap img = FilesUtility.decodeSampledBitmapFromResource(m_item.getImgUrl());
        m_imagePreview.setImageBitmap(img);
    }

    @Override
    protected void saveTagItem(TagItem tagItem) {

    }

    @Override
    protected void returnParam() {
        setResult(m_global.MODIFY_ACTIVITY, new Intent());
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

        m_global.addDeleteItem(m_item);

        setResult(m_global.MODIFY_ACTIVITY, new Intent());
        finish();
    }
}
