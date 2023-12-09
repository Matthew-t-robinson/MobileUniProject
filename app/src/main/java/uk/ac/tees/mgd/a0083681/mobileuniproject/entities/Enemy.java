package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.Directions.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.CanMoveHere;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.IsEntityOnFloor;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.IsFloor;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.IsSightClear;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.main.GamePanel.TILES_SIZE;

import android.graphics.Canvas;
import android.graphics.RectF;

public abstract class Enemy extends Entity{
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 8;
    protected boolean firstUpdate = true;
    protected boolean facingRight;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.2f * GAME_SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = 96;
    protected int maxHealth, currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;
    protected boolean stunned = false;
    protected RectF attackBox;
    protected int attackBoxOffsetX;

    public Enemy(float x, float y, int Height, int Width, int enemyType) {
        super(x, y, Height, Width);
        this.enemyType = enemyType;
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    protected void updateInAir(int[][] lvlData){
        if (CanMoveHere(hitbox.left,hitbox.top + fallSpeed,hitbox.width(),hitbox.height(),lvlData, false)) {
            hitbox.top += fallSpeed;
            hitbox.bottom += fallSpeed;
            fallSpeed += gravity;
        }else {
            float height = hitbox.height();
            inAir = false;
            hitbox.top = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            hitbox.bottom = hitbox.top + height;
            tileY = (int) (hitbox.top / TILES_SIZE);
        }
    }

    protected void updateAttackBox() {
        float width = attackBox.width();
        attackBox.left = hitbox.left - attackBoxOffsetX;
        attackBox.right = attackBox.left + width;
        attackBox.top = hitbox.top;
        attackBox.bottom = hitbox.bottom;
    }

    public void drawAttackBox(Canvas c, int xLvlOffset) {
        c.drawRect(attackBox.left - xLvlOffset, attackBox.top,attackBox.right - xLvlOffset, attackBox.bottom,redpaint);
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void move(int[][] lvlData){
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed -= 5;
        else
            xSpeed = 5;

        if (CanMoveHere(hitbox.left + xSpeed, hitbox.top - 2, hitbox.width(), hitbox.height(), lvlData, false))
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.left += xSpeed;
                hitbox.right += xSpeed;
                return;
            }
        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player){
        if (player.hitbox.left > hitbox.right) {
            walkDir = RIGHT;
            facingRight = true;
        }
        else if (player.hitbox.right < hitbox.left){
            walkDir = LEFT;
            facingRight = false;
        }
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player){
        tileY = (int) (hitbox.top / TILES_SIZE);
        int playerTileY = (int) player.getHitbox().top / TILES_SIZE;
        if(playerTileY == tileY)
            if (isPlayerInRange(player))
                if (IsSightClear(lvlData, hitbox, player.getHitbox(), tileY))
                    return true;
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int)Math.abs(player.hitbox.left - hitbox.left);
        return absValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player){
        int absValue = (int) Math.abs(player.hitbox.left - hitbox.left);
        return absValue <= attackDistance;
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT){
            walkDir = RIGHT;
            facingRight = true;
        }else{
            walkDir = LEFT;
            facingRight = false;
        }
    }

    protected void updateAnimation(){
        if (stunned && enemyState != HIT)
            return;
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, aniIndex)) {
                aniIndex = 0;
                switch (enemyState){
                    case IDLE:
                        enemyState = RUNNING;
                        break;
                    case ATTACK:
                    case HIT:
                        enemyState = IDLE;
                        break;
                    case DEAD:
                        active = false;
                        break;
                }
            }
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void resetEnemy() {
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;
        stunned = false;
    }

    public void damage(int amount) {
        currentHealth -= amount;
        if (currentHealth <=0) {
            stunned = false;
            newState(DEAD);
        }
        else
            newState(HIT);
    }


    protected void checkEnemyHit(RectF attackBox, Player player) {
        if (RectF.intersects(attackBox,player.getHitbox())){
            player.changeHealth(-GetEnemyDmg(enemyType));
            attackChecked = true;
        }
    }

    public boolean isActive(){
        return active;
    }

    public void setStun(boolean b) {
        stunned = b;
        if (stunned){
            newState(DEAD);
            aniIndex = 4;
        }
        else
            newState(IDLE);
    }

    protected void initAttackBox(float x, float y, float width, float height, int xOffset) {
        attackBox = new RectF(x,y,x + width,y + height);
        attackBoxOffsetX = (int)(GAME_SCALE *  xOffset);
    }

    public boolean isAlive(){
        return enemyState != DEAD || stunned;
    }
}


