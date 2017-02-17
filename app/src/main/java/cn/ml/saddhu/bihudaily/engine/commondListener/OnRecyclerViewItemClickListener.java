package cn.ml.saddhu.bihudaily.engine.commondListener;

/**
 * Created by sadhu on 2017/2/17.
 * Email static.sadhu@gmail.com
 */
public interface OnRecyclerViewItemClickListener<T> {
    /**
     * 列表item被点击回调
     *
     * @param t            数据
     * @param position     数据在list集合中的位置
     * @param realPosition 真实位置
     */
    void onItemClick(T t, int position, int realPosition);
}
