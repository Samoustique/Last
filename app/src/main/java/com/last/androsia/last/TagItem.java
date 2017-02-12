package com.last.androsia.last;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class TagItem {
    public enum Type{
        NONE,
        SCREEN,
        BOOK
    }

    private String m_title;
    //private String m_imgUrl;
    private byte[] m_img;
    private double m_ctrSeen;
    private double m_ctrOwned;
    private int m_iType;
    private double m_date;

    public String getTitle() {
        return m_title;
    }
    public void setTitle(String title) { m_title = title; }

    /*public String getImageUrl() {
        return m_imgUrl;
    }
    public void setImageUrl(String imgUrl) {
        m_imgUrl = imgUrl;
    }*/

    public byte[] getImage() {
        return m_img;
    }
    public void setImage(byte[] img) {
        m_img = img;
    }

    public double getCtrSeen() { return m_ctrSeen; }
    public void setCtrSeen(double counter) { m_ctrSeen = counter; }

    public double getCtrOwned() { return m_ctrOwned; }
    public void setCtrOwned(double counter) { m_ctrOwned = counter; }

    public double getDate() { return m_date; }
    public void setDate(double date) { m_date = date; }

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
    }

    @Override
    public String toString(){
        return m_title + " #" + m_ctrSeen;
    }
}
