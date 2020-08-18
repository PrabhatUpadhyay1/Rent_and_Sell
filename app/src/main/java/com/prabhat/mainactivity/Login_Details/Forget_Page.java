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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prabhat.mainactivity.R;

public class Forget_Page extends AppCompatActivity {
    EditText email3;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog dialog;
    RelativeLayout Forget;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_page);
        dialog = new ProgressDialog(Forget_Page.this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Forget=findViewById(R.id.Forget);
        email3 = findViewById(R.id.email3);



        //back button
        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login_Page.class));
                overridePendingTransition(0,0);
                finish();
            }
        });
        //****------------>SignIn TextView
        TextView signinforget = findViewById(R.id.signinforget);
        signinforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forget_Page.this, Login_Page.class));
                overridePendingTransition(0,0);
                finish();
            }
        });


        //**Forget password activity
        final Button Submit = findViewById(R.id.loginbtn2);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ForgetPassword();
            }
        });
    }

    //snackBar function
    private void snackbarShow(Exception e) {
        Snackbar.make(Forget, e.getMessage(), Snackbar.LENGTH_LONG)
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


    //Forget password function with firebase
    private void ForgetPassword() {
        String Email3 = email3.getText().toString();
        if (Email3.isEmpty()) {
            email3.setError("Please enter email Id");
            email3.requestFocus();
        } else {
            showLoad();
            firebaseAuth.sendPasswordResetEmail(Email3).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Forget_Page.this, "Password reset link has been sent to your EmailId", Toast.LENGTH_SHORT).show();
                        dialog.hide();
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
