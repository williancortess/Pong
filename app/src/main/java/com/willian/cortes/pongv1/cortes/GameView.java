package com.willian.cortes.pongv1.cortes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.willian.cortes.pongv1.R;
import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGImage;
import com.willian.cortes.simplegameenginev1.SGImageFactory;
import com.willian.cortes.simplegameenginev1.SGRenderer;
import com.willian.cortes.simplegameenginev1.SGView;

import java.util.ArrayList;

/**
 * Created by Willian on 11/02/2017.
 */

public class GameView extends SGView {

    private boolean 		mIsDebug = false;
    private GameModel 	    mModel;
    private Rect            mTempSrcRect = new Rect();
    private SGImage         mBallImage;
    private SGImage 		mOpponentImage;
    private SGImage 		mPlayerImage;

    private GameView(Context context)
    {
        super(context);
    }

    public GameView(Context context, GameModel model)
    {
        super(context);
        mModel = model;
    }

    @Override
    public void setup()
    {
        mModel.setup(); //Invoca o setup da classe GameModel

        SGImageFactory imageFactory = getImageFactory();

        mBallImage = imageFactory.createImage("ball.png");
        mPlayerImage = imageFactory.createImage("player.png");
        mOpponentImage = imageFactory.createImage("opponent.png");
    }

    @Override
    public void step(Canvas canvas, float elapsedTimeInSeconds)
    {
        //Chama o metodo steep do modelo dentro da visao para sincronizar o modelo com o loop do jogo
        mModel.step(elapsedTimeInSeconds);
        SGRenderer renderer = getRenderer();
        renderer.beginDrawing(canvas, Color.BLACK);

        ArrayList<SGEntity> entities = mModel.getEntities();

        if(mIsDebug)
        {
            for(SGEntity currentEntity : entities)
            {
                SGEntity.DebugDrawingStyle style = currentEntity.getDebugDrawingStyle();
                if(style == SGEntity.DebugDrawingStyle.FILLED)
                {
                    renderer.drawRect(currentEntity.getBoundingBox(), currentEntity.getDebugColor());
                }
                else
                {
                    renderer.drawOutlineRect(currentEntity.getBoundingBox(), currentEntity.getDebugColor());
                }
            }
        }
        else
        {
            for(SGEntity currentEntity : entities)
            {
                if(currentEntity.getCategory() != "trigger")
                {
                    if(currentEntity.getId() == GameModel.PLAYER_ID)
                    {
                        mTempSrcRect.set(0, 0, GameModel.PADDLE_WIDTH, GameModel.PADDLE_HEIGHT);
                        renderer.drawImage(mPlayerImage, mTempSrcRect,
                                currentEntity.getPosition(),
                                currentEntity.getDimensions());
                    }
                    else if(currentEntity.getId() == GameModel.OPPONENT_ID)
                    {
                        mTempSrcRect.set(0, 0, GameModel.PADDLE_WIDTH, GameModel.PADDLE_HEIGHT);
                        renderer.drawImage(mOpponentImage, mTempSrcRect,
                                currentEntity.getPosition(),
                                currentEntity.getDimensions());
                    }
                    else // (currentEntity.getId() == GameModel.BALL_ID)
                    {
                        mTempSrcRect.set(0, 0, GameModel.BALL_SIZE, GameModel.BALL_SIZE);
                        renderer.drawImage(mBallImage, mTempSrcRect,
                                currentEntity.getPosition(),
                                currentEntity.getDimensions());
                    }
                }
            }
        }
        renderer.endDrawing();
    }
    }

//    protected void setup()
//    {
//        SGImageFactory imageFactory = getImageFactory();
//
//        mBallImage = imageFactory.createImage(R.drawable.ball);
//        mPlayerImage = imageFactory.createImage("player.png");
//        mOpponentImage = imageFactory.createImage("opponent.png");
//
//        Point viewDimensions = getDimensions();
//        Point viewCenter = new Point(viewDimensions.x / 2, viewDimensions.y / 2);
//
//        int halfBall = BALL_SIZE / 2;
//        int halfPaddleHeight = PADDLE_HEIGHT / 2;
//
//        mBallDestination.set(viewCenter.x - halfBall, // Esquerda
//                viewCenter.y - halfBall, // Topo
//                viewCenter.x + halfBall, // Direita
//                viewCenter.y + halfBall); // Base
//
//        mPlayerDestination.set(DISTANCE_FROM_EDGE, // Esquerda
//                viewCenter.y - halfPaddleHeight, // Topo
//                DISTANCE_FROM_EDGE + PADDLE_WIDTH, // Direita
//                viewCenter.y + halfPaddleHeight); // Base
//
//        mOpponentDestination.set(
//                viewDimensions.x - (DISTANCE_FROM_EDGE + PADDLE_WIDTH), // Esquerda
//                viewCenter.y - halfPaddleHeight,    // Topo
//                viewDimensions.x - DISTANCE_FROM_EDGE, // Direita
//                viewCenter.y + halfPaddleHeight); // Base
//    }

