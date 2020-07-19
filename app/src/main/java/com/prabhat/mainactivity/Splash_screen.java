package com.prabhat.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.prabhat.mainactivity.Login_Details.Login_Page;

public class Splash_screen extends AppCompatActivity {
    ImageView screen;
    TextView screentext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        screen =findViewById(R.id.screen);
        screentext=findViewById(R.id.screentext);
        screen.setX(-1000);
        screentext.setY(-1000);
        screen.animate().translationXBy(1000).setDuration(2000);
        screentext.animate().translationYBy(1000).setDuration(2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(Splash_screen.this, Login_Page.class);
                startActivity(intent);
                finish();
            }
        },4000);


    }
}
