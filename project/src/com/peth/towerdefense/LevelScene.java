package com.peth.towerdefense;

import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class LevelScene extends Scene {
	
	// globals
	public TowerDefense mGame;
	public VertexBufferObjectManager mVertexBufferObjectManager;
	public Background mBackground;
	public HUD mHUD;
	public int mWavesTotal;
	public int mWaveCurrent;
	public float mWaveDelay;
	public int mEnemiesFinished = -1;
	public int mEnemiesTotal = 0;
	public int mCoins;
	public int mHealth;
	public ArrayList<SpawnPoint> mSpawnPoints = new ArrayList<SpawnPoint>();
	public ArrayList<Integer> mAvailableTowers = new ArrayList<Integer>();
	public ArrayList<Enemy> mCurrentEnemies = new ArrayList<Enemy>();
	public IUpdateHandler mUpdateHandler;
	public WaveTimer mWaveTimer;
	public SelectionWheel mSelectionWheel;
	public Music mMusic;
	public boolean mStarted;
	public boolean mPaused;
	public boolean mEnded;
	public IEntity mSelection;
	
	// constructor
	public LevelScene() {
	}
	
	// methods
	public void init() {
	}
	
	public void start() {
		
		this.mStarted = true;
		this.playMusic(TowerDefense.MUSIC_WAR);
		this.mWaveTimer = new WaveTimer(mWaveDelay);
		this.registerUpdateHandler(this.mWaveTimer);
		
	}
	
	public void detachGarbage() {
		
		for (int i = 0; i < this.getChildCount(); i++) {
			IEntity child = this.getChildByIndex(i);
			if (child != null && child.getTag() == TowerDefense.TAG_DETACHABLE) {
				this.detachChild(child);
			}
		}
		
	}
	
	public void getDamage(int damage) {
		this.mHealth -= damage;
	}
	
	public boolean isDead() {
		
		if (this.mHealth <= 0) {
			this.stop();
			Sprite defeatSprite = new Sprite(TowerDefense.CAMERA_WIDTH/2 - TowerDefense.TEXTURE_DEFEAT.getWidth()/2, TowerDefense.CAMERA_HEIGHT/2 - TowerDefense.TEXTURE_DEFEAT.getHeight()/2, TowerDefense.TEXTURE_DEFEAT, mVertexBufferObjectManager);
			defeatSprite.setZIndex(TowerDefense.ZINDEX_HUD + 12);
			this.attachChild(defeatSprite);
			return true;
		}
		return false;
		
	}
	
	public void stop() {
		for (int i = 0; i < this.getChildCount(); i++) {
			IEntity child = this.getChildByIndex(i);
			if (child instanceof Enemy) {
				((Enemy) child).mState = Enemy.STATE_DEAD;
				child.setTag(TowerDefense.TAG_DETACHABLE);
			} else if (child instanceof SpawnPoint) {
				((SpawnPoint) child).mActive = false;
			} else if (child instanceof Round || child instanceof SelectionWheel || child instanceof Option) {
				child.setTag(TowerDefense.TAG_DETACHABLE);
			} else if (child instanceof BasePoint || child instanceof Tower) {
				this.unregisterTouchArea((ITouchArea) child);
			}
		}
		this.mWaveTimer.mActive = false;
		this.stopMusic();
		this.mEnded = true;
	}
	
	public void checkVictory() {
		
		if (!isDead() && this.mEnemiesFinished == this.mEnemiesTotal) {
			this.stop();
			Sprite victorySprite = new Sprite(TowerDefense.CAMERA_WIDTH/2 - TowerDefense.TEXTURE_VICTORY.getWidth()/2, TowerDefense.CAMERA_HEIGHT/2 - TowerDefense.TEXTURE_VICTORY.getHeight()/2, TowerDefense.TEXTURE_VICTORY, mVertexBufferObjectManager);
			victorySprite.setZIndex(TowerDefense.ZINDEX_HUD + 12);
			this.attachChild(victorySprite);
			TowerDefense.SOUND_VICTORY.play();
		}
		
	}
	
	public void playMusic(Music music) {
		if (this.mMusic != null) {
			this.mMusic.stop();
		}
		this.mMusic = music;
		this.mMusic.play();
	}
	
	public void stopMusic() {
		this.mMusic.stop();
		this.mMusic = null;
	}
	
	public void unselect() {
		this.hideSelectionWheel();
		this.mHUD.hideInfo();
		if (this.mSelection != null) {
			if (this.mSelection instanceof Tower) {
				((Tower) this.mSelection).mRangeCircle.setVisible(false);
			}
		}
		this.mSelection = null;
	}
	
	public void hideSelectionWheel() {
		
		if (this.mSelectionWheel != null) {
			this.mSelectionWheel.hide();
			this.mSelectionWheel = null;
		}
		
	}
	
	class Update implements Runnable {

		@Override
        public void run() {
        	mHUD.update();
        	sortChildren();
        	detachGarbage();
        	if (!mEnded) {
        		checkVictory();
        	}
        	mGame.getEngine().runOnUpdateThread(new Update());
        }
		
	}
	
}

