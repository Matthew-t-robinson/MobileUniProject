package uk.ac.tees.mgd.a0083681.mobileuniproject.main;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.GAME_WIDTH;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static int TILES_IN_HEIGHT, TILES_IN_WIDTH, TILES_SIZE;
    private  GameLoop gameLoop;
    private final Game game;

    public GamePanel(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        game = new Game(surfaceHolder);

        TILES_SIZE = 96;
        TILES_IN_HEIGHT = (GAME_HEIGHT / TILES_SIZE) + 1;
        TILES_IN_WIDTH = (GAME_WIDTH / TILES_SIZE) + 1;

        //for (int i = 0 ; i < 50; i++){
      //      crabs.add(new PointF(rand.nextInt(1080), rand.nextInt(1920)));
      //  }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {return game.touchEvent(event);}



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {game.startGameLoop(); }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

}
