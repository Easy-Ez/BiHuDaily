package cn.ml.saddhu.bihudaily.mvp.presenter;

/**
 * Created by sadhu on 2017/2/11.
 * Email static.sadhu@gmail.com
 * Describe: 主题presenter
 */
public interface ThemeListPresenter extends BasePresenter {
    /**
     * 获取第一页
     */
    void getThemePageList(String themeId);

    /**
     * 加载更多
     */
    void loadMoreThemePageList(String themeId);
}
