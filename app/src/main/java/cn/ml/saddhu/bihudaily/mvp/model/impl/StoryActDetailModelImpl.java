package cn.ml.saddhu.bihudaily.mvp.model.impl;

import cn.ml.saddhu.bihudaily.engine.api.APIHelper;
import cn.ml.saddhu.bihudaily.engine.api.APIService;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;
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

    public StoryActDetailModelImpl(OnNetRefreshListener<StoryDetailExtra> mRefreshListener) {
        super(mRefreshListener);

    }


    @Override
    public void getStoryInfoExtral(String storyId) {
        APIService apiService = APIHelper.getInstance().create(APIService.class);
        Call<StoryDetailExtra> call = apiService.getStoryDetailExtra(storyId);
        mCalls.add(call);
        call.enqueue(new Callback<StoryDetailExtra>() {
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
                                 if (call.isCanceled()) {
                                 } else {
                                     mRefreshListener.onRefreshError(1);
                                 }
                             }
                         }
                     }
        );
    }
}
