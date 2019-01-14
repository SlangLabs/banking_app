package com.example.akanshisrivastava.bankingapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akanshisrivastava.bankingapp.adapters.DatePickerFragment;
import com.example.akanshisrivastava.bankingapp.adapters.NothingSelectedSpinnerAdapter;

import static android.view.View.GONE;

public class MoneyTransfer extends AppCompatActivity {

    private Button imps, neft, proceed;
    private LinearLayout neftDescription;
    private TextView description;
    private EditText amount, remarks, payLaterDate;
    private Spinner payeeSpinner;
    private RadioGroup neftRadio;
    private boolean payLaterVisible;
    private static final String TAG = MoneyTransfer.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        getSupportActionBar().setTitle("Funds Transfer");
        payLaterVisible = false;

        neftDescription = findViewById(R.id.neft_layout);
        description = findViewById(R.id.type_description);

        amount = findViewById(R.id.transfer_amount);
        remarks = findViewById(R.id.transfer_remarks);
        payLaterDate = findViewById(R.id.pay_later_date);

        neftRadio = findViewById(R.id.neft_rg);
        neftRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = neftRadio.findViewById(checkedId);
                boolean isChecked = button.isChecked();
                if(isChecked) {
                    if(checkedId == R.id.pay_now) {
                        payLaterDate.setVisibility(GONE);
                        enableSubmitIfReady();
                    }
                    else {
                        payLaterDate.setVisibility(View.VISIBLE);
                        enableSubmitIfReady();
                    }
                }
            }
        });

        payLaterDate.addTextChangedListener(new TextWatcher() {
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

        payLaterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

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
                neftDescription.setVisibility(GONE);
                //imps.requestFocus();
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
                //neftDescription.requestFocus();
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
        payeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enableSubmitIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                enableSubmitIfReady();
            }
        });


    }

    private void enableSubmitIfReady() {
        boolean neftDate = false;
        if(payLaterDate.getVisibility() == View.VISIBLE) {
            neftDate = !payLaterDate.getText().toString().isEmpty();
        }
        else {
            neftDate = true;
        }
        boolean spin = payeeSpinner != null && payeeSpinner.getSelectedItem() != null;
        boolean isReady = neftDate && spin && !amount.getText().toString().isEmpty() &&
                !remarks.getText().toString().isEmpty();
        proceed.setEnabled(isReady);
    }

}
