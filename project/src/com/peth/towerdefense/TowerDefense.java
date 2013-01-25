package com.peth.towerdefense;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.andengine.engine.camera.Camera;
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
	
	// constants
	public static final int STARTING_COINS = 200;
	public static final int MAX_HEALTH = 5;
	public static final int START_DELAY = 5000;
	public static final int WAVE_DELAY_NORMAL = 40000;
	public static final int ENEMY_TEST = 0;
	
	// camera variables
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	
	// globals
	public static int mScore;
	public Date mTime;
	public static int mWavesLeft;
	public static int mCoins;
	private static int mHealth;
	
	// fonts
	public static IFont FONT_NORMAL;
	
	// textures
	private ITextureRegion mBackgroundTextureRegion;
	public static ITextureRegion enemyTextureRegion;
	public static ITextureRegion spawnPointTextureRegion;
	public static ITextureRegion wayPointTextureRegion;
	public static ITextureRegion basePointTextureRegion;
	public static ITextureRegion towerTextureRegion;
	public static ITextureRegion roundTextureRegion;
	public static ITextureRegion TEXTURE_TOWER_SLOW;
	public static ITextureRegion TEXTURE_ROUND_SLOW;
	
	// scene
	public static Scene scene;
	
	// way points
	public static WayPoint mWayPoint1;
	public static WayPoint mWayPoint2;
	public static WayPoint mWayPoint3;
	public static WayPoint mWayPoint4;
	public static WayPoint mWayPoint5;
	
	// paths
	public static ArrayList<WayPoint> mPath1;
	
	// waves
	public static ArrayList<Integer> mWave1;
	public static ArrayList<Integer> mWave2;
	public static ArrayList<Integer> mWave3;
	
	// wave sets
	public static ArrayList<ArrayList<Integer>> mWaveSet1;
	
	// spawn points
	public static SpawnPoint mSpawnPoint1;
	
	// base points
	public static BasePoint mBasePoint1;
	public static BasePoint mBasePoint2;
	public static BasePoint mBasePoint3;
	public static BasePoint mBasePoint4;
	public static BasePoint mBasePoint5;
	public static BasePoint mBasePoint6;
	public static BasePoint mBasePoint7;
	public static BasePoint mBasePoint8;
	
	// ui text areas
	public static Text mHealthText;
	public static Text mCoinsText;
	public static Text mScoreText;
	
	// enemies
	public static ArrayList<Enemy> spawnedEnemies = new ArrayList<Enemy>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// superconstructor
		super.onCreate(savedInstanceState);
		
		// initialize variables
		mScore = 0;
		mTime = new Date();
		mCoins = STARTING_COINS;
		mHealth = MAX_HEALTH;
		
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
		    
		    // load bitmap textures into VRAM
		    backgroundTexture.load();
		    enemyTexture.load();
		    spawnPointTexture.load();
		    wayPointTexture.load();
		    basePointTexture.load();
		    towerTexture.load();
		    roundTexture.load();
		    slowTowerTexture.load();
		    slowRoundTexture.load();
		    
		    // set up texture regions
		    this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
		    TowerDefense.enemyTextureRegion = TextureRegionFactory.extractFromTexture(enemyTexture);
		    TowerDefense.spawnPointTextureRegion = TextureRegionFactory.extractFromTexture(spawnPointTexture);
		    TowerDefense.wayPointTextureRegion = TextureRegionFactory.extractFromTexture(wayPointTexture);
		    TowerDefense.basePointTextureRegion = TextureRegionFactory.extractFromTexture(basePointTexture);
		    TowerDefense.towerTextureRegion = TextureRegionFactory.extractFromTexture(towerTexture);
		    TowerDefense.roundTextureRegion = TextureRegionFactory.extractFromTexture(roundTexture);
		    TowerDefense.TEXTURE_TOWER_SLOW = TextureRegionFactory.extractFromTexture(slowTowerTexture);
		    TowerDefense.TEXTURE_ROUND_SLOW = TextureRegionFactory.extractFromTexture(slowRoundTexture);
		    
		} catch (IOException e) {
		    Debug.e(e);
		}
		
	}

	@Override
	protected Scene onCreateScene() {
		
		// create the scene
		scene = new Scene();
		Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
		scene.attachChild(backgroundSprite);
		
		// instantiate and place WayPoints
		mWayPoint1 = new WayPoint(665, 95, getVertexBufferObjectManager());
		mWayPoint2 = new WayPoint(618, 248, getVertexBufferObjectManager());
		mWayPoint3 = new WayPoint(122, 240, getVertexBufferObjectManager());
		mWayPoint4 = new WayPoint(134, 386, getVertexBufferObjectManager());
		mWayPoint5 = new WayPoint(800, 376, getVertexBufferObjectManager());
		scene.attachChild(mWayPoint1);
		scene.attachChild(mWayPoint2);
		scene.attachChild(mWayPoint3);
		scene.attachChild(mWayPoint4);
		scene.attachChild(mWayPoint5);
		
		// define paths
		mPath1 = new ArrayList<WayPoint>();
		mPath1.add(mWayPoint1);
		mPath1.add(mWayPoint2);
		mPath1.add(mWayPoint3);
		mPath1.add(mWayPoint4);
		mPath1.add(mWayPoint5);
		
		// define waves
		mWave1 = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			mWave1.add(TowerDefense.ENEMY_TEST);
		}
		mWave2 = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			mWave2.add(TowerDefense.ENEMY_TEST);
		}
		mWave3 = new ArrayList<Integer>();
		for (int i = 0; i < 30; i++) {
			mWave3.add(TowerDefense.ENEMY_TEST);
		}
		
		// define wave sets
		mWaveSet1 = new ArrayList<ArrayList<Integer>>();
		mWaveSet1.add(mWave1);
		mWaveSet1.add(mWave2);
		mWaveSet1.add(mWave3);
		
		// instantiate and place SpawnPoints
		mSpawnPoint1 = new SpawnPoint(mWaveSet1, TowerDefense.WAVE_DELAY_NORMAL, mPath1, -25, 110, getVertexBufferObjectManager());
		scene.attachChild(mSpawnPoint1);
		
		// instantiate and place BasePoints
		mBasePoint1 = new BasePoint(200, 165, getVertexBufferObjectManager());
		mBasePoint2 = new BasePoint(300, 165, getVertexBufferObjectManager());
		mBasePoint3 = new BasePoint(450, 165, getVertexBufferObjectManager());
		mBasePoint4 = new BasePoint(550, 165, getVertexBufferObjectManager());
		mBasePoint5 = new BasePoint(200, 305, getVertexBufferObjectManager());
		mBasePoint6 = new BasePoint(300, 305, getVertexBufferObjectManager());
		mBasePoint7 = new BasePoint(450, 305, getVertexBufferObjectManager());
		mBasePoint8 = new BasePoint(550, 305, getVertexBufferObjectManager());
		scene.attachChild(mBasePoint1);
		scene.attachChild(mBasePoint2);
		scene.attachChild(mBasePoint3);
		scene.attachChild(mBasePoint4);
		scene.attachChild(mBasePoint5);
		scene.attachChild(mBasePoint6);
		scene.attachChild(mBasePoint7);
		scene.attachChild(mBasePoint8);
		
		// set up gui
		mHealthText = new Text(30, 30, TowerDefense.FONT_NORMAL, "HEALTH: " + mHealth, 32, getVertexBufferObjectManager());
		mCoinsText = new Text(150, 30, TowerDefense.FONT_NORMAL, "COINS: " + mCoins, 32, getVertexBufferObjectManager());
		mScoreText = new Text(270, 30, TowerDefense.FONT_NORMAL, "SCORE: " + mScore, 32, getVertexBufferObjectManager());
		mHealthText.setColor(Color.WHITE);
		mCoinsText.setColor(Color.WHITE);
		mScoreText.setColor(Color.WHITE);
		scene.attachChild(mHealthText);
		scene.attachChild(mCoinsText);
		scene.attachChild(mScoreText);
		
		// return the scene
		return scene;
	}
	
	// damages the player's health
	public static void getDamage(int damage) {
		
		// deal damage
		mHealth -= damage;
		
		// check if player just died
		if (dead()) {
			
			//TODO go game over
			
		}
		
	}
	
	// checks whether the player is dead
	public static boolean dead() {
		return (mHealth <= 0);
	}
	
	public static void updateGUI() {
		
		mHealthText.setText("HEALTH: " + mHealth);
		mCoinsText.setText("COINS: " + mCoins);
		mScoreText.setText("SCORE: " + mScore);
		
	}

}
