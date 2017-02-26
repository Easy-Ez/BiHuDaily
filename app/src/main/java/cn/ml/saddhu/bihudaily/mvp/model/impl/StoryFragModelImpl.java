package cn.ml.saddhu.bihudaily.mvp.model.impl;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetail;
import cn.ml.saddhu.bihudaily.mvp.api.APIHelper;
import cn.ml.saddhu.bihudaily.mvp.api.APIService;
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

    public StoryFragModelImpl(OnNetRefreshListener<StoryDetail> mRefreshListener) {
        super(mRefreshListener);
        apiService = APIHelper.getInstance().create(APIService.class);
    }

    @Override
    public void onDestroy() {
        if (mStoryDetailCall != null) mStoryDetailCall.isCanceled();
    }

    @Override
    public void getStoryDetail(String storyId) {
        mStoryDetailCall = apiService.getStoryDetail(storyId);
        mStoryDetailCall.enqueue(new Callback<StoryDetail>() {
            @Override
            public void onResponse(Call<StoryDetail> call, Response<StoryDetail> response) {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefreshSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<StoryDetail> call, Throwable t) {
                // TODO: 2017/2/25 fixme error code
                if (mRefreshListener != null && !call.isCanceled()) {
                    mRefreshListener.onRefreshError(1);
                }
            }
        });
    }
}
