package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.adapter.NavigationAdapter;
import cn.ml.saddhu.bihudaily.engine.domain.Theme;
import cn.ml.saddhu.bihudaily.engine.domain.UserInfo;
import cn.ml.saddhu.bihudaily.mvp.presenter.INavigationDrawerPresenter;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.NavigationDrawerPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.INavigationDrawerView;
import cn.ml.saddhu.bihudaily.mvp.view.impl.activity.MainActivity;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe: 左侧导航页
 */
@EFragment(R.layout.frag_navigation_drawer)
public class NavigationDrawerFragment extends BaseFragment implements INavigationDrawerView, NavigationAdapter.OnNavigationItemClickListener {
    @ViewById
    RecyclerView navigation_list;

    private NavigationAdapter mAdapter;
    private INavigationDrawerPresenter mPresenter;
    public static final int WHAT_UPDATE_PROGRESS = 1;
    private android.os.Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_UPDATE_PROGRESS) {
                updatePorgress((String) msg.obj);
            }
        }
    };

    private void updatePorgress(String text) {
        RecyclerView.ViewHolder viewHolder = navigation_list.findViewHolderForAdapterPosition(0);
        if (viewHolder instanceof NavigationAdapter.NavigationUserVH) {
            ((NavigationAdapter.NavigationUserVH) viewHolder).drawer_offline_progress.setText(text);
        }
    }

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
    public void setOfflineText(String text) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            updatePorgress(text);
        } else {
            Message obtain = Message.obtain(mHandler, WHAT_UPDATE_PROGRESS);
            obtain.obj = text;
            obtain.sendToTarget();
        }

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
    public void onItemClick(Theme theme, int position, boolean isDifItem) {
        // 收起面板,请求list
        ((MainActivity) getActivity()).selectDrawerItem(theme, isDifItem);
    }

    @Override
    public void onUserClick() {

    }

    @Override
    public void onFavoriteClick() {

    }

    @Override
    public void onOfflineClick() {
        mPresenter.downloadOfflineData();
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
