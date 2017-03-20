package com.willian.cortes.pong;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;

import com.willian.cortes.simplegameenginev1.SGFont;
import com.willian.cortes.simplegameenginev1.SGGui;
import com.willian.cortes.simplegameenginev1.SGImage;
import com.willian.cortes.simplegameenginev1.SGImageFactory;
import com.willian.cortes.simplegameenginev1.SGRenderer;
import com.willian.cortes.simplegameenginev1.SGTileset;
import com.willian.cortes.simplegameenginev1.SGView;
import com.willian.cortes.simplegameenginev1.SGViewport;
import com.willian.cortes.simplegameenginev1.SGWidget;
import com.willian.cortes.simplegameenginev1.SGWidgetButton;
import com.willian.cortes.simplegameenginev1.SGWidgetContainer;
import com.willian.cortes.simplegameenginev1.SGWidgetLabel;

/**
 * Created by Willian on 18/03/2017.
 */

public class MenuScreenView extends SGView {
    MenuScreenActivity	    mActivity;
    private SGWidgetButton  mBtnEasy;
    private SGWidgetButton	mBtnHard;
    private SGWidgetButton	mBtnMedium;
    private SGFont mFnt8bit;
    private SGGui           mGui;
    private SGImage mImgBackground;
    private PointF          mImagePosition = new PointF(0, 0);
    private PointF			mImageDimensions = new PointF();
    private SGWidgetLabel   mLblDifficultyLevel;
    private SGWidgetLabel	mLblEasy;
    private SGWidgetLabel	mLblHard;
    private SGWidgetLabel	mLblLoading;
    private SGWidgetLabel	mLblMedium;

    private SGWidgetButton 	mBtnEasyText;
    private SGWidgetButton 	mBtnHardText;
    private SGWidgetButton 	mBtnMediumText;


    public MenuScreenView(Context context)
    {
        super(context);

        mActivity = (MenuScreenActivity)context;

        mGui = new SGGui(getRenderer(), new Point(480, 320));
    }

