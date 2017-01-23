package cn.ml.saddhu.bihudaily.mvp.view;

import android.graphics.Point;

import cn.ml.saddhu.bihudaily.engine.domain.Creative;
import cn.ml.saddhu.bihudaily.mvp.presenter.SplashPresenter;

/**
 * Created by sadhu on 2016/11/12.
 * Email static.sadhu@gmail.com
 * Describe: 启动页view
 */
public interface SplashView extends BaseView {
    void showImg(Creative creative);

    Point getSplashImageSize();
}
