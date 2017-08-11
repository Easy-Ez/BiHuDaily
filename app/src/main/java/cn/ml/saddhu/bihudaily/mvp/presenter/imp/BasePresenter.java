package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import cn.ml.saddhu.bihudaily.mvp.presenter.IBasePresenter;
import cn.ml.saddhu.bihudaily.mvp.view.IBaseView;

/**
 * Created by sadhu on 2017/6/13.
 * 所有的presenter都应该继承该类
 */
abstract class BasePresenter<T extends IBaseView> implements IBasePresenter {
    T mView;

    BasePresenter(T t) {
        this.mView = t;
    }
}
