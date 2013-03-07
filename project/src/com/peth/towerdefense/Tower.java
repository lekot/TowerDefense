package com.peth.towerdefense;

import java.util.ArrayList;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class Tower extends Sprite {
	
	// tower constants
	public static final int TOWER_TEST = 0;
	public static final int TOWER_SLOW = 1;
	public static final int TOWER_FIRE = 2;
	public static final int TOWER_FLAMETHROWER = 3;
	public static final int TOWER_BOMB = 4;
	public static final int TOWER_PEBBLE = 5;
	
	// scan method constants
	public static final int SCAN_FIRST = 0;
	public static final int SCAN_LAST = 1;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public float mOffsetX;
	public float mOffsetY;
	public String mName;
	public float mRange;
	public Sprite mRangeCircle;
	public int mDelay;
	public float mDamage;
	public int mScanMethod;
	public Enemy mTarget;
	public float mPrice;
	public ArrayList<Integer> mOptions;
	public BasePoint mBasePoint;
	public Thread mScanThread;
	public boolean mActive = true;
	
	// constructor
	public Tower(BasePoint parent, float offsetX, float offsetY, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(parent.mCenterX - (texture.getWidth() / 2), parent.mCenterY - (texture.getHeight() / 2), texture, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_TOWERS);
		mBasePoint = parent;
		mCenterX = parent.mCenterX;
		mCenterY = parent.mCenterY;
		mOffsetX = offsetX;
		mOffsetY = offsetY;
		mOptions = new ArrayList<Integer>();
		mOptions.add(Option.SELL_TOWER);
		
		// register touch handler
		TowerDefense.mSceneManager.getCurrentLevel().registerTouchArea(this);
		
		// start scanning for enemies
		mScanThread = new Thread(new ScanTask());
		mScanThread.start();
		
	}
	
	// scan task to be run on seperate thread
	class ScanTask implements Runnable {

		@Override
		public void run() {
			
			while (mActive) {
				if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
					mTarget = scan(mScanMethod);
					if (mTarget != null) {
						if (mTarget.mState == Enemy.STATE_DEAD) {
							mTarget = null;
						} else {
							if (inRange(mTarget)) {
								fireRound();
								try {Thread.sleep(mDelay);} catch (InterruptedException e) {e.printStackTrace();}
							} else {
								mTarget = null;
							}
						}
					}
				}
			}
			
		}
		
	}
	
	// checks all enemies to see if one is in range
	public Enemy scan(int scanMethod) {
		
		synchronized (TowerDefense.mSceneManager.getCurrentLevel().mCurrentEnemies) {
			
			switch (scanMethod) {
			case SCAN_FIRST:
				for (int i = 0; i < TowerDefense.mSceneManager.getCurrentLevel().mCurrentEnemies.size(); i++) {
					Enemy potentialTarget = TowerDefense.mSceneManager.getCurrentLevel().mCurrentEnemies.get(i);
					if (!potentialTarget.isDead() && inRange(potentialTarget)) {
						return potentialTarget;
					}
				}
				break;
			case SCAN_LAST:
				Enemy lastTarget = null;
				for (int i = 0; i < TowerDefense.mSceneManager.getCurrentLevel().mCurrentEnemies.size(); i++) {
					Enemy potentialTarget = TowerDefense.mSceneManager.getCurrentLevel().mCurrentEnemies.get(i);
					if (!potentialTarget.isDead() && inRange(potentialTarget)) {
						lastTarget = TowerDefense.mSceneManager.getCurrentLevel().mCurrentEnemies.get(i);
					}
				}
				return lastTarget;
			}
		
		}
		
		return null;
		
	}
	
	// takes an enemy and checks whether it is in range
	public boolean inRange(Enemy enemy) {
		
		float diffX = mCenterX - enemy.mCenterX;
		float diffY = (mCenterY - enemy.mCenterY) / TowerDefense.PERSPECTIVE;
		double dist = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
		
		return (dist <= mRange);
		
	}
	
	/* NO LONGER USED (WAS USED FOR ROTATING SPRITE), BUT MIGHT COME IN HANDY LATER
	// takes an enemy and calculates its direction in degrees
	public float getDirection(Enemy enemy) {
		return (float) (180 - Math.toDegrees(Math.atan2(enemy.getX() - getX(), enemy.getY() - getY())));
	}
	*/
	
	// creates a new round aimed at the current target
	public void fireRound() {
		// placeholder for subclasses
	}
	
	@Override
	public void onDetached() {
		mActive = false;
		TowerDefense.mSceneManager.getCurrentLevel().unregisterTouchArea(this);
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				if (TowerDefense.mSceneManager.getCurrentLevel().mSelectionWheel != null && TowerDefense.mSceneManager.getCurrentLevel().mSelectionWheel.mBasePoint == mBasePoint) {
					TowerDefense.mSceneManager.getCurrentLevel().unselect();
				} else {
					select();
				}
			}
		}
        return true;
    }
	
	public void select() {
		TowerDefense.mSceneManager.getCurrentLevel().unselect();
		TowerDefense.mSceneManager.getCurrentLevel().mSelection = this;
		mRangeCircle.setVisible(true);
		showSelectionWheel();
		TowerDefense.mSceneManager.getCurrentLevel().mHUD.showInfo(this);
	}
	
	public void showSelectionWheel() {
		if (TowerDefense.mSceneManager.getCurrentLevel().mSelectionWheel != null) TowerDefense.mSceneManager.getCurrentLevel().mSelectionWheel.hide();
		TowerDefense.mSceneManager.getCurrentLevel().mSelectionWheel = new SelectionWheel(mCenterX, mCenterY + mOffsetY / 2, mBasePoint, mOptions, getVertexBufferObjectManager());
	}
	
}

