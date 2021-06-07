package com.example.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.activity.DetailActivity;
import com.example.ecommerce.model.ShowAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public
class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> implements Filterable {
    private
    Context context;
    private List<ShowAll> listSearch;
    private
    List<ShowAll> list;

    public
    SearchAdapter(Context context, List<ShowAll> listShowAll) {
        this.context = context;
        this.listSearch = listShowAll;
        this.list = listShowAll;
    }

    @NonNull
    @Override
    public
    SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent,false));
    }

    @Override
    public
    void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        Glide.with(context).load(listSearch.get(position).getImg_url()).into(holder.img);
        holder.name.setText(listSearch.get(position).getName());
        holder.price.setText(listSearch.get(position).getPrice()+" đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",listSearch.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public
    int getItemCount() {
        return listSearch.size();
    }


    class SearchHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, price;
        public
        SearchHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_search);
            name = itemView.findViewById(R.id.name_search);
            price = itemView.findViewById(R.id.price_search);
        }
    }

    @Override
    public
    Filter getFilter() {
        return new Filter() {
            @Override
            protected
            FilterResults performFiltering(CharSequence constraint) {
                String strSearch =constraint.toString();
                if(strSearch.isEmpty())
                    listSearch = list;
                else{
                    List<ShowAll> mlist =new ArrayList<>();
                    for(ShowAll d : list){
                        if(d.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            mlist.add(d);
                        }
                    }
                    listSearch = mlist;
                }
                FilterResults rs =new FilterResults();
                rs.values=listSearch;
                return rs;

            }

            @Override
            protected
            void publishResults(CharSequence constraint, FilterResults results) {
                listSearch = (List<ShowAll>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public List<ShowAll> getFilter2(int type){
        switch (type){
            case 1:
                List<ShowAll> mlist =new ArrayList<>();
                for(ShowAll d : list){
                    if(d.getPrice() < 10000000){
                        mlist.add(d);
                    }
                }
                listSearch = mlist;
                break;
            case 2:
                List<ShowAll> mlist1 =new ArrayList<>();
                for(ShowAll d : list){
                    if(d.getPrice() >= 10000000 && d.getPrice() <= 20000000){
                        mlist1.add(d);
                    }
                }
                listSearch = mlist1;
                break;
            case 3:
                List<ShowAll> mlist2 =new ArrayList<>();
                for(ShowAll d : list){
                    if(d.getPrice() > 20000000){
                        mlist2.add(d);
                    }
                }
                listSearch = mlist2;
                break;
        }
        return listSearch;
    }

}
