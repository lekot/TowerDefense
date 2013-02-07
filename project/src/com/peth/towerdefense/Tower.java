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
	public static final int TOWER_BOMB = 3;
	
	// scan method constants
	public static final int SCAN_FIRST = 0;
	public static final int SCAN_FIRST_NOT_SLOW = 1;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public float mOffsetX;
	public float mOffsetY;
	public String mName;
	public float mRange;
	public Sprite mRangeCircle;
	public int mRound;
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
		TowerDefense.mLevel.mScene.registerTouchArea(this);
		
		// start scanning for enemies
		mScanThread = new Thread(new ScanTask());
		mScanThread.start();
		
	}
	
	// scan task to be run on seperate thread
	class ScanTask implements Runnable {

		@Override
		public void run() {
			
			while (mActive) {
				if (mTarget == null) {
					mTarget = scan(mScanMethod);
				} else {
					if (mTarget.mState == Enemy.STATE_DEAD) {
						mTarget = null;
					} else {
						if (inRange(mTarget)) {
							fireRound(mRound);
							try {Thread.sleep(mDelay);} catch (InterruptedException e) {e.printStackTrace();}
						} else {
							mTarget = null;
						}
					}
				}
			}
			
		}
		
	}
	
	// checks all enemies to see if one is in range
	public Enemy scan(int scanMethod) {
		
		synchronized (TowerDefense.mLevel.mCurrentEnemies) {
			
			switch (scanMethod) {
			case SCAN_FIRST:
				for (int i = 0; i < TowerDefense.mLevel.mCurrentEnemies.size(); i++) {
					Enemy potentialTarget = TowerDefense.mLevel.mCurrentEnemies.get(i);
					if (!potentialTarget.isDead() && inRange(potentialTarget)) {
						return TowerDefense.mLevel.mCurrentEnemies.get(i);
					}
				}
				break;
			case SCAN_FIRST_NOT_SLOW:
				for (int i = 0; i < TowerDefense.mLevel.mCurrentEnemies.size(); i++) {
					Enemy potentialTarget = TowerDefense.mLevel.mCurrentEnemies.get(i);
					if (!potentialTarget.isDead() && inRange(potentialTarget) && !potentialTarget.isSlow()) {
						return TowerDefense.mLevel.mCurrentEnemies.get(i);
					}
				}
				break;
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
	public void fireRound(int roundCode) {
		
		switch (roundCode) {
		case Round.ROUND_TEST:
			Round testRound = new TestRound(mTarget, mCenterX + mOffsetX, mCenterY + mOffsetY, getVertexBufferObjectManager());
			TowerDefense.mLevel.mScene.attachChild(testRound);
			break;
		case Round.ROUND_SLOW:
			Round slowRound = new SlowRound(mTarget, mCenterX + mOffsetX, mCenterY + mOffsetY, getVertexBufferObjectManager());
			TowerDefense.mLevel.mScene.attachChild(slowRound);
			break;
		case Round.ROUND_FIRE:
			Round fireRound = new FireRound(mTarget, mCenterX + mOffsetX, mCenterY + mOffsetY, getVertexBufferObjectManager());
			TowerDefense.mLevel.mScene.attachChild(fireRound);
			break;
		}
		
	}
	
	@Override
	public void onDetached() {
		mActive = false;
		TowerDefense.mLevel.mScene.unregisterTouchArea(this);
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if (TowerDefense.mLevel.mSelectionWheel != null && TowerDefense.mLevel.mSelectionWheel.mParent == this) {
				TowerDefense.mLevel.unselect();
			} else {
				select();
			}
		}
        return true;
    }
	
	public void select() {
		TowerDefense.mLevel.unselect();
		TowerDefense.mLevel.mSelection = this;
		mRangeCircle.setVisible(true);
		showSelectionWheel();
		TowerDefense.mHUD.showInfo(this);
	}
	
	public void showSelectionWheel() {
		if (TowerDefense.mLevel.mSelectionWheel != null) TowerDefense.mLevel.mSelectionWheel.hide();
		TowerDefense.mLevel.mSelectionWheel = new SelectionWheel(mCenterX, mCenterY + mOffsetY / 2, this, SelectionWheel.TYPE_TOWER, mOptions, getVertexBufferObjectManager());
	}
	
	public void sellTower() {
		TowerDefense.SOUND_COINS.play();
		TowerDefense.mLevel.mCoins += Math.ceil(mPrice * TowerDefense.SALE_RATIO);
		destroyTower();
	}
	
	public void destroyTower() {
		mBasePoint.mCurrentTower = null;
		setVisible(false);
		setTag(TowerDefense.TAG_DETACHABLE);
	}
	
	public void upgradeTower(int towerCode) {
		destroyTower();
		mBasePoint.buildTower(towerCode);
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
	public static final int ROUND = Round.ROUND_TEST;
	public static final int SCAN_METHOD = SCAN_FIRST;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_TOWER_TEST;
	
	public TestTower(BasePoint parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(parent, OFFSET_X, OFFSET_Y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mName = NAME;
		mRange = RANGE;
		mDelay = DELAY;
		mRound = ROUND;
		mDamage = (float) TestRound.DAMAGE * (1000f / mDelay);
		mScanMethod = SCAN_METHOD;
		mPrice = PRICE;
		mOptions.add(Option.LOCKED);
		mOptions.add(Option.BUILD_TOWER_FIRE);
		mOptions.add(Option.LOCKED);
		
		// build range circle //TODO create a RangeCircle class
		mRangeCircle = new Sprite(mCenterX - mRange, mCenterY - (mRange * TowerDefense.PERSPECTIVE), TowerDefense.TEXTURE_RANGECIRCLE, getVertexBufferObjectManager());
		float scale = (mRange * 2) / TowerDefense.TEXTURE_RANGECIRCLE.getWidth();
		mRangeCircle.setWidth(mRangeCircle.getWidth() * scale);
		mRangeCircle.setHeight(mRangeCircle.getHeight() * scale);
		mRangeCircle.setZIndex(TowerDefense.ZINDEX_TOWERS - 1);
		mRangeCircle.setVisible(false);
		TowerDefense.mLevel.mScene.attachChild(mRangeCircle);
		
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
	public static final int ROUND = Round.ROUND_SLOW;
	public static final int SCAN_METHOD = SCAN_FIRST_NOT_SLOW;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_TOWER_SLOW;
	
	public SlowTower(BasePoint parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(parent, OFFSET_X, OFFSET_Y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mName = NAME;
		mRange = RANGE;
		mDelay = DELAY;
		mRound = ROUND;
		mDamage = (float) SlowRound.DAMAGE * (1000f / mDelay);
		mScanMethod = SCAN_METHOD;
		mPrice = PRICE;
		mOptions.add(Option.LOCKED);
		mOptions.add(Option.BUILD_TOWER_FIRE);
		mOptions.add(Option.LOCKED);
		
		// build range circle //TODO create a RangeCircle class
		mRangeCircle = new Sprite(mCenterX - mRange, mCenterY - (mRange * TowerDefense.PERSPECTIVE), TowerDefense.TEXTURE_RANGECIRCLE, getVertexBufferObjectManager());
		float scale = (mRange * 2) / TowerDefense.TEXTURE_RANGECIRCLE.getWidth();
		mRangeCircle.setWidth(mRangeCircle.getWidth() * scale);
		mRangeCircle.setHeight(mRangeCircle.getHeight() * scale);
		mRangeCircle.setZIndex(TowerDefense.ZINDEX_TOWERS - 1);
		mRangeCircle.setVisible(false);
		TowerDefense.mLevel.mScene.attachChild(mRangeCircle);
		
	}
	
}

class FireTower extends Tower {
	
	// constants
	public static final int OFFSET_X = 0;
	public static final int OFFSET_Y = -30;
	public static final String NAME = "Fire Tower";
	public static final int PRICE = 120;
	public static final int RANGE = 100;
	public static final int DELAY = 5;
	public static final int ROUND = Round.ROUND_FIRE;
	public static final int SCAN_METHOD = SCAN_FIRST;
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_TOWER_FIRE;
	
	public FireTower(BasePoint parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(parent, OFFSET_X, OFFSET_Y, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mName = NAME;
		mRange = RANGE;
		mDelay = DELAY;
		mRound = ROUND;
		mDamage = (float) FireRound.DAMAGE * (1000f / mDelay);
		mScanMethod = SCAN_METHOD;
		mPrice = PRICE;
		mOptions.add(Option.LOCKED);
		mOptions.add(Option.BUILD_TOWER_FIRE);
		mOptions.add(Option.LOCKED);
		
		// build range circle //TODO create a RangeCircle class
		mRangeCircle = new Sprite(mCenterX - mRange, mCenterY - (mRange * TowerDefense.PERSPECTIVE), TowerDefense.TEXTURE_RANGECIRCLE, getVertexBufferObjectManager());
		float scale = (mRange * 2) / TowerDefense.TEXTURE_RANGECIRCLE.getWidth();
		mRangeCircle.setWidth(mRangeCircle.getWidth() * scale);
		mRangeCircle.setHeight(mRangeCircle.getHeight() * scale);
		mRangeCircle.setZIndex(TowerDefense.ZINDEX_TOWERS - 1);
		mRangeCircle.setVisible(false);
		TowerDefense.mLevel.mScene.attachChild(mRangeCircle);
		
	}
	
}