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
import com.example.ecommerce.model.ShowAll;

import java.util.List;

public
class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ShowViewHolder>{
    private
    Context context;
    private List<ShowAll> listShowAll;

    public
    ShowAllAdapter(Context context, List<ShowAll> listShowAll) {
        this.context = context;
        this.listShowAll = listShowAll;
    }

    @NonNull
    @Override
    public
    ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_item, parent,false));
    }

    @Override
    public
    void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        Glide.with(context).load(listShowAll.get(position).getImg_url()).into(holder.img);
        holder.name.setText(listShowAll.get(position).getName());
        holder.price.setText(listShowAll.get(position).getPrice()+" đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",listShowAll.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public
    int getItemCount() {
        return listShowAll.size();
    }

    class ShowViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, price;
        public
        ShowViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_show);
            name = itemView.findViewById(R.id.name_show);
            price = itemView.findViewById(R.id.price_show);
        }
    }
}
