package cn.ml.saddhu.bihudaily;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

import cn.ml.saddhu.bihudaily.engine.util.DayNightSpUtil;

/**
 * Created by sadhu on 2016/11/7.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DayNightSpUtil sp = new DayNightSpUtil(this);
        if (sp.isLight()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        Logger.init("cai")
                .methodCount(1)
                .hideThreadInfo();
        Fresco.initialize(this);
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());
        DBHelper.getInstance().init(this);
    }
}
