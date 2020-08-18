package com.prabhat.mainactivity.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prabhat.mainactivity.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

//
public class User_Profile_Page extends AppCompatActivity {
    CircleImageView imageView;
    FirebaseFirestore firebaseFirestore;
    Snackbar snackbar;
    FirebaseAuth firebaseAuth;
    private Uri imageuri;
    TextView Profilename, Profileemail, Profilephone;
    String Email;
    ProgressDialog dialogClass;
    private int gallarypic = 1;
    Button button;
    StorageReference storageReference;
    RelativeLayout ProfileSection;
    RelativeLayout cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        Email = firebaseAuth.getCurrentUser().getEmail();
        ProfileSection = findViewById(R.id.ProfileSection);
        Log.i("Eml", Email + "");
        String Name = firebaseAuth.getCurrentUser().getDisplayName();
        String Phone = firebaseAuth.getCurrentUser().getPhoneNumber();
        cardView = findViewById(R.id.cardView);
        cardView.setVisibility(View.INVISIBLE);
        dialogClass = new ProgressDialog(this);
        imageView = findViewById(R.id.Profileimage);
        Profilename = findViewById(R.id.Profilename);
        Profileemail = findViewById(R.id.Profileemail);
        Profilephone = findViewById(R.id.Profilephone);
        storageReference = FirebaseStorage.getInstance().getReference().child("profileimage");
        button = findViewById(R.id.photobutton);
        button.setVisibility(View.INVISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, gallarypic);

            }
        });

        firebaseFirestore.collection("User").document(Email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Profilename.setText(documentSnapshot.getString("Name"));
                    Profileemail.setText(documentSnapshot.get("Email").toString());
                    Profilephone.setText(documentSnapshot.getString("Phone1"));
                    if (documentSnapshot.getString("img") == null) {
                        Picasso.get().load(R.drawable.download1).into(imageView);
                    } else {
                        Picasso.get().load(documentSnapshot.getString("img")).into(imageView);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialogClass.hide();
                snackbarShow(e);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoad();
                final StorageReference ref = storageReference.child("profileimage" + UUID.randomUUID());
                ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {
                                String img = uri.toString();
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("img", img);
                                firebaseFirestore.collection("User").document(firebaseAuth.getCurrentUser().getEmail()).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(User_Profile_Page.this, "Profile Picture added", Toast.LENGTH_SHORT).show();
                                        button.setVisibility(View.INVISIBLE);
                                        dialogClass.hide();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogClass.hide();
                                button.setVisibility(View.INVISIBLE);
                                snackbarShow(e);
                            }
                        });
                    }
                });
            }
        });


        final BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setSelectedItemId(R.id.profile);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                View view = findViewById(R.id.navigation_home);
                View view1 = findViewById(R.id.profile);
                View view2 = findViewById(R.id.rent);
                View view3 = findViewById(R.id.yourad);

                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent2 = new Intent(User_Profile_Page.this, Home_Page.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent2);
                        break;
                    case R.id.profile:
                        break;
                    case R.id.rent:
                        Intent intent = new Intent(User_Profile_Page.this, Post_Ads_Page.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        break;
                    case R.id.yourad:
                        Intent intent1 = new Intent(User_Profile_Page.this, User_Ads_Page.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent1);
                }
                return true;
            }
        });


        Profileemail.setText(Email);
        Profilephone.setText(Phone);

        Button submit = findViewById(R.id.submit);
        final EditText profienameeditText = findViewById(R.id.profienameeditText);
        final EditText profilephoneeditText = findViewById(R.id.profilephoneeditText);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profienameeditText1 = profienameeditText.getText().toString();
                String profilephoneeditText1 = profilephoneeditText.getText().toString();
                if (profienameeditText1.isEmpty()) {
                    profienameeditText.setError("Field is empty");
                    profienameeditText.requestFocus();
                } else if (profilephoneeditText1.isEmpty()) {
                    profilephoneeditText.setError("Field is empty");
                    profilephoneeditText.requestFocus();
                } else if (!(profilephoneeditText1.length() == 10)) {
                    profilephoneeditText.setError("Please enter correct phone number");
                    profilephoneeditText.requestFocus();
                } else if (!(profienameeditText1.isEmpty() || profilephoneeditText1.isEmpty())) {
                    showLoad();
                    final HashMap<String, Object> hashMap = new HashMap();
                    hashMap.put("Name", profienameeditText1);
                    hashMap.put("Phone1", profilephoneeditText1);
                    firebaseFirestore.collection("User").document(Email).update(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(User_Profile_Page.this, "Updated", Toast.LENGTH_SHORT).show();
                                cardView.setVisibility(View.INVISIBLE);
                                ProfileSection.setVisibility(View.VISIBLE);
                                dialogClass.hide();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            snackbarShow(e);
                            cardView.setVisibility(View.INVISIBLE);
                            ProfileSection.setVisibility(View.VISIBLE);
                            dialogClass.hide();
                        }
                    });
                }
            }
        });

        LinearLayout updateprofile = findViewById(R.id.textView13);
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
                profienameeditText.setEnabled(true);
                ProfileSection.setVisibility(View.INVISIBLE);
                profilephoneeditText.setEnabled(true);

            }
        });
        ImageView cut = findViewById(R.id.cut);
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.INVISIBLE);
                profienameeditText.setEnabled(false);
                ProfileSection.setVisibility(View.VISIBLE);
                profilephoneeditText.setEnabled(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallarypic && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            imageView.setImageURI(imageuri);
            button.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void snackbarShow(Exception e) {
        snackbar.make(ProfileSection, e.getMessage(), Snackbar.LENGTH_LONG)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.White))
                .setDuration(1000)
                .show();
    }

    private void showLoad() {
        dialogClass.show();
        dialogClass.setContentView(R.layout.customedialog);
        dialogClass.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}