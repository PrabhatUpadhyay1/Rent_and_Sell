package com.prabhat.mainactivity.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.prabhat.mainactivity.R;
import com.squareup.picasso.Picasso;
public class Photo_Full_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen);
        ImageView image=findViewById(R.id.Profileimage);
        Intent intent=getIntent();
        Picasso.get().load(intent.getStringExtra("image")).into(image);
        ImageView imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
