package com.willian.cortes.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;

import com.willian.cortes.simplegameenginev1.SGImage;
import com.willian.cortes.simplegameenginev1.SGRenderer;
import com.willian.cortes.simplegameenginev1.SGView;
import com.willian.cortes.simplegameenginev1.SGViewport;

/**
 * Created by Willian on 18/03/2017.
 */

public class StartScreenView extends SGView {
    private SGImage mImgStartScreen;
    private PointF mImagePosition = new PointF(0, 0);
    private PointF	mImageDimensions = new PointF();

    public StartScreenView(Context context)
    {
        super(context);

        mImgStartScreen = getImageFactory().createImage("images/start_screen.png");
        mImageDimensions.set(mImgStartScreen.getDimensions().x, mImgStartScreen.getDimensions().y);
    }

    @Override
    public void setup()
    {
        Point viewDimensions = getDimensions();
        Point sceneDimensions = new Point(GameModel.SCENE_WIDTH, GameModel.SCENE_HEIGHT);
        SGViewport viewport = new SGViewport(sceneDimensions, viewDimensions, SGViewport.ScalingMode.FULL_SCREEN_KEEP_ORIGINAL_ASPECT);
        getRenderer().setViewport(viewport);
    }

    @Override
    public void step(Canvas canvas, float elapsedTimeInSeconds)
    {
        SGRenderer renderer = getRenderer();
        int screenColor = Color.LTGRAY;
        int viewportColor = Color.BLACK;

        renderer.beginDrawing(canvas, screenColor, viewportColor);

        renderer.drawImage(mImgStartScreen, null, mImagePosition, mImageDimensions);

        renderer.endDrawing();
    }
}
