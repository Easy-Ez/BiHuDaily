package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import java.util.List;

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
public class ThemeListPresenterImpl implements ThemeListPresenter {

    private ThemeListView mView;
    private ThemeListModel mModel;
    private ThemeInfo mThemeInfo;

    public ThemeListPresenterImpl(ThemeListView view) {
        this.mView = view;
        this.mModel = new ThemeListModelImpl();
    }

    @Override
    public void getThemePageList(String themeId) {
        mModel.getThemePageList(new ThemeListModel.OnRefreshListener() {
            @Override
            public void onSuccuss(ThemeInfo info) {
                mThemeInfo = info;
                mView.onRefreshSucces(info);
            }

            @Override
            public void onError(int code) {
                mView.onRefreshError(code);
            }
        }, themeId);
    }

    @Override
    public void loadMoreThemePageList() {
        mModel.loadMoreThemePageList(new ThemeListModel.OnLoadMoreListener() {
            @Override
            public void onSuccuss(List<BaseStory> info) {
                mView.onLoadMoreSuccuess(info);
            }

            @Override
            public void onError(int code) {
                mView.onLoadMoreError(code);
            }
        }, mThemeInfo.stories.get(mThemeInfo.stories.size() - 1).id);
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;
    }
}
