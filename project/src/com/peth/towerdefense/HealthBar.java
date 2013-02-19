package com.peth.towerdefense;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class HealthBar extends Rectangle {
	
	// constants
	private static final float WIDTH = 15;
	private static final float HEIGHT = 1;
	private static final float YOFFSET = 15;
	private static final float BORDER_THICKNESS = 1;
	
	// globals
	public Enemy mParent;
	public Rectangle mHealthForeground;
	public Rectangle mHealthBackground;
	
	public HealthBar(Enemy parent, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(parent.mCenterX - (WIDTH + BORDER_THICKNESS * 2) / 2, parent.mCenterY - YOFFSET - BORDER_THICKNESS, WIDTH + BORDER_THICKNESS * 2, HEIGHT + BORDER_THICKNESS * 2, pVertexBufferObjectManager);
		
		// set variables
		mParent = parent;
		setColor(0.3f, 0.3f, 0.3f);
		setZIndex(800);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(this);
		
		// set background
		mHealthBackground = new Rectangle(mParent.mCenterX - WIDTH / 2, mParent.mCenterY - YOFFSET, WIDTH, HEIGHT, getVertexBufferObjectManager());
		mHealthBackground.setColor(220f/255, 25f/255, 25f/255);
		mHealthBackground.setZIndex(801);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mHealthBackground);
		
		// set foreground
		mHealthForeground = new Rectangle(mParent.mCenterX - WIDTH / 2, mParent.mCenterY - YOFFSET, WIDTH, HEIGHT, getVertexBufferObjectManager());
		mHealthForeground.setColor(100f/255, 220f/255, 20f/255);
		mHealthForeground.setZIndex(802);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mHealthForeground);
		
	}
	
	public void follow() {
		
		setPosition(mParent.mCenterX - (WIDTH + BORDER_THICKNESS * 2) / 2, mParent.mCenterY - YOFFSET - BORDER_THICKNESS);
		mHealthBackground.setPosition(mParent.mCenterX - WIDTH / 2, mParent.mCenterY - YOFFSET);
		mHealthForeground.setPosition(mParent.mCenterX - WIDTH / 2, mParent.mCenterY - YOFFSET);
		
	}
	
	public void hide() {
		
		setVisible(false);
		mHealthForeground.setVisible(false);
		mHealthBackground.setVisible(false);
		
		setTag(TowerDefense.TAG_DETACHABLE);
		mHealthForeground.setTag(TowerDefense.TAG_DETACHABLE);
		mHealthBackground.setTag(TowerDefense.TAG_DETACHABLE);
		
	}
	
	public void update(float percentage) {
		
		mHealthForeground.setWidth(WIDTH * percentage);
		
	}
	
}