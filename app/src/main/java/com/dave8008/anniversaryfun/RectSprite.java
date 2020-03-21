package com.dave8008.anniversaryfun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class RectSprite implements GameObject {

    private Rect rectangle;
    private int color;
    private float speed;

    public void incrementY() {
        rectangle.top += speed;
        rectangle.bottom += speed;
    }
    public Rect getRectangle() {
        return  rectangle;
    }

    public RectSprite(int x, int y) {
        this.rectangle = new Rect(x, y, x + Constants.SPRITE_WIDTH,
                y + Constants.SPRITE_HEIGHT);
        this.color = Color.rgb(255,0,0);
        speed = (float)Math.random()*5 + 10.0f;
    }

    public boolean playerCollide(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle());
    }
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
//        canvas.drawText("test", rectangle.left,rectangle.right);
    }

    @Override
    public void update() {

    }
}