class Level1Scene extends LevelScene {
	
	// constants
	public static final int STARTING_HEALTH = 20;
	public static final int STARTING_COINS = 2065;
	public static final float WAVE_DELAY = 40; // in seconds
	
	// globals
	public WayPoint mWayPoint1;
	public WayPoint mWayPoint2;
	public WayPoint mWayPoint3;
	public WayPoint mWayPoint4;
	public WayPoint mWayPoint5;
	public WayPoint mWayPoint6;
	public WayPoint mWayPoint7;
	public WayPoint mWayPoint8;
	public WayPoint mWayPoint9;
	public WayPoint mWayPoint10;
	public WayPoint mWayPoint11;
	public WayPoint mWayPoint12;
	public WayPoint mWayPoint13;
	public WayPoint mWayPoint14;
	public WayPoint mWayPoint15;
	public WayPoint mWayPoint16;
	public WayPoint mWayPoint17;
	public WayPoint mWayPoint18;
	public WayPoint mWayPoint19;
	public WayPoint mWayPoint20;
	public WayPoint mWayPoint21;
	public WayPoint mWayPoint22;
	public WayPoint mWayPoint23;
	public WayPoint mWayPoint24;
	public WayPoint mWayPoint25;
	public WayPoint mWayPoint26;
	public WayPoint mWayPoint27;
	public WayPoint mWayPoint28;
	public WayPoint mWayPoint29;
	public WayPoint mWayPoint30;
	public ArrayList<WayPoint> mPath1;
	public ArrayList<Integer> mWave1 = new ArrayList<Integer>();;
	public ArrayList<Integer> mWave2 = new ArrayList<Integer>();;
	public ArrayList<Integer> mWave3 = new ArrayList<Integer>();;
	public ArrayList<Integer> mWave4 = new ArrayList<Integer>();;
	public ArrayList<Integer> mWave5 = new ArrayList<Integer>();;
	public ArrayList<ArrayList<Integer>> mWaveSet1;
	public SpawnPoint mSpawnPoint1;
	public SpawnPoint mSpawnPoint2;
	public BasePoint mBasePoint1;
	public BasePoint mBasePoint2;
	public BasePoint mBasePoint3;
	public BasePoint mBasePoint4;
	public BasePoint mBasePoint5;
	public BasePoint mBasePoint6;
	public BasePoint mBasePoint7;
	public BasePoint mBasePoint8;
	
	public Level1Scene(TowerDefense game, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// set game variables
		this.mGame = game;
		this.mCoins = STARTING_COINS;
		this.mHealth = STARTING_HEALTH;
		this.mAvailableTowers.add(Tower.TOWER_TEST);
		this.mAvailableTowers.add(Tower.TOWER_SLOW);
		this.mAvailableTowers.add(Tower.TOWER_FIRE);
		this.mAvailableTowers.add(Tower.TOWER_BOMB);
		this.mVertexBufferObjectManager = pVertexBufferObjectManager;
		this.mWaveDelay = WAVE_DELAY;
		
	}
	
