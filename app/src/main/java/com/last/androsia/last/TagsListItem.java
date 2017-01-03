package com.last.androsia.last;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public class TagsListItem {
    private String name;
    private String imageUrl;
    private int age;

    public TagsListItem(String name, int age, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return this.name + " : " + this.age;
    }
}
