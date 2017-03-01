package com.willian.cortes.pong;

import android.graphics.Point;
import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGTrigger;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 23/02/2017.
 */

public class TrgLowerWall extends SGTrigger
{
    public TrgLowerWall(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.TRG_LOWER_WALL_ID, position, dimensions);
    }

    @Override
    public void onHit(SGEntity entity, float elapsedTimeInSeconds)
    {
        Point worldDimensions = getWorld().getDimensions();

        if(entity.getId() == GameModel.PLAYER_ID)
        {
            entity.setPosition(entity.getPosition().x, worldDimensions.y - entity.getDimensions().y);
        }
        else if(entity.getId() == GameModel.OPPONENT_ID)
        {
            EntOpponent opponent = (EntOpponent)entity;
            opponent.setPosition(entity.getPosition().x, worldDimensions.y - entity.getDimensions().y);
//            opponent.setSpeed(-opponent.getSpeed());
        }
        else // (entity.getId() == GameModel.BALL_ID)
        {
            EntBall ball = (EntBall)entity;
            ball.setPosition(ball.getPosition().x, worldDimensions.y - ball.getDimensions().y);
            ball.setVelocity(ball.getVelocity().x, -ball.getVelocity().y);
        }
    }
}
