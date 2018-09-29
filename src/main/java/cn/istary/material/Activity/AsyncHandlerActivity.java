package cn.istary.material.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.istary.material.R;

public class AsyncHandlerActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton floatingActionButton;
    private TextView lifeTime;
    private int nowLifeTime = 100;

    public static final int UPDATE_TEXT = 1;
    public static final int UNDO_ADD = 2;


    //handler对象处理具体Message
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TEXT:
                    lifeTime.setText(nowLifeTime + "s");
                    break;
                case UNDO_ADD:
                    lifeTime.setText(nowLifeTime+ "s");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_handler);
        floatingActionButton = findViewById(R.id.async_floating_button);
        lifeTime = findViewById(R.id.life_time);

        floatingActionButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.async_floating_button:
                //开子线程发送更新ui的消息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
                nowLifeTime++;
                tellUsers(v);
                break;

            default:
                break;

        }
    }
    private void tellUsers(View view){
        Snackbar.make(view, "You have added 1s for the older", Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nowLifeTime --;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what = UNDO_ADD;
                                handler.sendMessage(message);
                            }
                        }).start();
                        Toast.makeText(AsyncHandlerActivity.this, "It's so bad you do so", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
