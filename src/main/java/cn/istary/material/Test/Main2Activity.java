package cn.istary.material.Test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import javax.inject.Inject;

import cn.istary.material.R;

public class Main2Activity extends AppCompatActivity {

    private final String TAG = "Main2Activity";
    private boolean isTextView = false;

    @Inject
    User mUser;

    @Inject
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //DaggerMain2Component.builder().build().inject(this);
        DaggerMain2Component.builder()
                .textViewModule(new TextViewModule(this))
                .build()
                .inject(this);

        if(textView != null){
            isTextView = true;
        }


        Log.d(TAG, "onCreate" + mUser.name);
        Log.d(TAG, "textview?" + isTextView);
    }
}
