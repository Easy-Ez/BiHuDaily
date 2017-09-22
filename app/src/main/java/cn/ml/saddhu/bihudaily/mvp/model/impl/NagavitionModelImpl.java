package cn.ml.saddhu.bihudaily.mvp.model.impl;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cn.ml.saddhu.bihudaily.engine.api.APIHelper;
import cn.ml.saddhu.bihudaily.engine.api.APIService;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.constant.SharedPreferenceConstants;
import cn.ml.saddhu.bihudaily.engine.db.DBHelper;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetail;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailDao;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.engine.domain.Theme;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeDao;
import cn.ml.saddhu.bihudaily.engine.domain.Themes;
import cn.ml.saddhu.bihudaily.engine.domain.UserInfo;
import cn.ml.saddhu.bihudaily.engine.domain.UserInfoDao;
import cn.ml.saddhu.bihudaily.engine.imageloader.ImageDownloadManager;
import cn.ml.saddhu.bihudaily.engine.util.HTMLUtils;
import cn.ml.saddhu.bihudaily.mvp.model.NagavitionModel;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.NavigationDrawerPresenterImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class NagavitionModelImpl extends BaseModelImpl<UserInfo, Void> implements NagavitionModel {

    private final ThemeDao mThemeDao;
    private final UserInfoDao mUserInfoDao;
    private final StoryDetailDao mStoryDetailDao;
    private APIService mApiService;
    private AtomicInteger mDownloadedSize;
    private int mDownloadSize;
    private boolean isDownloading;
    private Thread mThread;
    private List<Call> downloadCall = new ArrayList<>();
    private Call<StoryInfo> storyListCall;

    public NagavitionModelImpl(OnNetRefreshListener<UserInfo> mRefreshListener) {
        super(mRefreshListener);
        mUserInfoDao = DBHelper.getInstance().getDaoSession().getUserInfoDao();
        mThemeDao = DBHelper.getInstance().getDaoSession().getThemeDao();
        mStoryDetailDao = DBHelper.getInstance().getDaoSession().getStoryDetailDao();
        mApiService = APIHelper.getInstance().create(APIService.class);
        mDownloadedSize = new AtomicInteger(0);
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

        Call<Themes> call = mApiService.getThemes();
        mCalls.add(call);
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
                    mRefreshListener.onRefreshSuccess(userInfo);
                } else {
                    mRefreshListener.onRefreshError(0);
                }
            }

            @Override
            public void onFailure(Call<Themes> call, Throwable t) {
                if (call.isCanceled()) {

                } else {
                    mRefreshListener.onRefreshError(0);
                }
            }
        });
    }

    @Override
    public void updateTheme(Theme theme) {
        mThemeDao.update(theme);
    }

    @Override
    public void downloadOfflineData(@NonNull  final NavigationDrawerPresenterImpl.DownloadProgressListener listener) {
        isDownloading = !isDownloading;
        if (!isDownloading) {
            if (storyListCall.isCanceled()) {
                storyListCall.cancel();
            }
            for (Call call : downloadCall) {
                if (!call.isCanceled()) {
                    call.cancel();
                }
            }
            downloadCall.clear();
            if (mThread != null) {
                mThread.interrupt();
                mThread = null;
            }
            listener.onCancel();
        } else {
            mDownloadedSize.set(0);
            mDownloadSize = 0;
            storyListCall = mApiService.getHomePageList();
            mCalls.add(storyListCall);
            listener.onPrepared();
            storyListCall.enqueue(new Callback<StoryInfo>() {
                @Override
                public void onResponse(Call<StoryInfo> call, Response<StoryInfo> response) {
                    final StoryInfo body = response.body();
                    mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HashSet<String> ids = new HashSet<>();
                            if (body != null) {
                                if (body.stories != null) {
                                    for (int i = 0; i < body.stories.size(); i++) {
                                        ids.add(body.stories.get(i).getId());
                                    }
                                }
                                if (body.topStories != null) {
                                    for (int i = 0; i < body.topStories.size(); i++) {
                                        ids.add(body.topStories.get(i).getId());
                                    }
                                }
                                if (ids.size() > 0) {
                                    HashSet<String> srcs = new HashSet<>();
                                    int i = 0;
                                    for (String id : ids) {
                                        Call<StoryDetail> tmpCall = mApiService.getStoryDetail(id);
                                        downloadCall.add(tmpCall);
                                        mCalls.add(tmpCall);
                                        try {
                                            Response<StoryDetail> tmpResponse = tmpCall.execute();
                                            StoryDetail storyDetail = tmpResponse.body();
                                            if (storyDetail != null) {
                                                mStoryDetailDao.insertOrReplace(storyDetail);
                                                HashSet<String> src = HTMLUtils.parseAttribute(storyDetail.body, "src");
                                                if (src != null && src.size() > 0)
                                                    srcs.addAll(src);
                                            }
                                            listener.onFetchDataProgressChange(i + 1, body.stories.size());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            listener.onFetchDataProgressChange(i + 1, body.stories.size());
                                        }
                                        i++;
                                    }

                                    mDownloadSize = srcs.size();

                                    for (String src : srcs) {
                                        Logger.i("offline download url %s:", src);
                                        ImageDownloadManager.getInstance().addTask(src, new ImageDownloadManager.DownloadListener() {
                                            @Override
                                            public void onSuccuss(String oldUrl, String newFile) {
                                                mDownloadedSize.getAndIncrement();
                                                Logger.i("offline donwload succuss %d/%d threadName: %s", mDownloadedSize.get(), mDownloadSize, Thread.currentThread().getName());
                                                listener.onDownloadProgressChange(mDownloadedSize.get(), mDownloadSize);
                                                if (mDownloadedSize.get() == mDownloadSize) {
                                                    listener.onComplete();
                                                    isDownloading = false;
                                                }
                                            }

                                            @Override
                                            public void onError(String oldUrl) {
                                                mDownloadedSize.getAndIncrement();
                                                Logger.i("offline donwload succuss %d/%d threadName: %s", mDownloadedSize.get(), mDownloadSize, Thread.currentThread().getName());
                                                listener.onDownloadProgressChange(mDownloadedSize.get(), mDownloadSize);
                                                if (mDownloadedSize.get() == mDownloadSize) {
                                                    listener.onComplete();
                                                    isDownloading = false;
                                                }
                                            }
                                        });
                                    }

                                }
                            }
                        }
                    });
                    mThread.start();
                }

                @Override
                public void onFailure(Call<StoryInfo> call, Throwable throwable) {
                    isDownloading = false;
                    listener.onError();
                }
            });
        }
    }


}
