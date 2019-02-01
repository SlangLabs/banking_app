package com.example.akanshisrivastava.bankingapp.slang;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.akanshisrivastava.bankingapp.AccountStatement;
import com.example.akanshisrivastava.bankingapp.Accounts;
import com.example.akanshisrivastava.bankingapp.BillPayment;
import com.example.akanshisrivastava.bankingapp.Bills;
import com.example.akanshisrivastava.bankingapp.CustomerCare;
import com.example.akanshisrivastava.bankingapp.MoneyTransfer;
import com.example.akanshisrivastava.bankingapp.OrderCheque;
import com.example.akanshisrivastava.bankingapp.R;
import com.example.akanshisrivastava.bankingapp.RecentTransactions;
import com.example.akanshisrivastava.bankingapp.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import in.slanglabs.platform.SlangBuddy;
import in.slanglabs.platform.SlangBuddyOptions;
import in.slanglabs.platform.SlangEntity;
import in.slanglabs.platform.SlangIntent;
import in.slanglabs.platform.SlangLocale;
import in.slanglabs.platform.SlangSession;
import in.slanglabs.platform.action.SlangMultiStepIntentAction;

public class VoiceInterface {
    private static final String TAG = VoiceInterface.class.getSimpleName();

    private VoiceInterface sInstance = new VoiceInterface();
    private static Context appContext;

    private static boolean defaultMode;
    private static int amount;
    private static String date, payee, payment, month, start, end, mode, vendor;

    public static void init(final Application context) {
        appContext = context;
        String apiKey = "c80525dd5fa146d6a3a1aba91fc5d6b9";
        String buddyId = "5671e72eefe54307b5e32fcafdcf02ac";

            SlangBuddyOptions options = new SlangBuddyOptions.Builder()
                    .setContext(context)
                    .setBuddyId(buddyId)
                    .setAPIKey(apiKey)
                    .setListener(new BuddyListener())
                    .setIntentAction(new V1Action(appContext))
                    .setRequestedLocales(SlangLocale.getSupportedLocales())
                    .setDefaultLocale(SlangLocale.LOCALE_ENGLISH_IN)
                    .setEnvironment(SlangBuddy.Environment.STAGING)
                    .build();
            SlangBuddy.initialize(options);
    }

    private static class V1Action implements SlangMultiStepIntentAction {

        private final Context mContext;
        V1Action(Context ctx) {
            mContext = ctx;
        }

        @Override
        public void onIntentResolutionBegin(SlangIntent intent, SlangSession context) {
            mode = "";
            vendor = "";
            amount = 0;
            payment = "";
            payee = "";
            date = "";
            defaultMode = true;
            month = "";
            start = "";
            end = "";
        }

        @Override
        public Status onEntityUnresolved(SlangEntity entity, SlangSession context) {
            return Status.SUCCESS;
        }

