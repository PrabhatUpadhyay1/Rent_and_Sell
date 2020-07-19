package com.prabhat.mainactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class Post_Ads_Page extends AppCompatActivity {
    private static final int gallaypick = 1;
    ImageView imageViewinput;
    private Uri imageuri;
    private FirebaseFirestore firebaseFirestore;
    String Email1;
    Button submit;
    private int gallary_pic = 1;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    EditText productname, productdescription, productprice;
    String productname1, productdescription1, productprice1;
    Switch aSwitch;
    Snackbar snackbar;
    String status = null;
    ProgressDialog dialogClass;
    ConstraintLayout PostAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_ads);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        Intent intent = getIntent();
        Email1 = intent.getStringExtra("Email1");
        dialogClass = new ProgressDialog(this);
        //    Log.i("TAG","hello"+Email1);
        productname = (EditText) findViewById(R.id.textView9);
        productprice = (EditText) findViewById(R.id.textView3);
        productdescription = (EditText) findViewById(R.id.textView10);
        submit = (Button) findViewById(R.id.bottom3);
        imageViewinput = (ImageView) findViewById(R.id.imageView7);
        aSwitch = findViewById(R.id.switch1);
        PostAd = findViewById(R.id.PstAd);
        final TextView textView8 = findViewById(R.id.textView8);
        final TextView textView5 = findViewById(R.id.textView5);
        final TextView com = findViewById(R.id.com);


        aSwitch.setChecked(false);
        status = "Rent";
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    aSwitch.setText("Sell");
                    status = "Sell";
                    textView8.setText("Enter Selling price");
                    textView5.setText("Slide switch to Rent");
                } else {
                    status = "Rent";
                    aSwitch.setText("Rent");
                    textView8.setText("Enter Renting price");
                    textView5.setText("Slide switch to Sell");
                }
            }
        });


        imageViewinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, gallary_pic);
            }
        });

        final BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setSelectedItemId(R.id.rent);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                View view = findViewById(R.id.navigation_home);
                View view1 = findViewById(R.id.profile);
                View view2 = findViewById(R.id.rent);
                View view3 = findViewById(R.id.yourad);

                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(Post_Ads_Page.this, Home_Page.class);
                        intent.putExtra("Email1", Email1);
                        overridePendingTransition(1, 0);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        Intent intent2 = new Intent(Post_Ads_Page.this, User_Profile_Page.class);
                        intent2.putExtra("Email1", Email1);
                        overridePendingTransition(1, 0);
                        startActivity(intent2);
                        break;
                    case R.id.rent:
                        break;
                    case R.id.yourad:
                        Intent intent1 = new Intent(Post_Ads_Page.this, User_Ads_Page.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent1);
                }
                return true;
            }
        });
//

        storageReference = FirebaseStorage.getInstance().getReference().child("Image");

        imageViewinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, gallaypick);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productname1 = productname.getText().toString().trim();
                productdescription1 = productdescription.getText().toString().trim();
                productprice1 = productprice.getText().toString().trim();
                if (productname1.isEmpty()) {
                    productname.requestFocus();
                    productname.setError("Product name is empty");
                } else if (productprice1.isEmpty()) {
                    productname.requestFocus();
                    productprice.setError("Product price is empty");
                } else if (productdescription1.isEmpty()) {
                    productname.requestFocus();
                    productdescription.setError("Description is empty");
                } else if (imageuri == null) {
                    Toast.makeText(Post_Ads_Page.this, "Image is compulsory", Toast.LENGTH_SHORT).show();
                    imageViewinput.requestFocus();
                    com.setVisibility(View.VISIBLE);
                } else if (!(productname1.isEmpty() && productprice1.isEmpty() && productdescription1.isEmpty() && imageViewinput != null)) {
                    if (imageuri != null) {
                        showLoad();

                        final StorageReference ref = storageReference.child("Image" + UUID.randomUUID());
                        ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(final Uri uri) {
                                        final String id = UUID.randomUUID().toString();
                                        final String profileEmail = firebaseAuth.getCurrentUser().getEmail();
                                        firebaseFirestore.collection("User").document(profileEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                String imagelinl = uri.toString();
                                                Log.i("imge", imagelinl);
                                                final HashMap<String, Object> hashMap = new HashMap<>();

                                                hashMap.put("id", id);
                                                hashMap.put("customer", documentSnapshot.get("Name"));
                                                hashMap.put("Phone1", documentSnapshot.get("Phone1"));
                                                hashMap.put("name", productname1);
                                                hashMap.put("description", productdescription1);
                                                hashMap.put("price", productprice1);
                                                hashMap.put("imagelink", imagelinl);
                                                hashMap.put("status", status);
                                                hashMap.put("Email", profileEmail);


                                                firebaseFirestore.collection(profileEmail).document(id).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        firebaseFirestore.collection("Product").document(id).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                                dialogClass.hide();
                                                                Toast.makeText(Post_Ads_Page.this, "Product Added", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(Post_Ads_Page.this, Home_Page.class));
                                                                finish();
                                                                String X = " Congratulations! Your ad is Live now";
                                                                notification(X);

                                                            }
                                                        });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        snackbarShow(e);
                                                        Toast.makeText(Post_Ads_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        String X = "Something went Wrong";
                                                        notification(X);
                                                    }
                                                });

                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                }
            }

            ;

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallary_pic && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            imageViewinput.setImageURI(imageuri);

        }
    }

    public void notification(String X) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Congratulation !")
                .setColor(getResources()
                        .getColor(R.color.colorPrimary))
                .setContentText(X)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_insert_emoticon_black_24dp);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    private void snackbarShow(Exception e) {

        snackbar.make(PostAd, e.getMessage(), Snackbar.LENGTH_LONG)
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