package com.last.androsia.last.Common;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Samoustique on 03/02/2017.
 */

public class GlobalUtilities extends Application {
    public final int ADD_ACTIVITY = 1;
    public final int MODIFY_ACTIVITY = 2;
    public final String IS_ITEM_SAVED = "isItemSaved";
    public final String IS_ITEM_MODIFIED = "isItemModified";

    private ArrayList<TagItem> m_tagsList;
    private SQLiteDatabase m_db;
    private TagItem m_selectedItem;
    private boolean m_isBookDisplayed = true;
    private boolean m_isScreenDisplayed = true;

    public void setSelectedItem(TagItem selectedItem) {
        m_selectedItem = selectedItem;
    }

    public TagItem getSelectedItem() {
        return m_selectedItem;
    }

    public void setTagsList(ArrayList<TagItem> tagsList) {
        m_tagsList = tagsList;
    }

    public ArrayList<TagItem> getTagsListToDisplay() {
        if(m_isScreenDisplayed && m_isBookDisplayed) {
            return m_tagsList;
        }

        ArrayList<TagItem> tagsToDisplay = new ArrayList<>();

        for(TagItem item : m_tagsList){
            TagItem.Type type = item.getType();
            if((type == TagItem.Type.SCREEN && m_isScreenDisplayed) ||
                    (type == TagItem.Type.BOOK && m_isBookDisplayed)){
                tagsToDisplay.add(item);
            }
        }

        return tagsToDisplay;
    }

    public void addTagItemBeginning(TagItem tagItem) {
        m_tagsList.add(0, tagItem);
    }

    public void deleteItem(TagItem item) {
        m_tagsList.remove(item);
    }

    public void ModifyItemAndSetBeginning(TagItem itemToUse) {
        Iterator<TagItem> iterator = m_tagsList.iterator();
        while (iterator.hasNext()) {
            TagItem item = iterator.next();
            if(item.getId() == itemToUse.getId()){
                iterator.remove();
            }
        }
        addTagItemBeginning(itemToUse);
    }

    public SQLiteDatabase getDB(){
        if(m_db == null){
            DBManagerHelper dbManager = new DBManagerHelper(getBaseContext());
            m_db = dbManager.getWritableDatabase();
        }
        return m_db;
    }

    public void showScreenTags() {
        m_isScreenDisplayed = true;
    }

    public void showBookTags() {
        m_isBookDisplayed = true;
    }

    public void hideScreenTags() {
        m_isScreenDisplayed = false;
    }

    public void hideBookTags() {
        m_isBookDisplayed = false;
    }
}
