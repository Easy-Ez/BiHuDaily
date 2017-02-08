package cn.sadhu.daynightlibrary.widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by sadhu on 2017/2/8.
 * Email static.sadhu@gmail.com
 */
public class DayNightLinearLayout extends LinearLayout implements DayNightInterface {
    public DayNightLinearLayout(Context context) {
        super(context);
    }

    public DayNightLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DayNightLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DayNightLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {

    }
}
