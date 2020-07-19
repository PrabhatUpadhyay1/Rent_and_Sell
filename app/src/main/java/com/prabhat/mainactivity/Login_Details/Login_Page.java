package com.prabhat.mainactivity.Login_Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prabhat.mainactivity.Home_Page;
import com.prabhat.mainactivity.R;

public class Login_Page extends AppCompatActivity {

    EditText Email, Password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String Email2;
    ProgressDialog dialog;

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

        //****---------> Create Account TextView
        TextView creataccount = findViewById(R.id.creataccount);
        creataccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Page.this, SignUp_Page.class));
                finish();
            }
        });

        //****---------> Forget TextView
        final TextView forgetpassword = findViewById(R.id.forgetpassword);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Page.this, Forget_Page.class));
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

//    private void snackbarShow(Exception e) {
//        Snackbar.make(Login, e.getMessage(), Snackbar.LENGTH_LONG)
//                .setActionTextColor(getResources().getColor(R.color.White))
//                .setDuration(1500)
//                .show();
//    }

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
            Email.setError("Please provide Emailid");

        }
        if (Password2.isEmpty()) {
            Password.setError("Please provide password");

        }
        if (!(Email2.equals(null) && Password2.equals(null))) {
            showLoad();
            firebaseAuth.signInWithEmailAndPassword(Email2, Password2).addOnCompleteListener(Login_Page.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
//                        Toast.makeText(Login_Page.this, "Login Unsuccessful Please try again", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Login_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    //snackbarShow(e);

                }
            });
        }
    }
}
