package com.peth.towerdefense;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;

public class TowerDefense extends SimpleBaseGameActivity {
	
	// texture constants
	public static ITextureRegion TEXTURE_SELECTIONWHEEL;
	public static ITextureRegion TEXTURE_ENEMY_TEST;
	public static ITextureRegion TEXTURE_SPAWNPOINT;
	public static ITextureRegion TEXTURE_WAYPOINT;
	public static ITextureRegion TEXTURE_BASEPOINT;
	public static ITextureRegion TEXTURE_TOWER_TEST;
	public static ITextureRegion TEXTURE_ROUND_TEST;
	public static ITextureRegion TEXTURE_TOWER_SLOW;
	public static ITextureRegion TEXTURE_ROUND_SLOW;
	public static ITextureRegion TEXTURE_TOWER_FIRE;
	public static ITextureRegion TEXTURE_ROUND_FIRE;
	
	// game constants
	public static final int STARTING_COINS = 300;
	public static final int MAX_HEALTH = 20;
	public static final int START_DELAY = 5000;
	public static final int WAVE_DELAY_NORMAL = 40000;
	public static final int ENEMY_TEST = 0;
	
	// camera variables
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	
	// globals
	private ITextureRegion mBackgroundTextureRegion;
	public static TowerDefense mLevel;
	public int mScore;
	public Date mTime;
	public int mWavesLeft;
	public int mCoins;
	private int mHealth;
	public ArrayList<Integer> mAvailableTowers;
	public static IUpdateHandler mUpdateHandler;
	
	// fonts
	public static IFont FONT_NORMAL;
	
	// scene
	public Scene mScene;
	
	// way points
	public WayPoint mWayPoint1;
	public WayPoint mWayPoint2;
	public WayPoint mWayPoint3;
	public WayPoint mWayPoint4;
	public WayPoint mWayPoint5;
	public WayPoint mWayPoint6;
	public WayPoint mWayPoint7;
	public WayPoint mWayPoint8;
	public WayPoint mWayPoint9;
	public WayPoint mWayPoint10;
	public WayPoint mWayPoint11;
	public WayPoint mWayPoint12;
	public WayPoint mWayPoint13;
	public WayPoint mWayPoint14;
	public WayPoint mWayPoint15;
	public WayPoint mWayPoint16;
	public WayPoint mWayPoint17;
	public WayPoint mWayPoint18;
	public WayPoint mWayPoint19;
	public WayPoint mWayPoint20;
	public WayPoint mWayPoint21;
	public WayPoint mWayPoint22;
	public WayPoint mWayPoint23;
	public WayPoint mWayPoint24;
	public WayPoint mWayPoint25;
	public WayPoint mWayPoint26;
	public WayPoint mWayPoint27;
	public WayPoint mWayPoint28;
	public WayPoint mWayPoint29;
	public WayPoint mWayPoint30;
	
	// paths
	public ArrayList<WayPoint> mPath1;
	
	// waves
	public ArrayList<Integer> mWave1;
	public ArrayList<Integer> mWave2;
	public ArrayList<Integer> mWave3;
	
	// wave sets
	public ArrayList<ArrayList<Integer>> mWaveSet1;
	
	// spawn points
	public SpawnPoint mSpawnPoint1;
	
	// base points
	public BasePoint mBasePoint1;
	public BasePoint mBasePoint2;
	public BasePoint mBasePoint3;
	public BasePoint mBasePoint4;
	public BasePoint mBasePoint5;
	public BasePoint mBasePoint6;
	public BasePoint mBasePoint7;
	public BasePoint mBasePoint8;
	
	// ui text areas
	public Text mHealthText;
	public Text mCoinsText;
	public Text mScoreText;
	
	// enemies
	public ArrayList<Enemy> spawnedEnemies = new ArrayList<Enemy>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// superconstructor
		super.onCreate(savedInstanceState);
		
