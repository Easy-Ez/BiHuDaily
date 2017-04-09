package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;
import cn.ml.saddhu.bihudaily.mvp.model.StoryActDetailModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.StoryActDetailModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.StoryActDetailPresetner;
import cn.ml.saddhu.bihudaily.mvp.view.StoryDetailActView;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryActDetailPresenterImpl implements StoryActDetailPresetner, OnNetRefreshListener<StoryDetailExtra> {
    private StoryDetailActView mView;
    private StoryActDetailModel mModel;
    private String mStoryId;

    public StoryActDetailPresenterImpl(StoryDetailActView view) {
        this.mView = view;
        this.mModel = new StoryActDetailModelImpl(this);
    }

    @Override
    public void getStoryDetailExtra(String storyId) {
        mView.setToolBarInfo(null);
        mModel.getStoryInfoExtral(storyId);
        mStoryId = storyId;
    }

    @Override
    public String getCurrentStoryId() {
        return mStoryId;
    }

    @Override
    public void onRefreshSuccess(StoryDetailExtra storyDetailExtra) {
        mView.setToolBarInfo(storyDetailExtra);
    }

    @Override
    public void onRefreshError(int code) {

    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }
}
