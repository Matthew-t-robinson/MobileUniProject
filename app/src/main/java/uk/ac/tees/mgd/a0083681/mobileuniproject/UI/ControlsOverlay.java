package uk.ac.tees.mgd.a0083681.mobileuniproject.UI;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages.CONTROLS_OVERLAY;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages.GAMEOVER_BACKGROUND;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages.GAME_COMPLETE_BACKGROUND;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.BACKTOMAINMENU;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.ESCAPE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.isIn;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.Menu;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.Game;

public class ControlsOverlay {
    private int bgX, bgY;
    private final CustomButton Escape;
    private final Game game;
    private final Menu menu;
    private final Paint blackPaint = new Paint();

    public ControlsOverlay(Game game, Menu menu){
        this.game = game;
        this.menu = menu;
        blackPaint.setColor(Color.argb(200,0,0,0));
        bgX = GAME_WIDTH / 2 - (CONTROLS_OVERLAY.getWidth() / 2);
        bgY = 250;
        Escape = new CustomButton(bgX + CONTROLS_OVERLAY.getWidth() - (int) (UIImages.BUTTON_ESCAPE.getWidth()/2)  , bgY , ESCAPE,UIImages.BUTTON_ESCAPE);
    }

    public void update(){
        Escape.update();
    }

    public void draw(Canvas c){
        c.drawRect(0,0,GAME_WIDTH, GAME_HEIGHT, blackPaint);
        c.drawBitmap(CONTROLS_OVERLAY.getSpriteSheet(), bgX,bgY, null);
        Escape.draw(c);
    }

    public void touchEvents(PointF eventPos, int action, int pointerId) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (isIn(eventPos, Escape)) {
                    Escape.setPushed(true);
                    Escape.setBtnPointerId(pointerId);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (Escape.isPushed() && pointerId == Escape.getBtnPointerId()) {
                    Escape.setPushed(false);
                    Escape.setBtnPointerId(-1);
                    menu.ShowControlsOverlay = false;
                }
                break;
        }
    }
}
