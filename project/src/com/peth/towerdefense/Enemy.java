package com.peth.towerdefense;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Enemy extends Sprite {
	
	// enemy constants
	public static final int ENEMY_TEST = 0;
	
	// speed constants
	public static final double SPEED_SLOW = 0.5;
	public static final double SPEED_NORMAL = 1;
	public static final double SPEED_FAST = 2;
	
	// state constants
	public static final int STATE_DEAD = 0;
	public static final int STATE_NORMAL = 1;
	public static final int STATE_SLOW = 2;
	public static final int DURATION_SLOW = 300;
	
	// health bar constants
	private static final float HEALTHBAR_WIDTH = 15;
	private static final float HEALTHBAR_HEIGHT = 1;
	private static final float HEALTHBAR_YOFFSET = 15;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public float mOffsetX = (float) (Math.random() * 20 - 10);
	public float mOffsetY = (float) (Math.random() * 20 - 10);
	public ArrayList<WayPoint> mPath;
	public float mMaxHealth;
	public float mHealth;
	public double mSpeed;
	public double mSpeedFactor;
	public int mCoins;
	public WayPoint mTarget;
	public int mCurrentTarget = 0;
	public int mState = STATE_NORMAL;
	public int mSlowCount = 0;
	public Rectangle mHealthBarBorder;
	public Rectangle mHealthBarBackground;
	public Rectangle mHealthBarForeground;
	public Thread mMoveThread;
	
	// constructor
	public Enemy(ArrayList<WayPoint> path, float x, float y, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager) {
				
		// superconstructor
		super(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_ENEMIES);
		mCenterX = getX() + (texture.getWidth() / 2);
		mCenterY = getY() + (texture.getHeight() / 2);
		mPath = path;
		mTarget = mPath.get(mCurrentTarget);
		mSpeedFactor = SPEED_NORMAL;
		mHealthBarBorder = new Rectangle(mCenterX - HEALTHBAR_WIDTH / 2 - 1, mCenterY - HEALTHBAR_YOFFSET - 1, HEALTHBAR_WIDTH + 2, HEALTHBAR_HEIGHT + 2, getVertexBufferObjectManager());
		mHealthBarBorder.setColor(0.3f, 0.3f, 0.3f);
		mHealthBarBorder.setZIndex(800);
		TowerDefense.mLevel.mScene.attachChild(mHealthBarBorder);
		mHealthBarBackground = new Rectangle(mCenterX - HEALTHBAR_WIDTH / 2, mCenterY - HEALTHBAR_YOFFSET, HEALTHBAR_WIDTH, HEALTHBAR_HEIGHT, getVertexBufferObjectManager());
		mHealthBarBackground.setColor(220f/255, 25f/255, 25f/255);
		mHealthBarBackground.setZIndex(801);
		TowerDefense.mLevel.mScene.attachChild(mHealthBarBackground);
		mHealthBarForeground = new Rectangle(mCenterX - HEALTHBAR_WIDTH / 2, mCenterY - HEALTHBAR_YOFFSET, HEALTHBAR_WIDTH, HEALTHBAR_HEIGHT, getVertexBufferObjectManager());
		mHealthBarForeground.setColor(100f/255, 220f/255, 20f/255);
		mHealthBarForeground.setZIndex(802);
		TowerDefense.mLevel.mScene.attachChild(mHealthBarForeground);
		
		// start moving to target
		mMoveThread = new Thread(new MoveTask());
		mMoveThread.start();
		
	}
	
	class MoveTask implements Runnable {

		@Override
		public void run() {
			
			while (true) { // follow path
				
				while (true) { // take a step
					
					// end movement thread if dead
					if (mState == STATE_DEAD) {
						return;
					}
					
					// deal with being slowed
					if (mState == STATE_SLOW) {
						mSlowCount++;
						if (mSlowCount == DURATION_SLOW) {
							mState = STATE_NORMAL;
							mSpeedFactor = SPEED_NORMAL;
							mSlowCount = 0;
						}
					}
					
					// calculate distance to targeted waypoint
					float distX = mTarget.mCenterX - (mCenterX + mOffsetX);
					float distY = mTarget.mCenterY - (mCenterY + mOffsetY);
					float dist = (float) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
					
					// if close enough to targeted waypoint, skip to acquiring next waypoint
					if (dist < mSpeed) {
						break;
					}
					
					// otherwise move in direction of targeted waypoint
					float dX = distX / dist * (float) mSpeed * (float) mSpeedFactor;
					float dY = distY / dist * (float) mSpeed * (float) mSpeedFactor;
					move(dX, dY);
					
					// then sleep for animation's sake
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
	
	public void move(float dX, float dY) {
		
		setPosition(getX() + dX, getY() + dY);
		mCenterX += dX;
		mCenterY += dY;
		mHealthBarBorder.setPosition(mHealthBarBorder.getX() + dX, mHealthBarBorder.getY() + dY);
		mHealthBarBackground.setPosition(mHealthBarBackground.getX() + dX, mHealthBarBackground.getY() + dY);
		mHealthBarForeground.setPosition(mHealthBarForeground.getX() + dX, mHealthBarForeground.getY() + dY);
		
	}
	
	public synchronized void die() {
		
		if (mState != STATE_DEAD) {
			
			// play death cry
			int random = (int) (Math.random() * TowerDefense.SOUND_ENEMY_DEATHCRY.size());
			TowerDefense.SOUND_ENEMY_DEATHCRY.get(random).play();
			
			// gain stuff
			TowerDefense.mLevel.mCoins += mCoins;
			
			// remove enemy
			removeEnemy();
			
		}
		
	}
	
	public void removeEnemy() {
		mState = STATE_DEAD;
		TowerDefense.mLevel.mEnemiesFinished++;
		
		setVisible(false);
		mHealthBarBorder.setVisible(false);
		mHealthBarBackground.setVisible(false);
		mHealthBarForeground.setVisible(false);
		
		setTag(TowerDefense.TAG_DETACHABLE);
		mHealthBarBorder.setTag(TowerDefense.TAG_DETACHABLE);
		mHealthBarBackground.setTag(TowerDefense.TAG_DETACHABLE);
		mHealthBarForeground.setTag(TowerDefense.TAG_DETACHABLE);
	}
	
	public void finish() {
		
		//TODO play a sound
		TowerDefense.mVibrator.vibrate(20);
		
		// player takes damage
		TowerDefense.mLevel.getDamage(1);
		
		// remove enemy
		removeEnemy();
		
	}
	
	public synchronized void hit(double damage, int effectCode) {
		
		// enemy takes damage
		mHealth -= damage;
		
		float percentage = mHealth / mMaxHealth;
		
		// update health bar
		mHealthBarForeground.setWidth(HEALTHBAR_WIDTH * percentage);
		
		// die if health is too low
		if (mHealth <= 0) {
			die();
		} else {
			// else inflict the effect
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
	
	@Override
	public void onDetached() {
		TowerDefense.mLevel.mCurrentEnemies.remove(this);
	}
	
}

class TestEnemy extends Enemy {
	
	public static float MAX_HEALTH = 45;
	public static float SPEED = (float) 1;
	public static int COINS = 3;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_ENEMY_TEST;
	
	public TestEnemy(ArrayList<WayPoint> path, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(path, x, y, TEXTURE, pVertexBufferObjectManager);
		
		// initialize variables
		mMaxHealth = MAX_HEALTH;
		mHealth = MAX_HEALTH;
		mSpeed = SPEED;
		mCoins = COINS;
		
	}
	
}