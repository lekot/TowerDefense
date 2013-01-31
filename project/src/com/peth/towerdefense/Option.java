package com.peth.towerdefense;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Option extends Sprite {
	
	// option code constants
	public static final int BUILD_TOWER_TEST = 0;
	public static final int BUILD_TOWER_SLOW = 1;
	public static final int BUILD_TOWER_FIRE = 2;
	public static final int SELL_TOWER = 3;
	public static final int UPGRADE_TOWER = 4;
	
	// globals
	public Entity mParent;
	public int mOptionCode;
	
	// constructor
	public Option(float x, float y, Entity parent, SelectionWheel wheel, ITextureRegion texture, int optionCode, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture, pVertexBufferObjectManager);
		
		// set variables
		setZIndex(TowerDefense.ZINDEX_GUI);
		mParent = parent;
		mOptionCode = optionCode;
		
		// attach and register touch handler
		TowerDefense.mLevel.mScene.attachChild(this);
		TowerDefense.mLevel.mScene.registerTouchArea(this);
	}
	
	@Override
	public void onDetached() {
		TowerDefense.mLevel.mScene.unregisterTouchArea(this);
	}
	
}

class TestTowerOption extends Option {
	
	// texture constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_ARCHER;
	
	// option code constants
	public static final int OPTION_CODE = BUILD_TOWER_TEST;
	
	// constructor
	public TestTowerOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, parent, wheel, TEXTURE, OPTION_CODE, pVertexBufferObjectManager);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			((BasePoint) mParent).buildTower(mOptionCode);
			TowerDefense.mLevel.hideSelectionWheel();
		}
        return true;
    }
	
}

class SlowTowerOption extends Option {
	
	// texture constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_INFANTRY;

	// option code constants
	public static final int OPTION_CODE = BUILD_TOWER_SLOW;
	
	// constructor
	public SlowTowerOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, parent, wheel, TEXTURE, OPTION_CODE, pVertexBufferObjectManager);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			((BasePoint) mParent).buildTower(mOptionCode);
			TowerDefense.mLevel.hideSelectionWheel();
		}
        return true;
    }
	
}

class FireTowerOption extends Option {
	
	// texture constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_MAGICIAN;

	// option code constants
	public static final int OPTION_CODE = BUILD_TOWER_FIRE;
	
	// constructor
	public FireTowerOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, parent, wheel, TEXTURE, OPTION_CODE, pVertexBufferObjectManager);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			((BasePoint) mParent).buildTower(mOptionCode);
			TowerDefense.mLevel.hideSelectionWheel();
		}
        return true;
    }
	
}

class SellOption extends Option {
	
	// texture constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_SELL;

	// option code constants
	public static final int OPTION_CODE = BUILD_TOWER_FIRE;
	
	// constructor
	public SellOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, parent, wheel, TEXTURE, OPTION_CODE, pVertexBufferObjectManager);
		
	}
	
	@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        
		TowerDefense.SOUND_TOWER_SELL.play();
		TowerDefense.mLevel.mCoins += Math.ceil(((Tower) mParent).mPrice * TowerDefense.SALE_RATIO);
		((Tower) mParent).mBasePoint.mCurrentTower = null;
		mParent.setVisible(false);
		mParent.setTag(TowerDefense.TAG_DETACHABLE);
		
		TowerDefense.mLevel.hideSelectionWheel();
		
        return true;
    }
	
}

class LockedOption extends Option {
	
	// texture constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_OPTION_LOCKED;

	// option code constants
	public static final int OPTION_CODE = -1;
	
	// constructor
	public LockedOption(float x, float y, Entity parent, SelectionWheel wheel, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, parent, wheel, TEXTURE, OPTION_CODE, pVertexBufferObjectManager);
		
	}
	
}