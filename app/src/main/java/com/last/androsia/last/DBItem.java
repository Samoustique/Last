package com.last.androsia.last;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by SPhilipps on 1/3/2017.
 */

@DynamoDBTable(tableName="Item")
public class DBItem {
    public String m_id;
    public String m_title;
    public String m_imageUrl;
    public double m_ctrSeen;
    public double m_ctrOwned;
    public Integer m_iType;

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
}
