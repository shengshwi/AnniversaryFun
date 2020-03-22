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
    private String heart;

    public void setPosAndSpeed(int x, int y) {
        rectangle = new Rect(x, y, x + Constants.SPRITE_WIDTH,
                y + Constants.SPRITE_HEIGHT);
        speed = (float)Math.random()*10 + 10.0f;
    }

    public void incrementY() {
        rectangle.top += speed;
        rectangle.bottom += speed;
    }
    public Rect getRectangle() {
        return  rectangle;
    }

    public RectSprite(int x, int y, String heart) {
        setPosAndSpeed(x, y);
        this.color = Color.rgb(255,0,0);
        this.heart = heart;
    }

    public boolean playerCollide(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle());
    }
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(200);
        canvas.drawText(heart, rectangle.left + rectangle.width()/2,
                rectangle.top + rectangle.height()/2, paint);
    }

    @Override
    public void update() {

    }
}
