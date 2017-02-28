package com.willian.cortes.pongv1.cortes;


import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.willian.cortes.simplegameenginev1.SGActivity;
import com.willian.cortes.simplegameenginev1.SGPreferences;

/**
 * Created by Willian on 11/02/2017.
 */

public class GameActivity extends SGActivity implements GestureDetector.OnGestureListener{
    private GestureDetector mGestureDetector;

    public static final String TAG = "PongV1";

    private GameView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGestureDetector = new GestureDetector(this, this);
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
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event)
    {
        //Sempre que o usuario toca na tela
        Log.d(TAG, "onDown() chamado.");

        return true;
    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent2, float velocityX, float velocityY)
    {
        //Passa o dedo rapidamente na tela
        Log.d(TAG, "onFling() chamado.");

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event)
    {
        //Mantem o dedo durante um longo tempo - Nao invoca o singleTapUp quando retira o dedo
        Log.d(TAG, "onLongPress() chamado.");
    }

    @Override
    public boolean onScroll(MotionEvent downEvent, MotionEvent moveEvent, float distanceX, float distanceY)
    {
        mView.movePlayer(-distanceX, -distanceY);
        //Desliza o dedo pela tela com uma velocidade constante
        Log.d(TAG, "onScroll() chamado.");

        return true;

    }

    @Override
    public void onShowPress(MotionEvent event)
    {
        //invocado apos onDown - Destaca objeto que esta sendo tocado
        Log.d(TAG, "onShowPress() chamado.");
    }

    public boolean onSingleTapUp(MotionEvent event)
    {
        //Quando retira o dedo da tela - Nao ocorre com onFlings, onLongPress ou oScrolls
        Log.d(TAG, "onSingleTapUp() chamado.");

        return true;
    }
}
