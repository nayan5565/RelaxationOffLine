package com.swapnotech.relaxation.meditation.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.adapter.AdWallpaperItem;
import com.swapnotech.relaxation.meditation.model.MItems;
import com.swapnotech.relaxation.meditation.model.MSound;
import com.swapnotech.relaxation.meditation.model.MWallpaper2;
import com.swapnotech.relaxation.meditation.model.MWallpaperCat;
import com.swapnotech.relaxation.meditation.tools.DBManager;
import com.swapnotech.relaxation.meditation.tools.EndlessScrollListener;
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.MyLog;
import com.swapnotech.relaxation.meditation.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Dev on 11/27/2017.
 */

public class WallpaperrItemScreen extends AppCompatActivity implements View.OnClickListener {
    public static MWallpaperCat category;
    private static ArrayList<Integer> footerIcons;
    public TabLayout footerTab;
    private Context context;
    private RecyclerView recyclerView;
    public static ArrayList<MItems> recipes2;
    private AdWallpaperItem adapterRecipe;
    private int dataSize = 0;
    //    private RequestQueue requestQueue;
    private Gson gson;
    private Toolbar toolbar;
    TextView mTitle;
    private boolean isNotification = false;
    List<MSound> sounds;
    List<MWallpaper2> wallpaper2s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_wallpaper_item);
//        MyApp.getInstance().setupAnalytics("Wallpaper Screen");
        init();
        MainActivity.getInstance().cancelNotification();
        Log.e("Items__________", "method " + category.getCategoryId() + "");
        setupFooter();
////        getItemsFromOnline();
        getLocalData();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
////        getLocalData();
//        prepareDisplay();
//        MyApp.getInstance().setupAnalytics("Recipe List");
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            isNotification = true;
            MainActivity.showFrag = 0;
            MainActivity.selectTabPos = 0;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        context = this;
        isNotification = false;
        toolbar = findViewById(R.id.toolbar);
        mTitle =  toolbar.findViewById(R.id.toolbar_title);
        Utils.setFont(mTitle);

        footerTab = findViewById(R.id.footerTab);


//        imgBack.setOnClickListener(this);


        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addOnScrollListener(new EndlessScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                MyLog.e("LOad", "more : " + current_page);
//                getItemsFromOnline();

            }
        });

