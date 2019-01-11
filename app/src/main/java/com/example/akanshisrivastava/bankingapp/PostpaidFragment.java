package com.example.akanshisrivastava.bankingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akanshisrivastava.bankingapp.adapters.NothingSelectedSpinnerAdapter;
import com.example.akanshisrivastava.bankingapp.slang.ActivityDetector;

public class PostpaidFragment extends Fragment {

    private Button proceed;
    private Spinner postSpinner;
    private EditText number, amount;
    private TextView textView;
    private static int num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postpaid, container, false);

        proceed = view.findViewById(R.id.postpaid_proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Your bill payment will be completed.", Toast.LENGTH_LONG).show();
            }
        });

        postSpinner = view.findViewById(R.id.postpaid_spinner);
        postSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enableSubmitIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                enableSubmitIfReady();
            }
        });

        number = view.findViewById(R.id.postpaid_number);
        number.addTextChangedListener(new TextWatcher() {
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

        amount = view.findViewById(R.id.postpaid_amount);
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
        textView = view.findViewById(R.id.postpaid_tv_number);
        ArrayAdapter<CharSequence> adapter;

        Bundle bundle = getArguments();
        String mode = "";
        if (bundle != null)
            mode = bundle.getString(ActivityDetector.PAYMENT_MODE);
        if (mode.equals(ActivityDetector.PAYMENT_BROADBAND)) {
            textView.setText(R.string.broadband_number);
            number.setHint(R.string.broadband_hint);
            adapter = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.landline_list,
                    android.R.layout.simple_spinner_item);
            num = 11;
        }
        else {
            textView.setText(R.string.mobile_number);
            number.setHint(R.string.mobile_number_hint);
            adapter = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.postpaid_list,
                    android.R.layout.simple_spinner_item);
            num = 10;
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        postSpinner.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.spinner_row_nothing_selected_elec,
                getContext()
        ));
        return view;
    }

    private void enableSubmitIfReady() {
        boolean spin = postSpinner != null && postSpinner.getSelectedItem() != null;
        boolean isReady = spin && number.getText().toString().length() == num &&
                !amount.getText().toString().isEmpty();
        proceed.setEnabled(isReady);
    }
}