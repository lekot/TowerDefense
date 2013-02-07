package com.peth.towerdefense;

import org.andengine.engine.handler.IUpdateHandler;

public class WaveTimer implements IUpdateHandler {

    // globals        
    private float mInterval;
    private float mSecondsElapsed;    
    
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
        this.mSecondsElapsed += pSecondsElapsed;
        if (this.mSecondsElapsed >= this.mInterval) {
            this.mSecondsElapsed -= this.mInterval;
            this.launchWave();
        }
    }
    
    @Override
    public void reset() {
        this.mSecondsElapsed = 0;
    }
    
    // methods
    public void nextWave() {
    	TowerDefense.mLevel.mCoins += Math.max(0, Math.round(mInterval - mSecondsElapsed));
    	TowerDefense.SOUND_COINS.play();
    	this.mSecondsElapsed = this.mInterval;
    }
    
	public void launchWave() {
		if (TowerDefense.mLevel.mWaveCurrent < TowerDefense.mLevel.mWavesTotal) {
			TowerDefense.SOUND_WAVE.play();
			TowerDefense.mLevel.mWaveCurrent++;
			for (int i = 0; i < TowerDefense.mLevel.mSpawnPoints.size(); i++) {
				TowerDefense.mLevel.mSpawnPoints.get(i).launchWave(TowerDefense.mLevel.mWaveCurrent - 1);
			}
		}
	}
	
}