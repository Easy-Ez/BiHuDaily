package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import android.graphics.Point;
import android.support.annotation.NonNull;

import cn.ml.saddhu.bihudaily.engine.domain.Creative;
import cn.ml.saddhu.bihudaily.mvp.model.SplashModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.SplashModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.SplashPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.SplashView;

/**
 * Created by sadhu on 2016/11/12.
 * Email static.sadhu@gmail.com
 * Describe: 启动页presenter实现
 */
public class SplashPresenterImpl implements SplashPresenter {

    private SplashView mView;
    private SplashModel mModel;

    public SplashPresenterImpl(@NonNull SplashView mView) {
        this.mView = mView;
        this.mModel = new SplashModelImpl();
    }

    @Override
    public void getCreative() {
        Creative creative = mModel.getSplashBean();
        mView.showImg(creative);
    }

    @Override
    public void fetchSpashInfo() {
        Point size = mView.getSplashImageSize();
        mModel.getSplashInfo(size.x, size.y);
    }


    @Override
    public void onDestroy() {
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }


}
