package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public
class EditUserActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;
    Toolbar toolbar;
    CircleImageView img;
    EditText name, phone, address;
    Button btn;

    Customer c;
    String idCustomer;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        c = new Customer();

        img=findViewById(R.id.img);
        name=findViewById(R.id.ed_user_name);
        phone=findViewById(R.id.ed_user_phone);
        address=findViewById(R.id.ed_user_address);
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
        //get idcustomer
        db.collection("Customer").document(auth.getCurrentUser().getUid())
                .collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                idCustomer = doc.getId();
                                Toast.makeText(EditUserActivity.this,idCustomer,Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

        btn=findViewById(R.id.btnSaveUser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {

                //set data
                c.setCustomerName(name.getText().toString());
                c.setCustomerPhone(phone.getText().toString());
                c.setCustomerAddress(address.getText().toString());

                db.collection("Customer").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(idCustomer)
                        .set(c)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public
                            void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EditUserActivity.this,"Chỉnh sửa thành công",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(EditUserActivity.this,UserActivity.class));
                            }
                        });
            }
        });

    }
    //cấp quyền truy cập
    private
    void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Complete action using"),1);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(EditUserActivity.this, "Từ chối cấp quyền\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        //test request
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Nếu bạn từ chối quyền, bạn không thể sử dụng dịch vụ này\n" +
                        "\n" +
                        "Vui lòng bật quyền tại [Setting]> [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    public
    void selectImg(View view) {
        requestPermission();
    }

    @Override
    protected
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            Uri path = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(path);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}