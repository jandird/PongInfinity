package com.jandir.dalip.ponginfinityfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by dalip on 2017-07-06.
 */
public class Debuff {

    private int x;
    private int y;
    private int size;

    private Bitmap bitmap;

    private Player player;

    private int type;

    private Rect detectCol;

    public Debuff (Context context, int screenX, int screenY, Player player, int x, int y){

        this.x = x;
        this.y = y;

        Random generator = new Random();
        int temp = generator.nextInt(13);

        size = screenX / 10;

        if (temp < 3){

            type = 1;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shrink);
        }
        else if (temp >= 3 && temp <= 5 && player.getDivHeight() < 2.0){

            type = 2;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.moveup);
        }
        else if (temp >= 6 && temp <= 8){

            type = 3;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedup);
        }
        else if (temp >= 9 && temp <= 11){

            type = 4;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plus1);
        }
        else {

            type = 5;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plus3);
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

        this.player = player;

        detectCol = new Rect(x, y, x+size, y+size);
    }

    public boolean hit (){

        remove();

        if (type == 1){
            player.decSize();
        }
        else if (type == 2){
            player.incHeight();
        }
        else if (type == 3){
            return true;
        }
        else if (type == 4){
            player.increaseScore();
        }
        else if (type == 5){
            player.increaseScore();
            player.increaseScore();
            player.increaseScore();
        }

        return false;
    }

    public void remove () {

        x = -500;
        y = -500;

        detectCol.left = x;
        detectCol.top = y;
        detectCol.right = x + size;
        detectCol.bottom = y + size;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public Bitmap getBitmap () {
        return bitmap;
    }

    public Rect getDetectCol () {
        return detectCol;
    }
}
