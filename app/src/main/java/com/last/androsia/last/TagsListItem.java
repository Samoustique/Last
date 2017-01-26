package com.last.androsia.last;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class TagsListItem implements Comparable<TagsListItem> {

    public enum Type{
        NONE,
        SCREEN,
        BOOK
    }

    private DBItem m_dbItem;
    private DynamoDBMapper m_mapper;

    public TagsListItem(DBItem dbItem, DynamoDBMapper mapper) {
        m_dbItem = dbItem;
        m_mapper = mapper;
    }

    public String getId() { return m_dbItem.m_id; }
    public void setId(String id) { m_dbItem.m_id = id; }

    public String getTitle() {
        return m_dbItem.m_title;
    }
    public void setTitle(String title) { m_dbItem.m_title = title; }

    public String getImageUrl() {
        return m_dbItem.m_imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        m_dbItem.m_imageUrl = imageUrl;
    }

    public double getCtrSeen() { return m_dbItem.m_ctrSeen; }
    public void setCtrSeen(double counter) { m_dbItem.m_ctrSeen = counter; }

    public double getCtrOwned() { return m_dbItem.m_ctrOwned; }
    public void setCtrOwned(double counter) { m_dbItem.m_ctrOwned = counter; }

    public String getDate() { return m_dbItem.m_date; }
    public void setDate(String date) { m_dbItem.m_date = date; }

    public Type getType(){
        Type[] types = Type.values();
        if (types.length < m_dbItem.m_iType){
            return Type.NONE;
        }
        return types[m_dbItem.m_iType];
    }

    public void incrementCounter(){
        switch(getType()){
            case BOOK:
                ++m_dbItem.m_ctrSeen;
                break;
            case SCREEN:
                int real = (int) m_dbItem.m_ctrSeen;
                int decimal = (int) (m_dbItem.m_ctrSeen * 100 - real * 100);

                if(decimal == 0){
                    ++m_dbItem.m_ctrSeen;
                } else {
                    m_dbItem.m_ctrSeen = ((m_dbItem.m_ctrSeen * 100) + 1) / 100;
                }
                break;
            default:
                break;
        }
        new DBUpdater(m_mapper).execute(m_dbItem);
    }

    @Override
    public String toString(){
        return m_dbItem.m_title + " #" + m_dbItem.m_ctrSeen;
    }

    @Override
    public int compareTo(TagsListItem other) {
        return other.m_dbItem.m_date.compareTo(m_dbItem.m_date);
    }
}
