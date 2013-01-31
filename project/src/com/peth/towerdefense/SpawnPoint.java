package com.peth.towerdefense;

import java.util.ArrayList;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class SpawnPoint extends Sprite {
	
	// texture constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_SPAWNPOINT;
	
	// misc constants
	public static final int SPAWN_DELAY = 500;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public ArrayList<ArrayList<Integer>> mWaveSet;
	public ArrayList<WayPoint> mPath;
	public boolean mActive = true;
	
	public SpawnPoint(ArrayList<ArrayList<Integer>> waveSet, ArrayList<WayPoint> path, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
        
		// superconstructor
		super(x - (TEXTURE.getWidth() / 2), y - (TEXTURE.getHeight() / 2), TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mCenterX = getX() + (TEXTURE.getWidth() / 2);
		mCenterY = getY() + (TEXTURE.getHeight() / 2);
		mWaveSet = waveSet;
        mPath = path;
        
        // set visibility
     	setVisible(false);
        
    }
	
	public void launchWave(final int wave) {
		
		Thread waveThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				// get current wave
				ArrayList<Integer> currentWave = mWaveSet.get(wave);
				
				// spawn the enemies
				for (int i = 0; i < currentWave.size(); i++) {
					
					if (!mActive) break;
					
					// spawn an enemy
					spawn(currentWave.get(i));
					
					// sleep to add delay between spawns
					try {
						Thread.sleep(SPAWN_DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		
		});
		
		waveThread.start();
		
	}
	
	// spawns the requested enemy
	public void spawn(int enemyCode) {
		
		// determine which enemy was requested and spawn it
		switch (enemyCode) {
		case Enemy.ENEMY_TEST:
			Enemy enemy = new TestEnemy(mPath, mCenterX, mCenterY, getVertexBufferObjectManager());
			TowerDefense.mLevel.mCurrentEnemies.add(enemy);
			TowerDefense.mLevel.mScene.attachChild(enemy);
			break;
		}
		 
	}
	
}
