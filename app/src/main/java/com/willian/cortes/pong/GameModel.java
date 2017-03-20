package com.willian.cortes.pong;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGTimer;
import com.willian.cortes.simplegameenginev1.SGWorld;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Willian on 21/02/2017.
 *
 * Representa o mundo do Pong - Classe Anfitria
 */

public class GameModel extends SGWorld{
    public static final int     SCENE_WIDTH = 480;
    public static final int     SCENE_HEIGHT = 320;

    public final static int PADDLE_BBOX_PADDING = 3;
    public static final int BALL_ID = 0;
    public static final int OPPONENT_ID = 1;
    public static final int PLAYER_ID = 2;
    public final static int BALL_SIZE = 16;
    public final static int DISTANCE_FROM_EDGE = 16;
    public final static int PADDLE_HEIGHT = 98;
    public final static int PADDLE_WIDTH = 23;

    public static final int     STATE_RESTART = 0;
    public static final int     STATE_RUNNING = 1;
    public static final int     STATE_GAME_OVER = 2;
    public static final int     STATE_GOAL = 3;
    public static final int     STATE_PAUSED = 4;

    public static final int     TRG_GAP_ID = 3;
    public static final int     TRG_LEFT_GOAL_ID = 4;
    public static final int     TRG_LOWER_WALL_ID = 5;
    public static final int     TRG_RIGHT_GOAL_ID = 6;
    public static final int     TRG_UPPER_WALL_ID = 7;

    public final static int     GOAL_WIDTH = 64;
    public final static int     WALL_HEIGHT = 64;

    private SGTimer mGoalTimer;
    private SGTimer mRestartTimer;

    private EntBall 		mBall;
    private EntOpponent 	mOpponent;
    private EntPlayer		mPlayer;

    private int                 mDifficultyLevel;
    private int                 mCurrentState; //Guarda o estado atual do modelo
    private int                 mOpponentScore;
    private int                 mPlayerScore;
    private int                 mWhoScored; //Armazena o ID do paddle que marcou o ponto

    Random                      mRandom = new Random();
    private StringBuilder       mStringBuilder = new StringBuilder();//Printa mensagens no LogCat

    private ArrayList<SGEntity> mEntities = new ArrayList<SGEntity>();
    private TrgGap              mGap;
    private TrgLeftGoal         mLeftGoal;
    private TrgLowerWall        mLowerWall;
    private TrgRightGoal        mRightGoal;
    private TrgUpperWall        mUpperWall;

    private int                 mPreviousState;

    public GameModel(Point worldDimensions, int difficultyLevel)
    {
        super(worldDimensions);

        mDifficultyLevel = difficultyLevel;
    }

