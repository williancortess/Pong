package com.willian.cortes.pong;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.willian.cortes.simplegameenginev1.SGActivity;
import com.willian.cortes.simplegameenginev1.SGDialog;
import com.willian.cortes.simplegameenginev1.SGInputPublisher;
import com.willian.cortes.simplegameenginev1.SGInputSubscriber;

/**
 * Created by Willian on 18/03/2017.
 */

public class StartScreenActivity extends SGActivity implements SGInputSubscriber{

    private SGDialog mDialog;
    private StartScreenView mView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.enableKeepScreenOn();
        this.enableFullScreen();

        mView = new StartScreenView(this);
        setContentView(mView);

        setInputPublisher(new SGInputPublisher(this));
        getInputPublisher().registerSubscriber(this);

        mDialog = new SGDialog(this, R.string.dialog_start_screen_message, R.string.dialog_ok, R.string.dialog_cancel)
        {
            @Override
            public void onOk()
            {
                finish();
                overridePendingTransition(0, 0);
            }
        };
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void startNextActivity()
    {
        Intent intent = new Intent(this, MenuScreenActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
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
    public void onBackPressed()
    {
        mDialog.show();
    }
}
