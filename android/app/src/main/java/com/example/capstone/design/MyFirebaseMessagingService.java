package com.example.capstone.design;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private static PowerManager.WakeLock mWakeLock;
    // 메시지 수신
    // 앱이 실행중이면
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TAG, "앱 내에 실행");
        Log.d(TAG,remoteMessage.getNotification().getBody());
        Log.d(TAG,remoteMessage.getData().get("title"));
        Log.d(TAG,remoteMessage.getData().get("Content"));

        //remoteMessage.getData().get("title")

        // notification instance 추가하고 firebase에 저장하기
        Notification notification = new Notification(remoteMessage.getData().get("Content"), new Date().toString(), remoteMessage.getData().get("title"));
        String hash = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("Notification").child(hash).setValue(notification);


        //wake up screen

        //
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String message = data.get("Notification");

//        acquireWakeLock(this);
        //앱 동작중 일 때 실행
        sendNotification(title, message);
//        releaseWakeLock();
    }

    public void sendNotification(String title, String message) {
        Log.i(TAG, "sendNotification");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_info))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public static void acquireWakeLock(Context context){
        Log.i(TAG, "acquireWakeLock : "+mWakeLock);
        if(mWakeLock!=null) return;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,TAG);
        mWakeLock.acquire();
//        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,TAG);
//        mWakeLock.acquire();
        Log.i(TAG, "mWakeLock mWakeLock : "+mWakeLock);

    }

    public static void releaseWakeLock(){
        if(mWakeLock != null){
            mWakeLock.release();
            mWakeLock = null;
        }
        Log.i(TAG, "mWakeLock : "+mWakeLock);
    }
}
