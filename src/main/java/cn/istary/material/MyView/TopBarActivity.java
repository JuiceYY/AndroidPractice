package cn.istary.material.MyView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.istary.material.R;

public class TopBarActivity extends AppCompatActivity implements TopBar.TopBarClickListener {

    private TopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_bar);

        topBar = findViewById(R.id.topBar);
        topBar.setOnTopBarClickListener(this);
    }

    @Override
    public void leftClick() {
        Toast.makeText(TopBarActivity.this, "Left button clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void rightClick() {
        Toast.makeText(TopBarActivity.this, "Right button clicked", Toast.LENGTH_SHORT).show();
    }
}
