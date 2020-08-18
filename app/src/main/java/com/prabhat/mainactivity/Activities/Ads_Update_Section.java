package com.prabhat.mainactivity.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prabhat.mainactivity.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

public class Ads_Update_Section extends AppCompatActivity {
    EditText editText9, editText10, editText3, switch1;
    ImageView imageView7;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog dialog;
    StorageReference storageReference;
    Uri imageUri;
    String Name, Desc, Price, status, image;
    AlertDialog.Builder builder;
    String imagelinl;
    Snackbar snackbar;
    RelativeLayout UserUpdate;
    String id;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_update_section);
        firebaseFirestore = FirebaseFirestore.getInstance();
        editText3 = findViewById(R.id.textView3);
        editText9 = findViewById(R.id.textView9);
        editText10 = findViewById(R.id.textView10);
        imageView7 = findViewById(R.id.imageView7);
        dialog = new ProgressDialog(Ads_Update_Section.this);
        switch1 = findViewById(R.id.switch1);
        final Intent intent = getIntent();
        UserUpdate = findViewById(R.id.UserUpdate);
        storageReference = FirebaseStorage.getInstance().getReference().child("updateImage");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Name = intent.getStringExtra("Name");
        Price = intent.getStringExtra("Price");
        Desc = intent.getStringExtra("Description");
        image = intent.getStringExtra("image");
        status = intent.getStringExtra("status");
        id = intent.getStringExtra("id");
        Log.i("id", id + "");

        builder = new AlertDialog.Builder(this);


        editText9.setText(Name);
        editText9.setCursorVisible(false);
        editText10.setText(Desc);
        editText3.setText(Price);
        switch1.setText(status);
        Picasso.get().load(image).into(imageView7);


        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        storageReference = FirebaseStorage.getInstance().getReference().child("Image");

        final Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoad();
                if (imageUri != null) {
                    final StorageReference reference = storageReference.child("Image" + UUID.randomUUID());
                    reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imagelinl = uri.toString();
                                    update(imagelinl);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.hide();
                                    snackbarShow(e);

                                }
                            });
                        }
                    });
                } else {
                    update(image);
                }
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder
                        .setTitle(R.string.delete)
                        .setMessage("\nAre You Sure ?")
                        .setIcon(getResources().getDrawable(R.drawable.ic_info_black_24dp))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                showLoad();
                                firebaseFirestore.collection("Product").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        firebaseFirestore.collection(firebaseAuth.getCurrentUser().getEmail()).document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                startActivity(new Intent(Ads_Update_Section.this, User_Ads_Page.class));
                                                finish();
                                                Ads_Update_Section.this.dialog.hide();
                                                Toast.makeText(Ads_Update_Section.this,"Deleted successfully", Toast.LENGTH_SHORT).show();
                                                String X = "Your Ad is Deleted";
                                                createChannel();
                                                notification(X);
                                            }
                                        });
                                    }
                                });
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Ads_Update_Section.this.dialog.hide();
                                Toast.makeText(Ads_Update_Section.this, "Task cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

    }

    private void update(String str) {
        String name = editText9.getText().toString();
        String price = editText3.getText().toString();
        final String description = editText10.getText().toString();
        String status = switch1.getText().toString();
//        status = status.substring(0, 1).toUpperCase() + status.substring(1, status.length());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("price", price);
        hashMap.put("imagelink", str);
        hashMap.put("description", description);
        hashMap.put("status", status);
        firebaseFirestore.collection("Product").document(id).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Ads_Update_Section.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Ads_Update_Section.this, User_Ads_Page.class));
                finish();
                String X = "Your Ad has been Updated";
                createChannel();
                notification(X);
                dialog.hide();
            }
        });
        firebaseFirestore.collection(firebaseAuth.getCurrentUser().getEmail()).document(id).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.hide();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.hide();
                snackbarShow(e);
            }
        });
    }

    public void notification(String X) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Congratulation!")
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentText(X)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView7.setImageURI(imageUri);

        }
    }

    private void snackbarShow(Exception e) {
        Snackbar.make(UserUpdate, e.getMessage(), Snackbar.LENGTH_LONG)
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
        dialog.show();
        dialog.setContentView(R.layout.customedialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("prabhat.mainactivity.Firebase_Messaging_Services.test", "chanel 1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("");
            channel.enableLights(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
