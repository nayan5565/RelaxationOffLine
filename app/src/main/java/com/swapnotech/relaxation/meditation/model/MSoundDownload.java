package com.swapnotech.relaxation.meditation.model;

/**
 * Created by Dev on 3/7/2018.
 */

public class MSoundDownload {
    private int id;
    private String sound;
    private boolean isDownload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }
}
