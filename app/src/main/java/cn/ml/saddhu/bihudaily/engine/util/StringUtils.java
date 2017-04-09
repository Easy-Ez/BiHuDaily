package cn.ml.saddhu.bihudaily.engine.util;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import java.text.NumberFormat;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe: 字符串工具类
 */
public class StringUtils {
    public static String integer2StringWithThousand(int num) {
        String result = String.valueOf(num);
        if (num >= 1000) {
            NumberFormat instance = NumberFormat.getInstance();
            instance.setMaximumFractionDigits(1);
            result = instance.format(num / 1000.0) + "K";
        }
        return result;
    }

    public static void setAuthFixBoldSpan(TextView tv, String content, String authName) {
        String fix = tv.getContext().getResources().getString(R.string.reply_content_fix, authName);
        content = fix + content;
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        builder.setSpan(span, 0, fix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
    }
}
