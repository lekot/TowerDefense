package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

//TODO rename this class Projectile
public abstract class Round extends Sprite {
	
	// effect constants
	public static final int EFFECT_NONE = 0;
	public static final int EFFECT_SLOW = 1;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public int mType;
	public Enemy mTarget;
	public float mSpeed;
	public double mDamage;
	public int mEffect = EFFECT_NONE;
	public Thread mMoveThread;
	public float mGrowth = 0;
	
	// constructor
	public Round(float x, float y, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_ROUNDS);
		mCenterX = getX() + (texture.getWidth() / 2);
		mCenterY = getY() + (texture.getHeight() / 2);
		
		// start scanning for enemies
		mMoveThread = new Thread(new MoveTask());
		mMoveThread.start();
		
	}
	
	class MoveTask implements Runnable {

		@Override
		public void run() {
			
			// while enemy has not reached target (null check is for the sake of preventing thread collisions)
			while (mTarget != null) {
				
				if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
				
					// calculate distance to targeted waypoint
					float distX = mTarget.mCenterX - mCenterX;
					float distY = mTarget.mCenterY - mCenterY;
					float dist = (float) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
					
					// if close enough to targeted waypoint, skip to acquiring next waypoint
					if (dist < mSpeed) {
						break;
					}
					
					// otherwise move in direction of targeted waypoint
					float dX = distX / dist * (float) mSpeed;
					float dY = distY / dist * (float) mSpeed;
					setPosition(getX() + dX, getY() + dY);
					mCenterX += dX;
					mCenterY += dY;
					
					// increase size of the sprite if it is a fire bullet
					if (mGrowth > 0) {
						setScale(Math.min(1, getScaleX() + mGrowth));
					}
					
					// sleep for animation's sake
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
				}
				
			}
			
			// enemy has reached target (null check for the sake of thread collision)
			if (mTarget != null) {
				mTarget.hit(mDamage, mEffect);
			}
			
			setVisible(false);
			setTag(TowerDefense.TAG_DETACHABLE);
			
		}
		
	}
	
}

class TestRound extends Round {
	
	// constants
	public static final int SPEED = 5;
	public static final double DAMAGE = 15;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_ROUND_TEST;
	
	// constructor
	public TestRound(Enemy target, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mTarget = target;
		mSpeed = SPEED;
		mDamage = DAMAGE;
		
		// play sound
		TowerDefense.SOUND_PROJECTILE_ARROW.play();
		
	}
	
}

class PebbleRound extends Round {
	
	// constants
	public static final int SPEED = 5;
	public static final double DAMAGE = 8;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_ROUND_TEST;
	
	// constructor
	public PebbleRound(Enemy target, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mTarget = target;
		mSpeed = SPEED;
		mDamage = DAMAGE;
		
		// play sound
		TowerDefense.SOUND_PROJECTILE_ARROW.play();
		
	}
	
}

class SlowRound extends Round {
	
	// constants
	public static final int SPEED = 5;
	public static final float DAMAGE = 0;
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

class FlameRound extends Round {
	
	// constants
	public static final int SPEED = 4;
	public static final double DAMAGE = 0.2;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_ROUND_FIRE;
	
	// constructor
	public FlameRound(Enemy target, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mTarget = target;
		mSpeed = SPEED;
		mDamage = DAMAGE;
		mGrowth = 0.025f;
		setScale(0.2f);
		setAlpha(0.25f);
		
	}
	
}

class FireBallRound extends Round {
	
	// constants
	public static final int SPEED = 4;
	public static final double DAMAGE = 30;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_ROUND_FIRE;
	
	// constructor
	public FireBallRound(Enemy target, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mTarget = target;
		mSpeed = SPEED;
		mDamage = DAMAGE;
		setScale(0.5f);
		
	}
	
}