package cn.sadhu.daynightlibrary.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.sadhu.daynightlibrary.R;

/**
 * Created by sadhu on 2017/2/8.
 * Email static.sadhu@gmail.com
 */
public class DayNightSpUtil {
    private static final int THEME_MODE_DAY = 1;
    private static final int THEME_MODE_NIGHT = 2;

    /**
     * 获取当前模式
     *
     * @return
     */
    public static int getThemeMode(Application ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(ctx.getResources().getString(R.string.sp_name),
                Context.MODE_PRIVATE);
        int mode = sp.getInt(ctx.getResources().getString(R.string.sp_theme_mode),
                THEME_MODE_DAY);
        if (mode < 1 || mode > 2) {
            mode = THEME_MODE_DAY;
        }
        return mode;
    }

    /**
     * 设置当前模式
     *
     * @param ctx  上下文
     * @param mode 模式 @see {@link ThemeMode}
     */
    public static void setThemeMode(Application ctx, @ThemeMode int mode) {
        SharedPreferences sp = ctx.getSharedPreferences(ctx.getResources().getString(R.string.sp_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(ctx.getResources().getString(R.string.sp_theme_mode),
                mode);
        edit.apply();
    }

    @IntDef({THEME_MODE_DAY, THEME_MODE_NIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface ThemeMode {
    }
}
