package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.SearchAdapter;
import com.example.ecommerce.model.AllProduct;
import com.example.ecommerce.model.ShowAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public
class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    Toolbar toolbar;
    FirebaseFirestore db;
    List<ShowAll> list;
    SearchAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        searchView=findViewById(R.id.searchview);
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        db.collection("ShowAll")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                ShowAll s = doc.toObject(ShowAll.class);
                                list.add(s);
                            }
                        }
                    }
                });
        adapter = new SearchAdapter(this, list);
        recyclerView = findViewById(R.id.rev_search);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public
            boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public
            boolean onQueryTextChange(String newText) {
                String str = newText;
                adapter.getFilter().filter(str);
                return false;
            }
        });
    }

    public
    void getLess(View view) {
        adapter.getFilter2(1);
        adapter.notifyDataSetChanged();
    }

    public
    void getBettwen(View view) {
        adapter.getFilter2(2);
        adapter.notifyDataSetChanged();
    }

    public
    void getMore(View view) {
        adapter.getFilter2(3);
        adapter.notifyDataSetChanged();
    }
}