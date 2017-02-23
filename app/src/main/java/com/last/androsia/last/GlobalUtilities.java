package com.last.androsia.last;


import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Samoustique on 03/02/2017.
 */

public class GlobalUtilities extends Application {
    public final int ADD_ACTIVITY = 1;
    public final String IS_ITEM_SAVED = "isItemSaved";

    private ArrayList<TagItem> m_tagsList;
    private SQLiteDatabase m_db;

    public void setTagsList(ArrayList<TagItem> tagsList) {
        m_tagsList = tagsList;
    }

    public ArrayList<TagItem> getTagsList() {
        return m_tagsList;
    }

    public void addTagItemBeginning(TagItem tagItem) {
        m_tagsList.add(0, tagItem);
    }

    public SQLiteDatabase getDB(){
        if(m_db == null){
            DBManagerHelper dbManager = new DBManagerHelper(getBaseContext());
            m_db = dbManager.getWritableDatabase();
        }
        return m_db;
    }
}
