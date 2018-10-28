package cn.istary.material.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.istary.material.R;

public class FruitRecyclerAdapter extends RecyclerView.Adapter<FruitRecyclerAdapter.ViewHolder> {

    private List<Fruit> fruitList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            fruitImage = itemView.findViewById(R.id.listview_fruit_image);
            fruitName = itemView.findViewById(R.id.listview_fruit_name);
        }
    }

    public FruitRecyclerAdapter(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }

    @NonNull
    @Override
    public FruitRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建ViewHolder实例，并传入构造函数
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FruitRecyclerAdapter.ViewHolder holder, int position) {
        //用于对RecyclerView的子项数据进行赋值
        //在每个子项被滚动到屏幕内的时候执行
        Fruit fruit = fruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitImage.setImageResource(fruit.getImageId());
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }
}
