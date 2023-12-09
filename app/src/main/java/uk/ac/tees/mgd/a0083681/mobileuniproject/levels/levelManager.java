package uk.ac.tees.mgd.a0083681.mobileuniproject.levels;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.LevelConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.levels.levelSpriteManager.*;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.Playing;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.Game;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.GamePanel;

public class levelManager {
    private Bitmap levelSprite;
    private static Level levelOne =  new Level(LEVEL_ONE);
    private static Level levelTwo = new Level(LEVEL_TWO);
    public static Level currentLevel = levelOne;
    public static int lvlNumber = ONE;
    public static int endLvlNumber = TWO;
    private GamePanel gameView;

    private Game game;
    private Playing playing;


    public levelManager (Playing playing, Game game){
        this.game = game;
        currentLevel = levelOne;
        this.playing = playing;
    }

    public void draw(Canvas c, int xLvlOffset){
    c.drawBitmap(currentLevel.getLevel().getLvlBackground(), 0,0,null);
        for (int j = 0; j < GamePanel.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < currentLevel.getLvlData()[0].length; i++) {
                int index = currentLevel.getSpriteIndex(i, j);
                c.drawBitmap(currentLevel.getLevel().getSprite(index), 96 * i - xLvlOffset, 96 * j, null);
            }
    }

    public void nextLevel(){
        lvlNumber += 1;
        if (lvlNumber <= 2){
            setLevel(lvlNumber);
        }
    }

    public void setLevel(int LEVEL_NUM){
        lvlNumber = LEVEL_NUM;
        switch (LEVEL_NUM){
            case ONE:
                currentLevel = levelOne;
                break;
            case TWO:
                currentLevel = levelTwo;
                break;
            default:
                game.setCurrentGameState(Game.GameState.MENU);
        }
        playing.getPlayer().loadLvlData(currentLevel.getLvlData());
        playing.getPlayer().setSpawn();
        playing.getEnemyManager().addEnemies();
        playing.getObjectManager().loadObjects(currentLevel.getLevel());
        playing.getObjectManager().reset();
        playing.calcLvlOffset();
    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }
}