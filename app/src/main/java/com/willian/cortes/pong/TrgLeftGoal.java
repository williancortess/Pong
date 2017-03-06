package com.willian.cortes.pong;

import android.graphics.PointF;
import android.util.Log;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGTrigger;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 23/02/2017.
 */

public class TrgLeftGoal extends SGTrigger
{
    public TrgLeftGoal(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.TRG_LEFT_GOAL_ID, position, dimensions);
    }

    @Override
    public void onHit(SGEntity entity, float elapsedTimeInSeconds) {
        if (isActive()) {


        GameModel model = (GameModel) getWorld();
        model.increaseOpponentScore();

            //Controla a flg de humor do player
            if(model.getOpponentScore() == 2)
            {
                model.getPlayer().addFlags(EntPaddle.STATE_CONCERNED);
                model.getPlayer().removeFlags(EntPaddle.STATE_HAPPY);
            }
            else if(model.getOpponentScore() == 4)
            {
                model.getPlayer().addFlags(EntPaddle.STATE_ANGRY);
                model.getPlayer().removeFlags(EntPaddle.STATE_CONCERNED);
            }

        Log.d("PongV2", "Oponente marca um ponto!");
        model.logScore();

        model.setCurrentState(GameModel.STATE_GOAL);

        model.setWhoScored(GameModel.OPPONENT_ID);
    }
    }
}
