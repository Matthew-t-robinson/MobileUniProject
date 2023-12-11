package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.*;

import android.graphics.RectF;

public class Crab extends Enemy{
    public Crab(float x, float y) {
        super(x, y, CRAB_HEIGHT, CRAB_WIDTH, CRAB);
        initHitbox(x,y, 22 * GAME_SCALE, 19 * GAME_SCALE);
        initAttackBox(x,y, (int)82 * GAME_SCALE,(int) 19 * GAME_SCALE, 30);
    }

    @Override
    protected void checkEnemyHit(RectF attackBox, Player player) {
        if (aniIndex == 3 && !attackChecked)
            super.checkEnemyHit(attackBox, player);
    }
}
