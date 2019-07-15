package com.swapnotech.relaxation.meditation.tools;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by Dev on 12/5/2017.
 */

public class InMobAdManager {
    private static InMobAdManager instance;

    public static InMobAdManager getInstance(Context context) {
        if (instance == null) {
            instance = new InMobAdManager();
            MobileAds.initialize(context, Global.SAMLL_BANNER_AD);
        }
        return instance;
    }

    public void loadAd(AdView mAdView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
