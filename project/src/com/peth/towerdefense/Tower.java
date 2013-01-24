package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Tower extends Sprite {
	
	public int mPrice;
	public float mRange;
	public Round mRound;
	public int mDelay;
	public Enemy mTarget;
	
	public Tower(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TowerDefense.towerTextureRegion, pVertexBufferObjectManager);
		
		// get variables
		mRange = 120;
		mDelay = 1000;
		
		// start scanning for enemies
		Thread scanThread = new Thread(new ScanTask());
		scanThread.start();
		
	}
	
	class ScanTask implements Runnable {

		@Override
		public void run() {
			
			while (true) {
				
				if (mTarget == null) {
					
					scan();
					
				} else if (mTarget.mState == Enemy.STATE_DEAD) {
					
					mTarget = null;
					
				} else {
					
					if (inRange(mTarget)) {
						
						fireRound();
						try {
							Thread.sleep(mDelay);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					} else {
						
						mTarget = null;
						
					}
					
				}
			
			}
			
		}
		
		public void scan() {
			
			// loop through all enemies
			for (int i = 0; i < TowerDefense.currentEnemies.size(); i++) {
				
				// check if enemy is targetable
				if (inRange(TowerDefense.currentEnemies.get(i))) {
					
					// set the enemy as the target and stop scanning
					mTarget = TowerDefense.currentEnemies.get(i);
					break;
					
				}
				
			}
			
		}
		
		public boolean inRange(Enemy enemy) {
			
			float diffX = getX() - enemy.getX();
			float diffY = getY() - enemy.getY();
			double dist = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
			
			return (dist <= mRange);
			
		}
		
		public void fireRound() {
			
			Round round = new Round(mTarget, getX(), getY(), getVertexBufferObjectManager());
			TowerDefense.scene.attachChild(round);
			
		}
		
	}
	
}
