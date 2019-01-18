package com.example.akanshisrivastava.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.akanshisrivastava.bankingapp.slang.ActivityDetector;

public class BillPayment extends AppCompatActivity implements
        ElectricityFragment.OnFragmentElecInteractionListener,
        PostpaidFragment.OnFragmentPostInteractionListener{

    private static final String TAG = BillPayment.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);
        getSupportActionBar().setTitle("Pay Bills");

        Intent intent = getIntent();
        String mode = intent.getStringExtra(ActivityDetector.PAYMENT_MODE);
        int amount = intent.getIntExtra(ActivityDetector.ENTITY_AMOUNT, 0);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString(ActivityDetector.PAYMENT_MODE, mode);
            bundle.putInt(ActivityDetector.ENTITY_AMOUNT, amount);
            switch (mode) {
                case ActivityDetector.PAYMENT_ELEC:
                case ActivityDetector.PAYMENT_WATER:
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

    @Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);
    }
}
