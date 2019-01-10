package com.example.akanshisrivastava.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akanshisrivastava.bankingapp.adapters.NothingSelectedSpinnerAdapter;

public class MoneyTransfer extends AppCompatActivity {

    Button imps, neft, proceed;
    LinearLayout neftDescription;
    TextView description;
    EditText amount, remarks;
    Spinner payeeSpinner;
    private static final String TAG = MoneyTransfer.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        getSupportActionBar().setTitle("Funds Transfer");

        neftDescription = findViewById(R.id.neft_layout);
        description = findViewById(R.id.type_description);

        amount = findViewById(R.id.transfer_amount);
        remarks = findViewById(R.id.transfer_remarks);

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSubmitIfReady();
            }
        });

        remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSubmitIfReady();
            }
        });

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
        proceed.setEnabled(false);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MoneyTransfer.this, "Your funds will be transferred", Toast.LENGTH_LONG).show();
            }
        });

        payeeSpinner = findViewById(R.id.payee_spinner);

        ArrayAdapter<CharSequence> payeeAdapter =
                ArrayAdapter.createFromResource(this, R.array.transfer_list, android.R.layout.simple_spinner_item);
        payeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        payeeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(
                payeeAdapter,
                R.layout.spinner_row_nothing_selected_payee,
                this
        ));


    }

    private void enableSubmitIfReady() {
        boolean isReady = !amount.getText().toString().isEmpty() &&
                !remarks.getText().toString().isEmpty();
        proceed.setEnabled(isReady);
    }
}
