package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class Round extends Sprite {
	
	// effect constants
	public static final int EFFECT_NONE = 0;
	public static final int EFFECT_SLOW = 1;
	
	// globals
	public Enemy mTarget;
	public float mSpeed;
	public float mDamage;
	public int mEffect = EFFECT_NONE;
	
	// constructor
	public Round(float x, float y, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, texture, pVertexBufferObjectManager);
		
		// start scanning for enemies
		Thread moveThread = new Thread(new MoveTask());
		moveThread.start();
		
	}
	
	class MoveTask implements Runnable {

		@Override
		public void run() {
			
			// while enemy has not reached target (null check is for the sake of preventing thread collisions)
			while (mTarget != null && (Math.abs(getX() - mTarget.getX()) > mSpeed || Math.abs(getY() - mTarget.getY()) > mSpeed)) {
				
				// update location
				float diffX = mTarget.getX() - getX();
				float diffY = mTarget.getY() - getY();
				float dist = (float) Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
				float dX = diffX / dist * (float) mSpeed;
				float dY = diffY / dist * (float) mSpeed;
				setPosition(getX() + dX, getY() + dY);
				
				// sleep for animation's sake
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			// enemy has reached target (null check for the sake of thread collision)
			if (mTarget != null) {
				mTarget.hit(mDamage, mEffect);
			}
			setVisible(false); //TODO change to TowerDefense.scene.detachChild(this) on update thread
			
		}
		
	}
	
}

class TestRound extends Round {
	
	// constants
	public static final int SPEED = 5;
	public static final int DAMAGE = 15;
	public static final ITextureRegion TEXTURE = TowerDefense.roundTextureRegion;
	
	// constructor
	public TestRound(Enemy target, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mTarget = target;
		mSpeed = SPEED;
		mDamage = DAMAGE;
		
	}
	
}

class SlowRound extends Round {
	
	// constants
	public static final int SPEED = 5;
	public static final int DAMAGE = 0;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_ROUND_SLOW;
	public static final int EFFECT = EFFECT_SLOW;
	
	// constructor
	public SlowRound(Enemy target, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mTarget = target;
		mSpeed = SPEED;
		mDamage = DAMAGE;
		mEffect = EFFECT;
		
	}
	
}