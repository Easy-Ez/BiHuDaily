package cn.ml.saddhu.bihudaily.mvp.presenter;

import java.util.ArrayList;

/**
 * Created by sadhu on 2017/2/11.
 * Email static.sadhu@gmail.com
 * Describe: 主题presenter
 */
public interface ThemeListPresenter extends IBasePresenter {
    /**
     * 获取第一页
     */
    void getThemePageList(String themeId);

    /**
     * 加载更多
     */
    void loadMoreThemePageList(String themeId);

    /**
     * 获取id集合
     * @return 文章id集合
     */
    ArrayList<String> getThemeIdList();

    /**
     * 设置已读
     * @param position
     */
    void setItemRead(int position);
}
