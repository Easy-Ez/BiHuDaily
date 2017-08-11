package cn.ml.saddhu.bihudaily.mvp.view;

import cn.ml.saddhu.bihudaily.engine.domain.UserInfo;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe: 导航页view
 */
public interface INavigationDrawerView extends IBaseView {
    void showList(UserInfo userInfo);
}
