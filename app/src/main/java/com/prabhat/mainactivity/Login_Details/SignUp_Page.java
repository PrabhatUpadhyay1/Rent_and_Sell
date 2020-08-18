package com.prabhat.mainactivity.Login_Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prabhat.mainactivity.Activities.Home_Page;
import com.prabhat.mainactivity.R;

import java.util.HashMap;

public class SignUp_Page extends AppCompatActivity {
    EditText email1, password1, phone;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog dialog;
    String Email1;
    RelativeLayout SignUp;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        dialog = new ProgressDialog(SignUp_Page.this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        email1 = findViewById(R.id.email1);
        password1 = findViewById(R.id.password1);
        phone = findViewById(R.id.phone);
        SignUp = findViewById(R.id.SignUp);


        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login_Page.class));
                overridePendingTransition(0,0);
                finish();
            }
        });

        //****------------->SignIn TextView
        TextView loginsignin = findViewById(R.id.signinbtn);
        loginsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp_Page.this, Login_Page.class));
                overridePendingTransition(0,0);
                finish();

            }
        });
        //**Sign up activity
        Button signupbtn = findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

    }


    private void snackbarShow(Exception e) {
        Snackbar.make(SignUp, e.getMessage(), Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(R.color.White))
                .setBackgroundTint(getResources().getColor(R.color.colorPrimary))
                .setDuration(1500)
                .show();
    }

    private void showLoad() {
        dialog.show();
        dialog.setContentView(R.layout.customedialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }


    private void SignUp() {
        Email1 = email1.getText().toString().trim();
        String Password1 = password1.getText().toString().trim();
        String Phone = phone.getText().toString().trim();
        if (Email1.isEmpty()) {
            email1.setError("Please provide Email Id");
            email1.requestFocus();
        }
        if (!Email1.contains("@,.,com")) {
            email1.setError("Please provide correct Email Id");
            email1.requestFocus();
        }
        if (Password1.isEmpty()) {
            password1.setError("Please provide password");
            password1.requestFocus();
        }
        if (Password1.length() < 8) {
            password1.setError("Minimum 8 character requires");
            password1.requestFocus();
        }
        if (Phone.isEmpty()) {
            phone.setError("Please enter Phone no.");
            phone.requestFocus();
        }
        if (!(Phone.length() == 10)) {
            phone.setError("Please enter correct phone no.");
            phone.requestFocus();
        }
        if (!Email1.isEmpty() && !Password1.isEmpty()) {
            showLoad();
            firebaseAuth.createUserWithEmailAndPassword(Email1, Password1).addOnCompleteListener(SignUp_Page.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        snackbarShow(task.getException());
                        dialog.hide();
                    } else {
                        String Email1 = email1.getText().toString().trim();
                        String Password1 = password1.getText().toString().trim();
                        String Phone = phone.getText().toString().trim();
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("Name", null);
                        hashMap.put("Email", Email1);
                        hashMap.put("Password", Password1);
                        hashMap.put("Phone1", Phone);
                        hashMap.put("img", null);
                        firebaseFirestore.collection("User").document(Email1).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(SignUp_Page.this, Home_Page.class));
                                Toast.makeText(SignUp_Page.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                dialog.hide();
                                finish();
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.hide();
                    snackbarShow(e);

                }
            });
        }

    }
}
