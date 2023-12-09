package uk.ac.tees.mgd.a0083681.mobileuniproject.levels;

public class Level {

    private int[][] lvlData;
    private levelSpriteManager level;

    public Level(levelSpriteManager level) {
        this.lvlData = level.GetLevelData();
        this.level = level;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public levelSpriteManager getLevel() {
        return level;
    }
}
