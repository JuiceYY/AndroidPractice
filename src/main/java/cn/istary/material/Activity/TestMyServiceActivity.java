package cn.istary.material.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.health.ServiceHealthStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.istary.material.R;
import cn.istary.material.Service.MyService;

public class TestMyServiceActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bindServiceButton;
    private Button unbindServiceButton;

    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        /**
         * onServiceConnected和onServiceDisconnected分别在活动与服务绑定及断开连接时使用
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /**
             * 通过 向下转型得到DownloadBinder实例，现在我们可以在活动中根据具体场景来调用DownloadBinder
             * 中的任何public方法，实现了指挥服务干什么的功能
             */
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_my_service);

        bindServiceButton = findViewById(R.id.bind_service_button);
        unbindServiceButton = findViewById(R.id.unbind_service_button);

        bindServiceButton.setOnClickListener(this);
        unbindServiceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /**
             * 绑定与解绑服务
             */
            case R.id.bind_service_button:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service_button:
                unbindService(serviceConnection);
                break;

        }
    }
}
