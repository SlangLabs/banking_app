package com.example.akanshisrivastava.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.akanshisrivastava.bankingapp.slang.ActivityDetector;

public class BillPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);
        getSupportActionBar().setTitle("Pay Bills");

        Intent intent = getIntent();
        String mode = intent.getStringExtra(ActivityDetector.PAYMENT_MODE);

        if (savedInstanceState == null) {
            switch (mode) {
                //TODO disable some modes
                case ActivityDetector.PAYMENT_CREDIT:
                case ActivityDetector.PAYMENT_ELEC:
                case ActivityDetector.PAYMENT_POST:
                case ActivityDetector.PAYMENT_LANDLINE:
                case ActivityDetector.PAYMENT_WATER:
                case ActivityDetector.PAYMENT_TAX:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.billPayContainer, new ElectricityFragment())
                            .commit();
            }
        }
    }
}
