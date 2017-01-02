package com.effigrity.ayurvedichomemedicine.free;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.effigrity.Utilities.CommonData;
import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class DashboardActivity extends FragmentActivity {
    static final int VR_REQUEST = 999;
    private static final String FIREBASE_APP = "https://api-project-220529514935.firebaseio.com/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        init();
        super.onResume();
    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("All");

        Log.d("AndroidBash", "Subscribed");
        //Toast.makeText(DashboardActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        Log.d("AndroidBash","push tokan"+ token);
        //Toast.makeText(DashboardActivity.this, token, Toast.LENGTH_SHORT).show();
    }

    public void init() {
        CommonData.intialize();
        CommonData.prepareRemedyList();
        CommonData.typeFace = Typeface.createFromAsset(getAssets(), "arialbd.ttf");

        subscribeToPushService();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            CommonData.sendNotification(getApplicationContext(),"Data Not found");
        }*/
    }

    protected void onStart() {
        super.onStart();
    }

    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == 8 && menu != null && menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", new Class[]{Boolean.TYPE});
                m.setAccessible(true);
                m.invoke(menu, new Object[]{Boolean.valueOf(true)});
            } catch (NoSuchMethodException e) {
            } catch (Exception e2) {
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                onBackPressed();
                break;
            case R.id.rating /*2131034200*/:
                onClickGiveRating(getCurrentFocus());
                break;
            case R.id.like /*2131034201*/:
                onClickFacebook(getCurrentFocus());
                break;
            case R.id.googleplus /*2131034202*/:
                onClickGooglePlus(getCurrentFocus());
                break;
            case R.id.twitter /*2131034203*/:
                onClickTwitter(getCurrentFocus());
                break;
            case R.id.aboutUS /*2131034204*/:
                onClickAbout(getCurrentFocus());
                break;
        }
        return true;
    }

    protected void onStop() {
        super.onStop();
    }

    public void onClickTwitter(View v) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://twitter.com/natureayurveda")));
        } catch (ActivityNotFoundException e) {
            toast_short("Browser not found.");
        }
    }

    public void onClickFacebook(View v) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/pages/Nature-and-Ayurveda/1503426476579531")));
        } catch (ActivityNotFoundException e) {
            toast_short("Browser not found.");
        }
    }

    public void onClickGooglePlus(View v) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://plus.google.com/u/0/110601028167984069056/")));
        } catch (ActivityNotFoundException e) {
            toast_short("Browser not found.");
        }
    }

    public void onClickContact(View v) {
    }

    public void takeVoiceInput(View v) {
        if (getPackageManager().queryIntentActivities(new Intent("android.speech.action.RECOGNIZE_SPEECH"), 0).size() != 0) {
            listenToSpeech();
        } else {
            toast_short("Oops - Speech recognition not supported!");
        }
    }

    private void listenToSpeech() {
        Intent listenIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        listenIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "en-UK");
        startActivityForResult(listenIntent, VR_REQUEST);
    }

    public void onClickHome(View v) {
        onBackPressed();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void BackActivity(View v) {
        onBackPressed();
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onClickAbout(View v) {
        startActivity(new Intent(getApplicationContext(), AboutUs.class));
    }

    public void onClickGiveRating(View v) {
        String appName = getPackageName();
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + appName)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    public void goHome(Context context) {
        Intent intent = new Intent(context, MainSliderActivity.class);
        intent.setFlags(67108864);
        context.startActivity(intent);
    }

    public void setTitleFromActivityLabel(int textViewId) {
        TextView tv = (TextView) findViewById(textViewId);
        if (tv != null) {
            tv.setText(getTitle());
        }
    }

    public void toast_long(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void toast_short(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void trace(String msg) {
        Log.d("Demo", msg);
        toast_short(msg);
    }

    public void onFocusChange(View v, boolean hasFocus) {
    }

    private void registerDevice() {
        //Creating a firebase object
        Firebase firebase = new Firebase(FIREBASE_APP);

        //Pushing a new element to firebase it will automatically create a unique id
        Firebase newFirebase = firebase.push();

        //Getting the unique id generated at firebase
        String uniqueId = newFirebase.getKey();
        //Creating a map to store name value pair
        Map<String, String> val = new HashMap<>();

        //pushing msg = none in the map
        val.put("senderId", uniqueId);

        //saving the map to firebase
        newFirebase.setValue(val);

        //Finally we need to implement a method to store this unique id to our server
        sendIdToServer(uniqueId);
    }

    private void sendIdToServer(String uniqueId) {
    }
}
