package uk.ac.tees.mgd.a0083681.mobileuniproject.UI;

import android.graphics.Canvas;
import android.graphics.RectF;

public class CustomButton {

    private final RectF hitbox;

    private boolean pushed;

    private int aniTick, aniSpeed = 1;
    private int buttonNumber, animFrame;

    private int spriteAmount;
    private boolean animDone = true;
    private UIImages buttonImage;
    private int btnPointerId = -1;

    public CustomButton(float x, float y, int buttonNumber, UIImages buttonImage){
        hitbox = new RectF(x, y, x + buttonImage.getWidth(), y + buttonImage.getHeight());
        this.spriteAmount = buttonImage.getSpriteSheetWidth();
        this.buttonImage = buttonImage;
        this.buttonNumber = buttonNumber;
    }

    public void updateAnimation(){
        aniTick++;
        if (aniTick >= aniSpeed) {
            animFrame ++;
            if (isPushed() && animFrame >= spriteAmount)
            {
                animFrame--;
            }
        }
    }

    public void draw(Canvas c){
        c.drawBitmap(
                buttonImage.getSprite(buttonNumber, animFrame),
                getHitbox().left,
                getHitbox().top,
                null
        );

    }

    public void update(){
        if (isPushed())
            updateAnimation();
    }

    private boolean isDone() {
        return animDone;
    }

    public RectF getHitbox(){
        return hitbox;
    }

    public boolean isPushed(){
        return pushed;
    }

    public void setPushed(boolean pushed) {
        this.pushed = pushed;
        if (pushed){
            animFrame++;
        }
        if (!pushed){
            resetAnim();
        }
    }

    private void resetAnim() {
        aniTick = 0;
        animFrame = 0;
    }

    public int getBtnPointerId() {
        return btnPointerId;
    }

    public void setBtnPointerId(int btnPointerId) {
        this.btnPointerId = btnPointerId;
    }
}