    public void setup()
    {
        //Estado inicial
        mCurrentState = STATE_RESTART;

        Point worldDimensions = getDimensions();

        // Bola
        PointF tempPosition = new PointF((worldDimensions.x / 2) - (BALL_SIZE / 2),
                (worldDimensions.y / 2) - (BALL_SIZE / 2));
        PointF tempDimensions = new PointF(BALL_SIZE, BALL_SIZE);
        mBall = new EntBall(this, tempPosition, tempDimensions, mDifficultyLevel);

        mEntities.add(mBall);

        // Paddle do jogador
        tempPosition.set(DISTANCE_FROM_EDGE,
                (worldDimensions.y / 2) - (PADDLE_HEIGHT / 2));
        tempDimensions.set(PADDLE_WIDTH, PADDLE_HEIGHT);
        mPlayer = new EntPlayer(this, tempPosition, tempDimensions);
        mPlayer.setDebugColor(Color.GREEN);
        RectF bboxPadding = new RectF(0, 0, PADDLE_BBOX_PADDING, PADDLE_BBOX_PADDING);
        mPlayer.setBBoxPadding(bboxPadding);

        mEntities.add(mPlayer);

        // Paddle do oponente
        tempPosition.set(worldDimensions.x - (DISTANCE_FROM_EDGE + PADDLE_WIDTH),
                (worldDimensions.y / 2) - (PADDLE_HEIGHT / 2));
        tempDimensions.set(PADDLE_WIDTH, PADDLE_HEIGHT);
        mOpponent = new EntOpponent(this, tempPosition, tempDimensions, mDifficultyLevel);
        mOpponent.setDebugColor(Color.CYAN);
        mOpponent.setBBoxPadding(bboxPadding);

        mEntities.add(mOpponent);

        // Meta do jogador
        tempPosition.set(-GOAL_WIDTH, 0);
        tempDimensions.set(GOAL_WIDTH - BALL_SIZE, worldDimensions.y);
        mLeftGoal = new TrgLeftGoal(this, tempPosition, tempDimensions);
        mLeftGoal.addObservedEntity(mBall);
        mEntities.add(mLeftGoal);

        // Meta do oponente
        tempPosition.set(worldDimensions.x + BALL_SIZE, 0);
        tempDimensions.set(GOAL_WIDTH - BALL_SIZE, worldDimensions.y);
        mRightGoal = new TrgRightGoal(this, tempPosition, tempDimensions);
        mRightGoal.addObservedEntity(mBall);
        mEntities.add(mRightGoal);

        // Vão superior
        tempPosition.set(0, 0);
        tempDimensions.set(worldDimensions.x, TrgGap.GAP_SIZE);
        mGap = new TrgGap(this, tempPosition, tempDimensions);
        mGap.addObservedEntity(mOpponent);
        mGap.addObservedEntity(mPlayer);
        mEntities.add(mGap);

        // Parede inferior
        tempPosition.set(0, worldDimensions.y);
        tempDimensions.set(worldDimensions.x, WALL_HEIGHT);
        mLowerWall = new TrgLowerWall(this, tempPosition, tempDimensions);
        mLowerWall.addObservedEntity(mBall);
        mLowerWall.addObservedEntity(mOpponent);
        mLowerWall.addObservedEntity(mPlayer);
        mEntities.add(mLowerWall);

        // Parede superior
        tempPosition.set(0, -WALL_HEIGHT);
        tempDimensions.set(worldDimensions.x, WALL_HEIGHT);
        mUpperWall = new TrgUpperWall(this, tempPosition, tempDimensions);
        mUpperWall.addObservedEntity(mBall);
        mEntities.add(mUpperWall);

        // Timer de reinício
        mRestartTimer = new SGTimer(0.8f);

        // Timer de marcação de ponto
        mGoalTimer = new SGTimer(1.2f);
    }

    @Override
    public void step(float elapsedTimeInSeconds)
    {
        if(elapsedTimeInSeconds > 1.0f)
        {
            elapsedTimeInSeconds = 0.1f;
        }

        if(mCurrentState == STATE_RUNNING)
        {
            for(SGEntity currentEntity : mEntities)
            {
                //Chama o metodo step de cada uma das entidades
                currentEntity.step(elapsedTimeInSeconds);
            }
        }
        else if(mCurrentState == STATE_GOAL)
        {
            //Verifica se o timer foi iniciado
            if(!mGoalTimer.hasStarted())
            {
                mBall.setHasCollided(false);
                mBall.setCollisionType(EntBall.COLLISION_NONE);
                mGoalTimer.start();
            }

            //Verifica se o tempo transcorrido alcancou o valor
            if(mGoalTimer.step(elapsedTimeInSeconds))
            {
                mGoalTimer.stopAndReset();

                if(mOpponentScore == 5)
                {
                    Log.d("PongV2", "Fim de jogo!");
                    mCurrentState = STATE_GAME_OVER;
                }
                else
                {
                    mCurrentState = STATE_RESTART;
                }
            }
        }
        else if(mCurrentState == STATE_RESTART)
        {
            if(!mRestartTimer.hasStarted())
            {
                mRestartTimer.start();
                resetWorld();
            }
            if(mRestartTimer.step(elapsedTimeInSeconds))
            {
                mRestartTimer.stopAndReset();
                mCurrentState = STATE_RUNNING;
            }
        }

        else if(mCurrentState == STATE_GAME_OVER)
        {
        }
    }

    public void logScore()
    {
        mStringBuilder.delete(0, mStringBuilder.length());
        mStringBuilder.append("Placar: ");
        mStringBuilder.append(mPlayerScore);
        mStringBuilder.append(" X ");
        mStringBuilder.append(mOpponentScore);

        Log.d("PongV2", mStringBuilder.toString());
    }

