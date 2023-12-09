package uk.ac.tees.mgd.a0083681.mobileuniproject.objects;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.GAME_SCALE;
import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.ObjectConstants.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class GameObject {
    protected int x, y, objType;
    protected RectF hitbox;
    protected boolean doAnimation, animationDone;
    protected int aniTick,aniSpeed = 10;
    protected int aniIndex;
    protected int xDrawOffset, yDrawOffset;
    protected Paint redpaint = new Paint();

    public GameObject(int x, int y, int objType){
        this.x = x;
        this.y = y;
        this.objType = objType;
        redpaint.setColor(Color.RED);
    }

    public void update(){
        if (doAnimation)
            updateAnimationTick();
    }

    protected void initHitbox(int width, int height) {
        hitbox = new RectF(x,y,x + (width * GAME_SCALE),y + (height * GAME_SCALE));
    }

    public void drawHitbox(Canvas c, int xLvlOffset) {
        c.drawRect(hitbox.left - xLvlOffset, hitbox.top,hitbox.right - xLvlOffset, hitbox.bottom,redpaint);
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(objType)) {
                if (objType == CHEST) {
                    doAnimation = false;
                    animationDone = true;
                    aniIndex--;
                }else {
                    aniIndex = 0;
                }
            }
        }
    }

    protected void reset() {
        aniIndex = 0;
        aniTick = 0;
        animationDone = false;

        if (objType == CHEST)
            doAnimation = false;
        else
            doAnimation = true;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public int getObjType() {
        return objType;
    }

    public RectF getHitbox() {
        return hitbox;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public void setAnimation(boolean b) {
        doAnimation = b;
    }

    public boolean isAnimationDone(){
        return animationDone;
    }
}
