package cn.ml.saddhu.bihudaily.engine.viewholder;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.adapter.CommentAdapter;

/**
 * Created by sadhu on 2017/4/2.
 * Email static.sadhu@gmail.com
 * Describe: 短评bar vh
 */
public class CommentBarVH extends RecyclerView.ViewHolder implements View.OnClickListener {
    private boolean isShortBar;
    private ImageView img_comment_expand;
    private CommentAdapter.OnCommentItemClickListener listener;

    public CommentBarVH(View itemView, int commentNum) {
        this(itemView, commentNum, false, null);
    }

    public CommentBarVH(View itemView, int commentNum, boolean isShortBar, CommentAdapter.OnCommentItemClickListener listener) {
        super(itemView);
        this.isShortBar = isShortBar;
        this.listener = listener;
        TextView tv_comment_num = itemView.findViewById(R.id.tv_comment_num);
        img_comment_expand = itemView.findViewById(R.id.img_comment_expand);
        if (isShortBar) {
            tv_comment_num.setText(itemView.getContext().getString(R.string.short_comment_count, commentNum));
            itemView.setOnClickListener(this);
            img_comment_expand.setVisibility(View.VISIBLE);
        } else {
            tv_comment_num.setText(itemView.getContext().getString(R.string.long_comment_count, commentNum));
        }
    }

    @Override
    public void onClick(View v) {
        if (isShortBar && listener != null) {
            listener.onShortBarClick(img_comment_expand.getRotation() == 0);
            if (img_comment_expand.getRotation() == 0) {
                // 展开
                expandArrow();
            } else {
                // 收起
                collaspeArrow();
            }
        }
    }

    public void expandArrow() {
        ObjectAnimator.ofFloat(img_comment_expand, "rotation", 0, 180).setDuration(200).start();
    }

    public void collaspeArrow() {
        ObjectAnimator.ofFloat(img_comment_expand, "rotation", 180, 0).setDuration(200).start();
    }
}
