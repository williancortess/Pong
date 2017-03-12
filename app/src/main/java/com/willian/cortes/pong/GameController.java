package com.willian.cortes.pong;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.willian.cortes.simplegameenginev1.SGInputSubscriber;

/**
 * Created by Willian on 21/02/2017.
 */

public class GameController implements SGInputSubscriber
{
    private GameModel mModel;
    private GameView mView;

    public GameController(GameModel model, GameView view)
    {
        mModel = model;
        mView = view;
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
            PointF scalingFactor = mView.getRenderer().getViewport().getScalingFactor();
            mModel.movePlayer(-distanceX / scalingFactor.x, -distanceY / scalingFactor.y);
        }
    }

    @Override
    public void onUp(MotionEvent event)
    {
    }

    public GameModel getModel() { return mModel; }
}
