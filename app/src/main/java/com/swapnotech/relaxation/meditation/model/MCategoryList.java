package com.swapnotech.relaxation.meditation.model;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/15/2016.
 */
public class MCategoryList {
    private int totalCategory, totalVideo, adShowTime;
    private String letter;
    private ArrayList<MCategory> categories;
    private ArrayList<MWallpaperCat> mWallpaperCatLists;

    public ArrayList<MWallpaperCat> getmWallpaperCatLists() {
        return mWallpaperCatLists;
    }

    public void setmWallpaperCatLists(ArrayList<MWallpaperCat> mWallpaperCatLists) {
        this.mWallpaperCatLists = mWallpaperCatLists;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

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


    public ArrayList<MCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<MCategory> categories) {
        this.categories = categories;
    }
}
