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
            Bundle bundle = new Bundle();
            bundle.putString(ActivityDetector.PAYMENT_MODE, mode);
            switch (mode) {
                //TODO disable some modes
                //case ActivityDetector.PAYMENT_CREDIT:
                case ActivityDetector.PAYMENT_ELEC:
                case ActivityDetector.PAYMENT_WATER:
                //case ActivityDetector.PAYMENT_TAX:
                    ElectricityFragment electricityFragment = new ElectricityFragment();
                    electricityFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.billPayContainer, electricityFragment)
                            .commit();
                    break;
                case ActivityDetector.PAYMENT_POST:
                case ActivityDetector.PAYMENT_BROADBAND:
                    PostpaidFragment postpaidFragment = new PostpaidFragment();
                    postpaidFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.billPayContainer, postpaidFragment)
                            .commit();
                    break;
            }
        }
    }
}
