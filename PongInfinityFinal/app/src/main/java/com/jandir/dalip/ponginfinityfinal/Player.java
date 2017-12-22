package com.jandir.dalip.ponginfinityfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by dalip on 2017-07-01.
 */
public class Player {

    private Bitmap bitmap;

    private int x;
    private int y;

    private int speed;

    private boolean movingLeft;
    private boolean movingRight;

    private int maxX;
    private int minX;
    private int scX;
    private int scY;

    private int size;
    private double divLength;
    private double divHeight;

    private int score = 0;

    public Player(Context context, int screenX, int screenY){

        divLength = 3.0;
        divHeight = 1.5;

        speed = screenX/40;

        size = (int)(screenX/divLength);
        x = screenX / 2 - size / 2;
        y = (int)(screenY/divHeight);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_norm);
        bitmap = Bitmap.createScaledBitmap(bitmap, size, screenX/32, true);

        movingLeft = false;
        movingRight = false;

        minX = 0;
        maxX = screenX;
        scX = screenX;
        scY = screenY;
    }

    public void moveLeft() {

        movingLeft = true;
    }

    public void moveRight() {

        movingRight = true;
    }

    public void stopMoving() {

        movingLeft = false;
        movingRight = false;
    }

    public void update() {

        if (movingLeft == true && x > minX){

            x -= speed;
        }
        else if (movingRight == true && x + size < maxX){

            x += speed;
        }
    }

    public void decSize (){

        int oldSize = size;
        divLength += 1.0;
        size = (int)(scX/divLength);
        bitmap = Bitmap.createScaledBitmap(bitmap, size, scX/32, true);
        x = x + (oldSize - size)/2;
    }

    public void incHeight() {

        divHeight += 0.1;
        y = (int)(scY / divHeight);
    }

    public void increaseScore() {
        score++;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSize() {
        return size;
    }

    public int getScore() {
        return score;
    }

    public double getDivHeight() { return divHeight; }
}
