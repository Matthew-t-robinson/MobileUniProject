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

    public void resetEnemy() {
        hitbox.left = x;
        hitbox.top = y;
        hitbox.right = x + STARFISH_WIDTH;
        hitbox.bottom = y + STARFISH_HEIGHT;
        super.resetEnemy();
    }

    public void update(int[][] lvlData, double delta, Player player){
        if (!stunned) {
            updateBehaviour(lvlData, delta, player);
            updateAnimation();
            updateAttackBox();
        }
    }
    private void updateBehaviour(int[][] lvlData, double delta, Player player) {

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
                    if (aniIndex >= 3 && !attackChecked)
                        checkEnemyHit(attackBox, player);
                    break;
                case HIT:
                    break;

            }
        }
    }
}
