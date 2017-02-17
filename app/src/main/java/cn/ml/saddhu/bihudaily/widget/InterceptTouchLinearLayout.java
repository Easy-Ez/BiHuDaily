package cn.ml.saddhu.bihudaily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by sadhu on 2017/2/17.
 * Email static.sadhu@gmail.com
 * 当linearLayout子view会抢夺touch事件,而linearLayout需要touch事件时,使用此类
 *
 * @see {@link cn.ml.saddhu.bihudaily.mvp.adapter.ThemePageAdapter.ThemeHeaderVH#ThemeHeaderVH(View)}
 */
public class InterceptTouchLinearLayout extends LinearLayout {
    public InterceptTouchLinearLayout(Context context) {
        super(context);
    }

    public InterceptTouchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptTouchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
