package cn.ml.saddhu.bihudaily.mvp.view;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;

/**
 * Created by sadhu on 2017/3/27.
 * Email static.sadhu@gmail.com
 * Describe: 评论list
 */
public interface ICommentsListView extends IBaseListView<List<CommentBean>, List<CommentBean>> {
    void scroll2ShortBar();
}
