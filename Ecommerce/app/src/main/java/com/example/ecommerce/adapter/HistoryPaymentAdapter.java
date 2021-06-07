package com.example.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Payment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public
class HistoryPaymentAdapter extends RecyclerView.Adapter<HistoryPaymentAdapter.HistoryHolder>{
    List<Payment> list;
    Context context;

    FirebaseFirestore db;
    FirebaseAuth auth;
    public
    HistoryPaymentAdapter(List<Payment> list, Context context) {
        this.list = list;
        this.context = context;
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public
    HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_payment_item,parent,false));
    }

    @Override
    public
    void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        holder.name.setText(list.get(position).getNameProduct());
        holder.time.setText("Thời gian: "+list.get(position).getTime());
        holder.type.setText("Hình thức thanh toán: "+list.get(position).getPaymentType());
        holder.total.setText("Số tiền: "+list.get(position).getTotalAmount());
    }

    @Override
    public
    int getItemCount() {
        return list.size();
    }

    class HistoryHolder extends RecyclerView.ViewHolder {
        TextView time, type, name, total;
        public
        HistoryHolder(@NonNull View itemView) {
            super(itemView);

            time=itemView.findViewById(R.id.time_p);
            type=itemView.findViewById(R.id.type_p);
            name=itemView.findViewById(R.id.name_p);
            total=itemView.findViewById(R.id.total_p);
        }
    }
}
