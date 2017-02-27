package cn.ml.saddhu.bihudaily.mvp.presenter;

import java.util.ArrayList;

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

    /**
     * 获取头部信息
     *
     * @param storyInfo
     */
    void setData(StoryInfo storyInfo);

    /**
     * 获取轮播的所有id
     *
     * @return
     */
    ArrayList<String> getLooperIdList();

    /**
     * 获取列表的所有id
     *
     * @return
     */
    ArrayList<String> getNormalIdList();
}
