package cn.istary.material.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.istary.material.R;

/**
 * 动态添加碎片主要分为5步：
 * 1. 创建待添加的碎片实例
 * 2. 获取FragmentManager， 在活动中可以直接调用getSupportFragmentManager（）方法得到
 * 3. 开启一个事务， 通过调用beginTransaction（）方法开启
 * 4. 向容器内添加或替换碎片， 一般使用replace（）方法实现，需要传入容器的id和待添加的碎片实例
 * 5. 提交事务， 调用commit（）完成
 *
 */


public class FragmentTest extends AppCompatActivity {

    private Button fragmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        fragmentButton = findViewById(R.id.fragment_test_button1);

        fragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AnotherBottomFragment());
            }
        });

    }

    private void replaceFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bottom_fragment, fragment);
        transaction.commit();

    }
}
