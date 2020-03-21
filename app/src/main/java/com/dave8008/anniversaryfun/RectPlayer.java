package com.dave8008.anniversaryfun;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private boolean touching = false;

    public boolean isTouching() {
        return touching;
    }

    public Rect getRectangle() {
        return  rectangle;
    }

    public RectPlayer(Rect rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public void update() {

    }

    public void update(Point point, boolean touching) {
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2,
                point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        this.touching = touching;
    }
}
