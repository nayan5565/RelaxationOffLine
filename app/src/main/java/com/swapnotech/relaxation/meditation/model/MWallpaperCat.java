package com.swapnotech.relaxation.meditation.model;

/**
 * Created by Dev on 11/27/2017.
 */

public class MWallpaperCat {
    private int categoryTotalItem, ordering;
    private int categoryId;
    private int categoryType;
    private int categoryUpdateAvailable;
    private int image;
    private String categoryKeyword = "";
    private String categoryTitle = "", categoryDetails = "", categoryPhoto = "", categoryThumb = "";

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public int getCategoryUpdateAvailable() {
        return categoryUpdateAvailable;
    }

    public void setCategoryUpdateAvailable(int categoryUpdateAvailable) {
        this.categoryUpdateAvailable = categoryUpdateAvailable;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDetails() {
        return categoryDetails;
    }

    public void setCategoryDetails(String categoryDetails) {
        this.categoryDetails = categoryDetails;
    }

    public String getCategoryPhoto() {
        return categoryPhoto;
    }

    public void setCategoryPhoto(String categoryPhoto) {
        this.categoryPhoto = categoryPhoto;
    }

    public String getCategoryThumb() {
        return categoryThumb;
    }

    public void setCategoryThumb(String categoryThumb) {
        this.categoryThumb = categoryThumb;
    }

    public int getCategoryTotalItem() {
        return categoryTotalItem;
    }

    public void setCategoryTotalItem(int categoryTotalItem) {
        this.categoryTotalItem = categoryTotalItem;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public String getCategoryKeyword() {
        return categoryKeyword;
    }

    public void setCategoryKeyword(String categoryKeyword) {
        this.categoryKeyword = categoryKeyword;
    }
}
