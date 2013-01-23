package com.peth.towerdefense;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.peth.towerdefense.Enemy.MoveTask;

public class SpawnPoint extends Sprite {
	
	// constants
	public static final int SPAWN_DELAY = 500;
	
	// globals
	public ArrayList<ArrayList<Integer>> mWaveSet;
	int mCurrentWave;
	public int mWaveDelay;
	public ArrayList<WayPoint> mPath;
	public Timer waveTimer;
	public Timer spawnTimer;
	
	public SpawnPoint(ArrayList<ArrayList<Integer>> waveSet, int waveDelay, ArrayList<WayPoint> path, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
        
		// superconstructor
		super(x, y, TowerDefense.spawnPointTextureRegion, pVertexBufferObjectManager);
        
		// initialize variables
		mWaveSet = waveSet;
		mCurrentWave = 0;
        mWaveDelay = waveDelay;
        mPath = path;
        
        // start timer
        waveTimer = new Timer();
        waveTimer.schedule(new WaveTask(), mWaveDelay, mWaveDelay);
    }
	
	class WaveTask extends TimerTask {

		@Override
		public void run() {
			
			if (mCurrentWave < mWaveSet.size()) {
				
				// get current wave
				ArrayList<Integer> currentWave = mWaveSet.get(mCurrentWave);
				
				// spawn the enemies
				for (int i = 0; i < currentWave.size(); i++) {
					
					// spawn an enemy
					spawn(currentWave.get(i));
					
					// sleep to add delay between spawns
					try {
						Thread.sleep(SPAWN_DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				mCurrentWave++;
				
			} else {
				
				// the SpawnPoint has launched all its waves, deactivate the timer
				waveTimer.cancel();
			}
			
		}
		
	}
	
	// spawns the requested enemy
	public void spawn(int enemyCode) {
		
		// determine which enemy was requested and spawn it
		switch (enemyCode) {
			case TowerDefense.ENEMY_TEST:
				Enemy enemy = new Enemy(mPath, getX(), getY(), getVertexBufferObjectManager());
				TowerDefense.scene.attachChild(enemy);
				break;
		}
		 
	}
	
}
