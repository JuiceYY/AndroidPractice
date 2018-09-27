package cn.istary.material.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 服务的生命周期
 * 开启服务的两种方法:
 * 1.调用Context的startService 方法
 * 2.调用Context的bindService方法来获取一个服务的持久链接
 * 停止服务：
 * 1.stopService()或stopSelf()
 * 2.unbindService()
 *
 * 同时用两种方式开启服务，必须同时用两种方式停止，服务才能销毁
 */

public class MyService extends Service {

    private DownloadBinder downloadBinder = new DownloadBinder();

    //不声明public则TestMyServiceActivity活动无法创建实例
    public class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("MyService", "startDownload executed");
            /**
             * Google对Toast的说明中，有一句：
             * “A toast can be created and displayed from an Activity or Service.
             * If you create a toast notification from a Service,
             * it appears in front of the Activity currently in focus.”
             */
            Toast.makeText(getApplicationContext(), "startDownload executed", Toast.LENGTH_SHORT).show();
        }
        public int getProgress(){
            Log.d("MyService", "getProgress executed");
            Toast.makeText(getApplicationContext(), "getProgress executed", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    public MyService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        //throw new UnsupportedOperationException("Not yet implemented");
        return downloadBinder;
    }

    /**
     * 服务创建时调用onCreate
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MySercice", "onCreate exeucted");
        Toast.makeText(getApplicationContext(), "onCreate exeucted", Toast.LENGTH_SHORT).show();
    }

    /**
     * 服务每次启动时调用onStartCommand
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand exeucted");
        Toast.makeText(getApplicationContext(), "onStartCommand exeucted", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 服务销毁时调用onDestory
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestory exeucted");
        Toast.makeText(getApplicationContext(), "onDestory executed", Toast.LENGTH_SHORT).show();
    }
}
