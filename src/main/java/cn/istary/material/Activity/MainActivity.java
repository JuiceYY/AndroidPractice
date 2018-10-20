package cn.istary.material.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import cn.istary.material.MyView.ViewBaseActivity;
import cn.istary.material.R;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //网络状态变化broadcast receiver
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.coon.CONNECTIVITY_CHANGE");

        button1 = findViewById(R.id.mainactivity_button1);
        button2 = findViewById(R.id.mainactivity_button2);
        button3 = findViewById(R.id.mainactivity_button3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("MainActivity", "menu中setting被点击");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainactivity_button1:
                Intent intent1 = new Intent(MainActivity.this, DrawerActivity.class);
                startActivity(intent1);
                break;
            case R.id.mainactivity_button2:
                Intent intent2 = new Intent(MainActivity.this, ViewBaseActivity.class);
                startActivity(intent2);
                break;

            case R.id.mainactivity_button3:
                Toast.makeText(MainActivity.this, getString(R.string.activity_main_toast_forbutton3).toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("NetworkChangeReceiver", "network has changed");
             ConnectivityManager connetivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connetivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
