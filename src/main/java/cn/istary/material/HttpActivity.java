package cn.istary.material;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpActivity extends BaseActivity implements View.OnClickListener{

    private TextView responseText;
    private Button sendRequestButton;
    private String responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        sendRequestButton = findViewById(R.id.http_send_request);
        responseText = findViewById(R.id.http_response_text);
        sendRequestButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.http_send_request){
            //sendRequestWithHttpURLConnection();
            sendRequestWithOKhttp();
        }
    }

    private void sendRequestWithOKhttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://www.baidu.com").build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendRequestWithHttpURLConnection(){
        //start new thread to send connection request
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("https://www.istary.cn");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setConnectTimeout(8000);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    //read the input stream
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        response.append(line);
                    }
                    responseData = response.toString();
                    showResponse(responseData);

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(bufferedReader != null){
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new thread to ui option
                responseText.setText(response);
            }
        });
    }
}
