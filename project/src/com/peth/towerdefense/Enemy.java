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
		super(x, y, texture, pVertexBufferObjectManager);
		
		// initialize variables
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
			
			while (true) {
				
				while (Math.abs(getX() - mTarget.getX()) > mSpeed || Math.abs(getY() - mTarget.getY()) > mSpeed) {
					
					// end movement thread if dead
					if (mState == STATE_DEAD) {
						return;
					}
					
					if (mState == STATE_SLOW) {
						mSlowCount++;
						if (mSlowCount == DURATION_SLOW) {
							mState = STATE_NORMAL;
							mSpeedFactor = SPEED_NORMAL;
							mSlowCount = 0;
						}
					}
					
					// update location
					float diffX = mTarget.getX() - getX();
					float diffY = mTarget.getY() - getY();
					float dist = (float) Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
					float dX = diffX / dist * (float) mSpeed * (float) mSpeedFactor;
					float dY = diffY / dist * (float) mSpeed * (float) mSpeedFactor;
					float newX = getX() + dX;
					float newY = getY() + dY;
					setPosition(newX, newY);
					
					// sleep for animation's sake
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
			TowerDefense.mCoins += mCoins;
			TowerDefense.mScore += mScore;
			
			// update gui
			TowerDefense.updateGUI();
			
			// remove enemy
			setVisible(false); //TODO change to TowerDefense.scene.detachChild(this) on update thread
		
		}
		
	}
	
	public void finish() {
		
		// take damage
		TowerDefense.getDamage(1);
		
		// update gui
		TowerDefense.updateGUI();
		
		// remove enemy
		setVisible(false); //TODO change to TowerDefense.scene.detachChild(this) on update thread
		
	}
	
	public synchronized void hit(float damage, int effectCode) {
		
		// take damage
		mHealth -= damage;
		
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
	public static final ITextureRegion TEXTURE = TowerDefense.enemyTextureRegion;
	
	public TestEnemy(ArrayList<WayPoint> path, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(path, x, y, TEXTURE, pVertexBufferObjectManager);
		
		// initialize variables
		mHealth = MAX_HEALTH;
		mSpeed = SPEED;
		mCoins = COINS;
		mScore = POINTS;
	}
	
}