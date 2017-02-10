package cn.ml.saddhu.bihudaily.engine.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.ml.saddhu.bihudaily.R;


/**
 * Created by sadhu on 2017/2/8.
 * Email static.sadhu@gmail.com
 */
public class DayNightSpUtil {
    public static final int THEME_MODE_LIGHT = 1;
    public static final int THEME_MODE_DARK = 2;
    private final Context mContext;

    public DayNightSpUtil(Context ctx) {
        mContext = ctx;
    }

    /**
     * 获取当前模式
     *
     * @return
     */
    public int getThemeMode() {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getResources().getString(R.string.sp_theme_file_name),
                Context.MODE_PRIVATE);
        int mode = sp.getInt(mContext.getResources().getString(R.string.sp_key_name),
                THEME_MODE_LIGHT);
        if (mode < 1 || mode > 2) {
            mode = THEME_MODE_LIGHT;
        }
        return mode;
    }

    public boolean isLight() {
        return getThemeMode() == THEME_MODE_LIGHT;
    }

    public boolean isDark() {
        return getThemeMode() == THEME_MODE_DARK;
    }

    /**
     * 设置当前模式
     *
     * @param mode 模式 @see {@link ThemeMode}
     */
    public void setThemeMode(@ThemeMode int mode) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getResources().getString(R.string.sp_theme_file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(mContext.getResources().getString(R.string.sp_key_name),
                mode);
        edit.apply();
    }

    @IntDef({THEME_MODE_LIGHT, THEME_MODE_DARK})
    @Retention(RetentionPolicy.SOURCE)
    @interface ThemeMode {
    }
}
