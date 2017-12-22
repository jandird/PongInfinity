package com.jandir.dalip.ponginfinityfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonStart;
    private ImageButton buttonTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonStart = (ImageButton) findViewById(R.id.buttonStart);
        buttonTutorial = (ImageButton) findViewById(R.id.buttonTutorial);

        buttonStart.setOnClickListener(this);
        buttonTutorial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        if (v == buttonStart) {
            //starting game activity
            startActivity(new Intent(this, GameActivity.class));
        }
        if (v == buttonTutorial) {

            startActivity(new Intent(this, TutorialActivity.class));
        }
    }
}
