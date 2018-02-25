package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.adapter.ThemePageAdapter;
import cn.ml.saddhu.bihudaily.engine.domain.BaseStory;
import cn.ml.saddhu.bihudaily.engine.domain.Editor;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeInfo;
import cn.ml.saddhu.bihudaily.engine.util.ConfigurationManager;
import cn.ml.saddhu.bihudaily.mvp.presenter.ThemeListPresenter;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.ThemeListPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.IThemeListView;
import cn.ml.saddhu.bihudaily.mvp.view.impl.activity.EditorListActivity_;
import cn.ml.saddhu.bihudaily.mvp.view.impl.activity.StoryDetailActivity_;

/**
 * Created by sadhu on 2017/2/11.
 * Email static.sadhu@gmail.com
 * Describe: 订阅页
 */
@EFragment(R.layout.frag_story_list)
public class ThemeListFragment extends BaseFragment implements IThemeListView, ThemePageAdapter.OnItemClickListener, OnRefreshListener, OnLoadmoreListener {
    @ViewById
    SmartRefreshLayout refresh;
    @ViewById
    RecyclerView rv_story_list;
    @FragmentArg
    String mThemeId;
    private ThemeListPresenter mPresenter = new ThemeListPresenterImpl(this);
    private ThemePageAdapter mAdapter = new ThemePageAdapter();


    @AfterViews
    void afterViews() {
        refresh.setOnRefreshListener(this);
        refresh.setOnLoadmoreListener(this);
        mAdapter.setOnItemClickListener(this);
        rv_story_list.setLayoutManager( new LinearLayoutManager(getContext()));
        rv_story_list.setAdapter(mAdapter);
        mPresenter.getThemePageList(mThemeId);
    }

    public void setThemeId(String themeId) {
        if (!mThemeId.equals(themeId)) {
            // 切换到了其他主题,需要重新获取
            this.mThemeId = themeId;
            mPresenter.getThemePageList(mThemeId);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        loadMoreThemePageList();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.getThemePageList(mThemeId);
    }

    private void loadMoreThemePageList() {
        mPresenter.loadMoreThemePageList(mThemeId);
    }


    @Override
    public void onRefreshSucces(ThemeInfo data) {
        refresh.finishRefresh();
        mAdapter.setData(data);
    }


    @Override
    public void onRefreshError(int code) {
        refresh.finishRefresh(false);
    }

    @Override
    public void onLoadMoreSuccuess(List<BaseStory> data) {
        refresh.finishLoadmore();
        mAdapter.addData(data);
    }

    @Override
    public void onLoadMoreError(int code) {
        refresh.finishLoadmore(false);
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
        //设置已读
        mPresenter.setItemRead(position);
        // 进入文章详情
        StoryDetailActivity_
                .intent(this)
                .mIdLists(mPresenter.getThemeIdList())
                .mPosition(position)
                .start();
    }

    @Override
    public void notifyItemChange(int position) {
        // 有个头部
        mAdapter.notifyItemChanged(position + 1);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }


}
