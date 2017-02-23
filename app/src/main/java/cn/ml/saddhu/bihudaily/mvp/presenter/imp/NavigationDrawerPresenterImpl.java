package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.constant.SharedPreferenceConstants;
import cn.ml.saddhu.bihudaily.engine.domain.UserInfo;
import cn.ml.saddhu.bihudaily.mvp.model.NagavitionModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.NagavitionModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.NavigationDrawerPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.NavigationDrawerView;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe: 导航页
 */
public class NavigationDrawerPresenterImpl implements NavigationDrawerPresenter, OnNetRefreshListener<UserInfo> {

    private NavigationDrawerView mView;
    private NagavitionModel mModel;
    private final SharedPreferences mPreferences;
    private UserInfo mUerInfo;

    public NavigationDrawerPresenterImpl(NavigationDrawerView view) {
        this.mView = view;
        mModel = new NagavitionModelImpl(this);
        mPreferences = ((Fragment) mView).getContext().getSharedPreferences("config", Context.MODE_PRIVATE);

    }

    @Override
    public void getUserInfo() {
        mUerInfo = mModel.getUserInfo(mPreferences.getLong(SharedPreferenceConstants.KEY_UID, SharedPreferenceConstants.DEFAULT_UID));
        if (mUerInfo == null) {
            mModel.getDefaultUserInfo();
        } else {
            if (mUerInfo.themes == null || mUerInfo.themes.size() == 0) {
                mModel.getDefaultUserInfo();
            } else {
                mView.showList(mUerInfo);
            }
        }

    }

    @Override
    public void RemindTheme(int position) {
        mModel.updateTheme(mUerInfo.themes.get(position));
    }


    @Override
    public void onRefreshSuccess(UserInfo info) {
        mUerInfo = info;
        mView.showList(info);
    }

    @Override
    public void onRefreshError(int code) {
        mView.showList(null);
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }

}
