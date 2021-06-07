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
import com.example.ecommerce.activity.ShowAllActivity;
import com.example.ecommerce.model.Category;

import java.util.List;

public
class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private
    Context context;
    private
    List<Category> list;

    public
    CategoryAdapter(Context context, List<Category> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public
    ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list,parent, false));
    }

    @Override
    public
    void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.itemImg);
        holder.itemname.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent(context, ShowAllActivity.class);
                intent.putExtra("type", list.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public
    int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImg;
        TextView itemname;
        public
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.item_img);
            itemname =itemView.findViewById(R.id.item_name);
        }
    }
}
