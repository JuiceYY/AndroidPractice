package cn.istary.material;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    private  ForceOfflineReceiver forceOfflineReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    //活动拉起注册广播接受器
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.istary.broadcastforceoffline.FORCE_OFFLINE");
        forceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(forceOfflineReceiver, intentFilter);
    }

    //强制下线时活动失去焦点， 取消注册广播接收器
    @Override
    protected void onPause() {
        super.onPause();
        if(forceOfflineReceiver != null){
            unregisterReceiver(forceOfflineReceiver);
            forceOfflineReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class ForceOfflineReceiver extends BroadcastReceiver{

        //强制下线功能
        @Override
        public void onReceive(final Context context, final Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("WARNING");
            builder.setMessage("You are forced to be offline. Please try to login again.");
            builder.setCancelable(false); //设置对话框不可按back键取消
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAllActivity();
                    Toast.makeText(context, "You are offline now", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    context.startActivity(intent1);
                }
            });
            builder.show();
        }
    }
}
