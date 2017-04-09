package cn.ml.saddhu.bihudaily.engine.commondListener;

import cn.ml.saddhu.bihudaily.engine.domain.ErrorMsgBean;

/**
 * Created by sadhu on 2017/4/9.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface NetCallback<T> {
    void onSuccess(T t);

    void onError(ErrorMsgBean bean);
}
