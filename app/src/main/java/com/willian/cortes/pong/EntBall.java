package com.willian.cortes.pong;

import android.graphics.PointF;
import android.graphics.RectF;

import com.willian.cortes.simplegameenginev1.SGEntity;
import com.willian.cortes.simplegameenginev1.SGWorld;

/**
 * Created by Willian on 28/02/2017.
 */

public class EntBall extends SGEntity {
    private static final float MAX_SPEED = 480.0f;
    public static final int     STATE_ROLL_CW   = 0x01;//CW = Setido horario - clock wise

    //Usa as tabelas de pesquisa para guardar senos e cossenos pre calculados
    private float mCosTable[] = new float[10];
    private float mSinTable[] = new float[10];
    private float mSpeed;

    public PointF mVelocity = new PointF();
    public EntBall(SGWorld world, PointF position, PointF dimensions)
    {
        super(world, GameModel.BALL_ID, "ball", position, dimensions);

        //Fator de conversao de graus para radianos
        float radianFactor = (float) (Math.PI / 180);

        mCosTable[0] = (float) Math.cos(50 * radianFactor);
        mCosTable[1] = (float) Math.cos(40 * radianFactor);
        mCosTable[2] = (float) Math.cos(30 * radianFactor);
        mCosTable[3] = (float) Math.cos(20 * radianFactor);
        mCosTable[4] = (float) Math.cos(10 * radianFactor);
        mCosTable[5] = (float) Math.cos(350 * radianFactor);
        mCosTable[6] = (float) Math.cos(340 * radianFactor);
        mCosTable[7] = (float) Math.cos(330 * radianFactor);
        mCosTable[8] = (float) Math.cos(320 * radianFactor);
        mCosTable[9] = (float) Math.cos(310 * radianFactor);

        mSinTable[0] = (float) Math.sin(50 * radianFactor);
        mSinTable[1] = (float) Math.sin(40 * radianFactor);
        mSinTable[2] = (float) Math.sin(30 * radianFactor);
        mSinTable[3] = (float) Math.sin(20 * radianFactor);
        mSinTable[4] = (float) Math.sin(10 * radianFactor);
        mSinTable[5] = (float) Math.sin(350 * radianFactor);
        mSinTable[6] = (float) Math.sin(340 * radianFactor);
        mSinTable[7] = (float) Math.sin(330 * radianFactor);
        mSinTable[8] = (float) Math.sin(320 * radianFactor);
        mSinTable[9] = (float) Math.sin(310 * radianFactor);

        calculateSpeed(0);

        mVelocity.x = mSpeed * mCosTable[0];
        mVelocity.y = mSpeed * mSinTable[0];

        addFlags(STATE_ROLL_CW);

        //Multiplica o angulo pelo ftor de conversao
//        float angleInradians = 50 * radianFactor;
//        mVelocity.x = (float) (mSpeed * Math.cos(angleInradians));
//        mVelocity.y = (float) (mSpeed * Math.sin(angleInradians));

//        mSpeed = 120.0f;
//        mVelocity.set(90.0f, 90.0f); //Define o deslocamento da bola
    }

    public float calculateSpeed(int playerScore)
    {
        float playerScoreSqr = (playerScore + 8) * (playerScore + 8);
        mSpeed = (playerScoreSqr / (150 + playerScoreSqr)) * MAX_SPEED;

        return mSpeed;
    }

    @Override
    public void step(float elapsedTimeInSeconds)
    {
        move(mVelocity.x * elapsedTimeInSeconds, mVelocity.y * elapsedTimeInSeconds);

        RectF ballBB = getBoundingBox();
        EntPaddle 		collidedPaddle = null;
        GameModel 	    model = (GameModel)getWorld();
        EntPaddle 		player = model.getPlayer();
        EntOpponent	    opponent = model.getOpponent();
        RectF 			opponentBB = opponent.getBoundingBox();
        RectF 			playerBB = player.getBoundingBox();

        //Verifica se a bola colidiu com um paddle
        if(model.collisionTest(ballBB, playerBB))
        {
            collidedPaddle = player;
        }
        else if(model.collisionTest(ballBB, opponentBB))
        {
            collidedPaddle = opponent;
        }

        if(collidedPaddle != null)
        {
            float 	ballDimensionX = getDimensions().x;
            float 	ballPositionY = getPosition().y;
            RectF   paddleBB = collidedPaddle.getBoundingBox();
            float 	paddlePositionY = collidedPaddle.getPosition().y;
            float 	sectorSize = collidedPaddle.getSectorSize();
            int 	sector;

            //Aumenta a velocidade da bola a cada rebatida
            mSpeed += 10;

            //Descobre qual setor a bola colidiu
            if(ballPositionY < paddlePositionY)
            {
                sector = 0;
            }
            else
            {
                //Retorna um valor entre 0 e a altura do paddle
                float deltaPosition = ballPositionY - paddlePositionY;

                //Divide pela altura de um setor para descobrir qual foi atingido
                sector = (int) Math.ceil(deltaPosition / sectorSize); //Math.ceil arredonda pra cima
            }

            //Verifica qual paddle foi atingido
            if(collidedPaddle.getId() == GameModel.PLAYER_ID)
            {
                setPosition(paddleBB.right, ballPositionY);
                mVelocity.x = mSpeed * mCosTable[sector];

                player.addFlags(EntPaddle.STATE_HIT);
                opponent.removeFlags(EntPaddle.STATE_HIT);

                if(sector <= 4)
                {
                    removeFlags(EntBall.STATE_ROLL_CW);
                }
                else
                {
                    addFlags(EntBall.STATE_ROLL_CW);
                }
            }
            else if(collidedPaddle.getId() == GameModel.OPPONENT_ID)// Paddle do oponente
            {
                //Modifica a posicao
                setPosition(paddleBB.left - ballDimensionX, ballPositionY);
                //Modifica a projecao no eixo X
                mVelocity.x = -(mSpeed * mCosTable[sector]);

                opponent.addFlags(EntPaddle.STATE_HIT);
                player.removeFlags(EntPaddle.STATE_HIT);

                if(sector <= 4)
                {
                    addFlags(EntBall.STATE_ROLL_CW);
                }
                else
                {
                    removeFlags(EntBall.STATE_ROLL_CW);
                }

            }

            else
            {
                opponent.removeFlags(EntPaddle.STATE_HIT);
                player.removeFlags(EntPaddle.STATE_HIT);
            }
            //Modifica a projecao no eixo Y
            mVelocity.y = -(mSpeed * mSinTable[sector]);
        }
    }

    public PointF getVelocity() { return mVelocity; }

    public void setVelocity(float speedX, float speedY) { mVelocity.set(speedX, speedY); }
}
