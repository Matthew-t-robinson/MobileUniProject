package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.Directions.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.SFXConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.CanMoveHere;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.IsEntityOnFloor;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.IsFloor;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.IsSightClear;

import android.graphics.Canvas;
import android.graphics.RectF;

import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.BaseState;
import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.Playing;
import uk.ac.tees.mgd.a0083681.mobileuniproject.Sound.AudioManager;

public abstract class Enemy extends Character {
    protected int enemyType;
    protected float fallSpeed;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = TILES_SIZE;
    protected boolean active = true;
    protected boolean stunned = false;
    protected int attackBoxOffsetX;
    protected AudioManager audioManager = new AudioManager();

    public Enemy(float x, float y, int enemyType) {
        super(x, y, enemyType);
        this.enemyType = enemyType;
    }

    public void update(int[][] lvlData, double delta, Player player){
        updateBehaviour(lvlData, delta, player);
        updateAnimation();
        updateAttackBox();
    }

    protected void updateInAir(int[][] lvlData){
        if (CanMoveHere(hitbox.left,hitbox.top + fallSpeed,hitbox.width(),hitbox.height(),lvlData)) {
            hitbox.top += fallSpeed;
            hitbox.bottom += fallSpeed;
            fallSpeed += GRAVITY;
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

    protected void newState(int state) {
        this.state = state;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void move(int[][] lvlData){
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed -= 5;
        else
            xSpeed = 5;

        if (CanMoveHere(hitbox.left + xSpeed, hitbox.top - 2, hitbox.width(), hitbox.height(), lvlData))
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
            facingLeft = false;
        }
        else if (player.hitbox.right < hitbox.left){
            walkDir = LEFT;
            facingLeft = true;
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
            facingLeft = false;
        }else{
            walkDir = LEFT;
            facingLeft = true;
        }
    }

    protected void updateAnimation(){
        if (stunned && state != HIT)
            return;
        aniTick++;
        if (aniTick >= ANIMATION_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, aniIndex)) {
                aniIndex = 0;
                switch (state){
                    case HIT:
                    case ATTACK:
                        state = IDLE;
                        break;
                    case DEAD:
                        active = false;
                        break;
                    case IDLE:
                        state = RUNNING;
                        break;
                }
            }
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getState() {
        return state;
    }

    public void resetAll() {
        active = true;
        fallSpeed = 0;
        stunned = false;
        super.resetAll();
    }

    public void damage(int amount) {
        currentHealth -= amount;
        if (currentHealth <=0) {
            stunned = false;
            audioManager.ToggleSoundEffect(ENEMYDIE);
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
        super.initAttackbox(x,y,width,height);
        attackBoxOffsetX = (int)(GAME_SCALE *  xOffset);
    }

    public boolean isAlive(){
        return state != DEAD || stunned;
    }

    void updateBehaviour(int[][] lvlData, double delta, Player player) {

        if (firstUpdate)
            firstUpdateCheck(lvlData);
        if (inAir){
            updateInAir(lvlData);
        }else{
            switch (state){
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
                    checkEnemyHit(attackBox, player);
                    break;
                case HIT:
                    break;

            }
        }
    }
}


