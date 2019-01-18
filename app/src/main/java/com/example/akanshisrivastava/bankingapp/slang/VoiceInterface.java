package com.example.akanshisrivastava.bankingapp.slang;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.example.akanshisrivastava.bankingapp.AccountStatement;
import com.example.akanshisrivastava.bankingapp.Accounts;
import com.example.akanshisrivastava.bankingapp.BillPayment;
import com.example.akanshisrivastava.bankingapp.Bills;
import com.example.akanshisrivastava.bankingapp.CustomerCare;
import com.example.akanshisrivastava.bankingapp.MoneyTransfer;
import com.example.akanshisrivastava.bankingapp.OrderCheque;
import com.example.akanshisrivastava.bankingapp.RecentTransactions;
import com.example.akanshisrivastava.bankingapp.Statement;

import in.slanglabs.platform.application.ISlangApplicationStateListener;
import in.slanglabs.platform.application.SlangApplication;
import in.slanglabs.platform.application.SlangApplicationUninitializedException;
import in.slanglabs.platform.application.SlangLocaleException;
import in.slanglabs.platform.application.actions.DefaultResolvedIntentAction;
import in.slanglabs.platform.session.SlangEntity;
import in.slanglabs.platform.session.SlangResolvedIntent;
import in.slanglabs.platform.session.SlangSession;

public class VoiceInterface {

    private static Application appContext;
    private static final String TAG = VoiceInterface.class.getSimpleName();
    private static boolean defaultMode;
    private static int amount;
    private static String date, payee, payment, month, start, end, mode;

