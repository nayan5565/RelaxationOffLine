package com.swapnotech.relaxation.meditation.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.activity.DeepZoomSample;
import com.swapnotech.relaxation.meditation.activity.MainActivity;
import com.swapnotech.relaxation.meditation.activity.ProgressBarDownload;
import com.swapnotech.relaxation.meditation.activity.SingleImageActivity;
import com.swapnotech.relaxation.meditation.adapter.AdWallpaper;
import com.swapnotech.relaxation.meditation.model.MWallpaper;
import com.swapnotech.relaxation.meditation.model.MWallpaperCat;
import com.swapnotech.relaxation.meditation.model.Mdownload;
import com.swapnotech.relaxation.meditation.tools.DBManager;
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.MyApp;
import com.swapnotech.relaxation.meditation.tools.MyLog;
import com.swapnotech.relaxation.meditation.tools.Utils;

import java.util.ArrayList;

/**
 * Created by Dev on 11/22/2017.
 */

public class FragWallpaper extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private AdWallpaper adWallpaper;
    private ArrayList<MWallpaper> mWallpapers;
    public ArrayList<Mdownload> mdownloadArrayList;
    private String download;
    public static int showTargetView;
    private static final String TAG = "FrarWallpaper";

    public static FragWallpaper newInstance() {
//
        return new FragWallpaper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_wallpaper, container, false);
        MyApp.getInstance().setupAnalytics("FragWallpaper Screen");
        MyLog.e("Frag", " FragWallpaper");


        init();
//        if (showTargetView == 1) {
//            if (download.equals("0"))
//                tergetView();
//        }

//        getWallpaperFromDB();
        prepareDisplay();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            download = Utils.getPref("download", "0");
//            if (download.equals("0"))
//                tergetView();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mdownloadArrayList = DBManager.getInstance().getData(DBManager.TABLE_DOWNLOAD, new Mdownload());
        getWallpaperFromDB();
        prepareDisplay();
