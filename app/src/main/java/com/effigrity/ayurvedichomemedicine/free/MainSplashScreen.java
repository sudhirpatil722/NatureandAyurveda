package com.effigrity.ayurvedichomemedicine.free;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.effigrity.Utilities.CommonData;

public class MainSplashScreen extends Activity implements OnClickListener {
    TextView tv;
    String action ="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_splash);
        this.tv = (TextView) findViewById(R.id.TextView01);
        this.tv.setOnClickListener(this);
        if(getIntent()!=null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey("action")){
                action = getIntent().getExtras().getString("action");
                //Toast.makeText(MainSplashScreen.this,"Action "+action,Toast.LENGTH_LONG).show();
            }else{
                bundle2string(getIntent().getExtras());
                //Toast.makeText(MainSplashScreen.this,"Key Not found",Toast.LENGTH_LONG).show();
            }
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            CommonData.sendNotification(this,"fever","also known as a high or a high temperature ");
        }*/
        CommonData.intialize();
        CommonData.prepareRemedyList();
        CommonData.typeFace = Typeface.createFromAsset(getAssets(), "arialbd.ttf");
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(4000);
                    //AnonymousClass1.sleep(4000);
                    Intent in = new Intent(MainSplashScreen.this.getBaseContext(), MainActivity.class);
                    if(!TextUtils.isEmpty(action)){
                        in.putExtra("notification_action",action);
                    }
                    MainSplashScreen.this.startActivity(in);

                    MainSplashScreen.this.finish();
                } catch (Exception e) {
                }
            }
        }.start();
    }

    public void sendNotification() {
        String randomQuote = "Android OS";

        Intent showFullQuoteIntent = new Intent(this, MainSplashScreen.class);
        showFullQuoteIntent.putExtra("notification_action", randomQuote);

        // both of these approaches now work: FLAG_CANCEL, FLAG_UPDATE; the uniqueInt may be the real solution.
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(randomQuote)
                .setSmallIcon(android.R.drawable.ic_menu_view)
                .setContentTitle(randomQuote)
                .setContentText(randomQuote)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
            Log.d("FCM Service",key + " => " + bundle.get(key));
        }
        string += " }Bundle";
        return string;
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.effigrity.com/")));
        } catch (ActivityNotFoundException e) {
        }
    }
}
