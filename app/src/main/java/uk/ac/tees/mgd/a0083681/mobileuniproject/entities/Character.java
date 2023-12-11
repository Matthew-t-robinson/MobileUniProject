package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.GetMaxHealth;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.EnemyConstants.IDLE;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Character {

    protected float x,y;
    protected Paint redpaint = new Paint();

    protected int width, height;

    protected int aniIndex, aniTick;
    protected RectF hitbox, attackBox;
    protected int state;
    protected boolean attackChecked;
    protected boolean inAir;
    protected int currentHealth, maxHealth;
    protected boolean firstUpdate = true;

    protected boolean facingLeft;

    public Character(float x, float y, int Height, int Width, int entityType){
        this.x = x;
        this.y = y;
        this.width = Width;
        this.height = Height;
        redpaint.setColor(Color.RED);
        maxHealth = GetMaxHealth(entityType);
        currentHealth = maxHealth;
    }

    public void drawHitbox(Canvas c, int xLvlOffset) {

        c.drawRect(hitbox.left - xLvlOffset, hitbox.top,hitbox.right - xLvlOffset, hitbox.bottom,redpaint);
    }

    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new RectF(x,y,x + width,y + height);
    }

    protected void initAttackbox(float x, float y, float width, float height) {
        attackBox = new RectF(x,y,x + width,y + height);
    }

    public RectF getHitbox() {
        return hitbox;
    }

    public void resetAll() {
        aniIndex = 0;
        aniTick = 0;
        firstUpdate = true;
        state = IDLE;
        currentHealth = maxHealth;
        float hitboxWidth = hitbox.width();
        float hitboxHeight = hitbox.height();
        hitbox.left = x;
        hitbox.top = y;
        hitbox.right = hitbox.left + hitboxWidth ;
        hitbox.bottom = hitbox.top + hitboxHeight;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
}
