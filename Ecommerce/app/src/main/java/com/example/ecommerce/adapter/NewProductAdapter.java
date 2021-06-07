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
import com.example.ecommerce.model.NewProduct;

import java.util.List;

public
class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.NewProductViewHolder>{

    private
    List<NewProduct> listNew;
    private
    Context context;

    public
    NewProductAdapter(Context context,List<NewProduct> listNew) {
        this.listNew = listNew;
        this.context = context;
    }

    @NonNull
    @Override
    public
    NewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product,parent,false));
    }

    @Override
    public
    void onBindViewHolder(@NonNull NewProductViewHolder holder, int position) {
        Glide.with(context).load(listNew.get(position).getImg_url()).into(holder.img);
        holder.name.setText(listNew.get(position).getName());
        holder.price.setText(listNew.get(position).getPrice()+" đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",listNew.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public
    int getItemCount() {
        return listNew.size();
    }

    class NewProductViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name, price;
        public
        NewProductViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_new_product);
            name = itemView.findViewById(R.id.name_new_product);
            price = itemView.findViewById(R.id.price_new_product);
        }
    }
}
