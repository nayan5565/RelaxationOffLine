package com.swapnotech.relaxation.meditation.tools;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by JEWEL on 7/30/2016.
 */
public class AdsManager {
    private static AdsManager instance;
    private InterstitialAd mInterstitialAd;
    private Activity context;

    private AdsManager() {

    }

    public static AdsManager getInstance(Activity context) {
        instance = new AdsManager();
        instance.context = context;
        MobileAds.initialize(context, Global.BANNER_APP_ID);
        return instance;
    }

    public void setupBannerAd(int adViewResId) {
        AdView adView = (AdView) context.findViewById(adViewResId);
        if (!Utils.isInternetOn()) {
            adView.setVisibility(View.GONE);
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    public void showInterstisial(){
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(Global.FULL_SCREEN_AD);
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(context, "The interstitial is loaded", Toast.LENGTH_SHORT).show();
                mInterstitialAd.show();
            }
        });
    }

    private void fullScreenAd() {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(Global.INTERSTISIAL_UID_TEST);
        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        // Start loading the ad now so that it is ready by the time the user is ready to go to
        // the next level.
        mInterstitialAd.loadAd(adRequestBuilder.build());

        // Optionally populate the ad request builder.
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

        // Set an AdListener.
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                Toast.makeText(context, "The interstitial is loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
//                goToNextLevel();
            }
        });
    }
}
