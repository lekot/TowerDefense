package com.peth.towerdefense;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class HUD {
	
	// globals
	public Sprite mMainHUD;
	public Sprite mBottomHUD;
	public Sprite mInfoHUD;
	public Sprite mSkillsHUD;
	public Text mHealthText;
	public Text mCoinsText;
	public Text mWavesText;
	public Sprite mTowerIcon;
	public Text mTowerNameText;
	public Text mTowerDamageText;
	public Text mTowerDelayText;
	public Text mTowerRangeText;
	public Sprite mSkill1;
	public Sprite mSkill2;
	public StartButton mStartButton;
	public Sprite mOptionsButton;
	public Sprite mPauseButton;
	
	public HUD(VertexBufferObjectManager pVertexBufferObjectManager) {
		
		// set up main hud
		mMainHUD = new Sprite(20, 20, TowerDefense.TEXTURE_HUD_MAIN, pVertexBufferObjectManager);
		mHealthText = new Text(65, 31, TowerDefense.FONT_NORMAL, "" + TowerDefense.mSceneManager.getCurrentLevel().mHealth, 32, pVertexBufferObjectManager);
		mCoinsText = new Text(122, 31, TowerDefense.FONT_NORMAL, "" + TowerDefense.mSceneManager.getCurrentLevel().mCoins, 32, pVertexBufferObjectManager);
		mWavesText = new Text(76, 54, TowerDefense.FONT_NORMAL, "WAVE " + TowerDefense.mSceneManager.getCurrentLevel().mWaveCurrent + "/" + TowerDefense.mSceneManager.getCurrentLevel().mWavesTotal, 32, pVertexBufferObjectManager);
		mMainHUD.setZIndex(TowerDefense.ZINDEX_HUD);
		mHealthText.setZIndex(TowerDefense.ZINDEX_HUD);
		mCoinsText.setZIndex(TowerDefense.ZINDEX_HUD);
		mWavesText.setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mMainHUD);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mHealthText);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mCoinsText);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mWavesText);
		
		// set up bottom hud
		mBottomHUD = new Sprite(0, TowerDefense.CAMERA_HEIGHT - TowerDefense.TEXTURE_HUD_BOTTOM.getHeight(), TowerDefense.TEXTURE_HUD_BOTTOM, pVertexBufferObjectManager);
		mBottomHUD.setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mBottomHUD);
		mStartButton = new StartButton(TowerDefense.CAMERA_WIDTH - 10, TowerDefense.CAMERA_HEIGHT, pVertexBufferObjectManager);
		
		// set up info hud
		float centerAlign = 10 + TowerDefense.TEXTURE_HUD_SKILLS.getWidth() + 10 + (TowerDefense.CAMERA_WIDTH - (10 + TowerDefense.TEXTURE_HUD_SKILLS.getWidth() + 10 + 10 + TowerDefense.TEXTURE_HUD_START.getWidth() + 10)) / 2 - TowerDefense.TEXTURE_HUD_INFO.getWidth() / 2;
		mInfoHUD = new Sprite((int) centerAlign, TowerDefense.CAMERA_HEIGHT - TowerDefense.TEXTURE_HUD_INFO.getHeight(), TowerDefense.TEXTURE_HUD_INFO, pVertexBufferObjectManager);
		mTowerIcon = new Sprite((int) (mInfoHUD.getX() + 18 - TowerDefense.TEXTURE_ICON_TEST.getWidth() / 2), (int) (mInfoHUD.getY() + 18 - TowerDefense.TEXTURE_ICON_TEST.getHeight() / 2), TowerDefense.TEXTURE_ICON_TEST, pVertexBufferObjectManager);
		mTowerNameText = new Text(mInfoHUD.getX() + 45, mInfoHUD.getY() + 12, TowerDefense.FONT_NORMAL, "", 32, pVertexBufferObjectManager);
		mTowerDamageText = new Text(mInfoHUD.getX() + 190, mInfoHUD.getY() + 12, TowerDefense.FONT_NORMAL, "", 32, pVertexBufferObjectManager);
		mTowerDelayText = new Text(mInfoHUD.getX() + 265, mInfoHUD.getY() + 12, TowerDefense.FONT_NORMAL, "", 32, pVertexBufferObjectManager);
		mTowerRangeText = new Text(mInfoHUD.getX() + 360, mInfoHUD.getY() + 12, TowerDefense.FONT_NORMAL, "", 32, pVertexBufferObjectManager);
		mInfoHUD.setZIndex(TowerDefense.ZINDEX_HUD);
		mTowerIcon.setZIndex(TowerDefense.ZINDEX_HUD);
		mTowerNameText.setZIndex(TowerDefense.ZINDEX_HUD);
		mTowerDamageText.setZIndex(TowerDefense.ZINDEX_HUD);
		mTowerDelayText.setZIndex(TowerDefense.ZINDEX_HUD);
		mTowerRangeText.setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mInfoHUD);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mTowerIcon);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mTowerNameText);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mTowerDamageText);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mTowerDelayText);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mTowerRangeText);
		hideInfo();
		
		// set up skills hud
		mSkillsHUD = new Sprite(10, TowerDefense.CAMERA_HEIGHT - TowerDefense.TEXTURE_HUD_SKILLS.getHeight(), TowerDefense.TEXTURE_HUD_SKILLS, pVertexBufferObjectManager);
		mSkill1 = new Sprite(mSkillsHUD.getX() + 33, TowerDefense.CAMERA_HEIGHT - 15 - TowerDefense.TEXTURE_HUD_SKILL.getHeight(), TowerDefense.TEXTURE_HUD_SKILL, pVertexBufferObjectManager);
		mSkill2 = new Sprite(mSkillsHUD.getX() + 108, TowerDefense.CAMERA_HEIGHT - 15 - TowerDefense.TEXTURE_HUD_SKILL.getHeight(), TowerDefense.TEXTURE_HUD_SKILL, pVertexBufferObjectManager);
		mSkillsHUD.setZIndex(TowerDefense.ZINDEX_HUD);
		mSkill1.setZIndex(TowerDefense.ZINDEX_HUD);
		mSkill2.setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mSkillsHUD);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mSkill1);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mSkill2);
		
		// set up the options hud //TODO make OptionsButton and PauseButton classes
		mOptionsButton = new Sprite(TowerDefense.CAMERA_WIDTH - 20 - TowerDefense.TEXTURE_BUTTON_OPTIONS.getWidth(), 20, TowerDefense.TEXTURE_BUTTON_OPTIONS, pVertexBufferObjectManager);
		mOptionsButton.setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mSceneManager.getCurrentLevel().attachChild(mOptionsButton);
		mPauseButton = new PauseButton(TowerDefense.CAMERA_WIDTH - 86, 38, pVertexBufferObjectManager);
		
	}
	
	public void update() {
		
		mHealthText.setText("" + TowerDefense.mSceneManager.getCurrentLevel().mHealth);
		mCoinsText.setText("" + TowerDefense.mSceneManager.getCurrentLevel().mCoins);
		mWavesText.setText("WAVE " + TowerDefense.mSceneManager.getCurrentLevel().mWaveCurrent + "/" + TowerDefense.mSceneManager.getCurrentLevel().mWavesTotal);
		
	}
	
	public void hideInfo() {
		
		mInfoHUD.setVisible(false);
		mTowerIcon.setVisible(false);
		mTowerNameText.setVisible(false);
		mTowerDamageText.setVisible(false);
		mTowerDelayText.setVisible(false);
		mTowerRangeText.setVisible(false);
		
	}
	
	public void showInfo(Tower tower) {
		
		//TODO change the text
		mTowerNameText.setText(tower.mName);
		mTowerDamageText.setText("" + (int) tower.mDamage);
		mTowerDelayText.setText("" + (int) tower.mDelay);
		mTowerRangeText.setText("" + (int) tower.mRange);
		
		mInfoHUD.setVisible(true);
		mTowerIcon.setVisible(true);
		mTowerNameText.setVisible(true);
		mTowerDamageText.setVisible(true);
		mTowerDelayText.setVisible(true);
		mTowerRangeText.setVisible(true);
		
	}
	
}