        @Override
        public Status onEntityResolved(SlangEntity entity, SlangSession context) {
            Log.d(TAG, "Entity value " + entity.getValue());
            Log.d(TAG, "Parent intent name is " + entity.getIntent().getName());
            switch (entity.getIntent().getName()) {
                case ActivityDetector.INTENT_PAY_BILLS:
                    switch (entity.getName()) {
                        case ActivityDetector.ENTITY_BILL:
                            switch (entity.getValue()) {
                                case ActivityDetector.ENTITY_VALUE_ELEC:
                                    mode = ActivityDetector.PAYMENT_ELEC;
                                    return Status.SUCCESS;
                                case ActivityDetector.ENTITY_VALUE_WATER:
                                    mode = ActivityDetector.PAYMENT_WATER;
                                    return Status.SUCCESS;
                                case ActivityDetector.ENTITY_VALUE_BROADBAND:
                                    mode = ActivityDetector.PAYMENT_BROADBAND;
                                    return Status.SUCCESS;
                                case ActivityDetector.ENTITY_VALUE_POSTPAID:
                                    mode = ActivityDetector.PAYMENT_POST;
                                    return Status.SUCCESS;
                            }
                            break;
                        case ActivityDetector.ENTITY_AMOUNT:
                            amount = Integer.valueOf(entity.getValue());
                            Log.d(TAG, "Amount value is " + amount);
                            return Status.SUCCESS;
                        case ActivityDetector.ENTITY_VENDOR_NAME:
                            vendor = entity.getValue();
                            Log.d(TAG, "Vendor name is " + vendor);
                            return Status.SUCCESS;
                    }
                    break;
                case ActivityDetector.INTENT_FUND_TRANSFER:
                    switch (entity.getName()) {
                        case ActivityDetector.ENTITY_AMOUNT:
                            amount = Integer.valueOf(entity.getValue());
                            return Status.SUCCESS;
                        case ActivityDetector.ENTITY_PAYEE:
                            payee = entity.getValue();
                            return Status.SUCCESS;
                        case ActivityDetector.ENTITY_PAYMENT:
                            payment = entity.getValue().toUpperCase();
                            return Status.SUCCESS;
                        case ActivityDetector.ENTITY_DATE:
                            date = entity.getValue();
                            return Status.SUCCESS;
                    }
                    break;
                case ActivityDetector.INTENT_VIEW_ACCOUNT_STATEMENT:
                    Log.d(TAG, "Entity resolved is " + entity.getName() + " & value is "
                            + entity.getValue());
                    switch (entity.getName()) {
                        case ActivityDetector.ENTITY_MONTH:
                            month = entity.getValue();
                            defaultMode = false;
                            return Status.SUCCESS;
                        case ActivityDetector.ENTITY_START:
                            String year = entity.getValue().substring(0, 4);
                            String month = entity.getValue().substring(5, 7);
                            String date = entity.getValue().substring(8);
                            Log.d(TAG, "Year is " + year);
                            start = date + "/" + month + "/" + year;
                            Date dateValue = new Date();
                            SimpleDateFormat dateFormat =
                                    new SimpleDateFormat("dd/MM/yyyy");
                            Date startDate;
                            try {
                                startDate = dateFormat.parse(start);
                                if (!(dateValue.compareTo(startDate) > 0)) {
                                    int yearInt = Integer.parseInt(year);
                                    yearInt--;
                                    year = String.valueOf(yearInt);
                                }
                                start = date + "/" + month + "/" + year;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            defaultMode = false;
                            return Status.SUCCESS;
                        case ActivityDetector.ENTITY_END:
                            year = entity.getValue().substring(0, 4);
                            month = entity.getValue().substring(5, 7);
                            date = entity.getValue().substring(8);
                            Log.d(TAG, "Year is " + year);
                            end = date + "/" + month + "/" + year;
                            defaultMode = false;
                            return Status.SUCCESS;
                    }
                    break;
            }
            return Status.SUCCESS;
        }

        @Override
        public void onIntentResolutionEnd(SlangIntent intent, SlangSession context) {
            Log.d(TAG, "inside intentResolutionEnd");
        }

        @Override
        public Status action(SlangIntent slangIntent, SlangSession context) {
            Log.d(TAG, "inside action");
            switch (slangIntent.getName()) {
                case ActivityDetector.INTENT_VIEW_ACCOUNT_SUMMARY:
                    Intent intent = new Intent(appContext, Accounts.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appContext.startActivity(intent);
                    return Status.SUCCESS;
                case ActivityDetector.INTENT_VIEW_RECENT_TRANSACTIONS:
                    intent = new Intent(appContext, RecentTransactions.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appContext.startActivity(intent);
                    return Status.SUCCESS;
                case ActivityDetector.INTENT_REQUEST_ACCOUNT_STATEMENT:
                    intent = new Intent(appContext, Statement.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appContext.startActivity(intent);
                    return Status.SUCCESS;
                case ActivityDetector.INTENT_ORDER_CHEQUE:
                    intent = new Intent(appContext, OrderCheque.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(
                            ActivityDetector.ORDER_CHEQUE_MODE,
                            ActivityDetector.ORDER_CHEQUE_SLANG
                    );
                    appContext.startActivity(intent);
                    return Status.SUCCESS;
                case ActivityDetector.INTENT_CONTACT_US:
                    intent = new Intent(appContext, CustomerCare.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appContext.startActivity(intent);
                    return Status.SUCCESS;
                case ActivityDetector.INTENT_PAY_BILLS:
                    if(mode.isEmpty()) {
                        intent = new Intent(appContext, Bills.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                    }
                    else {
                        intent = new Intent(appContext, BillPayment.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(ActivityDetector.PAYMENT_MODE, mode);
                        if (amount > 0)
                            intent.putExtra(ActivityDetector.ENTITY_AMOUNT, amount);
                        if (!vendor.isEmpty()) {
                            switch (mode) {
                                case ActivityDetector.PAYMENT_ELEC:
                                    ArrayList<String> elecList =
                                            new ArrayList<>(Arrays.asList(appContext.getResources().getStringArray(R.array.elec_list)));
                                    if (elecList.contains(vendor))
                                        intent.putExtra(ActivityDetector.ENTITY_VENDOR_NAME, vendor);
                                    break;
                                case ActivityDetector.PAYMENT_WATER:
                                    ArrayList<String> waterList =
                                            new ArrayList<>(Arrays.asList(appContext.getResources().getStringArray(R.array.water_list)));
                                    if (waterList.contains(vendor))
                                        intent.putExtra(ActivityDetector.ENTITY_VENDOR_NAME, vendor);
                                    break;
                                case ActivityDetector.PAYMENT_BROADBAND:
                                    ArrayList<String> broadList =
                                            new ArrayList<>(Arrays.asList(appContext.getResources().getStringArray(R.array.broadband_list)));
                                    if (broadList.contains(vendor))
                                        intent.putExtra(ActivityDetector.ENTITY_VENDOR_NAME, vendor);
                                    break;
                                case ActivityDetector.PAYMENT_POST:
                                    ArrayList<String> postList =
                                            new ArrayList<>(Arrays.asList(appContext.getResources().getStringArray(R.array.postpaid_list)));
                                    if (postList.contains(vendor))
                                        intent.putExtra(ActivityDetector.ENTITY_VENDOR_NAME, vendor);
                                    break;
                            }
                        }
                        slangIntent
                                .getCompletionStatement()
                                .overrideAffirmative(
                                        getCompletionPrompt(ActivityDetector.PAYMENT_MODE)
                                );
                        appContext.startActivity(intent);
                    }
                    return Status.SUCCESS;
                case ActivityDetector.INTENT_FUND_TRANSFER:
                    intent = new Intent(appContext, MoneyTransfer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(ActivityDetector.ENTITY_PAYEE, payee);
                    intent.putExtra(ActivityDetector.ENTITY_AMOUNT, amount);
                    intent.putExtra(ActivityDetector.ENTITY_PAYMENT, payment);
                    intent.putExtra(ActivityDetector.ENTITY_DATE, date);
                    appContext.startActivity(intent);
                    if (!(amount == 0 && payment.isEmpty() && payee.isEmpty() &&
                            date.isEmpty())) {
                        slangIntent
                                .getCompletionStatement()
                                .overrideAffirmative(
                                        getCompletionPrompt(ActivityDetector.TRANSFER_MODE)
                                );
                    }
                    return Status.SUCCESS;
                case ActivityDetector.INTENT_VIEW_ACCOUNT_STATEMENT:
                    intent = new Intent(appContext, AccountStatement.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (defaultMode) {
                        intent.putExtra(
                                ActivityDetector.VIEW_STATEMENT_MODE,
                                ActivityDetector.VIEW_STATEMENT_DEFAULT
                        );
                        appContext.startActivity(intent);
                        slangIntent
                                .getCompletionStatement()
                                .overrideAffirmative(
                                        getCompletionPrompt(
                                                ActivityDetector.VIEW_STATEMENT_MODE
                                        )
                                );
                        return Status.SUCCESS;
                    }
                    else {
                        if (!month.isEmpty()) {
                            intent.putExtra(
                                    ActivityDetector.VIEW_STATEMENT_MODE,
                                    ActivityDetector.VIEW_STATEMENT_LAST_MONTH
                            );
                            intent.putExtra(ActivityDetector.ENTITY_MONTH, month);
                        } else if (!start.isEmpty()) {
                            intent.putExtra(
                                    ActivityDetector.VIEW_STATEMENT_MODE,
                                    ActivityDetector.VIEW_STATEMENT_DATE
                            );
                            intent.putExtra(ActivityDetector.ENTITY_START, start);
                            if (!end.isEmpty())
                                intent.putExtra(ActivityDetector.ENTITY_END, end);
                        }
                        appContext.startActivity(intent);
                        return Status.SUCCESS;
                    }
            }
            return null;
        }
    }

    private static String getCompletionPrompt(String mode) {
        switch (mode){
            case ActivityDetector.PAYMENT_MODE:
            case ActivityDetector.VIEW_STATEMENT_MODE:
            case ActivityDetector.TRANSFER_MODE:
                return "Please enter all details to proceed.";
        }
        return null;
    }

    private static class BuddyListener implements SlangBuddy.Listener {
        @Override
        public void onInitialized() {}

        @Override
        public void onInitializationFailed(SlangBuddy.InitializationError e) {}
    }
}
