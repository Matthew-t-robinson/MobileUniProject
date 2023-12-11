package uk.ac.tees.mgd.a0083681.mobileuniproject.levels;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.CRAB;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.STARFISH;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ObjectConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.PlayerConstants.PLAYER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;

import java.util.ArrayList;

import uk.ac.tees.mgd.a0083681.mobileuniproject.entities.*;
import uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity;
import uk.ac.tees.mgd.a0083681.mobileuniproject.R;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.Interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0083681.mobileuniproject.objects.*;

public enum levelSpriteManager implements BitmapMethods {
    LEVEL_TILE_SPRITES(R.drawable.outside_sprites,12, 4, R.drawable.bg_image);

    private final Bitmap[] sprites;
    private final Bitmap lvlBackground;

    levelSpriteManager(int resID, int spriteSheetWidth, int spriteSheetHeight, int lvlBackgroundResId){
        options.inScaled = false;
        sprites = new Bitmap[spriteSheetHeight * spriteSheetWidth];
        lvlBackground = getScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), lvlBackgroundResId, options),10);
        Bitmap spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < spriteSheetHeight; j++)
            for (int i = 0; i < spriteSheetWidth; i++) {
                int index = j * spriteSheetWidth + i;
                sprites[index] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, 32 * i, 32 * j, 32, 32), GAME_SCALE);
            }
    }

    public Bitmap getSprite(int id) {
        return sprites[id];
    }

    public Bitmap getLvlBackground() {
        return lvlBackground;
    }

}
