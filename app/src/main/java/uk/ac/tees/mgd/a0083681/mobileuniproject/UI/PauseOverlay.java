package uk.ac.tees.mgd.a0083681.mobileuniproject.UI;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages.PAUSE_BACKGROUND;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages.PAUSE_BUTTONS;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.isIn;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_WIDTH;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.Playing;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.Game;

public class PauseOverlay {
    private int bgX, bgY;
    private final CustomButton MainMenu,Restart, Unpause;
    private final Game game;
    private final Playing playing;
    private Paint blackPaint = new Paint();

    public PauseOverlay(Game game, Playing playing){
        this.game = game;
        this.playing = playing;
        blackPaint.setColor(Color.argb(200,0,0,0));
        bgX = GAME_WIDTH / 2 - (PAUSE_BACKGROUND.getWidth() / 2);
        bgY = 300;
        MainMenu = new CustomButton(bgX + 125, bgY + 280, BACKTOMAINMENU,UIImages.PAUSE_BUTTONS);
        Restart = new CustomButton(bgX + ((float) PAUSE_BACKGROUND.getWidth() / 2) - ((float) PAUSE_BUTTONS.getWidth() / 2), bgY + 280, RESTART,UIImages.PAUSE_BUTTONS);
        Unpause = new CustomButton(bgX + ((float) PAUSE_BACKGROUND.getWidth()) - 125 - (float) PAUSE_BUTTONS.getWidth() , bgY + 280, UNPAUSE,UIImages.PAUSE_BUTTONS);
    }

    public void update(){
        MainMenu.update();
        Restart.update();
        Unpause.update();
    }

    public void draw(Canvas c){
        c.drawRect(0,0,GAME_WIDTH, GAME_HEIGHT, blackPaint);
        c.drawBitmap(PAUSE_BACKGROUND.getSpriteSheet(), bgX,bgY, null);
        MainMenu.render(c);
        Restart.render(c);
        Unpause.render(c);
    }

    public void touchEvents(PointF eventPos, int action, int pointerId) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (isIn(eventPos, MainMenu)) {
                    MainMenu.setPushed(true);
                    MainMenu.setBtnPointerId(pointerId);
                } else if (isIn(eventPos, Restart)) {
                    Restart.setPushed(true);
                    Restart.setBtnPointerId(pointerId);
                } else if (isIn(eventPos, Unpause)) {
                    Unpause.setPushed(true);
                    Unpause.setBtnPointerId(pointerId);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (MainMenu.isPushed() && pointerId == MainMenu.getBtnPointerId()) {
                    MainMenu.setPushed(false);
                    MainMenu.setBtnPointerId(-1);
                    playing.resetAll();
                    game.setCurrentGameState(Game.GameState.MENU);
                } else if (Restart.isPushed() && pointerId == Restart.getBtnPointerId()) {
                    Restart.setPushed(false);
                    Restart.setBtnPointerId(-1);
                    playing.resetAll();
                } else if (Unpause.isPushed() && pointerId == Unpause.getBtnPointerId()) {
                    Unpause.setPushed(false);
                    Unpause.setBtnPointerId(-1);
                    playing.setPaused(false);
                }
                break;
        }
    }
}
