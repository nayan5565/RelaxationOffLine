package com.swapnotech.relaxation.meditation.activity;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.squareup.picasso.Picasso;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.model.MWallpaper;
import com.swapnotech.relaxation.meditation.model.Mdownload;
import com.swapnotech.relaxation.meditation.tools.DBManager;
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.InMobAdManager;
import com.swapnotech.relaxation.meditation.tools.MyApp;
import com.swapnotech.relaxation.meditation.tools.MyLog;
import com.swapnotech.relaxation.meditation.tools.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Dev on 11/25/2017.
 */

public class ProgressBarDownload extends AppCompatActivity {
    public static final String PARENT = Environment.getExternalStorageDirectory().getPath();
    public static MWallpaper mWallpaper;
    public static String image = File.separator + "Relax";
    long progress;
    TextView downloadCount, tvName;
    ProgressBar progressBar;
    Future<File> downloading;
    ImageView imgSmall;
    File d = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    DownloadManager.Request request;
    Bitmap bitMapImg;
    long bytesDownloaded;
    private boolean isNotification = false;
    private String imageName = "";
    private DownloadManager downloadmanager;
    private int STORAGE_PERMISSION_CODE = 23;
    private Mdownload mdownload;
    ArrayList<Mdownload> mdownloadArrayList;
    private RelativeLayout rlBackg;
    private ImageView imgFullBg;
    private static final String TAG = "ProgressDownload";
//    File f = new File(d,);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        rlBackg = (RelativeLayout) findViewById(R.id.rlBackg);
        imgFullBg = (ImageView) findViewById(R.id.imgFullBg);


        cancelNotification();
        // Enable global Ion logging
        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG);

        MyApp.getInstance().setupAnalytics("Download Screen");
//        Global.downloadImageList = new ArrayList<>();
        mdownloadArrayList = new ArrayList<>();

        getLicalData();

        AdView adView = (AdView) findViewById(R.id.adView);
        InMobAdManager.getInstance(this).loadAd(adView);
        downloadCount = (TextView) findViewById(R.id.download_count);
        tvName = (TextView) findViewById(R.id.tvName);
        imgSmall = (ImageView) findViewById(R.id.imgSmall);
        Ion.with(imgSmall)
                .error(R.drawable.placeholder_image)
                .load(Global.singleWallpaperURL);
        if (mWallpaper != null) {
            imageName = mWallpaper.getThumbnail().substring(mWallpaper.getThumbnail().lastIndexOf("/") + 1);
            DeepZoomSample.downlaodedImage = imageName;
            tvName.setText(mWallpaper.getCategory_title());
//            Ion.with(imgSmall)
//                    .error(R.drawable.placeholder_image)
//                    .load(mWallpaper.getThumbnail());
        }
        Utils.setFont(downloadCount, tvName);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        requestStoragePermissionToMashmallow();

//        imaDowWay();
    }

    private void getLicalData() {
        mdownloadArrayList = DBManager.getInstance().getData(DBManager.TABLE_DOWNLOAD, new Mdownload());
        MyLog.e("downloadList", " size " + mdownloadArrayList.size());
    }

    private void requestStoragePermissionToMashmallow() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.INTERNET}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                imaDowWay();
//                downloadAssets();
                //Displaying a toast
