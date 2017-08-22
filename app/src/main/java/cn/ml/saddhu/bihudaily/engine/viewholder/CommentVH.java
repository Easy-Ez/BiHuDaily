package cn.ml.saddhu.bihudaily.engine.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
import cn.ml.saddhu.bihudaily.engine.util.StringUtils;
import cn.ml.saddhu.bihudaily.widget.LayoutTextView;

/**
 * Created by sadhu on 2017/4/2.
 * Email static.sadhu@gmail.com
 * Describe: 评论的vh
 */
public class CommentVH extends RecyclerView.ViewHolder implements LayoutTextView.OnLayoutListener, View.OnClickListener {

    private SimpleDraweeView commentItemAvatar;
    private TextView commentItemAuthor;
    private TextView commentItemLikeCount;
    private TextView commentItemContent;
    private LayoutTextView commentRepliedContent;
    private TextView commentRepliedErrorMessage;
    private RelativeLayout commentRepliedLayout;
    private TextView commentItemTime;
    private TextView commentExpandButton;

    private java.text.SimpleDateFormat mSdf;


    public CommentVH(View itemView) {
        super(itemView);

        commentItemAvatar = itemView.findViewById(R.id.comment_item_avatar);
        commentItemAuthor = itemView.findViewById(R.id.comment_item_author);
        commentItemLikeCount = itemView.findViewById(R.id.comment_item_like_count);
        commentItemContent = itemView.findViewById(R.id.comment_item_content);
        commentRepliedErrorMessage = itemView.findViewById(R.id.comment_replied_error_message);
        commentRepliedLayout = itemView.findViewById(R.id.comment_replied_layout);
        commentItemTime = itemView.findViewById(R.id.comment_item_time);
        commentExpandButton = itemView.findViewById(R.id.comment_expand_button);
        commentRepliedContent = itemView.findViewById(R.id.comment_replied_content);
        mSdf = new java.text.SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        commentExpandButton.setOnClickListener(this);
    }

    public void setData(CommentBean bean) {
        commentItemAvatar.setImageURI(bean.avatar);
        commentItemAuthor.setText(bean.author);
        commentItemLikeCount.setText(String.valueOf(bean.likes));
        commentItemContent.setText(bean.content);
        if (bean.reply_to != null) {
            if (bean.reply_to.status != 0) {
                commentRepliedContent.setVisibility(View.GONE);
                commentRepliedContent.setOnLayoutListener(null);
                commentRepliedErrorMessage.setVisibility(View.VISIBLE);
                commentRepliedErrorMessage.setText(bean.reply_to.error_msg);
            } else {
                commentRepliedContent.setVisibility(View.VISIBLE);
                commentRepliedContent.setOnLayoutListener(this);
                commentRepliedErrorMessage.setVisibility(View.GONE);
                StringUtils.setAuthFixBoldSpan(commentRepliedContent, bean.reply_to.content, bean.reply_to.author);
            }
        } else {
            commentRepliedContent.setOnLayoutListener(null);
            commentRepliedContent.setVisibility(View.GONE);
        }
        commentItemTime.setText(mSdf.format(new Date(bean.time * 1000)));
    }

    @Override
    public void onLayout(TextView paramTextView) {

    }

    @Override
    public void onClick(View view) {

    }
}
