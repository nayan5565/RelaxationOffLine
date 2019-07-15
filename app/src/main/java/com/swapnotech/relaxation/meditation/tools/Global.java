package com.swapnotech.relaxation.meditation.tools;

import com.swapnotech.relaxation.meditation.model.Mdownload;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public class Global {

    public static ArrayList<Mdownload> downloadImageList;

    public static int categoriId;
    public static String subCatImageURL;
    public static String singleImageURL;
    public static String singleWallpaperURL;
    public static int isExit;
    public static int orderLayoutDissmiss;


    public static final String APP_NAME = "Candy Party";
    public static final String APK_LINK = "https://play.google.com/store/apps/details?id=com.swapnopuri.bangla.bangladeshi.deshi.recipes";

    //main state names
    public static final String STATE = "state";
    public static final String STATE_TUT = "tut";
    public static final String STATE_MAIN = "main";
    public static final String STATE_SPLASH = "splash";
    public static final String STATE_REGIS = "regis";
    public static final String STATE_PROFILE = "profile";
    public static final String STATE_PROFILE2 = "profile2";

    //user info
    public static final String USER = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_TYPE = "user_type";
    //tab names
    public static final String TAB_CATEGORY = "Relax";
    public static final String TAB_RECIPE = "Contact";


    //admob & analytics
    public static final String FULL_SCREEN_AD = "ca-app-pub-8474387536677949/3988196801";
    public static final String SAMLL_BANNER_AD = "ca-app-pub-8474387536677949/2511463600";
    public static final String BANNER_APP_ID_TEST = "ca-app-pub-3852049226216249~9232307213";
    public static final String BANNER_APP_ID = "ca-app-pub-1386205867680580~9210543752";
    public static final String BANNER_APP_UID = "ca-app-pub-1386205867680580/3024409355";
    public static final String INTERSTISIAL_UID = "ca-app-pub-1386205867680580/1712676150";
    public static final String INTERSTISIAL_UID_TEST = "ca-app-pub-3940256099942544/1033173712";
    public static final String BANNER_APP_UID_TEST = "ca-app-pub-3852049226216249/1709040413";
    public static final String DB_PASS = "test123";
    public static final String TITLE_VIDEO = "ভিডিও দেখে সহজে রাঁধুন ";

    public static final String ANALYTICS_ID = "UA-117108731-1";
    public static final String ANALYTICS_ID_TEST = "UA-111007286-1";
    public static final int LIMIT_VIDEO_INTERSTITAL = 1;
    public static final int LIMIT_RECIPE_INTERSTITIAL = 5;


    //urls

    public static final String BASE = "http://www.radhooni.com/";
    public static final String API_WALLPAPERS = "http://www.radhooni.com/content/match_game/v1/wallpapers.php";
    public static final String API_RELAX = "http://www.radhooni.com/content/match_game/v1/wallpapers_catgeroy.php";
    public static final String API_MUSIC = "http://www.radhooni.com/content/match_game/v1/sound.php";

    public static final String API_SYNC = "candyparty/api/v2/sync_a.php";


    public static final String API_FIREBASE_PUSH = "http://www.step2code.com/firebase_push";

    public static final String VIDEO_DOWNLOAD_DIR = "video";


    //email
    public static final String EMAIL_TO = "relax@gmail.com";
    public static final String EMAIL_SUBJECT_TXT = "Subject ";
    public static final String EMAIL_SUBJECT = "Contact";
    public static final String EMAIL_FOOTER = "";
    public static final String EMAIL_SEND_TXT = "Submit";
    public static final String EMAIL_FROM_TXT = "Name";
    public static final String EMAIL_TO_TXT = "Email";
    public static final String EMAIL_DETAILS_TXT = "Details ";
    // message
    public static final String MSG_EXIT = "Press again to exit";
    public static final String SYNC = "sync_date";

    public static final String COLOR_MAIN = "#6C4878";




}