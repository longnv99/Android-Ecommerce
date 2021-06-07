package com.example.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.ecommerce.R;

public
class ShipmentActivity extends AppCompatActivity {

    RadioButton rd_Best, rd_ghtk, rd_jt;
    Button btnSave;
    Toolbar toolbar;
    int ship = 0;
    int total;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);
        rd_Best=findViewById(R.id.rd_best);
        rd_ghtk=findViewById(R.id.rd_ghtk);
        rd_jt=findViewById(R.id.rd_jt);
        btnSave = findViewById(R.id.btnSaveShip);

        toolbar =findViewById(R.id.ship_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                finish();
            }
        });

        total = getIntent().getIntExtra("allPrice",0);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                if(rd_Best.isChecked()){
                    ship += 32000;
                }
                else if(rd_ghtk.isChecked()){
                    ship += 35000;
                }
                else if(rd_jt.isChecked()){
                    ship += 33000;
                }
                Intent i = new Intent(ShipmentActivity.this, PaymentActivity.class);
                i.putExtra("ship",ship);
                i.putExtra("tt2",total);
                startActivity(i);
            }
        });

    }
}