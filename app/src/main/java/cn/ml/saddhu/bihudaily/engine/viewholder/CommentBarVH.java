package cn.ml.saddhu.bihudaily.engine.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/4/2.
 * Email static.sadhu@gmail.com
 * Describe: 短评bar vh
 */
public class CommentBarVH extends RecyclerView.ViewHolder implements View.OnClickListener {
    private boolean isShortBar;
    private ImageView img_comment_expand;

    public CommentBarVH(View itemView, int commentNum) {
        this(itemView, commentNum, false);
    }

    public CommentBarVH(View itemView, int commentNum, boolean isShortBar) {
        super(itemView);
        this.isShortBar = isShortBar;
        TextView tv_comment_num = (TextView) itemView.findViewById(R.id.tv_comment_num);
        img_comment_expand = (ImageView) itemView.findViewById(R.id.img_comment_expand);
        if (isShortBar) {
            tv_comment_num.setText(itemView.getContext().getString(R.string.short_comment_count, commentNum));
            img_comment_expand.setOnClickListener(this);
            img_comment_expand.setVisibility(View.VISIBLE);
        } else {
            tv_comment_num.setText(itemView.getContext().getString(R.string.long_comment_count, commentNum));
        }
    }

    @Override
    public void onClick(View v) {
    }
}
