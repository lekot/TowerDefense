package com.peth.towerdefense;

import org.andengine.engine.handler.IUpdateHandler;

public class WaveTimer implements IUpdateHandler {

    // globals        
    private float mInterval;
    private float mSecondsElapsed;
    public boolean mActive = true;
    
    // constructor
    public WaveTimer(float pInterval) {
    	this.mInterval = pInterval;
    	this.mSecondsElapsed = this.mInterval;
    }
    
    // getters and setters
    public void setInterval(final float pInterval) {
    	this.mInterval = pInterval;
    }
    
    // super methods
    @Override
    public void onUpdate(float pSecondsElapsed) {
    	
    	if (mActive) {
	    	if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
		        this.mSecondsElapsed += pSecondsElapsed;
		        if (this.mSecondsElapsed >= this.mInterval) {
		            this.mSecondsElapsed -= this.mInterval;
		            this.launchWave();
		        }
	    	}
    	}
    }
    
    @Override
    public void reset() {
        this.mSecondsElapsed = 0;
    }
    
    // methods
    public void nextWave() {
    	TowerDefense.mSceneManager.getCurrentLevel().mCoins += Math.max(0, Math.round(mInterval - mSecondsElapsed));
    	TowerDefense.SOUND_COINS.play();
    	this.mSecondsElapsed = this.mInterval;
    }
    
	public void launchWave() {
		if (TowerDefense.mSceneManager.getCurrentLevel().mWaveCurrent < TowerDefense.mSceneManager.getCurrentLevel().mWavesTotal) {
			TowerDefense.SOUND_WAVE.play();
			TowerDefense.mSceneManager.getCurrentLevel().mWaveCurrent++;
			for (int i = 0; i < TowerDefense.mSceneManager.getCurrentLevel().mSpawnPoints.size(); i++) {
				TowerDefense.mSceneManager.getCurrentLevel().mSpawnPoints.get(i).launchWave(TowerDefense.mSceneManager.getCurrentLevel().mWaveCurrent - 1);
			}
		}
	}
	
}