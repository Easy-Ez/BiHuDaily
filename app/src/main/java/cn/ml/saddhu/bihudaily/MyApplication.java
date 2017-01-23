package cn.ml.saddhu.bihudaily;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

/**
 * Created by sadhu on 2016/11/7.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("cai")
                .methodCount(1)
                .hideThreadInfo();
        Fresco.initialize(this);
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());
        DBHelper.getInstance().init(this);
    }
}
