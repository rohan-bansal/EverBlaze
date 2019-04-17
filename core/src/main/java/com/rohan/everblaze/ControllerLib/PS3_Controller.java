package main.java.com.rohan.everblaze.ControllerLib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.Levels.World;
import main.java.com.rohan.everblaze.TileInteraction.CollisionDetector;

public class PS3_Controller implements ControllerListener {

    public final int BUTTON_X = 0;
    public final int BUTTON_O = 1;
    public final int BUTTON_TRI = 2;
    public final int BUTTON_SQU = 3;
    public final int BUTTON_SELECT = 8;
    public final int BUTTON_START = 9;
    public final int HOME = 16;
    public final int UP = 13;
    public final int DOWN = 14;
    public final int RIGHT = 16;
    public final int LEFT = 15;
    public final PovDirection BUTTON_DPAD_UP = PovDirection.north;
    public final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
    public final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
    public final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;
    public final int BUTTON_L1 = 4;
    public final int BUTTON_L2 = 6;
    public final int BUTTON_R1 = 5;
    public final int BUTTON_R2 = 7;
    public final int AXIS_LEFT_X = 0; //-1 is left | +1 is right
    public final int AXIS_LEFT_Y = 1; //-1 is up | +1 is down
    public final int AXIS_LEFT_TRIGGER = 10; //value 0 to 1f
    public final int AXIS_RIGHT_X = 3; //-1 is left | +1 is right
    public final int AXIS_RIGHT_Y = 2; //-1 is up | +1 is down
    public final int AXIS_RIGHT_TRIGGER = 11; //value 0 to -1f

    private CollisionDetector detector;

    Player player;

    public PS3_Controller(Player player) {

        this.player = player;
        Controllers.addListener(this);
    }

    @Override
    public void connected(Controller controller) {
        Gdx.app.log("Controllers", controller.getName() + " connected");
    }

    @Override
    public void disconnected(Controller controller) {
        Gdx.app.log("Controllers", controller.getName() + " disconnected");
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        Gdx.app.log("MAIN", Integer.toString(buttonCode));
        if(buttonCode == UP) {
            World.movingUp = true;
        }

        if(buttonCode == DOWN) {
            World.movingDown = true;
        }

        if(buttonCode == LEFT) {
            World.movingLeft = true;
        }

        if(buttonCode == RIGHT) {
            World.movingRight = true;
        }

        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if(buttonCode == UP)
            World.movingUp = false;
        if(buttonCode == DOWN)
            World.movingDown = false;
        if(buttonCode == LEFT)
            World.movingLeft = false;
        if(buttonCode == RIGHT)
            World.movingRight = false;
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if(axisCode == AXIS_LEFT_X) {
            if(value > 0.2f || value < -0.2f) {
                if (value > 0) {
                    World.movingRight = true;
                    player.direction = "right";
                    player.currentFrame = player.running;
                } else {
                    World.movingLeft = true;
                    player.direction = "left";
                    player.currentFrame = player.running;
                }
            } else {
                World.movingRight = false;
                World.movingLeft = false;
            }
        }
        if(axisCode == AXIS_LEFT_Y) {
            if(value > 0.2f || value < -0.2f) {
                if(value < 0) {
                    World.movingUp = true;
                    player.currentFrame = player.running;

                } else {
                    World.movingDown = true;
                    player.currentFrame = player.running;
                }
            } else {
                World.movingUp = false;
                World.movingDown = false;
            }
        }

        if(value <= 0.2f && value >= -0.2f) {
        }

        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
