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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.akanshisrivastava.bankingapp.adapters.NothingSelectedSpinnerAdapter;

public class ElectricityFragment extends Fragment {
    private Button proceed;
    private Spinner elec;
    private EditText cn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electricity, container, false);

        proceed = view.findViewById(R.id.electricity_proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Your bill payment will be completed.", Toast.LENGTH_LONG).show();
            }
        });

        elec = view.findViewById(R.id.electricity_spinner);
        cn = view.findViewById(R.id.electricity_cn);

        cn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isReady = cn.getText().toString().length() == 10;
                proceed.setEnabled(isReady);
            }
        });

        ArrayAdapter<CharSequence> elecAdapter =
                ArrayAdapter.createFromResource(getContext(), R.array.elec_list,android.R.layout.simple_spinner_item);
        elecAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        elec.setAdapter(new NothingSelectedSpinnerAdapter(
                elecAdapter,
                R.layout.spinner_row_nothing_selected_elec,
                getContext()
        ));
        return view;
    }
}
