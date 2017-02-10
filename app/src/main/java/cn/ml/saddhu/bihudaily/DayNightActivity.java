package cn.ml.saddhu.bihudaily;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import cn.ml.saddhu.bihudaily.engine.util.DayNightSpUtil;

/**
 * Created by sadhu on 2017/2/8.
 * Email static.sadhu@gmail.com
 */
public class DayNightActivity extends AppCompatActivity {

    protected final DayNightSpUtil mUtil = new DayNightSpUtil(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
    }

    /**
     * 初始化当前主题
     */
    private void initTheme() {
//        if (mUtil.isLight()) {
//            setTheme(R.style.AppTheme_NoActionBar_Light);
//        } else if (mUtil.isDark()) {
//            setTheme(R.style.AppTheme_NoActionBar_Dark);
//        }
    }

    /**
     * 切换主题模式
     *
     * @return 当前是否是夜间模式
     */
    protected final boolean toggleThemeSetting() {
//        boolean isDark = false;
//        if (mUtil.isLight()) {
//            mUtil.setThemeMode(DayNightSpUtil.THEME_MODE_DARK);
//            setTheme(R.style.AppTheme_NoActionBar_Dark);
//            isDark = true;
//        } else if (mUtil.isDark()) {
//            mUtil.setThemeMode(DayNightSpUtil.THEME_MODE_LIGHT);
//            setTheme(R.style.AppTheme_NoActionBar_Light);
//            isDark = false;
//        }
//        refreshStatusBar();
//        return isDark;
        return false;
    }


    /**
     * 刷新 StatusBar
     */
    private void refreshStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true);
            getWindow().setStatusBarColor(getResources().getColor(typedValue.resourceId));
        }
    }
}
