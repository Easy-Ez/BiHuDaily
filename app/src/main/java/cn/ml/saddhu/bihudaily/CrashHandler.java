package cn.ml.saddhu.bihudaily;

import com.orhanobut.logger.Logger;

/**
 * Created by sadhu on 2016/11/8.
 * Email static.sadhu@gmail.com
 * Describe: 自定义异常处理
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger.e(e, e.getMessage());
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
