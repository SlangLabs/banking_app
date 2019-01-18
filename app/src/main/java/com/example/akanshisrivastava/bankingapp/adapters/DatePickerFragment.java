package com.example.akanshisrivastava.bankingapp.adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.akanshisrivastava.bankingapp.AccountStatement;
import com.example.akanshisrivastava.bankingapp.MoneyTransfer;
import com.example.akanshisrivastava.bankingapp.R;
import com.example.akanshisrivastava.bankingapp.Statement;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static String mode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            mode = bundle.getString(AccountStatement.DATE_SELECTOR);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "" + dayOfMonth + "/" + (month + 1) + "/" + year;
        Activity activity = getActivity();
        if(activity instanceof Statement) {
            EditText editText = activity.findViewById(R.id.request_date);
            editText.setText(date);
        } else if (activity instanceof AccountStatement) {
            if(mode != null)
                AccountStatement.setDateSet(date, mode);
        } else if (activity instanceof MoneyTransfer) {
            EditText editText = activity.findViewById(R.id.pay_later_date);
            editText.setText(date);
        }
    }
}
