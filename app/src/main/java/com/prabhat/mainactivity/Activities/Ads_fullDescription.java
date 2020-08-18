package com.prabhat.mainactivity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prabhat.mainactivity.R;
import com.squareup.picasso.Picasso;

public class Ads_fullDescription extends AppCompatActivity {
    TextView productprice, productname, productdescription, Status;
    String Phone1, image, name, email, customer, id;
    ImageView imageView;
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Picasso.get().load(image).into(imageView);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fulldescription);
        productname = findViewById(R.id.productname);
        productprice = findViewById(R.id.productprice);
        productdescription = findViewById(R.id.productdescription);
        Status = findViewById(R.id.status);
        final TextView Phone = findViewById(R.id.phonenumber);
        final TextView nameid = findViewById(R.id.nameid);
        TextView Emailid = findViewById(R.id.emailid);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        imageView = findViewById(R.id.imageView8);
        Intent intent1 = getIntent();
        name = intent1.getStringExtra("Name");
        String price = intent1.getStringExtra("Price");
        String desc = intent1.getStringExtra("Description");
        final String status = intent1.getStringExtra("status");
        image = intent1.getStringExtra("image");
        customer = intent1.getStringExtra("customer");
        Phone1 = intent1.getStringExtra("Phone1");
        email = intent1.getStringExtra("email");
        id = intent1.getStringExtra("id");
        Log.i("id", id + "");


        ImageView imageView8 = findViewById(R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ads_fullDescription.this, Photo_Full_Screen.class);
                intent.putExtra("image", image);
                overridePendingTransition(0,0);
                startActivity(intent);
            }
        });
//        Log.i("Phone",Phone1+"");
        if ("Buy".equals(status)) {
            productprice.setText("₹ " + price);
            Status.setTextColor(getResources().getColor(R.color.Blue));
        } else {
            productprice.setText("₹ " + price + "/Day");
            Status.setTextColor(getResources().getColor(R.color.Green));
        }

        productname.setText(name);
        productdescription.setText(desc);
        Status.setText(status);
        Picasso.get().load(image).into(imageView);
        Emailid.setText(email);
        Phone.setText(Phone1);
        nameid.setText(customer);

        final ImageView makecall = findViewById(R.id.makecall);
        makecall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makecall();
            }
        });
    }

    private void makecall() {
        if (Phone1.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(Ads_fullDescription.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Ads_fullDescription.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                Log.i("call", Phone1 + "");
                Intent intentImage = new Intent(Intent.ACTION_CALL);
                intentImage.setData(Uri.parse("tel:" + Phone1));
                startActivity(intentImage);
            }
        } else {
            Toast.makeText(Ads_fullDescription.this, "Phone number is not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makecall();
            }
        }

    }

}
