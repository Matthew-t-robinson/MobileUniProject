package uk.ac.tees.mgd.a0083681.mobileuniproject.UI;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages.GAMEOVER_BACKGROUND;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages.PAUSE_BUTTONS;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.BACKTOMAINMENU;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.RESTART;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.UNPAUSE;
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

public class GameOverOverlay {

    private int bgX, bgY;
    private final CustomButton MainMenu, Restart;
    private final Game game;
    private final Playing playing;
    private Paint blackPaint = new Paint();

    public GameOverOverlay(Game game, Playing playing){
        this.game = game;
        this.playing = playing;
        blackPaint.setColor(Color.argb(200,0,0,0));
        bgX = GAME_WIDTH / 2 - (GAMEOVER_BACKGROUND.getWidth() / 2);
        bgY = 300;
        MainMenu = new CustomButton(bgX + 125, bgY + ((float) GAMEOVER_BACKGROUND.getWidth() / 2) - 25, BACKTOMAINMENU,UIImages.PAUSE_BUTTONS);
        Restart = new CustomButton(bgX + ((float) GAMEOVER_BACKGROUND.getWidth()) - 145 - (float) PAUSE_BUTTONS.getWidth() , bgY + ((float) GAMEOVER_BACKGROUND.getWidth() / 2) - 25, RESTART,UIImages.PAUSE_BUTTONS);
    }

    public void update(){
        MainMenu.update();
        Restart.update();
    }

    public void draw(Canvas c){
        c.drawRect(0,0,GAME_WIDTH, GAME_HEIGHT, blackPaint);
        c.drawBitmap(GAMEOVER_BACKGROUND.getSpriteSheet(), bgX,bgY, null);
        MainMenu.render(c);
        Restart.render(c);
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
                }
                break;
        }
    }
}
