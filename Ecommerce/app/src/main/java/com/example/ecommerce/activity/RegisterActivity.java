package com.example.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public
class RegisterActivity extends AppCompatActivity {

    EditText name, email, pass;
    private
    FirebaseAuth auth;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);

    }

    public
    void signUp(View view) {
        String u = name.getText().toString();
        String e = email.getText().toString();
        String p = pass.getText().toString();
        if(u.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Vui lòng nhập tên đăng nhập !",Toast.LENGTH_SHORT).show();
            return;
        }
        if(e.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Vui lòng nhập địa chỉ email !",Toast.LENGTH_SHORT).show();
            return;
        }
        if(p.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Vui lòng nhập mật khẩu !",Toast.LENGTH_SHORT).show();
            return;
        }
        if(p.length()<6){
            Toast.makeText(RegisterActivity.this, "Mật khẩu tối thiểu 6 kí tự !",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public
            void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công !", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký không thành công !", Toast.LENGTH_LONG).show();
                }
            }
        });
        //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    public
    void signIn(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}