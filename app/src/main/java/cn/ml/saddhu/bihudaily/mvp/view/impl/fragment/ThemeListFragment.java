package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.BaseStory;
import cn.ml.saddhu.bihudaily.engine.domain.Editor;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeInfo;
import cn.ml.saddhu.bihudaily.engine.util.SharePreferenceUtil;
import cn.ml.saddhu.bihudaily.engine.adapter.ThemePageAdapter;
import cn.ml.saddhu.bihudaily.mvp.presenter.ThemeListPresenter;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.ThemeListPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.ThemeListView;
import cn.ml.saddhu.bihudaily.mvp.view.impl.activity.EditorListActivity_;
import cn.ml.saddhu.bihudaily.mvp.view.impl.activity.StoryDetailActivity_;

/**
 * Created by sadhu on 2017/2/11.
 * Email static.sadhu@gmail.com
 * Describe: 订阅页
 */
@EFragment(R.layout.frag_story_list)
public class  ThemeListFragment extends Fragment implements ThemeListView, SwipeRefreshLayout.OnRefreshListener, ThemePageAdapter.OnItemClickListener {
    @ViewById
    SwipeRefreshLayout refresh;
    @ViewById
    RecyclerView rv_story_list;
    @FragmentArg
    String mThemeId;
    private ThemeListPresenter mPresenter = new ThemeListPresenterImpl(this);
    private LinearLayoutManager mLayoutManger;
    private ThemePageAdapter mAdapter = new ThemePageAdapter();
    private boolean isRefresh;
    private boolean isLoadMore;

    @AfterViews
    void afterViews() {
        Logger.d("StoryListFragment method");
        boolean isDark = SharePreferenceUtil.isDark(getContext());
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
                        loadMoreThemePageList();
                    }
                }
            }
        });
        mPresenter.getThemePageList(mThemeId);
    }

    public void setThemeId(String themeId) {
        if (!mThemeId.equals(themeId)) {
            // 切换到了其他主题,需要重新获取
            this.mThemeId = themeId;
            refresh.setRefreshing(true);
            mPresenter.getThemePageList(mThemeId);
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        mPresenter.getThemePageList(mThemeId);
    }

    private void loadMoreThemePageList() {
        isLoadMore = true;
        mPresenter.loadMoreThemePageList(mThemeId);
    }


    @Override
    public void onRefreshSucces(ThemeInfo data) {
        isRefresh = false;
        refresh.setRefreshing(false);
        mAdapter.setData(data);
    }

    @Override
    public void onLoadMoreSuccuess(List<BaseStory> data) {
        isLoadMore = false;
        mAdapter.addData(data);
    }

    @Override
    public void onRefreshError(int code) {
        refresh.setRefreshing(false);
        isRefresh = false;
    }

    @Override
    public void onLoadMoreError(int code) {
        isLoadMore = false;
    }


    @Override
    public void onEditorItemClick(List<Editor> editors) {
        // 进入主编页面
        Gson gson = new Gson();
        String editorsString = gson.toJson(editors);
        Logger.d(editorsString);
        EditorListActivity_.intent(this).mEditors(editorsString).start();
    }

    @Override
    public void onNormalItemClick(int position) {
        // 进入文章详情
        StoryDetailActivity_
                .intent(this)
                .mIdLists(mPresenter.getThemeIdList())
                .mPosition(position)
                .start();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
