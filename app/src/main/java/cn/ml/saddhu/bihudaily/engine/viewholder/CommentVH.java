package cn.ml.saddhu.bihudaily.engine.viewholder;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Date;
import java.util.Locale;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
import cn.ml.saddhu.bihudaily.engine.util.SpanUtils;
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
    private CommentBean mBean;
    private OnCommentViewClickedListener mListener;

    public CommentVH(View itemView, OnCommentViewClickedListener listener) {
        super(itemView);
        mListener = listener;
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
        itemView.setOnClickListener(this);
    }

    public void setData(CommentBean bean) {
        mBean = bean;
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
                if (bean.realLine > 0 && bean.realLine <= 2) {
                    commentRepliedContent.setOnLayoutListener(null);
                    commentRepliedContent.setMaxLines(2);
                    commentExpandButton.setVisibility(View.GONE);
                } else if (bean.realLine > 2) {
                    commentRepliedContent.setMaxLines(bean.isExpand ? 100 : 2);
                    commentRepliedContent.setOnLayoutListener(null);
                    commentExpandButton.setVisibility(View.VISIBLE);
                    commentExpandButton.setText(bean.isExpand ? R.string.comment_colspan : R.string.comment_expand);
                } else {
                    commentRepliedContent.setOnLayoutListener(this);
                    commentRepliedContent.setMaxLines(100);
                }
                commentRepliedContent.setText(TextUtils.concat(new CharSequence[]{SpanUtils.addAuthSpan(itemView.getContext(), bean.reply_to.author),
                        SpanUtils.addReplySpan(itemView.getContext(), bean.reply_to.content)}));
                commentRepliedContent.setVisibility(View.VISIBLE);
                commentRepliedErrorMessage.setVisibility(View.GONE);
            }
        } else {
            commentRepliedContent.setOnLayoutListener(null);
            commentRepliedContent.setVisibility(View.GONE);
            commentExpandButton.setVisibility(View.GONE);
        }
        commentItemTime.setText(mSdf.format(new Date(bean.time * 1000)));
    }

    @Override
    public void onLayout(final TextView textView) {
        // 还未计算出实际长度
        mBean.realLine = textView.getLineCount();
        if (mBean.realLine <= 2) {
            commentExpandButton.setVisibility(View.GONE);
        } else {
            commentExpandButton.post(new Runnable() {
                @Override
                public void run() {
                    commentRepliedContent.setMaxLines(2);
                    commentExpandButton.setText(R.string.comment_expand);
                    commentExpandButton.setVisibility(View.VISIBLE);
                }
            });
            String charSequence = textView.getText().toString();
            float width = (float) textView.getWidth();
            int lineStart = textView.getLayout().getLineStart(1);
            int lineEnd = textView.getLayout().getLineEnd(1);
            String substring = charSequence.substring(0, lineStart);
            substring = substring.substring(substring.indexOf("：") + 1, substring.length());
            Paint paint = new Paint();
            paint.setTextSize(itemView.getResources().getDimension(R.dimen.text_size_16));
            float measureText = paint.measureText("...");
            charSequence = charSequence.substring(lineStart, lineEnd);
            charSequence = substring + (charSequence.substring(0, paint.breakText(charSequence, false, width - measureText, null)) + "...");
            SpannableString a = SpanUtils.addAuthSpan(itemView.getContext(), mBean.reply_to.author);
            SpannableString b = SpanUtils.addReplySpan(itemView.getContext(), charSequence);
            commentRepliedContent.setOnLayoutListener(null);
            textView.setText(TextUtils.concat(new CharSequence[]{a, "", b}));
        }
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view.getId() == R.id.comment_expand_button) {
                mListener.onExpandViewClick(mBean, getAdapterPosition());
            } else {
                mListener.onCommentItemClick(mBean, getAdapterPosition());
            }
        }
    }

    public static interface OnCommentViewClickedListener {
        void onExpandViewClick(CommentBean bean, int position);

        void onCommentItemClick(CommentBean bean, int position);
    }
}
