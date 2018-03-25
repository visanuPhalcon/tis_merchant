package com.tis.merchant.app.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


import com.tis.merchant.app.R;
import com.tis.merchant.app.openapp.SplashScreenActivity;

import java.util.Map;
/**
 * Created by Nanthakorn on 8/21/2017
 */

//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//    private String TAG = "dam";
//    public void onMessageReceived(RemoteMessage remoteMessage){
//        super.onMessageReceived(remoteMessage);
//        RemoteMessage.Notification notification = remoteMessage.getNotification();
//        Map<String,String>data = remoteMessage.getData();
//        Log.d(TAG, "onMessageReceived");
//        sendNotification(notification, data);
//    }
//
//    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
//        String title = data.get("title");
//        String body = data.get("body");
//        Intent intent = new Intent(this, SplashScreenActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
//
//        /*if(APP_NOT_DESTROYED){
//            if(login){
//                intent = new Intent(this, MainActivity.class);
//                intent.putExtra("Page", 8);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            }else {
//                GO_NOTI_PAGE = true;
//                intent = new Intent(this, MainActivity.class);
//                intent.putExtra("Page", 16);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            }
//        }else {
//            GO_NOTI_PAGE = true;
//            intent = new Intent(this, SplashActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }*/
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText(body))
//                .setAutoCancel(true)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setContentIntent(pendingIntent)
//                .setContentInfo(title)
//                .setPriority(Notification.PRIORITY_HIGH)
//                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), getSmallIcon()))
//                .setSmallIcon(getSmallIcon());
//
//
//
//        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
//        notificationBuilder.setLights(Color.RED, 1000, 300);
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notificationBuilder.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        }
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, notificationBuilder.build());
//    }
//
//    public int getSmallIcon() {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ?
//                R.mipmap.ic_launcher : R.mipmap.ic_launcher;
//    }
//}
