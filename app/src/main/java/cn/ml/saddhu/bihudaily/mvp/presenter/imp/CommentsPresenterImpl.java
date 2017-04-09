package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.ml.saddhu.bihudaily.engine.adapter.CommentAdapter;
import cn.ml.saddhu.bihudaily.engine.commondListener.NetCallback;
import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
import cn.ml.saddhu.bihudaily.engine.domain.ErrorMsgBean;
import cn.ml.saddhu.bihudaily.mvp.model.CommentsModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.CommentsModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.CommentsPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.CommentsListView;

/**
 * Created by sadhu on 2017/4/9.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class CommentsPresenterImpl implements CommentsPresenter {
    private CommentsListView mView;
    private CommentsModel mModel;
    private CommentAdapter mAdapter;
    private List<CommentBean> mList;
    private String mStoryId;

    public CommentsPresenterImpl(CommentsListView view) {
        this.mView = view;
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
        mModel.getMoreLongCommentsList(mStoryId, String.valueOf(mList.get(mList.size() - 1).id), new NetCallback<List<CommentBean>>() {
            @Override
            public void onSuccess(List<CommentBean> comments) {
                mAdapter.addLongComments(comments);
            }

            @Override
            public void onError(ErrorMsgBean bean) {

            }
        });
    }

    @Override
    public void getShortCommentsList() {
        mModel.getShortCommentsList(mStoryId, new NetCallback<List<CommentBean>>() {

            @Override
            public void onSuccess(List<CommentBean> comments) {
                mAdapter.addShortComments(comments);
            }

            @Override
            public void onError(ErrorMsgBean bean) {

            }
        });
    }

    @Override
    public void getMoreShortCommentsList() {
        mModel.getMoreShortCommentsList(mStoryId, String.valueOf(mList.get(mList.size() - 1).id), new NetCallback<List<CommentBean>>() {
            @Override
            public void onSuccess(List<CommentBean> comments) {
                mAdapter.addShortComments(comments);
            }

            @Override
            public void onError(ErrorMsgBean bean) {

            }
        });
    }

    @Override
    public void onDestroy() {

    }


}
