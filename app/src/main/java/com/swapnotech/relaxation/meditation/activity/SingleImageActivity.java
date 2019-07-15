package com.swapnotech.relaxation.meditation.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.model.MWallpaper;
import com.swapnotech.relaxation.meditation.tools.MyApp;

/**
 * Created by Dev on 11/23/2017.
 */

public class SingleImageActivity extends AppCompatActivity {
    private ImageView imageView;
    private Toolbar toolbar;
    public static MWallpaper mWallpaper;
    private boolean isNotification = false;
    TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_single_image);

        MyApp.getInstance().setupAnalytics("Single Screen");
        isNotification = false;
        imageView = (ImageView) findViewById(R.id.img);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        MainActivity.getInstance().cancelNotification();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (mWallpaper != null) {
            mTitle.setText(mWallpaper.getCategory_title());

            Picasso.with(this).load(mWallpaper.getOriginal()).into(imageView);
//            Ion.with(this)
//                    .load(mWallpaper.getOriginal())
//                    .withBitmap()
//                    .deepZoom()
//                    .intoImageView(imageView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                isNotification = true;
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mWallpaper != null) {
            mTitle.setText(mWallpaper.getCategory_title());

//            Picasso.with(this).load(mWallpaper.getOriginal()).into(imageView);
            Ion.with(this)
                    .load(mWallpaper.getOriginal())
                    .withBitmap()
                    .deepZoom()
                    .intoImageView(imageView);
        }
        MainActivity.getInstance().cancelNotification();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mWallpaper != null) {
            mTitle.setText(mWallpaper.getCategory_title());

//            Picasso.with(this).load(mWallpaper.getOriginal()).into(imageView);
            Ion.with(this)
                    .load(mWallpaper.getOriginal())
                    .withBitmap()
                    .deepZoom()
                    .intoImageView(imageView);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isNotification)
            sendNotification();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.getInstance().cancelNotification();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isNotification = true;
    }


    public void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.meditation_applogo_4);
        Intent intent = new Intent(this, SingleImageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
//        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.meditation_applogo_4));
        builder.setContentTitle("Meditation");
        builder.setAutoCancel(true);
//        builder.setContentText("Your notification content here.");
//        builder.setSubText("Tap to view the website.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());
    }

//    void resetDownload() {
//        // cancel any pending download
//        downloading.cancel();
//        downloading = null;
//
//        // reset the ui
//        download.setText("Download");
//        downloadCount.setText(null);
//        progressBar.setProgress(0);
//    }
}
