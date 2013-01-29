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
		this.setZIndex(1000);
		mCenterX = getX() + (TEXTURE.getWidth() / 2);
		mCenterY = getY() + (TEXTURE.getHeight() / 2);
		mParent = parent;
		mOptions = new ArrayList<Option>();
		
		// create select options
		if (type == TYPE_BASE) {
			
			// loop through all options
			for (int i = 0; i < optionCodes.size(); i++) {
				
				// determine position
				double angle = Math.toRadians(((double) 360 / optionCodes.size()) * i - 45);
				float xPos = (float) (mCenterX + (Math.sin(angle) * mRadius));
				float yPos = (float) (mCenterY - (Math.cos(angle) * mRadius));
				
				// create and attach option
				switch (optionCodes.get(i)) {
				case BasePoint.TOWER_TEST:
					Option testTowerOption = new TestTowerOption(xPos, yPos, mParent, getVertexBufferObjectManager());
					mOptions.add(testTowerOption);
					TowerDefense.mLevel.mScene.attachChild(testTowerOption);
					break;
				case BasePoint.TOWER_SLOW:
					Option slowTowerOption = new SlowTowerOption(xPos, yPos, mParent, getVertexBufferObjectManager());
					mOptions.add(slowTowerOption);
					TowerDefense.mLevel.mScene.attachChild(slowTowerOption);
					break;
				case BasePoint.TOWER_FIRE:
					Option fireTowerOption = new FireTowerOption(xPos, yPos, mParent, getVertexBufferObjectManager());
					mOptions.add(fireTowerOption);
					TowerDefense.mLevel.mScene.attachChild(fireTowerOption);
					break;
				case BasePoint.TOWER_BOMB:
					Option bombTowerOption = new LockedOption(xPos, yPos, mParent, getVertexBufferObjectManager());
					mOptions.add(bombTowerOption);
					TowerDefense.mLevel.mScene.attachChild(bombTowerOption);
					break;
				}
				
			}
			
		}
		
	}
	
	public void hide() {
		
		for (int i = 0; i < mOptions.size(); i++) {
			mOptions.get(i).setVisible(false);
			mOptions.get(i).setTag(TowerDefense.TAG_DETACHABLE);
		}
		
		setVisible(false);
		setTag(TowerDefense.TAG_DETACHABLE);
		
	}
	
}