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

public class SplashScreenView extends SGView {
    private SGImage mImgSplashScreen;
    private PointF mImagePosition = new PointF(0, 0);
    private PointF	mImageDimensions = new PointF();

    public SplashScreenView(Context context)
    {
        super(context);

        mImgSplashScreen = getImageFactory().createImage("images/splash_screen.png");
        mImageDimensions.set(mImgSplashScreen.getDimensions().x, mImgSplashScreen.getDimensions().y);
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
        int screenColor = Color.DKGRAY;
        int viewportColor = Color.BLACK;

        renderer.beginDrawing(canvas, screenColor, viewportColor);

        renderer.drawImage(mImgSplashScreen, null, mImagePosition, mImageDimensions);

        renderer.endDrawing();
    }
}
