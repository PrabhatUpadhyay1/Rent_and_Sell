package com.prabhat.mainactivity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.mainactivity.Model.Home_model;
import com.prabhat.mainactivity.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.MyViewHolder> {

    List<Home_model> list;
    Context context;
    Class aClass;

    public Home_Adapter(List<Home_model> list, Context context, Class aClass) {
        this.list = list;
        this.context = context;
        this.aClass = aClass;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Home_model model = new Home_model();

        holder.product_name.setText(list.get(position).getName());
        holder.product_description.setText(list.get(position).getDescription());
        String colourdecider = list.get(position).getStatus();

        if ("Sell".equals(colourdecider)) {
            holder.product_price.setText("₹ " + list.get(position).getPrice());
//            holder.status.setTextColor(R.color.Blue);
        } else {
            holder.product_price.setText("₹ " + list.get(position).getPrice() + "/Day");
         //   holder.status.setTextColor(R.color.Green);
        }
        if (list.get(position).getStatus().equals("Sell")) {
            holder.status.setText("Buy");
        } else {
            holder.status.setText("Rent");
        }
        Picasso.get().load(list.get(position).getImagelink()).into(holder.image);

        final String name = list.get(position).getName();
        final String price = list.get(position).getPrice();
        final String description = list.get(position).getDescription();
        final String p = list.get(position).getImagelink();
        final String status = list.get(position).getStatus();
        final String phone = list.get(position).getPhone1();
        final String costomer = list.get(position).getCustomer();

        final String email=list.get(position).getEmail();
        final String id=list.get(position).getId();
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.product_name.getContext(), aClass);
                intent.putExtra("Phone1", phone);
                intent.putExtra("Name", name);
                intent.putExtra("customer", costomer);
                intent.putExtra("Price", price);
                intent.putExtra("Description", description);
                intent.putExtra("image", p);
                intent.putExtra("email", email);
                intent.putExtra("id", id);

                if (list.get(position).getStatus().equals("Sell")) {
                    intent.putExtra("status", "Buy");
                }
                if (list.get(position).getStatus().equals("Rent")) {
                    intent.putExtra("status", "Rent");
                }

                intent.putExtra("Phone1", phone);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView product_name;
        private TextView product_price;
        private TextView product_description;
        private TextView status;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_description = itemView.findViewById(R.id.product_description);
            status = itemView.findViewById(R.id.status);
            image = itemView.findViewById(R.id.Profileimage);
        }
    }
}
