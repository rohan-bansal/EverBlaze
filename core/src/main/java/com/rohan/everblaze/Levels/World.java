package com.rohan.everblaze.Levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rohan.everblaze.Entities.Classifier;
import com.rohan.everblaze.Entities.Item;
import com.rohan.everblaze.FileUtils.GameManager;
import com.rohan.everblaze.ControllerLib.FollowCam;
import com.rohan.everblaze.ControllerLib.PS3_Controller;
import com.rohan.everblaze.Debugger;
import com.rohan.everblaze.Entities.Player;
import com.rohan.everblaze.Effects.Sound_Effects;
import com.rohan.everblaze.TileInteraction.CollisionDetector;
import com.rohan.everblaze.TileInteraction.HUD;

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
    private Sound_Effects levelMusic;

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
        player = new Player(325, 1280);

        onFloor = new ArrayList<Item>();

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
        manager.load("level.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("level.tmx", TiledMap.class);

        renderer = new OrthogonalTiledMapRenderer(map);

        detector = new CollisionDetector(player, map);
        PS3_Controller controller = new PS3_Controller(player);
        hud = new HUD(player);
        cam = new FollowCam(player);
        debugger = new Debugger(player);
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
            batch.end();

            player.render(batch, cam.camera);



            hud.render();
        }

    }

    private void applyData() {
        gameManager.data.setPlayerPosition(player.position);
        gameManager.data.setInventory(player.inventory_.inventory);
        gameManager.data.setHealth(player.health);
        gameManager.data.setHearts(player.hearts);
        gameManager.data.setSlotSelected(player.inventory_.slotSelected);
        gameManager.saveData();
    }

    private void applyChanges() {
        player.position = gameManager.data.getPlayerPosition();
        player.inventory_.loadInventory(gameManager);
        player.inventory_.slotSelected = gameManager.data.getSlotSelected();
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
                applyData();
                game.setScreen(new Options(game, true, gameManager));
            } else if (save_quit.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                applyData();
                game.setScreen(new TitleScreen(game, false));
            } else if (back.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                pauseMenuActive = false;
            }
        }
    }

    public void createItems() {
        Item cherry = new Item("Cherry", "itemSprites/tile001.png", Classifier.Food, "Restores 2 hearts.");
        cherry.loadCoords(360, 1280);
        cherry.sprite.setSize(16, 16);
        onFloor.add(cherry);
        Gdx.app.log("World", "OnFloor Sprites Loaded");
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
