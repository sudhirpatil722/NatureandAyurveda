package com.effigrity;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by patisu06 on 11-12-2016.
 */

public class Ayurveda extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
