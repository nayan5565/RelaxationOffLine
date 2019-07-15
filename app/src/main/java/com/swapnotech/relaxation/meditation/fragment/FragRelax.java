package com.swapnotech.relaxation.meditation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.activity.MainActivity;
import com.swapnotech.relaxation.meditation.activity.WallpaperrItemScreen;
import com.swapnotech.relaxation.meditation.adapter.AdRelax;
import com.swapnotech.relaxation.meditation.model.MWallpaperCat;
import com.swapnotech.relaxation.meditation.tools.DBManager;
import com.swapnotech.relaxation.meditation.tools.MyApp;
import com.swapnotech.relaxation.meditation.tools.MyLog;

import java.util.ArrayList;
import java.util.List;

//import com.android.volley.RequestQueue;
//import com.swapnotech.relaxation.meditation.model.MVideo;

/**
 * Created by Dev on 11/27/2017.
 */

public class FragRelax extends Fragment {
    public static FragRelax instance;
    private View viewMain;
    private Context context;
    private RecyclerView recyclerView, recSubCat;
    private List<MWallpaperCat> categories;
    public static int image;
    private AdRelax adCategoryList;
    //    private RequestQueue requestQueue;
    private int videoNo;
    private Gson gson = new Gson();

    public static FragRelax newInstance() {
//
        return new FragRelax();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.frag_category_list, container, false);
        MyApp.getInstance().setupAnalytics("FragRelax Screen");
        MyLog.e("Frag", " FragRelax");
        init();
        prepareDisplay();
        return viewMain;
    }

    private void init() {

        context = getContext();
        recyclerView = (RecyclerView) viewMain.findViewById(R.id.recyclerView);
        recSubCat = (RecyclerView) viewMain.findViewById(R.id.recSubCat);
        recSubCat.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categories = new ArrayList<>();

        adCategoryList = new AdRelax(context) {
            @Override
            public void onClickItem(int itemPositon, View view) {
                if (categories != null && categories.size() > 0) {
                    WallpaperrItemScreen.category = categories.get(itemPositon);
                    MainActivity.showFrag = 0;
                    MainActivity.selectTabPos = 0;
                    MainActivity.getInstance().isNotification = true;
//                    MyApp.getInstance().setupAnalyticsEvent("Relax",categories.get(itemPositon).getCategoryTitle() );
                    startActivity(new Intent(getContext(), WallpaperrItemScreen.class));
//

                }
            }
        };
        recyclerView.setAdapter(adCategoryList);
    }

    private void generateLocalData() {
        MWallpaperCat mWallpaperCat = new MWallpaperCat();
        mWallpaperCat.setCategoryTitle("bd");
        mWallpaperCat.setImage(R.drawable.pexelsphoto417191);
        categories.add(mWallpaperCat);

        mWallpaperCat = new MWallpaperCat();
        mWallpaperCat.setCategoryTitle("bd2");
        mWallpaperCat.setImage(R.drawable.pexelsphoto4145623);
        categories.add(mWallpaperCat);

        mWallpaperCat = new MWallpaperCat();
        mWallpaperCat.setCategoryTitle("bd3");
        mWallpaperCat.setImage(R.drawable.pexelsphoto4145622);
        categories.add(mWallpaperCat);

        mWallpaperCat = new MWallpaperCat();
        mWallpaperCat.setCategoryTitle("bd4");
        mWallpaperCat.setImage(R.drawable.pexelsphoto87240);
        categories.add(mWallpaperCat);

        mWallpaperCat = new MWallpaperCat();
        mWallpaperCat.setCategoryTitle("bd5");
        mWallpaperCat.setImage(R.drawable.pexelsphoto235242);
        categories.add(mWallpaperCat);
    }

    private void getLocalData() {
        generateLocalData();
//        categories = DBManager.getInstance().getData(DBManager.TABLE_RELAX, new MWallpaperCat());
        Log.e("Relax____________", " catSize " + categories.size());
//        videoNo = DBManager.getInstance().getData(DBManager.TABLE_VIDEO, new MVideo()).size();
        if (categories != null && categories.size() > 0)
            adCategoryList.addData(categories);
    }


    private void prepareDisplay() {
        getLocalData();
//        MainActivity.getInstance().setVideoNo(videoNo);
    }

//    public void setData(String q) {
//        ArrayList<MWallpaperCat> c = new ArrayList<>();
//        for (MWallpaperCat ca : categories) {
//            try {
//                if (ca.getCategoryTitle().startsWith(q)) {
//                    c.add(ca);
//                }
//            } catch (Exception e) {
//                MyLog.e("ERR", e.toString());
//            }
//        }
//        adCategoryList.addData(c);
//    }
}
