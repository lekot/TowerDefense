package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Background extends Sprite {
	
	// constructor
	public Background(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// super constructor
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_BACKGROUND);
		
		// register touch area (used for unselecting things)
		TowerDefense.mLevel.mScene.registerTouchArea(this);
		
		// attach
		TowerDefense.mLevel.mScene.attachChild(this);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			TowerDefense.mLevel.unselect();
		}
		
        return true;
    }

}
