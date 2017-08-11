package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import com.huawei.android.pushagent.api.PushManager;

import cn.ml.saddhu.bihudaily.mvp.presenter.IMainPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.IMainView;

/**
 * Created by sadhu on 2017/2/20.
 * Email static.sadhu@gmail.com
 * Describe: 主界面presenter实现类
 */
public class MainPresenterImpl extends BasePresenter<IMainView> implements IMainPresenter {

    public MainPresenterImpl(IMainView iMainView) {
        super(iMainView);
    }

    @Override
    public void registerPush() {
        // 判断机型,选择合适的push方式
        PushManager.requestToken(mView.getContext());
    }

    @Override
    public void onDestroy() {

    }
}
