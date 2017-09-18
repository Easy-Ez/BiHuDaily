package cn.ml.saddhu.bihudaily.mvp.model.impl;

import java.util.ArrayList;
import java.util.List;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetLoadMoreListener;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.mvp.model.BaseModel;
import retrofit2.Call;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe:
 */
abstract class BaseModelImpl<R, L> implements BaseModel {
    OnNetRefreshListener<R> mRefreshListener;
    OnNetLoadMoreListener<L> mLoadMoreListener;
    protected List<Call<? extends Object>> mCalls = new ArrayList<>();

    BaseModelImpl() {
        this(null, null);
    }

    BaseModelImpl(OnNetRefreshListener<R> mRefreshListener) {
        this(mRefreshListener, null);
    }

    public BaseModelImpl(OnNetLoadMoreListener<L> mLoadMoreListener) {
        this(null, mLoadMoreListener);
    }

    BaseModelImpl(OnNetRefreshListener<R> refreshListener, OnNetLoadMoreListener<L> loadMoreListener) {
        mRefreshListener = refreshListener;
        mLoadMoreListener = loadMoreListener;
    }

    @Override
    public void onDestroy() {
        for (Call call : mCalls) {
            if (!call.isCanceled()) {
                call.cancel();
            }
        }
    }
}
