package com.willian.cortes.pongv1.cortes;

import android.graphics.PointF;
import android.util.Log;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGTrigger;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 23/02/2017.
 */

public class TrgRightGoal extends SGTrigger
{
    public TrgRightGoal(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.TRG_RIGHT_GOAL_ID, position, dimensions);
    }

    @Override
    public void onHit(SGEntity entity, float elapsedTimeInSeconds)
    {
        GameModel model = (GameModel)getWorld();

        model.increasePlayerScore();

        //Aumenta a dificuldade a cada ponto marcado
        EntOpponent opponent = model.getOpponent();
        opponent.calculateSpeed(model.getPlayerScore());
        opponent.decreaseReaction();

        Log.d("PongV2", "Jogador marca um ponto!");
        model.logScore();

        model.setCurrentState(GameModel.STATE_GOAL);
    }
}
