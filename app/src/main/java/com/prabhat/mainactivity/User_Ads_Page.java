package com.prabhat.mainactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.mainactivity.Adapter.User_Ads_Adapter;
import com.prabhat.mainactivity.Model.Home_model;

import java.util.ArrayList;
import java.util.List;

public class User_Ads_Page extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    //    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    // String modelEmail;
    User_Ads_Adapter adapter;
    List<Home_model> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_ads);

        recyclerView = findViewById(R.id.recyclerView);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        String emailid = firebaseAuth.getCurrentUser().getEmail();

        adapter = new User_Ads_Adapter(list, this, Ads_Update_Section.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(User_Ads_Page.this, "Refreshed", Toast.LENGTH_SHORT).show();
//                        Settingadapter();
                    }
                }, 4500);

            }

        });

        firebaseFirestore.collection(emailid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Home_model model = documentChange.getDocument().toObject(Home_model.class);
                    list.add(model);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        final BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setSelectedItemId(R.id.yourad);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                View view = findViewById(R.id.navigation_home);
                View view1 = findViewById(R.id.profile);
                View view2 = findViewById(R.id.rent);
                View view3 = findViewById(R.id.yourad);

                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent2 = new Intent(User_Ads_Page.this, Home_Page.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent2);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(User_Ads_Page.this, User_Profile_Page.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.rent:
                        Intent intent = new Intent(User_Ads_Page.this, Post_Ads_Page.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        break;
                    case R.id.yourad:
                }
                return true;
            }
        });

//
//        Query query=firebaseFirestore.collection(emailid);
//        FirestoreRecyclerOptions<Home_model> options=new FirestoreRecyclerOptions.Builder<Home_model>()
//                .setQuery(query, Home_model.class).build();
//        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Home_model, ProductViewHolder>(options) {
//            @NonNull
//            @Override
//            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youritemsingleview, parent, false);
//                    return new ProductViewHolder(view);
//
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Home_model model) {
//                String emailid=firebaseAuth.getCurrentUser().getEmail();
//                Log.i("email",emailid+"");
//                Log.i("email2",model.getEmail()+"");
//                Log.i("name",model.getName()+"");
//                modelEmail=model.getEmail();
//                if(emailid.equals(model.getEmail())) {
//                    holder.productname.setText(model.getName());
//                    Log.i("name", model.getName() + "");
//                    holder.status.setText(model.getStatus());
//                    holder.productprice.setText("Rs. "+model.getPrice());
//                    holder.productdescription.setText(model.getDescription());
//                    Picasso.get().load(model.getImagelink()).into(holder.image);
//
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent=new Intent(User_Ads_Page.this, Ads_Update_Section.class);
//                            intent.putExtra("productname",model.getName());
//                            intent.putExtra("productprice",model.getPrice());
//                            intent.putExtra("productdecscription",model.getDescription());
//                            intent.putExtra("image",model.getImagelink());
//                            intent.putExtra("status",model.getStatus());
//                            intent.putExtra("id",model.getId());
//                            startActivity(intent);
//
//                        }
//                    });
//                }
//
//            }
//        };
//        Settingadapter();
    }

//    private void Settingadapter() {
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(firestoreRecyclerAdapter);
//    }

//    private class ProductViewHolder extends RecyclerView.ViewHolder {
//        TextView productprice,productname,productdescription,status;
//        ImageView image;
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//            productname=itemView.findViewById(R.id.product_name);
//            productprice=itemView.findViewById(R.id.product_price);
//            productdescription=itemView.findViewById(R.id.product_description);
//            status=itemView.findViewById(R.id.status);
//            image=itemView.findViewById(R.id.image);
//
//        }


//    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        firestoreRecyclerAdapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        firestoreRecyclerAdapter.stopListening();
//    }
//

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
