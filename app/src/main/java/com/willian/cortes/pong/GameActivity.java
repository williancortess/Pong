package com.willian.cortes.pong;


import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.willian.cortes.simplegameenginev1.SGActivity;
import com.willian.cortes.simplegameenginev1.SGDialog;
import com.willian.cortes.simplegameenginev1.SGInputPublisher;
import com.willian.cortes.simplegameenginev1.SGPreferences;

/**
 * Created by Willian on 11/02/2017.
 */

public class GameActivity extends SGActivity{
    public static final String TAG = "PongV1";

    private SGDialog mDialog;
    private GameController mController;
    private GameModel mModel;
    private GameView mView;

    private int                 mDifficultyLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

            Bundle bundle = getIntent().getBundleExtra("settings");
            mDifficultyLevel = bundle.getInt("difficulty");
//            preferences.putInt("difficulty", mDifficultyLevel);
            mModel = new GameModel(worldDimensions, mDifficultyLevel);

            mView = new GameView(this, mModel);
            setContentView(mView);

            SGInputPublisher inputPublisher = new SGInputPublisher(this);
            mController = new GameController(mModel, mView, mView.getGui());
            inputPublisher.registerSubscriber(mController);
            setInputPublisher(inputPublisher);

        mDialog = new SGDialog(this, R.string.dialog_game_message, R.string.dialog_ok, R.string.dialog_cancel)
        {
            @Override
            public void onOk()
            {
                returnToStartScreen();
            }

            @Override
            public void onCancel()
            {
                mModel.unpause();
            }
        };
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
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        mView.getMusicPlayer().resume();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed()
    {
        if(mModel.getCurrentState() != GameModel.STATE_GAME_OVER)
        {
            mModel.pause();
            mDialog.show();
        }
        else
        {
            returnToStartScreen();
        }
    }

    public void returnToStartScreen()
    {
        finish();//Encerra a activity
        overridePendingTransition(0, 0); //Desativa o efeito de transicao
    }
}
