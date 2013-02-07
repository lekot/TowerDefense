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
		mHealthText = new Text(65, 31, TowerDefense.FONT_NORMAL, "" + TowerDefense.mLevel.mHealth, 32, pVertexBufferObjectManager);
		mCoinsText = new Text(122, 31, TowerDefense.FONT_NORMAL, "" + TowerDefense.mLevel.mCoins, 32, pVertexBufferObjectManager);
		mWavesText = new Text(76, 54, TowerDefense.FONT_NORMAL, "WAVE " + TowerDefense.mLevel.mWaveCurrent + "/" + TowerDefense.mLevel.mWavesTotal, 32, pVertexBufferObjectManager);
		mMainHUD.setZIndex(TowerDefense.ZINDEX_HUD);
		mHealthText.setZIndex(TowerDefense.ZINDEX_HUD);
		mCoinsText.setZIndex(TowerDefense.ZINDEX_HUD);
		mWavesText.setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mLevel.mScene.attachChild(mMainHUD);
		TowerDefense.mLevel.mScene.attachChild(mHealthText);
		TowerDefense.mLevel.mScene.attachChild(mCoinsText);
		TowerDefense.mLevel.mScene.attachChild(mWavesText);
		
		// set up bottom hud
		mBottomHUD = new Sprite(0, TowerDefense.CAMERA_HEIGHT - TowerDefense.TEXTURE_HUD_BOTTOM.getHeight(), TowerDefense.TEXTURE_HUD_BOTTOM, pVertexBufferObjectManager);
		mBottomHUD.setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mLevel.mScene.attachChild(mBottomHUD);
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
		TowerDefense.mLevel.mScene.attachChild(mInfoHUD);
		TowerDefense.mLevel.mScene.attachChild(mTowerIcon);
		TowerDefense.mLevel.mScene.attachChild(mTowerNameText);
		TowerDefense.mLevel.mScene.attachChild(mTowerDamageText);
		TowerDefense.mLevel.mScene.attachChild(mTowerDelayText);
		TowerDefense.mLevel.mScene.attachChild(mTowerRangeText);
		hideInfo();
		
		// set up skills hud
		mSkillsHUD = new Sprite(10, TowerDefense.CAMERA_HEIGHT - TowerDefense.TEXTURE_HUD_SKILLS.getHeight(), TowerDefense.TEXTURE_HUD_SKILLS, pVertexBufferObjectManager);
		mSkill1 = new Sprite(mSkillsHUD.getX() + 33, TowerDefense.CAMERA_HEIGHT - 15 - TowerDefense.TEXTURE_HUD_SKILL.getHeight(), TowerDefense.TEXTURE_HUD_SKILL, pVertexBufferObjectManager);
		mSkill2 = new Sprite(mSkillsHUD.getX() + 108, TowerDefense.CAMERA_HEIGHT - 15 - TowerDefense.TEXTURE_HUD_SKILL.getHeight(), TowerDefense.TEXTURE_HUD_SKILL, pVertexBufferObjectManager);
		mSkillsHUD.setZIndex(TowerDefense.ZINDEX_HUD);
		mSkill1.setZIndex(TowerDefense.ZINDEX_HUD);
		mSkill2.setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mLevel.mScene.attachChild(mSkillsHUD);
		TowerDefense.mLevel.mScene.attachChild(mSkill1);
		TowerDefense.mLevel.mScene.attachChild(mSkill2);
		
		// set up the options hud //TODO make OptionsButton and PauseButton classes
		mOptionsButton = new Sprite(TowerDefense.CAMERA_WIDTH - 20 - TowerDefense.TEXTURE_BUTTON_OPTIONS.getWidth(), 20, TowerDefense.TEXTURE_BUTTON_OPTIONS, pVertexBufferObjectManager);
		mPauseButton = new Sprite(TowerDefense.CAMERA_WIDTH - 70 - TowerDefense.TEXTURE_BUTTON_PAUSE.getWidth(), 20, TowerDefense.TEXTURE_BUTTON_PAUSE, pVertexBufferObjectManager);
		mOptionsButton.setZIndex(TowerDefense.ZINDEX_HUD);
		mPauseButton.setZIndex(TowerDefense.ZINDEX_HUD);
		TowerDefense.mLevel.mScene.attachChild(mOptionsButton);
		TowerDefense.mLevel.mScene.attachChild(mPauseButton);
		
	}
	
	public void update() {
		
		mHealthText.setText("" + TowerDefense.mLevel.mHealth);
		mCoinsText.setText("" + TowerDefense.mLevel.mCoins);
		mWavesText.setText("WAVE " + TowerDefense.mLevel.mWaveCurrent + "/" + TowerDefense.mLevel.mWavesTotal);
		
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