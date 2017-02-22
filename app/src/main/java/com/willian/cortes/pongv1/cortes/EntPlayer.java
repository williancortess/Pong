package com.willian.cortes.pongv1.cortes;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 21/02/2017.
 */

public class EntPlayer extends EntPaddle {
    public EntPlayer(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.PLAYER_ID, position, dimensions);
    }

    @Override
    public void step(float elapsedTimeInSeconds)
    {
    }
}
