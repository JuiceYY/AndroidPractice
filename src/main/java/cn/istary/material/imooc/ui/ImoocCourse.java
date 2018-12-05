package cn.istary.material.imooc.ui;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.istary.material.R;
import cn.istary.material.imooc.adapter.ImoocNewsAdapter;
import cn.istary.material.imooc.data.bean.ImoocNews;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ImoocCourse extends AppCompatActivity {

    private List<ImoocNews> newsList;
    private ImoocNewsAdapter adapter;
    private ListView listView;
    private String url = "http://www.imooc.com/api/teacher?type=4&num=30";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imooc_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.imooc_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();;
        ab.setHomeAsUpIndicator(R.drawable.ic_menu1);
        ab.setDisplayHomeAsUpEnabled(true);

        //初始化控件
        listView = findViewById(R.id.imooc_listview);
        new MyAsyncTask().execute(url);
    }


    class MyAsyncTask extends AsyncTask<String, Void, List<ImoocNews>>{

        @Override
        protected List<ImoocNews> doInBackground(String... strings) {
            return readJsonData(strings[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(List<ImoocNews> newsList) {
            super.onPostExecute(newsList);
            adapter = new ImoocNewsAdapter(ImoocCourse.this, newsList, R.id.imoocnews_item);
            listView.setAdapter(adapter);
        }
    }

    private List<ImoocNews> readJsonData(String url) {

        //okhttp请求
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            okhttp3.Response response = client.newCall(request).execute();
            String data = response.body().string();
            newsList = parseJson(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return newsList;
        }
    }

    private List<ImoocNews> parseJson(String data) {
        newsList = new ArrayList<ImoocNews>();
        Gson gson = new Gson();
        Response response = gson.fromJson(data, Response.class);
        for(CourseInfo courseInfo : response.data){
            String title = courseInfo.name;
            String content = courseInfo.description;
            String imageUrl = courseInfo.picSmall;
            ImoocNews imoocNews = new ImoocNews(imageUrl, title, content);
            newsList.add(imoocNews);
        }
        return newsList;
    }

    class Response{
        int status;
        List<CourseInfo> data;
        String msg;
    }
    class CourseInfo{
        int id;
        String name;
        String picSmall;
        String picBig;
        String description;
        int learner;
    }
}
