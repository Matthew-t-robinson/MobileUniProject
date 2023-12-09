package uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages.HUD_HEALTH_BAR;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.GameCompleteOverlay;
import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.GameOverOverlay;
import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.LevelCompleteOverlay;
import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.PauseOverlay;
import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.UIImages;
import uk.ac.tees.mgd.a0083681.mobileuniproject.UI.CustomButton;
import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.EnemyManager;
import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.Player;
import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.entitySpriteManager;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.Interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.Interfaces.GameStateInterface;
import uk.ac.tees.mgd.a0083681.mobileuniproject.levels.levelManager;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.Game;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity;
import uk.ac.tees.mgd.a0083681.mobileuniproject.objects.ObjectManager;

public class Playing extends BaseState implements GameStateInterface, BitmapMethods {
    private levelManager lvlManager;
    private EnemyManager enemyManager;
    private SensorManager sensorManager;
    private ObjectManager objectManager;
    private Player player;
    public CustomButton btnLeft,btnRight,btnA,btnB,btnPause;

    private int xLvlOffset;
    private final int leftBorder = (int) (0.2 * GAME_WIDTH);
    private final int rightBorder = (int) (0.8 * GAME_WIDTH);
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private int healthBarSpriteX = (int) (10 * GAME_SCALE);
    private int healthBarSpriteY = (int) (10 * GAME_SCALE);
    private int healthBarWidth = (int) (150 * GAME_SCALE);
    private int healthBarHeight = (int) (4 * GAME_SCALE);
    private int healthBarXStart = (int) (34 * GAME_SCALE);
    private int healthBarYStart = (int) (14 * GAME_SCALE);
    private int healthWidth = healthBarWidth;
    private Paint redpaint = new Paint();
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompleteOverlay levelCompleteOverlay;
    private GameCompleteOverlay gameCompleteOverlay;
    private boolean paused = false;
    private boolean GameOver = false;
    private boolean LevelCompleted;
    private boolean GameCompleted;



