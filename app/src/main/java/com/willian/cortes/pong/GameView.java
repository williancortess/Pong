package com.willian.cortes.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.willian.cortes.simplegameenginev1.SGAnimation;
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

    private SGAnimation		mAnimBallCCW;
    private SGAnimation		mAnimBallCW;
    private SGAnimation		mAnimOpponentDownAngry;
    private SGAnimation		mAnimOpponentDownConcerned;
    private SGAnimation		mAnimOpponentDownHappy;
    private SGAnimation     mAnimOpponentUpAngry;
    private SGAnimation		mAnimOpponentUpConcerned;
    private SGAnimation		mAnimOpponentUpHappy;
    private SGAnimation     mAnimPlayerDownAngry;
    private SGAnimation		mAnimPlayerDownConcerned;
    private SGAnimation		mAnimPlayerDownHappy;
    private SGAnimation		mAnimPlayerUpAngry;
    private SGAnimation		mAnimPlayerUpConcerned;
    private SGAnimation		mAnimPlayerUpHappy;

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
        SGImage ballImage = imageFactory.createImage("tilesets/ball.png");

        //4 x 2 = 8 tiles a bola -  Calcula as dimensoes de tile
        mTsetBall = new SGTileset(ballImage, new Point(4, 2), null);


        //Animacoes da bola
        int[] ballTilesCCW = { 0, 1, 2, 3 };
        mAnimBallCCW = new SGAnimation(ballTilesCCW, 0.1f);

        int[] ballTilesCW = { 4, 5, 6, 7 };
        mAnimBallCW = new SGAnimation(ballTilesCW, 0.1f);

        // Paddle do oponente
        SGImage opponentImage = imageFactory.createImage("tilesets/opponent.png");
        //8 x 2 = 16 tiles o paddle
        mTsetOpponent = new SGTileset(opponentImage, new Point(8, 2),
                new Rect(0, 0,
                        GameModel.PADDLE_WIDTH,
                        GameModel.PADDLE_HEIGHT));
        //Rect indca a area dentro de um tile que sera realmente desenhada

        //Animacoes Opponent
        int[] opponentTilesDownHappy = { 0, 1 };
        mAnimOpponentDownHappy = new SGAnimation(opponentTilesDownHappy, 0.1f);

        int[] opponentTilesUpHappy = { 2, 3 };
        mAnimOpponentUpHappy = new SGAnimation(opponentTilesUpHappy, 0.1f);

        int[] opponentTilesDownConcerned = { 4, 5 };
        mAnimOpponentDownConcerned = new SGAnimation(opponentTilesDownConcerned, 0.1f);

        int[] opponentTilesUpConcerned = { 6, 7 };
        mAnimOpponentUpConcerned = new SGAnimation(opponentTilesUpConcerned, 0.1f);

        int[] opponentTilesDownAngry = { 8, 9 };
        mAnimOpponentDownAngry = new SGAnimation(opponentTilesDownAngry, 0.1f);

        int[] opponentTilesUpAngry = { 10, 11 };
        mAnimOpponentUpAngry = new SGAnimation(opponentTilesUpAngry, 0.1f);

        // Paddle do jogador
        SGImage playerImage = imageFactory.createImage("tilesets/player.png");
        mTsetPlayer = new SGTileset(playerImage, new Point(8, 2),
                new Rect(0, 0,
                        GameModel.PADDLE_WIDTH,
                        GameModel.PADDLE_HEIGHT));

        int[] playerTilesDownHappy = { 0, 1 };
        mAnimPlayerDownHappy = new SGAnimation(playerTilesDownHappy, 0.1f);

        int[] playerTilesUpHappy = { 2, 3 };
        mAnimPlayerUpHappy = new SGAnimation(playerTilesUpHappy, 0.1f);

        int[] playerTilesDownConcerned = { 4, 5 };
        mAnimPlayerDownConcerned = new SGAnimation(playerTilesDownConcerned, 0.1f);

        int[] playerTilesUpConcerned = { 6, 7 };
        mAnimPlayerUpConcerned = new SGAnimation(playerTilesUpConcerned, 0.1f);

        int[] playerTilesDownAngry = { 8, 9 };
        mAnimPlayerDownAngry = new SGAnimation(playerTilesDownAngry, 0.1f);

        int[] playerTilesUpAngry = { 10, 11 };
        mAnimPlayerUpAngry = new SGAnimation(playerTilesUpAngry, 0.1f);

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
                //ANIMACOES
                if(currentEntity.getCategory() == "paddle")
                {
                    SGAnimation	currentAnimation;
                    SGTileset 	tileset;

                    if(currentEntity.getId() == GameModel.PLAYER_ID)
                    {
                        tileset = mTsetPlayer;
                        if(currentEntity.hasFlag(EntPaddle.STATE_LOOKING_UP))
                        {
                            if(currentEntity.hasFlag(EntPaddle.STATE_HAPPY))
                            {
                                currentAnimation = mAnimPlayerUpHappy;
                            }
                            else if(currentEntity.hasFlag(EntPaddle.STATE_CONCERNED))
                            {
                                currentAnimation = mAnimPlayerUpConcerned;
                            }
                            else // entity.hasFlag(EntPaddle.STATE_ANGRY)
                            {
                                currentAnimation = mAnimPlayerUpAngry;
                            }
                        }
                        else
                        {
                            if(currentEntity.hasFlag(EntPaddle.STATE_HAPPY))
                            {
                                currentAnimation = mAnimPlayerDownHappy;
                            }
                            else if(currentEntity.hasFlag(EntPaddle.STATE_CONCERNED))
                            {
                                currentAnimation = mAnimPlayerDownConcerned;
                            }
                            else // entity.hasFlag(EntPaddle.STATE_ANGRY)
                            {
                                currentAnimation = mAnimPlayerDownAngry;
                            }
                        }
                    }
                    else // entity.getId() == GameModel.OPPONENT_ID
                    {
                        tileset = mTsetOpponent;
                        if(currentEntity.hasFlag(EntPaddle.STATE_LOOKING_UP))
                        {
                            if(currentEntity.hasFlag(EntPaddle.STATE_HAPPY))
                            {
                                currentAnimation = mAnimOpponentUpHappy;
                            }
                            else if(currentEntity.hasFlag(EntPaddle.STATE_CONCERNED))
                            {
                                currentAnimation = mAnimOpponentUpConcerned;
                            }
                            else // entity.hasFlag(EntPaddle.STATE_ANGRY)
                            {
                                currentAnimation = mAnimOpponentUpAngry;
                            }
                        }
                        else
                        {
                            if(currentEntity.hasFlag(EntPaddle.STATE_HAPPY))
                            {
                                currentAnimation = mAnimOpponentDownHappy;
                            }
                            else if(currentEntity.hasFlag(EntPaddle.STATE_CONCERNED))
                            {
                                currentAnimation = mAnimOpponentDownConcerned;
                            }
                            else // entity.hasFlag(EntPaddle.STATE_ANGRY)
                            {
                                currentAnimation = mAnimOpponentDownAngry;
                            }
                        }
                    }

                    int tileIndex = currentAnimation.step(elapsedTimeInSeconds);

                    if(currentEntity.hasFlag(EntPaddle.STATE_HIT))
                    {
                        //Inicia a animacao repetindo 2 vezes
                        currentAnimation.start(2);

                        currentEntity.removeFlags(EntPaddle.STATE_HIT);
                    }

                    //Posicao e dimensao da entidade
                    PointF position = currentEntity.getPosition();
                    PointF dimensions = currentEntity.getDimensions();
                    Rect drawingArea = tileset.getTile(tileIndex); //Area de desenho

                    //Renderiza as imagens na tela
                    renderer.drawImage(tileset.getImage(), drawingArea, position, dimensions);
                }//Fim do if (currentEntity.getCategory() == "paddle")

                else if(currentEntity.getCategory() == "ball")
                {
                    SGAnimation currentAnimation;
                    int tileIndex;

                    if(currentEntity.hasFlag(EntBall.STATE_ROLL_CW))
                    {
                        currentAnimation = mAnimBallCW;
                    }
                    else
                    {
                        currentAnimation = mAnimBallCCW;
                    }

                    if(mModel.getCurrentState() == GameModel.STATE_RUNNING)
                    {
                        //Loop infinito -1
                        currentAnimation.start(-1);
                        tileIndex = currentAnimation.step(elapsedTimeInSeconds);
                    }
                    else
                    {
                        tileIndex = currentAnimation.getCurrentTile();
                    }

                    PointF position = currentEntity.getPosition();
                    PointF dimensions = currentEntity.getDimensions();
                    Rect drawingArea = mTsetBall.getTile(tileIndex);

                    renderer.drawImage(mTsetBall.getImage(), drawingArea, position, dimensions);
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

