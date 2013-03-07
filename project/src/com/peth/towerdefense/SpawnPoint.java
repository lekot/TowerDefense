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
	public static final int WAVEBUTTON_DELAY = 3000;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public ArrayList<ArrayList<Integer>> mWaveSet;
	public ArrayList<WayPoint> mPath;
	public boolean mActive = true;
	public WaveButton mWaveButton;
	
	public SpawnPoint(ArrayList<ArrayList<Integer>> waveSet, ArrayList<WayPoint> path, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
        
		// superconstructor
		super(x - (TEXTURE.getWidth() / 2), y - (TEXTURE.getHeight() / 2), TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mCenterX = getX() + (TEXTURE.getWidth() / 2);
		mCenterY = getY() + (TEXTURE.getHeight() / 2);
		mWaveSet = waveSet;
        mPath = path;
        mWaveButton = new WaveButton(this, getVertexBufferObjectManager());
        
        // set visibility
     	setVisible(false);
     	
     	// attach
     	attachChild(this);
        
    }
	
	public void launchWave(final int wave) {
		
		Thread waveThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				// hide wave button
				mWaveButton.hide();
				
				// get current wave
				ArrayList<Integer> currentWave = mWaveSet.get(wave);
				
				// spawn the enemies
				for (int i = 0; i < currentWave.size(); i++) {
					
					// check if the spawnpoint was deactivated
					if (!mActive) return;
					
					if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
					
						// spawn an enemy
						spawn(currentWave.get(i));
						
						// if this was not the last spawn
						if (i < currentWave.size() - 1) {
							
							// sleep to add delay between spawns
							try {
								Thread.sleep(SPAWN_DELAY);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
						} else {
							
							// if this was the last spawn, if this wasn't the last wave, show the wave timer again
							if (TowerDefense.mSceneManager.getCurrentLevel().mWaveCurrent < mWaveSet.size()) {
								try {
									Thread.sleep(WAVEBUTTON_DELAY);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								mWaveButton.show();
							}
						}
						
					} else {
						
						i--;
						
					}
					
				}
				
			}
		
		});
		
		waveThread.start();
		
	}
	
	// spawns the requested enemy
	public void spawn(int enemyCode) {
		
		// reset the wavetimer, so that the WAVE_DELAY interval only starts afer all enemies have been spawned
		//TODO this is a very crude solution. find something better, preferably inside of the WaveTimer class
		TowerDefense.mSceneManager.getCurrentLevel().mWaveTimer.reset();
		
		// determine which enemy was requested and spawn it
		switch (enemyCode) {
		case Enemy.ENEMY_TEST:
			new TestEnemy(mPath, mCenterX, mCenterY, getVertexBufferObjectManager());
			break;
		}
		
	}
	
}
