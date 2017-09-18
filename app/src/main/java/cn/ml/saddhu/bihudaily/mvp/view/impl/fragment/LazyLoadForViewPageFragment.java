package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.orhanobut.logger.Logger;

/**
 * Created by sadhu on 2017/2/24.
 * Email static.sadhu@gmail.com
 * 在viewpager中懒加载的fragment继承此fragment
 *
 *║ StoryDetailFragment_{fec691f}getUserVisibleHint:false
 *║ StoryDetailFragment_{fec691f}prepareFetchData
 *║ StoryDetailFragment_{fec691f}getUserVisibleHint:true
 *║ StoryDetailFragment_{fec691f}prepareFetchData
 *║ StoryDetailFragment_{fec691f}onActivityCreated
 *║ StoryDetailFragment_{fec691f}prepareFetchData
 *║ StoryDetailFragment_{fec691f}lazyLoadData
 */
public abstract class LazyLoadForViewPageFragment extends BaseFragment {
    private final String TAG = LazyLoadForViewPageFragment.this.toString();
    private boolean isViewInitiated;
    private boolean isUserVisible;
    private boolean isDataInitiated;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        Logger.i(TAG + "onActivityCreated");
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUserVisible = isVisibleToUser;
        Logger.i(TAG + "getUserVisibleHint:" + getUserVisibleHint());
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        Logger.i(TAG + "prepareFetchData");
        if (isUserVisible && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            Logger.i(TAG + "lazyLoadData");
            lazyLoadData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    abstract void lazyLoadData();

}
