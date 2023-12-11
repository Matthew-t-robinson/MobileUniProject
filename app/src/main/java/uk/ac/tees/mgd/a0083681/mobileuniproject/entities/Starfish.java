package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.*;

import android.graphics.Canvas;
import android.graphics.RectF;

public class Starfish extends Enemy{
    public Starfish(float x, float y) {
        super(x, y, STARFISH_HEIGHT, STARFISH_WIDTH, STARFISH);
        initHitbox(x,y, 17 * GAME_SCALE, 21 * GAME_SCALE);
        initAttackBox(x,y, (int) 40 * GAME_SCALE,(int) 21 * GAME_SCALE, 13);
    }

    @Override
    protected void checkEnemyHit(RectF attackBox, Player player) {
        if (aniIndex >= 3 && !attackChecked)
            super.checkEnemyHit(attackBox, player);
    }
}
