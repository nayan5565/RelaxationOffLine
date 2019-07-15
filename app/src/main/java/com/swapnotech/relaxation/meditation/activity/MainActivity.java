package com.swapnotech.relaxation.meditation.activity;

import android.app.LoaderManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.ads.MobileAds;
import com.swapnotech.relaxation.meditation.R;
import com.swapnotech.relaxation.meditation.adapter.AdFooterTab;
import com.swapnotech.relaxation.meditation.fragment.FragContact;
import com.swapnotech.relaxation.meditation.fragment.FragRelax;
import com.swapnotech.relaxation.meditation.fragment.FragWallpaper;
import com.swapnotech.relaxation.meditation.model.MTimeTracker;
import com.swapnotech.relaxation.meditation.model.Mdownload;
import com.swapnotech.relaxation.meditation.tools.CustomTypefaceSpan;
import com.swapnotech.relaxation.meditation.tools.DBManager;
import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.MyApp;
import com.swapnotech.relaxation.meditation.tools.Utils;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static int selectTabPos;
    private static ArrayList<Integer> footerIcons;
    private static MainActivity instance;
    public static TabLayout footerTab;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private AdFooterTab adapterFooter;
    private Toolbar toolbar;
    String state;
    private boolean doubleBackToExitPressedOnce;
    private MTimeTracker timeTracker;
    public static int showFrag;
    public static int stopSong = 0;
    public boolean isNotification = false;
    private LoginButton loginButton;
    private CallbackManager callbackManager;


    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(MyApp.getInstance().getContext());


        init();
//        loginWithFB();
//        Global.downloadImageList = DBManager.getInstance().getData(DBManager.TABLE_DOWNLOAD, new Mdownload());
        setupNavigation();
        setupFooter();
