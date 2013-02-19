package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PriceSign extends Sprite {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_PRICESIGN;
	public static final float YOFFSET = 22;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public float mPrice;
	public Text mPriceText;
	
	// constructor
	public PriceSign(float x, float y, float price, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super((int) x - TEXTURE.getWidth() / 2, (int) y - TEXTURE.getHeight() / 2, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_HUD);
		mPrice = price;
		mCenterX = (int) x;
		mCenterY = (int) y;
		
		//create text field with price
		mPriceText = new Text(mCenterX, mCenterY - 5, TowerDefense.FONT_SMALL, "" + (int) mPrice, 3, getVertexBufferObjectManager());
		mPriceText.setZIndex(TowerDefense.ZINDEX_HUD + 1);
		mPriceText.setPosition(mCenterX - mPriceText.getWidth() / 2, mCenterY - 5);
		if (TowerDefense.mSceneManager.getCurrentLevel().mCoins < mPrice) mPriceText.setColor(0.8f, 0.2f, 0.2f);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mPriceText);
		
	}
	
	@Override
	public void onDetached() {
		mPriceText.setVisible(false);
		mPriceText.setTag(TowerDefense.TAG_DETACHABLE);
	}

}