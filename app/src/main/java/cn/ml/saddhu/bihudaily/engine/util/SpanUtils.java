package cn.ml.saddhu.bihudaily.engine.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/9/14.
 * 描述 span工具类
 */
public class SpanUtils {

    public static SpannableString addAuthSpan(Context context, String str) {
        String str2 = "//" + str + "：";
        SpannableString spannableString = new SpannableString(str2);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.comment_item_author_reply)), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString addReplySpan(Context context, String str) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.comment_item_reply_comment_content)), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
