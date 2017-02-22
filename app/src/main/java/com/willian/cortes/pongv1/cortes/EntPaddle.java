package com.willian.cortes.pongv1.cortes;

import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 21/02/2017.
 */

public class EntPaddle extends SGEntity{

    public EntPaddle(SGWorld world, int id, PointF position, PointF dimensions)
    {
        super(world, id, "paddle", position, dimensions);
    }
}
