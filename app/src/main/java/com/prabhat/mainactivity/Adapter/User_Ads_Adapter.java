//package com.prabhat.mainactivity.Adapter;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.prabhat.mainactivity.Model.Home_model;
//import com.prabhat.mainactivity.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//public class User_Ads_Adapter extends RecyclerView.Adapter<User_Ads_Adapter.MyViewHolder> {
//
//    List<Home_model> list;
//    Context context;
//    Class aClass;
//
//    public User_Ads_Adapter(List<Home_model> list, Context context, Class aClass) {
//        this.list = list;
//        this.context = context;
//        this.aClass = aClass;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_single_ad_item, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @SuppressLint("ResourceAsColor")
//    @Override
//    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//
//        final Home_model model = new Home_model();
//
//        holder.product_name.setText(list.get(position).getName());
//        holder.product_description.setText(list.get(position).getDescription());
//        Picasso.get().load(list.get(position).getImagelink()).into(holder.image);
//
//        final String name = list.get(position).getName();
//        final String price = list.get(position).getPrice();
//        final String description = list.get(position).getDescription();
//        final String p = list.get(position).getImagelink();
//        final String status = list.get(position).getStatus();
//        final String phone = list.get(position).getPhone1();
//        final String costomer = list.get(position).getCustomer();
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(holder.product_name.getContext(), aClass);
//                intent.putExtra("Phone1", phone);
//                intent.putExtra("Name", name);
//                intent.putExtra("customer", costomer);
//                intent.putExtra("Price", price);
//                intent.putExtra("Description", description);
//                intent.putExtra("image", p);
//                intent.putExtra("email", model.getEmail());
//                intent.putExtra("id", model.getId());
//
//                if (list.get(position).getStatus().equals("Sell")) {
//                    intent.putExtra("status", "Buy");
//                }
//                if (list.get(position).getStatus().equals("Rent")) {
//                    intent.putExtra("status", "Rent");
//                }
//
//                intent.putExtra("Phone1", phone);
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        private TextView product_name;
//        private TextView product_description;
//        ImageView image;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            product_name = itemView.findViewById(R.id.product_name);
//            product_description = itemView.findViewById(R.id.product_description);
//            image = itemView.findViewById(R.id.Profileimage);
//        }
//    }
//}
