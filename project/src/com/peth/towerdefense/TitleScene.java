package com.peth.towerdefense;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class TitleScene extends Scene {
	
	public VertexBufferObjectManager mVertexBufferObjectManager;
	
	public TitleScene(VertexBufferObjectManager pVertexBufferObjectManager) {
		mVertexBufferObjectManager = pVertexBufferObjectManager;
	}
	
	public void init() {
		
		new Background(0, 0, TowerDefense.BACKGROUND_TITLE, mVertexBufferObjectManager);
		Sprite logo = new Sprite((int) (TowerDefense.CAMERA_WIDTH / 2 - TowerDefense.TEXTURE_LOGO.getWidth() / 2), 50, TowerDefense.TEXTURE_LOGO, mVertexBufferObjectManager);
		new TitleStartButton(TowerDefense.CAMERA_WIDTH / 2, 320, mVertexBufferObjectManager);
		logo.setZIndex(TowerDefense.ZINDEX_HUD);
		
		TowerDefense.mSceneManager.getCurrentScene().attachChild(logo);
		
	}
}
