/*
package com.peth.towerdefense;

import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

public class Level extends Scene {

}

class Level1 extends Level {
	
	public Background mBackground;
	public int mWavesTotal;
	public int mWaveCurrent;
	public int mEnemiesFinished = -1;
	public int mEnemiesTotal = 0;
	public int mCoins;
	public int mHealth;
	public ArrayList<Integer> mAvailableTowers = new ArrayList<Integer>();
	public ArrayList<Enemy> mCurrentEnemies = new ArrayList<Enemy>();
	private IUpdateHandler mUpdateHandler;
	public WaveTimer mWaveTimer;
	public SelectionWheel mSelectionWheel;
	public Music mMusic;
	public boolean mStarted;
	public boolean mEnded;
	public IEntity mSelection;
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
	public ArrayList<SpawnPoint> mSpawnPoints = new ArrayList<SpawnPoint>();
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
	
	@Override
	protected Scene onCreateScene() {
		
		// set touch area order
		mLevel.mScene.setOnAreaTouchTraversalFrontToBack();
		
		// create background
		mBackground = new Background(0, 0, TowerDefense.BACKGROUND_LEVEL_1, getVertexBufferObjectManager());
		
		// instantiate and place WayPoints
		mLevel.mWayPoint1 = new WayPoint(375, 95, getVertexBufferObjectManager());
		mLevel.mWayPoint2 = new WayPoint(364, 122, getVertexBufferObjectManager());
		mLevel.mWayPoint3 = new WayPoint(345, 147, getVertexBufferObjectManager());
		mLevel.mWayPoint4 = new WayPoint(318, 161, getVertexBufferObjectManager());
		mLevel.mWayPoint5 = new WayPoint(251, 182, getVertexBufferObjectManager());
		mLevel.mWayPoint6 = new WayPoint(216, 199, getVertexBufferObjectManager());
		mLevel.mWayPoint7 = new WayPoint(197, 225, getVertexBufferObjectManager());
		mLevel.mWayPoint8 = new WayPoint(189, 257, getVertexBufferObjectManager());
		mLevel.mWayPoint9 = new WayPoint(191, 285, getVertexBufferObjectManager());
		mLevel.mWayPoint10 = new WayPoint(207, 316, getVertexBufferObjectManager());
		mLevel.mWayPoint11 = new WayPoint(229, 335, getVertexBufferObjectManager());
		mLevel.mWayPoint12 = new WayPoint(256, 344, getVertexBufferObjectManager());
		mLevel.mWayPoint13 = new WayPoint(291, 349, getVertexBufferObjectManager());
		mLevel.mWayPoint14 = new WayPoint(377, 347, getVertexBufferObjectManager());
		mLevel.mWayPoint15 = new WayPoint(406, 341, getVertexBufferObjectManager());
		mLevel.mWayPoint16 = new WayPoint(435, 336, getVertexBufferObjectManager());
		mLevel.mWayPoint17 = new WayPoint(460, 338, getVertexBufferObjectManager());
		mLevel.mWayPoint18 = new WayPoint(526, 360, getVertexBufferObjectManager());
		mLevel.mWayPoint19 = new WayPoint(553, 363, getVertexBufferObjectManager());
		mLevel.mWayPoint20 = new WayPoint(576, 356, getVertexBufferObjectManager());
		mLevel.mWayPoint21 = new WayPoint(596, 340, getVertexBufferObjectManager());
		mLevel.mWayPoint22 = new WayPoint(636, 300, getVertexBufferObjectManager());
		mLevel.mWayPoint23 = new WayPoint(662, 282, getVertexBufferObjectManager());
		mLevel.mWayPoint24 = new WayPoint(687, 275, getVertexBufferObjectManager());
		mLevel.mWayPoint25 = new WayPoint(830, 275, getVertexBufferObjectManager());

		// define paths
		mLevel.mPath1 = new ArrayList<WayPoint>();
		mLevel.mPath1.add(mLevel.mWayPoint1);
		mLevel.mPath1.add(mLevel.mWayPoint2);
		mLevel.mPath1.add(mLevel.mWayPoint3);
		mLevel.mPath1.add(mLevel.mWayPoint4);
		mLevel.mPath1.add(mLevel.mWayPoint5);
		mLevel.mPath1.add(mLevel.mWayPoint6);
		mLevel.mPath1.add(mLevel.mWayPoint7);
		mLevel.mPath1.add(mLevel.mWayPoint8);
		mLevel.mPath1.add(mLevel.mWayPoint9);
		mLevel.mPath1.add(mLevel.mWayPoint10);
		mLevel.mPath1.add(mLevel.mWayPoint11);
		mLevel.mPath1.add(mLevel.mWayPoint12);
		mLevel.mPath1.add(mLevel.mWayPoint13);
		mLevel.mPath1.add(mLevel.mWayPoint14);
		mLevel.mPath1.add(mLevel.mWayPoint15);
		mLevel.mPath1.add(mLevel.mWayPoint16);
		mLevel.mPath1.add(mLevel.mWayPoint17);
		mLevel.mPath1.add(mLevel.mWayPoint18);
		mLevel.mPath1.add(mLevel.mWayPoint19);
		mLevel.mPath1.add(mLevel.mWayPoint20);
		mLevel.mPath1.add(mLevel.mWayPoint21);
		mLevel.mPath1.add(mLevel.mWayPoint22);
		mLevel.mPath1.add(mLevel.mWayPoint23);
		mLevel.mPath1.add(mLevel.mWayPoint24);
		mLevel.mPath1.add(mLevel.mWayPoint25);

		// define waves
		for (int i = 0; i < 6; i++) {
			mLevel.mWave1.add(Enemy.ENEMY_TEST);
		}
		for (int i = 0; i < 9; i++) {
			mLevel.mWave2.add(Enemy.ENEMY_TEST);
		}
		for (int i = 0; i < 17; i++) {
			mLevel.mWave3.add(Enemy.ENEMY_TEST);
		}
		for (int i = 0; i < 36; i++) {
			mLevel.mWave4.add(Enemy.ENEMY_TEST);
		}
		for (int i = 0; i < 50; i++) {
			mLevel.mWave5.add(Enemy.ENEMY_TEST);
		}
		
		// define wave sets
		mLevel.mWaveSet1 = new ArrayList<ArrayList<Integer>>();
		mLevel.mWaveSet1.add(mLevel.mWave1);
		mLevel.mWaveSet1.add(mLevel.mWave2);
		mLevel.mWaveSet1.add(mLevel.mWave3);
		mLevel.mWaveSet1.add(mLevel.mWave4);
		mLevel.mWaveSet1.add(mLevel.mWave5);

		// instantiate and place SpawnPoints
		mLevel.mSpawnPoint1 = new SpawnPoint(mLevel.mWaveSet1, mLevel.mPath1, 375, -30, getVertexBufferObjectManager());
		mLevel.mSpawnPoints.add(mLevel.mSpawnPoint1);

		// initialize the number of waves variables
		for (int i = 0; i < mLevel.mSpawnPoints.size(); i++) {
			if (mLevel.mSpawnPoints.get(i).mWaveSet.size() > mLevel.mWavesTotal) {
				mLevel.mWavesTotal = mLevel.mSpawnPoints.get(i).mWaveSet.size();
			}
		}

		// initialize the number of enemies variables
		for (int i = 0; i < mLevel.mSpawnPoints.size(); i++) {
			ArrayList<ArrayList<Integer>> waveSet = mLevel.mSpawnPoints.get(i).mWaveSet;
			for (int j = 0; j < waveSet.size(); j++) {
				mLevel.mEnemiesTotal += waveSet.get(j).size();
			}
		}
		mLevel.mEnemiesFinished = 0;

		// instantiate and place BasePoints
		mLevel.mBasePoint1 = new BasePoint(275, 110, getVertexBufferObjectManager());
		mLevel.mBasePoint2 = new BasePoint(202, 133, getVertexBufferObjectManager());
		mLevel.mBasePoint3 = new BasePoint(288, 232, getVertexBufferObjectManager());
		mLevel.mBasePoint4 = new BasePoint(283, 291, getVertexBufferObjectManager());
		mLevel.mBasePoint5 = new BasePoint(369, 290, getVertexBufferObjectManager());
		mLevel.mBasePoint6 = new BasePoint(442, 388, getVertexBufferObjectManager());
		mLevel.mBasePoint7 = new BasePoint(533, 303, getVertexBufferObjectManager());
		mLevel.mBasePoint8 = new BasePoint(670, 363, getVertexBufferObjectManager());
		
		// set up the hud
		mHUD = new HUD(getVertexBufferObjectManager());
		
		// register update handler
		mLevel.mUpdateHandler = new IUpdateHandler() {
			
	        @Override
	        public void onUpdate(float pSecondsElapsed) {
	        	mHUD.update();
	        	mLevel.mScene.sortChildren();
	        	detachGarbage();
	        	if (!mLevel.mEnded) {
	        		checkVictory();
	        	}
	        }

	        @Override
	        public void reset() {
	        	
	        }
	        
		};
		mLevel.mScene.registerUpdateHandler(mLevel.mUpdateHandler);
		
		// play music
		mLevel.playMusic(TowerDefense.MUSIC_PEACE);
		
		// return the scene
		return mLevel.mScene;
	}
	
	public void start() {
		
		mLevel.mStarted = true;
		mLevel.playMusic(TowerDefense.MUSIC_WAR);
		mLevel.mWaveTimer = new WaveTimer(WAVE_DELAY);
		mLevel.mScene.registerUpdateHandler(mLevel.mWaveTimer);
		
	}
	
	public void detachGarbage() {
		
		//TODO technically, this should be synchronized with the scene's list of children, but thi dont have acces to that. when
		// i extend Scene to make the (abstract) Level class, put this function in its body and change the syncronization to
		// lock the mChildren list. each level can then be a subclass of Level.
		synchronized (mLevel.mScene) {
		
			// loop through children
			for (int i = 0; i < mLevel.mScene.getChildCount(); i++) {
				
				// get referral to child
				IEntity child = mLevel.mScene.getChildByIndex(i);
				
				// detach child
				if (child.getTag() == TAG_DETACHABLE) {
					mLevel.mScene.detachChild(child);
				}
				
			}
		
		}
		
	}
	
	public void getDamage(int damage) {
		
		// deal damage
		mLevel.mHealth -= damage;
		
	}
	
	public boolean isDead() {
		
		if (mLevel.mHealth <= 0) {
			stop();
			Sprite defeatSprite = new Sprite(CAMERA_WIDTH/2 - TEXTURE_DEFEAT.getWidth()/2, CAMERA_HEIGHT/2 - 100 - TEXTURE_DEFEAT.getHeight()/2, TowerDefense.TEXTURE_DEFEAT, getVertexBufferObjectManager());
			defeatSprite.setZIndex(TowerDefense.ZINDEX_HUD);
			mLevel.mScene.attachChild(defeatSprite);
			return true;
		}
		return false;
		
	}
	
	public void stop() {
		for (int i = 0; i < mLevel.mScene.getChildCount(); i++) {
			IEntity child = mLevel.mScene.getChildByIndex(i);
			if (child instanceof Enemy) {
				((Enemy) child).mState = Enemy.STATE_DEAD;
				child.setTag(TAG_DETACHABLE);
			} else if (child instanceof SpawnPoint) {
				((SpawnPoint) child).mActive = false;
			} else if (child instanceof Round || child instanceof SelectionWheel || child instanceof Option) {
				child.setTag(TAG_DETACHABLE);
			} else if (child instanceof BasePoint || child instanceof Tower) {
				mLevel.mScene.unregisterTouchArea((ITouchArea) child);
			}
		}
		TowerDefense.mLevel.stopMusic();
		mLevel.mEnded = true;
	}
	
	public void checkVictory() {
		
		if (!isDead() && mLevel.mEnemiesFinished == mLevel.mEnemiesTotal) {
			stop();
			Sprite victorySprite = new Sprite(CAMERA_WIDTH/2 - TEXTURE_VICTORY.getWidth()/2, CAMERA_HEIGHT/2 - TEXTURE_VICTORY.getHeight()/2, TowerDefense.TEXTURE_VICTORY, getVertexBufferObjectManager());
			victorySprite.setZIndex(TowerDefense.ZINDEX_HUD);
			mLevel.mScene.attachChild(victorySprite);
			TowerDefense.SOUND_VICTORY.play();
		}
		
	}
	
	public void playMusic(Music music) {
		if (mLevel.mMusic != null) {
			mLevel.mMusic.stop();
		}
		mLevel.mMusic = music;
		mLevel.mMusic.play();
	}
	
	public void stopMusic() {
		mLevel.mMusic.stop();
		mLevel.mMusic = null;
	}
	
	public void unselect() {
		mLevel.hideSelectionWheel();
		mHUD.hideInfo();
		if (mLevel.mSelection != null) {
			if (mLevel.mSelection instanceof Tower) {
				((Tower) mLevel.mSelection).mRangeCircle.setVisible(false);
			}
		}
		mLevel.mSelection = null;
	}
	
	public void hideSelectionWheel() {
		
		if (mSelectionWheel != null) {
			mSelectionWheel.hide();
			mSelectionWheel = null;
		}
		
	}
	
}
*/