package cn.ml.saddhu.bihudaily.mvp.model;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.commondListener.NetCallback;
import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
import cn.ml.saddhu.bihudaily.engine.domain.VoteResultBean;

/**
 * Created by sadhu on 2017/4/9.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface CommentsModel extends BaseModel {

    /**
     * 获取长评
     *
     * @param storyId  storyId
     * @param callback 回调
     */
    void getLongCommentsList(String storyId, NetCallback<List<CommentBean>> callback);

    /**
     * 获取更多长评
     *
     * @param storyId   sotryId
     * @param commentId 最后一条长评Id
     * @param callback  回调
     */
    void getMoreLongCommentsList(String storyId, String commentId, NetCallback<List<CommentBean>> callback);

    /**
     * 获取短评
     *
     * @param storyId  storyId
     * @param callback 回调
     */
    void getShortCommentsList(String storyId, NetCallback<List<CommentBean>> callback);

    /**
     * 获取更多长评
     *
     * @param storyId   sotryId
     * @param commentId 最后一条短评Id
     * @param callback  回调
     */
    void getMoreShortCommentsList(String storyId, String commentId, NetCallback<List<CommentBean>> callback);

    /**
     * 点赞
     *
     * @param bean     具体的评论
     * @param callback 回调
     */
    void voteComment(CommentBean bean, NetCallback<VoteResultBean> callback);
}
