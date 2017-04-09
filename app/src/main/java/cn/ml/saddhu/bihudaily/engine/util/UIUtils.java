package cn.ml.saddhu.bihudaily.engine.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

/**
 * Created by sadhu on 2017/4/9.
 * Email static.sadhu@gmail.com
 * Describe: UI 相关的工具类
 */
public class UIUtils {
    public static int dp2px(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    /**
     * 状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * actionbar 高度
     *
     * @param context
     * @return
     */
    public static int getActionBarSize(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarSize;
    }

    /**
     * 屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * windows 区域高度
     *
     * @param context
     * @return
     */
    public static int getWindowsHeight(Context context) {
        return getScreenHeight(context) - getStatusHeight(context);
    }

    /**
     * 内容区域高度
     * @param context
     * @return
     */
    public static int getContentHeight(Context context) {
        return getWindowsHeight(context) - getActionBarSize(context);
    }
}
