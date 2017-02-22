package cn.ml.saddhu.bihudaily.engine.commondListener;

import android.support.annotation.FloatRange;

/**
 * Created by sadhu on 2017/2/21.
 * Email static.sadhu@gmail.com
 * Describe: 文章详情页 导致toolbar 状态变化的listener
 */
public interface OnToolBarNeedChangeListener {
    void setToolBarAlpha(@FloatRange(from = 0.0f, to = 1.0f) float alpha);

    void hideToolbar(boolean hide);
}
