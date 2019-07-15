package com.swapnotech.relaxation.meditation.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdView;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.model.MItems;
import com.swapnotech.relaxation.meditation.model.MSoundDownload;
import com.swapnotech.relaxation.meditation.tools.AdsManager;
import com.swapnotech.relaxation.meditation.tools.DBManager;
import com.swapnotech.relaxation.meditation.tools.FilesDownload;
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.InMobAdManager;
import com.swapnotech.relaxation.meditation.tools.MyApp;
import com.swapnotech.relaxation.meditation.tools.MyLog;
import com.swapnotech.relaxation.meditation.tools.PlayerControl;
import com.swapnotech.relaxation.meditation.tools.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dev on 11/27/2017.
 */

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    BandwidthMeter bandwidthMeter;
    TrackSelection.Factory videoTrackSelectionFactory;
    TrackSelector trackSelector;
    AudioManager audioManager;
    public static MItems mItems;
    public static String soundPos;
    private Toolbar toolbar;
    private TextView tvToolbarTitle, tvPlay5, tvPlay4, tvPlay3, tvPlay2, tvPlay, tvSetting, duration;
    private SeekBar seekBar1, seekBar2, seekBar3, seekBar4, seekBar5, seekBarSound;
    public static SimpleExoPlayer player, player1, player2, player3, player4, player6, player5;
    public static PlayerControl playerControl, playerControl1, playerControl2, playerControl3, playerControl4, playerControl6, playerControl5;
    private Button playPause, btnFullScreen, playPause3;
    private ImageView imgPlay5, imgPlay4, imgPlay3, imgPlay2, imgPlay, imgSetting, imgClose;
    private int cnt = 0, cnt2 = 0, songCount = 0, songCount2 = 0, songCount3 = 0, songCount4 = 0, songCount5 = 0, clickBtnBack = 0, songDestination, songDestination2, songDestination3, songDestination4, songDestination5, remaining, condision;
    private boolean isPlay = false, isSetting = false;
    private RelativeLayout relBackground;
    private ImageView imgBG;
    private int pos = 0, showOnce = 0;
    ImageView img;
    String image;
    public static int stopAllsong;
    Handler handler;
    private int DELAY = 15000;
    private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
    private boolean isNotification = false;
    private String targetView;
    private String targetViewFullScreen;
    private ArrayList<MSoundDownload> mSoundDownloads;
    private MSoundDownload mSoundDownload;

    public static File externalFilesDir = MyApp.getInstance().getContext().getExternalFilesDir(null);
    public static final String PARENT = externalFilesDir.getAbsolutePath() + File.separator + "Meditation";
    public static String sounds = PARENT + File.separator + "allSounds";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_music);

        init();

//        soundList();
//        getSoundList();
//        mainSoundDownload();
//        soundDownload();
        MyApp.getInstance().setupAnalytics("Music Screen");
//        handler.postDelayed(runnable, 15);
        Utils.setFont(tvSetting, tvToolbarTitle);
        changeBGImage();
//        setImageIntoImageViewFromOnline();
        visibilityGoneOfSeekbar();
//        test();
        soundServiceStop();
        cancelNotification();

        targetViewFullScreen = Utils.getPref("targetViewFull", "0");

        if (targetViewFullScreen.equals("0"))
            targetViewForFullScreen(R.id.btnFullScreen, "please click here to going to full Screen otherwise click any where");
//        playSongwithExoBackGround("asset:///background2.mp3");
        sound();
        seekBar1();
        seekBar2();
        seekBar3();
        seekBar4();
        seekBar5();
