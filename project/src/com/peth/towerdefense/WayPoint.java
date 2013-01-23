package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class WayPoint extends Sprite {
	
	public WayPoint(float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TowerDefense.wayPointTextureRegion, pVertexBufferObjectManager);
		
	}
	
}