package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BasePoint extends Sprite {
	
	// texture constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_BASEPOINT;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public Tower mCurrentTower = null;
	
	public BasePoint(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x - (TEXTURE.getWidth() / 2), y - (TEXTURE.getHeight() / 2), TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_BASEPOINTS);
		mCenterX = getX() + (TEXTURE.getWidth() / 2);
		mCenterY = getY() + (TEXTURE.getHeight() / 2);
		
		// register touch handler
		TowerDefense.mLevel.mScene.registerTouchArea(this);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if (mCurrentTower == null) { //TODO instead of using this check, unregister the basepoint's touch area when a tower is built on it, and re-register it when its tower is sold
				if (TowerDefense.mLevel.mSelectionWheel != null && TowerDefense.mLevel.mSelectionWheel.mParent == this) {
					TowerDefense.mLevel.mSelectionWheel.hide();
				} else {
					showSelectionWheel();
				}
			}
		}
        return true;
    }
	
	public void buildTower(int towerCode) {
		
		TowerDefense.SOUND_TOWER_BUILD.play();
		
		//TODO take money-check out of here and put it in the options check
		switch (towerCode) {
		case Tower.TOWER_TEST:
			if (TowerDefense.mLevel.mCoins >= TestTower.PRICE) {
				mCurrentTower = new TestTower(this, getVertexBufferObjectManager());
				TowerDefense.mLevel.mScene.attachChild(mCurrentTower);
				TowerDefense.mLevel.mCoins -= TestTower.PRICE;
			}
			break;
		case Tower.TOWER_SLOW:
			if (TowerDefense.mLevel.mCoins >= SlowTower.PRICE) {
				mCurrentTower = new SlowTower(this, getVertexBufferObjectManager());
				TowerDefense.mLevel.mScene.attachChild(mCurrentTower);
				TowerDefense.mLevel.mCoins -= SlowTower.PRICE;
			}
			break;
		case Tower.TOWER_FIRE:
			if (TowerDefense.mLevel.mCoins >= FireTower.PRICE) {
				mCurrentTower = new FireTower(this, getVertexBufferObjectManager());
				TowerDefense.mLevel.mScene.attachChild(mCurrentTower);
				TowerDefense.mLevel.mCoins -= FireTower.PRICE;
			}
			break;
		}
		
	}
	
	public void showSelectionWheel() {
		
		if (TowerDefense.mLevel.mSelectionWheel != null) TowerDefense.mLevel.mSelectionWheel.hide();
		TowerDefense.mLevel.mSelectionWheel = new SelectionWheel(mCenterX, mCenterY, this, SelectionWheel.TYPE_BASE, TowerDefense.mLevel.mAvailableTowers, getVertexBufferObjectManager());
		
	}
	
}