//        playPause2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //To decrease media player volume
////                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
//
////
//                if (cnt2 == 0) {
////                    if (playerControl2.isPlaying()) {
//////                        playerControl.pause();
//////                        player.release();
////                        playerControl2.pause();
//////                        player2.release();
////                    }
//                    playSongwithExoPlayerSecondSong("asset:///sound1.mp3");
//                    if (songDestination2 > 0)
//                        repeatSong2();
//                    cnt2++;
//                    playPause.setText("play");
////                    seekBarRepeat2.setVisibility(View.VISIBLE);
////                    seekBarRepeat.setVisibility(View.GONE);
////                    txtDuration2.setVisibility(View.GONE);
////                    txtDuration3.setVisibility(View.VISIBLE);
//                    condision = 1;
////                    txtDuration.setText(playerControl2.getDuration() + "");
//                } else {
//
//                    if (playerControl3.isPlaying()) {
//                        playerControl3.pause();
//                        playPause.setText("Play");
//                        Log.e("STAG", "Pasuse Song");
//
//                    } else {
////                        if (playerControl2.isPlaying())
////                            playerControl2.pause();
//                        if (songDestination2 > 0)
//                            repeatSong2();
//                        playerControl3.start();
//                        playPause.setText("play");
////                        seekBarRepeat.setVisibility(View.GONE);
////                        seekBarRepeat2.setVisibility(View.VISIBLE);
////                        txtDuration2.setVisibility(View.GONE);
////                        txtDuration3.setVisibility(View.VISIBLE);
//                        condision = 1;
//                        Log.e("STAG", "Play Song");
//                    }
//                }
//            }
//        });
//        playPause3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //To increase media player volume
//                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
//
//            }
//        });


    }


    private void fullAddShow() {
        AdsManager.getInstance(this).showInterstisial();

    }

    private void targetView(int view, String string) {
        TapTargetView.showFor(this, TapTarget.forView(findViewById(view), string)
                .cancelable(true)
                .drawShadow(true)
                .targetRadius(20)
                .tintTarget(true), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                clickBackOrCloseButton();
                clickBtnBack = 0;
                Utils.savePref("targetViewM", "1");
                // .. which evidently starts the sequence we defined earlier
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
//                Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetViewSample", "You dismissed me :(");
                Utils.savePref("targetViewM", "1");
            }
        });
    }

    private void targetViewForFullScreen(int view, String string) {
        TapTargetView.showFor(this, TapTarget.forView(findViewById(view), string)
                .cancelable(true)
                .drawShadow(true), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                clickBackOrCloseButton();
                clickBtnBack = 0;
                Utils.savePref("targetViewFull", "1");
                // .. which evidently starts the sequence we defined earlier
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
                Utils.savePref("targetViewFull", "1");
//                Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetViewSample", "You dismissed me :(");
                Utils.savePref("targetViewFull", "1");
//                Toast.makeText(view.getContext(), "You dismissed me", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyLog.e("soundServiceStop", " onStart");
//        songDestination = 0;
//        songCount = 0;
//        songDestination2 = 0;
//        songCount2 = 0;
//        songDestination3 = 0;
//        songCount3 = 0;
//        songDestination4 = 0;
//        songCount4 = 0;
//        songDestination5 = 0;
//        songCount5 = 0;
//        Global.isExit = 1;
//        soundServiceStop();
        cancelNotification();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cancelNotification();
        MyLog.e("soundServiceStop", " onReStart");
        if (Global.isExit == 1) {
            songDestination = 0;
            songCount = 0;
            songDestination2 = 0;
            songCount2 = 0;
            songDestination3 = 0;
            songCount3 = 0;
            songDestination4 = 0;
            songCount4 = 0;
            songDestination5 = 0;
            songCount5 = 0;
            Global.isExit = 1;
            soundServiceStop();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        cancelNotification();
        MyLog.e("soundServiceStop", " onResume");
        if (Global.isExit == 1) {
            songDestination = 0;
            songCount = 0;
            songDestination2 = 0;
            songCount2 = 0;
            songDestination3 = 0;
            songCount3 = 0;
            songDestination4 = 0;
            songCount4 = 0;
            songDestination5 = 0;
            songCount5 = 0;
            Global.isExit = 1;
            soundServiceStop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyLog.e("soundServiceStop", " onPause");
        if (!isNotification)
            sendNotification();
    }


    @Override
    protected void onStop() {
        super.onStop();

        MyLog.e("soundServiceStop", " onStop");
        Global.isExit = 0;
        MyLog.e("soundServiceStop", " onStop" + Global.isExit);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLog.e("soundServiceStop", " onDestroy");
        cancelNotification();
        songDestination = 0;
        songCount = 0;
        songDestination2 = 0;
        songCount2 = 0;
        songDestination3 = 0;
        songCount3 = 0;
        songDestination4 = 0;
        songCount4 = 0;
        songDestination5 = 0;
        songCount5 = 0;
        Global.isExit = 1;
        soundServiceStop();
    }

    public void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.meditation_applogo_4);
        Intent intent = new Intent(this, MusicActivity.class);
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

    private void seekBar5() {
        seekBar5.setMax(10);
        seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                txtDuration2.setText("Repeat " + i + " times");
                MyLog.e("seekbar_______5", " is " + i);
                songDestination5 = i;
                tvPlay5.setVisibility(View.VISIBLE);

                tvPlay.setVisibility(View.INVISIBLE);
                tvPlay2.setVisibility(View.INVISIBLE);
                tvPlay3.setVisibility(View.INVISIBLE);
                tvPlay4.setVisibility(View.INVISIBLE);
                tvPlay5.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (songDestination5 == 0) {
                    if (playerControl5.isPlaying())
                        playerControl5.pause();
                    return;
                }
                repeatSong5();


            }
        });
    }


    private void seekBar4() {
        seekBar4.setMax(10);
        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                txtDuration2.setText("Repeat " + i + " times");
                MyLog.e("seekbar_______4", " is " + i);
                songDestination4 = i;
                tvPlay4.setVisibility(View.VISIBLE);

                tvPlay.setVisibility(View.INVISIBLE);
                tvPlay2.setVisibility(View.INVISIBLE);
                tvPlay3.setVisibility(View.INVISIBLE);
                tvPlay5.setVisibility(View.INVISIBLE);
                tvPlay4.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (songDestination4 == 0) {
                    if (playerControl4.isPlaying())
                        playerControl4.pause();
                    return;
                }
                repeatSong4();


            }
        });
    }

    private void seekBar3() {
        seekBar3.setMax(10);
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                txtDuration2.setText("Repeat " + i + " times");
                MyLog.e("seekbar_______3", " is " + i);
                songDestination3 = i;
                tvPlay3.setVisibility(View.VISIBLE);
                tvPlay.setVisibility(View.INVISIBLE);
                tvPlay2.setVisibility(View.INVISIBLE);
                tvPlay4.setVisibility(View.INVISIBLE);
                tvPlay5.setVisibility(View.INVISIBLE);
                tvPlay3.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (songDestination3 == 0) {
                    if (playerControl3.isPlaying())
                        playerControl3.pause();
                    return;
                }
                repeatSong3();


            }
        });
    }

    private void seekBar2() {
        seekBar2.setMax(10);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                txtDuration2.setText("Repeat " + i + " times");
                MyLog.e("seekbar_______2", " is " + i);
                songDestination2 = i;
                tvPlay2.setVisibility(View.VISIBLE);
                tvPlay.setVisibility(View.INVISIBLE);
                tvPlay3.setVisibility(View.INVISIBLE);
                tvPlay4.setVisibility(View.INVISIBLE);
                tvPlay5.setVisibility(View.INVISIBLE);
                tvPlay2.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (songDestination2 == 0) {
                    if (playerControl2.isPlaying())
                        playerControl2.pause();
                    return;
                }
                repeatSong2();


            }
        });
    }


    private void seekBar1() {
        seekBar1.setMax(10);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                txtDuration2.setText("Repeat " + i + " times");
                MyLog.e("seekbar_______1", " is " + i);
                songDestination = i;
                seekBar1.setProgress(songDestination);
                tvPlay.setVisibility(View.VISIBLE);
                tvPlay2.setVisibility(View.INVISIBLE);
                tvPlay3.setVisibility(View.INVISIBLE);
                tvPlay4.setVisibility(View.INVISIBLE);
                tvPlay5.setVisibility(View.INVISIBLE);
                tvPlay.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (songDestination == 0) {
                    if (playerControl1.isPlaying())
                        playerControl1.pause();
                    return;
                }
                repeatSong();


            }
        });
    }

    private void sound() {
        seekBarSound.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBarSound.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));

        seekBarSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int progressValue, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progressValue, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                        textView.setText(progress + "/" + seekBar.getMax());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Display the value in textview
