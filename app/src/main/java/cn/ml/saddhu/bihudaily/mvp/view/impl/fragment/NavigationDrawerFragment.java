package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.Theme;
import cn.ml.saddhu.bihudaily.engine.domain.UserInfo;
import cn.ml.saddhu.bihudaily.mvp.adapter.NavigationAdapter;
import cn.ml.saddhu.bihudaily.mvp.presenter.NavigationDrawerPresenter;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.NavigationDrawerPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.NavigationDrawerView;
import cn.ml.saddhu.bihudaily.mvp.view.impl.activity.MainActivity;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe: 左侧导航页
 */
@EFragment(R.layout.frag_navigation_drawer)
public class NavigationDrawerFragment extends Fragment implements NavigationDrawerView, NavigationAdapter.OnNavigationItemClickListener {
    @ViewById
    RecyclerView navigation_list;

    private NavigationAdapter mAdapter;
    private NavigationDrawerPresenter mPresenter;


    @AfterViews
    void afterViews() {
        Logger.d("aferrViews");
        navigation_list.setLayoutManager(new LinearLayoutManager(getContext()));
        mPresenter = new NavigationDrawerPresenterImpl(this);
        mPresenter.getUserInfo();

    }


    @Override
    public void showList(UserInfo userInfo) {
        if (mAdapter == null) {
            mAdapter = new NavigationAdapter(userInfo);
            mAdapter.setOnNavigationItemClickListener(this);
            navigation_list.setAdapter(mAdapter);
        } else {
            mAdapter.setData(userInfo);
            mAdapter.notifyDataSetChanged();
        }
        Logger.d("navigationFragment method");
    }

    @Override
    public void onRemindClick(int position) {
        // 订阅主题
        mPresenter.RemindTheme(position);
    }

    public Theme getCurrentTheme() {
        return mAdapter.getCurrentTheme();
    }

    @Override
    public void onItemClick(Theme theme, int position) {
        // 收起面板,请求list
        ((MainActivity) getActivity()).selectDrawerItem(theme);
    }

    @Override
    public void onUserClick() {

    }

    @Override
    public void onFavoriteClick() {

    }

    @Override
    public void onOfflineClick() {

    }


}
