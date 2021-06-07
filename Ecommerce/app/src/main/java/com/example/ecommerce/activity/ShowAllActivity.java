package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.ShowAllAdapter;
import com.example.ecommerce.model.ShowAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public
class ShowAllActivity extends AppCompatActivity {

    private
    Toolbar toolbar;
    RecyclerView recyclerView;
    ShowAllAdapter adapter;
    List<ShowAll> list;

    FirebaseFirestore db;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        toolbar=findViewById(R.id.showall_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                finish();
            }
        });


        String type = getIntent().getStringExtra("type");

        db = FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.rev_show);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        list = new ArrayList<>();
        adapter = new ShowAllAdapter(this, list);
        recyclerView.setAdapter(adapter);



        if(type ==null){
            db.collection("ShowAll").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public
                void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (DocumentSnapshot doc :task.getResult().getDocuments()){
                            ShowAll showAll = doc.toObject(ShowAll.class);
                            list.add(showAll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //apple
        if(type !=null && type.equalsIgnoreCase("apple")){
            db.collection("ShowAll").whereEqualTo("type", "apple")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public
                void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (DocumentSnapshot doc :task.getResult().getDocuments()){
                            ShowAll showAll = doc.toObject(ShowAll.class);
                            list.add(showAll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //samsung
        if(type !=null && type.equalsIgnoreCase("samsung")){
            db.collection("ShowAll").whereEqualTo("type", "samsung")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public
                void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (DocumentSnapshot doc :task.getResult().getDocuments()){
                            ShowAll showAll = doc.toObject(ShowAll.class);
                            list.add(showAll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //oppo
        if(type !=null && type.equalsIgnoreCase("oppo")){
            db.collection("ShowAll").whereEqualTo("type", "oppo")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public
                void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (DocumentSnapshot doc :task.getResult().getDocuments()){
                            ShowAll showAll = doc.toObject(ShowAll.class);
                            list.add(showAll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }


        //lenovo
        if(type !=null && type.equalsIgnoreCase("lenovo")){
            db.collection("ShowAll").whereEqualTo("type", "lenovo")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public
                void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (DocumentSnapshot doc :task.getResult().getDocuments()){
                            ShowAll showAll = doc.toObject(ShowAll.class);
                            list.add(showAll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //xiaomi
        if(type !=null && type.equalsIgnoreCase("xiaomi")){
            db.collection("ShowAll").whereEqualTo("type", "xiaomi")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public
                void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (DocumentSnapshot doc :task.getResult().getDocuments()){
                            ShowAll showAll = doc.toObject(ShowAll.class);
                            list.add(showAll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //sony
        if(type !=null && type.equalsIgnoreCase("sony")){
            db.collection("ShowAll").whereEqualTo("type", "sony")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public
                void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (DocumentSnapshot doc :task.getResult().getDocuments()){
                            ShowAll showAll = doc.toObject(ShowAll.class);
                            list.add(showAll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //huawei
        if(type !=null && type.equalsIgnoreCase("huawei")){
            db.collection("ShowAll").whereEqualTo("type", "huawei")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public
                void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (DocumentSnapshot doc :task.getResult().getDocuments()){
                            ShowAll showAll = doc.toObject(ShowAll.class);
                            list.add(showAll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //vivo
        if(type !=null && type.equalsIgnoreCase("vivo")){
            db.collection("ShowAll").whereEqualTo("type", "vivo")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public
                void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (DocumentSnapshot doc :task.getResult().getDocuments()){
                            ShowAll showAll = doc.toObject(ShowAll.class);
                            list.add(showAll);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

    }
}