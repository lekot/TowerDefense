package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Option extends Sprite {
	
	// constants
	public static final int LOCKED = -1;
	public static final int BUILD_TOWER_TEST = 0;
	public static final int BUILD_TOWER_SLOW = 1;
	public static final int BUILD_TOWER_FIRE = 2;
	public static final int BUILD_TOWER_FLAMETHROWER = 3;
	public static final int SELL_TOWER = 4;
	public static final int UPGRADE_TOWER = 5;
	public static final int BUILD_TOWER_PEBBLE = 6;
	
	// globals
	public BasePoint mBasePoint;
	public float mCenterX;
	public float mCenterY;
	public boolean mAvailable = true;
	public Sprite mPriceSign;
	
	// constructor
	public Option(float x, float y, BasePoint basePoint, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_HUD);
		mBasePoint = basePoint;
		mCenterX = x;
		mCenterY = y;
		
		// attach and register touch handler
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(this);
		TowerDefense.mSceneManager.getCurrentLevel().registerTouchArea(this);
	}
	
	@Override
	public void onDetached() {
		TowerDefense.mSceneManager.getCurrentLevel().unregisterTouchArea(this);
		if (mPriceSign != null) {
			mPriceSign.setVisible(false);
			mPriceSign.setTag(TowerDefense.TAG_DETACHABLE);
		}
	}
	
}

class TestTowerOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_ARCHER;
	
	// constructor
	public TestTowerOption(float x, float y, BasePoint basePoint, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, basePoint, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPriceSign = new PriceSign(mCenterX, mCenterY + PriceSign.YOFFSET, TestTower.PRICE, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mPriceSign);
		mAvailable = (TowerDefense.mSceneManager.getCurrentLevel().mCoins < TestTower.PRICE) ? false : true;
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				if (mAvailable) {
					mBasePoint.mCurrentTower = new TestTower(mBasePoint, getVertexBufferObjectManager());
					TowerDefense.mSceneManager.getCurrentLevel().attachChild(mBasePoint.mCurrentTower);
					TowerDefense.mSceneManager.getCurrentLevel().mCoins -= mBasePoint.mCurrentTower.mPrice;
					TowerDefense.mSceneManager.getCurrentLevel().hideSelectionWheel();
				}
			}
		}
        return true;
    }
	
}

class PebbleTowerOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_ARCHER;
	
	// constructor
	public PebbleTowerOption(float x, float y, BasePoint basePoint, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, basePoint, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPriceSign = new PriceSign(mCenterX, mCenterY + PriceSign.YOFFSET, PebbleTower.PRICE, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mPriceSign);
		mAvailable = (TowerDefense.mSceneManager.getCurrentLevel().mCoins < PebbleTower.PRICE) ? false : true;
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				if (mAvailable) {
					mBasePoint.destroyTower();
					mBasePoint.mCurrentTower = new PebbleTower(mBasePoint, getVertexBufferObjectManager());
					TowerDefense.mSceneManager.getCurrentLevel().attachChild(mBasePoint.mCurrentTower);
					TowerDefense.mSceneManager.getCurrentLevel().mCoins -= mBasePoint.mCurrentTower.mPrice;
					TowerDefense.mSceneManager.getCurrentLevel().hideSelectionWheel();
				}
			}
		}
        return true;
    }
	
}

class SlowTowerOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_INFANTRY;
	
	// constructor
	public SlowTowerOption(float x, float y, BasePoint basePoint, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, basePoint, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPriceSign = new PriceSign(mCenterX, mCenterY + PriceSign.YOFFSET, SlowTower.PRICE, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mPriceSign);
		mAvailable = (TowerDefense.mSceneManager.getCurrentLevel().mCoins < SlowTower.PRICE) ? false : true;
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				if (mAvailable) {
					mBasePoint.mCurrentTower = new TestTower(mBasePoint, getVertexBufferObjectManager());
					TowerDefense.mSceneManager.getCurrentLevel().attachChild(mBasePoint.mCurrentTower);
					TowerDefense.mSceneManager.getCurrentLevel().mCoins -= mBasePoint.mCurrentTower.mPrice;
					TowerDefense.mSceneManager.getCurrentLevel().hideSelectionWheel();
				}
			}
		}
        return true;
    }
	
}

class FireTowerOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_MAGICIAN;
	public static final ITextureRegion TEXTURE_UNAVAILABLE = TowerDefense.TEXTURE_OPTION_MAGICIAN_UNAVAILABLE; //TODO use this
	
	// constructor
	public FireTowerOption(float x, float y, BasePoint basePoint, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, basePoint, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPriceSign = new PriceSign(mCenterX, mCenterY + PriceSign.YOFFSET, FireTower.PRICE, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mPriceSign);
		mAvailable = (TowerDefense.mSceneManager.getCurrentLevel().mCoins < FireTower.PRICE) ? false : true;
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				if (mAvailable) {
					mBasePoint.mCurrentTower = new FireTower(mBasePoint, getVertexBufferObjectManager());
					TowerDefense.mSceneManager.getCurrentLevel().attachChild(mBasePoint.mCurrentTower);
					TowerDefense.mSceneManager.getCurrentLevel().mCoins -= mBasePoint.mCurrentTower.mPrice;
					TowerDefense.mSceneManager.getCurrentLevel().hideSelectionWheel();
				}
			}
		}
        return true;
    }
	
}

class FlamethrowerTowerOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_MAGICIAN;
	public static final ITextureRegion TEXTURE_UNAVAILABLE = TowerDefense.TEXTURE_OPTION_MAGICIAN_UNAVAILABLE; //TODO use this
	
	// constructor
	public FlamethrowerTowerOption(float x, float y, BasePoint basePoint, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, basePoint, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPriceSign = new PriceSign(mCenterX, mCenterY + PriceSign.YOFFSET, FlamethrowerTower.PRICE, getVertexBufferObjectManager());
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mPriceSign);
		mAvailable = (TowerDefense.mSceneManager.getCurrentLevel().mCoins < FlamethrowerTower.PRICE) ? false : true;
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				if (mAvailable) {
					mBasePoint.destroyTower();
					mBasePoint.mCurrentTower = new FlamethrowerTower(mBasePoint, getVertexBufferObjectManager());
					TowerDefense.mSceneManager.getCurrentLevel().attachChild(mBasePoint.mCurrentTower);
					TowerDefense.mSceneManager.getCurrentLevel().mCoins -= mBasePoint.mCurrentTower.mPrice;
					TowerDefense.mSceneManager.getCurrentLevel().hideSelectionWheel();
				}
			}
		}
        return true;
    }
	
}

class SellOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_SELL;
	
	// constructor
	public SellOption(float x, float y, BasePoint basePoint, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, basePoint, TEXTURE, pVertexBufferObjectManager);
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (!TowerDefense.mSceneManager.getCurrentLevel().mPaused) {
			mBasePoint.sellTower();
			TowerDefense.mSceneManager.getCurrentLevel().hideSelectionWheel();
		}
        return true;
    }
	
}

class LockedOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_LOCKED;
	
	// constructor
	public LockedOption(float x, float y, BasePoint basePoint, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, basePoint, TEXTURE, pVertexBufferObjectManager);
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return true;
    }
	
}