//        targetView();
//        MyApp.getInstance().setupAnalytics("Home Screen");

        timeTracker = new MTimeTracker();
        timeTracker.setLogin(Utils.getDateTime());

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


    }



    private void loginWithFB() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile=Profile.getCurrentProfile();
                Toast.makeText(MainActivity.this, "success" + profile.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "cancel" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "success" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (footerTab != null) {
            footerTab.getTabAt(selectTabPos).select();
            footerTab.getTabAt(selectTabPos).setIcon(footerIcon(selectTabPos, true));
        }
//
//        if (showFrag == 1) {
//            if (footerTab != null) {
//                footerTab.getTabAt(1).select();
//                footerTab.getTabAt(1).setIcon(footerIcon(1, true));
//            }
//
//        } else if (showFrag == 0) {
//            if (footerTab != null) {
//                footerTab.getTabAt(0).select();
//                footerTab.getTabAt(0).setIcon(footerIcon(0, true));
//            }
//        } else if (showFrag == 2) {
//            if (footerTab != null) {
//                footerTab.getTabAt(2).select();
//                footerTab.getTabAt(2).setIcon(footerIcon(2, true));
//            }
//            if (footerTab != null) {
//                footerTab.getTabAt(selectTabPos).select();
//                footerTab.getTabAt(selectTabPos).setIcon(footerIcon(selectTabPos, true));
//            }
//        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (footerTab.getSelectedTabPosition() == 0) {
            exitFromApp();

        } else {
            footerTab.getTabAt(0).select();
            footerTab.getTabAt(0).setIcon(footerIcon(0, true));
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.meditation_applogo_4);
        Intent intent = new Intent(this, MainActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        isNotification = false;
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
        timeTracker.setLogout(Utils.getDateTime());
    }

    private void init() {
        instance = this;
        isNotification = false;
        state = Utils.getPref("profile2", Global.STATE_TUT);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        View view = navigationView.inflateHeaderView(R.layout.drawer_header);
        TextView title = (TextView) view.findViewById(R.id.headerTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Utils.setFont(mTitle, title);
        toolbar.setBackgroundColor(Color.parseColor("#FD8C49"));
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        callbackManager = CallbackManager.Factory.create();


        footerTab = (TabLayout) findViewById(R.id.footerTab);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }


    private void exitFromApp() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        isNotification = true;
        doubleBackToExitPressedOnce = true;
        Utils.showToast(Global.MSG_EXIT);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                selectTabPos = 0;
                doubleBackToExitPressedOnce = false;
                isNotification = true;
                soundServiceStop();

            }
        }, 2000);
    }

    private void soundServiceStop() {
        MusicActivity.stopAllsong = 1;
        if (MusicActivity.playerControl != null && MusicActivity.playerControl.isPlaying()) {
            {
                MusicActivity.playerControl.stop();
                MusicActivity.playerControl.release();
            }
        }
        if (MusicActivity.playerControl1 != null && MusicActivity.playerControl1.isPlaying()) {
            {
                MusicActivity.playerControl1.stop();
                MusicActivity.playerControl1.release();
            }
        }
        if (MusicActivity.playerControl2 != null && MusicActivity.playerControl2.isPlaying()) {
            {
                MusicActivity.playerControl2.stop();
                MusicActivity.playerControl2.release();
            }
        }
        if (MusicActivity.playerControl3 != null && MusicActivity.playerControl3.isPlaying()) {
            {
                MusicActivity.playerControl3.stop();
                MusicActivity.playerControl3.release();
            }
        }
        if (MusicActivity.playerControl4 != null && MusicActivity.playerControl4.isPlaying()) {
            {
                MusicActivity.playerControl4.stop();
                MusicActivity.playerControl4.release();
            }
        }
        if (MusicActivity.playerControl5 != null && MusicActivity.playerControl5.isPlaying()) {
            {
                MusicActivity.playerControl5.stop();
                MusicActivity.playerControl5.release();
            }
        }
        if (MusicActivity.playerControl6 != null && MusicActivity.playerControl6.isPlaying()) {
            {
                MusicActivity.playerControl6.stop();
                MusicActivity.playerControl6.release();
            }
        }
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/BLACKJAR.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void setupNavigation() {


        navigationView = (NavigationView) findViewById(R.id.navigationView);
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
//            SubMenu subMenu = mi.getSubMenu();
//            if (subMenu!=null && subMenu.size() >0 ) {
//                for (int j=0; j <subMenu.size();j++) {
//                    MenuItem subMenuItem = subMenu.getItem(j);
//                    applyFontToMenuItem(subMenuItem);
//                }
//            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        state = Utils.getPref("profile2", Global.STATE_TUT);
        Menu menu = navigationView.getMenu();
        MenuItem target = menu.findItem(R.id.mnuLogout);
        target.getTitle();
        if (state.equals(Global.STATE_PROFILE2)) {
            target.setVisible(true);

        } else {
            target.setVisible(false);

        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.mnuMusic:
                        footerTab.getTabAt(0).select();
                        break;
                    case R.id.mnuWallpaper:
                        footerTab.getTabAt(1).select();
                        FragWallpaper.showTargetView = 1;
                        break;
                    case R.id.mnuAsk:
                        footerTab.getTabAt(2).select();

                        break;
                }
                return true;
            }
        });

    }


    private void setupFooter() {
        adapterFooter = new AdFooterTab(getSupportFragmentManager());
        adapterFooter.addFragment(FragRelax.newInstance(), Global.TAB_CATEGORY);
        adapterFooter.addFragment(FragWallpaper.newInstance(), "Wallpaper");
        adapterFooter.addFragment(FragContact.getInstance(), Global.TAB_RECIPE);
        viewPager.setAdapter(adapterFooter);
        footerTab.setupWithViewPager(viewPager);
        footerTab.setSelectedTabIndicatorHeight(0);
        footerTab.setTabTextColors(Color.WHITE, Color.parseColor(Global.COLOR_MAIN));


        for (int i = 0; i < footerTab.getTabCount(); i++) {
            footerTab.getTabAt(i).setIcon(footerIcon(i, false));
        }
//        for (int i = 0; i < footerTab.getTabCount(); i++) {
//            //noinspection ConstantConditions
//            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
////            tv.setTextColor(Color.WHITE);
//            Utils.setFont("AvenirNext-Regular", tv);
//            footerTab.getTabAt(i).setCustomView(tv);
//
//        }
        footerTab.getTabAt(selectTabPos).select();
        footerTab.getTabAt(selectTabPos).setIcon(footerIcon(selectTabPos, true));
        footerTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tab.setIcon(footerIcon(tab.getPosition(), true));

                viewPager.setCurrentItem(tab.getPosition());
                if (viewPager.getCurrentItem() == 1)
                    FragWallpaper.showTargetView = 1;
                footerTab.setTabTextColors(Color.WHITE, Color.parseColor(Global.COLOR_MAIN));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(footerIcon(tab.getPosition(), false));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private int footerIcon(int pos, boolean isActive) {
        if (footerIcons == null) {
            footerIcons = new ArrayList<>();
            footerIcons.add(R.drawable.product_icon_white);
            footerIcons.add(R.drawable.delivery_icon_white);
            footerIcons.add(R.drawable.my_account_icon_white);

            footerIcons.add(R.drawable.product_icon_black);
            footerIcons.add(R.drawable.delivery_icon_black);
            footerIcons.add(R.drawable.my_account_icon_black);
        }

        return isActive ? footerIcons.get(pos + 3) : footerIcons.get(pos);

    }

//    public void setData(String q) {
//        tmpRecipes = new ArrayList<>();
//        for (MRecipe ca : allRecipes) {
//            if (ca.getSearchTag().contains(q) || ca.getTitle().contains(q))
//                tmpRecipes.add(ca);
//        }
//        adRecipe.setData(tmpRecipes);
//    }

//    public void setVideoNo(int no) {
////        tvVideoNo.setText(Utils.convertNum(no + ""));
//    }

//    private void openDialog(MNotification mNotification) {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////        dialog.setContentView(R.layout.lay_dialog);
//        dialog.show();
//
//        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
////        TextView tvDetails = (TextView) dialog.findViewById(R.id.tvDetails);
//
////        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
//
////        tvTitle.setText(mNotification.getText());
////        tvDetails.setText(mNotification.getDetails());
//
////        Utils.setFont(tvTitle, tvDetails);
////
////        btnOk.setText(Global.MSG_OK);
////        btnOk.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                dialog.dismiss();
////            }
////        });
//
//    }


    private void targetView() {
        final SpannableString spannedDesc = new SpannableString("This is the sample app for TapTargetView");
        spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "TapTargetView".length(), spannedDesc.length(), 0);
        TapTargetView.showFor(this, TapTarget.forToolbarNavigationIcon(toolbar, "Hello, world!", spannedDesc)
                .cancelable(false)
                .drawShadow(true)
                .tintTarget(false), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                // .. which evidently starts the sequence we defined earlier
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
                Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetViewSample", "You dismissed me :(");
            }
        });
    }


}
