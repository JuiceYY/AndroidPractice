package cn.istary.material.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.istary.material.R;
import cn.istary.material.adapter.Fruit;
import cn.istary.material.adapter.FruitRecyclerAdapter;

public class RecyclerView1Activity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view1);
        initFruit();//现在list有数据了
        recyclerView = findViewById(R.id.recycler_view1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FruitRecyclerAdapter adapter = new FruitRecyclerAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }

    private void initFruit(){
        for(int i = 0; i < 200; i++){
            Fruit fruit = new Fruit("第"+(i+1)+"个水果", R.mipmap.ic_launcher_round);
            fruitList.add(fruit);
        }
    }
}
