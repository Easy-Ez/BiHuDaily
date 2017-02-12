package cn.ml.saddhu.bihudaily.mvp.model;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface StoryListModel {

    void getHomePageList(OnRefreshListener onRefreshListener);

    void loadMoreHomePageList(OnLoadMoreListener onLoadMoreListener, String date);


    interface OnRefreshListener {
        void onSuccuss(StoryInfo info);

        void onError(int code);
    }

    interface OnLoadMoreListener {
        void onSuccuss(List<Story> info);

        void onError(int code);
    }
}
