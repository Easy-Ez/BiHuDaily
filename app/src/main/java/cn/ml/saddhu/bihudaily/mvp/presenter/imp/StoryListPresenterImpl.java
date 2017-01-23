package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.mvp.model.StoryListModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.StoryListModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.StoryListPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.StoryListView;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryListPresenterImpl implements StoryListPresenter {
    private StoryListView mView;
    private StoryListModel mModel;
    private StoryInfo mStoryInfo;

    public StoryListPresenterImpl(StoryListView view) {
        this.mView = view;
        mModel = new StoryListModelImpl();
    }


    @Override
    public void getHomePageList() {
        mModel.getHomePageList(new StoryListModel.OnRefreshListener() {
            @Override
            public void onSuccuss(StoryInfo info) {
                mStoryInfo = info;
                mView.setFirstPageData(info);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void loadMoreHomePageList() {
        mModel.loadMoreHomePageList(new StoryListModel.OnLoadMoreListener() {
            @Override
            public void onSuccuss(List<Story> info) {
                mView.onLoadMoreSuccess(info);
            }

            @Override
            public void onError(int code) {

            }
        }, mStoryInfo.stories.get(mStoryInfo.stories.size() - 1).date);
    }

    @Override
    public String getTagName(int position, boolean hasLooper) {
        return mStoryInfo.stories.get(hasLooper ? position - 1 : position).tagName;
    }

    @Override
    public void getThemePageList() {

    }

    @Override
    public void loadMoreThemePageList() {

    }


    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;
    }
}
