package cn.istary.material;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DataSaveDemoActivity extends BaseActivity implements View.OnClickListener{

    private Button fileSaveButton;
    private Button shareSaveButton;
    private Button forceofflineButton;
    private EditText dataEdit1;
    private EditText dataEdit2;
    private Button restoreButton;
    private TextView restoreText;
    private Button gotoWebview;
    private Button gotoHTTP;

    private String data;
    private String data2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasave_demo);

        fileSaveButton = findViewById(R.id.file_save_button);
        shareSaveButton = findViewById(R.id.share_save_button);
        forceofflineButton = findViewById(R.id.filedemo_forceoffline);
        dataEdit1 = findViewById(R.id.dataEdit1);
        dataEdit2 = findViewById(R.id.dataEdit2);
        restoreButton = findViewById(R.id.restore_button);
        restoreText = findViewById(R.id.restore_text);
        gotoWebview = findViewById(R.id.goto_webview);
        gotoHTTP = findViewById(R.id.goto_http);

        //所有button对应一个监听，实现OnClickListener接口
        fileSaveButton.setOnClickListener(this);
        shareSaveButton.setOnClickListener(this);
        forceofflineButton.setOnClickListener(this);
        restoreButton.setOnClickListener(this);
        gotoWebview.setOnClickListener(this);
        gotoHTTP.setOnClickListener(this);

        data = dataEdit1.getText().toString();
        data2 = dataEdit2.getText().toString();

    }

    //实现OnClickListener接口重写onClick

    //为什么用button不能保存？？？
    @Override
    public void onClick(View view) {
        //通过view.getId()得到点击的按钮的id
        switch (view.getId()){
            case R.id.filedemo_forceoffline:
                Toast.makeText(DataSaveDemoActivity.this, "forceofflinebutton clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("cn.istary.broadcastforceoffline.FORCE_OFFLINE");
                sendBroadcast(intent);
                break;
            case R.id.file_save_button:
                Toast.makeText(DataSaveDemoActivity.this, "filesavebutton clicked", Toast.LENGTH_SHORT).show();
                saveWithFile(data);
                break;
            case R.id.share_save_button:
                Toast.makeText(DataSaveDemoActivity.this, "sharedpreferencesavebutton clicked", Toast.LENGTH_SHORT).show();
                saveWithSharedpreference(data);
                break;
            case R.id.restore_button:
                restoreData();
                break;
            case R.id.goto_webview:
                Intent intent1 = new Intent(DataSaveDemoActivity.this, WebviewActivity.class);
                startActivity(intent1);
            case R.id.goto_http:
                Intent intent2 = new Intent(DataSaveDemoActivity.this, HttpActivity.class);
                startActivity(intent2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputtext = dataEdit1.getText().toString();
        saveWithFile(inputtext);
    }

    private void saveWithFile(String data){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("data", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        try {
            bufferedWriter.write(data);
            Toast.makeText(DataSaveDemoActivity.this, "the string has been saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void saveWithSharedpreference(String data){
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("name", "Tom");
        editor.putInt("age", 28);
        editor.putBoolean("married", false);
        editor.putString("edittext2", data2);
        editor.apply();
    }

    private void restoreData(){
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        int age = sharedPreferences.getInt("age", 0);
        boolean married = sharedPreferences.getBoolean("married", false);
        String string = sharedPreferences.getString("edittext2", "");
        restoreText.setText(name+age+married+string);
    }

}
