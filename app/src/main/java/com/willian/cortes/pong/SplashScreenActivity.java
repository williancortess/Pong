package com.willian.cortes.pong;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.willian.cortes.simplegameenginev1.SGActivity;
import com.willian.cortes.simplegameenginev1.SGInputPublisher;
import com.willian.cortes.simplegameenginev1.SGInputSubscriber;

/**
 * Created by Willian on 18/03/2017.
 */

public class SplashScreenActivity extends SGActivity implements SGInputSubscriber {
    private SplashScreenView mView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.enableKeepScreenOn();
        this.enableFullScreen();

        mView = new SplashScreenView(this);
        setContentView(mView);

        setInputPublisher(new SGInputPublisher(this));
        getInputPublisher().registerSubscriber(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void startNextActivity()
    {
        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onDown(MotionEvent event)
    {
    }

    @Override
    public void onScroll(MotionEvent downEvent, MotionEvent moveEvent, float distanceX, float distanceY)
    {
    }

    @Override
    public void onUp(MotionEvent event)
    {
        startNextActivity();
    }

    @Override
    public void onBackPressed() {
        startNextActivity();
    }
}