class TestTower extends Tower {
	
	// constants
	public static final int OFFSET_X = 0;
	public static final int OFFSET_Y = -30;
	public static final String NAME = "Earth Tower";
	public static final int PRICE = 70;
	public static final int RANGE = 120;
	public static final int DELAY = 800;
	public static final int SCAN_METHOD = SCAN_FIRST;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_TOWER_TEST;
	
	// constructor
	public TestTower(BasePoint parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(parent, OFFSET_X, OFFSET_Y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mName = NAME;
		mRange = RANGE;
		mDelay = DELAY;
		mDamage = (float) TestRound.DAMAGE * (1000f / mDelay);
		mScanMethod = SCAN_METHOD;
		mPrice = PRICE;
		mOptions.add(Option.LOCKED);
		mOptions.add(Option.BUILD_TOWER_PEBBLE);
		mOptions.add(Option.LOCKED);
		
		// build range circle //TODO create a RangeCircle class
		mRangeCircle = new Sprite(mCenterX - mRange, mCenterY - (mRange * TowerDefense.PERSPECTIVE), TowerDefense.TEXTURE_RANGECIRCLE, getVertexBufferObjectManager());
		float scale = (mRange * 2) / TowerDefense.TEXTURE_RANGECIRCLE.getWidth();
		mRangeCircle.setWidth(mRangeCircle.getWidth() * scale);
		mRangeCircle.setHeight(mRangeCircle.getHeight() * scale);
		mRangeCircle.setZIndex(TowerDefense.ZINDEX_TOWERS - 1);
		mRangeCircle.setVisible(false);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mRangeCircle);
		
	}
	
	// super methods
	public void fireRound() {
		Round round = new TestRound(mTarget, mCenterX + mOffsetX, mCenterY + mOffsetY, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(round);
	}
	
}

class PebbleTower extends Tower {
	
	// constants
	public static final int OFFSET_X = 0;
	public static final int OFFSET_Y = -30;
	public static final String NAME = "Pebble Tower";
	public static final int PRICE = 140;
	public static final int RANGE = 120;
	public static final int DELAY = 200;
	public static final int SCAN_METHOD = SCAN_FIRST;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_TOWER_PEBBLE;
	
	// constructor
	public PebbleTower(BasePoint parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(parent, OFFSET_X, OFFSET_Y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mName = NAME;
		mRange = RANGE;
		mDelay = DELAY;
		mDamage = (float) TestRound.DAMAGE * (1000f / mDelay);
		mScanMethod = SCAN_METHOD;
		mPrice = PRICE;
		mOptions.add(Option.LOCKED);
		mOptions.add(Option.LOCKED);
		mOptions.add(Option.LOCKED);
		
		// build range circle //TODO create a RangeCircle class
		mRangeCircle = new Sprite(mCenterX - mRange, mCenterY - (mRange * TowerDefense.PERSPECTIVE), TowerDefense.TEXTURE_RANGECIRCLE, getVertexBufferObjectManager());
		float scale = (mRange * 2) / TowerDefense.TEXTURE_RANGECIRCLE.getWidth();
		mRangeCircle.setWidth(mRangeCircle.getWidth() * scale);
		mRangeCircle.setHeight(mRangeCircle.getHeight() * scale);
		mRangeCircle.setZIndex(TowerDefense.ZINDEX_TOWERS - 1);
		mRangeCircle.setVisible(false);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mRangeCircle);
		
	}
	
	// super methods
	public void fireRound() {
		Round round = new PebbleRound(mTarget, mCenterX + mOffsetX, mCenterY + mOffsetY, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(round);
	}
	
}

class SlowTower extends Tower {
	
	// constants
	public static final int OFFSET_X = 0;
	public static final int OFFSET_Y = -30;
	public static final String NAME = "Water Tower";
	public static final int PRICE = 70;
	public static final int RANGE = 100;
	public static final int DELAY = 2000;
	public static final int SCAN_METHOD = SCAN_LAST;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_TOWER_SLOW;
	
