package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BasePoint extends Sprite {
	
	// texture constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_BASEPOINT;
	
	// tower constants
	public static final int TOWER_TEST = 0;
	public static final int TOWER_SLOW = 1;
	public static final int TOWER_FIRE = 2;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public Tower mCurrentTower = null;
	public SelectionWheel mSelectionWheel;
	
	public BasePoint(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x - (TEXTURE.getWidth() / 2), y - (TEXTURE.getHeight() / 2), TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mCenterX = getX() + (TEXTURE.getWidth() / 2);
		mCenterY = getY() + (TEXTURE.getHeight() / 2);
		
		// register touch handler
		TowerDefense.mLevel.mScene.registerTouchArea(this);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (mCurrentTower == null) {
				if (mSelectionWheel == null) {
					showSelectionWheel();
				} else {
					hideSelectionWheel();
				}
			}
		}
        return true;
    }
	
	public void buildTower(int towerCode) {
		
		switch (towerCode) {
		case TOWER_TEST:
			if (TowerDefense.mLevel.mCoins >= TestTower.PRICE) {
				mCurrentTower = new TestTower(mCenterX, mCenterY, getVertexBufferObjectManager());
				TowerDefense.mLevel.mScene.attachChild(mCurrentTower);
				TowerDefense.mLevel.mCoins -= TestTower.PRICE;
			}
			break;
		case TOWER_SLOW:
			if (TowerDefense.mLevel.mCoins >= SlowTower.PRICE) {
				mCurrentTower = new SlowTower(mCenterX, mCenterY, getVertexBufferObjectManager());
				TowerDefense.mLevel.mScene.attachChild(mCurrentTower);
				TowerDefense.mLevel.mCoins -= SlowTower.PRICE;
			}
			break;
		case TOWER_FIRE:
			if (TowerDefense.mLevel.mCoins >= FireTower.PRICE) {
				mCurrentTower = new FireTower(mCenterX, mCenterY, getVertexBufferObjectManager());
				TowerDefense.mLevel.mScene.attachChild(mCurrentTower);
				TowerDefense.mLevel.mCoins -= FireTower.PRICE;
			}
			break;
		}
		
	}
	
	public void showSelectionWheel() {
		
		mSelectionWheel = new SelectionWheel(mCenterX, mCenterY, this, SelectionWheel.TYPE_BASE, TowerDefense.mLevel.mAvailableTowers, getVertexBufferObjectManager());
		TowerDefense.mLevel.mScene.attachChild(mSelectionWheel);
		
	}
	
	public void hideSelectionWheel() {
		
		if (mSelectionWheel != null) {
			mSelectionWheel.hide();
			mSelectionWheel = null;
		}
		
	}
	
}