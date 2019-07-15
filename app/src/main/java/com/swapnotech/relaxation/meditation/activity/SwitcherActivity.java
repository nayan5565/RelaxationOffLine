package com.swapnotech.relaxation.meditation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swapnotech.relaxation.meditation.tools.Global;
import com.swapnotech.relaxation.meditation.tools.Utils;

/**
 * Created by Jewel on 7/13/2016.
 */
public class SwitcherActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImageSliderActivity.start(this, 1);


        switchMe();
    }


    private void switchMe() {

        String state = Utils.getPref(Global.STATE, Global.STATE_TUT);
        switch (state) {
            case Global.STATE_TUT:
//                TutorialActivity.start(this);
                break;
            case Global.STATE_MAIN:
                startActivity(new Intent(this, MainActivity.class));
//                startActivity(new Intent(this, HomeActivity.class));
                break;
            case Global.STATE_SPLASH:
                startActivity(new Intent(this, SplashActivity.class));
                break;
            case Global.STATE_REGIS:
//                startActivity(new Intent(this, LogSignActivity.class));
                break;

            default:
                break;
        }
        finish();
    }
}