//                        textView.setText(progress + "/" + seekBar.getMax());
            }
        });
    }

    private void test() {
        String file = "desert_wind.mp33";

        try {

            AssetFileDescriptor afd = getAssets().openFd(file);

            AssetManager manager = getAssets();
            Log.e("STAG AS", "___________________" + manager.toString());

            Log.e("STAG ASSET", "_____________" + afd);
            Log.e("STAG ASSET DIS", "_____________" + afd.getFileDescriptor());
            Log.e("STAG ASSET OFF", "_____________" + afd.getStartOffset());
            Log.e("STAG ASSET LNG", "_____________" + afd.getLength());

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("STAG", "_____________-- Error");
        }


        String filename = "android.resource://" + this.getPackageName() + "/raw/song";

        Log.e("STAG File", "_______________" + filename);
        Uri uri = Uri.parse(filename);

        Log.e("STAG", "_____________" + uri);
    }

    private void visibilityGoneOfSeekbar() {
        seekBar1.setVisibility(View.INVISIBLE);
        seekBar2.setVisibility(View.INVISIBLE);
        seekBar3.setVisibility(View.INVISIBLE);
        seekBar4.setVisibility(View.INVISIBLE);
        seekBar5.setVisibility(View.INVISIBLE);

        tvPlay.setVisibility(View.INVISIBLE);
        tvPlay2.setVisibility(View.INVISIBLE);
        tvPlay3.setVisibility(View.INVISIBLE);
        tvPlay4.setVisibility(View.INVISIBLE);
        tvPlay5.setVisibility(View.INVISIBLE);
    }

    private void visibilityGoneOfOthersView() {
        playPause.setVisibility(View.INVISIBLE);
        btnFullScreen.setVisibility(View.INVISIBLE);
        imgPlay.setVisibility(View.INVISIBLE);
        imgPlay2.setVisibility(View.INVISIBLE);
        imgPlay3.setVisibility(View.INVISIBLE);
        imgPlay4.setVisibility(View.INVISIBLE);
        imgPlay5.setVisibility(View.INVISIBLE);
        tvSetting.setVisibility(View.INVISIBLE);
        imgSetting.setVisibility(View.GONE);
        imgClose.setVisibility(View.VISIBLE);
        tvToolbarTitle.setVisibility(View.GONE);
    }

    private void clickBackOrCloseButton() {

        playPause.setVisibility(View.VISIBLE);
        btnFullScreen.setVisibility(View.VISIBLE);
        imgPlay.setVisibility(View.VISIBLE);
        imgPlay2.setVisibility(View.VISIBLE);
        imgPlay3.setVisibility(View.VISIBLE);
        imgPlay4.setVisibility(View.VISIBLE);
        imgPlay5.setVisibility(View.VISIBLE);
        tvSetting.setVisibility(View.VISIBLE);
        imgSetting.setVisibility(View.VISIBLE);
        imgClose.setVisibility(View.GONE);
        tvToolbarTitle.setVisibility(View.VISIBLE);
    }

    private void init() {
        mSoundDownloads = new ArrayList<>();
        AdView adView = (AdView) findViewById(R.id.adView);
        InMobAdManager.getInstance(this).loadAd(adView);
        isNotification = false;
        handler = new Handler();
        imgBG = (ImageView) findViewById(R.id.imgBG);
        relBackground = (RelativeLayout) findViewById(R.id.relBackground);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarTitle = (TextView) toolbar.findViewById(R.id.tvToolbarTitle);


        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        duration = (TextView) findViewById(R.id.duration);
        tvSetting = (TextView) findViewById(R.id.tvSetting);
        playPause = (Button) findViewById(R.id.btnPlay);
        btnFullScreen = (Button) findViewById(R.id.btnFullScreen);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
        seekBarSound = (SeekBar) findViewById(R.id.seekBarSound);
        seekBar5 = (SeekBar) findViewById(R.id.seekBar5);
        imgPlay4 = (ImageView) findViewById(R.id.imgPlay4);
        imgPlay5 = (ImageView) findViewById(R.id.imgPlay5);
        imgPlay3 = (ImageView) findViewById(R.id.imgPlay3);
        imgPlay2 = (ImageView) findViewById(R.id.imgPlay2);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        tvPlay = (TextView) findViewById(R.id.tvPlay);
        GradientDrawable drawable = (GradientDrawable) tvPlay.getBackground();
        tvPlay2 = (TextView) findViewById(R.id.tvPlay2);
        GradientDrawable drawable2 = (GradientDrawable) tvPlay2.getBackground();
        tvPlay3 = (TextView) findViewById(R.id.tvPlay3);
        GradientDrawable drawable3 = (GradientDrawable) tvPlay3.getBackground();
        tvPlay4 = (TextView) findViewById(R.id.tvPlay4);
        GradientDrawable drawable4 = (GradientDrawable) tvPlay4.getBackground();
        tvPlay5 = (TextView) findViewById(R.id.tvPlay5);
        GradientDrawable drawable5 = (GradientDrawable) tvPlay5.getBackground();
        imgSetting = (ImageView) findViewById(R.id.imgSetting);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgPlay.setOnClickListener(this);
        imgPlay2.setOnClickListener(this);
        imgPlay3.setOnClickListener(this);
        imgPlay4.setOnClickListener(this);
        imgPlay5.setOnClickListener(this);
        imgSetting.setOnClickListener(this);
        playPause.setOnClickListener(this);
        btnFullScreen.setOnClickListener(this);
        imgClose.setOnClickListener(this);


//        GradientDrawable drawable = (GradientDrawable) playPause.getBackground();
//        drawable.setColor(Color.parseColor("#28292E"));
//        GradientDrawable drawable2 = (GradientDrawable) playPause2.getBackground();
//        drawable2.setColor(Color.parseColor("#A18158"));
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        bandwidthMeter = new DefaultBandwidthMeter();
        videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        playerControl = new PlayerControl(player);
        player1 = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        playerControl1 = new PlayerControl(player1);

        player2 = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        playerControl2 = new PlayerControl(player2);

        player3 = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        playerControl3 = new PlayerControl(player3);

        player4 = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        playerControl4 = new PlayerControl(player4);
        player5 = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        playerControl5 = new PlayerControl(player5);
        player6 = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        playerControl6 = new PlayerControl(player6);


//        image = getPREF(KEY_IMAGE);
//        MyLog.e("trueFalse", " is " + image);
//        if (image.equals(1 + "")) {
//            isPlay = true;
//            playPause.setBackgroundResource(R.drawable.play_round_button);
//        } else if (image.equals(0 + "")) {
//            isPlay = false;
//            playPause.setBackgroundResource(R.drawable.play_button);
//        }

//        if (playerControl6.isPlaying())
//            playPause.setBackgroundResource(R.drawable.play_round_button);
//        else
//            playPause.setBackgroundResource(R.drawable.play_button);
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        image = getPREF(KEY_IMAGE);
//        if (image.equals(1 + "")) {
//            isPlay = true;
//            playPause.setBackgroundResource(R.drawable.play_round_button);
//        } else if (image.equals(0 + "")) {
//            isPlay = false;
//            playPause.setBackgroundResource(R.drawable.play_button);
//        }
//    }

    private void changeBGImage() {

        MyLog.e("call ", " bgImage");
//        for (int i = 0; i < mItems.getWallpaper().size(); i++) {
//
//        }
        if (showOnce == 0) {
            imgBG.setBackgroundResource(mItems.getImage());

//            Ion.with(imgBG)
//                    .placeholder(R.drawable.placeholder_image)
//                    .error(R.drawable.placeholder_image)
//                    .load(mItems.getThumb());
            Log.e("ChangeImage", " is first " + mItems.getThumb());
            showOnce++;
            changeBGImage();
        } else {
            handler.postDelayed(runnable, 15000);
        }

//        else {
//
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (mItems != null || mItems.getWallpaper().size() > 0) {
//
//                        if (mItems.getWallpaper().size() - 1 < pos) {
//                            pos = 0;
//                            changeBGImage();
//                        } else {
//                            Log.e("ItemsImage__________", " is  " + mItems.getWallpaper().get(pos).getFile());
//                            if (mItems.getWallpaper().get(pos).getFile() != null && !mItems.getWallpaper().get(pos).getFile().equals("") && !mItems.getWallpaper().get(pos).getFile().equals("null")) {
////                                Ion.with(imgBG)
////                                        .load(mItems.getWallpaper().get(pos).getFile())
////                                        .setCallback(new FutureCallback<ImageView>() {
////                                            @Override
////                                            public void onCompleted(Exception e, ImageView imageView) {
////                                                showOnce++;
////                                                pos++;
////                                                changeBGImage();
////                                            }
////                                        });
//
//                                Picasso.with(MusicActivity.this)
//                                        .load(mItems.getWallpaper().get(pos).getFile())
//                                        .placeholder(R.drawable.placeholder_image)
//                                        .skipMemoryCache()
//                                        .into(imgBG);
//                                showOnce++;
//                                pos++;
//                                changeBGImage();
////                                                , new Callback() {
////                                            @Override
////                                            public void onSuccess() {
////                                                showOnce++;
////                                                pos++;
////                                                changeBGImage();
////                                            }
////
////                                            @Override
////                                            public void onError() {
////
////                                            }
////                                        });
//                            } else {
//                                imgBG.setBackgroundResource(R.drawable.placeholder_image);
//                            }
//
//
//                        }
//                    }
//
//                }
//            }, 15000);
//
//        }
    }

    public void changeImage() {
        if (pos < mItems.getWallpaper().size()) {
            Runnable task = new Runnable() {
                public void run() {
                    Picasso.with(MusicActivity.this)
                            .load(mItems.getWallpaper().get(pos).getFile())
                            .skipMemoryCache()
                            .into(imgBG);
                }
            };
            worker.schedule(task, 5 * pos++, TimeUnit.SECONDS);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mItems != null && mItems.getWallpaper().size() > 0) {
                if (pos < mItems.getWallpaper().size()) {
                    Log.e("ChangeImage", " is " + pos + " " + mItems.getWallpaper().get(pos).getFile());
                    Ion.with(imgBG)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.placeholder_image)
                            .load(mItems.getWallpaper().get(pos).getFile());

//                Picasso.with(MusicActivity.this)
//                        .load(mItems.getWallpaper().get(pos).getFile())
//                        .skipMemoryCache()
//                        .placeholder(R.drawable.placeholder_image)
//                        .into(imgBG);
                    handler.postDelayed(runnable, DELAY);
                    pos++;
//                                , new com.squareup.picasso.Callback() {
//                            @Override
//                            public void onSuccess() {
//                                handler.postDelayed(runnable, DELAY);
//                                pos++;
//                            }
//
//                            @Override
//                            public void onError() {
//
//                            }
//                        });


                } else {
                    handler.postDelayed(runnable, DELAY);
                    pos = 0;
//                handler.removeCallbacks(runnable);
                }
            }
        }
    };

    private void setImageIntoImageViewFromOnline() {
        if (mItems != null && mItems.getSounds().size() > 4) {
            tvToolbarTitle.setText(mItems.getTitle());
            if (mItems.getSounds().get(0).getFile() != null && !mItems.getSounds().get(0).getFile().equals("") && !mItems.getSounds().get(0).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(0).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay);

            } else
                imgPlay.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(1).getFile() != null && !mItems.getSounds().get(1).getFile().equals("") && !mItems.getSounds().get(1).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(1).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay2);


            } else
                imgPlay2.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(2).getFile() != null && !mItems.getSounds().get(2).getFile().equals("") && !mItems.getSounds().get(2).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(2).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay3);
            } else
                imgPlay3.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(3).getFile() != null && !mItems.getSounds().get(3).getFile().equals("") && !mItems.getSounds().get(3).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(3).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay4);
            } else
                imgPlay4.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(4).getFile() != null && !mItems.getSounds().get(4).getFile().equals("") && !mItems.getSounds().get(4).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(4).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay5);
            } else
                imgPlay5.setBackgroundResource(R.drawable.placeholder_image);
        }
        if (mItems != null && mItems.getSounds().size() == 4) {
            tvToolbarTitle.setText(mItems.getTitle());
            if (mItems.getSounds().get(0).getFile() != null && !mItems.getSounds().get(0).getFile().equals("") && !mItems.getSounds().get(0).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(0).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay);

            } else
                imgPlay.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(1).getFile() != null && !mItems.getSounds().get(1).getFile().equals("") && !mItems.getSounds().get(1).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(1).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay2);


            } else
                imgPlay2.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(2).getFile() != null && !mItems.getSounds().get(2).getFile().equals("") && !mItems.getSounds().get(2).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(2).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay3);
            } else
                imgPlay3.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(3).getFile() != null && !mItems.getSounds().get(3).getFile().equals("") && !mItems.getSounds().get(3).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(3).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay4);
            } else
                imgPlay4.setBackgroundResource(R.drawable.placeholder_image);

        }
        if (mItems != null && mItems.getSounds().size() == 3) {
            tvToolbarTitle.setText(mItems.getTitle());
            if (mItems.getSounds().get(0).getFile() != null && !mItems.getSounds().get(0).getFile().equals("") && !mItems.getSounds().get(0).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(0).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay);

            } else
                imgPlay.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(1).getFile() != null && !mItems.getSounds().get(1).getFile().equals("") && !mItems.getSounds().get(1).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(1).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay2);


            } else
                imgPlay2.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(2).getFile() != null && !mItems.getSounds().get(2).getFile().equals("") && !mItems.getSounds().get(2).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(2).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay3);
            } else
                imgPlay3.setBackgroundResource(R.drawable.placeholder_image);

        }
        if (mItems != null && mItems.getSounds().size() == 2) {
            tvToolbarTitle.setText(mItems.getTitle());
            if (mItems.getSounds().get(0).getFile() != null && !mItems.getSounds().get(0).getFile().equals("") && !mItems.getSounds().get(0).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(0).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay);

            } else
                imgPlay.setBackgroundResource(R.drawable.placeholder_image);

            if (mItems.getSounds().get(1).getFile() != null && !mItems.getSounds().get(1).getFile().equals("") && !mItems.getSounds().get(1).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(1).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay2);


            } else
                imgPlay2.setBackgroundResource(R.drawable.placeholder_image);

        }
        if (mItems != null && mItems.getSounds().size() == 1) {
            tvToolbarTitle.setText(mItems.getTitle());
            if (mItems.getSounds().get(0).getFile() != null && !mItems.getSounds().get(0).getFile().equals("") && !mItems.getSounds().get(0).getFile().equals("null")) {
                Picasso.with(this).load(mItems.getSounds().get(0).getIcon()).placeholder(R.drawable.placeholder_image).into(imgPlay);

            } else
                imgPlay.setBackgroundResource(R.drawable.placeholder_image);

        }

    }



    //seekbar up down when volume key up down

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            int index = seekBarSound.getProgress();
            seekBarSound.setProgress(index + 1);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            int index = seekBarSound.getProgress();
            seekBarSound.setProgress(index - 1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void repeatSong() {

        if (stopAllsong == 1 || songDestination == 0) {

            if (playerControl1.isPlaying())
                playerControl1.pause();
//            songCount = 0;
            return;


        }
        Log.e("Repeat ", "___________________call repeat song method");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (condision == 1)
//                    return;
                Log.e("Repeat " + songDestination, "___________________repeat start" + songCount);
                if (songCount < songDestination - 1) {
                    if (playerControl1.isPlaying())
                        playerControl1.pause();
                    if (mItems != null && mItems.getSounds().size() > 0) {
//                        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_call);
                        playSongwithExoPlayerFirstSong("asset:///cave_waterdrop.mp3");
//                        if (!Utils.isInternetOn()) {
//                            Utils.log("Internet", "No Internet");
//                            String fileName = mItems.getSounds().get(0).getFile().substring(mItems.getSounds().get(0).getFile().lastIndexOf("/") + 1);
//                            playSongwithExoPlayerFirstSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//                        } else {
//                            playSongwithExoPlayerFirstSong(mItems.getSounds().get(0).getFile());
//                        }
                    }

                    songCount++;

                    repeatSong();

                } else {
//                    seekBarRepeat.setVisibility(View.GONE);
//                    txtDuration2.setVisibility(View.GONE);
                    playerControl1.pause();
                    songCount = 0;
                    return;
//                    songCount = 0;
//                    Log.e("Repeat", "___________________repeat return" + songCount);
//                    repeatSong();
                }
            }
        }, 6000);
    }

    private void repeatSong2() {
//        remaining = songDestination--;
//        txtDuration2.setText("Repeat " + remaining + " times");

        if (stopAllsong == 1 || songDestination2 == 0) {

            if (playerControl2.isPlaying())
                playerControl2.pause();
//            songCount2 = 0;
            return;


        }
        Log.e("Repeat ", "___________________call repeat song method 2");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (condision == 0)
//                    return;
                Log.e("Repeat " + songDestination, "___________________repeat2 start" + songCount2);
                if (songCount2 < songDestination2 - 1) {
                    if (playerControl2.isPlaying())
                        playerControl2.pause();
                    if (mItems != null && mItems.getSounds().size() > 1) {
//                        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.birds_singing_in_the_forest);
//                        if (!Utils.isInternetOn()) {
//                            Utils.log("Internet", "No Internet");
//                            String fileName = mItems.getSounds().get(1).getFile().substring(mItems.getSounds().get(1).getFile().lastIndexOf("/") + 1);
//                            playSongwithExoPlayerSecondSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//                        } else {
//                            playSongwithExoPlayerSecondSong(mItems.getSounds().get(1).getFile());
//                        }

                        playSongwithExoPlayerSecondSong("asset:///crow_cawing.mp3");
                    }
                    songCount2++;

                    repeatSong2();

                } else {
//                    seekBarRepeat2.setVisibility(View.GONE);
//                    txtDuration2.setVisibility(View.GONE);
                    playerControl2.pause();
                    songCount2 = 0;
                    return;
//                    songCount = 0;
//                    Log.e("Repeat", "___________________repeat2 return" + songCount);
//                    repeatSong2();
                }
            }
        }, 6000);
    }

    private void repeatSong3() {
//        remaining = songDestination--;
//        txtDuration2.setText("Repeat " + remaining + " times");

        if (stopAllsong == 1 || songDestination3 == 0) {

            if (playerControl3.isPlaying())
                playerControl3.pause();
//            songCount3 = 0;
            return;


        }
        Log.e("Repeat ", "___________________call repeat song method 3");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (condision == 0)
//                    return;
                Log.e("Repeat " + songDestination, "___________________repeat3 start" + songCount3);
                if (songCount3 < songDestination3 - 1) {
                    if (playerControl3.isPlaying())
                        playerControl3.pause();
                    if (mItems != null && mItems.getSounds().size() > 2) {
//                        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.birds_flock_fly_away);
//                        if (!Utils.isInternetOn()) {
//                            Utils.log("Internet", "No Internet");
//                            String fileName = mItems.getSounds().get(2).getFile().substring(mItems.getSounds().get(2).getFile().lastIndexOf("/") + 1);
//                            playSongwithExoPlayerThirdSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//                        } else {
//                            playSongwithExoPlayerThirdSong(mItems.getSounds().get(2).getFile());
//                        }
                        playSongwithExoPlayerThirdSong("asset:///dolphin_chatter.mp3");

                    }
                    songCount3++;

                    repeatSong3();

                } else {
//                    seekBarRepeat2.setVisibility(View.GONE);
//                    txtDuration2.setVisibility(View.GONE);
                    playerControl3.pause();
                    songCount3 = 0;
                    return;
//                    songCount = 0;
//                    Log.e("Repeat", "___________________repeat2 return" + songCount);
//                    repeatSong2();
                }
            }
        }, 6000);
    }

    private void repeatSong4() {
//        remaining = songDestination--;
//        txtDuration2.setText("Repeat " + remaining + " times");
        Log.e("Repeat ", "___________________call repeat song method 4");
        if (stopAllsong == 1 || songDestination4 == 0) {

            if (playerControl4.isPlaying())
                playerControl4.pause();
//            songCount4 = 0;
            return;


        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (condision == 0)
//                    return;
                Log.e("Repeat " + songDestination, "___________________repeat4 start" + songCount4);
                if (songCount4 < songDestination4 - 1) {
                    if (playerControl4.isPlaying())
                        playerControl4.pause();
                    if (mItems != null && mItems.getSounds().size() > 3) {
//                        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bats_flying_in_cave);
//                        if (!Utils.isInternetOn()) {
//                            Utils.log("Internet", "No Internet");
//                            String fileName = mItems.getSounds().get(3).getFile().substring(mItems.getSounds().get(3).getFile().lastIndexOf("/") + 1);
//                            playSongwithExoPlayerFourthSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//                        } else {
//                            playSongwithExoPlayerFourthSong(mItems.getSounds().get(3).getFile());
//                        }
                        playSongwithExoPlayerFourthSong("asset:///fire.mp3");
                    }
                    songCount4++;

                    repeatSong4();

                } else {
//                    seekBarRepeat2.setVisibility(View.GONE);
//                    txtDuration2.setVisibility(View.GONE);
                    playerControl4.pause();
                    songCount4 = 0;
                    return;
//                    songCount = 0;
//                    Log.e("Repeat", "___________________repeat2 return" + songCount);
//                    repeatSong2();
                }
            }
        }, 6000);
    }

    private void repeatSong5() {
        remaining = songDestination--;
//        txtDuration2.setText("Repeat " + remaining + " times");

        if (stopAllsong == 1 || songDestination5 == 0) {

            if (playerControl5.isPlaying())
                playerControl5.pause();
//            songCount5 = 0;
            return;


        }
        Log.e("Repeat ", "___________________call repeat song method 5");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (condision == 0)
//                    return;
                Log.e("Repeat " + songDestination, "___________________repeat4 start" + songCount5);
                if (songCount5 < songDestination5 - 1) {
                    if (playerControl5.isPlaying())
                        playerControl5.pause();
                    if (mItems != null && mItems.getSounds().size() > 4) {
//                        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_call);
                        playSongwithExoPlayerFifthSong("asset:///fire_burning_wood.mp3");
//                        if (!Utils.isInternetOn()) {
//
//                            Utils.log("Internet", "No Internet");
//                            String fileName = mItems.getSounds().get(4).getFile().substring(mItems.getSounds().get(4).getFile().lastIndexOf("/") + 1);
//                            playSongwithExoPlayerFifthSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//                        } else {
//                            playSongwithExoPlayerFifthSong(mItems.getSounds().get(4).getFile());
//                        }
                    }

                    songCount5++;

                    repeatSong5();

                } else {
//                    seekBarRepeat2.setVisibility(View.GONE);
//                    txtDuration2.setVisibility(View.GONE);
                    playerControl5.pause();
                    songCount5 = 0;
                    return;
//                    songCount = 0;
//                    Log.e("Repeat", "___________________repeat2 return" + songCount);
//                    repeatSong2();
                }
            }
        }, 6000);
    }

    private void soundList() {
        if (mItems.getSounds().size() > 0) {
            for (int i = 0; i < mItems.getSounds().size(); i++) {
                mSoundDownload = new MSoundDownload();
                mSoundDownload.setSound(mItems.getSounds().get(i).getFile());

                DBManager.getInstance().addSoundDownload(mSoundDownload, DBManager.TABLE_SOUND_DOWNLOAD);
            }
        }
    }

    private void getSoundList() {
        mSoundDownloads = DBManager.getInstance().getData(DBManager.TABLE_SOUND_DOWNLOAD, new MSoundDownload());
        mSoundDownloads.size();
    }

    public void mainSoundDownload() {
        if (mItems != null) {
            if (!Utils.isInternetOn()) {
                Utils.log("Internet", "No Internet");
                return;
            }
            FilesDownload filesDownload = FilesDownload.getInstance(this, sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos);
            filesDownload.addUrl(mItems.getMainSound());
            FilesDownload.getInstance(MusicActivity.this, "").start();
        }
    }

    public void soundDownload() {

        if (!Utils.isInternetOn()) {
            Utils.log("Internet", "No Internet");
            return;
        }
        FilesDownload filesDownload = FilesDownload.getInstance(this, sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos);
        for (int i = 0; i < mItems.getSounds().size(); i++) {
//            if (!mSoundDownloads.get(i).isDownload()) {
//                mSoundDownload = new MSoundDownload();
//                mSoundDownload.setDownload(true);
//                DBManager.getInstance().addSoundDownload(mSoundDownload, DBManager.TABLE_SOUND_DOWNLOAD);
//                filesDownload.addUrl(mSoundDownloads.get(i).getSound());
//            }
            filesDownload.addUrl(mItems.getSounds().get(i).getFile());

        }
        FilesDownload.getInstance(MusicActivity.this, "").start();
    }

    public void playSongwithExoBackGround(String url) {

        Log.e("Repeat", "___________________background song");
        // DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);
        // ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri., dataSourceFactory, extractorsFactory, null, null);
        // ExtractorMediaSource source = new ExtractorMediaSource(Uri., dataSourceFactory, Mp3Extractor.FACTORY, null, null);

        player.prepare(mediaSource);
        if (player.getPlayWhenReady())


            playerControl.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playSongwithExoBackGround("asset:///fire_near_the_river.mp3");
//                txtDuration.setText(playerControl.getCurrentPosition() + "");
            }
        }, 80100);


    }

    //    private Runnable updateSeekBarTime = new Runnable() {
