package cn.ml.saddhu.bihudaily.mvp.view;

import android.graphics.Point;

import cn.ml.saddhu.bihudaily.engine.domain.Creative;

/**
 * Created by sadhu on 2016/11/12.
 * Email static.sadhu@gmail.com
 * Describe: 启动页view
 */
public interface ISplashView extends IBaseView {
    void showImg(Creative creative);

    Point getSplashImageSize();
}
