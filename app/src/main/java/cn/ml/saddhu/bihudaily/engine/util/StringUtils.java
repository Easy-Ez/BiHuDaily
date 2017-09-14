package cn.ml.saddhu.bihudaily.engine.util;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import java.text.NumberFormat;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.widget.LayoutTextView;

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
}
