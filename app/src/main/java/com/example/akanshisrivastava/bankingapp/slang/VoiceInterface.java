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
    private static boolean callAction;
    private static int amount;
    private static String date, payee, payment;

    public static void init(final Application context, String appId, String authKey, final boolean shouldHide) throws SlangLocaleException {
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
                Toast.makeText(appContext, "Not able to initialize", Toast.LENGTH_LONG).show();
                Log.d(TAG, "________________ " + failureReason);
            }
        });
    }

    private static void registerActionsNew() throws SlangApplicationUninitializedException {
        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_VIEW_ACCOUNT_SUMMARY)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        Intent intent = new Intent(appContext, Accounts.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_VIEW_RECENT_TRANSACTIONS)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        Intent intent = new Intent(appContext, RecentTransactions.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_VIEW_ACCOUNT_STATEMENT)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        Intent intent = new Intent(appContext, AccountStatement.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_REQUEST_ACCOUNT_STATEMENT)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        Intent intent = new Intent(appContext, Statement.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_FUND_TRANSFER)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        Intent intent = new Intent(appContext, MoneyTransfer.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(ActivityDetector.ENTITY_PAYEE, payee);
                        intent.putExtra(ActivityDetector.ENTITY_AMOUNT, amount);
                        intent.putExtra(ActivityDetector.ENTITY_PAYMENT, payment);
                        intent.putExtra(ActivityDetector.ENTITY_DATE, date);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }

                    @Override
                    public SlangSession.Status onIntentResolutionBegin(SlangResolvedIntent intent, SlangSession session) {
                        amount = 0;
                        payment = "";
                        payee = "";
                        date = "";
                        return super.onIntentResolutionBegin(intent, session);
                    }

                    @Override
                    public SlangSession.Status onEntityResolved(SlangEntity entity, SlangSession session) {
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
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        if (callAction) {
                            Intent intent = new Intent(appContext, Bills.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            appContext.startActivity(intent);
                        }
                        return slangSession.success();
                    }

                    @Override
                    public SlangSession.Status onIntentResolutionBegin(SlangResolvedIntent intent, SlangSession session) {
                        callAction = true;
                        return super.onIntentResolutionBegin(intent, session);
                    }

                    @Override
                    public SlangSession.Status onEntityResolved(SlangEntity entity, SlangSession session) {
                        if(entity.getName().equals(ActivityDetector.ENTITY_BILL)) {
                            Intent intent;
                            switch (entity.getValue().toLowerCase()) {
                                case ActivityDetector.ENTITY_VALUE_ELEC:
                                    intent = new Intent(appContext, BillPayment.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_ELEC);
                                    callAction = false;
                                    appContext.startActivity(intent);
                                    return session.success();
                                case ActivityDetector.ENTITY_VALUE_WATER:
                                    intent = new Intent(appContext, BillPayment.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_WATER);
                                    callAction = false;
                                    appContext.startActivity(intent);
                                    return session.success();
                                case ActivityDetector.ENTITY_VALUE_BROADBAND:
                                    intent = new Intent(appContext, BillPayment.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_BROADBAND);
                                    callAction = false;
                                    appContext.startActivity(intent);
                                    return session.success();
                                case ActivityDetector.ENTITY_VALUE_POSTPAID:
                                    intent = new Intent(appContext, BillPayment.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_POST);
                                    callAction = false;
                                    appContext.startActivity(intent);
                                    return session.success();
                            }
                        }
                        return super.onEntityResolved(entity, session);
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_ORDER_CHEQUE)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        Intent intent = new Intent(appContext, OrderCheque.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_CONTACT_US)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        Intent intent = new Intent(appContext, CustomerCare.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });
    }
}

