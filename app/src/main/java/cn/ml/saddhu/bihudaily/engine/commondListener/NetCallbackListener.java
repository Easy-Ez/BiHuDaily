package cn.ml.saddhu.bihudaily.engine.commondListener;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe: 网络响应回调
 */
public interface NetCallbackListener<T> {
    void onSuccess(T t);

    void onError(int code);
}
