package com.effigrity.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.effigrity.ayurvedichomemedicine.free.MainSplashScreen;
import com.effigrity.ayurvedichomemedicine.free.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM_Service";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //RemoteMessage.Notification ni = remoteMessage.getNotification();

        //message will contain the Push Message
        String message = remoteMessage.getData().get("action");
        //imageUri will contain URL of the image to be displayed with Notification
        String title = remoteMessage.getData().get("title");
        //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
        //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.
        //String TrueOrFlase = remoteMessage.getData().get("AnotherActivity");
        //sendNotification(getApplicationContext(),title,message,ni.getBody());
        sendCustomNotification(title,title,message);
        //sendNotification(getApplicationContext(),"Fever","Fever","MyMessage");
        //notification(title,message);
    }

    private void sendCustomNotification(String title,String action,String message){
        Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);

        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.notification);
        // Set Notification Title
        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title,title);
        remoteViews.setTextViewText(R.id.subtitle,message);
        // Set Notification Text


        Intent notificationIntent = new Intent(this, MainSplashScreen.class);
        notificationIntent.putExtra("action",action);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Log.d(TAG,"Received push message => "+action);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//Assign inbox style notification
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(message);
        bigText.setBigContentTitle(title);
        /*bigText.setSummaryText("By: Author of Lorem ipsum");*/

        String shortmessage = "";
        if(message.length()>50){
            shortmessage = message.substring(0,45)+"...";
        }else{
            shortmessage = message;
        }

//build notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(shortmessage)
                        .setContentIntent(contentIntent)
                        .setLargeIcon(icon1)
                        .setContent(remoteViews)
                        .setStyle(bigText);

// Gets an instance of the NotificationManager service
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//to post your notification to the notification bar
        mNotificationManager.notify(0, mBuilder.build());
    }

    public void sendNotification(Context context,String title,String action,String message){
        Intent notificationIntent = new Intent(context, MainSplashScreen.class);
        notificationIntent.putExtra("action",action);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Log.d(TAG,"Received push message => "+action);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentText(message)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }


    public void notification(String strtitle,String message) {
        /*// Set Notification Title
        String strtitle = getString(R.string.notificationtitle);
        // Set Notification Text
        String strtext = getString(R.string.notificationtext);*/

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, MainSplashScreen.class);
        // Send data to NotificationView Class
        intent.putExtra("action", strtitle);
        /*intent.putExtra("text", message);*/
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.drawable.ic_launcher)
                // Set Ticker Message
                .setTicker(strtitle)
                // Set Title
                .setContentTitle(strtitle)
                // Set Text
                .setContentText(message)
                // Add an Action Button below Notification
                .addAction(R.drawable.ic_launcher, "More", pIntent)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());

    }

}
