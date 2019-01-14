package com.example.akanshisrivastava.bankingapp;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.akanshisrivastava.bankingapp.adapters.DatePickerFragment;
import com.example.akanshisrivastava.bankingapp.adapters.NothingSelectedSpinnerAdapter;

public class Statement extends AppCompatActivity {

    private Spinner dispatchSpinner, frequencySpinner;
    private EditText selectDate, emailID;
    private Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        getSupportActionBar().setTitle("Request Statement");

        dispatchSpinner = findViewById(R.id.request_dispatch_mode);
        frequencySpinner = findViewById(R.id.request_frequency);
        selectDate = findViewById(R.id.request_date);
        selectDate.setInputType(InputType.TYPE_NULL);
        emailID = findViewById(R.id.request_email);
        proceed = findViewById(R.id.request_register);

        dispatchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enableSubmitIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                enableSubmitIfReady();
            }
        });
        ArrayAdapter<CharSequence> dispatchAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.dispatch_list,
                android.R.layout.simple_spinner_item
        );
        dispatchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dispatchSpinner.setAdapter(new NothingSelectedSpinnerAdapter(
                dispatchAdapter,
                R.layout.spinner_row_nothing_selected_request_dispatch,
                this
        ));

        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enableSubmitIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                enableSubmitIfReady();
            }
        });
        ArrayAdapter<CharSequence> frequencyAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.frequency_list,
                android.R.layout.simple_spinner_item
        );
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(new NothingSelectedSpinnerAdapter(
                frequencyAdapter,
                R.layout.spinner_row_nothing_selected_request_frequency,
                this
        ));
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        selectDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    v.performClick();
            }
        });
        selectDate.addTextChangedListener(new TextWatcher() {
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
        emailID.addTextChangedListener(new TextWatcher() {
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

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Statement.this, "Your request has been submitted successfully!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void enableSubmitIfReady() {
        boolean spin = dispatchSpinner != null && dispatchSpinner.getSelectedItem() != null &&
                frequencySpinner != null && frequencySpinner.getSelectedItem() != null;
        boolean isReady = spin && !selectDate.getText().toString().isEmpty() &&
                !emailID.getText().toString().isEmpty();
        proceed.setEnabled(isReady);
    }
}
