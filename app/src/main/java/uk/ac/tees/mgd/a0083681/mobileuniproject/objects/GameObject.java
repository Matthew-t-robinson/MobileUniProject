package uk.ac.tees.mgd.a0083681.mobileuniproject.objects;

import static uk.ac.tees.mgd.a0083681.mobileuniproject.helpers.GameConstants.AllConstants.ANIMATION_SPEED;
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
    protected int aniTick;
    protected int aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objType){
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    public void update(){
        if (doAnimation)
            updateAnimationTick();
    }

    protected void initHitbox(int width, int height) {
        hitbox = new RectF(x,y,x + (width * GAME_SCALE),y + (height * GAME_SCALE));
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANIMATION_SPEED) {
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
        doAnimation = objType != CHEST;
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