//        public void run() {
//
//            //get current position
//            timeElapsed = mediaPlayer.getCurrentPosition();
//
//            //set seekbar progress using time played
//            seekbar.setProgress((int) timeElapsed);
//
//            //set time remaining in minutes and seconds
//            timeRemaining = finalTime - timeElapsed;
//            txtDuration4.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
//
//            //holding time for 100 miliseconds
//            durationHandler.postDelayed(this, 100);
//        }
//    };
    private void convertTime() {
        long millis = 3600000;
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        //another
        String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public void playSongwithExoPlayPause(String url) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                fullAddShow();
            }
        }, 6000);
        Log.e("STAG", "playSongwithExoPlayPause method");
       /*
        if(playerControl.isPlaying())
        {
            Log.e("isLoading","____________________________"+"Isloading");
            player.stop();

        }
        */
       /* player = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        playerControl = new PlayerControl(player);*/

        //DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);
        player6.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                if (playbackState == SimpleExoPlayer.STATE_READY) {
                    duration.setText(String.format("%02d:%02d ", TimeUnit.MILLISECONDS.toMinutes((long) player6.getDuration()), TimeUnit.MILLISECONDS.toSeconds((long) player6.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) player6.getDuration()))));
                }
                if (playbackState == SimpleExoPlayer.STATE_ENDED) {
                    if (mItems != null){
//                        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_sparrow);
                        playSongwithExoPlayPause("asset:///fire_near_the_river.mp3");
                    }

                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });
        player6.prepare(mediaSource);
        playerControl6.start();
    }

    public void playSongwithExoPlayerFifthSong(String url) {

        Log.e("STAG", "playSongwithExoPlayerSecondSong method");

        // DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);
        player5.prepare(mediaSource);
        playerControl5.start();
    }

    public void playSongwithExoPlayerFirstSong(String url) {

        Log.e("STAG", "playSongwithExoPlayerSecondSong method");

        // DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);
        player1.prepare(mediaSource);
        playerControl1.start();
    }

    public void playSongwithExoPlayerSecondSong(String url) {

        Log.e("STAG", "playSongwithExoPlayerSecondSong method");

        // DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);
        player2.prepare(mediaSource);
        playerControl2.start();
    }

    public void playSongwithExoPlayerThirdSong(String url) {

        Log.e("STAG", "playSongwithExoPlayerSecondSong method");

        // DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);
        player3.prepare(mediaSource);
        playerControl3.start();
    }

    public void playSongwithExoPlayerFourthSong(String url) {

        Log.e("STAG", "playSongwithExoPlayerSecondSong method");

        // DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);
        player4.prepare(mediaSource);
        playerControl4.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgPlay) {

//            MyApp.getInstance().setupAnalyticsEvent("Play", mItems.getSounds().get(0).getFile());
            stopAllsong = 0;
//            if (!Utils.isInternetOn()) {
//                Utils.log("Internet", "No Internet");
//                String fileName = mItems.getSounds().get(0).getFile().substring(mItems.getSounds().get(0).getFile().lastIndexOf("/") + 1);
//                playSongwithExoPlayerFirstSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//            } else {
//                playSongwithExoPlayerFirstSong(mItems.getSounds().get(0).getFile());
//            }
//            Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_sparrow);
            playSongwithExoPlayerFirstSong("asset:///fire_near_the_river.mp3");
            seekBar1.setVisibility(View.VISIBLE);
            seekBar2.setVisibility(View.INVISIBLE);
            seekBar3.setVisibility(View.INVISIBLE);
            seekBar4.setVisibility(View.INVISIBLE);
            seekBar5.setVisibility(View.INVISIBLE);

            tvPlay2.setVisibility(View.INVISIBLE);
            tvPlay3.setVisibility(View.INVISIBLE);
            tvPlay4.setVisibility(View.INVISIBLE);
            tvPlay5.setVisibility(View.INVISIBLE);
        } else if (v.getId() == R.id.imgPlay2) {
//            MyApp.getInstance().setupAnalyticsEvent("Play", mItems.getSounds().get(1).getFile());
            stopAllsong = 0;
//            Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_sparrow);
            playSongwithExoPlayerSecondSong("asset:///fire_near_the_river.mp3");
//            if (!Utils.isInternetOn()) {
//                Utils.log("Internet", "No Internet");
//                String fileName = mItems.getSounds().get(1).getFile().substring(mItems.getSounds().get(1).getFile().lastIndexOf("/") + 1);
//                playSongwithExoPlayerSecondSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//            } else {
//                playSongwithExoPlayerSecondSong(mItems.getSounds().get(1).getFile());
//            }
            seekBar1.setVisibility(View.INVISIBLE);
            seekBar2.setVisibility(View.VISIBLE);
            seekBar3.setVisibility(View.INVISIBLE);
            seekBar4.setVisibility(View.INVISIBLE);
            seekBar5.setVisibility(View.INVISIBLE);

            tvPlay.setVisibility(View.INVISIBLE);
            tvPlay3.setVisibility(View.INVISIBLE);
            tvPlay4.setVisibility(View.INVISIBLE);
            tvPlay5.setVisibility(View.INVISIBLE);

        } else if (v.getId() == R.id.imgPlay3) {
//            MyApp.getInstance().setupAnalyticsEvent("Play", mItems.getSounds().get(2).getFile());
            stopAllsong = 0;
            seekBar1.setVisibility(View.INVISIBLE);
            seekBar2.setVisibility(View.INVISIBLE);
            seekBar3.setVisibility(View.VISIBLE);
            seekBar4.setVisibility(View.INVISIBLE);
            seekBar5.setVisibility(View.INVISIBLE);

            tvPlay2.setVisibility(View.INVISIBLE);
            tvPlay.setVisibility(View.INVISIBLE);
            tvPlay4.setVisibility(View.INVISIBLE);
            tvPlay5.setVisibility(View.INVISIBLE);
//            Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_sparrow);
            playSongwithExoPlayerThirdSong("asset:///fire_near_the_river.mp3");
//            if (!Utils.isInternetOn()) {
//                Utils.log("Internet", "No Internet");
//                String fileName = mItems.getSounds().get(2).getFile().substring(mItems.getSounds().get(2).getFile().lastIndexOf("/") + 1);
//                playSongwithExoPlayerThirdSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//            } else {
//                playSongwithExoPlayerThirdSong(mItems.getSounds().get(2).getFile());
//            }
        } else if (v.getId() == R.id.imgPlay4) {
//            MyApp.getInstance().setupAnalyticsEvent("Play", mItems.getSounds().get(3).getFile());
            stopAllsong = 0;
            seekBar1.setVisibility(View.INVISIBLE);
            seekBar2.setVisibility(View.INVISIBLE);
            seekBar3.setVisibility(View.INVISIBLE);
            seekBar4.setVisibility(View.VISIBLE);
            seekBar5.setVisibility(View.INVISIBLE);

            tvPlay2.setVisibility(View.INVISIBLE);
            tvPlay3.setVisibility(View.INVISIBLE);
            tvPlay.setVisibility(View.INVISIBLE);
            tvPlay5.setVisibility(View.INVISIBLE);
//            Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_sparrow);
            playSongwithExoPlayerFourthSong("asset:///fire_near_the_river.mp3");
//            if (!Utils.isInternetOn()) {
//                Utils.log("Internet", "No Internet");
//                String fileName = mItems.getSounds().get(3).getFile().substring(mItems.getSounds().get(3).getFile().lastIndexOf("/") + 1);
//                playSongwithExoPlayerFourthSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//            } else {
//                playSongwithExoPlayerFourthSong(mItems.getSounds().get(3).getFile());
//            }
//
        } else if (v.getId() == R.id.imgPlay5) {
//            MyApp.getInstance().setupAnalyticsEvent("Play", mItems.getSounds().get(4).getFile());
            stopAllsong = 0;
            seekBar1.setVisibility(View.INVISIBLE);
            seekBar2.setVisibility(View.INVISIBLE);
            seekBar3.setVisibility(View.INVISIBLE);
            seekBar4.setVisibility(View.INVISIBLE);
            seekBar5.setVisibility(View.VISIBLE);

            tvPlay2.setVisibility(View.INVISIBLE);
            tvPlay3.setVisibility(View.INVISIBLE);
            tvPlay4.setVisibility(View.INVISIBLE);
            tvPlay.setVisibility(View.INVISIBLE);
//            if (!Utils.isInternetOn()) {
//                Utils.log("Internet", "No Internet");
//                String fileName = mItems.getSounds().get(4).getFile().substring(mItems.getSounds().get(4).getFile().lastIndexOf("/") + 1);
//                playSongwithExoPlayerFifthSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//            } else {
//                playSongwithExoPlayerFifthSong(mItems.getSounds().get(4).getFile());
//            }
//            Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_sparrow);
            playSongwithExoPlayerFifthSong("asset:///fire_near_the_river.mp3");
        } else if (v.getId() == R.id.btnPlay) {
            {
                visibilityGoneOfSeekbar();
                btnPlay();
            }
        } else if (v.getId() == R.id.btnFullScreen) {
//            MyApp.getInstance().setupAnalyticsEvent("Click", "FullScreen");
            clickBtnBack++;
            visibilityGoneOfSeekbar();
            visibilityGoneOfOthersView();
            targetView = Utils.getPref("targetViewM", "0");
            if (targetView.equals("0"))
                targetView(R.id.imgClose, "please click cross or back button if want to back previous screen otherwise click out of circle ");

        } else if (v.getId() == R.id.imgSetting) {
            if (!isSetting) {
                isSetting = true;
            } else if (isSetting) {
                isSetting = false;
            }
            if (isSetting) {
//                MyApp.getInstance().setupAnalyticsEvent("Settings", "Setting Option Gone");
                callImageSettingGone();
            } else {
//                MyApp.getInstance().setupAnalyticsEvent("Settings", "Setting Option Visibility");
                callImageSettingShow();
            }

        } else if (v.getId() == R.id.imgClose) {

            clickBackOrCloseButton();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (clickBtnBack == 0) {
                    Global.isExit = 1;
                    soundServiceStop();
                    isNotification = true;
                    startActivity(new Intent(this, WallpaperrItemScreen.class));
                    finish();
                } else {

                    clickBackOrCloseButton();
                    clickBtnBack = 0;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callImageSettingGone() {
        imgPlay.setVisibility(View.INVISIBLE);
        imgPlay2.setVisibility(View.INVISIBLE);
        imgPlay3.setVisibility(View.INVISIBLE);
        imgPlay4.setVisibility(View.INVISIBLE);
        imgPlay5.setVisibility(View.INVISIBLE);
        tvSetting.setVisibility(View.INVISIBLE);
        visibilityGoneOfSeekbar();
    }

    private void callImageSettingShow() {
        imgPlay.setVisibility(View.VISIBLE);
        imgPlay2.setVisibility(View.VISIBLE);
        imgPlay3.setVisibility(View.VISIBLE);
        imgPlay4.setVisibility(View.VISIBLE);
        imgPlay5.setVisibility(View.VISIBLE);
        tvSetting.setVisibility(View.VISIBLE);
    }

    public static final String MYPREF = "mpref";
    public static final String KEY_IMAGE = "image";


    public static String getPREF(String key) {
        SharedPreferences preferences = MyApp.getInstance().getContext().getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        return preferences.getString(key, "null");
    }


    public static void savePref(String key, String value) {
        SharedPreferences preferences = MyApp.getInstance().getContext().getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        songDestination = 0;
        songCount = 0;
        songDestination2 = 0;
        songCount2 = 0;
        songDestination3 = 0;
        songCount3 = 0;
        songDestination4 = 0;
        songCount4 = 0;
        songDestination5 = 0;
        songCount5 = 0;
        Global.isExit = 1;
        soundServiceStop();
        isNotification = true;
        startActivity(new Intent(this, WallpaperrItemScreen.class));
        finish();

    }

    private void soundServiceStop() {
        MyLog.e("soundServiceStop", " soundServiceStop");
        if (playerControl != null && playerControl.isPlaying()) {
            {
                player.stop();
                player.release();
            }
        }
        if (playerControl1 != null && playerControl1.isPlaying()) {
            {
                player1.stop();
                player1.release();
            }
        }
        if (playerControl2 != null && playerControl2.isPlaying()) {
            {
                player2.stop();
                player2.release();
            }
        }
        if (playerControl3 != null && playerControl3.isPlaying()) {
            {
                player3.stop();
                player3.release();
            }
        }
        if (playerControl4 != null && playerControl4.isPlaying()) {
            {
                player4.stop();
                player4.release();
            }
        }
        if (playerControl5 != null && playerControl5.isPlaying()) {
            {
                player5.stop();
                player5.release();
            }
        }
        if (playerControl6 != null && playerControl6.isPlaying()) {
            {
                player6.stop();
                player6.release();
            }
        }
    }


    private void anotherLogicForBtnPlay() {
        if (image.equals(1 + "")) {
            savePref(KEY_IMAGE, 0 + "");

        } else if (image.equals(0 + "")) {

            savePref(KEY_IMAGE, 1 + "");

        }
        image = getPREF(KEY_IMAGE);
        if (image.equals(1 + "")) {
            playPause.setBackgroundResource(R.drawable.play_round_button);
            condision = 0;
            Log.e("STAG", "1");
            stopAllsong = 0;
//            playSongwithExoPlayPause(mItems.getMainSound());
            repeatSong();
            repeatSong2();
            repeatSong3();
            repeatSong4();
            repeatSong5();
        } else {
            Log.e("STAG", "0");
            playPause.setBackgroundResource(R.drawable.play_button);
            stopAllsong = 1;
            playerControl6.pause();
//            playerControl6.release();

            playerControl1.pause();
//            playerControl1.release();

            playerControl2.pause();
//            playerControl2.release();

            playerControl3.pause();
//            playerControl3.release();

            playerControl4.pause();
//            playerControl4.release();

            playerControl5.pause();
//            playerControl5.release();
        }
//        if (!isPlay) {
//            isPlay = true;
//            savePref(KEY_IMAGE, 1 + "");
//        } else if (isPlay) {
//            isPlay = false;
//            savePref(KEY_IMAGE, 0 + "");
//        }
//        if (isPlay) {
//            playPause.setBackgroundResource(R.drawable.play_round_button);
//            condision = 0;
//            Log.e("STAG", "Play Song");
//            stopAllsong = 0;
//            playSongwithExoPlayPause(mItems.getMainSound());
//            repeatSong();
//            repeatSong2();
//            repeatSong3();
//            repeatSong4();
//            repeatSong5();
//
//
//        } else {
//            playPause.setBackgroundResource(R.drawable.play_button);
//            stopAllsong = 1;
//            playerControl6.stop();
//            playerControl6.release();
//
//            playerControl1.stop();
//            playerControl1.release();
//
//            playerControl2.stop();
//            playerControl2.release();
//
//            playerControl3.stop();
//            playerControl3.release();
//
//            playerControl4.stop();
//            playerControl4.release();
//
//            playerControl5.stop();
//            playerControl5.release();
//
//
//            Log.e("STAG", "Pasuse Song");
//
//
//        }
    }

    private void btnPlay() {
//        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_sparrow_call);
//        MyApp.getInstance().setupAnalyticsEvent("Play", mItems.getMainSound());
        if (cnt == 0) {
            if (mItems != null) {
//                if (!Utils.isInternetOn()) {
//                    Utils.log("Internet", "No Internet");
//                    String fileName = mItems.getMainSound().substring(mItems.getMainSound().lastIndexOf("/") + 1);
//                    playSongwithExoPlayPause(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//                } else {
//                    playSongwithExoPlayPause(mItems.getMainSound());
//                }
//                playSongwithExoPlayPause("asset:///bird_sparrow_call.mp3");
                playSongwithExoPlayPause(mItems.getMainSound());
            }

            cnt++;

            playPause.setBackgroundResource(R.drawable.pause_round_button);
        } else {

            if (playerControl6.isPlaying()) {

                if (playerControl1.isPlaying())
                    playerControl1.pause();
                if (playerControl2.isPlaying())
                    playerControl2.pause();
                if (playerControl3.isPlaying())
                    playerControl3.pause();
                if (playerControl4.isPlaying())
                    playerControl4.pause();
                if (playerControl5.isPlaying())
                    playerControl5.pause();
                playerControl6.pause();

                stopAllsong = 1;
                playPause.setBackgroundResource(R.drawable.play_round_button);
//                playPause.setText("Play");
                Log.e("STAG", "Pasuse Song");

            } else {
                stopAllsong = 0;
                if (!playerControl1.isPlaying())
                    repeatSong();
                if (!playerControl2.isPlaying())
                    repeatSong2();
                if (!playerControl3.isPlaying())
                    repeatSong3();
                if (!playerControl4.isPlaying())
                    repeatSong4();
                if (!playerControl5.isPlaying())
                    repeatSong5();
                playerControl6.start();
                playPause.setBackgroundResource(R.drawable.pause_round_button);
                condision = 0;
                Log.e("STAG", "Play Song");
            }
        }
    }
}
