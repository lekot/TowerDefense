package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class WaveButton extends Sprite {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_BUTTON_WAVE;
	
	// constructor
	public WaveButton(SpawnPoint parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(0, 0, TEXTURE, pVertexBufferObjectManager);
		
		float[] position = getPosition(parent);
		setPosition(position[0] - TEXTURE.getWidth() / 2, position[1] - TEXTURE.getHeight() / 2);
		setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(this);
		TowerDefense.mSceneManager.getCurrentLevel().registerTouchArea(this);
		
	}
	
	// super methods
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if (!TowerDefense.mSceneManager.getCurrentLevel().mStarted) {
				TowerDefense.mSceneManager.getCurrentLevel().start();
			} else {
				TowerDefense.mSceneManager.getCurrentLevel().mWaveTimer.nextWave();
			}
		}
        return true;
        
    }
	
	// methods
	public float[] getPosition(SpawnPoint parent) {
		float[] position = new float[2];
		position[0] = Math.max(0 + 30, Math.min(parent.mCenterX, TowerDefense.CAMERA_WIDTH - 30));
		position[1] = Math.max(0 + 30, Math.min(parent.mCenterY, TowerDefense.CAMERA_HEIGHT - 30));
		return position;
	}
	
	public void hide() {
		setVisible(false);
		TowerDefense.mSceneManager.getCurrentLevel().unregisterTouchArea(this);
	}
	
	public void show() {
		setVisible(true);
		TowerDefense.mSceneManager.getCurrentLevel().registerTouchArea(this);
	}
	
}