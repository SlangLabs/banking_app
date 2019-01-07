package com.example.akanshisrivastava.bankingapp.slang;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

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
        VoiceInterface.init(this,"5671e72eefe54307b5e32fcafdcf02ac",
                "c80525dd5fa146d6a3a1aba91fc5d6b9", false);

        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

}
