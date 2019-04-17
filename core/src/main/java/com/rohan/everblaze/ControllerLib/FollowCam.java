package com.rohan.everblaze.ControllerLib;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.rohan.everblaze.Entities.Player;
import com.rohan.everblaze.Levels.World;

public class FollowCam {

    public OrthographicCamera camera;
    private Player player;

    public FollowCam(Player player) {
        this.player = player;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 500, 400);
        this.camera.position.set(420, 1290, 0);

    }

    public void update() {

        camera.update();

        float x = player.position.x;
        float y = player.position.y;

        float halfViewPortWidth = camera.viewportWidth / 2;
        float halfViewPortHeight = camera.viewportHeight / 2;

        if (x < halfViewPortWidth) {
            camera.position.x = halfViewPortWidth;
        } else if (x > World.detector.mapWidthInPixels - halfViewPortWidth) {
            camera.position.x = World.detector.mapWidthInPixels - halfViewPortWidth;
        } else {
            camera.position.x = x;
        }

        if (y < halfViewPortHeight) {
            camera.position.y = halfViewPortHeight;
        } else if (y > World.detector.mapHeightInPixels - halfViewPortHeight) {
            camera.position.y = World.detector.mapHeightInPixels - halfViewPortHeight;
        } else {
            camera.position.y = y;
        }
    }
}
