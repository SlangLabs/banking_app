package com.example.akanshisrivastava.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.slanglabs.platform.ui.SlangUI;

public class MainActivity extends AppCompatActivity {

    Button account,transfer,care,deposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SlangUI.showTrigger();
        account = findViewById(R.id.account);
        transfer = findViewById(R.id.mtransfer);
        care = findViewById(R.id.care);
        deposit = findViewById(R.id.deposit);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Accounts.class);
                startActivity(intent);
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoneyTransfer.class);
                startActivity(intent);
            }
        });
        care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerCare.class);
                startActivity(intent);
            }
        });
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Deposit.class);
                startActivity(intent);
            }
        });
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        SlangUI.showTrigger();
    }*/
}
