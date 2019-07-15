package com.swapnotech.relaxation.meditation.model;

import java.util.ArrayList;

/**
 * Created by Dev on 11/29/2017.
 */

public class MItemList {
    private int adShowTime;
    private ArrayList<MItems> items;

    public int getAdShowTime() {
        return adShowTime;
    }

    public void setAdShowTime(int adShowTime) {
        this.adShowTime = adShowTime;
    }

    public ArrayList<MItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<MItems> items) {
        this.items = items;
    }
}
