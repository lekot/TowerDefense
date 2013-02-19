package com.peth.towerdefense;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
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

public class TowerDefense extends SimpleBaseGameActivity {
	
	// constants
	public static ITextureRegion BACKGROUND_TITLE;
	public static ITextureRegion TEXTURE_LOGO;
	public static ITextureRegion TEXTURE_BUTTON_START;
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
	public static ITextureRegion TEXTURE_TOWER_PEBBLE;
	public static ITextureRegion TEXTURE_TOWER_SLOW;
	public static ITextureRegion TEXTURE_TOWER_FIRE;
	public static ITextureRegion TEXTURE_TOWER_FLAMETHROWER;
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
	public static final int START_DELAY = 1000;
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
	public static TowerDefense mGame;
	public static SceneManager mSceneManager;
	public static Vibrator mVibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// initialize variables
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
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
			ITexture textureBackgroundTitle = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/background_title.png");
		        }
		    });
			ITexture textureLogo = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/logo.png");
		        }
		    });
			ITexture textureButtonStart = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/button_start.png");
		        }
		    });
		    ITexture level1Background = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/background_level1.png");
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
		            return getAssets().open("gfx/hud_start.png");
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
		    ITexture pebbleTowerTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/tower_pebble.png");
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
		    ITexture flamethrowerTowerTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/tower_flamethrower.png");
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
		    textureBackgroundTitle.load();
		    textureLogo.load();
		    textureButtonStart.load();
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
		    pebbleTowerTexture.load();
		    roundTexture.load();
		    slowTowerTexture.load();
		    slowRoundTexture.load();
		    fireTowerTexture.load();
		    fireRoundTexture.load();
		    flamethrowerTowerTexture.load();
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
		    TowerDefense.BACKGROUND_TITLE = TextureRegionFactory.extractFromTexture(textureBackgroundTitle);
		    TowerDefense.TEXTURE_LOGO = TextureRegionFactory.extractFromTexture(textureLogo);
		    TowerDefense.TEXTURE_BUTTON_START = TextureRegionFactory.extractFromTexture(textureButtonStart);
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
		    TowerDefense.TEXTURE_TOWER_PEBBLE = TextureRegionFactory.extractFromTexture(pebbleTowerTexture);
		    TowerDefense.TEXTURE_ROUND_TEST = TextureRegionFactory.extractFromTexture(roundTexture);
		    TowerDefense.TEXTURE_TOWER_SLOW = TextureRegionFactory.extractFromTexture(slowTowerTexture);
		    TowerDefense.TEXTURE_ROUND_SLOW = TextureRegionFactory.extractFromTexture(slowRoundTexture);
		    TowerDefense.TEXTURE_TOWER_FIRE = TextureRegionFactory.extractFromTexture(fireTowerTexture);
		    TowerDefense.TEXTURE_ROUND_FIRE = TextureRegionFactory.extractFromTexture(fireRoundTexture);
		    TowerDefense.TEXTURE_TOWER_FLAMETHROWER = TextureRegionFactory.extractFromTexture(flamethrowerTowerTexture);
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
		mSceneManager = new SceneManager(getVertexBufferObjectManager());
		mSceneManager.init(this);
		mSceneManager.setTitleScene();
		return mSceneManager.getCurrentScene();
	}
	
}