	@Override
	public void init() {
		
		// set touch area order
		this.setOnAreaTouchTraversalFrontToBack();
		
		// create background
		mBackground = new Background(0, 0, TowerDefense.BACKGROUND_LEVEL_1, mVertexBufferObjectManager);
		
		// instantiate and place WayPoints
		this.mWayPoint1 = new WayPoint(375, 95, mVertexBufferObjectManager);
		this.mWayPoint2 = new WayPoint(364, 122, mVertexBufferObjectManager);
		this.mWayPoint3 = new WayPoint(345, 147, mVertexBufferObjectManager);
		this.mWayPoint4 = new WayPoint(318, 161, mVertexBufferObjectManager);
		this.mWayPoint5 = new WayPoint(251, 182, mVertexBufferObjectManager);
		this.mWayPoint6 = new WayPoint(216, 199, mVertexBufferObjectManager);
		this.mWayPoint7 = new WayPoint(197, 225, mVertexBufferObjectManager);
		this.mWayPoint8 = new WayPoint(189, 257, mVertexBufferObjectManager);
		this.mWayPoint9 = new WayPoint(191, 285, mVertexBufferObjectManager);
		this.mWayPoint10 = new WayPoint(207, 316, mVertexBufferObjectManager);
		this.mWayPoint11 = new WayPoint(229, 335, mVertexBufferObjectManager);
		this.mWayPoint12 = new WayPoint(256, 344, mVertexBufferObjectManager);
		this.mWayPoint13 = new WayPoint(291, 349, mVertexBufferObjectManager);
		this.mWayPoint14 = new WayPoint(377, 347, mVertexBufferObjectManager);
		this.mWayPoint15 = new WayPoint(406, 341, mVertexBufferObjectManager);
		this.mWayPoint16 = new WayPoint(435, 336, mVertexBufferObjectManager);
		this.mWayPoint17 = new WayPoint(460, 338, mVertexBufferObjectManager);
		this.mWayPoint18 = new WayPoint(526, 360, mVertexBufferObjectManager);
		this.mWayPoint19 = new WayPoint(553, 363, mVertexBufferObjectManager);
		this.mWayPoint20 = new WayPoint(576, 356, mVertexBufferObjectManager);
		this.mWayPoint21 = new WayPoint(596, 340, mVertexBufferObjectManager);
		this.mWayPoint22 = new WayPoint(636, 300, mVertexBufferObjectManager);
		this.mWayPoint23 = new WayPoint(662, 282, mVertexBufferObjectManager);
		this.mWayPoint24 = new WayPoint(687, 275, mVertexBufferObjectManager);
		this.mWayPoint25 = new WayPoint(830, 275, mVertexBufferObjectManager);

		// define paths
		this.mPath1 = new ArrayList<WayPoint>();
		this.mPath1.add(this.mWayPoint1);
		this.mPath1.add(this.mWayPoint2);
		this.mPath1.add(this.mWayPoint3);
		this.mPath1.add(this.mWayPoint4);
		this.mPath1.add(this.mWayPoint5);
		this.mPath1.add(this.mWayPoint6);
		this.mPath1.add(this.mWayPoint7);
		this.mPath1.add(this.mWayPoint8);
		this.mPath1.add(this.mWayPoint9);
		this.mPath1.add(this.mWayPoint10);
		this.mPath1.add(this.mWayPoint11);
		this.mPath1.add(this.mWayPoint12);
		this.mPath1.add(this.mWayPoint13);
		this.mPath1.add(this.mWayPoint14);
		this.mPath1.add(this.mWayPoint15);
		this.mPath1.add(this.mWayPoint16);
		this.mPath1.add(this.mWayPoint17);
		this.mPath1.add(this.mWayPoint18);
		this.mPath1.add(this.mWayPoint19);
		this.mPath1.add(this.mWayPoint20);
		this.mPath1.add(this.mWayPoint21);
		this.mPath1.add(this.mWayPoint22);
		this.mPath1.add(this.mWayPoint23);
		this.mPath1.add(this.mWayPoint24);
		this.mPath1.add(this.mWayPoint25);

		// define waves
		for (int i = 0; i < 6; i++) this.mWave1.add(Enemy.ENEMY_TEST);
		for (int i = 0; i < 9; i++) this.mWave2.add(Enemy.ENEMY_TEST);
		for (int i = 0; i < 17; i++) this.mWave3.add(Enemy.ENEMY_TEST);
		for (int i = 0; i < 36; i++) this.mWave4.add(Enemy.ENEMY_TEST);
		for (int i = 0; i < 50; i++) this.mWave5.add(Enemy.ENEMY_TEST);
		
		// define wave sets
		this.mWaveSet1 = new ArrayList<ArrayList<Integer>>();
		this.mWaveSet1.add(this.mWave1);
		this.mWaveSet1.add(this.mWave2);
		this.mWaveSet1.add(this.mWave3);
		this.mWaveSet1.add(this.mWave4);
		this.mWaveSet1.add(this.mWave5);

		// instantiate and place SpawnPoints
		this.mSpawnPoint1 = new SpawnPoint(this.mWaveSet1, this.mPath1, 375, -30, mVertexBufferObjectManager);
		this.mSpawnPoints.add(this.mSpawnPoint1);

		// initialize the number of waves variables
		for (int i = 0; i < this.mSpawnPoints.size(); i++) {
			if (this.mSpawnPoints.get(i).mWaveSet.size() > this.mWavesTotal) {
				this.mWavesTotal = this.mSpawnPoints.get(i).mWaveSet.size();
			}
		}

		// initialize the number of enemies variables
		for (int i = 0; i < this.mSpawnPoints.size(); i++) {
			ArrayList<ArrayList<Integer>> waveSet = this.mSpawnPoints.get(i).mWaveSet;
			for (int j = 0; j < waveSet.size(); j++) {
				this.mEnemiesTotal += waveSet.get(j).size();
			}
		}
		this.mEnemiesFinished = 0;

		// instantiate and place BasePoints
		this.mBasePoint1 = new BasePoint(275, 110, mVertexBufferObjectManager);
		this.mBasePoint2 = new BasePoint(202, 133, mVertexBufferObjectManager);
		this.mBasePoint3 = new BasePoint(288, 232, mVertexBufferObjectManager);
		this.mBasePoint4 = new BasePoint(283, 291, mVertexBufferObjectManager);
		this.mBasePoint5 = new BasePoint(369, 290, mVertexBufferObjectManager);
		this.mBasePoint6 = new BasePoint(442, 388, mVertexBufferObjectManager);
		this.mBasePoint7 = new BasePoint(533, 303, mVertexBufferObjectManager);
		this.mBasePoint8 = new BasePoint(670, 363, mVertexBufferObjectManager);
		
		// set up the hud
		this.mHUD = new HUD(mVertexBufferObjectManager);
		
		// add custom update to the update thread
		mGame.getEngine().runOnUpdateThread(new Update());
		
		// play music
		this.playMusic(TowerDefense.MUSIC_PEACE);
		
	}
	
}