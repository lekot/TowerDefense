package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Background extends Sprite {
	
	// constructor
	public Background(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		
		setZIndex(TowerDefense.ZINDEX_BACKGROUND);
		if (TowerDefense.mSceneManager.getCurrentScene() instanceof LevelScene) TowerDefense.mSceneManager.getCurrentLevel().registerTouchArea(this);
		TowerDefense.mSceneManager.getCurrentScene().attachChild(this);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			TowerDefense.mSceneManager.getCurrentLevel().unselect();
		}
		
        return true;
    }

}
