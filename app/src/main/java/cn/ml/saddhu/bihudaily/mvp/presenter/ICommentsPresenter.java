package cn.ml.saddhu.bihudaily.mvp.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by sadhu on 2017/4/9.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface ICommentsPresenter extends IBasePresenter {
    /**
     * 设置评论数量
     *
     * @param shortNum
     * @param longNum
     */
    void setCommentsNum(int shortNum, int longNum);

    /**
     * 设置storyId
     *
     * @param mStoryId
     */
    void setStoryId(String mStoryId);

    /**
     * 获取adpater
     *
     * @return
     */
    RecyclerView.Adapter getAdapter();

    void getLongCommentsList( );

    void getMoreLongCommentsList();

    void getShortCommentsList();

    void getMoreShortCommentsList();


}
