package com.swapnotech.relaxation.meditation.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.InMobAdManager;
import com.swapnotech.relaxation.meditation.tools.MyApp;
import com.swapnotech.relaxation.meditation.tools.MyLog;
import com.swapnotech.relaxation.meditation.tools.PlayerControl;
import com.swapnotech.relaxation.meditation.tools.Utils;

import java.util.concurrent.TimeUnit;

public class MusicNewActivity extends AppCompatActivity implements View.OnClickListener {

    BandwidthMeter bandwidthMeter;
    TrackSelection.Factory videoTrackSelectionFactory;
    TrackSelector trackSelector;
    AudioManager audioManager;
    //    public static MItems mItems;
//    public static String soundPos;
    private Toolbar toolbar;
    private TextView tvToolbarTitle, tvPlay5, tvPlay4, tvPlay3, tvPlay2, tvPlay, tvSetting, duration;
    private SeekBar seekBar1, seekBar2, seekBar3, seekBar4, seekBar5, seekBarSound;
    public static SimpleExoPlayer player, player1, player2, player3, player4, player6, player5;
    public static PlayerControl playerControl, playerControl1, playerControl2, playerControl3, playerControl4, playerControl6, playerControl5;
    private Button playPause, btnFullScreen;
    private ImageView imgPlay5, imgPlay4, imgPlay3, imgPlay2, imgPlay, imgSetting, imgClose;
    private int cnt = 0, songCount = 0, songCount2 = 0, songCount3 = 0, songCount4 = 0, songCount5 = 0, clickBtnBack = 0, songDestination, songDestination2, songDestination3, songDestination4, songDestination5, remaining, condision;
    private boolean isSetting = false;
    private ImageView imgBG;
    public static int stopAllsong;
    Handler handler;
    private int DELAY = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_new);

        init();
