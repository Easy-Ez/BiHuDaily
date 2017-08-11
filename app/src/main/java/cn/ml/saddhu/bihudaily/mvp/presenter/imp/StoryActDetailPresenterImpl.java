package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;
import cn.ml.saddhu.bihudaily.mvp.model.StoryActDetailModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.StoryActDetailModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.IStoryActDetailPresetner;
import cn.ml.saddhu.bihudaily.mvp.view.IStoryDetailActView;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryActDetailPresenterImpl  extends BasePresenter<IStoryDetailActView> implements IStoryActDetailPresetner, OnNetRefreshListener<StoryDetailExtra> {
    private StoryActDetailModel mModel;
    private String mStoryId;

    public StoryActDetailPresenterImpl(IStoryDetailActView storyDetailActView) {
        super(storyDetailActView);
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
