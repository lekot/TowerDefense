package com.peth.towerdefense;

import java.util.ArrayList;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Enemy extends Sprite {
	
	// constants
	public static final float MAX_HEALTH = 100;
	public static final double SPEED_SLOW = 0.5;
	public static final double SPEED_NORMAL = 1;
	public static final double SPEED_FAST = 2;
	
	// globals
	public ArrayList<WayPoint> mPath;
	public float mMaxHealth;
	public float mHealth;
	public double mSpeed;
	public WayPoint mTarget;
	public int mCurrentTarget = 0;
	
	public Enemy(ArrayList<WayPoint> path, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
				
		// superconstructor
		super(x, y, TowerDefense.enemyTextureRegion, pVertexBufferObjectManager);
		
		// initialize variables
		mPath = path;
		mMaxHealth = MAX_HEALTH;
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
			
			while(true) {
				
				while (Math.abs(getX() - mTarget.getX()) > mSpeed || Math.abs(getY() - mTarget.getY()) > mSpeed) {
					
					// update location
					float diffX = mTarget.getX() - getX();
					float diffY = mTarget.getY() - getY();
					float dist = (float) Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
					float dX = diffX / dist * (float) mSpeed;
					float dY = diffY / dist * (float) mSpeed;
					setPosition(getX() + dX, getY() + dY);
					
					// sleep for animation's sake
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				// enemy has reached the next waypoint
				if (mCurrentTarget < mPath.size()-1) {
					
					// target the next waypoint
					mCurrentTarget++;
					mTarget = mPath.get(mCurrentTarget);
					
				} else {
					
					// enemy has reached end of path
					TowerDefense.getDamage(1);
					detachSelf();
					
				}
				
			}
			
		}
		
	}
	
}