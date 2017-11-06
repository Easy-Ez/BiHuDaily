package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.adapter.HomePageAdapter;
import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.mvp.presenter.StoryListPresenter;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.StoryListPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.IStoryListView;
import cn.ml.saddhu.bihudaily.mvp.view.impl.activity.StoryDetailActivity_;

/**
 * Created by sadhu on 2016/11/14.
 * Email static.sadhu@gmail.com
 * Describe: 新闻列表fragment
 */
@EFragment(R.layout.frag_story_list)
public class StoryListFragment extends BaseFragment implements IStoryListView, HomePageAdapter.OnItemClickListener, OnRefreshListener, OnLoadmoreListener {
    @ViewById
    SmartRefreshLayout refresh;
    @ViewById
    RecyclerView rv_story_list;

    private OnToolBarTitleChangeListener mListener;
    private StoryListPresenter mPresenter = new StoryListPresenterImpl(this);
    private LinearLayoutManager mLayoutManger;
    private HomePageAdapter mAdapter = new HomePageAdapter();
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
        refresh.setOnRefreshListener(this);
        refresh.setOnLoadmoreListener(this);
        mLayoutManger = new LinearLayoutManager(getContext());
        mAdapter.setOnItemClickListener(this);
        rv_story_list.setLayoutManager(mLayoutManger);
        rv_story_list.setAdapter(mAdapter);
        rv_story_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        mPresenter.loadMoreHomePageList();
    }

    @Override
    public void onRefreshSucces(StoryInfo data) {
        mStoryInfo = data;
        mAdapter.setData(data);
        refresh.finishRefresh();
    }

    @Override
    public void onRefreshError(int code) {
        refresh.finishRefresh(false);
    }

    @Override
    public void onLoadMoreSuccuess(List<Story> data) {
        refresh.finishLoadmore();
        mAdapter.addData(data);
    }

    @Override
    public void onLoadMoreError(int code) {
        refresh.finishLoadmore(false);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getHomePageList();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        // 加载更多
        loadMoreHomePageList();
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
    public void onDestroyView() {
        mPresenter.onDestroy();
        if (mAdapter != null && mLayoutManger.findFirstVisibleItemPosition() == 0) {
            RecyclerView.ViewHolder holder = rv_story_list.findViewHolderForAdapterPosition(0);
            if (holder != null && holder instanceof HomePageAdapter.HomePageLooperVH) {
                ((HomePageAdapter.HomePageLooperVH) holder).release();
            }
        }
        super.onDestroyView();
    }

    @Override
    public void onNormalItemClick(int position) {
        //设置已读
        mPresenter.setItemRead(position);
        // 进入详情
        StoryDetailActivity_
                .intent(this)
                .mIdLists(mPresenter.getNormalIdList())
                .mPosition(position)
                .start();
    }

    @Override
    public void notifyItemChange(int position) {
        mAdapter.notifyItemChanged(mAdapter.isHasLooper() ? position + 1 : position);
    }

    @Override
    public void onLooperItemClick(int position) {
        Logger.d("onLooperItemClick position %d", position);
        StoryDetailActivity_
                .intent(this)
                .mIdLists(mPresenter.getLooperIdList())
                .mPosition(position)
                .start();
    }


    public interface OnToolBarTitleChangeListener {
        void onTitleChange(String tagName);
    }

}
