package com.jandir.dalip.ponginfinityfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by dalip on 2017-07-02.
 */
public class Ball {

    private int x;
    private int y;

    private Bitmap bitmap;

    private double angle;

    private int size;

    private int maxX;
    private int maxY;
    private double xSpeed;
    private double ySpeed;

    private int speed;

    private Player player;

    private Rect detectCol;

    private boolean pointAdded = false;

    public Ball (Context context, int screenX, int screenY, Player player){

        size = screenX/16;
        x = screenX/2 - size/2;
        y = screenY/4;
        speed = screenX/35;

        Random generator = new Random();
        int temp = 3 + generator.nextInt(7);
        angle = Math.PI * (temp/12.0);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

        maxX = screenX;
        maxY = screenY;

        this.player = player;

        detectCol = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {

        xSpeed = speed * cos(angle);
        ySpeed = speed * -1 * sin(angle);
        x += xSpeed;
        y += ySpeed;

        detectCol.left = x;
        detectCol.top = y;
        detectCol.right = x + size;
        detectCol.bottom = y + size;

        int midX = x + (size/2);

        System.out.println(angle*(180/Math.PI));

        if ((x <= 0 && y <= 0) || (x + size >= maxX && y <= 0)) {

            angle = Math.PI + angle;
        }
        else if (x <= 0) {

            if (ySpeed <= 0){

                angle = Math.PI/2 - (angle - Math.PI/2);
            }
            else {

                angle = Math.PI/2 + (Math.PI/2 - angle);
            }
        }
        else if (y <= 0) {

            if (xSpeed <= 0){

                angle = Math.PI - (angle - Math.PI);
            }
            else {

                angle = Math.PI + (Math.PI - angle);
            }

            pointAdded = false;
        }
        else if (x + size >= maxX) {

            if (ySpeed >= 0){

                angle = Math.PI/2 - (angle - Math.PI/2);
            }
            else {

                angle = Math.PI/2 + (Math.PI/2 - angle);
            }
        }
        else if (y + size >= maxY) {

            if (xSpeed >= 0){

                angle = Math.PI - (angle - Math.PI);
            }
            else {

                angle = Math.PI + (Math.PI - angle);
            }
        }
        else if ((y + size) >= player.getY() && (y <= player.getY() + maxX/32) && (x <= (player.getX() + player.getSize()) && (x + size >= player.getX()))){

            if (midX < player.getX() + player.getSize()/2){

                angle = (Math.PI/2 + Math.PI/48) + Math.PI/800 * ((player.getX() + player.getSize()/2) - (x + size/2));
            }
            else if ((midX > player.getX() + player.getSize()/2)) {

                angle = (Math.PI/2 - Math.PI/48) - Math.PI/800 * ((x + size/2) - (player.getX() + player.getSize()/2));
            }
            else {

                angle = (Math.PI/2 + Math.PI/48);
            }

            if (!pointAdded){
                player.increaseScore();
                pointAdded = true;
            }
        }
    }

    public void speedUp () {
        speed += 5;
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

    public double getYSpeed () {
        return ySpeed;
    }

    public Rect getDetectCol() {
        return detectCol;
    }
}

