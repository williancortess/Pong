package com.willian.cortes.pongv1.cortes;

import android.view.MotionEvent;

import com.willian.cortes.simplegameenginev1.SGInputSubscriber;

/**
 * Created by Willian on 21/02/2017.
 */

public class GameController implements SGInputSubscriber
{
    private GameModel mModel;

    public GameController(GameModel model)
    {
        mModel = model;
    }

    @Override
    public void onDown(MotionEvent event)
    {
    }

    @Override
    public void onScroll(MotionEvent downEvent, MotionEvent moveEvent,
                         float distanceX, float distanceY)
    {
        if(mModel.getCurrentState() == GameModel.STATE_RUNNING)
        {
            mModel.movePlayer(-distanceX, -distanceY);
        }
    }

    @Override
    public void onUp(MotionEvent event)
    {
    }

    public GameModel getModel() { return mModel; }
}
