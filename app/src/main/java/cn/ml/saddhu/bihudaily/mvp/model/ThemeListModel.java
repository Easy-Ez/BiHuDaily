package cn.ml.saddhu.bihudaily.mvp.model;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.domain.BaseStory;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeInfo;

/**
 * Created by sadhu on 2017/2/12.
 * Email static.sadhu@gmail.com
 * Describe: 主题界面数据操作接口
 */
public interface ThemeListModel {


    /**
     * 获取主题界面第一页的数据/刷新
     *
     * @param onRefreshListener 刷新回调
     */
    void getThemePageList(OnRefreshListener onRefreshListener, String id);

    /**
     * 主题界面加载更多
     *
     * @param onLoadMoreListener 加载更多回调
     * @param id                 获取 id 之前的数据
     */
    void loadMoreThemePageList(OnLoadMoreListener onLoadMoreListener, String id);


    interface OnRefreshListener {
        void onSuccuss(ThemeInfo info);

        void onError(int code);
    }

    interface OnLoadMoreListener {
        void onSuccuss(List<BaseStory> info);

        void onError(int code);
    }
}
