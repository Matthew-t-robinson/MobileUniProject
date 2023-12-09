package uk.ac.tees.mgd.a0083681.mobileuniproject.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;

public abstract class Entity {

    protected float x,y;
    protected Paint redpaint = new Paint();

    protected int width, height;
    protected RectF hitbox;
    public Entity(float x, float y, int Height, int Width){
        this.x = x;
        this.y = y;
        this.width = Width;
        this.height = Height;
        redpaint.setColor(Color.RED);

    }

    public void drawHitbox(Canvas c, int xLvlOffset) {

        c.drawRect(hitbox.left - xLvlOffset, hitbox.top,hitbox.right - xLvlOffset, hitbox.bottom,redpaint);
    }

    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new RectF(x,y,x + width,y + height);
    }

    public RectF getHitbox() {
        return hitbox;
    }
}
