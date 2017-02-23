package cn.ml.saddhu.bihudaily.mvp.model.impl;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;
import cn.ml.saddhu.bihudaily.mvp.api.APIHelper;
import cn.ml.saddhu.bihudaily.mvp.api.APIService;
import cn.ml.saddhu.bihudaily.mvp.model.StoryActDetailModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryActDetailModelImpl extends BaseModelImpl<StoryDetailExtra, Void> implements StoryActDetailModel {
    private final APIService apiService;
    private Call<StoryDetailExtra> mCall;

    public StoryActDetailModelImpl(OnNetRefreshListener<StoryDetailExtra> mRefreshListener) {
        super(mRefreshListener);
        apiService = APIHelper.getInstance().create(APIService.class);
    }


    @Override
    public void getStoryInfoExtral(String storyId) {
        mCall = apiService.getStoryExtraInfo(storyId);
        mCall.enqueue(new Callback<StoryDetailExtra>() {
            @Override
            public void onResponse(Call<StoryDetailExtra> call, Response<StoryDetailExtra> response) {
                if (mRefreshListener != null) {
                    if (response.body() != null) {
                        mRefreshListener.onRefreshSuccess(response.body());
                    }
                } else {
                    // TODO: 2017/2/23 错误
                }

            }

            @Override
            public void onFailure(Call<StoryDetailExtra> call, Throwable t) {
                // TODO: 2017/2/23  错误
                if (mRefreshListener != null) {
                    mRefreshListener.onRefreshError(1);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mCall != null) mCall.cancel();
    }


}
