package com.prabhat.mainactivity.Login_Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prabhat.mainactivity.Activities.Home_Page;
import com.prabhat.mainactivity.R;

public class Login_Page extends AppCompatActivity {

    EditText Email, Password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String Email2;
    ProgressDialog dialog;
    RelativeLayout Login;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        assert user != null;
        if(user != null){
            startActivity(new Intent(Login_Page.this,Home_Page.class));finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        dialog = new ProgressDialog(Login_Page.this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Email = findViewById(R.id.email1);
        Password = findViewById(R.id.password1);
        Login=findViewById(R.id.Login);

        //****---------> Create Account TextView
        TextView creataccount = findViewById(R.id.creataccount);
        creataccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Page.this, SignUp_Page.class));
                overridePendingTransition(0,0);
                finish();
            }
        });

        //****---------> Forget TextView
        final TextView forgetpassword = findViewById(R.id.forgetpassword);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Page.this, Forget_Page.class));
                overridePendingTransition(0,0);
                finish();

            }
        });

        //****Login activity
        final Button loginbtn = (Button) findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

    }

    private void snackbarShow(Exception e) {
        Snackbar.make(Login, e.getMessage(), Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(R.color.White))
                .setBackgroundTint(getResources().getColor(R.color.colorPrimary))
                .setDuration(2000)
                .show();
    }

    private void showLoad() {
        dialog.show();
        dialog.setContentView(R.layout.customedialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    //Sign in with firebase function
    private void SignIn() {
        Email2 = Email.getText().toString();
        String Password2 = Password.getText().toString();
        if (Email2.isEmpty()) {
            Email.setError("Please provide Email Id");
            Email.requestFocus();
        }
        if (Password2.isEmpty()) {
            Password.setError("Please provide password");
            Password.requestFocus();
        }
        if (!Email2.isEmpty() && !Password2.isEmpty()) {
            showLoad();
            firebaseAuth.signInWithEmailAndPassword(Email2, Password2).addOnCompleteListener(Login_Page.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        snackbarShow(task.getException());
                        dialog.hide();
                    } else {
                        Intent intent = new Intent(Login_Page.this, Home_Page.class);
                        intent.putExtra("Email1", Email2);
                        dialog.hide();
                        finish();
                        startActivity(intent);
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
