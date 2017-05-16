package cn.ml.saddhu.bihudaily.mvp.model.impl;

import com.orhanobut.logger.Logger;

import cn.ml.saddhu.bihudaily.engine.api.APIHelper;
import cn.ml.saddhu.bihudaily.engine.api.APIService;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.db.DBHelper;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetail;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailDao;
import cn.ml.saddhu.bihudaily.mvp.model.StoryFragModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2017/2/25.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryFragModelImpl extends BaseModelImpl<StoryDetail, Void> implements StoryFragModel {


    private final APIService apiService;
    private Call<StoryDetail> mStoryDetailCall;
    private final StoryDetailDao mStoryDetailDao;

    public StoryFragModelImpl(OnNetRefreshListener<StoryDetail> mRefreshListener) {
        super(mRefreshListener);
        apiService = APIHelper.getInstance().create(APIService.class);
        mStoryDetailDao = DBHelper.getInstance().getDaoSession().getStoryDetailDao();
    }

    @Override
    public void onDestroy() {
        if (mStoryDetailCall != null) {
            mStoryDetailCall.cancel();
        }
    }

    @Override
    public void getStoryDetail(String storyId) {
        StoryDetail unique = mStoryDetailDao.queryBuilder().where(StoryDetailDao.Properties.Id.eq(storyId)).unique();
        if (unique == null) {
            mStoryDetailCall = apiService.getStoryDetail(storyId);
            mStoryDetailCall.enqueue(new Callback<StoryDetail>() {
                @Override
                public void onResponse(Call<StoryDetail> call, Response<StoryDetail> response) {
                    Logger.e("onResponse and isCanceled" + call.isCanceled());
                    if (mRefreshListener != null && !call.isCanceled()) {
                        mRefreshListener.onRefreshSuccess(response.body());
                        // 存入数据库
                        mStoryDetailDao.insert(response.body());
                    }
                }

                @Override
                public void onFailure(Call<StoryDetail> call, Throwable t) {
                    // TODO: 2017/2/25 fixme error code
                    Logger.e("onFailure and isCanceled" + call.isCanceled());
                    if (mRefreshListener != null && !call.isCanceled()) {
                        mRefreshListener.onRefreshError(1);
                    }
                }
            });
        } else {
            Logger.e("data from db");
            mRefreshListener.onRefreshSuccess(unique);
        }

    }
}
