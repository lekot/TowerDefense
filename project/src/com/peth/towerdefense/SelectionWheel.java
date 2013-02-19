package com.peth.towerdefense;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class SelectionWheel extends Sprite {
	
	// texture constants
	public static final ITextureRegion TEXTURE = TowerDefense.TEXTURE_SELECTIONWHEEL;
	
	// type constants
	public static final int TYPE_BASE = 0;
	public static final int TYPE_TOWER = 1;
	
	// globals
	public float mCenterX;
	public float mCenterY;
	public float mRadius = 52; // change this according the texture used
	public Entity mParent;
	public int mSelection = -1;
	public ArrayList<Option> mOptions;
	
	// constructor
	public SelectionWheel(float x, float y, Entity parent, int type, ArrayList<Integer> optionCodes, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x - (TEXTURE.getWidth() / 2), y - (TEXTURE.getHeight() / 2), TEXTURE, pVertexBufferObjectManager);
		
		// initialize variables
		setZIndex(TowerDefense.ZINDEX_HUD);
		mCenterX = getX() + (TEXTURE.getWidth() / 2);
		mCenterY = getY() + (TEXTURE.getHeight() / 2);
		mParent = parent;
		mOptions = new ArrayList<Option>();
		
		// attach
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(this);
			
		// loop through all options
		for (int i = 0; i < optionCodes.size(); i++) {
			
			// create select options
			if (type == TYPE_BASE) {
				
				// determine position
				double angle = Math.toRadians(((double) 360 / optionCodes.size()) * i - 45);
				float xPos = (float) (mCenterX + (Math.sin(angle) * mRadius));
				float yPos = (float) (mCenterY - (Math.cos(angle) * mRadius));
				
				// create options
				switch (optionCodes.get(i)) {
				case Option.LOCKED:
					mOptions.add(new LockedOption(xPos, yPos, mParent, this, getVertexBufferObjectManager()));
					break;
				case Option.BUILD_TOWER_TEST:
					mOptions.add(new TestTowerOption(xPos, yPos, mParent, this, getVertexBufferObjectManager()));
					break;
				case Option.BUILD_TOWER_SLOW:
					mOptions.add(new SlowTowerOption(xPos, yPos, mParent, this, getVertexBufferObjectManager()));
					break;
				case Option.BUILD_TOWER_FIRE:
					mOptions.add(new FireTowerOption(xPos, yPos, mParent, this, getVertexBufferObjectManager()));
					break;
				}
				
			} else if (type == TYPE_TOWER) {
				
				// determine position
				double angle = Math.toRadians(((double) 360 / optionCodes.size()) * i - 135);
				float xPos = (float) (mCenterX + (Math.sin(angle) * mRadius));
				float yPos = (float) (mCenterY - (Math.cos(angle) * mRadius));
				
				// create options
				switch (optionCodes.get(i)) {
				case Option.LOCKED:
					mOptions.add(new LockedOption(xPos, yPos, mParent, this, getVertexBufferObjectManager()));
					break;
				case Option.SELL_TOWER:
					mOptions.add(new SellOption(xPos, yPos, mParent, this, getVertexBufferObjectManager()));
					break;
				case Option.BUILD_TOWER_FIRE:
					mOptions.add(new FireTowerOption(xPos, yPos, mParent, this, getVertexBufferObjectManager()));
					break;
				case Option.BUILD_TOWER_FLAMETHROWER:
					mOptions.add(new FlamethrowerTowerOption(xPos, yPos, mParent, this, getVertexBufferObjectManager()));
					break;
				case Option.BUILD_TOWER_PEBBLE:
					mOptions.add(new PebbleTowerOption(xPos, yPos, mParent, this, getVertexBufferObjectManager()));
					break;
				}
				
			}
			
		}
		
	}
	
	@Override
	public void onDetached() {
		for (int i = 0; i < mOptions.size(); i++) {
			mOptions.get(i).setVisible(false);
			mOptions.get(i).setTag(TowerDefense.TAG_DETACHABLE);
		}
	}
	
	public void hide() {
		
		TowerDefense.mSceneManager.getCurrentLevel().mSelectionWheel = null;
		
		setVisible(false);
		setTag(TowerDefense.TAG_DETACHABLE);
		
	}
	
}