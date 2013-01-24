package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BasePoint extends Sprite {
	
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
				buildTower(TowerDefense.TOWER_TEST);
			} else {
				destroyTower();
			}
		}
		
        return true;
    }
	
	public void buildTower(int towerCode) {
		
		switch (towerCode) {
		case TowerDefense.TOWER_TEST:
			currentTower = new Tower(getX(), getY(), getVertexBufferObjectManager());
			TowerDefense.scene.attachChild(currentTower);
			break;
		}
		
	}
	
	public void destroyTower() {
		
		//TowerDefense.scene.detachChild(currentTower); //TODO needs to happen onUpdateThread
		//currentTower = null;
		
	}
	
}