//        MyApp.getInstance().setupAnalytics("Music Screen");
//        Utils.setFont(tvSetting, tvToolbarTitle);
////        changeBGImage();
//  setImageIntoImageViewFromOnline();
        visibilityGoneOfSeekbar();
        soundServiceStop();
        sound();
        seekBar1();
        seekBar2();
        seekBar3();
        seekBar4();
        seekBar5();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        MyLog.e("soundServiceStop", " onReStart");
        if (Global.isExit == 1) {
            logic();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.e("soundServiceStop", " onResume");
        if (Global.isExit == 1) {
            logic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLog.e("soundServiceStop", " onDestroy");
        logic();
    }

    private void logic() {
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
        AdView adView = findViewById(R.id.adView);
        InMobAdManager.getInstance(this).loadAd(adView);
        handler = new Handler();
        imgBG = findViewById(R.id.imgBG);
        toolbar = findViewById(R.id.toolbar);
        tvToolbarTitle = toolbar.findViewById(R.id.tvToolbarTitle);


        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        duration = findViewById(R.id.duration);
        tvSetting = findViewById(R.id.tvSetting);
        playPause = findViewById(R.id.btnPlay);
        btnFullScreen = findViewById(R.id.btnFullScreen);
        seekBar1 = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
        seekBar4 = findViewById(R.id.seekBar4);
        seekBarSound = findViewById(R.id.seekBarSound);
        seekBar5 = findViewById(R.id.seekBar5);
        imgPlay4 = findViewById(R.id.imgPlay4);
        imgPlay5 = findViewById(R.id.imgPlay5);
        imgPlay3 = findViewById(R.id.imgPlay3);
        imgPlay2 = findViewById(R.id.imgPlay2);
        imgPlay = findViewById(R.id.imgPlay);
        tvPlay = findViewById(R.id.tvPlay);
//        GradientDrawable drawable = (GradientDrawable) tvPlay.getBackground();
        tvPlay2 = findViewById(R.id.tvPlay2);
//        GradientDrawable drawable2 = (GradientDrawable) tvPlay2.getBackground();
        tvPlay3 = findViewById(R.id.tvPlay3);
//        GradientDrawable drawable3 = (GradientDrawable) tvPlay3.getBackground();
        tvPlay4 = findViewById(R.id.tvPlay4);
//        GradientDrawable drawable4 = (GradientDrawable) tvPlay4.getBackground();
        tvPlay5 = findViewById(R.id.tvPlay5);
//        GradientDrawable drawable5 = (GradientDrawable) tvPlay5.getBackground();
        imgSetting = findViewById(R.id.imgSetting);
        imgClose = findViewById(R.id.imgClose);
        imgPlay.setOnClickListener(this);
        imgPlay2.setOnClickListener(this);
        imgPlay3.setOnClickListener(this);
        imgPlay4.setOnClickListener(this);
        imgPlay5.setOnClickListener(this);
        imgSetting.setOnClickListener(this);
        playPause.setOnClickListener(this);
        btnFullScreen.setOnClickListener(this);
        imgClose.setOnClickListener(this);
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
    }

    private void changeBGImage() {
//        if (showOnce == 0) {
        imgBG.setBackgroundResource(R.drawable.pexelsphoto87240);
//        showOnce++;
//        changeBGImage();
//        } else {
//            handler.postDelayed(runnable, 15000);
//        }
    }

    private void setImageIntoImageViewFromOnline() {
        imgPlay.setBackgroundResource(R.drawable.placeholder_image);

        imgPlay2.setBackgroundResource(R.drawable.placeholder_image);

        imgPlay3.setBackgroundResource(R.drawable.placeholder_image);

        imgPlay4.setBackgroundResource(R.drawable.placeholder_image);

        imgPlay5.setBackgroundResource(R.drawable.placeholder_image);


    }

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
//                        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_call);
                    playSongwithExoPlayerFirstSong("asset:///fire.mp3");
//                        if (!Utils.isInternetOn()) {
//                            Utils.log("Internet", "No Internet");
//                            String fileName = mItems.getSounds().get(0).getFile().substring(mItems.getSounds().get(0).getFile().lastIndexOf("/") + 1);
//                            playSongwithExoPlayerFirstSong(sounds + File.separator + WallpaperrItemScreen.category.getCategoryId() + "" + File.separator + soundPos + File.separator + fileName);
//                        } else {
//                            playSongwithExoPlayerFirstSong(mItems.getSounds().get(0).getFile());
//                        }

                    songCount++;

                    repeatSong();

                } else {
                    playerControl1.pause();
                    songCount = 0;
                    return;
                }
            }
        }, 6000);
    }

    private void repeatSong2() {

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
                Log.e("Repeat " + songDestination, "___________________repeat2 start" + songCount2);
                if (songCount2 < songDestination2 - 1) {
                    if (playerControl2.isPlaying())
                        playerControl2.pause();

                    playSongwithExoPlayerSecondSong("asset:///dolphin_chatter.mp3");
                    songCount2++;

                    repeatSong2();

                } else {
//                    seekBarRepeat2.setVisibility(View.GONE);
//                    txtDuration2.setVisibility(View.GONE);
                    playerControl2.pause();
                    songCount2 = 0;
                    return;
                }
            }
        }, 6000);
    }

    private void repeatSong3() {

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
                Log.e("Repeat " + songDestination, "___________________repeat3 start" + songCount3);
                if (songCount3 < songDestination3 - 1) {
                    if (playerControl3.isPlaying())
                        playerControl3.pause();
                    playSongwithExoPlayerThirdSong("asset:///crow_cawing.mp3");

                    songCount3++;

                    repeatSong3();

                } else {
//                    seekBarRepeat2.setVisibility(View.GONE);
//                    txtDuration2.setVisibility(View.GONE);
                    playerControl3.pause();
                    songCount3 = 0;
                    return;
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
                    playSongwithExoPlayerFourthSong("asset:///mountain_river_flowing.mp3");
                    songCount4++;

                    repeatSong4();

                } else {
//                    seekBarRepeat2.setVisibility(View.GONE);
//                    txtDuration2.setVisibility(View.GONE);
                    playerControl4.pause();
                    songCount4 = 0;
                    return;
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
                    playSongwithExoPlayerFifthSong("asset:///fire_near_the_river.mp3");
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
                    playSongwithExoPlayPause("asset:///fire_near_the_river.mp3");

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
            playSongwithExoPlayerFirstSong("asset:///fire.mp3");
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
            stopAllsong = 0;
//            Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_sparrow);
            playSongwithExoPlayerSecondSong("asset:///dolphin_chatter.mp3");
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
            playSongwithExoPlayerThirdSong("asset:///crow_cawing.mp3");
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
            playSongwithExoPlayerFourthSong("asset:///mountain_river_flowing.mp3");
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

    private void btnPlay() {
//        Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bird_sparrow_call);
//        MyApp.getInstance().setupAnalyticsEvent("Play", mItems.getMainSound());
        if (cnt == 0) {
            playSongwithExoPlayPause("asset:///cave_waterdrop.mp3");

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
