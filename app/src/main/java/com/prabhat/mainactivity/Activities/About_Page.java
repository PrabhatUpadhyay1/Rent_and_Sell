package com.prabhat.mainactivity.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prabhat.mainactivity.R;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class About_Page extends AppCompatActivity {
     String phone;
    FirebaseAuth firebaseAuth;
     FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

   firebaseAuth=FirebaseAuth.getInstance();
   firestore=FirebaseFirestore.getInstance();
    ImageView imageView3=findViewById(R.id.imageView3);
    imageView3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        showDialog1();
        }
    });

        final ImageView imageView2=findViewById(R.id.imageView2);
         phone="8700564484";
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makecall();
            }
        });

    }

    private void showDialog1() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(About_Page.this);
        final View view=getLayoutInflater().inflate(R.layout.feedback,null);

        final Button button2=view.findViewById(R.id.button2);
        final EditText editText=view.findViewById(R.id.feed);

        builder.setView(view);

        final AlertDialog alertDialog=builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=editText.getText().toString().trim();
                if (s.isEmpty()) {
                    editText.setError("Field is Empty");
                    editText.requestFocus();
                } else {

                    HashMap<String,String> hashMap =new HashMap<>();

                    hashMap.put("email",firebaseAuth.getCurrentUser().getEmail());
                    hashMap.put("Feedback",s);
                    firestore.collection("feedback").document().set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(About_Page.this, "Sending....", LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            Toast.makeText(About_Page.this, " Thank you for your feedback", LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alertDialog.dismiss();
                            Toast.makeText(About_Page.this, e.getMessage(), LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        alertDialog.show();

    }


    private void makecall() {
        if(phone.length()>0){
            if (ContextCompat.checkSelfPermission(About_Page.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(About_Page.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
        Intent intent=new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+"8700564484"));
        startActivity(intent);
    }
}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

