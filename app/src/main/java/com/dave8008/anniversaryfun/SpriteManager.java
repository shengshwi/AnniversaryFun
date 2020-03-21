package com.dave8008.anniversaryfun;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;

public class SpriteManager {
    private ArrayList<RectSprite> sprites;
    private int spriteGap;
    private long startTime;

    public SpriteManager(int spriteGap, int goal){
        this.spriteGap = spriteGap;

        startTime = System.currentTimeMillis();
        sprites = new ArrayList<>();

        populateSprites(goal);
    }

    private void populateSprites(int goal) {
        int currY = 0;
        for (int i = 0; i < goal; i++) {
            int xStart = (int)(Math.random()*(Constants.SREEN_WIDTH - Constants.SPRITE_WIDTH));
            currY -= Constants.SPRITE_HEIGHT - (int)(Math.random()*spriteGap);
            sprites.add(new RectSprite(xStart, currY));
        }
    }

    public boolean playerCollide(RectPlayer player) {

        Iterator<RectSprite> it = sprites.iterator();
        while (it.hasNext()) {
            RectSprite sprite = it.next();
            if (sprite.playerCollide(player)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public void update() {
        ArrayList<RectSprite> remove = new ArrayList<>();
        for (RectSprite sprite: sprites) {
            sprite.incrementY();
            if (sprite.getRectangle().top >= Constants.SREEN_HEIGHT) {
                remove.add(sprite);
            }
        }

        for (RectSprite sprite: remove) {
            int xStart = (int)(Math.random()*(Constants.SREEN_WIDTH - Constants.SPRITE_WIDTH));
            sprites.add(0, new RectSprite(xStart, 0 -
                    Constants.SPRITE_HEIGHT - (int)(Math.random()*spriteGap)));
            sprites.remove(sprite);
        }
    }

    public void draw(Canvas canvas) {
        for (RectSprite sprite: sprites) {
            sprite.draw(canvas);
        }
    }

}
