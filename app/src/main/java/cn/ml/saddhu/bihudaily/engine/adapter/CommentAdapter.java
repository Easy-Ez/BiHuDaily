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
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CommentVH.OnExpandViewClickedListener {
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
                vh = new CommentVH(inflater.inflate(R.layout.item_coments, parent, false), this);
                break;
            case TYPE_SHORT_BAR:
                vh = new CommentBarVH(inflater.inflate(R.layout.item_coments_bar, parent, false), shortCommentNumber, true, mListener);
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
                    ((CommentVH) holder).setData(mList.get(getLongCommentRealPosition(position)));
                    break;
                case TYPE_SHORT_COMMENT:
                    // 减去 longsize long_bar short_bar
                    ((CommentVH) holder).setData(mList.get(getShortCommentRealPosition(position)));
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        if (mIsEmpty) {
            if ((mList == null || mList.size() == 0)) {
                // empty + bar
                return 1 + 2;
            } else {
                // empty + bar + comments size
                return 1 + 2 + mList.size();
            }
        } else {
            return mList.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsEmpty) {
            if ((mList == null || mList.size() == 0)) {
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
                } else if (position == 1) {
                    return TYPE_EMPTY;
                } else if (position == 2) {
                    return TYPE_SHORT_BAR;
                } else {
                    return TYPE_SHORT_COMMENT;
                }
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


    /**
     * 根据长评实际的index
     *
     * @param position
     * @return
     */
    private int getLongCommentRealPosition(int position) {
        return position - 1;
    }

    /**
     * 获取短评实际的index
     *
     * @param position
     * @return
     */
    private int getShortCommentRealPosition(int position) {
        if (mIsEmpty) {
            return position - 3;
        } else {
            return position - 2;
        }
    }

    /**
     * 设置短评bar上面的数字
     *
     * @param shortCommentNumber
     */
    public void setShortCommentNumber(int shortCommentNumber) {
        this.shortCommentNumber = shortCommentNumber;
    }

    public int getShortCommentNumber() {
        return shortCommentNumber;
    }

    public int getShortCommentListSize() {
        return mShortListSize;
    }

    /**
     * 设置长评bar上面的数字
     *
     * @param longCommentNumber
     */
    public void setLongCommentNumber(int longCommentNumber) {
        this.longCommentNumber = longCommentNumber;
    }

    public int getLongCommentNumber() {
        return longCommentNumber;
    }

    public int getLongCommentListSize() {
        return mLongListSize;
    }

    /**
     * 长评是否为空
     *
     * @param mIsEmpty
     */
    public void setIsEmpty(boolean mIsEmpty) {
        this.mIsEmpty = mIsEmpty;
        notifyDataSetChanged();
    }

    /**
     * 设置长评
     *
     * @param comments
     */
    public void setLongComments(List<CommentBean> comments) {
        mList.clear();
        mList.addAll(comments);
        mLongListSize = comments.size();
        notifyDataSetChanged();
    }

    /**
     * 添加长评
     *
     * @param comments
     */
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

    public void removeShortComent() {
        int totalSize = mList.size();
        mList.subList(mLongListSize, totalSize).clear();
        notifyItemRangeRemoved(getShorBarPosition(), totalSize - mLongListSize);

    }

    public int getShorBarPosition() {
        if (mIsEmpty) {
            // 前3个 分别是 long_bar empty short_bar
            return 2;
        } else {
            // 加上 long_bar以及short_bar 的位置
            return mLongListSize + 1 + 1 - 1;
        }
    }

    private OnCommentItemClickListener mListener;

    public void setItemClickListener(OnCommentItemClickListener listener) {
        this.mListener = listener;

    }

    @Override
    public void onClick(CommentBean bean, int position) {
        bean.isExpand = !bean.isExpand;
        notifyItemChanged(position);
    }


    public static interface OnCommentItemClickListener {
        void onShortBarClick(boolean isExpand);
    }
}
