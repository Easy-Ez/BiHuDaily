package cn.ml.saddhu.bihudaily.mvp.model;

import cn.ml.saddhu.bihudaily.engine.domain.Theme;
import cn.ml.saddhu.bihudaily.engine.domain.UserInfo;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface NagavitionModel {

    /**
     * 获取默认的用户信息(themes 从网络获取)
     */
    void getDefaultUserInfo();


    /**
     * 获取用户信息
     *
     * @param uid
     * @return
     */
    UserInfo getUserInfo(long uid);

    /**
     * 更新专栏
     *
     * @param theme
     */
    void updateTheme(Theme theme);
}
