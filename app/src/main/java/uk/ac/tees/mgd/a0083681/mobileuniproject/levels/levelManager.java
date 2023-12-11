package uk.ac.tees.mgd.a0083681.mobileuniproject.levels;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.TILES_SIZE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.LevelConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.levels.LoadLevelManager.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.levels.levelSpriteManager.LEVEL_TILE_SPRITES;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.Playing;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.Game;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.GamePanel;

public class levelManager {
    private static final Level levelOne =  new Level(LEVEL_ONE);
    private static final Level levelTwo = new Level(LEVEL_TWO);
    private static final Level levelThree = new Level(LEVEL_THREE);
    private static final levelSpriteManager SpriteTiles = LEVEL_TILE_SPRITES;
    public static Level currentLevel = levelOne;
    public static int lvlNumber = ONE;
    public static int endLvlNumber = THREE;
    private final Game game;
    private final Playing playing;


    public levelManager (Playing playing, Game game){
        this.game = game;
        this.playing = playing;
    }

    public void draw(Canvas c, int xLvlOffset){
    c.drawBitmap(SpriteTiles.getLvlBackground(), 0,0,null);
        for (int j = 0; j < GamePanel.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < currentLevel.getLvlData()[0].length; i++) {
                int index = currentLevel.getSpriteIndex(i, j);
                c.drawBitmap(SpriteTiles.getSprite(index), TILES_SIZE * i - xLvlOffset, TILES_SIZE * j, null);
            }
    }

    public void nextLevel(){
        lvlNumber += 1;
        if (lvlNumber <= endLvlNumber){
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
            case THREE:
                currentLevel = levelThree;
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
