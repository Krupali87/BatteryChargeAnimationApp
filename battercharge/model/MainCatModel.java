package com.app.battercharge.model;

public class MainCatModel {
    String categoryAnimation;
    Integer categoryId;
    String categoryName;
    String categorySound;

    public MainCatModel(String str, Integer num, String str2, String str3) {
        this.categoryName = str;
        this.categoryId = num;
        this.categoryAnimation = str2;
        this.categorySound = str3;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer num) {
        this.categoryId = num;
    }

    public String getCategoryAnimation() {
        return this.categoryAnimation;
    }

    public void setCategoryAnimation(String str) {
        this.categoryAnimation = str;
    }

    public String getCategorySound() {
        return this.categorySound;
    }

    public void setCategorySound(String str) {
        this.categorySound = str;
    }
}
