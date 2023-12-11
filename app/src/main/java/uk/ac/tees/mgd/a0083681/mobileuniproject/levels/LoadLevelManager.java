package uk.ac.tees.mgd.a0083681.mobileuniproject.levels;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.TILES_SIZE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ObjectConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.PlayerConstants.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;

import java.util.ArrayList;

import uk.ac.tees.mgd.a0083681.mobileuniproject.R;
import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.*;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.Interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity;
import uk.ac.tees.mgd.a0083681.mobileuniproject.objects.*;

public enum LoadLevelManager implements BitmapMethods {
    LEVEL_ONE(R.drawable.level_one),
    LEVEL_TWO(R.drawable.level_two),
    LEVEL_THREE(R.drawable.level_three);

    int[][] lvlData;
    ArrayList<Crab> crabsList = new ArrayList<>();
    ArrayList<Starfish> starfishList = new ArrayList<>();
    PointF playerStartPos;
    ArrayList<Chest> chestList = new ArrayList<>();
    ArrayList<Spikes> spikesList = new ArrayList<>();
    private final Bitmap lvlDataBitmap;

    LoadLevelManager(int resID) {
        options.inScaled = false;
        lvlDataBitmap = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        lvlData = new int[lvlDataBitmap.getHeight()][lvlDataBitmap.getWidth()];
        for (int j = 0; j < lvlDataBitmap.getHeight(); j++)
            for (int i = 0; i < lvlDataBitmap.getWidth(); i++) {
                int argbPixel = lvlDataBitmap.getPixel(i, j);
                int red = Color.red(argbPixel);
                int green = Color.green(argbPixel);
                int blue = Color.blue(argbPixel);

                LoadLevelData(red, i, j);
                LoadEnemies(green, i, j);
                LoadObjects(blue, i, j);
            }
    }

    public void LoadLevelData(int value, int i, int j) {
        if (value >= 48)
            value = 0;
        lvlData[j][i] = value;
    }

    public void LoadEnemies(int value, int i, int j){
        switch (value){
            case CRAB:
                crabsList.add(new Crab(i * TILES_SIZE,j * TILES_SIZE));
                break;
            case STARFISH:
                starfishList.add(new Starfish(i* TILES_SIZE, j * TILES_SIZE));
                break;
            case PLAYER:
                playerStartPos = new PointF(i * TILES_SIZE, j * TILES_SIZE);
                break;
        }
    }

    public void LoadObjects(int value, int i, int j){
        switch (value){
            case CHEST:
                chestList.add(new Chest(i * TILES_SIZE,j * TILES_SIZE));
                break;
            case SPIKES:
                spikesList.add(new Spikes(i * TILES_SIZE,j * TILES_SIZE));
        }
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public ArrayList<Crab> getCrabsList() {
        return crabsList;
    }

    public ArrayList<Starfish> getStarfishList() {
        return starfishList;
    }

    public PointF getPlayerStartPos() {
        return playerStartPos;
    }

    public ArrayList<Chest> getChestList() {
        return chestList;
    }

    public ArrayList<Spikes> getSpikesList() {
        return spikesList;
    }
}
