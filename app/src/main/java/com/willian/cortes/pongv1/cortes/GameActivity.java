package com.willian.cortes.pongv1.cortes;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.willian.cortes.pongv1.R;
import com.willian.cortes.simplegameenginev1.SGActivity;
import com.willian.cortes.simplegameenginev1.SGInputPublisher;
import com.willian.cortes.simplegameenginev1.SGInputSubscriber;
import com.willian.cortes.simplegameenginev1.SGPreferences;

/**
 * Created by Willian on 11/02/2017.
 */

public class GameActivity extends SGActivity implements SGInputSubscriber{
    public static final String TAG = "PongV1";

    private GameView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableFullScreen();
        enableKeepScreenOn();

        //CRIACAO DA VIEW CUSTOMIZADA
        mView = new GameView(this);
        setContentView(mView);
        //setContentView(R.layout.activity_game);

        SGPreferences preferences = new SGPreferences(this);
        if(preferences.getInt("first_time", -1) == -1)
        {
            preferences.begin()
                    .putInt("first_time", 1)
                    .putInt("difficulty", 0)
                    .putInt("high_score", 15)
                    .end();
            Log.d(TAG, "Primeira inicialização.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Nível de dificuldade: ");
        stringBuilder.append(preferences.getInt("difficulty", 0));

        Log.d(TAG, stringBuilder.toString());

        stringBuilder.setLength(0);
        stringBuilder.append("High score: ");
        stringBuilder.append(preferences.getInt("high_score", 0));

        Log.d(TAG, stringBuilder.toString());

        SGInputPublisher inputPublisher = new SGInputPublisher(this);
        inputPublisher.registerSubscriber(this);
        setInputPublisher(inputPublisher);
    }

    @Override
    public void onDown(MotionEvent event) {

    }

    @Override
    public void onScroll(MotionEvent downEvent, MotionEvent moveEvention, float distanceX, float distanceY) {
        mView.movePlayer(-distanceX, -distanceY);
    }

    @Override
    public void onUp(MotionEvent event) {

    }
}
