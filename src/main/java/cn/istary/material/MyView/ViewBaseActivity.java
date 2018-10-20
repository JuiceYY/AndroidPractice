package cn.istary.material.MyView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.istary.material.R;

public class ViewBaseActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_base);
        button2 = findViewById(R.id.activity_view_base_button2);
        button1 = findViewById(R.id.activity_view_base_button1);

        button3 = findViewById(R.id.activity_view_base_button3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_view_base_button1:
            //    Intent intent1 = new Intent(ViewBaseActivity.this, MeasureViewActivity.class);
            //    startActivity(intent1);
                break;
            case R.id.activity_view_base_button2:
                Intent intent2 = new Intent(ViewBaseActivity.this, MyTextViewActivity.class);
                startActivity(intent2);
                break;
            case R.id.activity_view_base_button3:

                break;
        }
    }
}
