package com.peth.towerdefense;

import java.util.ArrayList;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Enemy extends Sprite {
	
	// speed constants
	public static final double SPEED_SLOW = 0.5;
	public static final double SPEED_NORMAL = 1;
	public static final double SPEED_FAST = 2;
	
	// state constants
	public static final int STATE_DEAD = 0;
	public static final int STATE_NORMAL = 1;
	public static final int STATE_SLOW = 2;
	public static final int DURATION_SLOW = 500;
	
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
	public int mScore;
	public int mCoins;
	public WayPoint mTarget;
	public int mCurrentTarget = 0;
	public int mState = STATE_NORMAL;
	public int mSlowCount = 0;
	
	public Enemy(ArrayList<WayPoint> path, float x, float y, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager) {
				
		// superconstructor
		super(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture, pVertexBufferObjectManager);
		
		// set variables
		mCenterX = getX() + (texture.getWidth() / 2);
		mCenterY = getY() + (texture.getHeight() / 2);
		mPath = path;
		mTarget = mPath.get(mCurrentTarget);
		mSpeedFactor = SPEED_NORMAL;
		
		// start moving to target
		Thread moveThread = new Thread(new MoveTask());
		moveThread.start();
		
	}
	
	class MoveTask implements Runnable {

		@Override
		public void run() {
			
			while (true) { // follow path
				
				while (true) { // take a step
					
					// end movement thread if dead
					if (mState == STATE_DEAD) {
						return;
					}
					
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
					setPosition(getX() + dX, getY() + dY);
					mCenterX += dX;
					mCenterY += dY;
					
					// then sleep for animation's sake
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
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
	
	public synchronized void die() {
		
		if (mState != STATE_DEAD) {
		
			// set state to dead
			mState = STATE_DEAD;
			
			// gain stuff
			TowerDefense.mLevel.mCoins += mCoins;
			TowerDefense.mLevel.mScore += mScore;
			
			// remove enemy
			setVisible(false);
			//TODO detach self
		}
		
	}
	
	public void finish() {
		
		// player takes damage
		TowerDefense.mLevel.getDamage(1);
		
		// remove enemy
		setVisible(false);
		//TODO detach self
		
	}
	
	public synchronized void hit(double damage, int effectCode) {
		
		// enemy takes damage
		mHealth -= damage;
		
		// set opacity (temporary form of health bar)
		setAlpha(Math.max((float) 0.1, (float) mHealth / mMaxHealth));
		
		// die if health is too low
		if (mHealth <= 0) {
			
			die();
			
		// else inflict the effect
		} else {
			
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
	
	public static float MAX_HEALTH = 80;
	public static float SPEED = 2;
	public static int COINS = 10;
	public static int POINTS = 10;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_ENEMY_TEST;
	
	public TestEnemy(ArrayList<WayPoint> path, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(path, x, y, TEXTURE, pVertexBufferObjectManager);
		
		// initialize variables
		mMaxHealth = MAX_HEALTH;
		mHealth = MAX_HEALTH;
		mSpeed = SPEED;
		mCoins = COINS;
		mScore = POINTS;
	}
	
}