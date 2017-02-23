package cn.ml.saddhu.bihudaily.mvp.view;

import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe: 文章详情界面
 */
public interface StoryDetailView extends BaseView {
    void setToolBarInfo(StoryDetailExtra extra);
}
