package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class Tower extends Sprite {
	
	// round constants
	public static final int ROUND_TEST = 0;
	public static final int ROUND_SLOW = 1;
	
	// scan method constants
	public static final int SCAN_FIRST = 0;
	public static final int SCAN_FIRST_NOT_SLOW = 1;
	
	// globals
	public int mPrice;
	public float mRange;
	public int mRound;
	public int mDelay;
	public int mScanMethod;
	public Enemy mTarget;
	
	// constructor
	public Tower(float x, float y, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, texture, pVertexBufferObjectManager);
		
		// start scanning for enemies
		Thread scanThread = new Thread(new ScanTask());
		scanThread.start();
		
	}
	
	// scan task to be run on seperate thread
	class ScanTask implements Runnable {

		@Override
		public void run() {
			
			while (true) {
				
				scan(mScanMethod);
				
				if (mTarget != null) {
					
					if (mTarget.mState == Enemy.STATE_DEAD) {
						
						mTarget = null;
						
					} else {
						
						if (inRange(mTarget)) {
							
							// rotate sprite towards enemy
							setRotation(getDirection(mTarget));
							
							// fire!
							fireRound(mRound);
							
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
			
		}
		
	}
	
	// checks all enemies to see if one is in range
	public void scan(int scanMethod) {
		
		switch(scanMethod) {
		
		case SCAN_FIRST:
			
			// loop through all enemies
			for (int i = 0; i < TowerDefense.spawnedEnemies.size(); i++) {
				
				Enemy potentialTarget = TowerDefense.spawnedEnemies.get(i);
				
				// check if enemy is targetable
				if (!potentialTarget.isDead() && inRange(potentialTarget)) {
					
					// set the enemy as the target and stop scanning
					mTarget = TowerDefense.spawnedEnemies.get(i);
					break;
					
				}
				
			}
			break;
		
		case SCAN_FIRST_NOT_SLOW:
			
			// loop through all enemies
			for (int i = 0; i < TowerDefense.spawnedEnemies.size(); i++) {
				
				Enemy potentialTarget = TowerDefense.spawnedEnemies.get(i);
				
				// check if enemy is targetable
				if (!potentialTarget.isDead() && inRange(potentialTarget) && !potentialTarget.isSlow()) {
					
					// set the enemy as the target and stop scanning
					mTarget = TowerDefense.spawnedEnemies.get(i);
					break;
					
				}
				
			}
			break;
			
		}
		
	}
	
	// takes an enemy and checks whether it is in range
	public boolean inRange(Enemy enemy) {
		
		float diffX = getX() - enemy.getX();
		float diffY = getY() - enemy.getY();
		double dist = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
		
		return (dist <= mRange);
		
	}
	
	// takes an enemy and calculates its direction in degrees
	public float getDirection(Enemy enemy) {
		return (float) (180 - Math.toDegrees(Math.atan2(enemy.getX() - getX(), enemy.getY() - getY())));
	}
	
	// creates a new round aimed at the current target
	public void fireRound(int roundCode) {
		
		switch (roundCode) {
		case ROUND_TEST:
			Round testRound = new TestRound(mTarget, getX(), getY(), getVertexBufferObjectManager());
			TowerDefense.scene.attachChild(testRound);
			break;
		case ROUND_SLOW:
			Round slowRound = new SlowRound(mTarget, getX(), getY(), getVertexBufferObjectManager());
			TowerDefense.scene.attachChild(slowRound);
			break;
		}
		
	}
	
}

class TestTower extends Tower {
	
	// constants
	public static final int PRICE = 120;
	public static final int RANGE = 120;
	public static final int DELAY = 250;
	public static final int ROUND = ROUND_TEST;
	public static final int SCAN_METHOD = SCAN_FIRST;
	public static final ITextureRegion TEXTURE = TowerDefense.towerTextureRegion;
	
	public TestTower(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPrice = PRICE;
		mRange = RANGE;
		mDelay = DELAY;
		mRound = ROUND;
		mScanMethod = SCAN_METHOD;
		
	}
	
}

class SlowTower extends Tower {
	
	// constants
	public static final int PRICE = 240;
	public static final int RANGE = 80;
	public static final int DELAY = 2000;
	public static final int ROUND = ROUND_SLOW;
	public static final int SCAN_METHOD = SCAN_FIRST_NOT_SLOW;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_TOWER_SLOW;
	
	public SlowTower(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPrice = PRICE;
		mRange = RANGE;
		mDelay = DELAY;
		mRound = ROUND;
		mScanMethod = SCAN_METHOD;
		
	}
	
}