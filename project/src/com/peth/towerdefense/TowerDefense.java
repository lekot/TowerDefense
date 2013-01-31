package com.peth.towerdefense;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
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

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;

public class TowerDefense extends SimpleBaseGameActivity {
	
	// background constants
	public static ITextureRegion BACKGROUND_LEVEL_1;
	
	// texture constants
	public static ITextureRegion TEXTURE_HUD_MAIN;
	public static ITextureRegion TEXTURE_HUD_SKILLS;
	public static ITextureRegion TEXTURE_VICTORY;
	public static ITextureRegion TEXTURE_DEFEAT;
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
	public static ITextureRegion TEXTURE_OPTION_ARCHER;
	public static ITextureRegion TEXTURE_OPTION_MAGICIAN;
	public static ITextureRegion TEXTURE_OPTION_INFANTRY;
	public static ITextureRegion TEXTURE_OPTION_LOCKED;
	public static ITextureRegion TEXTURE_OPTION_SELL;
	
	// game constants
	public static final int STARTING_HEALTH = 20;
	public static final int STARTING_COINS = 265;
	public static final int START_DELAY = 1000;
	public static final int WAVE_DELAY = 40000;
	public static final double SALE_RATIO = 0.5;
	
	// camera constants
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	// z-index constants
	public static final int ZINDEX_BACKGROUND = 0;
	public static final int ZINDEX_BASEPOINTS = 100;
	public static final int ZINDEX_TOWERS = 200;
	public static final int ZINDEX_ENEMIES = 300;
	public static final int ZINDEX_ROUNDS = 400;
	public static final int ZINDEX_HEALTHBARS = 900;
	public static final int ZINDEX_GUI = 1000;
	
	// tag constants
	public static final int TAG_DETACHABLE = -1;
	
	// font constants
	public static IFont FONT_NORMAL;
	public static IFont FONT_SMALL;
	
	// sound constants
	public static Sound SOUND_PROJECTILE_ARROW;
	public static Sound SOUND_TOWER_BUILD;
	public static Sound SOUND_TOWER_SELL;
	public static Sound SOUND_VICTORY;
	public static ArrayList<Sound> SOUND_ENEMY_DEATHCRY = new ArrayList<Sound>();
	
	// music constants
	public static Music MUSIC_THEME_1;
	
	// misc globals
	public static TowerDefense mLevel;
	public static Vibrator mVibrator;
	
	private Sprite mBackground;
	public Sprite mMainHud;
	public Sprite mSkillsHud;
	public int mWavesTotal;
	public int mWaveCurrent;
	public int mEnemiesFinished = -1;
	public int mEnemiesTotal = 0;
	public int mCoins;
	private int mHealth;
	public ArrayList<Integer> mAvailableTowers = new ArrayList<Integer>();
	public ArrayList<Enemy> mCurrentEnemies = new ArrayList<Enemy>();
	private IUpdateHandler mUpdateHandler;
	private Timer mWaveTimer;
	public SelectionWheel mSelectionWheel;
	
	// scene globals
	public Scene mScene;
	
	// way point globals
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
	
	// path globals
	public ArrayList<WayPoint> mPath1;
	
	// wave globals
	public ArrayList<Integer> mWave1;
	public ArrayList<Integer> mWave2;
	public ArrayList<Integer> mWave3;
	
	// wave set globals
	public ArrayList<ArrayList<Integer>> mWaveSet1;
	
	// spawn point globals
	public ArrayList<SpawnPoint> mSpawnPoints = new ArrayList<SpawnPoint>();
	public SpawnPoint mSpawnPoint1;
	public SpawnPoint mSpawnPoint2;
	
	// base point globals
	public BasePoint mBasePoint1;
	public BasePoint mBasePoint2;
	public BasePoint mBasePoint3;
	public BasePoint mBasePoint4;
	public BasePoint mBasePoint5;
	public BasePoint mBasePoint6;
	public BasePoint mBasePoint7;
	public BasePoint mBasePoint8;
	
	// gui globals
	public Text mHealthText;
	public Text mCoinsText;
	public Text mWavesText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// superconstructor
		super.onCreate(savedInstanceState);
		