	// constructor
	public SlowTower(BasePoint parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(parent, OFFSET_X, OFFSET_Y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mName = NAME;
		mRange = RANGE;
		mDelay = DELAY;
		mDamage = (float) SlowRound.DAMAGE * (1000f / mDelay);
		mScanMethod = SCAN_METHOD;
		mPrice = PRICE;
		mOptions.add(Option.LOCKED);
		mOptions.add(Option.LOCKED);
		mOptions.add(Option.LOCKED);
		
		// build range circle //TODO create a RangeCircle class
		mRangeCircle = new Sprite(mCenterX - mRange, mCenterY - (mRange * TowerDefense.PERSPECTIVE), TowerDefense.TEXTURE_RANGECIRCLE, getVertexBufferObjectManager());
		float scale = (mRange * 2) / TowerDefense.TEXTURE_RANGECIRCLE.getWidth();
		mRangeCircle.setWidth(mRangeCircle.getWidth() * scale);
		mRangeCircle.setHeight(mRangeCircle.getHeight() * scale);
		mRangeCircle.setZIndex(TowerDefense.ZINDEX_TOWERS - 1);
		mRangeCircle.setVisible(false);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mRangeCircle);
		
	}
	
	// super methods
	public void fireRound() {
		Round round = new SlowRound(mTarget, mCenterX + mOffsetX, mCenterY + mOffsetY, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(round);
	}
	
}

class FireTower extends Tower {
	
	// constants
	public static final int OFFSET_X = 0;
	public static final int OFFSET_Y = -30;
	public static final String NAME = "Fire Tower";
	public static final int PRICE = 120;
	public static final int RANGE = 150;
	public static final int DELAY = 1500;
	public static final int SCAN_METHOD = SCAN_FIRST;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_TOWER_FIRE;
	
	// constructor
	public FireTower(BasePoint parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(parent, OFFSET_X, OFFSET_Y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mName = NAME;
		mRange = RANGE;
		mDelay = DELAY;
		mDamage = (float) FireBallRound.DAMAGE * (1000f / mDelay);
		mScanMethod = SCAN_METHOD;
		mPrice = PRICE;
		mOptions.add(Option.LOCKED);
		mOptions.add(Option.BUILD_TOWER_FLAMETHROWER);
		mOptions.add(Option.LOCKED);
		
		// build range circle //TODO create a RangeCircle class
		mRangeCircle = new Sprite(mCenterX - mRange, mCenterY - (mRange * TowerDefense.PERSPECTIVE), TowerDefense.TEXTURE_RANGECIRCLE, getVertexBufferObjectManager());
		float scale = (mRange * 2) / TowerDefense.TEXTURE_RANGECIRCLE.getWidth();
		mRangeCircle.setWidth(mRangeCircle.getWidth() * scale);
		mRangeCircle.setHeight(mRangeCircle.getHeight() * scale);
		mRangeCircle.setZIndex(TowerDefense.ZINDEX_TOWERS - 1);
		mRangeCircle.setVisible(false);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mRangeCircle);
		
	}
	
	// super methods
	public void fireRound() {
		Round round = new FireBallRound(mTarget, mCenterX + mOffsetX, mCenterY + mOffsetY, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(round);
	}
	
}

class FlamethrowerTower extends Tower {
	
	// constants
	public static final int OFFSET_X = 0;
	public static final int OFFSET_Y = -30;
	public static final String NAME = "Flamethrower Tower";
	public static final int PRICE = 180;
	public static final int RANGE = 100;
	public static final int DELAY = 5;
	public static final int SCAN_METHOD = SCAN_FIRST;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_TOWER_FLAMETHROWER;
	
	// constructor
	public FlamethrowerTower(BasePoint parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(parent, OFFSET_X, OFFSET_Y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mName = NAME;
		mRange = RANGE;
		mDelay = DELAY;
		mDamage = (float) FlameRound.DAMAGE * (1000f / mDelay);
		mScanMethod = SCAN_METHOD;
		mPrice = PRICE;
		mOptions.add(Option.LOCKED);
		
		// build range circle //TODO create a RangeCircle class
		mRangeCircle = new Sprite(mCenterX - mRange, mCenterY - (mRange * TowerDefense.PERSPECTIVE), TowerDefense.TEXTURE_RANGECIRCLE, getVertexBufferObjectManager());
		float scale = (mRange * 2) / TowerDefense.TEXTURE_RANGECIRCLE.getWidth();
		mRangeCircle.setWidth(mRangeCircle.getWidth() * scale);
		mRangeCircle.setHeight(mRangeCircle.getHeight() * scale);
		mRangeCircle.setZIndex(TowerDefense.ZINDEX_TOWERS - 1);
		mRangeCircle.setVisible(false);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mRangeCircle);
		
	}
	
	// super methods
	public void fireRound() {
		Round round = new FlameRound(mTarget, mCenterX + mOffsetX, mCenterY + mOffsetY, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(round);
	}
	
}