package com.willian.cortes.pongv1.cortes;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 21/02/2017.
 */

public class EntBall extends SGEntity{
    private float mSpeed;

    public EntBall(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.BALL_ID, "ball", position, dimensions);
        mSpeed = 120.0f;
    }

    @Override
    public void step(float elapsedTimeInSeconds)
    {
        move(mSpeed * elapsedTimeInSeconds, 0);

        SGWorld world = getWorld();
        PointF position = getPosition();
        PointF dimensions = getDimensions();

        if(position.x < 0)
        {
            setPosition(0, getPosition().y);
            mSpeed = -mSpeed;
        }
        else if(position.x + dimensions.x > world.getDimensions().x)
        {
            setPosition(world.getDimensions().x - dimensions.x, getPosition().y);
            mSpeed = -mSpeed;
        }
    }
}
