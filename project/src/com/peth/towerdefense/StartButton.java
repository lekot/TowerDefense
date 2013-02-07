package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

//TODO turn into AnimatedSprite
public class StartButton extends Sprite {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_HUD_START;
	
	// constructor
	public StartButton(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x - TEXTURE.getWidth(), y - TEXTURE.getHeight(), TEXTURE, pVertexBufferObjectManager);
		
		setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mLevel.mScene.attachChild(this);
		TowerDefense.mLevel.mScene.registerTouchArea(this);
		
	}
	
	// methods
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if (!TowerDefense.mLevel.mStarted) {
				TowerDefense.mLevel.start();
			}
		}
        return true;
        
    }
	
}