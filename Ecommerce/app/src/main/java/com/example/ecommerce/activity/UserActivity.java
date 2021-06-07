package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.adapter.HistoryPaymentAdapter;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Payment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

import static gun0912.tedbottompicker.TedBottomPicker.*;

public
class UserActivity extends AppCompatActivity {

    List<Payment> list;
    HistoryPaymentAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth auth;
    Toolbar toolbar;
    CircleImageView img;
    TextView user;
    RecyclerView recyclerView;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        img=findViewById(R.id.img);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                finish();
            }
        });

        //lấy thông tin customerName
        user=findViewById(R.id.username);
        db.collection("Customer").document(auth.getCurrentUser().getUid())
                .collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()){
                            Customer c = doc.toObject(Customer.class);
                            user.setText(c.getCustomerName());
                        }
                    }
                });

        //lấy thông tin giao dich
        recyclerView = findViewById(R.id.rev_history_payment);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryPaymentAdapter(list, this);
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        db.collection("Payment").document(auth.getCurrentUser().getUid())
                .collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc : task.getResult().getDocuments()){
                                String documentId = doc.getId();
                                Payment p = doc.toObject(Payment.class);
                                p.setDocumentId(documentId);
                                list.add(p);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        //xoa
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public
            boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public
            void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                db.collection("Payment").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(list.get(position).getDocumentId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<Void> task) {
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(UserActivity.this,"Đã xóa lịch sử",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public
    void editUser(View view) {
        startActivity(new Intent(UserActivity.this, EditUserActivity.class));
    }
}