package com.prabhat.mainactivity.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.prabhat.mainactivity.Adapter.Home_Adapter;
import com.prabhat.mainactivity.Login_Details.Login_Page;
import com.prabhat.mainactivity.Model.Home_model;
import com.prabhat.mainactivity.R;

import java.util.ArrayList;

public class Home_Page extends AppCompatActivity {
    private RecyclerView recyclerView;
    FirebaseFirestore firestore;
    //    FirestoreRecyclerAdapter adapter;
    FirebaseAuth firebaseAuth;
    Home_Adapter adapter;
    String Email1;

    //String name,price,description;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recyclerView = findViewById(R.id.recyclerView);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        final ArrayList<Home_model> list = new ArrayList();

        adapter = new Home_Adapter(list, this, Ads_fullDescription.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore.collection("Product").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Home_model model = documentChange.getDocument().toObject(Home_model.class);
                    list.add(model);
                    recyclerView.setAdapter(adapter);
//                    recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
//                        @Override
//                        public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
//
//                        }
//                    });
//
//                    lastSender=model.getSender();
//                    if(model.getId().equals(id)){
//
//                        list.add(model);
//
//                    }
                    //        adapter.notifyDataSetChanged();
                }
            }
        });


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
                        Toast.makeText(Home_Page.this, "Refreshed", Toast.LENGTH_SHORT).show();
                        //Settingadapter();
                    }
                }, 4500);
            }

        });

        ///***************////Bottom Navigation bar****************///////////////////
        final BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setSelectedItemId(R.id.navigation_home);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                View view = findViewById(R.id.navigation_home);
                View view1 = findViewById(R.id.profile);
                View view2 = findViewById(R.id.rent);
                View view3 = findViewById(R.id.yourad);

                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.profile:
                        Intent intent3 = new Intent(Home_Page.this, User_Profile_Page.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.rent:
                        Intent intent = new Intent(Home_Page.this, Post_Ads_Page.class);
                        intent.putExtra("Email1", Email1);
                        overridePendingTransition(0,0);
                        startActivity(intent);
                        break;
                    case R.id.yourad:
                        Intent intent1 = new Intent(Home_Page.this, User_Ads_Page.class);
                        overridePendingTransition(0, 0);
                        startActivity(intent1);
                }
                return true;
            }
        });
//
//
//        Query query = firestore.collection("Product");
//
//                FirestoreRecyclerOptions<Home_model> options = new FirestoreRecyclerOptions.Builder<Home_model>()
//                        .setQuery(query, Home_model.class).build();
//
//                adapter = new FirestoreRecyclerAdapter<Home_model, ProductViewHolder>(options) {
//                    @NonNull
//                    @Override
//                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
//                        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
//                        return new ProductViewHolder(view);
//                    }
//
//                    @Override
//                    protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final Home_model model) {
//                        holder.product_name.setText(model.getName());
//                        holder.product_description.setText(model.getDescription());
//                        String colourdecider=model.getStatus();
//                        if("Sell".equals(colourdecider)){
//                            holder.product_price.setText("₹ "+model.getPrice());
//                            holder.status.setTextColor(getResources().getColor(R.color.Blue));
//                        }
//                        else {
//                            holder.product_price.setText("₹ "+model.getPrice()+"/Day");
//                            holder.status.setTextColor(getResources().getColor(R.color.Green));
//                        }
//                        if(model.getStatus().equals("Sell")){
//                            holder.status.setText("Buy");
//                        }
//                        else{
//                            holder.status.setText("Rent");
//                        }
//
//                        Picasso.get().load(model.getImagelink()).into(holder.image);
//
//                        final String name=model.getName();
//                        final String price=model.getPrice();
//                        final String description=model.getDescription();
//                        final String p=model.getImagelink();
//                        final String status=model.getStatus();
//                        final String phone=model.getPhone1();
//                        final String costomer=model.getCustomer();
//                        Log.i("p",p+"");
//                          holder.itemView.setOnClickListener(new View.OnClickListener() {
//                              @Override
//                              public void onClick(View v) {
//
//
//                                  Intent intent1=new Intent(Home_Page.this, Ads_fullDescription.class);
//                                  intent1.putExtra("Phone1",phone);
//                                  intent1.putExtra("Name",name);
//                                  intent1.putExtra("customer",costomer);
//                                  intent1.putExtra("Price",price);
//                                  intent1.putExtra("Description",description);
//                                  intent1.putExtra("image",p);
//                                  intent1.putExtra("email",model.getEmail());
//                                  intent1.putExtra("id",model.getId());
//
//                                  if(model.getStatus().equals("Sell")){
//                                      intent1.putExtra("status","Buy");
//                                  }
//                                  if(model.getStatus().equals("Rent")){
//                                      intent1.putExtra("status","Rent");
//                                  }
//
//                                  intent1.putExtra("Phone1",phone);
//                                  startActivity(intent1);
//                              }
//                          });
//                    }
//                };

//        Settingadapter();
    }
//
//    private void Settingadapter() {
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(adapter);
//    }
//
//    private class ProductViewHolder extends RecyclerView.ViewHolder {
//        private TextView product_name;
//        private TextView product_price;
//        private TextView product_description;
//        private TextView status;
//        ImageView image;
//
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//            product_name = itemView.findViewById(R.id.product_name);
//            product_price = itemView.findViewById(R.id.product_price);
//            product_description = itemView.findViewById(R.id.product_description);
//            status = itemView.findViewById(R.id.status);
//            image = itemView.findViewById(R.id.image);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout: {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.exit)
                        .setMessage("\nAre You Sure ?")
                        .setIcon(getResources().getDrawable(R.drawable.ic_info_black_24dp))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(Home_Page.this, Login_Page.class));
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Home_Page.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            }

            case R.id.profile: {
                  startActivity(new Intent(Home_Page.this, User_Profile_Page.class));
                break;
            }
            case R.id.yourad: {
                startActivity(new Intent(Home_Page.this, User_Ads_Page.class));
                break;
            }
            case R.id.about: {
                startActivity(new Intent(Home_Page.this, About_Page.class));
                break;
            }
            case R.id.send: {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "www.google.com");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.rentandearn)));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
