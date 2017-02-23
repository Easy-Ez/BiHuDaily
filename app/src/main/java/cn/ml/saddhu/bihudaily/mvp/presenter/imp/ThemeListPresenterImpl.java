package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetLoadMoreListener;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.BaseStory;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeInfo;
import cn.ml.saddhu.bihudaily.mvp.model.ThemeListModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.ThemeListModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.ThemeListPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.ThemeListView;

/**
 * Created by sadhu on 2017/2/11.
 * Email static.sadhu@gmail.com
 * Describe: 主题presenter实现
 */
public class ThemeListPresenterImpl implements ThemeListPresenter, OnNetRefreshListener<ThemeInfo>, OnNetLoadMoreListener<List<BaseStory>> {

    private ThemeListView mView;
    private ThemeListModel mModel;
    private ThemeInfo mThemeInfo;

    public ThemeListPresenterImpl(ThemeListView view) {
        this.mView = view;
        this.mModel = new ThemeListModelImpl(this, this);
    }

    @Override
    public void getThemePageList(String themeId) {
        mModel.getThemePageList(themeId);
    }

    @Override
    public void loadMoreThemePageList(String themeId) {
        mModel.loadMoreThemePageList(themeId, mThemeInfo.stories.get(mThemeInfo.stories.size() - 1).id);
    }


    @Override
    public void onRefreshSuccess(ThemeInfo themeInfo) {
        mThemeInfo = themeInfo;
        mView.onRefreshSucces(themeInfo);
    }

    @Override
    public void onRefreshError(int code) {
        mView.onRefreshError(code);
    }

    @Override
    public void onLoadMoreSuccess(List<BaseStory> baseStories) {
        mView.onLoadMoreSuccuess(baseStories);
    }

    @Override
    public void onLoadMoreError(int code) {

    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }
}
