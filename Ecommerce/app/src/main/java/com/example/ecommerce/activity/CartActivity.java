package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.CartAdapter;
import com.example.ecommerce.model.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public
class CartActivity extends AppCompatActivity {
    int totalBill = 0;
    TextView totalAmount;
    Toolbar toolbar;
    RecyclerView recyclerView;
    CartAdapter adapter;
    List<Cart> list;
    Button btnPay;
    private FirebaseFirestore db;
    private
    FirebaseAuth auth;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.cart_toolbar);
        totalAmount = findViewById(R.id.cart_price);
        btnPay = findViewById(R.id.payment_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                finish();
            }
        });

        //total amount
        //get data tu cartadapter
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceive, new IntentFilter("MyTotalAmount"));

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.rev_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new CartAdapter(this, list);
        recyclerView.setAdapter(adapter);

        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                String documentId = doc.getId();
                                Cart c = doc.toObject(Cart.class);
                                c.setDocumentId(documentId);
                                list.add(c);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent i = new Intent(CartActivity.this, ShipmentActivity.class);
                i.putExtra("allPrice",totalBill);
                startActivity(i);
            }
        });

    }

    public
    BroadcastReceiver messageReceive = new BroadcastReceiver() {
        @Override
        public
        void onReceive(Context context, Intent intent) {
            totalBill = intent.getIntExtra("totalAmount",0);
            totalAmount.setText(totalBill+" đ");
        }
    };
}