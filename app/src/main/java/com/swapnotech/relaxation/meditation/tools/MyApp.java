package com.swapnotech.relaxation.meditation.tools;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

//import com.google.android.gms.analytics.GoogleAnalytics;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;

/**
 * Created by JEWEL on 7/13/2016.
 */
public class MyApp extends Application {
    private static MyApp instance;
    private static Tracker mTracker;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public Context getContext() {
        return instance.getApplicationContext();
    }


    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(Global.ANALYTICS_ID);
        }
        return mTracker;
    }

    public void setupAnalytics(String tag) {

        Tracker mTracker = getDefaultTracker();
        mTracker.setScreenName(tag);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public void setupAnalyticsEvent(String category, String action) {
        Tracker mTracker = getDefaultTracker();

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .build());
    }

    public void setupAnalyticsCampaign(String tag) {

        Tracker mTracker = getDefaultTracker();
        mTracker.setScreenName(tag);
        // In this example, campaign information is set using
// a url string with Google Analytics campaign parameters.
// Note: This is for illustrative purposes. In most cases campaign
//       information would come from an incoming Intent.
        String campaignData = "http://examplepetstore.com/index.html?" +
                "utm_source=email&utm_medium=email_marketing&utm_campaign=summer" +
                "&utm_content=email_variation_1";

// Campaign data sent with this hit.
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setCampaignParamsFromUrl(campaignData)
                .build()
        );

    }

    public void setupAnalyticsSendTime() {
        long l = 0;
        // Build and send timing.
        Tracker mTracker = getDefaultTracker();
        mTracker.send(new HitBuilders.TimingBuilder()
                .setCategory("")
                .setValue(l)
                .setVariable("")
                .setLabel("")
                .build());
    }

    public void setupAnalyticsSendSocialIntraction() {
        // Build and send social interaction.
        Tracker mTracker = getDefaultTracker();
        mTracker.send(new HitBuilders.SocialBuilder()
                .setNetwork("")
                .setAction("")
                .setTarget("")
                .build());
    }


}
