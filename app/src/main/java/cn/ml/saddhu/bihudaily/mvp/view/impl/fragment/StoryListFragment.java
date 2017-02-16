package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.engine.util.DayNightSpUtil;
import cn.ml.saddhu.bihudaily.mvp.adapter.HomePageAdapter;
import cn.ml.saddhu.bihudaily.mvp.presenter.StoryListPresenter;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.StoryListPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.StoryListView;
import cn.ml.saddhu.bihudaily.mvp.view.impl.activity.StroyDetailActivity_;

/**
 * Created by sadhu on 2016/11/14.
 * Email static.sadhu@gmail.com
 * Describe: 新闻列表fragment
 */
@EFragment(R.layout.frag_story_list)
public class StoryListFragment extends Fragment implements StoryListView, SwipeRefreshLayout.OnRefreshListener, HomePageAdapter.OnItemClickListener {
    @ViewById
    SwipeRefreshLayout refresh;
    @ViewById
    RecyclerView rv_story_list;

    private OnToolBarTitleChangeListener mListener;
    private StoryListPresenter mPresenter = new StoryListPresenterImpl(this);
    private LinearLayoutManager mLayoutManger;
    private HomePageAdapter mAdapter = new HomePageAdapter();
    private boolean isRefresh;
    private boolean isLoadMore;
    @InstanceState
    StoryInfo mStoryInfo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnToolBarTitleChangeListener) {
            mListener = (OnToolBarTitleChangeListener) context;
        }

    }


    @AfterViews
    void afterViews() {
        Logger.d("StoryListFragment method");
        DayNightSpUtil mUtil = new DayNightSpUtil(getContext());
        boolean isDark = mUtil.isDark();
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeResources(isDark ? R.color.color22 : R.color.colorPrimary);
        mLayoutManger = new LinearLayoutManager(getContext());
        mAdapter.setOnItemClickListener(this);
        rv_story_list.setLayoutManager(mLayoutManger);
        rv_story_list.setAdapter(mAdapter);
        rv_story_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mAdapter.getData() != null && !isRefresh && !isLoadMore && mLayoutManger.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1) {
                        // 加载更多
                        loadMoreHomePageList();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mListener != null) {
                    if (mLayoutManger.findFirstVisibleItemPosition() == 0) {
                        mListener.onTitleChange("首页");
                    } else {
                        mListener.onTitleChange(mPresenter.getTagName(mLayoutManger.findFirstVisibleItemPosition(), mAdapter.isHasLooper()));
                    }
                }
            }
        });
        // 处理切换夜间模式 被回收的情况
        if (mStoryInfo == null) {
            mPresenter.getHomePageList();
        } else {
            mPresenter.setData(mStoryInfo);
            mAdapter.setData(mStoryInfo);
        }
    }

    public void getHomePageList() {
        mPresenter.getHomePageList();
    }

    private void loadMoreHomePageList() {
        isLoadMore = true;
        mPresenter.loadMoreHomePageList();
    }

    @Override
    public void setFirstPageData(StoryInfo info) {
        mStoryInfo = info;
        mAdapter.setData(info);
        isRefresh = false;
        refresh.setRefreshing(false);
    }

    @Override
    public void onLoadMoreSuccess(List<Story> info) {
        isLoadMore = false;
        mAdapter.addData(info);
    }


    @Override
    public void onRefresh() {
        isRefresh = true;
        mPresenter.getHomePageList();
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d("onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("onResume");
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onNormalItemClick(int position) {
        Logger.d("onNormalItemClick position %d", position);
    }

    @Override
    public void onLooperItemClick(int position) {
        Logger.d("onLooperItemClick position %d", position);
        StroyDetailActivity_.intent(this).start();
    }


    public interface OnToolBarTitleChangeListener {
        void onTitleChange(String tagName);
    }

}
