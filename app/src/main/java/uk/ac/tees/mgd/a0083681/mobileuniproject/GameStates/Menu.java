package uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.CONTROLS;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.ESCAPE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.PLAY;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.QUIT;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.isIn;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.ControlsOverlay;
import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.PauseOverlay;
import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages;
import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.CustomButton;
import uk.ac.tees.mgd.a0083681.mobileuniproject.levels.levelSpriteManager;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.Game;


public class Menu extends BaseState{
    public CustomButton btnPlay;
    public CustomButton btnQuit;
    public CustomButton btnControls;
    private Bitmap menuButtonBackground;
    private int menuX, menuY;
    public boolean ShowControlsOverlay;
    private ControlsOverlay controlsOverlay;

    public Menu(Game game){
        super(game);
        loadMenuBackground();
        initOverlays();
        btnPlay = new CustomButton((float) ((GAME_WIDTH / 2) - (UIImages.MENU_BUTTONS.getWidth() / 2)), 120 * GAME_SCALE, PLAY, UIImages.MENU_BUTTONS);
        btnQuit = new CustomButton((float) ((GAME_WIDTH / 2) - (UIImages.MENU_BUTTONS.getWidth() / 2)), 250 * GAME_SCALE, QUIT, UIImages.MENU_BUTTONS);
        btnControls = new CustomButton((float) ((GAME_WIDTH / 2) - (UIImages.MENU_BUTTONS.getWidth() / 2)), GAME_HEIGHT / 2, CONTROLS, UIImages.MENU_BUTTONS);
    }

    private void initOverlays() {
        this.controlsOverlay = new ControlsOverlay(game,this);
    }

    private void loadMenuBackground() {
        menuButtonBackground = UIImages.MENU_BACKGROUND.getSpriteSheet();
        int menuWidth = menuButtonBackground.getWidth();
        menuX = GAME_WIDTH / 2 - menuWidth / 2;
        menuY = 45;
    }

    public void update() {
        updateUI();
    }

    private void updateUI() {
        if (!ShowControlsOverlay) {
            btnPlay.update();
            btnQuit.update();
            btnControls.update();
        }
        else {
            controlsOverlay.update();
        }
    }

    public void render(Canvas c) {
        c.drawBitmap(levelSpriteManager.LEVEL_TILE_SPRITES.getLvlBackground(), 0,0, null);
        if (!ShowControlsOverlay) {
            drawUI(c);
        }else
            controlsOverlay.draw(c);
    }

    private void drawUI(Canvas c) {
        c.drawBitmap(menuButtonBackground, menuX,menuY, null);
        btnPlay.draw(c);
        btnQuit.draw(c);
        btnControls.draw(c);
    }

    public void touchEvents(MotionEvent event) {
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(actionIndex);

        final PointF eventPos = new PointF(event.getX(actionIndex), event.getY(actionIndex));
        if (ShowControlsOverlay) {
            controlsOverlay.touchEvents(eventPos, action, pointerId);
            return;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (isIn(eventPos,btnPlay)) {
                    btnPlay.setPushed(true);
                    btnPlay.setBtnPointerId(pointerId);
                } else if (isIn(eventPos, btnQuit)) {
                    btnQuit.setPushed(true);
                    btnQuit.setBtnPointerId(pointerId);
                } else if (isIn(eventPos, btnControls)){
                    btnControls.setPushed(true);
                    btnControls.setBtnPointerId(pointerId);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (btnPlay.isPushed() && pointerId == btnPlay.getBtnPointerId()) {
                    btnPlay.setPushed(false);
                    btnPlay.setBtnPointerId(-1);
                    game.setCurrentGameState(Game.GameState.PLAYING);
                } else if (btnQuit.isPushed() && pointerId == btnQuit.getBtnPointerId()) {
                    btnQuit.setPushed(false);
                    btnQuit.setBtnPointerId(-1);
                    System.exit(0);
                } else if (btnControls.isPushed() && pointerId == btnControls.getBtnPointerId()) {
                    btnControls.setPushed(false);
                    btnControls.setBtnPointerId(-1);
                    ShowControlsOverlay = true;
                }
                break;
        }
    }
}
