package uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.PLAYBTN;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.QUITBTN;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.isIn;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages;
import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.CustomButton;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.Interfaces.GameStateInterface;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.Game;


public class Menu extends BaseState implements GameStateInterface {

    private Paint paint;
    public CustomButton btnPlay;
    public CustomButton btnQuit;
    private Bitmap menuButtonBackground;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game){
        super(game);
        loadBackground();
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
        btnPlay = new CustomButton((float) ((GAME_WIDTH / 2) - (UIImages.MENU_BUTTONS.getWidth() / 2)), 150 * GAME_SCALE, PLAYBTN, UIImages.MENU_BUTTONS);
        btnQuit = new CustomButton((float) ((GAME_WIDTH / 2) - (UIImages.MENU_BUTTONS.getWidth() / 2)), 220 * GAME_SCALE, QUITBTN, UIImages.MENU_BUTTONS);
    }

    private void loadBackground() {
        menuButtonBackground = UIImages.MENU_BACKGROUND.getSpriteSheet();
        menuWidth = (int) (menuButtonBackground.getWidth());
        menuHeight = (int) (menuButtonBackground.getHeight());
        menuX = GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * 1);

    }

    @Override
    public void update(double delta) {
        updateUI();
    }

    private void updateUI() {
        btnPlay.update();
        btnQuit.update();
    }

    @Override
    public void render(Canvas c) {
        drawUI(c);
    }

    private void drawUI(Canvas c) {
        c.drawBitmap(menuButtonBackground, menuX,menuY, null);
        btnPlay.render(c);
        btnQuit.render(c);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(actionIndex);

        final PointF eventPos = new PointF(event.getX(actionIndex), event.getY(actionIndex));

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (isIn(eventPos,btnPlay)) {
                    btnPlay.setPushed(true);
                    btnPlay.setBtnPointerId(pointerId);
                } else if (isIn(eventPos, btnQuit)) {
                    btnQuit.setPushed(true);
                    btnQuit.setBtnPointerId(pointerId);
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
                }
                break;
        }
    }
}
