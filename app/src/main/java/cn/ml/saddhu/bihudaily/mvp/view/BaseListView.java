package cn.ml.saddhu.bihudaily.mvp.view;

/**
 * Created by sadhu on 2017/2/12.
 * Email static.sadhu@gmail.com
 * Describe: 所以能刷新.加载更多的界面都应该实现该接口或其子类
 */
public interface BaseListView<R, L> extends BaseView {
    void onRefreshSucces(R data);

    void onLoadMoreSuccuess(L data);

    void onRefreshError(int code);

    void onLoadMoreError(int code);
}