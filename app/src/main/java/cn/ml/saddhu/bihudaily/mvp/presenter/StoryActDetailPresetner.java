package cn.ml.saddhu.bihudaily.mvp.presenter;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe: 文章详情相关接口
 */
public interface StoryActDetailPresetner extends BasePresenter {
    void getStoryDetailExtra(String storyId);

    String getCurrentStoryId();
}
