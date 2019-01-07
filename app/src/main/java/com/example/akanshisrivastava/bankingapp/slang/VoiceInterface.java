package com.example.akanshisrivastava.bankingapp.slang;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.example.akanshisrivastava.bankingapp.Accounts;
import com.example.akanshisrivastava.bankingapp.CustomerCare;
import com.example.akanshisrivastava.bankingapp.MoneyTransfer;
import com.example.akanshisrivastava.bankingapp.Services;
import com.example.akanshisrivastava.bankingapp.Statement;

import in.slanglabs.platform.application.ISlangApplicationStateListener;
import in.slanglabs.platform.application.SlangApplication;
import in.slanglabs.platform.application.SlangApplicationUninitializedException;
import in.slanglabs.platform.application.actions.DefaultResolvedIntentAction;
import in.slanglabs.platform.session.SlangResolvedIntent;
import in.slanglabs.platform.session.SlangSession;
import in.slanglabs.platform.ui.SlangScreenContext;

public class VoiceInterface {

    private static Application appContext;
    private static final String TAG = VoiceInterface.class.getSimpleName();

    public static void init(final Application context, String appId, String authKey, final boolean shouldHide) {
        appContext = context;
        Log.d(TAG, "Calling init");
        SlangApplication.initialize(appContext, appId, authKey, new ISlangApplicationStateListener() {
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
                        final Activity activity = SlangScreenContext.getInstance().getCurrentActivity();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "View Recent Transactions Feature not yet included", Toast.LENGTH_LONG).show();
                            }
                        });
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_VIEW_ACCOUNT_STATEMENT)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        final Activity activity = SlangScreenContext.getInstance().getCurrentActivity();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "View Account Statement Feature not yet included", Toast.LENGTH_LONG).show();
                            }
                        });
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
                        appContext.startActivity(intent);
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_PAY_BILLS)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        final Activity activity = SlangScreenContext.getInstance().getCurrentActivity();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "Pay Bills Feature not yet included", Toast.LENGTH_LONG).show();
                            }
                        });
                        return slangSession.success();
                    }
                });

        SlangApplication.getIntentDescriptor(ActivityDetector.INTENT_ORDER_CHEQUE)
                .setResolutionAction(new DefaultResolvedIntentAction() {
                    @Override
                    public SlangSession.Status action(SlangResolvedIntent slangResolvedIntent, SlangSession slangSession) {
                        Intent intent = new Intent(appContext, Services.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appContext.startActivity(intent);
                        final Activity activity = SlangScreenContext.getInstance().getCurrentActivity();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "Order Cheque Feature not yet included", Toast.LENGTH_LONG).show();
                            }
                        });
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
                        final Activity activity = SlangScreenContext.getInstance().getCurrentActivity();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "Customer Support Feature not yet included", Toast.LENGTH_LONG).show();
                            }
                        });
                        return slangSession.success();
                    }
                });
    }
}

