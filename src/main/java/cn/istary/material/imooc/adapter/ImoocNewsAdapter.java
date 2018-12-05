package cn.istary.material.imooc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.istary.material.R;
import cn.istary.material.imooc.data.bean.ImoocNews;

public class ImoocNewsAdapter extends BaseAdapter {

    private List<ImoocNews> newsList;
    private LayoutInflater mLayoutInflater;
    private int resourceId;

    public ImoocNewsAdapter(Context context, List<ImoocNews> newsList, int resourceId){
        super();
        this.newsList = newsList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.resourceId = resourceId;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.mImageIv = convertView.findViewById(R.id.imoocnews_image);
            viewHolder.mTitleTv = convertView.findViewById(R.id.imoocnews_title);
            viewHolder.mContentTv = convertView.findViewById(R.id.imoocnews_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImageIv.setImageResource(R.mipmap.ic_launcher);
        viewHolder.mTitleTv.setText(newsList.get(position).getTitle());
        viewHolder.mContentTv.setText(newsList.get(position).getContent());
        return convertView;

    }

    class ViewHolder{
        ImageView mImageIv;
        TextView mTitleTv;
        TextView mContentTv;
    }

}
