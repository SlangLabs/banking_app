package com.example.akanshisrivastava.bankingapp;

import android.content.Context;
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
import android.widget.Toast;

import com.example.akanshisrivastava.bankingapp.adapters.NothingSelectedSpinnerAdapter;
import com.example.akanshisrivastava.bankingapp.slang.ActivityDetector;

public class ElectricityFragment extends Fragment {
    private Button proceed;
    private Spinner elecSpinner;
    private EditText cn;
    private OnFragmentElecInteractionListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electricity, container, false);

        Bundle bundle = getArguments();
        String mode = "";
        if (bundle != null)
            mode = bundle.getString(ActivityDetector.PAYMENT_MODE);

        proceed = view.findViewById(R.id.electricity_proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Your bill payment will be completed.", Toast.LENGTH_LONG).show();
            }
        });

        elecSpinner = view.findViewById(R.id.electricity_spinner);
        elecSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enableSubmitIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                enableSubmitIfReady();
            }
        });

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
                enableSubmitIfReady();
            }
        });

        ArrayAdapter<CharSequence> elecAdapter;

        if(mode.equals(ActivityDetector.PAYMENT_ELEC)) {
            elecAdapter = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.elec_list,
                    android.R.layout.simple_spinner_item
            );
            if (mListener != null) {
                mListener.onFragmentInteraction("Electricity Bill");
                proceed.setText(R.string.pay_elec_bill);
            }
        }
        else {
            elecAdapter = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.water_list,
                    android.R.layout.simple_spinner_item
            );
            if (mListener != null) {
                mListener.onFragmentInteraction("Water Bill");
                proceed.setText(R.string.pay_water_bill);
            }
        }
        elecAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        elecSpinner.setAdapter(new NothingSelectedSpinnerAdapter(
                elecAdapter,
                R.layout.spinner_row_nothing_selected_elec,
                getContext()
        ));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentElecInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentPostInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void enableSubmitIfReady() {
        boolean spin = elecSpinner != null && elecSpinner.getSelectedItem() != null;
        boolean isReady = spin && cn.getText().toString().length() == 10;
        proceed.setEnabled(isReady);
    }

    public interface OnFragmentElecInteractionListener {
        public void onFragmentInteraction(String title);
    }
}
