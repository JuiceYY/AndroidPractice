package cn.istary.material.Activity;

import android.Manifest;
import android.Manifest.permission;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.istary.material.R;
import cn.istary.material.Service.DownloadService;

import static android.Manifest.permission.*;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener{

    private Button startDownloadButton;
    private Button pauseDownloadButton;
    private Button cancelDownloadButton;
    private EditText downloadUrlText;

    private DownloadService.DownloadBinder downloadBinder;

    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        startDownloadButton = findViewById(R.id.start_download_button);
        pauseDownloadButton = findViewById(R.id.pause_download_button);
        cancelDownloadButton = findViewById(R.id.cancel_download_button);
        downloadUrlText = findViewById(R.id.download_url_text);

        startDownloadButton.setOnClickListener(this);
        pauseDownloadButton.setOnClickListener(this);
        cancelDownloadButton.setOnClickListener(this);

        downloadUrl = downloadUrlText.getText().toString();

        /**
         * 绑定服务
         * 为了使Service既可以脱离ui运行， 又可以与前台进行数据交互， 所以要同时用两种方式启动Service
         * startService使服务可以脱离ui执行
         * bindService使服务可以与Activity交互数据
         */
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);

        if(ContextCompat.checkSelfPermission(DownloadActivity.this, WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DownloadActivity.this,
                    new String[] {permission. WRITE_EXTERNAL_STORAGE}, 1);
        }

    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //在这里要让Activity能得到Service的实例
            //接下来就可以通过downloadBinder调用Service中的方法
            downloadBinder = (DownloadService.DownloadBinder) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_download_button:
                downloadBinder.startDownload(downloadUrl);
                break;
            case R.id.pause_download_button:
                downloadBinder.pausedDownload();
                break;
            case R.id.cancel_download_button:
                downloadBinder.cancelDownload();
                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission denied, cannot download", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            default:
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }
}
