package com.willian.cortes.pongv1.cortes;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 21/02/2017.
 */

public class EntOpponent extends  EntPaddle{
    private float mSpeed;

    public EntOpponent(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.OPPONENT_ID, position, dimensions);
        mSpeed = 60.0f;
    }

    @Override
    public void step(float elapsedTimeInSeconds)
    {
        move(0, mSpeed * elapsedTimeInSeconds);

        PointF position = getPosition();
        PointF dimensions = getDimensions();
        SGWorld world = getWorld();

        float bboxPaddingBottom = getBBoxPadding().bottom;

        if(position.y < 0)
        {
            setPosition(getPosition().x, 0);
            //mSpeed = -mSpeed;
        }
        else if(getBoundingBox().bottom >= world.getDimensions().y)
        {
            position.y = world.getDimensions().y - (dimensions.y - bboxPaddingBottom);
            //mSpeed = -mSpeed;
        }
    }

    public float    getSpeed() { return mSpeed; }

    public void     setSpeed(float speed) { mSpeed = speed; }
}
