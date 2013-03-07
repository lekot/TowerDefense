package com.peth.towerdefense;

import java.util.ArrayList;

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
		TowerDefense.mSceneManager.getCurrentLevel().registerTouchArea(this);
		
		// attach
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(this);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				if (mCurrentTower == null) { //TODO instead of using this check, unregister the basepoint's touch area when a tower is built on it, and re-register it when its tower is sold
					if (TowerDefense.mSceneManager.getCurrentLevel().mSelectionWheel != null && TowerDefense.mSceneManager.getCurrentLevel().mSelectionWheel.mBasePoint == this) {
						TowerDefense.mSceneManager.getCurrentLevel().unselect();
					} else {
						select();
					}
				}
			}
		}
        return true;
    }
	
	public void select() {
		
		TowerDefense.mSceneManager.getCurrentLevel().unselect();
		
		TowerDefense.mSceneManager.getCurrentLevel().mSelection = this;
		ArrayList<Integer> baseTowerOptions = new ArrayList<Integer>();
		baseTowerOptions.add(Option.BUILD_TOWER_TEST);
		baseTowerOptions.add(Option.BUILD_TOWER_SLOW);
		baseTowerOptions.add(Option.BUILD_TOWER_FIRE);
		baseTowerOptions.add(Option.LOCKED);
		TowerDefense.mSceneManager.getCurrentLevel().mSelectionWheel = new SelectionWheel(mCenterX, mCenterY, this, baseTowerOptions, getVertexBufferObjectManager());
		
	}
	
	public void sellTower() {
		if (mCurrentTower != null) {
			TowerDefense.SOUND_COINS.play();
			TowerDefense.mSceneManager.getCurrentLevel().mCoins += Math.ceil(mCurrentTower.mPrice * TowerDefense.SALE_RATIO);
			destroyTower();
		}
	}
	
	public void destroyTower() {
		if (mCurrentTower != null) {
			mCurrentTower.setVisible(false);
			mCurrentTower.setTag(TowerDefense.TAG_DETACHABLE);
			mCurrentTower = null;
			TowerDefense.mSceneManager.getCurrentLevel().unselect();
		}
	}
	
}