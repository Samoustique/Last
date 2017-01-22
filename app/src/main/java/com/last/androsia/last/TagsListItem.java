package com.last.androsia.last;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class TagsListItem {

    public enum Type{
        NONE,
        SCREEN,
        BOOK
    }

    private DBItem m_dbItem;
    private DBUpdater m_dbUpdater;

    public TagsListItem(DBItem dbItem) {
        m_dbItem = dbItem;
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

    public DBUpdater getDBUpdater() { return m_dbUpdater; }
    public void setDBUpdater(DBUpdater dbUpdater) { m_dbUpdater = dbUpdater; }

    public Integer getItype() { return m_dbItem.m_iType; }
    public void setItype(Integer iType) { m_dbItem.m_iType = iType; }

    public DBItem getDBItem() { return m_dbItem; }
    public void setDBItem(DBItem dbItem) { m_dbItem = dbItem; }

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
        m_dbUpdater.execute(m_dbItem);
    }

    @Override
    public String toString(){
        return m_dbItem.m_title + " #" + m_dbItem.m_ctrSeen;
    }
}
