package com.willian.cortes.pongv1.cortes;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 21/02/2017.
 */

public class EntBall extends SGEntity{
//    private float mSpeed;
    public PointF mVelocity = new PointF();

    public EntBall(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.BALL_ID, "ball", position, dimensions);
//        mSpeed = 120.0f;
        mVelocity.set(90.0f, 90.0f); //Define o deslocamento da bola
    }

    @Override
    public void step(float elapsedTimeInSeconds)
    {
        move(mVelocity.x * elapsedTimeInSeconds, mVelocity.y * elapsedTimeInSeconds);

        GameModel world = (GameModel) getWorld();
        PointF position = getPosition();
        PointF dimensions = getDimensions();

//        //Verifica se a bola colidiu com um dos limites laterais
//        if(position.x < 0)
//        {
//            setPosition(0, getPosition().y);
//            mSpeed = -mSpeed;
//        }
//        else if(position.x + dimensions.x > world.getDimensions().x)
//        {
//            setPosition(world.getDimensions().x - dimensions.x, getPosition().y);
//            mSpeed = -mSpeed;
//        }

        //Verifica se a bola colidiu com algum paddle
        EntOpponent opponent = world.getOpponent();
        EntPlayer player = world.getPlayer();

        if(world.collisionTest(getBoundingBox(), player.getBoundingBox()))
        {
            setPosition(player.getBoundingBox().right, position.y);
            mVelocity.x = -mVelocity.x;
        }
        else if(world.collisionTest(getBoundingBox(), opponent.getBoundingBox()))
        {
            setPosition(opponent.getPosition().x - dimensions.x, position.y);
            mVelocity.x = -mVelocity.x;
        }
    }

    public PointF getVelocity() { return mVelocity; }

    public void setVelocity(float speedX, float speedY) { mVelocity.set(speedX, speedY); }
}
