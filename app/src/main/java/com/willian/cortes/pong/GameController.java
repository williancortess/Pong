package com.willian.cortes.pong;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.willian.cortes.simplegameenginev1.SGGui;
import com.willian.cortes.simplegameenginev1.SGInputSubscriber;

/**
 * Created by Willian on 21/02/2017.
 */

public class GameController implements SGInputSubscriber
{
    private GameModel mModel;
    private GameView mView;

    private SGGui mGui;
    private boolean 	mSendToModel = true; //Sinaliza se a interface grafica tratou o evento ou nao
    private PointF	    mTempPosition = new PointF();

    public GameController(GameModel model, GameView view, SGGui gui)
    {
        mModel = model;
        mView = view;
        mGui = gui;
    }

    @Override
    public void onDown(MotionEvent event)
    {
        mTempPosition.set(event.getX(), event.getY());

        if(mGui.injectDown(mTempPosition))
        {
            mSendToModel = false;
        }
    }

    @Override
    public void onScroll(MotionEvent downEvent, MotionEvent moveEvent,
                         float distanceX, float distanceY)
    {
        if(mModel.getCurrentState() == GameModel.STATE_RUNNING && mSendToModel)
        {
            PointF scalingFactor = mView.getRenderer().getViewport().getScalingFactor();
            mModel.movePlayer(-distanceX / scalingFactor.x, -distanceY / scalingFactor.y);
        }
    }

    @Override
    public void onUp(MotionEvent event)
    {
        mTempPosition.set(event.getX(), event.getY());
        mGui.injectUp(mTempPosition);
        mSendToModel = true;
    }

    public GameModel getModel() { return mModel; }
}
