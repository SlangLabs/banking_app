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
import android.widget.TextView;
import android.widget.Toast;

import in.slanglabs.bankingapp.R;

import in.slanglabs.bankingapp.adapters.NothingSelectedSpinnerAdapter;
import in.slanglabs.bankingapp.slang.ActivityDetector;

public class PostpaidFragment extends Fragment {

    private Button proceed;
    private Spinner postSpinner, accountSpinner;
    private EditText number, amount;
    private TextView textView;
    private static int num;
    private OnFragmentPostInteractionListener mListener;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postpaid, container, false);

        proceed = view.findViewById(R.id.postpaid_proceed);
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

        accountSpinner = view.findViewById(R.id.postpaid_account);
        ArrayAdapter<CharSequence> accountAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.account_number,
                R.layout.spinner_item
        );
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(accountAdapter);

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
        int amountPay = 0;
        String vendor = "";
        if (bundle != null) {
            mode = bundle.getString(ActivityDetector.PAYMENT_MODE);
            amountPay = bundle.getInt(ActivityDetector.ENTITY_AMOUNT);
            vendor = bundle.getString(ActivityDetector.ENTITY_VENDOR_NAME);
            if (amountPay > 0)
                amount.setText(String.valueOf(amountPay));
        }
        if (mode != null && mode.equals(ActivityDetector.PAYMENT_BROADBAND)) {
            textView.setText(R.string.broadband_number);
            number.setHint(R.string.broadband_hint);
            adapter = ArrayAdapter.createFromResource(
                    context,
                    R.array.broadband_list,
                    R.layout.spinner_item);
            if (mListener != null) {
                mListener.onFragmentInteraction("Broadband Bill");
                proceed.setText(R.string.pay_broad_bill);
            }
            num = 11;
        }
        else {
            textView.setText(R.string.mobile_number);
            number.setHint(R.string.mobile_number_hint);
            adapter = ArrayAdapter.createFromResource(
                    context,
                    R.array.postpaid_list,
                    R.layout.spinner_item);
            if (mListener != null) {
                mListener.onFragmentInteraction("Postpaid Bill");
                proceed.setText(R.string.pay_post_bill);
            }
            num = 10;
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        postSpinner.setAdapter(new NothingSelectedSpinnerAdapter(
                adapter,
                R.layout.spinner_row_nothing_selected_elec,
                getContext()
        ));
        if (vendor != null && !vendor.isEmpty()) {
            for(int i = 1; i < postSpinner.getCount(); i++) {
                if (postSpinner.getItemAtPosition(i).equals(vendor)) {
                    postSpinner.setSelection(i);
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
            mListener = (OnFragmentPostInteractionListener) context;
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
        boolean spin = postSpinner != null && postSpinner.getSelectedItem() != null;
        boolean isReady = spin && number.getText().toString().length() == num &&
                !amount.getText().toString().isEmpty();
        proceed.setEnabled(isReady);
        if(isReady) {
            proceed.setTextColor(getResources().getColor(R.color.slang_lib_white));
        }
        else {
            proceed.setTextColor(getResources().getColor(R.color.slang_lib_warm_grey));
        }
    }

    public interface OnFragmentPostInteractionListener {
        void onFragmentInteraction(String title);
    }
}
