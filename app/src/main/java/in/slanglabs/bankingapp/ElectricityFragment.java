package in.slanglabs.bankingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import in.slanglabs.bankingapp.adapters.NothingSelectedSpinnerAdapter;
import in.slanglabs.bankingapp.slang.ActivityDetector;

public class ElectricityFragment extends Fragment {
    private Button proceed;
    private Spinner elecSpinner, accountSpinner;
    private EditText cn, amount;
    private OnFragmentElecInteractionListener mListener;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electricity, container, false);

        Bundle bundle = getArguments();
        String mode = "";
        int amountPay = 0;
        String vendor = "";
        if (bundle != null) {
            mode = bundle.getString(ActivityDetector.PAYMENT_MODE);
            amountPay = bundle.getInt(ActivityDetector.ENTITY_AMOUNT);
            vendor = bundle.getString(ActivityDetector.ENTITY_VENDOR_NAME);
        }

        proceed = view.findViewById(R.id.electricity_proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Please confirm your bill payment");
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        getActivity().onBackPressed();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked cancel button
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        accountSpinner = view.findViewById(R.id.electricity_account);
        ArrayAdapter<CharSequence> accountAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.account_number,
                R.layout.spinner_item
        );
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(accountAdapter);

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

        amount = view.findViewById(R.id.electricity_amount);

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

        ArrayAdapter<CharSequence> elecAdapter;

        if (amountPay > 0)
            amount.setText(String.valueOf(amountPay));

        if(mode != null && mode.equals(ActivityDetector.PAYMENT_ELEC)) {
            elecAdapter = ArrayAdapter.createFromResource(
                    context,
                    R.array.elec_list,
                    R.layout.spinner_item
            );
            if (mListener != null) {
                mListener.onFragmentInteraction("Electricity Bill");
                proceed.setText(R.string.pay_elec_bill);
            }
        }
        else {
            elecAdapter = ArrayAdapter.createFromResource(
                    context,
                    R.array.water_list,
                    R.layout.spinner_item
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
        if (vendor != null && !vendor.isEmpty()) {
            for(int i = 1; i < elecSpinner.getCount(); i++) {
                if (elecSpinner.getItemAtPosition(i).equals(vendor)) {
                    elecSpinner.setSelection(i);
                    break;
                }
            }
        }
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
        boolean isReady = spin && cn.getText().toString().length() == 10 &&
                !amount.getText().toString().isEmpty();
        if (!amount.getText().toString().isEmpty()) {
            isReady = isReady && (Integer.parseInt(amount.getText().toString()) > 0);
        }
        proceed.setEnabled(isReady);
        if(isReady) {
            proceed.setTextColor(getResources().getColor(R.color.white));
        }
        else {
            proceed.setTextColor(getResources().getColor(R.color.warm_grey));
        }
    }

    public interface OnFragmentElecInteractionListener {
        void onFragmentInteraction(String title);
    }
}
