package cn.ml.saddhu.bihudaily.mvp.model.impl;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetLoadMoreListener;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe:
 */
abstract class BaseModelImpl<R, L> {
    OnNetRefreshListener<R> mRefreshListener;
    OnNetLoadMoreListener<L> mLoadMoreListener;


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



}
