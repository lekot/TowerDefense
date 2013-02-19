package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class TitleStartButton extends Sprite {
	
	// constructor
	public TitleStartButton(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		super((int) (x - TowerDefense.TEXTURE_BUTTON_START.getWidth() / 2), (int) (y - TowerDefense.TEXTURE_BUTTON_START.getHeight() / 2), TowerDefense.TEXTURE_BUTTON_START, pVertexBufferObjectManager);
		
		setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mSceneManager.getCurrentScene().attachChild(this);
		TowerDefense.mSceneManager.getCurrentScene().registerTouchArea(this);
		
	}
	
	// super methods
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			TowerDefense.mSceneManager.setLevelScene(1);
		}
        return true;
        
    }

}
