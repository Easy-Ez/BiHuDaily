package cn.ml.saddhu.bihudaily.engine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
import cn.ml.saddhu.bihudaily.engine.viewholder.CommentBarVH;
import cn.ml.saddhu.bihudaily.engine.viewholder.CommentEmptyVH;
import cn.ml.saddhu.bihudaily.engine.viewholder.CommentVH;

/**
 * Created by sadhu on 2017/4/2.
 * Email static.sadhu@gmail.com
 * Describe: 评论adapter
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LONG_BAR = 1;
    private static final int TYPE_LONG_COMMENT = 2;
    private static final int TYPE_SHORT_BAR = 3;
    private static final int TYPE_SHORT_COMMENT = 4;
    private static final int TYPE_EMPTY = 5;

    private int shortCommentNumber;
    private int longCommentNumber;
    private List<CommentBean> mList;
    private int mLongListSize;
    private int mShortListSize;
    private boolean mIsEmpty;

    public CommentAdapter(List<CommentBean> list) {
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_LONG_BAR:
                vh = new CommentBarVH(inflater.inflate(R.layout.item_coments_bar, parent, false), longCommentNumber);
                break;
            case TYPE_LONG_COMMENT:
            case TYPE_SHORT_COMMENT:
                vh = new CommentVH(inflater.inflate(R.layout.item_coments, parent, false));
                break;
            case TYPE_SHORT_BAR:
                vh = new CommentBarVH(inflater.inflate(R.layout.item_coments_bar, parent, false), shortCommentNumber, true);
                break;
            case TYPE_EMPTY:
                vh = new CommentEmptyVH(inflater.inflate(R.layout.item_comments_empty, parent, false));
                break;
        }
        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentVH) {
            switch (getItemViewType(position)) {
                case TYPE_LONG_COMMENT:
                    // 减去long_bar
                    ((CommentVH) holder).setData(mList.get(position - 1));
                    break;
                case TYPE_SHORT_COMMENT:
                    // 减去 longsize long_bar short_bar
                    ((CommentVH) holder).setData(mList.get(position - mLongListSize - 2));
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return mIsEmpty ? 1 + 2 : mList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (mIsEmpty) {
            if (position == 0) {
                return TYPE_LONG_BAR;
            } else if (position == 1) {
                return TYPE_EMPTY;
            } else {
                return TYPE_SHORT_BAR;
            }
        } else {
            if (position == 0) {
                return TYPE_LONG_BAR;
            } else if (position < mLongListSize + 1) {
                return TYPE_LONG_COMMENT;
            } else if (position == mLongListSize + 1) {
                return TYPE_SHORT_BAR;
            } else {
                return TYPE_SHORT_COMMENT;
            }
        }

    }

    public void setShortCommentNumber(int shortCommentNumber) {
        this.shortCommentNumber = shortCommentNumber;
    }

    public void setLongCommentNumber(int longCommentNumber) {
        this.longCommentNumber = longCommentNumber;
    }

    public void setIsEmpty(boolean mIsEmpty) {
        this.mIsEmpty = mIsEmpty;
        notifyDataSetChanged();
    }

    /**
     * 第一次
     *
     * @param comments
     */
    public void setLongComments(List<CommentBean> comments) {
        mList.clear();
        mList.addAll(comments);
        mLongListSize = comments.size();
        notifyDataSetChanged();
    }

    public void addLongComments(List<CommentBean> comments) {
        // 加上 long bar  的位置
        int position = mLongListSize + 1;
        mList.addAll(comments);
        mLongListSize += comments.size();
        notifyItemInserted(position);
    }

    public void addShortComments(List<CommentBean> comments) {
        mShortListSize += comments.size();
        mList.addAll(comments);
        if (mIsEmpty) {
            // 前3个 分别是 long_bar empty short_bar
            notifyItemInserted(3);
        } else {
            // 加上 long_bar以及short_bar 的位置
            int position = mLongListSize + 1 + 1;
            notifyItemInserted(position);
        }
    }
}