    //Reseta a posicao das entidades quando um ponto e marcado
    public void resetWorld()
    {
        float halfWorldWidth = getDimensions().x / 2;
        float halfWorldHeight = getDimensions().y / 2;
        float halfBallWidth = mBall.getDimensions().x / 2;
        float halfBallHeight = mBall.getDimensions().y / 2;
        float halfPaddleHeight = mPlayer.getDimensions().y / 2;
        float ballPositionX = halfWorldWidth - halfBallWidth;
        float ballPositionY = halfWorldHeight - halfBallHeight;
        float paddlePositionY = halfWorldHeight - halfPaddleHeight;

        mBall.setPosition(ballPositionX, ballPositionY);
        mOpponent.setPosition(mOpponent.getPosition().x, paddlePositionY);
        mPlayer.setPosition(mPlayer.getPosition().x, paddlePositionY);

        _calculateBallAngle();

        //Random para a bola iniciar para esquerda ou direita
//        if(mRandom.nextInt(2) == 0)
//        {
//            mBall.setVelocity(-90.0f, 90.0f);
//        }
//        else
//        {
//            mBall.setVelocity(90.0f, 90.0f);
//        }
    }

    public void pause()
    {
        mPreviousState = mCurrentState;
        mCurrentState = STATE_PAUSED;
    }

    public void unpause()
    {
        mCurrentState = mPreviousState;
    }

    //Indica os fatores de deslocamento nos eixo x / y
    public void movePlayer(float x, float y)
    {
        mPlayer.move(0, y);

        PointF playerPosition = mPlayer.getPosition();
        PointF playerDimensions = mPlayer.getDimensions();
        Point worldDimensions = getDimensions();

        RectF tempBoundingBox = mPlayer.getBoundingBox();

        if(playerPosition.y < 0)
        {
            mPlayer.setPosition(playerPosition.x, 0);
        }
        else if(tempBoundingBox.bottom > worldDimensions.y)
        {
            mPlayer.setPosition(playerPosition.x, worldDimensions.y - (playerDimensions.y - PADDLE_BBOX_PADDING));
        }
    }

    //Anglo inicial da bola
    private void _calculateBallAngle()
    {
        //Gera valor aleatorio de 0 a 3
        int chooseSide = mRandom.nextInt(4);
        int angle;

        if(chooseSide == 0) // Superior direito
        {
            //Gera valor de 0 a 15
            angle = mRandom.nextInt(16);
        }
        else if(chooseSide == 1) // Superior esquerdo
        {
            angle = mRandom.nextInt(16) + 165; //Soma o valor equivalente ao angulo inicial
        }
        else if(chooseSide == 2) // Inferior esquerdo
        {
            angle = mRandom.nextInt(16) + 180;
        }
        else // chooseSide == 3, inferior direito
        {
            angle = mRandom.nextInt(16) + 345;
        }


        mBall.setPosition(getDimensions().x / 2 - mBall.getDimensions().x / 2,
                getDimensions().y / 2 - mBall.getDimensions().y / 2);

        float ballSpeed = mBall.calculateSpeed(mPlayerScore);
        float radian = (float)(angle * (Math.PI / 180));
        mBall.setVelocity(ballSpeed * (float) Math.cos(radian),
                ballSpeed * (float)Math.sin(radian));
    }

    public EntBall      getBall() { return mBall; }
    public EntOpponent  getOpponent() { return mOpponent; }
    public EntPlayer    getPlayer() { return mPlayer; }

    public int          getCurrentState() { return mCurrentState; }
    public int          getOpponentScore() { return mOpponentScore; }
    public int          getPlayerScore() { return mPlayerScore; }
    public int          getWhoScored(){return  mWhoScored;}

    public void         setCurrentState(int state) { mCurrentState = state; }
    public void         setWhoScored(int whoScored){mWhoScored = whoScored;}

    public void         increaseOpponentScore() { mOpponentScore++; }
    public void         increasePlayerScore() { mPlayerScore++; }

    //Usado pela visao para recuperar os elementos a serem desenhados
    public ArrayList<SGEntity> getEntities() { return mEntities; }
}
