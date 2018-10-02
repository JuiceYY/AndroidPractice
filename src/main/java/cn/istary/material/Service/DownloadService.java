package cn.istary.material.Service;
/**
 * 在服务中实例化DownloadTask类，保证其可以一直运行
 * 在服务中实现DownloadListener接口，对下载的不同状态执行不同操作
 *
 * Activity  <==> Service <==> DownloadTask
 *                         ||
 *                   DownloadListener
 *
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

import cn.istary.material.Activity.DownloadActivity;
import cn.istary.material.DownloadListerner;
import cn.istary.material.R;
import cn.istary.material.util.DownloadTask;

public class DownloadService extends Service {

    private DownloadTask downloadTask;

    private String downloadUrl;

    private DownloadListerner downloadListerner = new DownloadListerner() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading...", progress));

        }

        @Override
        public void onSuccess() {
            downloadTask = null;

            //下载成功将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.makeText(DownloadService.this, "Download success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTask = null;

            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download failed", -1));
            Toast.makeText(DownloadService.this, "Download failed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this, "Download paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Download Canceled", Toast.LENGTH_SHORT).show();
        }
    };
    //private MyService.DownloadBinder mBinder = new MyService.DownloadBinder();
    //没有静态的类不能使用外部类进行.操作，必须用实例来实例化内部类
    private DownloadBinder mBinder = new DownloadBinder();


    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    //Activity通过Binder可以调用service的方法
    //Binder必须声明public， 否则Activity不能使用
    public class DownloadBinder extends Binder{
        public void startDownload(String url){
            if(downloadTask == null){
                downloadUrl = url;
                downloadTask = new DownloadTask(downloadListerner);
                downloadTask.execute(downloadUrl);//?
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(DownloadService.this, "Downloading ...", Toast.LENGTH_SHORT).show();
            }
        }

        public void pausedDownload(){
            if(downloadTask != null){
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload(){
            if(downloadTask != null){
                downloadTask.cancelDownload();
            }else{
                if(downloadUrl != null){
                    //将已下载文件删除
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));//分割字符串得到文件名
                    String directoryName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directoryName + fileName);
                    if(file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Download canceled", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress){
        Intent intent = new Intent(this, DownloadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        if(progress>= 0){
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }

        return builder.build();

    }
}
