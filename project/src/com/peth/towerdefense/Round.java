package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.widget.Toast;

public class Round extends Sprite {
	
	final Sprite self = this;
	public Enemy mTarget;
	public float mSpeed;
	public float mDamage;
	
	public Round(Enemy target, float x, float y, VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// superconstructor
		super(x, y, TowerDefense.roundTextureRegion, pVertexBufferObjectManager);
		
		// get variables
		mTarget = target;
		mSpeed = 5;
		mDamage = 20;
		
		// start scanning for enemies
		Thread moveThread = new Thread(new MoveTask());
		moveThread.start();
		
	}
	
	class MoveTask implements Runnable {

		@Override
		public void run() {
			
			while (mTarget != null && (Math.abs(getX() - mTarget.getX()) > mSpeed || Math.abs(getY() - mTarget.getY()) > mSpeed)) {
				
				// update location
				float diffX = mTarget.getX() - getX();
				float diffY = mTarget.getY() - getY();
				float dist = (float) Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
				float dX = diffX / dist * (float) mSpeed;
				float dY = diffY / dist * (float) mSpeed;
				setPosition(getX() + dX, getY() + dY);
				
				// sleep for animation's sake
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			// enemy has reached target
			impact();
			
		}
		
	}
	
	public void impact() {
		
		if (mTarget != null) mTarget.getDamage(mDamage);
		setVisible(false); //TODO change to TowerDefense.scene.detachChild(this) on update thread
		
	}
	
}