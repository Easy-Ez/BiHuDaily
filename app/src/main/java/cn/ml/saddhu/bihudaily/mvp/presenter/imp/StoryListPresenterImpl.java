package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetLoadMoreListener;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.engine.domain.TopStory;
import cn.ml.saddhu.bihudaily.mvp.model.StoryListModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.StoryListModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.StoryListPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.IStoryListView;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryListPresenterImpl extends BasePresenter<IStoryListView> implements StoryListPresenter, OnNetRefreshListener<StoryInfo>, OnNetLoadMoreListener<List<Story>> {
    private StoryListModel mModel;
    private StoryInfo mStoryInfo;
    private ArrayList<String> mNormalIds = new ArrayList<>();

    public StoryListPresenterImpl(IStoryListView storyListView) {
        super(storyListView);
        mModel = new StoryListModelImpl(this, this);
    }


    @Override
    public void setData(StoryInfo storyInfo) {
        this.mStoryInfo = storyInfo;
        mNormalIds.clear();
        for (Story story : mStoryInfo.stories) {
            mNormalIds.add(story.id);
        }
    }

    @Override
    public ArrayList<String> getLooperIdList() {
        ArrayList<String> looperIds = new ArrayList<>();
        for (TopStory story : mStoryInfo.topStories) {
            looperIds.add(story.id);
        }
        return looperIds;
    }

    @Override
    public ArrayList<String> getNormalIdList() {
        return mNormalIds;
    }

    @Override
    public void setItemRead(int position) {
        if (!mStoryInfo.stories.get(position).isRead) {
            Logger.d("setItemRead position %d", position);
            mStoryInfo.stories.get(position).setIsRead(true);
            mModel.setItemRead(mStoryInfo.stories.get(position).getId());
            mView.notifyItemChange(position);
        }

    }

    @Override
    public void getHomePageList() {
        mModel.getHomePageList();
    }

    @Override
    public void loadMoreHomePageList() {
        mModel.loadMoreHomePageList(mStoryInfo.stories.get(mStoryInfo.stories.size() - 1).date);
    }

    @Override
    public String getTagName(int position, boolean hasLooper) {
        return mStoryInfo.stories.get(hasLooper ? position - 1 : position).tagName;
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }

    @Override
    public void onRefreshSuccess(StoryInfo t) {
        mStoryInfo = t;
        mNormalIds.clear();
        for (Story story : mStoryInfo.stories) {
            mNormalIds.add(story.id);
        }
        mView.onRefreshSucces(t);
    }

    @Override
    public void onRefreshError(int code) {
        mView.onRefreshError(code);
    }

    @Override
    public void onLoadMoreSuccess(List<Story> stories) {
        for (Story story : stories) {
            mNormalIds.add(story.id);
        }
        mView.onLoadMoreSuccuess(stories);
    }

    @Override
    public void onLoadMoreError(int code) {
        mView.onLoadMoreError(code);
    }
}
