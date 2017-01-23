package cn.ml.saddhu.bihudaily.mvp.model;

import cn.ml.saddhu.bihudaily.engine.domain.Creative;

/**
 * Created by sadhu on 2016/11/12.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface SplashModel {
    void getSplashInfo(int width, int height);

    Creative getSplashBean();
}
