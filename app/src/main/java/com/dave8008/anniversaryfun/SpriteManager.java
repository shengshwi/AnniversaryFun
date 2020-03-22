package com.dave8008.anniversaryfun;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
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
        ArrayList<String> hearts = new ArrayList<>(Arrays.asList(
                "\uD83D\uDC9D",
                "\uD83D\uDC98",
                "\uD83D\uDC97",
                "\uD83D\uDC96",
                "\uD83D\uDC93",
                "\uD83D\uDC99",
                "\uD83D\uDC9C",
                "\uD83D\uDC9F",
                "❦",
                "❤",
                "ღ",
                "ও",
                "❣"
        ));
        for (int i = 0; i < goal; i++) {
            int xStart = (int)(Math.random()*(Constants.SREEN_WIDTH - Constants.SPRITE_WIDTH));
            currY += -Constants.SPRITE_HEIGHT - (int)(Math.random()*spriteGap);
            sprites.add(new RectSprite(xStart, currY, hearts.get(i)));
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
        for (RectSprite sprite: sprites) {
            sprite.incrementY();
            if (sprite.getRectangle().top >= Constants.SREEN_HEIGHT) {
                sprite.setPosAndSpeed(
                        (int)(Math.random()*(Constants.SREEN_WIDTH - Constants.SPRITE_WIDTH)),
                        0 - Constants.SPRITE_HEIGHT - (int)(Math.random()*2*spriteGap));
            }
        }
    }

    public void draw(Canvas canvas) {
        for (RectSprite sprite: sprites) {
            sprite.draw(canvas);
        }
    }

}
