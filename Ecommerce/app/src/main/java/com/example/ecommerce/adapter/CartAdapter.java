package com.example.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.activity.CartActivity;
import com.example.ecommerce.model.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public
class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private
    Context context;
    private
    List<Cart> list;

    int total = 0;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public
    CartAdapter(Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public
    CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public
    void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getProductImg()).into(holder.img);
        holder.date.setText("Ngày thêm: "+list.get(position).getCurrentDate());
        holder.time.setText("Thời gian: "+list.get(position).getCurrentTime());
        holder.name.setText(list.get(position).getProductName());
        holder.price.setText("Giá: "+list.get(position).getProductPrice());
        holder.quantity.setText("Số lượng: "+list.get(position).getTotalQuantity());
        holder.total.setText("Tổng tiền: "+list.get(position).getTotalPrice()+" đ");

        total = total + list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",total);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                db.collection("AddToCart")
                        .document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(list.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public
                            void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    list.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context,"Xóa thành công",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
    }

    @Override
    public
    int getItemCount() {
        return list.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        TextView date,time,name,price,quantity,total;
        ImageView img;
        Button btnXoa;
        public
        CartViewHolder(@NonNull View itemView) {
            super(itemView);

            btnXoa = itemView.findViewById(R.id.btnXoa);
            img = itemView.findViewById(R.id.cart_item_img);
            date = itemView.findViewById(R.id.cart_item_date);
            time = itemView.findViewById(R.id.cart_item_time);
            name = itemView.findViewById(R.id.cart_item_name);
            price = itemView.findViewById(R.id.cart_item_price);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
            total = itemView.findViewById(R.id.cart_item_total);
        }
    }
}
