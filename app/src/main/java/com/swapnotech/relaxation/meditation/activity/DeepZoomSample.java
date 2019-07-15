package com.swapnotech.relaxation.meditation.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.ads.AdView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.model.MWallpaper;
import com.swapnotech.relaxation.meditation.tools.AdsManager;
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.InMobAdManager;
import com.swapnotech.relaxation.meditation.tools.MyApp;
import com.swapnotech.relaxation.meditation.tools.MyLog;
import com.swapnotech.relaxation.meditation.tools.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.senab.photoview.PhotoView;

import static android.support.v4.content.FileProvider.getUriForFile;


/**
 * Created by Dev on 11/25/2017.
 */

public class DeepZoomSample extends AppCompatActivity implements View.OnClickListener {
    Animation animation;
    Animation animation2;
    public static MWallpaper mWallpaper;
    private static final float SUPER_MIN_MULTIPLIER = .75f;
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private Handler handler;
    private PhotoView photo;
    private Toolbar toolbar;
    private boolean isNotification = false;
    //    private InterstitialAd mInterstitialAd;
    private Button imgShare;
    public static int addShow;
    private String targetView;
    public static String downlaodedImage;
    private static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    private static final String AUTHORITY = "com.commonsware.android.cp.v4file";

    private Uri uri;
    ImageView imgCk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_single_image);


        Intent intent = getIntent();
        uri = intent.getParcelableExtra("uri");


        MyApp.getInstance().setupAnalytics("DeepZoom Screen");
        MyLog.e("addShow", " is " + addShow);

        isNotification = false;

        if (addShow == 5) {
            isNotification = true;
            AdsManager.getInstance(this).showInterstisial();
        }


        AdView adView = (AdView) findViewById(R.id.adView);
        InMobAdManager.getInstance(this).loadAd(adView);


        photo = (PhotoView) findViewById(R.id.photo);
        photo.setMaximumScale(16);

        MainActivity.getInstance().cancelNotification();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        imgShare = (Button) toolbar.findViewById(R.id.imgShare);
        imgShare.setOnClickListener(this);
        Utils.setFont(mTitle);

        if (mWallpaper != null)
            mTitle.setText(mWallpaper.getCategory_title());
        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        targetView = Utils.getPref("targetView", "0");
        if (targetView.equals("0"))
            targetView();
        handler = new Handler();

//        Ion.with(this)
////                .load(new File(Environment.getExternalStorageDirectory().getPath() + "/" + "RelaxationMeditation" + "/" + downlaodedImage))
//                .load(Global.singleWallpaperURL)
//                .setLogging("DeepZoom", Log.VERBOSE)
//                .withBitmap()
//                .deepZoom()
//                .intoImageView(photo)
//                .setCallback(new FutureCallback<ImageView>() {
//                    @Override
//                    public void onCompleted(Exception e, ImageView result) {
////                        dlg.cancel();
//                    }
//                });


//        File f = new File(getFilesDir(), "test.pdf");
//
//        if (!f.exists()) {
//            AssetManager assets = getAssets();
//
//            try {
//                copy(assets.open("test.pdf"), f);
//            } catch (IOException e) {
//                Log.e("FileProvider", "Exception copying from assets", e);
//            }
//        }
//
//        Intent i =
//                new Intent(Intent.ACTION_VIEW,
//                        FileProvider.getUriForFile(this, AUTHORITY, f));
//
//        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        startActivity(i);
    }

    static private void copy(InputStream in, File dst) throws IOException {
        FileOutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
    }

//    private void takePicture() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(MyApp.getInstance().getApplicationContext().getPackageManager()) != null) {
//            Uri photoURI = null;
//            try {
//                File photoFile = createImageFile();
//                path = photoFile.getAbsolutePath();
//                photoURI = FileProvider.getUriForFile(MyApp.getInstance().getApplicationContext(),
//                        MyApp.getInstance().getApplicationContext().getString(R.string.file_provider_authority),
//                        photoFile);
//
//            } catch (IOException ex) {
//                Log.e("TakePicture", ex.getMessage());
//            }
//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(takePictureIntent, PHOTO_REQUEST_CODE);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                isNotification = true;
                startActivity(new Intent(this, MainActivity.class));
//                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void targetView() {
        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.imgShare), "please click share button if want to share image otherwise click out of circle")
                .cancelable(true)
                .drawShadow(true)
                .targetRadius(20)
                .tintTarget(true), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                if (Build.VERSION.SDK_INT >= 25) {
                    shareImageNougat2(mWallpaper.getThumbnail());
                } else {
                    Utils.shareImageFromSd(mWallpaper.getThumbnail());
                }
                Utils.savePref("targetView", "1");
                // .. which evidently starts the sequence we defined earlier
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetViewSample", "You dismissed me :(");
                Utils.savePref("targetView", "1");
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.getInstance().cancelNotification();
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
        startActivity(new Intent(this, MainActivity.class));
    }

    public void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.meditation_applogo_4);
        Intent intent = new Intent(this, DeepZoomSample.class);
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

    public void shareImageNougat() {
        File imagePath = new File(uri.getPath());
        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        String fileName = s.substring(s.lastIndexOf("/") + 1);
        Uri contentUri = getUriForFile(this, getApplicationContext().getPackageName() + ".provider", imagePath);
        share.setDataAndType(contentUri, "image/*");
        share.putExtra(Intent.EXTRA_STREAM, contentUri);
        startActivity(share);
    }

    public void shareImageNougat2(String s) {
        String fileName = s.substring(s.lastIndexOf("/") + 1);
        File root = Environment.getExternalStorageDirectory();
        Uri path = FileProvider.getUriForFile(this, "com.quiro.fileproviderexample.fileprovider", new File(root.getPath() + "/" + "RelaxationMeditation" + "/" + fileName));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "send image");
        intent.putExtra(Intent.EXTRA_STREAM, path);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgShare) {
//            MyApp.getInstance().setupAnalyticsEvent("Share Image", mWallpaper.getThumbnail());
            if (Build.VERSION.SDK_INT >= 25) {
                shareImageNougat2(mWallpaper.getThumbnail());
            } else {
                Utils.shareImageFromSd(mWallpaper.getThumbnail());
            }


//            Utils.shareImageNougat(mWallpaper.getThumbnail());
//            Utils.shareImageFromSd(mWallpaper.getThumbnail());

//            Utils.shareImage(DeepZoomSample.this, mWallpaper.getOriginal());
//            Utils.shareOption(DeepZoomSample.this, mWallpaper.getOriginal());
        }
    }
}
