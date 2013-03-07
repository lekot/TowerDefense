package com.peth.towerdefense;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class SceneManager {
	private static TowerDefense game;
	private static SceneManager sceneManager;
	private Scene mScene;
	private static VertexBufferObjectManager mVertexBufferObjectManager;

	public SceneManager(VertexBufferObjectManager pVertexBufferObjectManager) {
		mVertexBufferObjectManager = pVertexBufferObjectManager;
	}

	public void init(TowerDefense pGame) {
		SceneManager.game = pGame;
	}

	public SceneManager getManager() {
		if (game == null) throw new IllegalStateException("You must first initialize scenemanager class");
		if (sceneManager == null) return sceneManager = new SceneManager(mVertexBufferObjectManager);
		return sceneManager;
	}
	
	public void setTitleScene() {
		mScene = new TitleScene(mVertexBufferObjectManager);
		((TitleScene) mScene).init();
		game.getEngine().setScene(mScene);
	}
	
	public void setLevelScene(int level) {
		if (level == 1) {
			mScene = new Level1Scene(game, mVertexBufferObjectManager);
			((LevelScene) mScene).init();
		}
		else throw new IllegalStateException("Level " + level + " does not exist.");
		game.getEngine().setScene(mScene);
	}
	
	public Scene getCurrentScene() {
		return mScene;
	}
	
	public LevelScene getCurrentLevel() {
		return (LevelScene) mScene;
	}
}