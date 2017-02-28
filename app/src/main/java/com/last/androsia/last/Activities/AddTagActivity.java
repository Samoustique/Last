package com.last.androsia.last.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import com.last.androsia.last.Common.DBContract;
import com.last.androsia.last.R;
import com.last.androsia.last.Common.TagItem;

public class AddTagActivity extends TagEdit {
    private ImageView m_btnSave;
    private ImageView m_btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.add_tag_activity);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_add_tag);

        initGUI();

        // 1. Title buttons
        m_btnSave = (ImageView) findViewById(R.id.btnSave);
        m_btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
        m_btnBack = (ImageView) findViewById(R.id.btnBack);
        m_btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                back();
            }
        });
        setTitle("New Tag");

        // 2. User Image
        activateGoldImage();
    }

    private void back() {
        Intent intent = new Intent();
        intent.putExtra(m_global.IS_ITEM_SAVED, false);
        setResult(m_global.ADD_ACTIVITY, intent);
        this.finish();
    }

    @Override
    protected void saveTagItem(TagItem tagItem) {
        SQLiteDatabase db = m_global.getDB();

        ContentValues values = new ContentValues();
        values.put(DBContract.TagItem.COLUMN_TITLE, tagItem.getTitle());
        values.put(DBContract.TagItem.COLUMN_IMG_URL, tagItem.getImgUrl());
        values.put(DBContract.TagItem.COLUMN_CTR_SEEN, tagItem.getCtrSeen());
        values.put(DBContract.TagItem.COLUMN_CTR_OWNED, tagItem.getCtrOwned());
        values.put(DBContract.TagItem.COLUMN_TYPE, tagItem.getIType());
        values.put(DBContract.TagItem.COLUMN_DATE, tagItem.getDate());

        long id = db.insert(DBContract.TagItem.TABLE_NAME, null, values);
        tagItem.setId(id);
        tagItem.setDB(db);

        m_global.addTagItemBeginning(tagItem);
    }

    @Override
    protected void returnParam() {
        Intent intent = new Intent();
        intent.putExtra(m_global.IS_ITEM_SAVED, true);
        setResult(m_global.ADD_ACTIVITY, intent);
    }
}
