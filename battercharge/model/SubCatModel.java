package com.app.battercharge.model;

import java.io.Serializable;

public class SubCatModel implements Serializable {
    String animation;


    int f8526id;
    String sound;

    public SubCatModel(int i, String str, String str2) {
        this.f8526id = i;
        this.animation = str;
        this.sound = str2;
    }

    public int getId() {
        return this.f8526id;
    }

    public void setId(int i) {
        this.f8526id = i;
    }

    public String getAnimation() {
        return this.animation;
    }

    public void setAnimation(String str) {
        this.animation = str;
    }

    public String getSound() {
        return this.sound;
    }

    public void setSound(String str) {
        this.sound = str;
    }
}
