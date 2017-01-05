package com.last.androsia.last;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class TagsListItem {
    private String m_title;
    private String m_imageUrl;
    private int m_counter;

    public TagsListItem(String title, int counter, String imageUrl) {
        m_title = title;
        m_imageUrl = imageUrl;
        m_counter = counter;
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

    public int getCounter() {
        return m_counter;
    }

    public void setCounter(int counter) {
        m_counter = counter;
    }

    public void incrementCounter(){
        ++m_counter;
    }

    @Override
    public String toString(){
        return m_title + " #" + m_counter;
    }
}
