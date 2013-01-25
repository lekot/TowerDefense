package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BasePoint extends Sprite {
	
	// tower constants
	public static final int TOWER_TEST = 0;
	public static final int TOWER_SLOW = 1;
	
	// globals
	Tower currentTower = null;
	
	public BasePoint(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TowerDefense.basePointTextureRegion, pVertexBufferObjectManager);
		
		// register touch handler
		TowerDefense.scene.registerTouchArea(this);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			
			if (currentTower == null) {
				
				//TODO make a way to select which tower to build
				if (TowerDefense.mCoins < 240) {
					if (TowerDefense.mCoins >= TestTower.PRICE) {
						buildTower(TOWER_TEST);
						TowerDefense.mCoins -= TestTower.PRICE;
						TowerDefense.updateGUI();
					}
				} else {
					if (TowerDefense.mCoins >= SlowTower.PRICE) {
						buildTower(TOWER_SLOW);
						TowerDefense.mCoins -= SlowTower.PRICE;
						TowerDefense.updateGUI();
					}
				}
				
			} else {
				
				destroyTower();
				
			}
		}
		
        return true;
    }
	
	public void buildTower(int towerCode) {
		
		switch (towerCode) {
		case TOWER_TEST:
			currentTower = new TestTower(getX(), getY(), getVertexBufferObjectManager());
			TowerDefense.scene.attachChild(currentTower);
			break;
		case TOWER_SLOW:
			currentTower = new SlowTower(getX(), getY(), getVertexBufferObjectManager());
			TowerDefense.scene.attachChild(currentTower);
			break;
		}
		
	}
	
	public void destroyTower() {
		
		//TowerDefense.scene.detachChild(currentTower); //TODO needs to happen onUpdateThread
		//currentTower = null;
		
	}
	
}