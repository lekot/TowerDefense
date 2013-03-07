package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

//TODO turn into AnimatedSprite
public class PauseButton extends Sprite {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_BUTTON_PAUSE;
	
	// globals
	Sprite mPauseBanner;
	
	// constructor
	public PauseButton(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x - Math.round(TEXTURE.getWidth()/2), y - Math.round(TEXTURE.getHeight()/2), TEXTURE, pVertexBufferObjectManager);
		
		setZIndex(TowerDefense.ZINDEX_HUD + 11);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(this);
		TowerDefense.mSceneManager.getCurrentLevel().registerTouchArea(this);
		mPauseBanner = new Sprite(TowerDefense.CAMERA_WIDTH/2 - TowerDefense.TEXTURE_PAUSE_BANNER.getWidth()/2, TowerDefense.CAMERA_HEIGHT/2 - TowerDefense.TEXTURE_PAUSE_BANNER.getHeight()/2, TowerDefense.TEXTURE_PAUSE_BANNER, getVertexBufferObjectManager());
		mPauseBanner.setVisible(false);
		mPauseBanner.setZIndex(TowerDefense.ZINDEX_HUD + 10);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mPauseBanner);
	}
	
	// super methods
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (!TowerDefense.mSceneManager.getCurrentLevel().mEnded) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				TowerDefense.mSceneManager.getCurrentLevel().mPaused = !TowerDefense.mSceneManager.getCurrentLevel().mPaused;
				mPauseBanner.setVisible(!mPauseBanner.isVisible());
			}
		}
        return true;
        
    }
	
}