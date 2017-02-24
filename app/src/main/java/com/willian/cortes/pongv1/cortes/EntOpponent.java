package com.willian.cortes.pongv1.cortes;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 21/02/2017.
 */

public class EntOpponent extends  EntPaddle{
    public static final float MIN_REACTION = 30;
    public static final float MAX_SPEED = 300;

    private float mReaction;
    private float mSpeed;

    public EntOpponent(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.OPPONENT_ID, position, dimensions);
//        mSpeed = 120.0f;
        //Velocidade inicial do oponente
        calculateSpeed(0);
        mReaction = dimensions.y / 2; //Metade da altura do paddle
    }

    @Override
    public void step(float elapsedTimeInSeconds) {

        PointF position = getPosition();
        PointF dimensions = getDimensions();
        GameModel model = (GameModel) getWorld();

        float paddleCenterY = position.y + (dimensions.y / 2);
        float reactionTop = paddleCenterY - mReaction;
        float reactionBottom = paddleCenterY + mReaction;

        EntBall ball = model.getBall();
        float ballCenterY = ball.getPosition().y + (ball.getDimensions().y / 2);

        //Verifica se o centro do paddle esta acima ou abaixo do centro da bola e tenta alinha-los
        if (reactionTop > ballCenterY) {
            move(0, -(mSpeed * elapsedTimeInSeconds));
        } else if(reactionBottom < ballCenterY){
            move(0, mSpeed * elapsedTimeInSeconds);
        }
    }

    public void decreaseReaction()
    {
        if(--mReaction < MIN_REACTION)
        {
            mReaction = MIN_REACTION;
        }
    }

    public void increaseSpeed()
    {
        mSpeed += 10;
    }

    public void calculateSpeed(int playerScore)
    {
        float playerScoreSqr = (playerScore + 5) * (playerScore + 5);
        mSpeed = (playerScoreSqr / (150 + playerScoreSqr)) * MAX_SPEED;
    }


    public float    getSpeed() { return mSpeed; }
    public float    getReaction() { return mReaction; }

    public void     setSpeed(float speed) { mSpeed = speed; }
    public void     setReaction(float reaction) { mReaction = reaction; }
}
