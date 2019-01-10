package com.example.akanshisrivastava.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoneyTransfer extends AppCompatActivity {

    Button imps, neft, proceed;
    LinearLayout neftDescription;
    TextView description;
    private static final String TAG = MoneyTransfer.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        getSupportActionBar().setTitle("Funds Transfer");

        neftDescription = findViewById(R.id.neft_layout);
        description = findViewById(R.id.type_description);

        imps = findViewById(R.id.imps);
        imps.setFocusable(true);
        imps.setFocusableInTouchMode(true);
        imps.requestFocus();
        imps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick IMPS");
                neftDescription.setVisibility(View.GONE);
                imps.requestFocus();
                description.setText(R.string.imps_description);
            }
        });
        imps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    v.performClick();
            }
        });

        neft = findViewById(R.id.neft);
        neft.setFocusable(true);
        neft.setFocusableInTouchMode(true);
        neft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick NEFT");
                neftDescription.setVisibility(View.VISIBLE);
                neftDescription.requestFocus();
                description.setText(R.string.neft_description);
            }
        });
        neft.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    v.performClick();
            }
        });

        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MoneyTransfer.this, "Your funds will be transferred", Toast.LENGTH_LONG).show();
            }
        });
    }
}
