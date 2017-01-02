package com.effigrity.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.effigrity.ayurvedichomemedicine.free.MainSplashScreen;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand (Intent intent, int flags, int startId){

        String userID = intent.getStringExtra("notification_action");
        sendNotification(userID);

        return START_STICKY;
    }

    public void sendNotification(String user) {
        String randomQuote = user;

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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
