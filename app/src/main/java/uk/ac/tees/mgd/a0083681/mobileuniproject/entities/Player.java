package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.PlayerConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.SFXConstants.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.HelpMethods.*;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.*;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import uk.ac.tees.mgd.a0083681.mobileuniproject.GameStates.Playing;
import uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.Interfaces.BitmapMethods;

public class Player extends Character {
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private int[][] lvlData;

    public float xDrawOffset = 21 * GAME_SCALE;
    public float yDrawOffset = 4 * GAME_SCALE;

    private float airSpeed;
    private boolean jumpingAnim;
    private boolean attackChecked;
    private final Playing playing;
    public boolean shakeAttacking;

    public Player(float x, float y, Playing playing) {
        super(x, y, 100);
        this.playing = playing;
        initHitbox(x,y,PLAYER_HITBOX_WIDTH,PLAYER_HITBOX_HEIGHT);
        initAttackbox(x,y,PLAYER_HITBOX_WIDTH,PLAYER_HITBOX_WIDTH);
    }

    public void update(double delta) {
        if (currentHealth != 0) {
            updateAttackBox();
            updatePos(delta);
            if(attacking)
                checkAttack();
            if (shakeAttacking)
                ShakeAttack();
        }
        setPlayerAnim();
        updateAnimation();

    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        playing.audioManager.ToggleSoundEffect(PLAYERATTACK);
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
    }

    private void updateAttackBox() {
        if(right){
            attackBox.left = hitbox.right + (GAME_SCALE * 10);
            attackBox.right = attackBox.left + (20*GAME_SCALE);
        } else if (left) {
            attackBox.left = hitbox.left - hitbox.width() - (GAME_SCALE * 10);
            attackBox.right = attackBox.left + (20*GAME_SCALE);
        }
        attackBox.top = hitbox.top + (GAME_SCALE * 10);
        attackBox.bottom = attackBox.top + 20 * GAME_SCALE;
    }

    private void updateAnimation(){
        aniTick++;
        if (aniTick >= ANIMATION_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state) && !jumpingAnim) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
                shakeAttacking = false;
                if (state == DEAD_GROUND)
                    playing.setGameOver(true);
            }
            else if (jumpingAnim && aniIndex >= GetSpriteAmount(state))
            {
                aniIndex--;
            }
        }
    }

    public void updatePos(double delta){
        setPlayerMove(false);
        checkSpikesTouched();
        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (left && right))
                return;

        double xSpeed = 0;

        if (left)
            xSpeed = -(delta * 400);
        if (right)
            xSpeed = delta * 400;

        if (!inAir) {
            if(!IsEntityOnFloor(hitbox,lvlData))
                inAir = true;
        }

        if (inAir){
            if (CanMoveHere(hitbox.left,hitbox.top + airSpeed, hitbox.width(), hitbox.height(), lvlData)){
                hitbox.top += airSpeed;
                hitbox.bottom += airSpeed;
                airSpeed += GRAVITY;
                float maxAirSpeed = 16;
                float minAirSpeed = -16;
                if (airSpeed < minAirSpeed)
                    airSpeed = minAirSpeed;
                if (airSpeed > maxAirSpeed)
                    airSpeed = maxAirSpeed;
                updateXpos(xSpeed);
            }
            else {
                float height = hitbox.height();
                if (firstUpdate)
                {
                    hitbox.top = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed) - 1;
                    firstUpdate= false;
                } else
                    hitbox.top = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);

                hitbox.bottom = hitbox.top + height;
                if (airSpeed > 0){
                    playing.audioManager.ToggleSoundEffect(PLAYERLAND);
                    resetInAir();
                    jump = false;
                }
                else {
                    airSpeed = PLAYER_FALL_SPEED;
                }
            }
        }else{
            updateXpos(xSpeed);
        }
        setPlayerMove(true);
    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }

    public void draw(Canvas c, int xLvlOffset){
        c.drawBitmap(BitmapMethods.createFlippedBitmap(entitySpriteManager.PLAYER.getSprite(getState(), getAniIndex()), isFacingLeft(), false), getHitbox().left - xDrawOffset - xLvlOffset,getHitbox().top - yDrawOffset, null);
    }

    private void jump() {
        if (inAir)
            return;
        playing.audioManager.ToggleSoundEffect(JUMP);
        inAir = true;
        airSpeed = JUMP_SPEED;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXpos(double xSpeed) {
        if (CanMoveHere(hitbox.left+(float)xSpeed, hitbox.top, hitbox.width(), hitbox.height(), lvlData)) {
            hitbox.left += xSpeed;
            hitbox.right += xSpeed;
        }else{
            float width = hitbox.width();
            hitbox.left = GetEntityXPosNextToWall(hitbox, (float)xSpeed);
            hitbox.right = hitbox.left + width;
        }
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
    }

    public void setPlayerAnim (){
        int currentAnim = state;
        if (moving) {
            state = RUNNING;
        } else {
            state = IDLE;
        }
        if (inAir) {
            if (airSpeed < 0) {
                state = JUMP;
                jumpingAnim = true;
            } else {
                state = FALLING;
                jumpingAnim = false;
            }
        }
        if (attacking) {
            state = ATTACK_1;
        }
        if (currentHealth == 0) {
            state = DEAD_GROUND;
        }
        if (shakeAttacking){
            state = SHAKE_ATTACK;
        }

        if (state != currentAnim) {
           // playing.audioManager.ToggleSoundEffect(PLAYERMOVING, false);
            resetAnim();
        }
    }

    private void resetAnim() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir= true;
    }

    public void setPlayerMove(boolean moving) {
        this.moving = moving;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int getState(){
        return state;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public void setLeft(boolean left) {
        this.left = left;
        if (left){
            setFacingLeft(true);
        }
    }

    public void setRight(boolean right) {
        this.right = right;
        if (right){
            setFacingLeft(false);
        }
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void changeHealth(int value) {
        currentHealth += value;
        if (currentHealth <= 0){
            playing.audioManager.ToggleSoundEffect(PLAYERDIE);
            currentHealth = 0;
            setAttacking(false);
            setJump(false);
        }else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        super.resetAll();
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

    public void setSpawn(){
        PointF spawnPoint = playing.getLvlManager().getCurrentLevel().getLevel().getPlayerStartPos();
        this.x = spawnPoint.x;
        this.y = spawnPoint.y;
        resetAll();
    }

    public void kill() {
        changeHealth(-currentHealth);
    }

    public void ShakeAttack() {
        if (aniIndex != 1)
            return;
        playing.shakeAttack(attackBox);
    }

    public RectF getAttackBox() {
        return attackBox;
    }

    public void setShakeAttacking(boolean b) {
        shakeAttacking = b;
    }
}
