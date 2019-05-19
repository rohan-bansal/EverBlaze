package main.java.com.rohan.everblaze.Levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import main.java.com.rohan.everblaze.Effects.ScreenText;
import main.java.com.rohan.everblaze.Entities.*;
import main.java.com.rohan.everblaze.FileUtils.GameManager;
import main.java.com.rohan.everblaze.ControllerLib.FollowCam;
import main.java.com.rohan.everblaze.ControllerLib.PS3_Controller;
import main.java.com.rohan.everblaze.Debugger;
import main.java.com.rohan.everblaze.Effects.Sound_Effects;
import main.java.com.rohan.everblaze.TileInteraction.CollisionDetector;
import main.java.com.rohan.everblaze.TileInteraction.HUD;

import java.util.ArrayList;

public class World implements Screen {

    private TiledMap map;
    private AssetManager manager;
    private SpriteBatch batch;
    private SpriteBatch pauseBatch;
    private SpriteBatch overwriteBatch;
    private SpriteBatch itemBatch;

    public static CollisionDetector detector;
    private HUD hud;
    private FollowCam cam;
    private Player player;
    private Debugger debugger;
    public GameManager gameManager;
    public static Sound_Effects levelMusic;
    public static ScreenText drawManager;

    public static ArrayList<Enemy> enemies;
    public static ArrayList<Item> onFloor;

    private boolean pauseMenuActive = false;
    private boolean overwriteMenuActive = false;

    private Game game;

    public static boolean movingRight, movingLeft, movingUp, movingDown;
    private Sprite options, save_quit, back, go, overwrite;

    private OrthogonalTiledMapRenderer renderer;

    public World(Game game, boolean loadData) {

        this.game = game;
        batch = new SpriteBatch();
        pauseBatch = new SpriteBatch();
        overwriteBatch = new SpriteBatch();
        itemBatch = new SpriteBatch();
        player = new Player(580, 1300);

        onFloor = new ArrayList<Item>();
        enemies = new ArrayList<Enemy>();

        options = new Sprite(new Texture(Gdx.files.internal("UI/pause-options.png")));
        save_quit = new Sprite(new Texture(Gdx.files.internal("UI/save-quit.png")));
        back = new Sprite(new Texture(Gdx.files.internal("UI/back.png")));
        go = new Sprite(new Texture(Gdx.files.internal("UI/go.png")));
        overwrite = new Sprite(new Texture(Gdx.files.internal("UI/overwrite.png")));

        options.setCenter(500, 600);
        save_quit.setCenter(500, 400);
        back.setCenter(500, 200);
        go.setCenter(700, 400);
        overwrite.setCenter(500, 600);

        createItems();
        loadEnemies();

        gameManager = new GameManager(player);
        if(loadData) {
            if(gameManager.loadData()) {
                applyChanges();
                gameManager.saveData();
            } else {
                game.setScreen(new TitleScreen(game, true));
            }
        } else {
            if(gameManager.createNewSave(false)) {
                overwriteMenuActive = true;
            } else {
                gameManager.saveData();
            }
        }

        manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("level/overworld_forest.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("level/overworld_forest.tmx", TiledMap.class);

        renderer = new OrthogonalTiledMapRenderer(map);

        detector = new CollisionDetector(player, map);
        drawManager = new ScreenText(player);
        PS3_Controller controller = new PS3_Controller(player);
        hud = new HUD(player);
        cam = new FollowCam(player);
        debugger = new Debugger(player);

        loadMusic();
        levelMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(37/255f, 32/255f, 31/255f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        if(hud.pausePressed()) {
            pauseMenuActive = true;
        }

        if(pauseMenuActive) {
            drawPauseMenu();
        } else if(overwriteMenuActive) {
            drawOverwriteMenu();
        } else {

            debugger.printDebug();

            cam.update();

            player.update();
            renderer.setView(cam.camera);
            renderer.render();

            batch.begin();
            for(Item item : onFloor) {
                item.render(batch);
            }
            for(Enemy enemy : enemies) {
                enemy.render(batch);
            }
            batch.end();

            player.render(batch, cam.camera);
            hud.render();
        }

    }

    private void setSave() {
        gameManager.data.setPlayerPosition(player.position);
        gameManager.data.setInventory(player.inventory_.inventory);
        gameManager.data.setOnFloor(onFloor);
        gameManager.data.setHealth(player.health);
        gameManager.data.setHearts(player.hearts);
        gameManager.data.setSlotSelected(player.inventory_.slotSelected);
        gameManager.saveData();
    }

    private void applyChanges() {
        player.position = gameManager.data.getPlayerPosition();
        player.health = gameManager.data.getHealth();
        player.hearts = gameManager.data.getHearts();
        player.inventory_.loadInventory(gameManager);
        player.inventory_.slotSelected = gameManager.data.getSlotSelected();
        onFloor = gameManager.data.getOnFloor();
        if(onFloor.size() != 0) {
            for(Item item : onFloor) {
                item.sprite.setSize(16, 16);
            }
        }

    }

    private void drawOverwriteMenu() {
        back.setCenter(300, 400);
        overwriteBatch.begin();
        go.draw(overwriteBatch);
        back.draw(overwriteBatch);
        overwrite.draw(overwriteBatch);
        overwriteBatch.end();

        if(Gdx.input.isTouched()) {
            if (go.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                gameManager.createNewSave(true);
                gameManager.saveData();
                overwriteMenuActive = false;
            } else if (back.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                game.setScreen(new TitleScreen(game, false));
            }
        }

    }

    private void drawPauseMenu() {
        back.setCenter(500, 200);
        pauseBatch.begin();
        options.draw(pauseBatch);
        save_quit.draw(pauseBatch);
        back.draw(pauseBatch);
        pauseBatch.end();

        if(Gdx.input.isTouched()) {
            if (options.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                setSave();
                game.setScreen(new Options(game, true, gameManager));
            } else if (save_quit.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                setSave();
                levelMusic.stop();
                game.setScreen(new TitleScreen(game, false));
            } else if (back.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                levelMusic.play();
                pauseMenuActive = false;
            }
        }
    }

    private void createItems() {
        Item sword = new Item("Sword", "itemSprites/tile072.png", Classifier.Weapon, "A typical adventurer's sword. Deals 2 damage per hit.");
        sword.loadCoords(360, 1280);
        sword.sprite.setSize(16, 16);
        onFloor.add(sword);
        Gdx.app.log("World", "OnFloor Sprites Loaded");
    }

    private void loadEnemies() {
        enemies.add(new Blob("Slime_1", Classifier.Slime, 300, 1280, new MovementScript("upDown3")));
    }

    private void loadMusic() {
        levelMusic = new Sound_Effects("worldMusic", true);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        manager.dispose();
        batch.dispose();
    }
}
