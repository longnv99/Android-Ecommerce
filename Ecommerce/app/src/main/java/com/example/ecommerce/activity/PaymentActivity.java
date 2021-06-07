package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public
class PaymentActivity extends AppCompatActivity {

    RadioButton rd_card, rd_cash;
    EditText edMessage;
    TextView total, ship, totalAll, totalAll2, txtShipment, txtAddress;
    Button btnPay;
    Toolbar toolbar;

    FirebaseAuth auth;
    FirebaseFirestore db;

    List<Cart> list;

    String paymentType;
    int totalAmount, shipTotal, all;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        rd_card=findViewById(R.id.rd_card);
        rd_cash=findViewById(R.id.rd_cash);

        edMessage =findViewById(R.id.edit_message);
        btnPay =findViewById(R.id.pay_pm);

        txtShipment=findViewById(R.id.txt_shipment);
        total=findViewById(R.id.txt_total);
        ship=findViewById(R.id.txt_ship);
        totalAll=findViewById(R.id.txt_alltotal);
        totalAll2=findViewById(R.id.txt_total_all);
        txtAddress =findViewById(R.id.txt_address);
        //get info customer
        db.collection("Customer").document(auth.getCurrentUser().getUid())
                .collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                Customer c = doc.toObject(Customer.class);
                                txtAddress.setText(c.getCustomerName()+"\n"+c.getCustomerPhone()+"\n"+c.getCustomerAddress());
                            }
                        }
                    }
                });

        list = new ArrayList<>();
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
                            }
                        }
                    }
                });

        toolbar =findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                finish();
            }
        });

        totalAmount = getIntent().getIntExtra("tt2",0);
        total.setText(totalAmount+" đ");

        shipTotal = getIntent().getIntExtra("ship",0);
        ship.setText(shipTotal+" đ");

        if(shipTotal == 32000){
            txtShipment.setText("BEST Express");
        }
        else if(shipTotal == 35000){
            txtShipment.setText("Giao hàng tiết kiệm");
        }
        else if(shipTotal == 33000){
            txtShipment.setText("J&T Express");
        }

        all = shipTotal + totalAmount;
        totalAll.setText(all+" đ");
        totalAll2.setText(all+" đ");


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                //payment type
                if(rd_card.isChecked())
                    paymentType = rd_card.getText().toString();
                else if(rd_cash.isChecked())
                    paymentType = rd_cash.getText().toString();

                //nameproduct
                String nproduct = "Sản phẩm : ";
                for(int i=0; i<list.size(); i++){
                    nproduct += list.get(i).getProductName()+", ";
                }

                String saveCurrentTime, saveCurrentDate;
                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                saveCurrentDate = date.format(calendar.getTime());

                SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                saveCurrentTime = time.format(calendar.getTime());

                final HashMap<String,Object> payMap = new HashMap<>();

                payMap.put("nameProduct", nproduct);
                payMap.put("transport", txtShipment.getText().toString());
                payMap.put("message", edMessage.getText().toString());
                payMap.put("paymentType", paymentType+"");
                payMap.put("totalAmount", all);
                payMap.put("time",saveCurrentTime+" "+saveCurrentDate);

                db.collection("Payment").document(auth.getCurrentUser().getUid())
                        .collection("User").add(payMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<DocumentReference> task) {
                        for(int i=0; i<list.size();i++){
                            db.collection("AddToCart")
                                    .document(auth.getCurrentUser().getUid())
                                    .collection("User")
                                    .document(list.get(i).getDocumentId())
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public
                                        void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(PaymentActivity.this, "Đặt hàng thành công",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        startActivity(new Intent(PaymentActivity.this,MainActivity.class));

                    }
                });
            }
        });
    }
}