		// initialize variables
		mLevel = new TowerDefense();
		mLevel.mScore = 0;
		mLevel.mTime = new Date();
		mLevel.mCoins = STARTING_COINS;
		mLevel.mHealth = MAX_HEALTH;
		mLevel.mAvailableTowers = new ArrayList<Integer>();
		mLevel.mAvailableTowers.add(BasePoint.TOWER_TEST);
		mLevel.mAvailableTowers.add(BasePoint.TOWER_SLOW);
		mLevel.mAvailableTowers.add(BasePoint.TOWER_FIRE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_tower_defense, menu);
		return true;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		
		// initialize the engine with a camera
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		
	}

	@Override
	protected void onCreateResources() {
		
		try {
			
			// set up fonts
			TowerDefense.FONT_NORMAL = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 16);
			TowerDefense.FONT_NORMAL.load();
			
		    // set up bitmap textures
		    ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/background.png");
		        }
		    });
		    ITexture selectionWheelTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/selection_wheel.png");
		        }
		    });
		    ITexture enemyTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/enemy.png");
		        }
		    });
		    ITexture spawnPointTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/spawnpoint.png");
		        }
		    });
		    ITexture wayPointTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/waypoint.png");
		        }
		    });
		    ITexture basePointTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/basepoint.png");
		        }
		    });
		    ITexture towerTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/tower.png");
		        }
		    });
		    ITexture roundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/round.png");
		        }
		    });
		    ITexture slowTowerTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/tower_slow.png");
		        }
		    });
		    ITexture slowRoundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/round_slow.png");
		        }
		    });
		    ITexture fireTowerTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/tower_fire.png");
		        }
		    });
		    ITexture fireRoundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/round_fire.png");
		        }
		    });
		    
		    // load bitmap textures into VRAM
		    backgroundTexture.load();
		    selectionWheelTexture.load();
		    enemyTexture.load();
		    spawnPointTexture.load();
		    wayPointTexture.load();
		    basePointTexture.load();
		    towerTexture.load();
		    roundTexture.load();
		    slowTowerTexture.load();
		    slowRoundTexture.load();
		    fireTowerTexture.load();
		    fireRoundTexture.load();
		    
		    // set up texture regions
		    this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
		    TowerDefense.TEXTURE_SELECTIONWHEEL = TextureRegionFactory.extractFromTexture(selectionWheelTexture);
		    TowerDefense.TEXTURE_ENEMY_TEST = TextureRegionFactory.extractFromTexture(enemyTexture);
		    TowerDefense.TEXTURE_SPAWNPOINT = TextureRegionFactory.extractFromTexture(spawnPointTexture);
		    TowerDefense.TEXTURE_WAYPOINT = TextureRegionFactory.extractFromTexture(wayPointTexture);
		    TowerDefense.TEXTURE_BASEPOINT = TextureRegionFactory.extractFromTexture(basePointTexture);
		    TowerDefense.TEXTURE_TOWER_TEST = TextureRegionFactory.extractFromTexture(towerTexture);
		    TowerDefense.TEXTURE_ROUND_TEST = TextureRegionFactory.extractFromTexture(roundTexture);
		    TowerDefense.TEXTURE_TOWER_SLOW = TextureRegionFactory.extractFromTexture(slowTowerTexture);
		    TowerDefense.TEXTURE_ROUND_SLOW = TextureRegionFactory.extractFromTexture(slowRoundTexture);
		    TowerDefense.TEXTURE_TOWER_FIRE = TextureRegionFactory.extractFromTexture(fireTowerTexture);
		    TowerDefense.TEXTURE_ROUND_FIRE = TextureRegionFactory.extractFromTexture(fireRoundTexture);
		    
		} catch (IOException e) {
		    Debug.e(e);
		}
		
	}

	@Override
	protected Scene onCreateScene() {
		
		// create the scene
		mLevel.mScene = new Scene();
		Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
		mLevel.mScene.attachChild(backgroundSprite);
		
		// instantiate and place WayPoints
		mLevel.mWayPoint1 = new WayPoint(100, 430, getVertexBufferObjectManager());
		mLevel.mWayPoint2 = new WayPoint(110, 300, getVertexBufferObjectManager());
		mLevel.mWayPoint3 = new WayPoint(140, 200, getVertexBufferObjectManager());
		mLevel.mWayPoint4 = new WayPoint(165, 150, getVertexBufferObjectManager());
		mLevel.mWayPoint5 = new WayPoint(200, 118, getVertexBufferObjectManager());
		mLevel.mWayPoint6 = new WayPoint(250, 90, getVertexBufferObjectManager());
		mLevel.mWayPoint7 = new WayPoint(300, 80, getVertexBufferObjectManager());
		mLevel.mWayPoint8 = new WayPoint(400, 70, getVertexBufferObjectManager());
		mLevel.mWayPoint9 = new WayPoint(500, 80, getVertexBufferObjectManager());
		mLevel.mWayPoint10 = new WayPoint(580, 100, getVertexBufferObjectManager());
		mLevel.mWayPoint11 = new WayPoint(640, 130, getVertexBufferObjectManager());
		mLevel.mWayPoint12 = new WayPoint(670, 170, getVertexBufferObjectManager());
		mLevel.mWayPoint13 = new WayPoint(675, 200, getVertexBufferObjectManager());
		mLevel.mWayPoint14 = new WayPoint(660, 235, getVertexBufferObjectManager());
		mLevel.mWayPoint15 = new WayPoint(630, 250, getVertexBufferObjectManager());
		mLevel.mWayPoint16 = new WayPoint(580, 255, getVertexBufferObjectManager());
		mLevel.mWayPoint17 = new WayPoint(520, 250, getVertexBufferObjectManager());
		mLevel.mWayPoint18 = new WayPoint(440, 240, getVertexBufferObjectManager());
		mLevel.mWayPoint19 = new WayPoint(390, 235, getVertexBufferObjectManager());
		mLevel.mWayPoint20 = new WayPoint(330, 245, getVertexBufferObjectManager());
		mLevel.mWayPoint21 = new WayPoint(300, 275, getVertexBufferObjectManager());
		mLevel.mWayPoint22 = new WayPoint(295, 310, getVertexBufferObjectManager());
		mLevel.mWayPoint23 = new WayPoint(315, 350, getVertexBufferObjectManager());
		mLevel.mWayPoint24 = new WayPoint(365, 380, getVertexBufferObjectManager());
		mLevel.mWayPoint25 = new WayPoint(460, 400, getVertexBufferObjectManager());
		mLevel.mWayPoint26 = new WayPoint(580, 410, getVertexBufferObjectManager());
		mLevel.mWayPoint27 = new WayPoint(700, 410, getVertexBufferObjectManager());
		mLevel.mWayPoint28 = new WayPoint(815, 405, getVertexBufferObjectManager());
		
		// define paths
		mLevel.mPath1 = new ArrayList<WayPoint>();
		mLevel.mPath1.add(mLevel.mWayPoint1);
		mLevel.mPath1.add(mLevel.mWayPoint2);
		mLevel.mPath1.add(mLevel.mWayPoint3);
		mLevel.mPath1.add(mLevel.mWayPoint4);
		mLevel.mPath1.add(mLevel.mWayPoint5);
		mLevel.mPath1.add(mLevel.mWayPoint6);
		mLevel.mPath1.add(mLevel.mWayPoint7);
		mLevel.mPath1.add(mLevel.mWayPoint8);
		mLevel.mPath1.add(mLevel.mWayPoint9);
		mLevel.mPath1.add(mLevel.mWayPoint10);
		mLevel.mPath1.add(mLevel.mWayPoint11);
		mLevel.mPath1.add(mLevel.mWayPoint12);
		mLevel.mPath1.add(mLevel.mWayPoint13);
		mLevel.mPath1.add(mLevel.mWayPoint14);
		mLevel.mPath1.add(mLevel.mWayPoint15);
		mLevel.mPath1.add(mLevel.mWayPoint16);
		mLevel.mPath1.add(mLevel.mWayPoint17);
		mLevel.mPath1.add(mLevel.mWayPoint18);
		mLevel.mPath1.add(mLevel.mWayPoint19);
		mLevel.mPath1.add(mLevel.mWayPoint20);
		mLevel.mPath1.add(mLevel.mWayPoint21);
		mLevel.mPath1.add(mLevel.mWayPoint22);
		mLevel.mPath1.add(mLevel.mWayPoint23);
		mLevel.mPath1.add(mLevel.mWayPoint24);
		mLevel.mPath1.add(mLevel.mWayPoint25);
		mLevel.mPath1.add(mLevel.mWayPoint26);
		mLevel.mPath1.add(mLevel.mWayPoint27);
		mLevel.mPath1.add(mLevel.mWayPoint28);
		
		// add paths to the scene
		for (int i = 0; i < mLevel.mPath1.size(); i++) {
			mLevel.mScene.attachChild(mLevel.mPath1.get(i));
		}
		
		// define waves
		mLevel.mWave1 = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			mLevel.mWave1.add(TowerDefense.ENEMY_TEST);
		}
		mLevel.mWave2 = new ArrayList<Integer>();
		for (int i = 0; i < 40; i++) {
			mLevel.mWave2.add(TowerDefense.ENEMY_TEST);
		}
		mLevel.mWave3 = new ArrayList<Integer>();
		for (int i = 0; i < 60; i++) {
			mLevel.mWave3.add(TowerDefense.ENEMY_TEST);
		}
		
		// define wave sets
		mLevel.mWaveSet1 = new ArrayList<ArrayList<Integer>>();
		mLevel.mWaveSet1.add(mLevel.mWave1);
		mLevel.mWaveSet1.add(mLevel.mWave2);
		mLevel.mWaveSet1.add(mLevel.mWave3);
		
		// instantiate and place SpawnPoints
		mLevel.mSpawnPoint1 = new SpawnPoint(mLevel.mWaveSet1, TowerDefense.WAVE_DELAY_NORMAL, mLevel.mPath1, 103, 495, getVertexBufferObjectManager());
		mLevel.mScene.attachChild(mLevel.mSpawnPoint1);
		
		// instantiate and place BasePoints
		mLevel.mBasePoint1 = new BasePoint(215, 240, getVertexBufferObjectManager());
		mLevel.mBasePoint2 = new BasePoint(310, 165, getVertexBufferObjectManager());
		mLevel.mBasePoint3 = new BasePoint(465, 160, getVertexBufferObjectManager());
		mLevel.mBasePoint4 = new BasePoint(595, 185, getVertexBufferObjectManager());
		mLevel.mBasePoint5 = new BasePoint(240, 405, getVertexBufferObjectManager());
		mLevel.mBasePoint6 = new BasePoint(380, 310, getVertexBufferObjectManager());
		mLevel.mBasePoint7 = new BasePoint(520, 330, getVertexBufferObjectManager());
		mLevel.mBasePoint8 = new BasePoint(700, 330, getVertexBufferObjectManager());
		mLevel.mScene.attachChild(mLevel.mBasePoint1);
		mLevel.mScene.attachChild(mLevel.mBasePoint2);
		mLevel.mScene.attachChild(mLevel.mBasePoint3);
		mLevel.mScene.attachChild(mLevel.mBasePoint4);
		mLevel.mScene.attachChild(mLevel.mBasePoint5);
		mLevel.mScene.attachChild(mLevel.mBasePoint6);
		mLevel.mScene.attachChild(mLevel.mBasePoint7);
		mLevel.mScene.attachChild(mLevel.mBasePoint8);
		
		// set up gui
		mLevel.mHealthText = new Text(30, 30, TowerDefense.FONT_NORMAL, "HEALTH: " + mLevel.mHealth, 32, getVertexBufferObjectManager());
		mLevel.mCoinsText = new Text(30, 50, TowerDefense.FONT_NORMAL, "COINS: " + mLevel.mCoins, 32, getVertexBufferObjectManager());
		mLevel.mScoreText = new Text(30, 70, TowerDefense.FONT_NORMAL, "SCORE: " + mLevel.mScore, 32, getVertexBufferObjectManager());
		mLevel.mHealthText.setColor(Color.WHITE);
		mLevel.mCoinsText.setColor(Color.WHITE);
		mLevel.mScoreText.setColor(Color.WHITE);
		mLevel.mScene.attachChild(mLevel.mHealthText);
		mLevel.mScene.attachChild(mLevel.mCoinsText);
		mLevel.mScene.attachChild(mLevel.mScoreText);
		
		// register update handler
		mUpdateHandler = new IUpdateHandler() {
			
	        @Override
	        public void onUpdate(float pSecondsElapsed) {
	        	updateGUI();
	        	TowerDefense.mLevel.mScene.sortChildren();
	        }

	        @Override
	        public void reset() {
	        	
	        }
	        
		};
		mLevel.mScene.registerUpdateHandler(mUpdateHandler);
		
		// return the scene
		return mLevel.mScene;
	}
	
	// damages the player's health
	public void getDamage(int damage) {
		
		// deal damage
		mLevel.mHealth -= damage;
		
		// check if player just died
		if (dead()) {
			
			//TODO go game over
			
		}
		
	}
	
	// checks whether the player is dead
	public boolean dead() {
		return (mLevel.mHealth <= 0);
	}
	
	// updates the gui
	public void updateGUI() {
		
		mLevel.mHealthText.setText("HEALTH: " + mLevel.mHealth);
		mLevel.mCoinsText.setText("COINS: " + mLevel.mCoins);
		mLevel.mScoreText.setText("SCORE: " + mLevel.mScore);
		
	}
	
}
