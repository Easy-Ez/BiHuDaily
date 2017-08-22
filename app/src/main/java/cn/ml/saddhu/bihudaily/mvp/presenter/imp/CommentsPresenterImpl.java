package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.ml.saddhu.bihudaily.engine.adapter.CommentAdapter;
import cn.ml.saddhu.bihudaily.engine.commondListener.NetCallback;
import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
import cn.ml.saddhu.bihudaily.engine.domain.ErrorMsgBean;
import cn.ml.saddhu.bihudaily.mvp.model.CommentsModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.CommentsModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.ICommentsPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.ICommentsListView;

/**
 * Created by sadhu on 2017/4/9.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class CommentsPresenterImpl extends BasePresenter<ICommentsListView> implements ICommentsPresenter {

    private CommentsModel mModel;
    private CommentAdapter mAdapter;
    private List<CommentBean> mList;
    private String mStoryId;

    public CommentsPresenterImpl(ICommentsListView commentsListView) {
        super(commentsListView);
        mModel = new CommentsModelImpl();
        mList = new ArrayList<>();
        mAdapter = new CommentAdapter(mList);
    }


    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setCommentsNum(int shortNum, int longNum) {
        hasMore = longNum > 20;
        mAdapter.setShortCommentNumber(shortNum);
        mAdapter.setLongCommentNumber(longNum);
    }

    @Override
    public void setStoryId(String storyId) {
        mStoryId = storyId;
    }


    @Override
    public void getLongCommentsList() {
        mModel.getLongCommentsList(mStoryId, new NetCallback<List<CommentBean>>() {

            @Override
            public void onSuccess(List<CommentBean> comments) {
                if (comments == null || comments.size() == 0) {
                    // 没有长评
                    mAdapter.setIsEmpty(true);
                } else {
                    mAdapter.setLongComments(comments);
                }
            }

            @Override
            public void onError(ErrorMsgBean bean) {

            }
        });
    }

    @Override
    public void getMoreLongCommentsList() {
        isLoadingMore = true;
        mModel.getMoreLongCommentsList(mStoryId, String.valueOf(mList.get(mList.size() - 1).id), new NetCallback<List<CommentBean>>() {
            @Override
            public void onSuccess(List<CommentBean> comments) {
                isLoadingMore = false;
                mAdapter.addLongComments(comments);
                hasMore = mAdapter.getLongCommentNumber() > mAdapter.getLongCommentListSize();
            }

            @Override
            public void onError(ErrorMsgBean bean) {
                isLoadingMore = false;
            }
        });
    }

    @Override
    public void getShortCommentsList() {
        hasMore = mAdapter.getShortCommentNumber() > 20;
        mModel.getShortCommentsList(mStoryId, new NetCallback<List<CommentBean>>() {

            @Override
            public void onSuccess(List<CommentBean> comments) {
                mAdapter.addShortComments(comments);
                mView.scroll2ShortBar();
            }

            @Override
            public void onError(ErrorMsgBean bean) {
                mView.resetShorBarStatus(mAdapter.getShorBarPosition());
            }
        });
    }

    @Override
    public void getMoreShortCommentsList() {
        isLoadingMore = true;
        mModel.getMoreShortCommentsList(mStoryId, String.valueOf(mList.get(mList.size() - 1).id), new NetCallback<List<CommentBean>>() {
            @Override
            public void onSuccess(List<CommentBean> comments) {
                isLoadingMore = false;
                mAdapter.addShortComments(comments);
                hasMore = mAdapter.getShortCommentNumber() > mAdapter.getShortCommentListSize();
            }

            @Override
            public void onError(ErrorMsgBean bean) {
                isLoadingMore = false;
            }
        });
    }

    private boolean hasMore;
    private boolean isLoadingMore;

    @Override
    public void loadMoreComments() {
        Logger.d("loadMoreComments");
        if (hasMore && !isLoadingMore) {
            if (mAdapter.getLongCommentNumber() > mAdapter.getLongCommentListSize()) {
                // 加载更多长评
                getMoreLongCommentsList();
                Logger.d("loadMoreLongComments");
            } else {
                getMoreShortCommentsList();
                Logger.d("loadMoreShortComments");
            }
        }

    }

    @Override
    public void onDestroy() {

    }


}
