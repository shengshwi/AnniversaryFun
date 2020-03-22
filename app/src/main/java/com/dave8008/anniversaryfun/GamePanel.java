package com.dave8008.anniversaryfun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private RectPlayer player;
    private Point playerPoint;
    private SpriteManager spriteManager;

    private boolean celebration = false;
    private long celebrationTime;

    private boolean intro = true;
    private long introTime;

    private boolean touching = false;
    private int score = 0;
    private int goal = 10;


    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        player = new RectPlayer(new Rect(0,0,200, 200));
        playerPoint = new Point(150,150);

        startGame();
        intro = true;
        introTime = System.currentTimeMillis();
        setFocusable(true);
    }

    public void startGame() {
        spriteManager = new SpriteManager(650, goal);
        touching = false;
        celebration = false;
        intro = false;
        score = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}

            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!celebration && !intro) {
                    touching = true;
                    playerPoint.set((int)event.getX(), (int)event.getY());
                }
                else if ((celebration && System.currentTimeMillis() - celebrationTime >= 1000) ||
                        (intro && System.currentTimeMillis() - introTime >= 500)){
                    startGame();
                }
                break;
            case MotionEvent.ACTION_UP:
                touching = false;
                break;
        }
        return true;
    }

    public void update() {
        if (celebration) return;

        player.update(playerPoint);
        spriteManager.update();

        if (touching && spriteManager.playerCollide(player)) {
            touching = false;  //only get one at a time
            score++;
            if (score >= goal) {
                celebration = true;
                celebrationTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);


        if (celebration || intro) {
            Paint textPaint = new Paint();
            textPaint.setARGB(200, 254, 0, 0);
            textPaint.setTextAlign(Paint.Align.CENTER);
            if (celebration) {
                List<String> lines = new ArrayList<>(Arrays.asList(
                        "Dear Dani,",
                        "Roses are red.",
                        "Violets are blue.",
                        "My heart has fallen",
                        "over and over for you!"
                ));
                textPaint.setTextSize(80);
                int y = 300;
                for (String s: lines) {
                    canvas.drawText(s, canvas.getWidth()/2, y, textPaint);
                    y += 100;
                }


            }
            textPaint.setTextSize(100);
            String text = intro ? "Touch 10 hearts!" : "Happy Anniversary!!!";
            canvas.drawText(text, canvas.getWidth()/2, canvas.getHeight()/2  , textPaint);
            if (celebration) {
                textPaint.setTextSize(80);
                int y = canvas.getHeight()/2 + 220;
                canvas.drawText("X   X   ,", canvas.getWidth()/2 + 100, y, textPaint);
                canvas.drawText("   ღ  ღ ", canvas.getWidth()/2 + 96, y-12, textPaint);
                y += 100;
                canvas.drawText("David\uD83D\uDC9E", canvas.getWidth()/2 + 100, y, textPaint);
            }
            textPaint.setTextSize(50);
            canvas.drawText("Touch screen to begin.", canvas.getWidth()/2,
                    canvas.getHeight() - 150  , textPaint);
        } else {
            player.draw(canvas);
            spriteManager.draw(canvas);
        }
    }
}
