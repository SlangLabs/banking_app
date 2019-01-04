package com.example.akanshisrivastava.bankingapp.slang;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

public class SlangMockApplication extends Application {
    private static SlangMockApplication instance;
    private static Context appContext;

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
        VoiceInterface.init(this,"9daf450b6eea48149565f7a7d7b63c47",
                "d38d14ebfe0446b786994109f6cbef43", false);

        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

}
