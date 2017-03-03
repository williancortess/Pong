package com.willian.cortes.pong;

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
        //Indica se o player esta olhando pra cima ou pra baixo
        float playerHeight = getBoundingBox().bottom - getBoundingBox().top;
        float playerCenterY = getPosition().y + (playerHeight / 2);

        EntBall ball = ((GameModel)getWorld()).getBall();
        float ballCenterY = (ball.getPosition().y + ball.getDimensions().y / 2);

        if(playerCenterY > ballCenterY)
        {
            addFlags(EntPaddle.STATE_LOOKING_UP);
        }
        else
        {
            removeFlags(EntPaddle.STATE_LOOKING_UP);
        }
    }
}
