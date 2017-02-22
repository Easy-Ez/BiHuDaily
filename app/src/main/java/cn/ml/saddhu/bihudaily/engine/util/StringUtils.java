package cn.ml.saddhu.bihudaily.engine.util;

import java.text.NumberFormat;

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
            instance.setMaximumFractionDigits(2);
            result = instance.format(num / 1000.0) + "K";
        }
        return result;
    }
}
