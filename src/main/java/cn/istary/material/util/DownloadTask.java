package cn.istary.material.util;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import cn.istary.material.DownloadListerner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListerner downloadListerner;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress; //之前的进度

    public DownloadTask(DownloadListerner downloadListerner){
        this.downloadListerner = downloadListerner;
    }

    //后台任务开始执行前执行
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        switch (integer){
            case TYPE_SUCCESS:
                downloadListerner.onSuccess();
                break;
            case  TYPE_PAUSED:
                downloadListerner.onPaused();
                break;
            case TYPE_FAILED:
                downloadListerner.onFailed();
                break;
            case TYPE_CANCELED:
                downloadListerner.onCanceled();
                break;
            default:
                break;
        }
    }

    //向主线程发送进度信息
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = values[0];
        if(progress > lastProgress){
            downloadListerner.onProgress(progress);
            lastProgress = progress;
        }

    }

    @Override
    protected Integer doInBackground(String... strings) {
        //子线程执行下载任务
        InputStream inputStream = null;
        RandomAccessFile savedFile = null;//随机存取文件，用于断点续传
        File file = null;

        try{
            /**
             * 下载文件
             */
            //先初始化一些变量：已下载文件长度，下载链接， 文件名， 目录名，文件对象， 全文件长度
            long downloadedLength = 0; //记录已下载长度
            String downloadUrl = strings[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));//分割字符串得到文件名
            String directoryName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directoryName + fileName);
            if(file.exists()){
                downloadedLength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);

            //判断链接是否正确与文件是否已经下载完成
            if(contentLength == 0){
                //得知文件总长度为0， 下载错误
                return TYPE_FAILED;
            }else if(contentLength == downloadedLength){
                return TYPE_SUCCESS;
            }

            //OkHttp 发送请求
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //实现断点下载， 指定文件从哪个字节开始下载
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();

            //处理response
            if(response != null){
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadedLength);
                byte[] bytes = new byte[1024];
                int total = 0;
                int len;

                while ((len = inputStream.read(bytes)) != -1){
                    if(isCanceled){
                        return TYPE_CANCELED;
                    }else if(isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        savedFile.write(bytes, 0, len);
                        int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
            }

            response.body().close();
            return TYPE_SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //总要关闭流
            if(inputStream!= null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(savedFile != null){
                try {
                    savedFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(isCanceled && file != null){
                file.delete();
            }
        }



        return TYPE_FAILED; //下载未完成
    }

    private long getContentLength(String downloadUrl) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if(response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }

        return 0;
    }

    public void pauseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCanceled = true;
    }
}
