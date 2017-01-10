package com.last.androsia.last;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class TagsListItem {

    public enum Type{
        BOOK,
        SCREEN
    }

    private String m_title;
    private String m_imageUrl;
    private double m_counter;
    private Type m_type;

    public TagsListItem(String title, double counter, String imageUrl, Type type) {
        m_title = title;
        m_imageUrl = imageUrl;
        m_counter = counter;
        m_type = type;
    }

    public String getTitle() {
        return m_title;
    }

    public void setTitle(String title) {
        m_title = title;
    }

    public String getImageUrl() {
        return m_imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        m_imageUrl = imageUrl;
    }

    public double getCounter() {
        return m_counter;
    }

    public void setCounter(double counter) {
        m_counter = counter;
    }

    public Type getType() { return m_type; }

    public void setType(Type type) {
        m_type = type;
    }

    public void incrementCounter(){
        switch(m_type){
            case BOOK:
                ++m_counter;
                break;
            case SCREEN:
                int real = (int) m_counter;
                int decimal = (int) (m_counter * 100 - real * 100);

                if(decimal == 0){
                    ++m_counter;
                } else {
                    m_counter = ((m_counter * 100) + 1) / 100;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String toString(){
        return m_title + " #" + m_counter;
    }
}
