package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity;
import uk.ac.tees.mgd.a0083681.mobileuniproject.R;

public enum entitySpriteManager {

    PLAYER(R.drawable.player_spritesheet, 64, 40, 6, 14),
    CRAB(R.drawable.crabby_sprite, 72, 32, 9, 5),
    CHEST(R.drawable.chest_spritesheet,64,35,7,1),
    STARFISH(R.drawable.pinkstar_atlas,34,30,8,5),
    SPIKES(R.drawable.spike_sprite, 32,32,1,1);


    private Bitmap spriteSheet;
    private Bitmap[][] sprites;
    private BitmapFactory.Options options = new BitmapFactory.Options();

    entitySpriteManager(int resID, int width, int height, int spritesheetWidth, int spritesheetHeight) {
        sprites = new Bitmap[spritesheetHeight][spritesheetWidth];
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        for (int j = 0; j < sprites.length; j++)
            for (int i = 0; i < sprites[j].length; i++)
                sprites[j][i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, width * i, height * j, width, height));
    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int yPos, int xPos) {
        return sprites[yPos][xPos];
    }

    private Bitmap getScaledBitmap(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * GAME_SCALE, bitmap.getHeight() * GAME_SCALE, false);
    }
}
