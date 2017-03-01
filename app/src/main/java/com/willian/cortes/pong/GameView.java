package com.willian.cortes.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGImage;
import com.willian.cortes.simplegameenginev1.SGImageFactory;
import com.willian.cortes.simplegameenginev1.SGRenderer;
import com.willian.cortes.simplegameenginev1.SGTileset;
import com.willian.cortes.simplegameenginev1.SGView;

import java.util.ArrayList;

/**
 * Created by Willian on 11/02/2017.
 */

public class GameView extends SGView {

    private boolean mIsDebug = false;
    private GameModel mModel;
    private Rect mTempSrcRect = new Rect();
//    private SGImage         mBallImage;
//    private SGImage 		mOpponentImage;
//    private SGImage 		mPlayerImage;

    private SGTileset mTsetBall;
    private SGTileset mTsetOpponent;
    private SGTileset mTsetPlayer;

    private GameView(Context context) {
        super(context);
    }

    public GameView(Context context, GameModel model) {
        super(context);
        mModel = model;
    }

    @Override
    public void setup() {
        mModel.setup(); //Invoca o setup da classe GameModel

        SGImageFactory imageFactory = getImageFactory();

        // Descrição da bola
        SGImage ballImage = imageFactory.createImage("ball.png");

        //4 x 2 = 8 tiles a bola -  Calcula as dimensoes de tile
        mTsetBall = new SGTileset(ballImage, new Point(4, 2), null);

        // Paddle do oponente
        SGImage opponentImage = imageFactory.createImage("opponent.png");
        //8 x 2 = 16 tiles o paddle
        mTsetOpponent = new SGTileset(opponentImage, new Point(8, 2),
                new Rect(0, 0,
                        GameModel.PADDLE_WIDTH,
                        GameModel.PADDLE_HEIGHT));
        //Rect indca a area dentro de um tile que sera realmente desenhada

        // Paddle do jogador
        SGImage playerImage = imageFactory.createImage("player.png");
        mTsetPlayer = new SGTileset(playerImage, new Point(8, 2),
                new Rect(0, 0,
                        GameModel.PADDLE_WIDTH,
                        GameModel.PADDLE_HEIGHT));
    }

    @Override
    public void step(Canvas canvas, float elapsedTimeInSeconds) {
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
                    SGTileset tileset;

                    if(currentEntity.getId() == GameModel.PLAYER_ID)
                    {
                        tileset = mTsetPlayer;
                    }
                    else if(currentEntity.getId() == GameModel.OPPONENT_ID)
                    {
                        tileset = mTsetOpponent;
                    }
                    else // (currentEntity.getId() == GameModel.BALL_ID)
                    {
                        tileset = mTsetBall;
                    }

                    //Posicao e dimensao da entidade
                    PointF position = currentEntity.getPosition();
                    PointF dimensions = currentEntity.getDimensions();
                    Rect drawingArea = tileset.getTile(0); //Area de desenho

                    renderer.drawImage(tileset.getImage(), drawingArea, position, dimensions);
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

