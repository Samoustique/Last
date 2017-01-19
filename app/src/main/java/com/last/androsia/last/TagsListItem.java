package com.last.androsia.last;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by SPhilipps on 1/3/2017.
 */

@DynamoDBTable(tableName="Item")
public class TagsListItem {

    public enum Type{
        NONE,
        SCREEN,
        BOOK
    }

    private String m_id;
    private String m_title;
    private String m_imageUrl;
    private double m_ctrSeen;
    private double m_ctrOwned;
    private Integer m_iType;

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() { return m_id; }
    public void setId(String id) { m_id = id; }

    @DynamoDBAttribute(attributeName = "Title")
    public String getTitle() {
        return m_title;
    }
    public void setTitle(String title) {
        m_title = title;
    }

    @DynamoDBAttribute(attributeName = "Img")
    public String getImageUrl() {
        return m_imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        m_imageUrl = imageUrl;
    }

    @DynamoDBAttribute(attributeName = "CtrSeen")
    public double getCtrSeen() { return m_ctrSeen; }
    public void setCtrSeen(double counter) { m_ctrSeen = counter; }


    @DynamoDBAttribute(attributeName = "CtrOwned")
    public double getCtrOwned() { return m_ctrOwned; }
    public void setCtrOwned(double counter) { m_ctrOwned = counter; }


    @DynamoDBAttribute(attributeName = "Type")
    public Integer getItype() { return m_iType; }
    public void setItype(Integer iType) { m_iType = iType; }

    public Type getType(){
        Type[] types = Type.values();
        if (types.length < m_iType){
            return Type.NONE;
        }
        return types[m_iType];
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
    }

    @Override
    public String toString(){
        return m_title + " #" + m_ctrSeen;
    }
}
