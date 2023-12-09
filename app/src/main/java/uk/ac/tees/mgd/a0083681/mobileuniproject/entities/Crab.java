package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.*;

import android.graphics.Canvas;
import android.graphics.RectF;

public class Crab extends Enemy{
    public Crab(float x, float y) {
        super(x, y, CRABBY_HEIGHT, CRABBY_WIDTH, CRAB);
        initHitbox(x,y, 22 * GAME_SCALE, 19 * GAME_SCALE);
        initAttackBox(x,y, (int)82 * GAME_SCALE,(int) 19 * GAME_SCALE, 30);
    }

    @Override
    public void resetEnemy() {
        hitbox.left = x;
        hitbox.top = y;
        hitbox.right = x + CRABBY_WIDTH;
        hitbox.bottom = y + CRABBY_HEIGHT;
        super.resetEnemy();
    }

    public void update(int[][] lvlData, double delta, Player player){
        updateBehaviour(lvlData, delta, player);
        updateAnimation();
        updateAttackBox();
    }

    private void updateBehaviour(int[][] lvlData, double delta, Player player) {
        if (stunned)
            return;
        if (firstUpdate)
            firstUpdateCheck(lvlData);
        if (inAir){
            updateInAir(lvlData);
        }else{
            switch (enemyState){
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 3 && !attackChecked)
                        checkEnemyHit(attackBox, player);
                    break;
                case HIT:
                    break;

            }
        }
    }
}
