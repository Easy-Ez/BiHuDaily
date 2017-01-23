package cn.ml.saddhu.bihudaily.mvp.model.impl;

import java.util.Collections;

import cn.ml.saddhu.bihudaily.DBHelper;
import cn.ml.saddhu.bihudaily.engine.commondListener.NetCallbackListener;
import cn.ml.saddhu.bihudaily.engine.constant.SharedPreferenceConstants;
import cn.ml.saddhu.bihudaily.engine.domain.Theme;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeDao;
import cn.ml.saddhu.bihudaily.engine.domain.Themes;
import cn.ml.saddhu.bihudaily.engine.domain.UserInfo;
import cn.ml.saddhu.bihudaily.engine.domain.UserInfoDao;
import cn.ml.saddhu.bihudaily.mvp.api.APIHelper;
import cn.ml.saddhu.bihudaily.mvp.api.APIService;
import cn.ml.saddhu.bihudaily.mvp.model.NagavitionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class NagavitionModelImpl implements NagavitionModel {

    private final Call<Themes> call;
    private final ThemeDao mThemeDao;
    private NetCallbackListener<UserInfo> listener;
    private final UserInfoDao mUserInfoDao;

    public NagavitionModelImpl(NetCallbackListener<UserInfo> listener) {
        APIService apiService = APIHelper.getInstance().create(APIService.class);
        mUserInfoDao = DBHelper.getInstance().getDaoSession().getUserInfoDao();
        mThemeDao = DBHelper.getInstance().getDaoSession().getThemeDao();
        call = apiService.getThemes();
        this.listener = listener;
    }


    @Override
    public UserInfo getUserInfo(long uid) {
        UserInfo load = mUserInfoDao.load(uid);
        if (load != null) {
            load.themes = load.getThemes();
            Collections.sort(load.themes);
        }
        return load;
    }

    @Override
    public void getDefaultUserInfo() {
        getThemesFromNet();
    }


    private void getThemesFromNet() {
        call.enqueue(new Callback<Themes>() {
            @Override
            public void onResponse(Call<Themes> call, Response<Themes> response) {
                if (response.body() != null) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.uid = SharedPreferenceConstants.DEFAULT_UID;
                    userInfo.themes = response.body().themes;
                    for (int i = 0; i < userInfo.themes.size(); i++) {
                        userInfo.themes.get(i).owerId = userInfo.uid;
                        mThemeDao.insertOrReplace(userInfo.themes.get(i));
                    }
                    mUserInfoDao.insertOrReplace(userInfo);
                    listener.onSuccess(userInfo);
                } else {
                    listener.onError(0);
                }
            }

            @Override
            public void onFailure(Call<Themes> call, Throwable t) {
                listener.onError(0);
            }
        });
    }

    @Override
    public void updateTheme(Theme theme) {
        mThemeDao.update(theme);
    }
}
