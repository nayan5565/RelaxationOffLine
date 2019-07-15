package com.swapnotech.relaxation.meditation.model;

import java.util.ArrayList;

/**
 * Created by Dev on 11/27/2017.
 */

public class MWallpaperCatList {
    private int totalCategory, totalVideo, adShowTime;
    private String letter;
    private ArrayList<MWallpaperCat> categories;

    public int getTotalCategory() {
        return totalCategory;
    }

    public void setTotalCategory(int totalCategory) {
        this.totalCategory = totalCategory;
    }

    public int getTotalVideo() {
        return totalVideo;
    }

    public void setTotalVideo(int totalVideo) {
        this.totalVideo = totalVideo;
    }

    public int getAdShowTime() {
        return adShowTime;
    }

    public void setAdShowTime(int adShowTime) {
        this.adShowTime = adShowTime;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public ArrayList<MWallpaperCat> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<MWallpaperCat> categories) {
        this.categories = categories;
    }
}
