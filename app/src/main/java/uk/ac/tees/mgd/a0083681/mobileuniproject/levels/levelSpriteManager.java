package uk.ac.tees.mgd.a0083681.mobileuniproject.levels;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.CRAB;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.STARFISH;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ObjectConstants.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.Crab;
import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.Starfish;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity;
import uk.ac.tees.mgd.a0083681.mobileuniproject.R;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.Interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0083681.mobileuniproject.objects.Chest;
import uk.ac.tees.mgd.a0083681.mobileuniproject.objects.GameObject;
import uk.ac.tees.mgd.a0083681.mobileuniproject.objects.Spikes;

public enum levelSpriteManager implements BitmapMethods {
    LEVEL_ONE(R.drawable.outside_sprites,12, 4, R.drawable.level_three, R.drawable.bg_image),
    LEVEL_TWO(R.drawable.outside_sprites,12, 4, R.drawable.level_two, R.drawable.bg_image);

    private final Bitmap[] sprites;
    private final Bitmap lvlDataBitmap;
    private final Bitmap lvlBackground;

    levelSpriteManager(int resID, int spritesheetWidth, int spritesheetHeight, int lvlDataResId, int lvlBackgroundResId){
        options.inScaled = false;
        sprites = new Bitmap[spritesheetHeight * spritesheetWidth];
        lvlDataBitmap = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), lvlDataResId, options);
        lvlBackground = getScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), lvlBackgroundResId, options),10);
        Bitmap spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < spritesheetHeight; j++)
            for (int i = 0; i < spritesheetWidth; i++) {
                int index = j * spritesheetWidth + i;
                sprites[index] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, 32 * i, 32 * j, 32, 32), GAME_SCALE);
            }
    }

    public Bitmap getSprite(int id) {
        return sprites[id];
    }

    public int[][] GetLevelData() {
        int[][] lvlData = new int[lvlDataBitmap.getHeight()][lvlDataBitmap.getWidth()];
        for (int j = 0; j < lvlDataBitmap.getHeight(); j++)
            for (int i = 0; i < lvlDataBitmap.getWidth(); i++) {
                int argbPixel = lvlDataBitmap.getPixel(i,j);
                int value = Color.red(argbPixel);
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;

    }

    public ArrayList<Crab> GetCrabs(){
        ArrayList<Crab> list = new ArrayList<>();
        for (int j = 0; j < lvlDataBitmap.getHeight(); j++)
            for (int i = 0; i < lvlDataBitmap.getWidth(); i++) {
                int argbPixel = lvlDataBitmap.getPixel(i,j);
                int value = Color.green(argbPixel);
                if (value == CRAB)
                    list.add(new Crab(i * 96,j * 96));
            }
        return list;
    }

    public ArrayList<Starfish> GetStarfish(){
        ArrayList<Starfish> list = new ArrayList<>();
        for (int j = 0; j < lvlDataBitmap.getHeight(); j++)
            for (int i = 0; i < lvlDataBitmap.getWidth(); i++) {
                int argbPixel = lvlDataBitmap.getPixel(i,j);
                int value = Color.green(argbPixel);
                if (value == STARFISH)
                    list.add(new Starfish(i * 96,j * 96));
            }
        return list;
    }

    public PointF GetPlayerSpawn() {
        for (int j = 0; j < lvlDataBitmap.getHeight(); j++)
            for (int i = 0; i < lvlDataBitmap.getWidth(); i++) {
                int argbPixel = lvlDataBitmap.getPixel(i,j);
                int value = Color.green(argbPixel);
                if (value == 100)
                    return new PointF(i * 96, j * 96);
            }
        return new PointF(96, 96);
    }

    public ArrayList<Chest> GetChest(){
        ArrayList<Chest> list = new ArrayList<>();
        for (int j = 0; j < lvlDataBitmap.getHeight(); j++)
            for (int i = 0; i < lvlDataBitmap.getWidth(); i++) {
                int argbPixel = lvlDataBitmap.getPixel(i,j);
                int value = Color.blue(argbPixel);
                if (value == CHEST)
                    list.add(new Chest(i * 96, j * 96));
            }
        return list;
    }

    public ArrayList<Spikes> GetSpikes(){
        ArrayList<Spikes> list = new ArrayList<>();
        for (int j = 0; j < lvlDataBitmap.getHeight(); j++)
            for (int i = 0; i < lvlDataBitmap.getWidth(); i++) {
                int argbPixel = lvlDataBitmap.getPixel(i,j);
                int value = Color.blue(argbPixel);
                if (value == SPIKES)
                    list.add(new Spikes(i * 96, j * 96));
            }
        return list;
    }

    public Bitmap getLvlBackground() {
        return lvlBackground;
    }
}
