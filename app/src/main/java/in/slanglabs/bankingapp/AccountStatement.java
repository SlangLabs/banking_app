package in.slanglabs.bankingapp;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import in.slanglabs.bankingapp.adapters.DatePickerFragment;
import in.slanglabs.bankingapp.adapters.RecentTransactionsAdapter;
import in.slanglabs.bankingapp.network.RecentTransactionsPojo;
import in.slanglabs.bankingapp.slang.ActivityDetector;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountStatement extends AppCompatActivity {

    private static final String TAG = AccountStatement.class.getSimpleName();

    private LinearLayout transactionLayout;
    private RadioGroup radioGroup;
    private RadioButton lastMonth, lastThreeMonths;
    private Button proceed;
    private TextView noMatch;
    private static EditText start, end;
    private RecentTransactionsAdapter adapter;
    private RecyclerView recyclerView;
    private List<RecentTransactionsPojo> recentTransactions;
    public static final String DATE_SELECTOR = "date_selector";
    public static final String DATE_START = "date_start";
    public static final String DATE_END = "date_end";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_statement);
        getSupportActionBar().setTitle("Account Statement");

        Intent intent = getIntent();
        String mode = intent.getStringExtra(ActivityDetector.VIEW_STATEMENT_MODE);

        transactionLayout = findViewById(R.id.vs_linear_layout_transactions);
        radioGroup = findViewById(R.id.vs_radio_group);
        lastMonth = findViewById(R.id.vs_last_month);
        lastThreeMonths = findViewById(R.id.vs_last_three_months);
        proceed = findViewById(R.id.vs_proceed);
        noMatch = findViewById(R.id.no_match);
        start = findViewById(R.id.vs_start);
        end = findViewById(R.id.vs_end);
        start.setInputType(InputType.TYPE_NULL);
        end.setInputType(InputType.TYPE_NULL);
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
                recentTransactions = RecentTransactionsPojo.initCompleteList(getResources());
                List<RecentTransactionsPojo> recentTransactionsMonth = new ArrayList<>();
                if(!(radioGroup.getCheckedRadioButtonId() == -1)) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if (selectedId == R.id.vs_last_month || selectedId == R.id.vs_last_three_months) {
                        Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = 0;
                        if(selectedId == R.id.vs_last_month)
                            month = c.get(Calendar.MONTH) - 1;
                        else
                            month = c.get(Calendar.MONTH) - 3;
                        if(month < 0) {
                            month = month + 12;
                            year--;
                        }
                        int day = c.get(Calendar.DATE);
                        String requiredDate = day + "/" + (month + 1) + "/" + year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date strDate = new Date();
                        try {
                            strDate = sdf.parse(requiredDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for(int i = 0; i < recentTransactions.size(); i++) {
                            try {
                                if((sdf.parse(recentTransactions.get(i).date)).after(strDate)) {
                                    recentTransactionsMonth.add(recentTransactions.get(i));
                                }
                                else {
                                    break;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String requiredStart = start.getText().toString();
                    String requiredEnd = end.getText().toString();
                    for (int i = 0; i < recentTransactions.size(); i++) {
                        try {
                            Date requiredStartDate = sdf.parse(requiredStart);
                            Date requiredEndDate = sdf.parse(requiredEnd);
                            Date currentDate = sdf.parse(recentTransactions.get(i).date);
                            if ((currentDate.after(requiredStartDate) ||
                                    currentDate.equals(requiredStartDate)) &&
                                    (currentDate.before(requiredEndDate) ||
                                            currentDate.equals(requiredEndDate))) {
                                recentTransactionsMonth.add(recentTransactions.get(i));
                            } else if (!currentDate.after(requiredStartDate) &&
                                    !currentDate.before(requiredEndDate)) {
                                break;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AccountStatement.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                if (!recentTransactionsMonth.isEmpty()) {
                    noMatch.setVisibility(View.GONE);
                    adapter = new RecentTransactionsAdapter(AccountStatement.this, recentTransactionsMonth);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    noMatch.setVisibility(View.VISIBLE);
                    transactionLayout.setVisibility(View.GONE);
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                enableSubmitIfRead();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.clearCheck();
                DialogFragment newFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString(DATE_SELECTOR, DATE_START);
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.clearCheck();
                DialogFragment newFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString(DATE_SELECTOR, DATE_END);
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        if(mode.equals(ActivityDetector.VIEW_STATEMENT_LAST_MONTH)) {
            String month = intent.getStringExtra(ActivityDetector.ENTITY_MONTH);
            if(month.equalsIgnoreCase(ActivityDetector.ENTITY_VALUE_LAST_MONTH)) {
                lastMonth.setChecked(true);
                proceed.callOnClick();
            } else if(month.equalsIgnoreCase(ActivityDetector.ENTITY_VALUE_LAST_THREE_MONTHS)) {
                lastThreeMonths.setChecked(true);
                proceed.callOnClick();
            }
        } else if(mode.equals(ActivityDetector.VIEW_STATEMENT_DATE)) {
            String startDate = intent.getStringExtra(ActivityDetector.ENTITY_START);
            String endDate = intent.getStringExtra(ActivityDetector.ENTITY_END);
            if (endDate==null || endDate.isEmpty()) {
                Date date = new Date();
                String strDateFormat = "dd/MM/yyyy";
                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                endDate = dateFormat.format(date);
            }
            start.setText(startDate);
            end.setText(endDate);
            proceed.callOnClick();
        }
    }

    private void enableSubmitIfRead() {
        boolean radio = !(radioGroup.getCheckedRadioButtonId() == -1);
        boolean isReady = radio || (!start.getText().toString().isEmpty() &&
                !end.getText().toString().isEmpty());
        proceed.setEnabled(isReady);
    }

    public static void setDateSet(String dateSet, String mode) {
        if(mode.equals(DATE_START))
            start.setText(dateSet);
        else
            end.setText(dateSet);
    }
}
