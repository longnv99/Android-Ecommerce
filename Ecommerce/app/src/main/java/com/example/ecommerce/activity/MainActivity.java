package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ecommerce.R;
import com.example.ecommerce.fragment.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

//Author - Nguyen Van Long - D17PTIT

public
class MainActivity extends AppCompatActivity {
    Fragment fragment;
    Toolbar toolbar;
    FirebaseAuth auth;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_search_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        auth = FirebaseAuth.getInstance();

        fragment = new HomeFragment();
        loadFragment(fragment);
    }

    private
    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, fragment);
        transaction.commit();
    }

    //menu

    @Override
    public
    boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public
    boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.user){
            startActivity(new Intent(MainActivity.this, UserActivity.class));
        }
        else if(id == R.id.cart){
            startActivity(new Intent(MainActivity.this, CartActivity.class));
            //finish();
        }
        else if(id == R.id.logout){
            auth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }
}