package cn.ml.saddhu.bihudaily.engine.commondListener;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe: 网络响应回调
 */
public interface OnNetRefreshListener<R> {
    void onRefreshSuccess(R r);

    void onRefreshError(int code);

}
