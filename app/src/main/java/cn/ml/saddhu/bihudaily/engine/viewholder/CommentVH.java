package cn.ml.saddhu.bihudaily.engine.viewholder;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Date;
import java.util.Locale;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
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
    private int maxLine;
    private CommentBean mBean;
    private OnExpandViewClickedListener mListener;

    public CommentVH(View itemView, OnExpandViewClickedListener listener) {
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
        maxLine = 2;
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
                commentRepliedContent.setOnLayoutListener(this);
                commentRepliedContent.setText(TextUtils.concat(new CharSequence[]{a(bean.reply_to.author), b(bean.reply_to.content)}));
                if (bean.viewHasExpand) {
                    maxLine = 100;
                    commentRepliedContent.setMaxLines(100);
                } else {
                    maxLine = 2;
                    commentRepliedContent.setMaxLines(2);

                }
                commentRepliedContent.setVisibility(View.VISIBLE);
                commentRepliedErrorMessage.setVisibility(View.GONE);
            }
        } else {
            commentRepliedContent.setOnLayoutListener(null);
            commentRepliedContent.setVisibility(View.GONE);
        }
        commentItemTime.setText(mSdf.format(new Date(bean.time * 1000)));
    }

    @Override
    public void onLayout(final TextView textView) {
        if (textView.getLineCount() <= 2) {
            commentExpandButton.setVisibility(View.GONE);
        } else if (maxLine == 100) {
            commentExpandButton.setText(R.string.comment_colspan);
            commentExpandButton.setVisibility(View.VISIBLE);
        } else {
            commentExpandButton.post(new Runnable() {
                @Override
                public void run() {
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
            SpannableString a = a(mBean.reply_to.author);
            SpannableString b = b(charSequence);
            textView.setText(TextUtils.concat(new CharSequence[]{a, "", b}));
            commentRepliedContent.setOnLayoutListener(null);
        }

    }

    private SpannableString a(String str) {
        String str2 = "//" + str + "：";
        SpannableString spannableString = new SpannableString(str2);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(itemView.getContext(), R.color.comment_item_author_reply)), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private SpannableString b(String str) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(itemView.getContext(), R.color.comment_item_reply_comment_content)), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onClick(mBean, getAdapterPosition());
        }
    }

    public static interface OnExpandViewClickedListener {
        void onClick(CommentBean bean, int positio);
    }
}