    @Override
    public void setup()
    {
        Context context = getContext();
        SGRenderer renderer = getRenderer();
        SGImageFactory imageFactory = getImageFactory();

        Point viewDimensions = getDimensions();
        Point sceneDimensions = new Point(GameModel.SCENE_WIDTH, GameModel.SCENE_HEIGHT);
        SGViewport viewport = new SGViewport(sceneDimensions, viewDimensions, SGViewport.ScalingMode.FULL_SCREEN_KEEP_ORIGINAL_ASPECT);
        renderer.setViewport(viewport);

        String strDifficultyLevel = context.getResources().getString(R.string.difficulty_level);
        String strEasy = context.getResources().getString(R.string.easy);
        String strMedium = context.getResources().getString(R.string.medium);
        String strHard = context.getResources().getString(R.string.hard);
        String strLoading = context.getResources().getString(R.string.loading);

        /********************* Imagem de fundo **********************/
        mImgBackground = imageFactory.createImage("images/menu_screen.png");
        mImageDimensions.set(mImgBackground.getDimensions().x, mImgBackground.getDimensions().y);
        SGWidgetContainer guiRoot = mGui.getRoot();

        /********************** Botão 'fácil' **********************/
        PointF position = new PointF();
        PointF dimensions = new PointF();

        Point tilesNum = new Point(2, 1);
        SGTileset tileset = new
                SGTileset(imageFactory.createImage("gui/button_menu.png"), tilesNum, null);
        position.set(150, 60);
        dimensions.set(32, 32);

        mBtnEasy =
                new SGWidgetButton(SGWidget.Alignment.Left, position, dimensions, tileset, mGui)
                {
                    @Override
                    public boolean onUp(PointF position)
                    {
                        mLblLoading.setIsVisible(true);
                        Intent intent = new Intent(getContext(), GameActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("difficulty", 0);
                        intent.putExtra("settings", bundle);
                        mActivity.startNextActivity(intent);
                        return true;
                    }
                };
        guiRoot.addChild("btnEasy", mBtnEasy);

        /************************* Botão 'médio' *************************/
        position.set(150, 130);
        dimensions.set(32, 32);

        mBtnMedium =
                new SGWidgetButton(SGWidget.Alignment.Left, position, dimensions, tileset, mGui) {
                    @Override
                    public boolean onUp(PointF position) {
                        mLblLoading.setIsVisible(true);
                        Intent intent = new Intent(getContext(), GameActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("difficulty", 10);
                        intent.putExtra("settings", bundle);
                        mActivity.startNextActivity(intent);
                        return true;
                    }
                };
        guiRoot.addChild("btnMedium", mBtnMedium);

        /*********************** Botão 'difícil' ***********************/
        position.set(150, 200);
        dimensions.set(32, 32);

        mBtnHard =
                new SGWidgetButton(SGWidget.Alignment.Left, position, dimensions, tileset, mGui) {
                    @Override
                    public boolean onUp(PointF position) {
                        mLblLoading.setIsVisible(true);
                        Intent intent = new Intent(getContext(), GameActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("difficulty", 20);
                        intent.putExtra("settings", bundle);
                        mActivity.startNextActivity(intent);
                        return true;
                    }
                };
        guiRoot.addChild("btnHard", mBtnHard);

        /********************** Fonte 8bit Operator *********************/
        SGImage imgFont8bit = imageFactory.createImage("fonts/8bit_operator_white.png");
        Point fontTilesNum = new Point(16, 16);
        SGTileset fontTileset = new SGTileset(imgFont8bit, fontTilesNum, null);
        mFnt8bit = new SGFont(fontTileset, new PointF(16, 32));

        /**************** Rótulo 'nível de dificuldade' *****************/
        position.set(0, 10);
        mLblDifficultyLevel = new SGWidgetLabel(SGWidget.Alignment.Center, position, mFnt8bit,
                strDifficultyLevel);
        guiRoot.addChild("lblDifficultyLevel", mLblDifficultyLevel);

        /************************ Rótulo 'fácil' ***********************/
        position.set(190, 58);
        mLblEasy = new SGWidgetLabel(SGWidget.Alignment.Left, position, mFnt8bit, strEasy);
        guiRoot.addChild("lblEasy", mLblEasy);

        /********************** Rótulo 'médio' ***********************/
        position.set(190, 128);
        mLblMedium = new SGWidgetLabel(SGWidget.Alignment.Left, position, mFnt8bit, strMedium);
        guiRoot.addChild("lblMedium", mLblMedium);

        /********************** Rótulo 'difícil' *********************/
        position.set(190, 198);
        mLblHard = new SGWidgetLabel(SGWidget.Alignment.Left, position, mFnt8bit, strHard);
        guiRoot.addChild("lblHard", mLblHard);

        /******************** Rótulo 'carregando...' *****************/
        position.set(0, 268);
        mLblLoading = new SGWidgetLabel(SGWidget.Alignment.Center, position, mFnt8bit, strLoading);
        guiRoot.addChild("lblLoading", mLblLoading);
        mLblLoading.setIsVisible(false);

        /********************** Botão 'fácil' - rótulo **********************/
        tileset = new SGTileset(imageFactory.createImage("gui/button_placeholder.png"), tilesNum, null);
        position.set(mLblEasy.getAbsolutePosition());
        dimensions.set(mLblEasy.getDimensions());

        mBtnEasyText =
                new SGWidgetButton(SGWidget.Alignment.Left, position, dimensions, tileset, mGui)
                {
                    @Override
                    public boolean onDown(PointF position)
                    {
                        mBtnEasy.injectDown(mBtnEasy.getAbsolutePosition());
                        mGui.setCurrentButton(this);

                        return true;
                    }
                    @Override
                    public boolean onUp(PointF position)
                    {
                        mGui.setCurrentButton(mBtnEasy);
                        mBtnEasy.injectUp(mBtnEasy.getAbsolutePosition());

                        return true;
                    }
                };
        mBtnEasyText.setIsVisible(false);
        guiRoot.addChild("btnEasyText", mBtnEasyText);

        /********************** Botão 'médio' - rótulo **********************/
        tileset = new SGTileset(imageFactory.createImage("gui/button_placeholder.png"), tilesNum, null);
        position.set(mLblMedium.getAbsolutePosition());
        dimensions.set(mLblMedium.getDimensions());

        mBtnMediumText =
                new SGWidgetButton(SGWidget.Alignment.Left, position, dimensions, tileset, mGui)
                {
                    @Override
                    public boolean onDown(PointF position)
                    {
                        mBtnMedium.injectDown(mBtnMedium.getAbsolutePosition());
                        mGui.setCurrentButton(this);
                        return true;
                    }

                    @Override
                    public boolean onUp(PointF position)
                    {
                        mGui.setCurrentButton(mBtnMedium);
                        mBtnMedium.injectUp(mBtnMedium.getAbsolutePosition());
                        return true;
                    }
                };
        mBtnMediumText.setIsVisible(false);
        guiRoot.addChild("btnMediumText", mBtnMediumText);

        /******************** Botão 'difícil' - rótulo ********************/
        tileset = new SGTileset(imageFactory.createImage("gui/button_placeholder.png"), tilesNum, null);
        position.set(mLblHard.getAbsolutePosition());
        dimensions.set(mLblHard.getDimensions());

        mBtnHardText =
                new SGWidgetButton(SGWidget.Alignment.Left, position, dimensions, tileset, mGui)
                {
                    @Override
                    public boolean onDown(PointF position)
                    {
                        mBtnHard.injectDown(mBtnHard.getAbsolutePosition());
                        mGui.setCurrentButton(this);
                        return true;
                    }

                    @Override
                    public boolean onUp(PointF position)
                    {
                        mGui.setCurrentButton(mBtnHard);
                        mBtnHard.injectUp(mBtnHard.getAbsolutePosition());
                        return true;
                    }
                };
        mBtnHardText.setIsVisible(false);
        guiRoot.addChild("btnHardText", mBtnHardText);
    }

    @Override
    public void step(Canvas canvas, float elapsedTimeInSeconds)
    {
        SGRenderer renderer = getRenderer();

        int screenColor = Color.DKGRAY;
        int viewportColor = Color.BLACK;

        renderer.beginDrawing(canvas, screenColor, viewportColor);

        renderer.drawImage(mImgBackground, null, mImagePosition, mImageDimensions);
        mGui.render();

        renderer.endDrawing();
    }

    public SGGui getGui() { return mGui; }
}
