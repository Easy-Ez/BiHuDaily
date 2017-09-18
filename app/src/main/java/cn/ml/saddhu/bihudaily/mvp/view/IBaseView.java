package cn.ml.saddhu.bihudaily.mvp.view;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Created by sadhu on 2016/11/12.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface IBaseView {

    Context getContext();

    void showToast(@StringRes int res);

    void showToast(String msg);

    void showToast(String msg, int duration);
}
