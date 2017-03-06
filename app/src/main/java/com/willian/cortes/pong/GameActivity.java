package com.willian.cortes.pong;


import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.willian.cortes.simplegameenginev1.SGActivity;
import com.willian.cortes.simplegameenginev1.SGInputPublisher;
import com.willian.cortes.simplegameenginev1.SGPreferences;

/**
 * Created by Willian on 11/02/2017.
 */

public class GameActivity extends SGActivity{
    public static final String TAG = "PongV1";

    private GameController mController;
    private GameModel mModel;
    private GameView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);

            enableFullScreen();
            enableKeepScreenOn();

            SGPreferences preferences = new SGPreferences(this);
            if (preferences.getInt("first_time", -1) == -1) {
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

            Point worldDimensions = new Point(480, 320);
            mModel = new GameModel(worldDimensions);

            mView = new GameView(this, mModel);
            setContentView(mView);

            SGInputPublisher inputPublisher = new SGInputPublisher(this);
            mController = new GameController(mModel);
            inputPublisher.registerSubscriber(mController);
            setInputPublisher(inputPublisher);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        mView.getMusicPlayer().release();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        mView.getMusicPlayer().pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        mView.getMusicPlayer().resume();
    }
}
