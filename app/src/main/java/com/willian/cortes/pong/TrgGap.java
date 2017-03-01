package com.willian.cortes.pong;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGTrigger;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 23/02/2017.
 *
 * Trigger do vao superior do campo Gap = vao
 */

public class TrgGap extends SGTrigger{

    public static final int GAP_SIZE = 50;

    public TrgGap(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.TRG_GAP_ID, position, dimensions);
    }
    @Override

    public void onHit(SGEntity entity, float elapsedTimeInSeconds)
    {
        entity.setPosition(entity.getPosition().x, GAP_SIZE);

        //Caso seja o paddle do oponente sua velocidade sera invertida
//        if(entity.getId() == GameModel.OPPONENT_ID)
//        {
            //Convertendo o entety em EntOpponent para acessar getSpeed() e setSpeed()
//            EntOpponent opponent = (EntOpponent)entity;
//            opponent.setSpeed(-opponent.getSpeed());
//        }
    }
}
