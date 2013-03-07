package com.peth.towerdefense;

import java.util.ArrayList;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Enemy extends Sprite {
	
	// enemy constants
	public static final int ENEMY_TEST = 0;
	
	// speed constants
	public static final double SPEED_SLOW = 0.5;
	public static final double SPEED_NORMAL = 1;
	public static final double SPEED_FAST = 2;
	
	// state constants
	public static final int STATE_DEAD = 0;
	public static final int STATE_NORMAL = 1;
	public static final int STATE_SLOW = 2;
	public static final int STATE_PAUSED = 3;
	public static final int DURATION_SLOW = 300;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public float mOffsetX = (float) (Math.random() * 20 - 10);
	public float mOffsetY = (float) (Math.random() * 20 - 10);
	public ArrayList<WayPoint> mPath;
	public float mMaxHealth;
	public float mHealth;
	public double mSpeed;
	public double mSpeedFactor;
	public int mCoins;
	public WayPoint mTarget;
	public int mCurrentTarget = 0;
	public int mState = STATE_NORMAL;
	public int mSlowCount = 0;
	public HealthBar mHealthBar;
	public Thread mLifeThread;
	
	// constructor
	public Enemy(ArrayList<WayPoint> path, float x, float y, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager) {
				
		// superconstructor
		super(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_ENEMIES);
		mCenterX = getX() + (texture.getWidth() / 2);
		mCenterY = getY() + (texture.getHeight() / 2);
		mPath = path;
		mTarget = mPath.get(mCurrentTarget);
		mSpeedFactor = SPEED_NORMAL;
		mHealthBar = new HealthBar(this, getVertexBufferObjectManager());
		
		// attach
		TowerDefense.mSceneManager.getCurrentLevel().mCurrentEnemies.add(this);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(this);
		
		// start moving to target
		mLifeThread = new Thread(new MoveTask());
		mLifeThread.start();
		
	}
	
	class MoveTask implements Runnable {

		@Override
		public void run() {
			
			while (true) { // while end of path is not reached, follow path
				
				while (true) { // while target is not reached, take a step
					
					// end movement thread if dead
					if (mState == STATE_DEAD) {
						return;
					}
					
					// if level is paused, skip the movement phase
					if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
					
						// deal with being slowed
						if (mState == STATE_SLOW) {
							mSlowCount++;
							if (mSlowCount == DURATION_SLOW) {
								mState = STATE_NORMAL;
								mSpeedFactor = SPEED_NORMAL;
								mSlowCount = 0;
							}
						}
						
						// calculate distance to targeted waypoint
						float distX = mTarget.mCenterX - (mCenterX + mOffsetX);
						float distY = mTarget.mCenterY - (mCenterY + mOffsetY);
						float dist = (float) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
						
						// if close enough to targeted waypoint, skip to acquiring next waypoint
						if (dist < mSpeed) {
							break;
						}
						
						// otherwise move in direction of targeted waypoint
						float dX = distX / dist * (float) mSpeed * (float) mSpeedFactor;
						float dY = distY / dist * (float) mSpeed * (float) mSpeedFactor;
						move(dX, dY);
						
						// then sleep for animation's sake
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
					
				}
				
				// enemy has reached the next waypoint
				if (mCurrentTarget < mPath.size()-1) {
					
					// target the next waypoint
					mCurrentTarget++;
					mTarget = mPath.get(mCurrentTarget);
					
				} else {
					
					finish();
					break;
					
				}
				
			}
			
		}
		
	}
	
	public void move(float dX, float dY) {
		
		setPosition(getX() + dX, getY() + dY);
		mCenterX += dX;
		mCenterY += dY;
		mHealthBar.follow();
		
	}
	
	public synchronized void die() {
		
		if (mState != STATE_DEAD) {
			
			// play death cry
			int random = (int) (Math.random() * TowerDefense.SOUND_ENEMY_DEATHCRY.size());
			TowerDefense.SOUND_ENEMY_DEATHCRY.get(random).play();
			
			// gain stuff
			TowerDefense.mSceneManager.getCurrentLevel().mCoins += mCoins;
			
			// remove enemy
			removeEnemy();
			
		}
		
	}
	
	public void removeEnemy() {
		mState = STATE_DEAD;
		TowerDefense.mSceneManager.getCurrentLevel().mEnemiesFinished++;
		
		setVisible(false);
		setTag(TowerDefense.TAG_DETACHABLE);
		
		synchronized(TowerDefense.mSceneManager.getCurrentLevel().mCurrentEnemies) {
			TowerDefense.mSceneManager.getCurrentLevel().mCurrentEnemies.remove(this);
		}
	}
	
	@Override
	public void onDetached() {
		mHealthBar.hide();
	}
	
	public void finish() {
		
		//TODO play a sound
		TowerDefense.mVibrator.vibrate(20);
		
		// player takes damage
		TowerDefense.mSceneManager.getCurrentLevel().getDamage(1);
		
		// remove enemy
		removeEnemy();
		
	}
	
	public synchronized void hit(double damage, int effectCode) {
		
		// enemy takes damage
		mHealth -= damage;
		
		// update health bar
		mHealthBar.update(mHealth / mMaxHealth);
		
		// die if health is too low
		if (mHealth <= 0) {
			die();
		} else {
			// else inflict the effect
			inflict(effectCode);
		}
		
	}
	
	public synchronized void inflict(int effectCode) {
		
		switch(effectCode) {
		case Round.EFFECT_SLOW:
			mState = STATE_SLOW;
			mSpeedFactor = SPEED_SLOW;
		}
		
	}
	
	public boolean isDead() {
		return (mState == STATE_DEAD);
	}
	
	public boolean isSlow() {
		return (mState == STATE_SLOW);
	}
	
}

class TestEnemy extends Enemy {
	
	public static float MAX_HEALTH = 45;
	public static float SPEED = (float) 1;
	public static int COINS = 3;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_ENEMY_TEST;
	
	public TestEnemy(ArrayList<WayPoint> path, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(path, x, y, TEXTURE, pVertexBufferObjectManager);
		
		// initialize variables
		mMaxHealth = MAX_HEALTH;
		mHealth = MAX_HEALTH;
		mSpeed = SPEED;
		mCoins = COINS;
		
	}
	
}