package com.example.akanshisrivastava.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.akanshisrivastava.bankingapp.slang.ActivityDetector;

public class Services extends AppCompatActivity {

    Button request, cheque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        request = findViewById(R.id.requests);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this, Statement.class);
                startActivity(intent);
            }
        });

        cheque = findViewById(R.id.apply);
        cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this, OrderCheque.class);
                intent.putExtra(
                        ActivityDetector.ORDER_CHEQUE_MODE,
                        ActivityDetector.ORDER_CHEQUE_DEFAULT
                );
                startActivity(intent);
            }
        });
    }
}
