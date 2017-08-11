package cn.ml.saddhu.bihudaily.mvp.view;

/**
 * Created by sadhu on 2017/3/27.
 * Email static.sadhu@gmail.com
 * Describe: 所有能加载更得的view 都应该实现该接口
 */
public interface IBaseListLoadmoreView<L> extends IBaseView {
    void onLoadMoreSuccuess(L data);

    void onLoadMoreError(int code);
}
