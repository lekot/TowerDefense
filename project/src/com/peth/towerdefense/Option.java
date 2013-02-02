package com.peth.towerdefense;

import org.andengine.entity.Entity;
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
	public static final int SELL_TOWER = 4;
	public static final int UPGRADE_TOWER = 5;
	
	// globals
	public Entity mParent;
	public float mCenterX;
	public float mCenterY;
	public boolean mAvailable = true;
	public Sprite mPriceSign;
	
	// constructor
	public Option(float x, float y, Entity parent, SelectionWheel wheel, ITextureRegion texture, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_HUD);
		mParent = parent;
		mCenterX = x;
		mCenterY = y;
		
		// attach and register touch handler
		TowerDefense.mLevel.mScene.attachChild(this);
		TowerDefense.mLevel.mScene.registerTouchArea(this);
	}
	
	@Override
	public void onDetached() {
		TowerDefense.mLevel.mScene.unregisterTouchArea(this);
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
	public TestTowerOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, parent, wheel, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPriceSign = new PriceSign(mCenterX, mCenterY + PriceSign.YOFFSET, TestTower.PRICE, getVertexBufferObjectManager());
		TowerDefense.mLevel.mScene.attachChild(mPriceSign);
		mAvailable = (TowerDefense.mLevel.mCoins < TestTower.PRICE) ? false : true;
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if (mAvailable) {
				((BasePoint) mParent).buildTower(Tower.TOWER_TEST);
				TowerDefense.mLevel.hideSelectionWheel();
			}
		}
        return true;
    }
	
}

class SlowTowerOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_INFANTRY;
	
	// constructor
	public SlowTowerOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, parent, wheel, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPriceSign = new PriceSign(mCenterX, mCenterY + PriceSign.YOFFSET, SlowTower.PRICE, getVertexBufferObjectManager());
		TowerDefense.mLevel.mScene.attachChild(mPriceSign);
		mAvailable = (TowerDefense.mLevel.mCoins < SlowTower.PRICE) ? false : true;
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if (mAvailable) {
				((BasePoint) mParent).buildTower(Tower.TOWER_SLOW);
				TowerDefense.mLevel.hideSelectionWheel();
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
	public FireTowerOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, parent, wheel, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mPriceSign = new PriceSign(mCenterX, mCenterY + PriceSign.YOFFSET, FireTower.PRICE, getVertexBufferObjectManager());
		TowerDefense.mLevel.mScene.attachChild(mPriceSign);
		mAvailable = (TowerDefense.mLevel.mCoins < FireTower.PRICE) ? false : true;
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if (mAvailable) {
				((BasePoint) mParent).buildTower(Tower.TOWER_FIRE);
				TowerDefense.mLevel.hideSelectionWheel();
			}
		}
        return true;
    }
	
}

class UpgradeOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_UPGRADE;
	
	// globals
	public int mTowerCode;
	
	// constructor
	public UpgradeOption(float x, float y, Entity parent, SelectionWheel wheel, int towerCode, float price, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, parent, wheel, TEXTURE, pVertexBufferObjectManager);
		
		// set variables
		mTowerCode = towerCode;
		mPriceSign = new PriceSign(mCenterX, mCenterY + PriceSign.YOFFSET, price, getVertexBufferObjectManager());
		TowerDefense.mLevel.mScene.attachChild(mPriceSign);
		mAvailable = (TowerDefense.mLevel.mCoins < price) ? false : true;
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if (mAvailable) {
				((Tower) mParent).upgradeTower(mTowerCode);
				TowerDefense.mLevel.hideSelectionWheel();
			}
		}
        return true;
    }
	
}

class SellOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_SELL;
	
	// constructor
	public SellOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, parent, wheel, TEXTURE, pVertexBufferObjectManager);
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		((Tower) mParent).sellTower();
		TowerDefense.mLevel.hideSelectionWheel();
        return true;
    }
	
}

class LockedOption extends Option {
	
	// constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_LOCKED;
	
	// constructor
	public LockedOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(x, y, parent, wheel, TEXTURE, pVertexBufferObjectManager);
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return true;
    }
	
}