//                Utils.toastMassage(this, "Permission granted now you can read the storage");
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(ProgressBarDownload.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    void resetDownload() {
        // cancel any pending download
        downloading.cancel();
        downloading = null;

        // reset the ui
        downloadCount.setText(null);
        progressBar.setProgress(0);
    }

    private void imaDowWay() {

//        if (downloading != null) {
//            progressBar.setVisibility(View.GONE);
//            startActivity(new Intent(ProgressBarDownload.this, DeepZoomSample.class));
////            resetDownload();
//            return;
//        }
        for (int i = 0; i < mdownloadArrayList.size(); i++) {
            if (mWallpaper.getThumbnail().equals(mdownloadArrayList.get(i).getDownloadFile())) {
                Picasso.with(this).load(Global.singleWallpaperURL).into(imgFullBg);
                progressBar.setVisibility(View.GONE);
                imgSmall.setVisibility(View.GONE);
                tvName.setVisibility(View.GONE);
                isNotification = true;
                if (DeepZoomSample.addShow == 5)
                    DeepZoomSample.addShow = 1;
                startActivity(new Intent(ProgressBarDownload.this, DeepZoomSample.class));
                finish();
                MyLog.e("downloadList", " already downloaded ");
                Toast.makeText(this, TAG + " already downloaded", Toast.LENGTH_SHORT).show();
                return;
            }
        }
//        Toast.makeText(ProgressBarDownload.this, "start downloading", Toast.LENGTH_SHORT).show();
        MyApp.getInstance().setupAnalyticsEvent("Downloaded", mWallpaper.getThumbnail());
        imgSmall.setVisibility(View.VISIBLE);
        tvName.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        File mydir = new File(Environment.getExternalStorageDirectory() + "/RelaxationMeditation/");
        if (!mydir.exists())
            mydir.mkdirs();
        // this is a 180MB zip file to test with
        downloading = Ion.with(ProgressBarDownload.this)
                .load(mWallpaper.getOriginal())
//                .load("https://raw.githubusercontent.com/koush/ion/master/ion-sample/assets/telescope.jpg")
                // attach the percentage report to a progress bar.
                // can also attach to a ProgressDialog with progressDialog.
                .progressBar(progressBar)

                // callbacks on progress can happen on the UI thread
                // via progressHandler. This is useful if you need to update a TextView.
                // Updates to TextViews MUST happen on the UI thread.
                .progressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Utils.log("total:" + total);
                        progress = ((downloaded * 100) / total);
                        downloadCount.setText(progress + "%");
//                        downloadCount.setText("" + downloaded + " / " + total);
                    }
                })
                // write to a file
//                .write(getFileStreamPath("zip-" + System.currentTimeMillis() + ".zip"))
//                .write(getFileStreamPath( Environment.getExternalStorageDirectory().getPath()
//                        + "/saved.jpg"))
                .write(new File(Environment.getExternalStorageDirectory().getPath() + "/" + "RelaxationMeditation" + "/" + imageName))
                // run a callback on completion
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {

                        if (e != null) {
                            resetDownload();
                            Toast.makeText(ProgressBarDownload.this, "Error downloading file", Toast.LENGTH_LONG).show();
                            return;
                        }
//                        Global.downloadImageList.add(mWallpaper.getThumbnail());
                        mdownload = new Mdownload();
                        mdownload.setDownloadFile(mWallpaper.getThumbnail());
                        DBManager.getInstance().addDownload(mdownload, DBManager.TABLE_DOWNLOAD);
//                        Toast.makeText(ProgressBarDownload.this, "start downloading", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(ProgressBarDownload.this, "File upload complete", Toast.LENGTH_LONG).show();
                        isNotification = true;
                        DeepZoomSample.addShow++;
                        if (DeepZoomSample.addShow == 6)
                            DeepZoomSample.addShow = 1;
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(ProgressBarDownload.this, DeepZoomSample.class));
                        finish();
                    }
                });
    }

    private void imageDown() {
        Ion.with(ProgressBarDownload.this)

                .load(mWallpaper.getOriginal())
// have a ProgressBar get updated automatically with the percent
                .progressBar(progressBar)


// and a ProgressDialog
// can also use a custom callback
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        progress = ((downloaded * 100) / total);
                        downloadCount.setText(progress + "%");
                    }
                })
                .write(new File(PARENT + image + imageName + ".png"))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        // download done...
                        // do stuff with the File or error
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isNotification = true;
//        startActivity(new Intent(this,));
    }

    @Override
    protected void onStart() {
        super.onStart();
        cancelNotification();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cancelNotification();
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
        cancelNotification();
    }


    public void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.meditation_applogo_4);
        Intent intent = new Intent(this, ProgressBarDownload.class);
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


    public void cancelNotification() {

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(1);
    }

}