//        if (download.equals("0"))
//            tergetView();
    }

    private void generateLocalData() {
        MWallpaper mWallpaperCat = new MWallpaper();
        mWallpaperCat.setShare("75");
        mWallpaperCat.setView("7");
        mWallpaperCat.setImage(R.drawable.ganapathykumar95863);
        mWallpapers.add(mWallpaperCat);

        mWallpaperCat = new MWallpaper();
        mWallpaperCat.setShare("52");
        mWallpaperCat.setView("9");
        mWallpaperCat.setImage(R.drawable.garybendig218145);
        mWallpapers.add(mWallpaperCat);

        mWallpaperCat = new MWallpaper();
        mWallpaperCat.setShare("55");
        mWallpaperCat.setView("17");
        mWallpaperCat.setImage(R.drawable.dimitrityan232294);
        mWallpapers.add(mWallpaperCat);

        mWallpaperCat = new MWallpaper();
        mWallpaperCat.setShare("50");
        mWallpaperCat.setView("71");
        mWallpaperCat.setImage(R.drawable.irinablok177640);
        mWallpapers.add(mWallpaperCat);

        mWallpaperCat = new MWallpaper();
        mWallpaperCat.setShare("25");
        mWallpaperCat.setView("34");
        mWallpaperCat.setImage(R.drawable.paulgilmore101372);
        mWallpapers.add(mWallpaperCat);

        mWallpaperCat = new MWallpaper();
        mWallpaperCat.setShare("15");
        mWallpaperCat.setView("7");
        mWallpaperCat.setImage(R.drawable.seabeachholidayvacation);
        mWallpapers.add(mWallpaperCat);
    }


    private void tergetView() {
        // We need the display to get the width and height at this point in time
        final Display display = getActivity().getWindowManager().getDefaultDisplay();
        // Load our little droid guy
        final Drawable droid = ContextCompat.getDrawable(getContext(), R.drawable.ic_android_black_24dp);
        // Tell our droid buddy where we want him to appear
        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 25, droid.getIntrinsicHeight() * 25);
        // Using deprecated methods makes you look way cool
        droidTarget.offset(display.getWidth() / 5, display.getHeight() / 5);
        TapTargetView.showFor(getActivity(), TapTarget.forBounds(droidTarget, "Image will be download if you click any image. If do not want image download so click hard back button")
                .cancelable(true)
                .drawShadow(true)
                .targetRadius(20)
                .tintTarget(false), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);

                Utils.savePref("download", "1");
                // .. which evidently starts the sequence we defined earlier
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetViewSample", "You dismissed me :(");
                Utils.savePref("download", "1");
            }
        });
    }

    private void init() {
        mWallpapers = new ArrayList<>();
//        mdownloadArrayList = new ArrayList<>();
//        mdownloadArrayList = DBManager.getInstance().getData(DBManager.TABLE_DOWNLOAD, new Mdownload());
        recyclerView =  view.findViewById(R.id.recWallpaper);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adWallpaper = new AdWallpaper(getContext()) {
            @Override
            public void onClickItem(int position, View view) {
//                Global.orderLayoutDissmiss = 0;
//
//                SingleImageActivity.mWallpaper = mWallpapers.get(position);
//                ProgressBarDownload.mWallpaper = mWallpapers.get(position);
//                DeepZoomSample.mWallpaper = mWallpapers.get(position);
//                Global.singleWallpaperURL = mWallpapers.get(position).getThumbnail();
//                DeepZoomSample.downlaodedImage = mWallpapers.get(position).getThumbnail().substring(mWallpapers.get(position).getThumbnail().lastIndexOf("/") + 1);
//
//                MainActivity.showFrag = 1;
//                MainActivity.selectTabPos = 1;
//                MainActivity.getInstance().isNotification = true;
//                MyApp.getInstance().setupAnalyticsEvent("FragWallpaper", mWallpapers.get(position).getCategory_title());
//                if (mdownloadArrayList.size() > 0) {
//                    for (int i = 0; i < mdownloadArrayList.size(); i++) {
//                        MyLog.e(TAG + "present " + mWallpapers.get(position).getThumbnail(), " downloaded " + mdownloadArrayList.get(i).getDownloadFile());
//                        if (mWallpapers.get(position).getThumbnail().equals(mdownloadArrayList.get(i).getDownloadFile())) {
//                            DeepZoomSample.addShow++;
//                            if (DeepZoomSample.addShow == 6)
//                                DeepZoomSample.addShow = 1;
//                            MyLog.e("downloadList", " already downloaded ");
////                        MyLog.e("present " + mWallpapers.get(position).getThumbnail(), " downloaded " + mdownloadArrayList.get(i).getDownloadFile());
////                        Toast.makeText(getContext(), TAG + " already downloaded", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getContext(), DeepZoomSample.class));
//
//                            return;
//                        } else {
////                        Toast.makeText(getContext(), TAG + " start Download", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getContext(), ProgressBarDownload.class));
//
//                        }
//                    }
//                } else {
//                    startActivity(new Intent(getContext(), ProgressBarDownload.class));
//
//                }


            }
        };
        recyclerView.setAdapter(adWallpaper);

    }

    private void prepareDisplay() {
        MyLog.e("Frag", " showTargetView " + showTargetView);
//        if (showTargetView == 1) {
//            if (download.equals("0"))
//                tergetView();
//        }
        generateLocalData();

        if (mWallpapers != null && mWallpapers.size() > 0)
            adWallpaper.setData(mWallpapers);
//        if (mdownloadArrayList != null && mdownloadArrayList.size() > 0)
//            adWallpaper.setDownloadedData(mdownloadArrayList);
    }


    private void getWallpaperFromDB() {

        mWallpapers = DBManager.getInstance().getData(DBManager.TABLE_WALLPAPER, new MWallpaper());
        Log.e("Wallpaper__________DB", " Size " + mWallpapers.size());
    }


}
