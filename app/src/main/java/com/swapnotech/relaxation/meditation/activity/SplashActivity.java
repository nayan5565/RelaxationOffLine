package com.swapnotech.relaxation.meditation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.model.MCategory;
import com.swapnotech.relaxation.meditation.model.MCategoryList;
import com.swapnotech.relaxation.meditation.model.MItems;
import com.swapnotech.relaxation.meditation.model.MWallpaper;
import com.swapnotech.relaxation.meditation.model.MWallpaperCat;
import com.swapnotech.relaxation.meditation.model.MWallpaperCatList;
import com.swapnotech.relaxation.meditation.tools.DBManager;
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.Utils;
import com.swapnotech.relaxation.meditation.tools.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Jewel on 7/13/2016.
 */
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private RequestQueue requestQueue;
    private ArrayList<MWallpaperCat> mWallpaperCatLists;
    private ArrayList<MCategory> categories;
    private MCategoryList categoryList;
    private MWallpaperCatList mWallpaperCatList;
    private Gson gson;
    private ProgressBar progressBar;
    TextView tvTitle, tvTitle3;
    private ArrayList<MItems> recipes2;
    private ArrayList<MWallpaper> mWallpapers;


    public static void start(Context context) {
        context.startActivity(new Intent(context, SplashActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_splash);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
//        init();


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void init() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle3 = (TextView) findViewById(R.id.tvTitle3);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        gson = new Gson();
        requestQueue = VolleySingleton.getInstance().getRequestQueue();

//        if (DBManager.getInstance().getData(DBManager.TABLE_RELAX, new MWallpaperCat()).size() <= 0) {
        getWallpaperFromOnline();
//            callForCategory();
//            getHomeListFromOnline();
//        } else
//            callForSync();


        if (!Utils.isInternetOn()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void getWallpaperFromOnline() {
        if (!Utils.isInternetOn()) return;
//        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Global.API_WALLPAPERS, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("Wallpaper__________res", response.toString());
                try {
                    mWallpapers = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("post").toString(), MWallpaper[].class)));
                    DBManager.getInstance().addAllData(DBManager.TABLE_WALLPAPER, mWallpapers, "Id");
                    getItemsFromOnline();
//                    getWallpaperFromDB();
//                    callForCategory();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("Wallpaper__________res", " Size " + mWallpapers.size());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
//                progressBar.setVisibility(View.GONE);
                Log.e("Wallpaper_____________", responseString);
            }
        });
    }

    private void getItemsFromOnline() {
//        Log.e("Items__________", "method " + category.getCategoryId() + "");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
//        params.put("cat", category.getCategoryId() + "");
//        params.put("startat", dataSize + "");
        client.get(Global.API_MUSIC, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("Items__________", " response " + response.toString());
                try {

                    recipes2 = new ArrayList<>(Arrays.asList(gson.fromJson(response.getJSONArray("items").toString(), MItems[].class)));
                    saveRecipiList();
                    getOnlineRelaxData();
//                    getLocalData();
//                    getWallpaperFromOnline();

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

    private void getOnlineRelaxData() {
        if (!Utils.isInternetOn()) return;
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Global.API_RELAX, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressBar.setVisibility(View.GONE);
                Log.e("Relax ", response.toString());
                mWallpaperCatList = gson.fromJson(response.toString(), MWallpaperCatList.class);
                mWallpaperCatLists = mWallpaperCatList.getCategories();
                Log.e("Relax", " size " + mWallpaperCatLists.size());
                DBManager.getInstance().addAllData(DBManager.TABLE_RELAX, mWallpaperCatLists, "categoryId");
//                callForCategory();
//                getItemsFromOnline();

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressBar.setVisibility(View.GONE);
                Log.e("NEW-ERR", responseString);
            }
        });
    }

    private void callForSync() {
        if (!Utils.isInternetOn()) return;
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
//        params.put("send", "01-01-2015");
        params.put("send", Utils.getPref(Global.SYNC, "01-01-2015"));
        client.post(Global.BASE + Global.API_SYNC, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("Sync", response.toString());
                progressBar.setVisibility(View.GONE);
//                sync(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressBar.setVisibility(View.GONE);
                Log.e("NEW-ERR", responseString);
            }
        });
    }


//    private void sync(String response) {
//        MSync sync = gson.fromJson(response, MSync.class);
//        ArrayList<MCategory> categories = sync.getCategory();
//        ArrayList<MRecipe> recipes = sync.getRecipes();
//        if (categories != null && categories.size() > 0) {
//            DBManager.getInstance().addAllData(DBManager.TABLE_CATEGORY, categories, "categoryId");
//            for (MCategory category : categories) {
//                if (category.getCategoryDelete() == 1)
//                    DBManager.getInstance().deleteData(DBManager.TABLE_CATEGORY, "categoryId", category.getCategoryId() + "");
//            }
//        }
//        if (recipes != null && recipes.size() > 0) {
//            DBManager.getInstance().addAllData(DBManager.TABLE_RECEIPE, recipes, "Id");
//            for (MRecipe recipe : recipes) {
//                if (recipe.getRecipeDelete() == 1)
//                    DBManager.getInstance().deleteData(DBManager.TABLE_RECEIPE, "Id", recipe.getId() + "");
//            }
//        }
//
//
//        Utils.savePref(Global.SYNC, Utils.getToday());
//        startActivity(new Intent(SplashActivity.this, MainActivity.class));
////        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//        finish();
//    }


}
