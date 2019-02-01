package com.example.akanshisrivastava.bankingapp;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.example.akanshisrivastava.bankingapp.slang.VoiceInterface;

public class SlangMockApplication extends Application {
    private static SlangMockApplication instance;
    private static Context appContext;
    private static final String TAG = SlangMockApplication.class.getSimpleName();

    public static SlangMockApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context mAppContext) {
        this.appContext = mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.d(TAG,"inside OnCreate, calling init now");
        // Create a local Client object
        VoiceInterface.init(this);

        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

}
