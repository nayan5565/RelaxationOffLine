package com.swapnotech.relaxation.meditation.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev on 11/29/2017.
 */

public class MItems {
    private int Id, InApp, View, NumberOfPhotos, CategoryId, UpdateAvailable, Status, TotalLike,image,sound;
    private String Title, Photo, Thumb, MainSound, CategoryTitle, Keyword, Description, UpdateDateTime;
    private float price;
    private List<MSound> sounds;
    private List<MWallpaper2> wallpaper;

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public List<MSound> getSounds() {
        return sounds;
    }

    public void setSounds(List<MSound> sounds) {
        this.sounds = sounds;
    }

    public List<MWallpaper2> getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(List<MWallpaper2> wallpaper) {
        this.wallpaper = wallpaper;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getInApp() {
        return InApp;
    }

    public void setInApp(int inApp) {
        InApp = inApp;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getView() {
        return View;
    }

    public void setView(int view) {
        View = view;
    }

    public int getNumberOfPhotos() {
        return NumberOfPhotos;
    }

    public void setNumberOfPhotos(int numberOfPhotos) {
        NumberOfPhotos = numberOfPhotos;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getUpdateAvailable() {
        return UpdateAvailable;
    }

    public void setUpdateAvailable(int updateAvailable) {
        UpdateAvailable = updateAvailable;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getTotalLike() {
        return TotalLike;
    }

    public void setTotalLike(int totalLike) {
        TotalLike = totalLike;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        Thumb = thumb;
    }

    public String getMainSound() {
        return MainSound;
    }

    public void setMainSound(String mainSound) {
        MainSound = mainSound;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public String getKeyword() {
        return Keyword;
    }

    public void setKeyword(String keyword) {
        Keyword = keyword;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUpdateDateTime() {
        return UpdateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        UpdateDateTime = updateDateTime;
    }




}
