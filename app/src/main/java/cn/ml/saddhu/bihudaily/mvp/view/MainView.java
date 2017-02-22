package cn.ml.saddhu.bihudaily.mvp.view;

import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;

/**
 * Created by sadhu on 2017/2/20.
 * Email static.sadhu@gmail.com
 * Describe: 主界面View
 */
public interface MainView extends BaseView {
    void setToolBarInfo(StoryDetailExtra extra);
}
