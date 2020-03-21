package com.dave8008.anniversaryfun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private RectPlayer player;
    private Point playerPoint;
    private SpriteManager spriteManager;
    private boolean celebration = false;
    private long celebrationTime;
    private boolean touching = false;
    private int score = 0;


    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        player = new RectPlayer(new Rect(100,100,200, 200));
        playerPoint = new Point(150,150);

        spriteManager = new SpriteManager(350);

        setFocusable(true);
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
            } catch (Exception e) {e.printStackTrace();

            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (!celebration) {
                    touching = true;
                    playerPoint.set((int)event.getX(), (int)event.getY());
                }
                else if (System.currentTimeMillis() - celebrationTime >= 3000) {
                    celebration = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                touching = false;
        }
        return true;
//        return super.onTouchEvent(event);
    }

    public void update() {
        if (celebration) return;

        player.update(playerPoint, touching);
        spriteManager.update();

        if (spriteManager.playerCollide(player)) {
            score++;
            if (score >= 10) {
                celebration = true;
                celebrationTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        spriteManager.draw(canvas);
    }
}
