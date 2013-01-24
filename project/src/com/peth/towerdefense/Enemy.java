package com.peth.towerdefense;

import java.util.ArrayList;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Enemy extends Sprite {
	
	// constants
	public static final float HEALTH_NORMAL = 100;
	public static final double SPEED_SLOW = 0.5;
	public static final double SPEED_NORMAL = 1;
	public static final double SPEED_FAST = 2;
	public static final int STATE_DEAD = 0;
	public static final int STATE_NORMAL = 1;
	
	// globals
	public ArrayList<WayPoint> mPath;
	public float mMaxHealth;
	public float mHealth;
	public double mSpeed;
	public WayPoint mTarget;
	public int mCurrentTarget = 0;
	public int mCoins = 100;
	public int mScore = 100;
	public int mState = STATE_NORMAL;
	
	public Enemy(ArrayList<WayPoint> path, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
				
		// superconstructor
		super(x, y, TowerDefense.enemyTextureRegion, pVertexBufferObjectManager);
		
		// initialize variables
		mPath = path;
		mMaxHealth = HEALTH_NORMAL;
		mHealth = mMaxHealth;
		mSpeed = SPEED_FAST;
		mTarget = mPath.get(mCurrentTarget);
		
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
					
					// update location
					float diffX = mTarget.getX() - getX();
					float diffY = mTarget.getY() - getY();
					float dist = (float) Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
					float dX = diffX / dist * (float) mSpeed;
					float dY = diffY / dist * (float) mSpeed;
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
	
	public void die() {
		
		// set state to dead
		mState = STATE_DEAD;
		
		// gain stuff
		TowerDefense.mCoins += mCoins;
		TowerDefense.mScore += mScore;
		
		// remove enemy
		TowerDefense.currentEnemies.remove(TowerDefense.currentEnemies.indexOf(this));
		setVisible(false); //TODO change to TowerDefense.scene.detachChild(this) on update thread
		
	}
	
	public void finish() {
		
		// take damage
		TowerDefense.getDamage(1);
		
		// remove enemy
		TowerDefense.currentEnemies.remove(TowerDefense.currentEnemies.indexOf(this));
		setVisible(false); //TODO change to TowerDefense.scene.detachChild(this) on update thread
		
	}
	
	public void getDamage(float damage) {
		
		// take damage
		mHealth -= damage;
		
		// check if the enemy is dead
		if (mHealth <= 0) {
			die();
		}
		
	}
	
}