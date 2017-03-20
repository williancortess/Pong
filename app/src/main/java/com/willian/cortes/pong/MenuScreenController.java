package com.willian.cortes.pong;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.willian.cortes.simplegameenginev1.SGGui;
import com.willian.cortes.simplegameenginev1.SGInputSubscriber;

/**
 * Created by Willian on 18/03/2017.
 */

public class MenuScreenController implements SGInputSubscriber{
    private SGGui mGui;
    private PointF mTempPosition = new PointF();

    public MenuScreenController(SGGui gui)
    {
        mGui = gui;
    }

    @Override
    public void onDown(MotionEvent event)
    {
        mTempPosition.set(event.getX(), event.getY());
        mGui.injectDown(mTempPosition);
    }

    @Override
    public void onScroll(MotionEvent downEvent, MotionEvent moveEvent, float distanceX, float distanceY) { }

    @Override
    public void onUp(MotionEvent event)
    {
        mTempPosition.set(event.getX(), event.getY());
        mGui.injectUp(mTempPosition);
    }
}
