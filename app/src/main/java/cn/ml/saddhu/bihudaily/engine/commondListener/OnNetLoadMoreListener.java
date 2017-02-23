package cn.ml.saddhu.bihudaily.engine.commondListener;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface OnNetLoadMoreListener<L> {
    void onLoadMoreSuccess(L l);

    void onLoadMoreError(int code);
}
