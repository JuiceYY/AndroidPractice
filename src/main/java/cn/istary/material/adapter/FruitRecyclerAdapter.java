package cn.istary.material.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.istary.material.R;

public class FruitRecyclerAdapter extends RecyclerView.Adapter<FruitRecyclerAdapter.ViewHolder> {

    private List<Fruit> fruitList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        //这个是每一项的view, 实现点击事件
        public final View mView;

        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            fruitImage = itemView.findViewById(R.id.listview_fruit_image);
            fruitName = itemView.findViewById(R.id.listview_fruit_name);
            mView = itemView;
        }
    }

    public FruitRecyclerAdapter(List<Fruit> fruitList, Context mContext) {
        this.fruitList = fruitList;
        this.mContext = mContext;
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
    public void onBindViewHolder(@NonNull FruitRecyclerAdapter.ViewHolder holder, final int position) {
        //用于对RecyclerView的子项数据进行赋值
        //在每个子项被滚动到屏幕内的时候执行
        Fruit fruit = fruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitImage.setImageResource(fruit.getImageId());
        //在这里可以通过viewholder获取view对象然后设置onclicklistener
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了第"+position+"项", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }
}
