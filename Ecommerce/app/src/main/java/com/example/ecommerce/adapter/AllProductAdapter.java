package com.example.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.activity.DetailActivity;
import com.example.ecommerce.model.AllProduct;

import java.util.List;

public
class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.AllViewHolder> {
    private
    Context context;
    private
    List<AllProduct> listAll;

    public
    AllProductAdapter(Context context, List<AllProduct> listAll) {
        this.context = context;
        this.listAll = listAll;
    }

    @NonNull
    @Override
    public
    AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_product,parent,false));
    }

    @Override
    public
    void onBindViewHolder(@NonNull AllViewHolder holder, int position) {
        Glide.with(context).load(listAll.get(position).getImg_url()).into(holder.img);
        holder.name.setText(listAll.get(position).getName());
        holder.price.setText(listAll.get(position).getPrice()+" đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",listAll.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public
    int getItemCount() {
        return listAll.size();
    }

    class AllViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, price;
        public
        AllViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_all_product);
            name = itemView.findViewById(R.id.name_all_product);
            price = itemView.findViewById(R.id.price_all_product);
        }
    }
}
