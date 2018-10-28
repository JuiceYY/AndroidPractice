package cn.istary.material.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.istary.material.R;
import cn.istary.material.adapter.Fruit;
import cn.istary.material.adapter.FruitAdapter;

public class ListViewTest1Activity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private ArrayList<Fruit> fruitArrayList = new ArrayList<>();
    private ListView listView;
    FruitAdapter fruitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_test1);
        initFruit();
        listView = findViewById(R.id.listview_test1);
        fruitAdapter = new FruitAdapter(ListViewTest1Activity.this, R.layout.fruit_item, fruitArrayList);
        listView.setAdapter(fruitAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitArrayList.get(position);
                Toast.makeText(ListViewTest1Activity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnScrollListener(this);

    }

    private void initFruit(){
        for(int i = 0; i < 20; i++){
            Fruit fruit = new Fruit("第"+(i+1)+"个水果", R.mipmap.ic_launcher_round);
            fruitArrayList.add(fruit);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
            case SCROLL_STATE_FLING:
                Toast.makeText(this, "正在惯性滑动", Toast.LENGTH_SHORT).show();
                break;
            case SCROLL_STATE_IDLE:
                Toast.makeText(this, "停止滑动", Toast.LENGTH_SHORT).show();
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Toast.makeText(this, "手指正在滑动, 加载10个水果", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < 10; i++){
                    Fruit fruit = new Fruit("新增"+i+"水果", R.mipmap.ic_launcher_round);
                    fruitArrayList.add(fruit);
                }
                fruitAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
