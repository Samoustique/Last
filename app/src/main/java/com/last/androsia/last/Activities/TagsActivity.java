package com.last.androsia.last.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.last.androsia.last.Grid.CustomAdapter;
import com.last.androsia.last.Common.DBContract;
import com.last.androsia.last.Common.DBManagerHelper;
import com.last.androsia.last.Grid.ExpandedGridView;
import com.last.androsia.last.Common.GlobalUtilities;
import com.last.androsia.last.Common.ImgCounterView;
import com.last.androsia.last.Grid.OnLongTagClickListener;
import com.last.androsia.last.Trio.LastestTrio;
import com.last.androsia.last.Grid.OnTagClickListener;
import com.last.androsia.last.R;
import com.last.androsia.last.Common.TagItem;

import java.util.ArrayList;
import java.util.List;

public class TagsActivity extends Activity {
    private LastestTrio m_trio;
    private ExpandedGridView m_tagsGridView;
    private ImageView m_btnGoToAddActivity;
    private GlobalUtilities m_global;

    TagsActivity(){}

    // only for debug
    private void deleteDB(SQLiteDatabase db){
        DBManagerHelper dbManager = new DBManagerHelper(getBaseContext());
        dbManager.onUpgrade(db, 0, 0);
    }

    private ArrayList<TagItem> retrieveTagsFromDB(){
        SQLiteDatabase db = m_global.getDB();
        //deleteDB(db);

        String sortOrder = DBContract.TagItem.COLUMN_DATE + " DESC";

        Cursor cursor = db.query(
                DBContract.TagItem.TABLE_NAME,            // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<TagItem> items = new ArrayList<>();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.TagItem._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.TagItem.COLUMN_TITLE));
            double date = cursor.getDouble(cursor.getColumnIndexOrThrow(DBContract.TagItem.COLUMN_DATE));
            String imgUrl = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.TagItem.COLUMN_IMG_URL));
            double ctrOwned = cursor.getDouble(cursor.getColumnIndexOrThrow(DBContract.TagItem.COLUMN_CTR_OWNED));
            double ctrSeen = cursor.getDouble(cursor.getColumnIndexOrThrow(DBContract.TagItem.COLUMN_CTR_SEEN));
            int type = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.TagItem.COLUMN_TYPE));

            TagItem item = new TagItem();
            item.setTitle(title);
            item.setDate(date);
            item.setImgUrl(imgUrl);
            item.setCtrOwned(ctrOwned);
            item.setCtrSeen(ctrSeen);
            item.setIType(type);
            item.setId(id);
            item.setDB(db);

            items.add(item);
        }
        cursor.close();

        return items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.tags_activity);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

        m_global = (GlobalUtilities) getApplicationContext();

        m_btnGoToAddActivity = (ImageView) findViewById(R.id.btnGoToAddActivity);
        m_btnGoToAddActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToAddActivity();
            }
        });

        m_global.setTagsList(retrieveTagsFromDB());
        displayTags();
    }

    private void displayTags(){
        List trioList;
        ArrayList<TagItem> clonedList = new ArrayList<>(m_global.getTagsList());
        if (clonedList.size() > 3) {
            trioList = new ArrayList<>(clonedList.subList(0, 3));
        } else {
            trioList = new ArrayList<>(clonedList);
        }
        clonedList.removeAll(trioList);

        m_trio = new LastestTrio(
                this,
                trioList,
                new ImgCounterView((ImageView) findViewById(R.id.imgUserGold), (TextView) findViewById(R.id.txtCounterGold), (RelativeLayout) findViewById(R.id.layoutGold)),
                new ImgCounterView((ImageView) findViewById(R.id.imgUserSilver), (TextView) findViewById(R.id.txtCounterSilver), (RelativeLayout) findViewById(R.id.layoutSilver)),
                new ImgCounterView((ImageView) findViewById(R.id.imgUserBronze), (TextView) findViewById(R.id.txtCounterBronze), (RelativeLayout) findViewById(R.id.layoutBronze))
        );
        m_trio.setupClickListeners();
        m_trio.display();

        CustomAdapter adapter = new CustomAdapter(this, clonedList);
        m_tagsGridView = (ExpandedGridView) findViewById(R.id.tagsGridView);
        m_tagsGridView.setAdapter(adapter);
        m_tagsGridView.setExpanded(true);
        m_tagsGridView.setOnItemClickListener(new OnTagClickListener(adapter));
        m_tagsGridView.setOnItemLongClickListener(new OnLongTagClickListener(this, adapter));
    }

    public void goToAddActivity(){
        startActivityForResult(new Intent(this, AddTagActivity.class), m_global.ADD_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == m_global.ADD_ACTIVITY) {
            if(null != data){
                if(data.getBooleanExtra(m_global.IS_ITEM_SAVED, false)){
                    displayTags();
                }
            }
        } else if (requestCode == m_global.MODIFY_ACTIVITY) {
            m_global.setSelectedItem(null);
        }
    }

    public void createPopUp(TagItem item) {
        m_global.setSelectedItem(item);
        startActivityForResult(new Intent(this, ModifyTagActivity.class), m_global.MODIFY_ACTIVITY);
    }
}