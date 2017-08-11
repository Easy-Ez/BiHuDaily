package cn.ml.saddhu.bihudaily.mvp.view;

import cn.ml.saddhu.bihudaily.engine.domain.StoryDetail;

/**
 * Created by sadhu on 2017/2/25.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface IStoryDetailFragView extends IBaseView {
    /**
     * 设置头部相关信息
     *
     * @param detail
     */
    void setViewWithData(StoryDetail detail);

    /**
     * 加载parse过后的html源文件
     *
     * @param htmlString
     */
    void loadDataWithBaseUrl(String htmlString);

    /**
     * 加载url
     *
     * @param url
     */
    void loadUrl(String url);
}
