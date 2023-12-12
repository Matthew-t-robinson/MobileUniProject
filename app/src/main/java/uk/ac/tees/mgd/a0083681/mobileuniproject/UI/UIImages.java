package uk.ac.tees.mgd.a0083681.mobileuniproject.UI;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ButtonConstants.BUTTON_SCALE;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0083681.mobileuniproject.main.MainActivity;
import uk.ac.tees.mgd.a0083681.mobileuniproject.R;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.Interfaces.BitmapMethods;

public enum UIImages implements BitmapMethods {
    HUD_BUTTON_LEFT(R.drawable.hud_button_left,32, 32,2,1, BUTTON_SCALE),
    HUD_BUTTON_RIGHT(R.drawable.hud_button_right,32,32,2,1, BUTTON_SCALE),
    HUD_BUTTON_A(R.drawable.hud_button_a,32,32,2,1, BUTTON_SCALE),
    HUD_BUTTON_B(R.drawable.hud_button_b,32,32,2,1,BUTTON_SCALE),
    MENU_BUTTONS(R.drawable.button_atlas,140, 56,3,4,GAME_SCALE),
    HUD_HEALTH_BAR(R.drawable.health_bar,192, 34,1,1,GAME_SCALE),
    MENU_BACKGROUND(R.drawable.menu_background,282, 336,1,1,1),
    HUD_BUTTON_PAUSE(R.drawable.pause_button,42, 42, 2, 1, GAME_SCALE),
    PAUSE_BACKGROUND(R.drawable.pause_menu,258, 139, 1, 1, GAME_SCALE),
    PAUSE_BUTTONS(R.drawable.urm_buttons,56, 56, 3, 3, 2),
    GAMEOVER_BACKGROUND(R.drawable.death_screen,235, 225, 1,1, GAME_SCALE),
    LEVEL_COMPLETE_BACKGROUND(R.drawable.completed_sprite,224,204,1,1,GAME_SCALE),
    GAME_COMPLETE_BACKGROUND(R.drawable.game_completed,258,258,1,1,GAME_SCALE),
    CONTROLS_OVERLAY(R.drawable.controls,234,227,1,1,GAME_SCALE),
    BUTTON_ESCAPE(R.drawable.escape_button,42,42,2,1,GAME_SCALE);

    private final int width,  height;
    private final Bitmap spriteSheet;
    private final Bitmap[][] sprites;
    private BitmapFactory.Options options = new BitmapFactory.Options();
    UIImages(int resId, int width, int height, int spritesheetWidth, int spritesheetHeight, int scale) {
        sprites = new Bitmap[spritesheetHeight][spritesheetWidth];
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options);
        for (int j = 0; j < sprites.length; j++)
            for (int i = 0; i < sprites[j].length; i++)
                sprites[j][i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, width * i, height * j, width, height),scale);
        this.width = width * scale;
        this.height = height * scale;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Bitmap getSprite(int yPos, int xPos) {
        return sprites[yPos][xPos];
    }

    public int getSpriteSheetWidth(){
        return sprites[0].length;
    }

    public Bitmap getSpriteSheet() {return getScaledBitmap(spriteSheet, GAME_SCALE);}
}