    public Playing(Game game){
        super(game);
        lvlManager = new levelManager(this,game);
        enemyManager = new EnemyManager(this);
        PointF playerSpawn = levelManager.getCurrentLevel().getLevel().GetPlayerSpawn();
        player = new Player(playerSpawn.x,playerSpawn.y, 40 * GAME_SCALE, 64 * GAME_SCALE, this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        objectManager = new ObjectManager(this);
        redpaint.setColor(Color.RED);
        initSensorManager();
        initButtons();
        initOverlays();
        calcLvlOffset();
    }

    public void calcLvlOffset() {
        lvlTilesWide = levelManager.getCurrentLevel().getLvlData()[0].length;
        maxTilesOffset = lvlTilesWide - 25;
        maxLvlOffsetX = maxTilesOffset * 96;
    }

    private void initSensorManager() {
        this.sensorManager = MainActivity.mSensorManager;

        Objects.requireNonNull(this.sensorManager).registerListener(mSensorListener, this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    private void initButtons() {
        btnLeft = new CustomButton(150, 800, LEFTBTN, UIImages.HUD_BUTTON_LEFT);
        btnRight = new CustomButton(150 + UIImages.HUD_BUTTON_LEFT.getWidth() + 100, 800, RIGHTBTN , UIImages.HUD_BUTTON_RIGHT);
        btnA = new CustomButton(MainActivity.GAME_WIDTH - (150 + UIImages.HUD_BUTTON_A.getWidth()), 800, ABTN, UIImages.HUD_BUTTON_A);
        btnB = new CustomButton(MainActivity.GAME_WIDTH - (150 + (UIImages.HUD_BUTTON_A.getWidth() * 2) + 100), 800, BBTN, UIImages.HUD_BUTTON_B);
        btnPause = new CustomButton(MainActivity.GAME_WIDTH - (42 + (UIImages.HUD_BUTTON_PAUSE.getWidth())), 42, BBTN, UIImages.HUD_BUTTON_PAUSE);
    }
    private void initOverlays() {
        this.pauseOverlay = new PauseOverlay(game,this);
        this.gameOverOverlay = new GameOverOverlay(game,this);
        this.levelCompleteOverlay = new LevelCompleteOverlay(game,this);
        this.gameCompleteOverlay = new GameCompleteOverlay(game,this);
    }

    @Override
    public void update(double delta) {
        if (paused){
            pauseOverlay.update();
        } else if (GameOver) {
            gameOverOverlay.update();
        } else if (LevelCompleted) {
            levelCompleteOverlay.update();
            objectManager.update();
        } else if (GameCompleted) {
            gameOverOverlay.update();
            objectManager.update();
        } else {
            player.update(delta);
            checkCloseToBorder();
            enemyManager.Update(delta, player);
            objectManager.update();
            updateUI();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().left;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset +=diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;
        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    private void updateUI() {
        btnLeft.update();
        btnRight.update();
        btnA.update();
        btnB.update();
        btnPause.update();
        updateHealthBar();
    }

    private void updateHealthBar() {
        healthWidth = (int) ((player.getCurrentHealth() / (float) player.getMaxHealth()) * healthBarWidth);
    }

    @Override
    public void render(Canvas c) {
        lvlManager.draw(c, xLvlOffset);
        enemyManager.draw(c, xLvlOffset);
        objectManager.draw(c, xLvlOffset);
        player.render(c, xLvlOffset);
        drawUI(c);
    }

    private void drawUI(Canvas c) {
        if (paused){
            pauseOverlay.draw(c);
        } else if (GameOver) {
            gameOverOverlay.draw(c);
        } else if (LevelCompleted) {
            levelCompleteOverlay.draw(c);
        } else if (GameCompleted) {
            gameCompleteOverlay.draw(c);
        }else {
            btnLeft.render(c);
            btnRight.render(c);
            btnA.render(c);
            btnB.render(c);
            btnPause.render(c);
            drawHealthBar(c);
        }
    }

    private void drawHealthBar(Canvas c) {
        c.drawBitmap(
                HUD_HEALTH_BAR.getSpriteSheet(),
                healthBarSpriteX,
                healthBarSpriteY,
                null
        );
        c.drawRect(healthBarXStart + healthBarSpriteX, healthBarYStart + healthBarSpriteY, healthBarXStart + healthBarSpriteX + healthWidth,healthBarYStart + healthBarSpriteY + healthBarHeight, redpaint);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(actionIndex);

        final PointF eventPos = new PointF(event.getX(actionIndex), event.getY(actionIndex));
        if (paused){
            pauseOverlay.touchEvents(eventPos, action, pointerId);
            return;
        }else if (GameOver){
            gameOverOverlay.touchEvents(eventPos, action, pointerId);
            return;
        }else if (LevelCompleted){
            levelCompleteOverlay.touchEvents(eventPos,action,pointerId);
            return;
        } else if (GameCompleted) {
            gameCompleteOverlay.touchEvents(eventPos,action,pointerId);
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                    if (isIn(eventPos, btnLeft)) {
                        btnLeft.setPushed(true);
                        getPlayer().setLeft(true);
                        btnLeft.setBtnPointerId(pointerId);
                    } else if (isIn(eventPos, btnRight)) {
                        btnRight.setPushed(true);
                        getPlayer().setRight(true);
                        btnRight.setBtnPointerId(pointerId);
                    } else if (isIn(eventPos, btnA)) {
                        btnA.setPushed(true);
                        getPlayer().setAttacking(true);
                        btnA.setBtnPointerId(pointerId);
                    } else if (isIn(eventPos, btnB)) {
                        btnB.setPushed(true);
                        getPlayer().setJump(true);
                        btnB.setBtnPointerId(pointerId);
                    } else if (isIn(eventPos, btnPause)) {
                        btnPause.setPushed(true);
                        btnPause.setBtnPointerId(pointerId);
                        paused = true;
                    }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                    if (btnLeft.isPushed() && pointerId == btnLeft.getBtnPointerId()) {
                        btnLeft.setPushed(false);
                        getPlayer().setLeft(false);
                        if (btnRight.isPushed())
                        {
                            getPlayer().setRight(true);
                        }
                        btnLeft.setBtnPointerId(-1);
                    } else if (btnRight.isPushed() && pointerId == btnRight.getBtnPointerId()) {
                        btnRight.setPushed(false);
                        getPlayer().setRight(false);
                        if (btnLeft.isPushed())
                        {
                            getPlayer().setLeft(true);
                        }
                        btnRight.setBtnPointerId(-1);
                    } else if (btnA.isPushed() && pointerId == btnA.getBtnPointerId()) {
                        btnA.setPushed(false);
                        btnA.setBtnPointerId(-1);
                    } else if (btnB.isPushed() && pointerId == btnB.getBtnPointerId()) {
                        btnB.setPushed(false);
                        getPlayer().setJump(false);
                        btnB.setBtnPointerId(-1);
                    } else if (btnPause.isPushed() && pointerId == btnPause.getBtnPointerId()) {
                        btnPause.setPushed(false);
                        btnPause.setBtnPointerId(-1);
                    }
                break;
        }
    }
    public Player getPlayer(){
        return player;
    }

    public levelManager getLvlManager() {
        return lvlManager;
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 5) {
                if (Game.getCurrentGameState() == Game.GameState.PLAYING)
                    if (enemyManager.canShakeAttackHit(player))
                        player.setShakeAttacking(true);
                    else
                        enemyManager.stunTimer();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void setPaused(boolean paused) {
        this.paused = paused;
        if (paused == false && btnPause.isPushed() == true){
            btnPause.setPushed(false);
        }
    }

    public void resetAll() {
        setPaused(false);
        setGameOver(false);
        setLevelCompleted(false);
        setGameCompleted(false);
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    private void setGameCompleted(boolean b) {
        GameCompleted = b;
    }

    public void checkEnemyHit(RectF attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void setGameOver(boolean gameOver) {
        GameOver = gameOver;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void checkObjectHit(RectF attackBox) {
        objectManager.checkObjectHit(attackBox);
    }

    public void setLevelCompleted(boolean levelCompleted) {
        if (levelCompleted && (levelManager.lvlNumber == levelManager.endLvlNumber))
            GameCompleted = true;
        else
            LevelCompleted = levelCompleted;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void checkSpikesTouched(Player player) {
        objectManager.checkSpikesTouched(player);
    }

    public void shakeAttack(RectF attackBox) {
        enemyManager.shakeAttackHit(attackBox);
    }
}