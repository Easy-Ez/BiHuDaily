package cn.sadhu.guidelibrary.util;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by sadhu on 2017/3/14.
 * Email static.sadhu@gmail.com
 * Describe:
 */

public class UIUtils {
    public static int dp2px(Context context, float dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public static Rect getViewOnScreenRect(View v) {
        Rect rect = new Rect();
        int[] outLocation = new int[2];
        v.getLocationOnScreen(outLocation);
        rect.left = outLocation[0];
        rect.top = outLocation[1];
        rect.right = rect.left + v.getWidth();
        rect.bottom = rect.top + v.getHeight();
        return rect;
    }
}
