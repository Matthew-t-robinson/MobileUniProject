package uk.ac.tees.mgd.a0083681.mobileuniproject.levels;

public class Level {

    private int[][] lvlData;
    private LoadLevelManager level;

    public Level(LoadLevelManager level) {
        this.level = level;
        this.lvlData = level.getLvlData();
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public LoadLevelManager getLevel() {
        return level;
    }
}
