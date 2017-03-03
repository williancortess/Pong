package com.willian.cortes.pong;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 21/02/2017.
 */

public class EntPaddle extends SGEntity{
    public static final int NUM_OF_SECTORS = 10;

    //1 - 4 - 6 - 8 - 16 em hexadecimais - Em binarios b00001 - b00010 - b00100 - b01000 - b10000
    public static final int STATE_HIT			= 0x01;//Colidiu com a bola
    public static final int STATE_LOOKING_UP	= 0x02;//Olhando pra cima
    public static final int STATE_HAPPY		    = 0x04;//Feliz - Player ate sofrer 1 ponto - Opponent 9
    public static final int STATE_CONCERNED	    = 0x08;//Preocupdo - Player entre sofrer 2 e 3 ponto - Opp. do 10 ao 19
    public static final int STATE_ANGRY		    = 0x10;//Bravo - Player apos sofrer o 4 ponto - Opp. 20.

    private float mSectorSize;

    public EntPaddle(SGWorld world, int id, PointF position, PointF dimensions)
    {
        super(world, id, "paddle", position, dimensions);

        mSectorSize = dimensions.y / (NUM_OF_SECTORS - 1);

        addFlags(STATE_HAPPY);
    }

    public float getSectorSize() { return mSectorSize; }
}