		// initialize variables
		mLevel = new TowerDefense();
		mLevel.mCoins = STARTING_COINS;
		mLevel.mHealth = STARTING_HEALTH;
		mLevel.mAvailableTowers.add(Tower.TOWER_TEST);
		mLevel.mAvailableTowers.add(Tower.TOWER_SLOW);
		mLevel.mAvailableTowers.add(Tower.TOWER_FIRE);
		mLevel.mAvailableTowers.add(Tower.TOWER_BOMB);
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_tower_defense, menu);
		return true;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		
		// initialize camera
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		// create engine options
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		
		// enable sound and music
		engineOptions.getAudioOptions().setNeedsSound(true).setNeedsMusic(true);
		
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		
		try {
			
			// set up fonts
			TowerDefense.FONT_NORMAL = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 12, true, Color.WHITE_ABGR_PACKED_INT);
			TowerDefense.FONT_NORMAL.load();
			TowerDefense.FONT_SMALL = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 10, true, Color.WHITE_ABGR_PACKED_INT);
			TowerDefense.FONT_SMALL.load();
			
			// set up sounds
			SoundFactory.setAssetBasePath("sfx/");
			TowerDefense.SOUND_PROJECTILE_ARROW = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "projectile_arrow.wav");
			TowerDefense.SOUND_VICTORY = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "victory.wav");
			TowerDefense.SOUND_TOWER_BUILD = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "tower_build.wav");
			TowerDefense.SOUND_TOWER_SELL = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "tower_sell.wav");
			Sound deathCry1 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "enemy_deathcry1.wav");
			Sound deathCry2 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "enemy_deathcry2.wav");
			Sound deathCry3 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "enemy_deathcry3.wav");
			Sound deathCry4 = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "enemy_deathcry4.wav");
			TowerDefense.SOUND_ENEMY_DEATHCRY.add(deathCry1);
			TowerDefense.SOUND_ENEMY_DEATHCRY.add(deathCry2);
			TowerDefense.SOUND_ENEMY_DEATHCRY.add(deathCry3);
			TowerDefense.SOUND_ENEMY_DEATHCRY.add(deathCry4);
			
			// set up music
			MusicFactory.setAssetBasePath("bgm/");
			TowerDefense.MUSIC_THEME_1 = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "theme_1.mp3");
			TowerDefense.MUSIC_THEME_1.setLooping(true);
			
		    // set up bitmap textures
		    ITexture level1Background = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/background.png");
		        }
		    });
		    ITexture mainHudTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/hud_main.png");
		        }
		    });
		    ITexture skillsHudTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/hud_skills.png");
		        }
		    });
		    ITexture victoryTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/victory.png");
		        }
		    });
		    ITexture defeatTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/defeat.png");
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
		            return getAssets().open("gfx/tower_archer.png");
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
		            return getAssets().open("gfx/tower_magician.png");
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
		            return getAssets().open("gfx/tower_bombardier.png");
		        }
		    });
		    ITexture fireRoundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/round_fire.png");
		        }
		    });
		    ITexture archerOptionTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/option_archer.png");
		        }
		    });
		    ITexture magicianOptionTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/option_magician.png");
		        }
		    });
		    ITexture infantryOptionTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/option_infantry.png");
		        }
		    });
		    ITexture lockedOptionTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/option_locked.png");
		        }
		    });
		    ITexture sellOptionTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/option_sell.png");
		        }
		    });
		    
		    // load bitmap textures into VRAM
		    level1Background.load();
		    mainHudTexture.load();
		    skillsHudTexture.load();
		    victoryTexture.load();
		    defeatTexture.load();
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
		    archerOptionTexture.load();
		    magicianOptionTexture.load();
		    infantryOptionTexture.load();
		    lockedOptionTexture.load();
		    sellOptionTexture.load();
		    
		    // set up texture regions
		    TowerDefense.BACKGROUND_LEVEL_1 = TextureRegionFactory.extractFromTexture(level1Background);
		    TowerDefense.TEXTURE_HUD_MAIN = TextureRegionFactory.extractFromTexture(mainHudTexture);
		    TowerDefense.TEXTURE_HUD_SKILLS = TextureRegionFactory.extractFromTexture(skillsHudTexture);
		    TowerDefense.TEXTURE_VICTORY = TextureRegionFactory.extractFromTexture(victoryTexture);
		    TowerDefense.TEXTURE_DEFEAT = TextureRegionFactory.extractFromTexture(defeatTexture);
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
		    TowerDefense.TEXTURE_OPTION_ARCHER = TextureRegionFactory.extractFromTexture(archerOptionTexture);
		    TowerDefense.TEXTURE_OPTION_MAGICIAN = TextureRegionFactory.extractFromTexture(magicianOptionTexture);
		    TowerDefense.TEXTURE_OPTION_INFANTRY = TextureRegionFactory.extractFromTexture(infantryOptionTexture);
		    TowerDefense.TEXTURE_OPTION_LOCKED = TextureRegionFactory.extractFromTexture(lockedOptionTexture);
		    TowerDefense.TEXTURE_OPTION_SELL = TextureRegionFactory.extractFromTexture(sellOptionTexture);
		    
		} catch (IOException e) {
		    Debug.e(e);
		}
		
	}

	@Override
	protected Scene onCreateScene() {
		
		// create the scene
		mLevel.mScene = new Scene();
		
		// set touch area order
		mLevel.mScene.setOnAreaTouchTraversalFrontToBack();
		
		// create background
		mBackground = new Background(0, 0, TowerDefense.BACKGROUND_LEVEL_1, getVertexBufferObjectManager());
		mLevel.mScene.attachChild(mBackground);
		
		// instantiate and place WayPoints
		mLevel.mWayPoint1 = new WayPoint(375, 95, getVertexBufferObjectManager());
		mLevel.mWayPoint2 = new WayPoint(364, 122, getVertexBufferObjectManager());
		mLevel.mWayPoint3 = new WayPoint(345, 147, getVertexBufferObjectManager());
		mLevel.mWayPoint4 = new WayPoint(318, 161, getVertexBufferObjectManager());
		mLevel.mWayPoint5 = new WayPoint(251, 182, getVertexBufferObjectManager());
		mLevel.mWayPoint6 = new WayPoint(216, 199, getVertexBufferObjectManager());
		mLevel.mWayPoint7 = new WayPoint(197, 225, getVertexBufferObjectManager());
		mLevel.mWayPoint8 = new WayPoint(189, 257, getVertexBufferObjectManager());
		mLevel.mWayPoint9 = new WayPoint(191, 285, getVertexBufferObjectManager());
		mLevel.mWayPoint10 = new WayPoint(207, 316, getVertexBufferObjectManager());
		mLevel.mWayPoint11 = new WayPoint(229, 335, getVertexBufferObjectManager());
		mLevel.mWayPoint12 = new WayPoint(256, 344, getVertexBufferObjectManager());
		mLevel.mWayPoint13 = new WayPoint(291, 349, getVertexBufferObjectManager());
		mLevel.mWayPoint14 = new WayPoint(377, 347, getVertexBufferObjectManager());
		mLevel.mWayPoint15 = new WayPoint(406, 341, getVertexBufferObjectManager());
		mLevel.mWayPoint16 = new WayPoint(435, 336, getVertexBufferObjectManager());
		mLevel.mWayPoint17 = new WayPoint(460, 338, getVertexBufferObjectManager());
		mLevel.mWayPoint18 = new WayPoint(526, 360, getVertexBufferObjectManager());
		mLevel.mWayPoint19 = new WayPoint(553, 363, getVertexBufferObjectManager());
		mLevel.mWayPoint20 = new WayPoint(576, 356, getVertexBufferObjectManager());
		mLevel.mWayPoint21 = new WayPoint(596, 340, getVertexBufferObjectManager());
		mLevel.mWayPoint22 = new WayPoint(636, 300, getVertexBufferObjectManager());
		mLevel.mWayPoint23 = new WayPoint(662, 282, getVertexBufferObjectManager());
		mLevel.mWayPoint24 = new WayPoint(687, 275, getVertexBufferObjectManager());
		mLevel.mWayPoint25 = new WayPoint(830, 275, getVertexBufferObjectManager());
		
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
		
		// add waypoints to the scene
		for (int i = 0; i < mLevel.mPath1.size(); i++) {
			mLevel.mScene.attachChild(mLevel.mPath1.get(i));
		}
		
		// define waves
		mLevel.mWave1 = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			mLevel.mWave1.add(Enemy.ENEMY_TEST);
		}
		mLevel.mWave2 = new ArrayList<Integer>();
		for (int i = 0; i < 17; i++) {
			mLevel.mWave2.add(Enemy.ENEMY_TEST);
		}
		mLevel.mWave3 = new ArrayList<Integer>();
		for (int i = 0; i < 36; i++) {
			mLevel.mWave3.add(Enemy.ENEMY_TEST);
		}
		
		// define wave sets
		mLevel.mWaveSet1 = new ArrayList<ArrayList<Integer>>();
		mLevel.mWaveSet1.add(mLevel.mWave1);
		mLevel.mWaveSet1.add(mLevel.mWave2);
		mLevel.mWaveSet1.add(mLevel.mWave3);
		
		// instantiate and place SpawnPoints
		mLevel.mSpawnPoint1 = new SpawnPoint(mLevel.mWaveSet1, mLevel.mPath1, 375, -30, getVertexBufferObjectManager());
		mLevel.mSpawnPoints.add(mLevel.mSpawnPoint1);
		mLevel.mScene.attachChild(mLevel.mSpawnPoint1);
		
		// initialize the number of waves variables
		for (int i = 0; i < mLevel.mSpawnPoints.size(); i++) {
			if (mLevel.mSpawnPoints.get(i).mWaveSet.size() > mLevel.mWavesTotal) {
				mLevel.mWavesTotal = mLevel.mSpawnPoints.get(i).mWaveSet.size();
			}
		}
		
		// initialize the number of enemies variables
		for (int i = 0; i < mLevel.mSpawnPoints.size(); i++) {
			ArrayList<ArrayList<Integer>> waveSet = mLevel.mSpawnPoints.get(i).mWaveSet;
			for (int j = 0; j < waveSet.size(); j++) {
				mLevel.mEnemiesTotal += waveSet.get(j).size();
			}
		}
		mLevel.mEnemiesFinished = 0;
		
		// instantiate and place BasePoints
		mLevel.mBasePoint1 = new BasePoint(275, 110, getVertexBufferObjectManager());
		mLevel.mBasePoint2 = new BasePoint(202, 133, getVertexBufferObjectManager());
		mLevel.mBasePoint3 = new BasePoint(288, 232, getVertexBufferObjectManager());
		mLevel.mBasePoint4 = new BasePoint(283, 291, getVertexBufferObjectManager());
		mLevel.mBasePoint5 = new BasePoint(369, 290, getVertexBufferObjectManager());
		mLevel.mBasePoint6 = new BasePoint(442, 388, getVertexBufferObjectManager());
		mLevel.mBasePoint7 = new BasePoint(533, 303, getVertexBufferObjectManager());
		mLevel.mBasePoint8 = new BasePoint(670, 363, getVertexBufferObjectManager());
		mLevel.mScene.attachChild(mLevel.mBasePoint1);
		mLevel.mScene.attachChild(mLevel.mBasePoint2);
		mLevel.mScene.attachChild(mLevel.mBasePoint3);
		mLevel.mScene.attachChild(mLevel.mBasePoint4);
		mLevel.mScene.attachChild(mLevel.mBasePoint5);
		mLevel.mScene.attachChild(mLevel.mBasePoint6);
		mLevel.mScene.attachChild(mLevel.mBasePoint7);
		mLevel.mScene.attachChild(mLevel.mBasePoint8);
		
		// set up gui
		mLevel.mMainHud = new Sprite(30, 30, TowerDefense.TEXTURE_HUD_MAIN, getVertexBufferObjectManager());
		mLevel.mScene.attachChild(mLevel.mMainHud);
		mLevel.mHealthText = new Text(75, 41, TowerDefense.FONT_NORMAL, "" + mLevel.mHealth, 32, getVertexBufferObjectManager());
		mLevel.mCoinsText = new Text(132, 41, TowerDefense.FONT_NORMAL, "" + mLevel.mCoins, 32, getVertexBufferObjectManager());
		mLevel.mWavesText = new Text(86, 64, TowerDefense.FONT_NORMAL, "WAVE " + mLevel.mWaveCurrent + "/" + mLevel.mWavesTotal, 32, getVertexBufferObjectManager());
		mLevel.mScene.attachChild(mLevel.mHealthText);
		mLevel.mScene.attachChild(mLevel.mCoinsText);
		mLevel.mScene.attachChild(mLevel.mWavesText);
		mLevel.mSkillsHud = new Sprite(0, CAMERA_HEIGHT - TEXTURE_HUD_SKILLS.getHeight(), TowerDefense.TEXTURE_HUD_SKILLS, getVertexBufferObjectManager());
		mLevel.mScene.attachChild(mLevel.mSkillsHud);
		
		// register update handler
		mUpdateHandler = new IUpdateHandler() {
			
	        @Override
	        public synchronized void onUpdate(float pSecondsElapsed) {
	        	updateGUI();
	        	detachGarbage();
	        	TowerDefense.mLevel.mScene.sortChildren();
	        	checkDead();
	        	checkVictory();
	        }

	        @Override
	        public void reset() {
	        	
	        }
	        
		};
		mLevel.mScene.registerUpdateHandler(mUpdateHandler);
		
		// start level
		start();
		
		// return the scene
		return mLevel.mScene;
	}
	
	public void start() {
		
		// start music
		TowerDefense.MUSIC_THEME_1.play();
		
		// start wave timer
        mLevel.mWaveTimer = new Timer();
        mLevel.mWaveTimer.schedule(new WaveTask(), START_DELAY, WAVE_DELAY);
		
	}
	
	class WaveTask extends TimerTask {

		@Override
		public void run() {
			
			if (mLevel.mWaveCurrent < mLevel.mWavesTotal) {
				mLevel.mWaveCurrent++;
				for (int i = 0; i < mLevel.mSpawnPoints.size(); i++) {
					mLevel.mSpawnPoints.get(i).launchWave(mLevel.mWaveCurrent - 1);
				}
			} else {
				mLevel.mWaveTimer.cancel();
			}
			
		}
		
	}
	
	public synchronized void detachGarbage() {
		
		// loop through children
		for (int i = 0; i < mLevel.mScene.getChildCount(); i++) {
			
			// get referral to child
			IEntity child = mLevel.mScene.getChildByIndex(i);
			
			// detach child
			if (child.getTag() == TAG_DETACHABLE) {
				mLevel.mScene.detachChild(child);
			}
			
		}
		
	}
	
	// damages the player's health
	public void getDamage(int damage) {
		
		// deal damage
		mLevel.mHealth -= damage;
		
	}
	
	// checks whether the player is dead
	public void checkDead() {
		
		if (dead()) {
			Sprite defeatSprite = new Sprite(CAMERA_WIDTH/2 - TEXTURE_DEFEAT.getWidth()/2, CAMERA_HEIGHT/2 - 100 - TEXTURE_DEFEAT.getHeight()/2, TowerDefense.TEXTURE_DEFEAT, getVertexBufferObjectManager());
			mLevel.mScene.attachChild(defeatSprite);
			clearScene(mLevel.mScene);
		}
		
	}
	
	public boolean dead() {
		return (mLevel.mHealth <= 0);
	}
	
	public void clearScene(Scene scene) {
		mLevel.mWaveTimer.cancel();
		for (int i = 0; i < scene.getChildCount(); i++) {
			IEntity child = scene.getChildByIndex(i);
			if (child instanceof Enemy) {
				((Enemy) child).mState = Enemy.STATE_DEAD;
				child.setTag(TAG_DETACHABLE);
			} else if (child instanceof SpawnPoint) {
				((SpawnPoint) child).mActive = false;
			} else if (child instanceof Round || child instanceof SelectionWheel || child instanceof Option) {
				child.setTag(TAG_DETACHABLE);
			} else if (child instanceof BasePoint) {
				mLevel.mScene.unregisterTouchArea((ITouchArea) child);
			}
		}
		scene.setIgnoreUpdate(true);
	}
	
	// checks whether the player has won
	public void checkVictory() {
		
		if (!dead() && mLevel.mEnemiesFinished == mLevel.mEnemiesTotal) {
			clearScene(mLevel.mScene);
			Sprite victorySprite = new Sprite(CAMERA_WIDTH/2 - TEXTURE_VICTORY.getWidth()/2, CAMERA_HEIGHT/2 - TEXTURE_VICTORY.getHeight()/2, TowerDefense.TEXTURE_VICTORY, getVertexBufferObjectManager());
			mLevel.mScene.attachChild(victorySprite);
			TowerDefense.MUSIC_THEME_1.stop();
			TowerDefense.SOUND_VICTORY.play();
		}
		
	}
	
	// updates the gui
	public void updateGUI() {
		
		mLevel.mHealthText.setText("" + mLevel.mHealth);
		mLevel.mCoinsText.setText("" + mLevel.mCoins);
		mLevel.mWavesText.setText("WAVE " + mLevel.mWaveCurrent + "/" + mLevel.mWavesTotal);
		
	}
	
	public void hideSelectionWheel() {
		
		if (mSelectionWheel != null) {
			mSelectionWheel.hide();
			mSelectionWheel = null;
		}
		
	}
	
}
