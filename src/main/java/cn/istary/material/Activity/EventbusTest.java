package cn.istary.material.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.istary.material.Event.MyEvent;
import cn.istary.material.R;

/**
 * 1. 添加依赖
 * 2. 创建事件类
 * 3. 注册和反注册事件
 * 4. 订阅事件的方法（即接收到事件要进行的操作）
 * 5. 发布事件（发布事件后4中方法执行）
 */

public class EventbusTest extends AppCompatActivity implements View.OnClickListener{

    private Button eventbusPostButton;
    private TextView eventbusChangedTextview;

    private final String MSG = "陈相是个煞笔";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_test);

        eventbusChangedTextview = findViewById(R.id.eventbus_changed_textview);
        eventbusPostButton  = findViewById(R.id.eventbus_post_button);

        //注册事件总线
        EventBus.getDefault().register(this);

        eventbusPostButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.eventbus_post_button:
                postEvent(MSG);
                break;
        }
    }


    //反注册eventbus
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //发布事件
    private void postEvent(String msg){
        MyEvent myEvent = new MyEvent(msg);
        EventBus.getDefault().post(myEvent);

    }

    //订阅事件，Textview改变, 必须public
    @Subscribe
    public void onPostingEvent(MyEvent myEvent){
        eventbusChangedTextview.setText("I have received a event, content ==> " + myEvent.getMsg() );
    }
}
