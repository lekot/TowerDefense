package com.peth.towerdefense;

import org.andengine.entity.scene.Scene;

public class SceneManager {
	private static TowerDefense game;
	private static SceneManager sceneManager;
	private Scene mScene;

	private SceneManager() {
	}

	public static void init(TowerDefense pGame) {
		SceneManager.game = pGame;
	}

	public static SceneManager getManager() {
		if (game == null) throw new IllegalStateException("You must first initialize scenemanager class");
		if (sceneManager == null) return sceneManager = new SceneManager();
		return sceneManager;
	}
	
	/* activate when TitleScreen class is made
	public void setTitleScreen() {
		mScene = new TitleScreen();
		game.getEngine().setScene(mScene);
	}
	
	public void setLevelScreen(int level) {
		if (level == 1) mScene = new Level1();
		else throw new IllegalStateException("Level " + level + " does not exist.");
		game.getEngine().setScene(mScene);
	}
	*/

	public Scene getCurrentScene(){
		return mScene;
	}
}