//        Global.products = new ArrayList<>();
        recipes2 = new ArrayList<>();
        gson = new Gson();

        adapterRecipe = new AdWallpaperItem(context) {
            @Override
            public void onClickItem(int position, View view) {
//                MItems recipe = tmpRecipies.get(position);
//                    if (recipe.getIsNew() == 0) {
//                        recipe.setIsNew(1);
//                        DBManager.getInstance().addRecipeData(recipe, false, DBManager.TABLE_RECEIPE);
//                    }
//                Global.productId = Global.products.get(position).getId();
//                MyApp.getInstance().setupAnalyticsEvent("Item", recipes2.get(position).getCategoryTitle());
                MainActivity.stopSong = 1;
                Global.isExit = 1;
                MyLog.e("soundServiceStop", " goingMusic " + Global.isExit);
//                MusicNewActivity.mItems = recipes2.get(position);
//                MusicNewActivity.soundPos = position + "";
//                Toast.makeText(context, position + " so " + MusicActivity.soundPos, Toast.LENGTH_SHORT).show();
                isNotification = true;
                startActivity(new Intent(context, MusicNewActivity.class));
                finish();

            }
        };
        recyclerView.setAdapter(adapterRecipe);


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getLocalData();
//        if (recipes2.size() > 0 && recipes2 != null) {
//            MyLog.e("Receipe", " getDbManager " + recipes2.size());
//            adapterRecipe.setData(recipes2);
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void getItemsFromOnline() {
        Log.e("Items__________", "method " + category.getCategoryId() + "");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("cat", category.getCategoryId() + "");
        params.put("startat", dataSize + "");
        client.get(Global.API_MUSIC, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("Items__________", " response " + response.toString());
                try {

                    recipes2 = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("items").toString(), MItems[].class)));
                    saveRecipiList();
                    getLocalData();
                    Log.e("Items__________", "size" + recipes2.size());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Items__________", "failer");
            }
        });

    }

    private void saveRecipiList() {
        if (recipes2 != null && recipes2.size() > 0) {
            for (MItems mRecipe : recipes2) {
                DBManager.getInstance().addItemsData(mRecipe, DBManager.TABLE_ITEMS);
            }
        }

    }

    private void generateLocalData() {
        String imageUri = "drawable://" + R.drawable.pexelsphoto417191;
        Uri path = Uri.parse("android.resource://com.segf4ult.test/" + R.drawable.pexelsphoto417191);
        sounds = new ArrayList<>();
        wallpaper2s = new ArrayList<>();
        MItems mWallpaperCat = new MItems();
        mWallpaperCat.setCategoryTitle("bd");
        mWallpaperCat.setImage(R.drawable.astronomyconstellationcosmos433155);
        mWallpaperCat.setThumb(path.toString());
        mWallpaperCat.setMainSound("asset:///fire_near_the_river.mp3");
//        mWallpaperCat.setSound(R.raw.bird_call);

        MWallpaper2 mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        //sound add

        MSound mSound =new MSound();
//        mSound.setSound(R.raw.bats_flying_in_cave);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.bird_call);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.bird_sparrow);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.birds_flock_fly_away);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        recipes2.add(mWallpaperCat);

        sounds = new ArrayList<>();
        wallpaper2s = new ArrayList<>();
        mWallpaperCat = new MItems();
        mWallpaperCat.setCategoryTitle("bd2");
        mWallpaperCat.setImage(R.drawable.actionbicyclebike763398);
        mWallpaperCat.setMainSound("asset:///fire_near_the_river.mp3");
//        mWallpaperCat.setSound(R.raw.bats_flying_in_cave);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        //add sound

        mSound =new MSound();
//        mSound.setSound(R.raw.bats_flying_in_cave);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.buffalo_growl);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.campfire);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        recipes2.add(mWallpaperCat);

        sounds = new ArrayList<>();
        wallpaper2s = new ArrayList<>();
        mWallpaperCat = new MItems();
        mWallpaperCat.setCategoryTitle("bd3");
        mWallpaperCat.setImage(R.drawable.bokehdropsofwaterrain8486);
        mWallpaperCat.setMainSound("asset:///fire_near_the_river.mp3");
//        mWallpaperCat.setSound(R.raw.bird_sparrow);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        //add sound

        mSound =new MSound();
//        mSound.setSound(R.raw.cave_by_max_milliner_preview);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.birds_flock_fly_away);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.bird_sparrow);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        recipes2.add(mWallpaperCat);

        sounds = new ArrayList<>();
        wallpaper2s = new ArrayList<>();
        mWallpaperCat = new MItems();
        mWallpaperCat.setCategoryTitle("bd4");
        mWallpaperCat.setImage(R.drawable.breathtakingcalmenvironment35884);
        mWallpaperCat.setMainSound("asset:///fire_near_the_river.mp3");
//        mWallpaperCat.setSound(R.raw.buffalo_growl);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        //add sound

        mSound =new MSound();
//        mSound.setSound(R.raw.bird_sparrow);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.bird_call);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.birds_singing_in_the_forest);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        recipes2.add(mWallpaperCat);

        sounds = new ArrayList<>();
        wallpaper2s = new ArrayList<>();
        mWallpaperCat = new MItems();
        mWallpaperCat.setCategoryTitle("bd5");
        mWallpaperCat.setImage(R.drawable.pexelsphoto4145623);
        mWallpaperCat.setMainSound("asset:///fire_near_the_river.mp3");
//        mWallpaperCat.setSound(R.raw.birds_flock_fly_away);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        mWallpaper2 = new MWallpaper2();
        mWallpaper2.setImage(R.drawable.pexelsphoto4145623);
        wallpaper2s.add(mWallpaper2);
        mWallpaperCat.setWallpaper(wallpaper2s);

        //add sound

        mSound =new MSound();
//        mSound.setSound(R.raw.car_passing_on_countryside_road);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.birds_singing_in_the_forest);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        mSound =new MSound();
//        mSound.setSound(R.raw.buffalo_growl);
        sounds.add(mSound);
        mWallpaperCat.setSounds(sounds);

        recipes2.add(mWallpaperCat);

    }

    private void getLocalData() {


//        recipes2 = DBManager.getInstance().getItemsData(category.getCategoryId());
        generateLocalData();

        MyLog.e("Receipe", " getDbHelper " + recipes2.size());
//        MyLog.e("Receipe", " getDbManager " + Global.products.size());
//        tmpRecipies = recipes2;
//        videoSize = DBManager.getInstance().getData(DBManager.TABLE_VIDEO, new MVideo()).size();
//        MyApp.getInstance().setRecipes(Global.products);
        dataSize = recipes2.size();
//        dataSize = Global.products.size();

        if (recipes2.size() <= 0) {
            return;
        } else {
            mTitle.setText(category.getCategoryTitle());
            adapterRecipe.setData(recipes2);
        }
//        if (Global.products.size() <= 0) {
//            return;
//        } else {
//            adapterRecipe.setData(Global.products);
//        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.imgBack:
//                startActivity(new Intent(WallpaperrItemScreen.this, MainActivity.class));
//                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isNotification = true;
        MainActivity.showFrag = 0;
        MainActivity.selectTabPos = 0;
        MainActivity.getInstance().isNotification = false;
        Intent intent = new Intent(WallpaperrItemScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
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

    public void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.meditation_applogo_4);
        Intent intent = new Intent(this, WallpaperrItemScreen.class);
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

    private void setupFooter() {
        footerTab.setSelectedTabIndicatorHeight(0);


        footerTab.addTab(footerTab.newTab().setText(Global.TAB_CATEGORY));
        footerTab.addTab(footerTab.newTab().setText("Wallpaper"));
        footerTab.addTab(footerTab.newTab().setText(Global.TAB_RECIPE));
        footerTab.setTabTextColors(Color.WHITE, Color.WHITE);
        for (int i = 0; i < footerTab.getTabCount(); i++) {
            footerTab.getTabAt(i).setIcon(footerIcon(i, false));
        }
        footerTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MainActivity.selectTabPos = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:
                        MainActivity.showFrag = 0;
                        MainActivity.selectTabPos = 0;
                        isNotification = true;
                        startActivity(new Intent(WallpaperrItemScreen.this, MainActivity.class));
                        finish();
                        break;
                    case 1:
                        MainActivity.showFrag = 1;
                        MainActivity.selectTabPos = 1;
                        isNotification = true;
                        startActivity(new Intent(WallpaperrItemScreen.this, MainActivity.class));
                        finish();
                        break;
                    case 2:
                        MainActivity.showFrag = 2;
                        MainActivity.selectTabPos = 2;
                        isNotification = true;
                        startActivity(new Intent(WallpaperrItemScreen.this, MainActivity.class));
                        finish();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    MainActivity.selectTabPos = tab.getPosition();
                    startActivity(new Intent(WallpaperrItemScreen.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private int footerIcon(int id, boolean isActive) {
        if (footerIcons == null) {
            footerIcons = new ArrayList<>();
            footerIcons.add(R.drawable.product_icon_white);
            footerIcons.add(R.drawable.delivery_icon_white);
            footerIcons.add(R.drawable.my_account_icon_white);

            footerIcons.add(R.drawable.product_icon_black);
            footerIcons.add(R.drawable.delivery_icon_black);
            footerIcons.add(R.drawable.my_account_icon_black);
        }

        return isActive ? footerIcons.get(id + 3) : footerIcons.get(id);

    }


}
