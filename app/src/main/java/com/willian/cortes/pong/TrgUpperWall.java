package com.willian.cortes.pong;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGTrigger;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 23/02/2017.
 *
 * Reflete verticalmente  o movimento da bola quando pegar na parte superior
 */

public class TrgUpperWall extends SGTrigger
{
    public TrgUpperWall(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.TRG_UPPER_WALL_ID, position, dimensions);
    }

    //Somente a bola ativa esta trigger - o TrgGap nao deixa os paddles chegarem ate aqui
    @Override
    public void onHit(SGEntity entity, float elapsedTimeInSeconds)
    {
        //Convertendo a variavel entity em EntBall
        EntBall ball = (EntBall)entity;
        PointF ballVelocity = ball.getVelocity();

        ball.setPosition(ball.getPosition().x, 0);
        ball.setVelocity(ballVelocity.x, -ballVelocity.y);

        if(ball.getVelocity().x > 0)
        {
            removeFlags(EntBall.STATE_ROLL_CW);
        }
        else
        {
            addFlags(EntBall.STATE_ROLL_CW);
        }
    }
}
