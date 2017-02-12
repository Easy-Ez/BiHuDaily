package cn.ml.saddhu.bihudaily.mvp.presenter;

import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe: 文章列表
 */
public interface StoryListPresenter extends BasePresenter {
    /**
     * 获取第一页
     */
    void getHomePageList();

    /**
     * 加载更多
     */
    void loadMoreHomePageList();

    /**
     * 获取item日期字符串
     *
     * @param position  位置
     * @param hasLooper 是否有looper
     * @return
     */
    String getTagName(int position, boolean hasLooper);

    void setData(StoryInfo storyInfo);
}
