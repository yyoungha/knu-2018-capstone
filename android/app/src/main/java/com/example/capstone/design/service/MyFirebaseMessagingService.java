package com.example.capstone.design.service;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.capstone.design.MainActivity;
import com.example.capstone.design.Notification;
import com.example.capstone.design.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    // 앱이 실행중이면
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // notification instance 추가하고 firebase에 저장하기
        Notification notification = new Notification(remoteMessage.getData().get("Notification"), new Date().toString(), remoteMessage.getData().get("title"));
        String hash = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("Notification").child(hash).setValue(notification);
        //

        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String message = data.get("Notification");

        sendNotification(title, message);

    }

    public void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        //현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를 없앤다.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //일회용 인텐트 같은 개념
        //FLAG_UPDATE_CURRENT : 현재 생성된 팬딩 인탠트가 존재하면 INTENT의 내용을 변경함
        //FLAG_CANCEL_CURRENT : 이전에 생성된 인탠트를 취소하고 새롭게 하나 만듬
        //FLAG_NO_CREATE : 현재 생성된 인탠트를 반환한다
        //FLAG_ONE_SHOT : 이 플래그를 사용해 생성된 팬딩 인탠트는 한번밖에 사용할 수 없다.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        //notification setting
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_info))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent).setChannelId(getString(R.string.channel_id));

        //send push message
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
