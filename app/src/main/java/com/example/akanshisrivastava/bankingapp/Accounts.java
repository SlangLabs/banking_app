package com.example.akanshisrivastava.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Accounts extends AppCompatActivity {

    Button services, recent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        services = findViewById(R.id.services);
        recent = findViewById(R.id.recent);

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accounts.this, Services.class);
                startActivity(intent);
            }
        });

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accounts.this, RecentTransactions.class);
                startActivity(intent);
            }
        });

    }
}
