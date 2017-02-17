package cn.ml.saddhu.bihudaily.engine.commondListener;

/**
 * Created by sadhu on 2017/2/17.
 * Email static.sadhu@gmail.com
 */
public interface ItemClickable<T> {
    void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener<T> listener);
}
