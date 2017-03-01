package com.willian.cortes.pong;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 21/02/2017.
 */

public class EntPaddle extends SGEntity{
    public static final int NUM_OF_SECTORS = 10;

    private float mSectorSize;

    public EntPaddle(SGWorld world, int id, PointF position, PointF dimensions)
    {
        super(world, id, "paddle", position, dimensions);

        mSectorSize = dimensions.y / (NUM_OF_SECTORS - 1);
    }

    public float getSectorSize() { return mSectorSize; }
}
