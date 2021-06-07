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
class LoginActivity extends AppCompatActivity {

    EditText email, pass;
    private
    FirebaseAuth auth;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
    }

    public
    void signIn(View view) {
        String e = email.getText().toString();
        String p = pass.getText().toString();
        if(e.isEmpty()){
            Toast.makeText(LoginActivity.this, "Vui lòng nhập email đăng nhập !",Toast.LENGTH_SHORT).show();
            return;
        }
        if(p.isEmpty()){
            Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu !",Toast.LENGTH_SHORT).show();
            return;
        }
        if(p.length()<6){
            Toast.makeText(LoginActivity.this, "Mật khẩu tối thiểu 6 kí tự !",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public
            void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, OnBoardingActivity.class));
                }
                else
                    Toast.makeText(LoginActivity.this,"Đăng nhập thất bại !",Toast.LENGTH_SHORT).show();
            }
        });
        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public
    void signUp(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}