    public static void init(final Application context, String appId, String authKey,
                            final boolean shouldHide) throws SlangLocaleException {
        appContext = context;
        Log.d(TAG, "Calling init");
        SlangApplication.initialize(appContext, appId, authKey,
                SlangApplication.getSupportedLocales(),
                SlangApplication.LOCALE_ENGLISH_IN,
                new ISlangApplicationStateListener() {
                    @Override
                    public void onInitialized() {
                        try {
                            registerActionsNew();
                        } catch (SlangApplicationUninitializedException e) {
                            Toast.makeText(
                                    appContext,
                                    "Slang uninitialized - " + e.getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onInitializationFailed(FailureReason failureReason) {
                        Toast.makeText(appContext, "Slang not initialized", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "________________ " + failureReason);
                    }
                });
    }

    private static void registerActionsNew() throws SlangApplicationUninitializedException {
        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_VIEW_ACCOUNT_SUMMARY)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent,
                                                      SlangSession slangSession) {
                        Intent intent = new Intent(appContext, Accounts.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_VIEW_RECENT_TRANSACTIONS)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent,
                                                      SlangSession slangSession) {
                        Intent intent = new Intent(appContext, RecentTransactions.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_VIEW_ACCOUNT_STATEMENT)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status onIntentResolutionBegin(SlangResolvedIntent intent,
                                                                       SlangSession session) {
                        defaultMode = true;
                        month = "";
                        start = "";
                        end = "";
                        return super.onIntentResolutionBegin(intent, session);
                    }

                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent,
                                                      SlangSession slangSession) {
                        Intent intent = new Intent(appContext, AccountStatement.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (defaultMode) {
                            intent.putExtra(
                                    ActivityDetector.VIEW_STATEMENT_MODE,
                                    ActivityDetector.VIEW_STATEMENT_DEFAULT
                            );
                            appContext.startActivity(intent);
                            slangResolvedIntent
                                    .getCompletionStatement()
                                    .overrideAffirmative(
                                            getCompletionPrompt(
                                                    ActivityDetector.VIEW_STATEMENT_MODE
                                            )
                                    );
                            return slangSession.success();
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
                            return slangSession.success();
                        }
                    }

                    @Override
                    public SlangSession.Status onEntityResolved(SlangEntity entity,
                                                                SlangSession session) {
                        Log.d(TAG, "Entity resolved is " + entity.getName() + " & value is "
                                + entity.getValue());
                        switch (entity.getName()) {
                            case ActivityDetector.ENTITY_MONTH:
                                month = entity.getValue();
                                defaultMode = false;
                                return session.success();
                            case ActivityDetector.ENTITY_START:
                                String year = entity.getValue().substring(0, 4);
                                String month = entity.getValue().substring(5, 7);
                                String date = entity.getValue().substring(8);
                                Log.d(TAG, "Year is " + year);
                                start = date + "/" + month + "/" + year;
                                defaultMode = false;
                                return session.success();
                            case ActivityDetector.ENTITY_END:
                                year = entity.getValue().substring(0, 4);
                                month = entity.getValue().substring(5, 7);
                                date = entity.getValue().substring(8);
                                Log.d(TAG, "Year is " + year);
                                end = date + "/" + month + "/" + year;
                                defaultMode = false;
                                return session.success();
                        }
                        return super.onEntityResolved(entity, session);
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_REQUEST_ACCOUNT_STATEMENT)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent,
                                                      SlangSession slangSession) {
                        Intent intent = new Intent(appContext, Statement.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_FUND_TRANSFER)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent,
                                                      SlangSession slangSession) {
                        Intent intent = new Intent(appContext, MoneyTransfer.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(ActivityDetector.ENTITY_PAYEE, payee);
                        intent.putExtra(ActivityDetector.ENTITY_AMOUNT, amount);
                        intent.putExtra(ActivityDetector.ENTITY_PAYMENT, payment);
                        intent.putExtra(ActivityDetector.ENTITY_DATE, date);
                        appContext.startActivity(intent);
                        if (!(amount == 0 && payment.isEmpty() && payee.isEmpty() &&
                                date.isEmpty()))
                            slangResolvedIntent
                                    .getCompletionStatement()
                                    .overrideAffirmative(
                                            getCompletionPrompt(ActivityDetector.TRANSFER_MODE)
                                    );
                        return slangSession.success();
                    }

                    @Override
                    public SlangSession.Status onIntentResolutionBegin(SlangResolvedIntent intent,
                                                                       SlangSession session) {
                        amount = 0;
                        payment = "";
                        payee = "";
                        date = "";
                        return super.onIntentResolutionBegin(intent, session);
                    }

                    @Override
                    public SlangSession.Status onEntityResolved(SlangEntity entity,
                                                                SlangSession session) {
                        switch (entity.getName()) {
                            case ActivityDetector.ENTITY_AMOUNT:
                                amount = Integer.valueOf(entity.getValue());
                                return session.success();
                            case ActivityDetector.ENTITY_PAYEE:
                                payee = entity.getValue();
                                return session.success();
                            case ActivityDetector.ENTITY_PAYMENT:
                                payment = entity.getValue().toUpperCase();
                                return session.success();
                            case ActivityDetector.ENTITY_DATE:
                                date = entity.getValue();
                                return session.success();
                            default:
                                return super.onEntityResolved(entity, session);
                        }
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_PAY_BILLS)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent,
                                                      SlangSession slangSession) {
                        if(mode.isEmpty()) {
                            Intent intent = new Intent(appContext, Bills.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            appContext.startActivity(intent);
                            return slangSession.success();
                        }
                        else {
                            Intent intent = new Intent(appContext, BillPayment.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ActivityDetector.PAYMENT_MODE, mode);
                            if (amount > 0)
                                intent.putExtra(ActivityDetector.ENTITY_AMOUNT, amount);
                            else
                                slangResolvedIntent
                                        .getCompletionStatement()
                                        .overrideAffirmative(
                                                getCompletionPrompt(ActivityDetector.PAYMENT_MODE)
                                        );
                            appContext.startActivity(intent);
                            return slangSession.success();
                        }
                    }

                    @Override
                    public SlangSession.Status onIntentResolutionBegin(SlangResolvedIntent intent, SlangSession session) {
                        mode = "";
                        amount = 0;
                        return super.onIntentResolutionBegin(intent, session);
                    }

                    @Override
                    public SlangSession.Status onEntityResolved(SlangEntity entity,
                                                                SlangSession session) {
                        Log.d(TAG, "Entity value " + entity.getValue());
                        if (entity.getName().equals(ActivityDetector.ENTITY_BILL)) {
                            switch (entity.getValue()) {
                                case ActivityDetector.ENTITY_VALUE_ELEC:
                                    mode = ActivityDetector.PAYMENT_ELEC;
                                    return session.success();
                                case ActivityDetector.ENTITY_VALUE_WATER:
                                    mode = ActivityDetector.PAYMENT_WATER;
                                    return session.success();
                                case ActivityDetector.ENTITY_VALUE_BROADBAND:
                                    mode = ActivityDetector.PAYMENT_BROADBAND;
                                    return session.success();
                                case ActivityDetector.ENTITY_VALUE_POSTPAID:
                                    mode = ActivityDetector.PAYMENT_POST;
                                    return session.success();
                            }
                        } else if(entity.getName().equals(ActivityDetector.ENTITY_AMOUNT)) {
                            amount = Integer.valueOf(entity.getValue());
                            Log.d(TAG, "Amount value is " + amount);
                            return session.success();
                        }
                        return super.onEntityResolved(entity, session);
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_ORDER_CHEQUE)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent,
                                                      SlangSession slangSession) {
                        Intent intent = new Intent(appContext, OrderCheque.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(
                                ActivityDetector.ORDER_CHEQUE_MODE,
                                ActivityDetector.ORDER_CHEQUE_SLANG
                        );
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_CONTACT_US)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent,
                                                      SlangSession slangSession) {
                        Intent intent = new Intent(appContext, CustomerCare.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });
    }

    private static String getCompletionPrompt(String mode) {
        switch (mode){
            case ActivityDetector.PAYMENT_MODE:
            case ActivityDetector.VIEW_STATEMENT_MODE:
            case ActivityDetector.TRANSFER_MODE:
                return "Please enter the remaining details to proceed.";
        }
        return null;
    }
}

