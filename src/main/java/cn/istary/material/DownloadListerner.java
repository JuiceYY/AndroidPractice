package cn.istary.material;

/**
 * 定义回调接口， 这里的方法处理每一次子线程返回后的结果
 * 这里接口具体去怎么实现，对DownloadTask并不重要
 * 其我们也没必要去管，我们只需要做的就是定义好相关接口
 * 接口方法默认修饰为public
 * 接口变量默认修饰为public final
 */

public interface DownloadListerner {
    void onProgress(int progress); //通知下载进度
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
