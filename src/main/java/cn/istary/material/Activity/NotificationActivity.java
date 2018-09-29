package cn.istary.material.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.istary.material.R;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton noticeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        noticeButton = findViewById(R.id.notice_floating_button);
        noticeButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //点击通知跳转照相机活动
        Intent intent = new Intent(this, CameraActivity.class);
        /**
         * PendingIntent "延迟执行的Intent"
         * 通过getActivity， getBroadcast, getService 几个方法获取实例
         * 上面的方法都是四个参数
         * 1. Context
         * 2. 0
         * 3. Intent对象，通过这个Intent构造PendingIntent
         * 4. 4个值， 一般传入0
         */
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        switch (v.getId()){
            case R.id.notice_floating_button:
                sendNotice(pendingIntent);
                break;
        }
    }

    protected void sendNotice(PendingIntent pendingIntent){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("This is content title")
                .setContentText("This is a content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)//点击通知行为
                .setAutoCancel(true)//点击后通知自动取消
                .build();
        manager.notify(1, notification);//1是id， 要保证每个通知的id不同
    }
}
