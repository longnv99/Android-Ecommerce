package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.model.AllProduct;
import com.example.ecommerce.model.NewProduct;
import com.example.ecommerce.model.ShowAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public
class DetailActivity extends AppCompatActivity {
    ImageView img, addItem, removeItem;
    TextView name, price, description, rating, quantity;
    Button btnAddCart;
    RatingBar ratingBar;

    private
    Toolbar toolbar;

    private FirebaseAuth auth;
    private
    FirebaseFirestore db;

    NewProduct newProduct = null;
    AllProduct allProduct = null;
    ShowAll showProduct = null;

    int totalQuantity = 1;
    int totalPrice = 0;
    String img_url;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ratingBar =findViewById(R.id.ratingbar);
        img=findViewById(R.id.img);
        name=findViewById(R.id.txtName);
        price=findViewById(R.id.txtPrice);
        description=findViewById(R.id.txtDes);
        quantity=findViewById(R.id.quantity);
        rating=findViewById(R.id.star);
        btnAddCart=findViewById(R.id.btnAddCart);
        addItem=findViewById(R.id.add);
        removeItem=findViewById(R.id.minus);

        toolbar =findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Object obj = getIntent().getSerializableExtra("detail");

        if(obj instanceof NewProduct){
            newProduct = (NewProduct) obj;
        }
        else if(obj instanceof AllProduct){
            allProduct = (AllProduct) obj;
        }
        else if(obj instanceof ShowAll){
            showProduct = (ShowAll) obj;
        }

        //new product
        if(newProduct != null){
            Glide.with(getApplicationContext()).load(newProduct.getImg_url()).into(img);
            name.setText(newProduct.getName());
            price.setText(newProduct.getPrice()+" đ");
            rating.setText(newProduct.getRating());
            ratingBar.setRating(Float.parseFloat(newProduct.getRating()));
            description.setText(newProduct.getDescription());

            totalPrice = newProduct.getPrice() * totalQuantity;
            img_url = newProduct.getImg_url();
        }

        //all
        if(allProduct != null){
            Glide.with(getApplicationContext()).load(allProduct.getImg_url()).into(img);
            name.setText(allProduct.getName());
            price.setText(allProduct.getPrice()+" đ");
            rating.setText(allProduct.getRating());
            ratingBar.setRating(Float.parseFloat(allProduct.getRating()));
            description.setText(allProduct.getDescription());

            totalPrice = allProduct.getPrice() * totalQuantity;
            img_url = allProduct.getImg_url();
        }

        //show all
        if(showProduct != null){
            Glide.with(getApplicationContext()).load(showProduct.getImg_url()).into(img);
            name.setText(showProduct.getName());
            price.setText(showProduct.getPrice()+" đ");
            rating.setText(showProduct.getRating());
            ratingBar.setRating(Float.parseFloat(showProduct.getRating()));
            description.setText(showProduct.getDescription());

            totalPrice = showProduct.getPrice() * totalQuantity;
            img_url = showProduct.getImg_url();
        }

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                add();
            }

        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                if(totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(totalQuantity+"");

                    if(newProduct != null){
                        totalPrice = newProduct.getPrice() * totalQuantity;
                    }
                    if(allProduct != null){
                        totalPrice = allProduct.getPrice() * totalQuantity;
                    }
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(totalQuantity+"");
                }
            }
        });
    }

    private
    void add() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate = date.format(calendar.getTime());

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = time.format(calendar.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productImg", img_url);
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User")
                .add(cartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}