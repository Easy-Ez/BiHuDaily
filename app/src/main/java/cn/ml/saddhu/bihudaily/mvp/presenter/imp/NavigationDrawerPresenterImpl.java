package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.orhanobut.logger.Logger;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.constant.SharedPreferenceConstants;
import cn.ml.saddhu.bihudaily.engine.domain.UserInfo;
import cn.ml.saddhu.bihudaily.mvp.model.NagavitionModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.NagavitionModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.INavigationDrawerPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.INavigationDrawerView;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe: 导航页
 */
public class NavigationDrawerPresenterImpl extends BasePresenter<INavigationDrawerView> implements INavigationDrawerPresenter, OnNetRefreshListener<UserInfo> {

    private NagavitionModel mModel;
    private final SharedPreferences mPreferences;
    private UserInfo mUerInfo;

    public NavigationDrawerPresenterImpl(INavigationDrawerView iNavigationDrawerView) {
        super(iNavigationDrawerView);
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
    public void downloadOfflineData() {
        final float fetchProgressRatio = 0.2f;
        mModel.downloadOfflineData(new DownloadProgressListener() {
            @Override
            public void onPrepared() {
                mView.setOfflineText("0%");
            }

            @Override
            public void onFetchDataProgressChange(int progress, int total) {
                float v = progress * 100f * fetchProgressRatio / total;
                Logger.i("progress onFetchDataProgressChange %f", v);
                mView.setOfflineText((int) v + "%");
            }

            @Override
            public void onDownloadProgressChange(int progress, int total) {
                float v = progress * 100f * (1 - fetchProgressRatio) / total + fetchProgressRatio * 100;
                Logger.i("progress onDownloadProgressChange %f", v);
                mView.setOfflineText((int) v + "%");
            }

            @Override
            public void onComplete() {
                Logger.i("progress onComplete");
                mView.setOfflineText(mView.getContext().getString(R.string.drawer_complete));
            }

            @Override
            public void onCancel() {
                Logger.i("progress onCancel");
                mView.setOfflineText(mView.getContext().getString(R.string.drawer_offline));
            }

            @Override
            public void onError() {
                Logger.i("progress onError");
                mView.setOfflineText(mView.getContext().getString(R.string.drawer_error));
            }
        });

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

    public interface DownloadProgressListener {
        void onCancel();

        void onPrepared();

        void onFetchDataProgressChange(int progress, int total);

        void onDownloadProgressChange(int progress, int total);

        void onComplete();

        void onError();
    }
}
