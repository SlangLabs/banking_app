package com.example.akanshisrivastava.bankingapp;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.akanshisrivastava.bankingapp.adapters.DatePickerFragment;
import com.example.akanshisrivastava.bankingapp.adapters.RecentTransactionsAdapter;
import com.example.akanshisrivastava.bankingapp.network.RecentTransactionsPojo;

import java.util.List;

public class AccountStatement extends AppCompatActivity {

    private LinearLayout customDateLayout, transactionLayout;
    private RadioGroup radioGroup;
    private Button proceed;
    private static EditText start, end;
    private RecentTransactionsAdapter adapter;
    private RecyclerView recyclerView;
    private List<RecentTransactionsPojo> recentTransactions;
    private static boolean startFocus, endFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_statement);
        getSupportActionBar().setTitle("Account Statement");

        customDateLayout = findViewById(R.id.custom_date_layout);
        transactionLayout = findViewById(R.id.vs_linear_layout_transactions);
        radioGroup = findViewById(R.id.vs_radio_group);
        proceed = findViewById(R.id.vs_proceed);
        start = findViewById(R.id.vs_start);
        end = findViewById(R.id.vs_end);
        recyclerView = findViewById(R.id.vs_view_transactions_rv);

        transactionLayout.setVisibility(View.GONE);
        start.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSubmitIfRead();
            }
        });
        end.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSubmitIfRead();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionLayout.setVisibility(View.VISIBLE);
                recentTransactions = RecentTransactionsPojo.initRecentList(getResources());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AccountStatement.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new RecentTransactionsAdapter(AccountStatement.this, recentTransactions);
                recyclerView.setAdapter(adapter);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                enableSubmitIfRead();
            }
        });
        start.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    startFocus = true;
                    endFocus = false;
                    radioGroup.clearCheck();
                    v.performClick();
                }
                else
                    startFocus = false;
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        end.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    endFocus = true;
                    startFocus = false;
                    radioGroup.clearCheck();
                    v.performClick();
                }
                else
                    endFocus = false;
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    private void enableSubmitIfRead() {
        boolean radio = !(radioGroup.getCheckedRadioButtonId() == -1);
        boolean isReady = radio || (!start.getText().toString().isEmpty() &&
                !end.getText().toString().isEmpty());
        proceed.setEnabled(isReady);
    }

    public static void setDateSet(String dateSet) {
        if(startFocus && !endFocus)
            start.setText(dateSet);
        else if(!startFocus && endFocus)
            end.setText(dateSet);
    }
}
