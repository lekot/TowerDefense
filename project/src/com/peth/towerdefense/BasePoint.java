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
		TowerDefense.mLevel.mScene.registerTouchArea(this);
		
		// attach
		TowerDefense.mLevel.mScene.attachChild(this);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if (mCurrentTower == null) { //TODO instead of using this check, unregister the basepoint's touch area when a tower is built on it, and re-register it when its tower is sold
				if (TowerDefense.mLevel.mSelectionWheel != null && TowerDefense.mLevel.mSelectionWheel.mParent == this) {
					TowerDefense.mLevel.unselect();
				} else {
					select();
				}
			}
		}
        return true;
    }
	
	public void buildTower(int towerCode) {
		
		TowerDefense.SOUND_TOWER_BUILD.play();
		
		switch (towerCode) {
		case Tower.TOWER_TEST:
			mCurrentTower = new TestTower(this, getVertexBufferObjectManager());
			TowerDefense.mLevel.mScene.attachChild(mCurrentTower);
			TowerDefense.mLevel.mCoins -= TestTower.PRICE;
			break;
		case Tower.TOWER_SLOW:
			mCurrentTower = new SlowTower(this, getVertexBufferObjectManager());
			TowerDefense.mLevel.mScene.attachChild(mCurrentTower);
			TowerDefense.mLevel.mCoins -= SlowTower.PRICE;
			break;
		case Tower.TOWER_FIRE:
			mCurrentTower = new FireTower(this, getVertexBufferObjectManager());
			TowerDefense.mLevel.mScene.attachChild(mCurrentTower);
			TowerDefense.mLevel.mCoins -= FireTower.PRICE;
			break;
		}
		
	}
	
	public void select() {
		
		TowerDefense.mLevel.unselect();
		
		TowerDefense.mLevel.mSelection = this;
		ArrayList<Integer> baseTowerOptions = new ArrayList<Integer>();
		baseTowerOptions.add(Option.BUILD_TOWER_TEST);
		baseTowerOptions.add(Option.BUILD_TOWER_SLOW);
		baseTowerOptions.add(Option.BUILD_TOWER_FIRE);
		baseTowerOptions.add(Option.LOCKED);
		TowerDefense.mLevel.mSelectionWheel = new SelectionWheel(mCenterX, mCenterY, this, SelectionWheel.TYPE_BASE, baseTowerOptions, getVertexBufferObjectManager());
		
	}
	
}