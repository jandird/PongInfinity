package com.jandir.dalip.ponginfinityfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

/**
 * Created by dalip on 2017-06-30.
 */
public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;

    private Thread gameThread = null;

    private Player player;
    private Ball ball;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private int mActivePointerId;

    private int screenX;
    private int screenY;

    private Debuff debuff;
    private boolean debuffCreated = false;
    private boolean debuffTried = false;
    private int missCount;

    private int startCount = 0;

    private boolean isGameOver;

    Context context;

    public GameView(Context context, int screenX, int screenY){
        super(context);

        player = new Player(context, screenX, screenY);
        ball = new Ball(context, screenX, screenY, player);

        surfaceHolder = getHolder();
        paint = new Paint();

        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Pixeled.ttf");
        paint.setTypeface(typeface);

        this.screenX = screenX;
        this.screenY = screenY;

        this.context = context;
    }

    @Override
    public void run(){

        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update(){

        if (ball.getY() > player.getY() / 2 && ball.getY() + ball.getBitmap().getHeight() < player.getY() && ball.getYSpeed() > 0 && !debuffCreated && !debuffTried) {

            Random generator = new Random();
            int temp = generator.nextInt(2);
            debuffTried = true;

            if (temp == 0) {

                debuff = new Debuff(context, screenX, screenY, player, 50 + generator.nextInt(screenX - screenX / 10 - 100), 50 + generator.nextInt(screenY - player.getY() - 100));
                debuffCreated = true;
                missCount = 0;
            }
        }

        if (!isGameOver){

            player.update();
            ball.update();
        }

        if (ball.getY() > player.getY() + screenX / 32) {
            isGameOver = true;
            if (debuffCreated) {
                debuff.remove();
            }
        }

        if (debuffTried) {
            if (ball.getY() + ball.getBitmap().getHeight() >= player.getY()) {
                debuffTried = false;
            }
        }

        if (debuffCreated) {

            if (ball.getY() + ball.getBitmap().getHeight() >= player.getY()) {

                missCount++;
                if (missCount == 5) {
                    debuffCreated = false;
                    debuff.remove();
                }
            }

            if (Rect.intersects(debuff.getDetectCol(), ball.getDetectCol())) {

                boolean speedUp = debuff.hit();

                if (speedUp) {
                    ball.speedUp();
                }
            }
        }
    }

    private void draw(){

        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), paint);

            if (debuffCreated) {

                canvas.drawBitmap(debuff.getBitmap(), debuff.getX(), debuff.getY(), paint);

            }
            canvas.drawText("" + player.getScore(), screenX / 2, screenY - 100, paint);


            if (isGameOver){
                canvas.drawText("GAME OVER", canvas.getWidth()/2, 200, paint);

                paint.setTextSize(50);
                canvas.drawText("RETRY", canvas.getWidth()/4, canvas.getHeight()/5 * 2, paint);
                canvas.drawText("MAIN", canvas.getWidth()/4 * 3, canvas.getHeight()/5 * 2, paint);
                canvas.drawText("MENU", canvas.getWidth()/4 * 3, canvas.getHeight()/5 * 2 + 100, paint);
                paint.setTextSize(100);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {

        try {
            if (startCount == 0) {
                gameThread.sleep(100);
                startCount++;
            } else if (startCount == 1) {
                gameThread.sleep(2000);
                startCount++;
            }
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopMoving();
                break;
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = motionEvent.getPointerId(0);
                int pointerIndex = motionEvent.findPointerIndex(mActivePointerId);
                // Get the pointer's current position
                float x = motionEvent.getX(pointerIndex);

                if (x < screenX/2){
                    player.moveLeft();
                }
                else if(x > screenX/2){
                    player.moveRight();
                }

        }

        if (isGameOver){

            mActivePointerId = motionEvent.getPointerId(0);
            int pointerIndex = motionEvent.findPointerIndex(mActivePointerId);
            // Get the pointer's current position
            float x = motionEvent.getX(pointerIndex);

            if (x < screenX/2){
                context.startActivity(new Intent(context, GameActivity.class));
            }
            else if(x > screenX/2){
                context.startActivity(new Intent(context, MainActivity.class));
            }
        }
        return true;
    }
}
