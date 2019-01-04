package com.example.akanshisrivastava.bankingapp.slang;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import in.slanglabs.platform.application.ISlangApplicationStateListener;
import in.slanglabs.platform.application.SlangApplication;
import in.slanglabs.platform.application.SlangApplicationUninitializedException;

public class VoiceInterface {

    //static private Handler handler;
    static Application appContext;
    private static final String TAG = VoiceInterface.class.getSimpleName();

    public static void init(final Application appContext, String appId, String authKey, final boolean shouldHide) {
        VoiceInterface.appContext = appContext;
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

        //handler = new Handler();
    }

    private static void registerActionsNew() throws SlangApplicationUninitializedException {

                    }
    }

