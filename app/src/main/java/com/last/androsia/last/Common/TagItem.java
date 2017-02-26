package com.last.androsia.last.Common;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class TagItem {
    public enum Type{
        NONE,
        SCREEN,
        BOOK
    }

    private long m_id;
    private String m_title;
    private String m_imgUrl;
    private double m_ctrSeen;
    private double m_ctrOwned;
    private int m_iType;
    private double m_date;

    private SQLiteDatabase m_db;

    public long getId() {
        return m_id;
    }
    public void setId(long id) { m_id = id; }

    public String getTitle() {
        return m_title;
    }
    public void setTitle(String title) { m_title = title; }

    public String getImgUrl() {
        return m_imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        m_imgUrl = imgUrl;
    }

    public double getCtrSeen() { return m_ctrSeen; }
    public void setCtrSeen(double counter) { m_ctrSeen = counter; }

    public double getCtrOwned() { return m_ctrOwned; }
    public void setCtrOwned(double counter) { m_ctrOwned = counter; }

    public double getDate() { return m_date; }
    public void setDate(double date) { m_date = date; }

    public void setDB(SQLiteDatabase db) { m_db = db; }

    public int getIType() { return m_iType; }
    public void setIType(int type) { m_iType = type; }

    public Type getType(){
        Type[] types = Type.values();
        if (types.length < m_iType){
            return Type.NONE;
        }
        return types[m_iType];
    }

    public void setType(Type type) {
        switch (type){
            case SCREEN:
                this.m_iType = 1;
                break;
            case BOOK:
                this.m_iType = 2;
                break;
            default:
                this.m_iType = 0;
                break;
        }
    }

    public void incrementCounter(){
        switch(getType()){
            case BOOK:
                ++m_ctrSeen;
                break;
            case SCREEN:
                int real = (int) m_ctrSeen;
                int decimal = (int) (m_ctrSeen * 100 - real * 100);

                if(decimal == 0){
                    ++m_ctrSeen;
                } else {
                    m_ctrSeen = ((m_ctrSeen * 100) + 1) / 100;
                }
                break;
            default:
                break;
        }

        m_date = (new Date()).getTime();

        // upload increment
        ContentValues values = new ContentValues();
        values.put(DBContract.TagItem.COLUMN_CTR_SEEN, m_ctrSeen);
        values.put(DBContract.TagItem.COLUMN_DATE, m_date);
        m_db.update(DBContract.TagItem.TABLE_NAME, values, "_id=" + m_id, null);
    }

    @Override
    public String toString(){
        return m_title + " #" + m_ctrSeen;
    }
}
