package com.dave8008.anniversaryfun;

import android.graphics.Canvas;

import java.util.ArrayList;

public class SpriteManager {
    private ArrayList<RectSprite> sprites;
    private int spriteGap;
    private long startTime;

    public SpriteManager(int spriteGap){
        this.spriteGap = spriteGap;

        startTime = System.currentTimeMillis();
        sprites = new ArrayList<>();

        populateSprites();
    }

    private void populateSprites() {
        int currY = -5*Constants.SREEN_HEIGHT/4;
        while(currY < 0) {
            int xStart = (int)(Math.random()*(Constants.SREEN_WIDTH - Constants.SPRITE_WIDTH));
            sprites.add(new RectSprite(xStart, currY));
            currY += Constants.SPRITE_HEIGHT + (int)(Math.random()*spriteGap);
        }
    }

    public boolean playerCollide(RectPlayer player) {

        if (!player.isTouching()) return false;

        for(RectSprite sprite: sprites)
        {
            if (sprite.playerCollide(player))
                return true;
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
