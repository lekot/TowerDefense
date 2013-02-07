package com.peth.towerdefense;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
	
	// constants
	public static ITextureRegion BACKGROUND_LEVEL_1;
	public static ITextureRegion TEXTURE_HUD_MAIN;
	public static ITextureRegion TEXTURE_HUD_BOTTOM;
	public static ITextureRegion TEXTURE_HUD_INFO;
	public static ITextureRegion TEXTURE_HUD_SKILLS;
	public static ITextureRegion TEXTURE_HUD_SKILL;
	public static ITextureRegion TEXTURE_HUD_START;
	public static ITextureRegion TEXTURE_VICTORY;
	public static ITextureRegion TEXTURE_DEFEAT;
	public static ITextureRegion TEXTURE_SELECTIONWHEEL;
	public static ITextureRegion TEXTURE_ENEMY_TEST;
	public static ITextureRegion TEXTURE_SPAWNPOINT;
	public static ITextureRegion TEXTURE_WAYPOINT;
	public static ITextureRegion TEXTURE_BASEPOINT;
	public static ITextureRegion TEXTURE_TOWER_TEST;
	public static ITextureRegion TEXTURE_TOWER_SLOW;
	public static ITextureRegion TEXTURE_TOWER_FIRE;
	public static ITextureRegion TEXTURE_ROUND_TEST;
	public static ITextureRegion TEXTURE_RANGECIRCLE;
	public static ITextureRegion TEXTURE_ROUND_SLOW;
	public static ITextureRegion TEXTURE_ROUND_FIRE;
	public static ITextureRegion TEXTURE_ICON_TEST;
	public static ITextureRegion TEXTURE_OPTION_ARCHER;
	public static ITextureRegion TEXTURE_OPTION_MAGICIAN;
	public static ITextureRegion TEXTURE_OPTION_MAGICIAN_UNAVAILABLE;
	public static ITextureRegion TEXTURE_OPTION_INFANTRY;
	public static ITextureRegion TEXTURE_OPTION_LOCKED;
	public static ITextureRegion TEXTURE_OPTION_SELL;
	public static ITextureRegion TEXTURE_OPTION_UPGRADE;
	public static ITextureRegion TEXTURE_PRICESIGN;
	public static ITextureRegion TEXTURE_BUTTON_WAVE;
	public static ITextureRegion TEXTURE_BUTTON_PAUSE;
	public static ITextureRegion TEXTURE_BUTTON_OPTIONS;
	public static final int STARTING_HEALTH = 20;
	public static final int STARTING_COINS = 265;
	public static final int START_DELAY = 1000;
	public static final float WAVE_DELAY = 40; // in seconds
	public static final double SALE_RATIO = 0.5;
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	public static final int ZINDEX_BACKGROUND = 0;
	public static final int ZINDEX_BASEPOINTS = 100;
	public static final int ZINDEX_TOWERS = 200;
	public static final int ZINDEX_ENEMIES = 300;
	public static final int ZINDEX_ROUNDS = 400;
	public static final int ZINDEX_HEALTHBARS = 900;
	public static final int ZINDEX_HUD = 1000;
	public static final int TAG_DETACHABLE = -1;
	public static IFont FONT_NORMAL;
	public static IFont FONT_SMALL;
	public static Sound SOUND_PROJECTILE_ARROW;
	public static Sound SOUND_TOWER_BUILD;
	public static Sound SOUND_COINS;
	public static Sound SOUND_WAVE;
	public static Sound SOUND_VICTORY;
	public static ArrayList<Sound> SOUND_ENEMY_DEATHCRY = new ArrayList<Sound>();
	public static Music MUSIC_PEACE;
	public static Music MUSIC_WAR;
	public static Music MUSIC_BOSS;
	public static float PERSPECTIVE = 0.75f;
	
	// globals
	public static TowerDefense mLevel;
	public static Vibrator mVibrator;
	public static HUD mHUD;
	
	// level globals //TODO these shouldn't be here, make an abstract Level extends Scene class and a Player class and move all this shit there.
	public Scene mScene;
	public Background mBackground;
	public int mWavesTotal;
	public int mWaveCurrent;
	public int mEnemiesFinished = -1;
	public int mEnemiesTotal = 0;
	public int mCoins;
	public int mHealth;
	public ArrayList<Integer> mAvailableTowers = new ArrayList<Integer>();
	public ArrayList<Enemy> mCurrentEnemies = new ArrayList<Enemy>();
	private IUpdateHandler mUpdateHandler;
	public WaveTimer mWaveTimer;
	public SelectionWheel mSelectionWheel;
	public Music mMusic;
	public boolean mStarted;
	public boolean mEnded;
	public IEntity mSelection;
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
	public ArrayList<WayPoint> mPath1;
	public ArrayList<Integer> mWave1 = new ArrayList<Integer>();;
	public ArrayList<Integer> mWave2 = new ArrayList<Integer>();;
	public ArrayList<Integer> mWave3 = new ArrayList<Integer>();;
	public ArrayList<Integer> mWave4 = new ArrayList<Integer>();;
	public ArrayList<Integer> mWave5 = new ArrayList<Integer>();;
	public ArrayList<ArrayList<Integer>> mWaveSet1;
	public ArrayList<SpawnPoint> mSpawnPoints = new ArrayList<SpawnPoint>();
	public SpawnPoint mSpawnPoint1;
	public SpawnPoint mSpawnPoint2;
	public BasePoint mBasePoint1;
	public BasePoint mBasePoint2;
	public BasePoint mBasePoint3;
	public BasePoint mBasePoint4;
	public BasePoint mBasePoint5;
	public BasePoint mBasePoint6;
	public BasePoint mBasePoint7;
	public BasePoint mBasePoint8;
	
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
			TowerDefense.SOUND_WAVE = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "wave.wav");
			TowerDefense.SOUND_TOWER_BUILD = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "tower_build.wav");
			TowerDefense.SOUND_COINS = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "tower_sell.wav");
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
			TowerDefense.MUSIC_PEACE = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "peace.mp3");
			TowerDefense.MUSIC_PEACE.setLooping(true);
			TowerDefense.MUSIC_WAR = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "war.mp3");
			TowerDefense.MUSIC_WAR.setLooping(true);
			TowerDefense.MUSIC_BOSS = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "boss.mp3");
			TowerDefense.MUSIC_BOSS.setLooping(true);
			
		    // set up bitmap textures
		    ITexture level1Background = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/background.png");
		        }
		    });
		    ITexture textureHUDMain = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/hud_main.png");
		        }
		    });
		    ITexture textureHUDBottom = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/hud_bottom.png");
		        }
		    });
		    ITexture textureHUDInfo = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/hud_info.png");
		        }
		    });
		    ITexture textureHUDSkills = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/hud_skills.png");
		        }
		    });
		    ITexture textureHUDSkill = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/hud_skill.png");
		        }
		    });
		    ITexture textureHUDStart = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/button_start.png");
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
		    ITexture textureOptionMagicianUnavailable = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/option_magician_unable.png");
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
		    ITexture textureOptionUpgrade = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/option_upgrade.png");
		        }
		    });
		    ITexture texturePriceSign = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/price_sign.png");
		        }
		    });
		    ITexture textureButtonWave = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/button_wave_timer.png");
		        }
		    });
		    ITexture textureIconTest = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/icon_archer.png");
		        }
		    });
		    ITexture textureRangeCircle = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/range_circle.png");
		        }
		    });
		    ITexture textureButtonPause = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/button_pause.png");
		        }
		    });
		    ITexture textureButtonOptions = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/button_options.png");
		        }
		    });
		    
		    // load bitmap textures into VRAM
		    level1Background.load();
		    textureHUDMain.load();
		    textureHUDBottom.load();
		    textureHUDInfo.load();
		    textureHUDSkills.load();
		    textureHUDSkill.load();
		    textureHUDStart.load();
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
		    textureOptionMagicianUnavailable.load();
		    infantryOptionTexture.load();
		    lockedOptionTexture.load();
		    sellOptionTexture.load();
		    textureOptionUpgrade.load();
		    texturePriceSign.load();
		    textureButtonWave.load();
		    textureIconTest.load();
		    textureRangeCircle.load();
		    textureButtonPause.load();
		    textureButtonOptions.load();
		    
		    // set up texture regions
		    TowerDefense.BACKGROUND_LEVEL_1 = TextureRegionFactory.extractFromTexture(level1Background);
		    TowerDefense.TEXTURE_HUD_MAIN = TextureRegionFactory.extractFromTexture(textureHUDMain);
		    TowerDefense.TEXTURE_HUD_BOTTOM = TextureRegionFactory.extractFromTexture(textureHUDBottom);
		    TowerDefense.TEXTURE_HUD_INFO = TextureRegionFactory.extractFromTexture(textureHUDInfo);
		    TowerDefense.TEXTURE_HUD_SKILLS = TextureRegionFactory.extractFromTexture(textureHUDSkills);
		    TowerDefense.TEXTURE_HUD_SKILL = TextureRegionFactory.extractFromTexture(textureHUDSkill);
		    TowerDefense.TEXTURE_HUD_START = TextureRegionFactory.extractFromTexture(textureHUDStart);
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
		    TowerDefense.TEXTURE_OPTION_MAGICIAN_UNAVAILABLE = TextureRegionFactory.extractFromTexture(textureOptionMagicianUnavailable);
		    TowerDefense.TEXTURE_OPTION_INFANTRY = TextureRegionFactory.extractFromTexture(infantryOptionTexture);
		    TowerDefense.TEXTURE_OPTION_LOCKED = TextureRegionFactory.extractFromTexture(lockedOptionTexture);
		    TowerDefense.TEXTURE_OPTION_SELL = TextureRegionFactory.extractFromTexture(sellOptionTexture);
		    TowerDefense.TEXTURE_OPTION_UPGRADE = TextureRegionFactory.extractFromTexture(textureOptionUpgrade);
		    TowerDefense.TEXTURE_PRICESIGN = TextureRegionFactory.extractFromTexture(texturePriceSign);
		    TowerDefense.TEXTURE_BUTTON_WAVE = TextureRegionFactory.extractFromTexture(textureButtonWave);
		    TowerDefense.TEXTURE_ICON_TEST = TextureRegionFactory.extractFromTexture(textureIconTest);
		    TowerDefense.TEXTURE_RANGECIRCLE = TextureRegionFactory.extractFromTexture(textureRangeCircle);
		    TowerDefense.TEXTURE_BUTTON_PAUSE = TextureRegionFactory.extractFromTexture(textureButtonPause);
		    TowerDefense.TEXTURE_BUTTON_OPTIONS = TextureRegionFactory.extractFromTexture(textureButtonOptions);
		    
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

		// define waves
		for (int i = 0; i < 6; i++) {
			mLevel.mWave1.add(Enemy.ENEMY_TEST);
		}
		for (int i = 0; i < 9; i++) {
			mLevel.mWave2.add(Enemy.ENEMY_TEST);
		}
		for (int i = 0; i < 17; i++) {
			mLevel.mWave3.add(Enemy.ENEMY_TEST);
		}
		for (int i = 0; i < 36; i++) {
			mLevel.mWave4.add(Enemy.ENEMY_TEST);
		}
		for (int i = 0; i < 50; i++) {
			mLevel.mWave5.add(Enemy.ENEMY_TEST);
		}
		
		// define wave sets
		mLevel.mWaveSet1 = new ArrayList<ArrayList<Integer>>();
		mLevel.mWaveSet1.add(mLevel.mWave1);
		mLevel.mWaveSet1.add(mLevel.mWave2);
		mLevel.mWaveSet1.add(mLevel.mWave3);
		mLevel.mWaveSet1.add(mLevel.mWave4);
		mLevel.mWaveSet1.add(mLevel.mWave5);

		// instantiate and place SpawnPoints
		mLevel.mSpawnPoint1 = new SpawnPoint(mLevel.mWaveSet1, mLevel.mPath1, 375, -30, getVertexBufferObjectManager());
		mLevel.mSpawnPoints.add(mLevel.mSpawnPoint1);

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
		
		// set up the hud
		mHUD = new HUD(getVertexBufferObjectManager());
		
		// register update handler
		mLevel.mUpdateHandler = new IUpdateHandler() {
			
	        @Override
	        public void onUpdate(float pSecondsElapsed) {
	        	mHUD.update();
	        	mLevel.mScene.sortChildren();
	        	detachGarbage();
	        	if (!mLevel.mEnded) {
	        		checkVictory();
	        	}
	        }

	        @Override
	        public void reset() {
	        	
	        }
	        
		};
		mLevel.mScene.registerUpdateHandler(mLevel.mUpdateHandler);
		
		// play music
		mLevel.playMusic(TowerDefense.MUSIC_PEACE);
		
		// return the scene
		return mLevel.mScene;
	}
	
	public void start() {
		
		mLevel.mStarted = true;
		mLevel.playMusic(TowerDefense.MUSIC_WAR);
		mLevel.mWaveTimer = new WaveTimer(WAVE_DELAY);
		mLevel.mScene.registerUpdateHandler(mLevel.mWaveTimer);
		
	}
	
	public void detachGarbage() {
		
		//TODO technically, this should be synchronized with the scene's list of children, but thi dont have acces to that. when
		// i extend Scene to make the (abstract) Level class, put this function in its body and change the syncronization to
		// lock the mChildren list. each level can then be a subclass of Level.
		synchronized (mLevel.mScene) {
		
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
		
	}
	
	public void getDamage(int damage) {
		
		// deal damage
		mLevel.mHealth -= damage;
		
	}
	
	public boolean isDead() {
		
		if (mLevel.mHealth <= 0) {
			stop();
			Sprite defeatSprite = new Sprite(CAMERA_WIDTH/2 - TEXTURE_DEFEAT.getWidth()/2, CAMERA_HEIGHT/2 - 100 - TEXTURE_DEFEAT.getHeight()/2, TowerDefense.TEXTURE_DEFEAT, getVertexBufferObjectManager());
			defeatSprite.setZIndex(TowerDefense.ZINDEX_HUD);
			mLevel.mScene.attachChild(defeatSprite);
			return true;
		}
		return false;
		
	}
	
	public void stop() {
		for (int i = 0; i < mLevel.mScene.getChildCount(); i++) {
			IEntity child = mLevel.mScene.getChildByIndex(i);
			if (child instanceof Enemy) {
				((Enemy) child).mState = Enemy.STATE_DEAD;
				child.setTag(TAG_DETACHABLE);
			} else if (child instanceof SpawnPoint) {
				((SpawnPoint) child).mActive = false;
			} else if (child instanceof Round || child instanceof SelectionWheel || child instanceof Option) {
				child.setTag(TAG_DETACHABLE);
			} else if (child instanceof BasePoint || child instanceof Tower) {
				mLevel.mScene.unregisterTouchArea((ITouchArea) child);
			}
		}
		TowerDefense.mLevel.stopMusic();
		mLevel.mEnded = true;
	}
	
	public void checkVictory() {
		
		if (!isDead() && mLevel.mEnemiesFinished == mLevel.mEnemiesTotal) {
			stop();
			Sprite victorySprite = new Sprite(CAMERA_WIDTH/2 - TEXTURE_VICTORY.getWidth()/2, CAMERA_HEIGHT/2 - TEXTURE_VICTORY.getHeight()/2, TowerDefense.TEXTURE_VICTORY, getVertexBufferObjectManager());
			victorySprite.setZIndex(TowerDefense.ZINDEX_HUD);
			mLevel.mScene.attachChild(victorySprite);
			TowerDefense.SOUND_VICTORY.play();
		}
		
	}
	
	public void playMusic(Music music) {
		if (mLevel.mMusic != null) {
			mLevel.mMusic.stop();
		}
		mLevel.mMusic = music;
		mLevel.mMusic.play();
	}
	
	public void stopMusic() {
		mLevel.mMusic.stop();
		mLevel.mMusic = null;
	}
	
	public void unselect() {
		mLevel.hideSelectionWheel();
		mHUD.hideInfo();
		if (mLevel.mSelection != null) {
			if (mLevel.mSelection instanceof Tower) {
				((Tower) mLevel.mSelection).mRangeCircle.setVisible(false);
			}
		}
		mLevel.mSelection = null;
	}
	
	public void hideSelectionWheel() {
		
		if (mSelectionWheel != null) {
			mSelectionWheel.hide();
			mSelectionWheel = null;
		}
		
	}
	
}
