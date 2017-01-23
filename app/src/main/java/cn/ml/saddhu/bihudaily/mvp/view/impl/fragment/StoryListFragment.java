package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.engine.domain.Theme;
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
    private StoryListPresenter mPresenter;
    private LinearLayoutManager mLayoutManger;
    private HomePageAdapter mAdapter;
    private boolean isRefresh;
    private boolean isLoadMore;

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
        refresh.setOnRefreshListener(this);
        refresh.setColorSchemeResources(R.color.colorPrimary);
        mPresenter = new StoryListPresenterImpl(this);
        mLayoutManger = new LinearLayoutManager(getContext());
        mAdapter = new HomePageAdapter();
        mAdapter.setOnItemClickListener(this);
        rv_story_list.setLayoutManager(mLayoutManger);
        rv_story_list.setAdapter(mAdapter);
        rv_story_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isRefresh && !isLoadMore && mLayoutManger.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1) {
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
        mPresenter.getHomePageList();
    }


    public void getHomePageList() {
        mPresenter.getHomePageList();
    }

    private void loadMoreHomePageList() {
        isLoadMore = true;
        mPresenter.loadMoreHomePageList();
    }

    public void getThemePageList(Theme theme) {

    }


    @Override
    public void setFirstPageData(StoryInfo info) {
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
