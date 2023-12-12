package uk.ac.tees.mgd.a0083681.mobileuniproject.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.*;

public class Game {

    private SurfaceHolder surfaceHolder;
    private Menu menu;
    private Playing playing;
    private static GameState currentGameState = GameState.MENU;
    private final GameLoop gameLoop;

    public Game(SurfaceHolder surfaceHolder){
        this.surfaceHolder = surfaceHolder;
        gameLoop = new GameLoop(this);
        initGameState();
    }

    public void update(double delta){
        switch (currentGameState) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update(delta);
                break;
        }
    }

    public void render(){
        Canvas c = surfaceHolder.lockCanvas();
        c.drawColor(Color.BLACK);

        switch (currentGameState){
            case MENU:
                menu.render(c);
                break;
            case PLAYING:
                playing.render(c);
                break;
        }

        surfaceHolder.unlockCanvasAndPost(c);
    }

    private void initGameState() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState) {
            case MENU:
                menu.touchEvents(event);
                break;
            case PLAYING:
                playing.touchEvents(event);
                break;
        }
        return true;
    }

    public void startGameLoop() {
        gameLoop.startGameLoop();
    }

    public enum GameState{
        MENU, PLAYING;
    }

    public static GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }
}
