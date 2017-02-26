package cn.ml.saddhu.bihudaily.engine.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import cn.ml.saddhu.bihudaily.MyApplication;

/**
 * Created by sadhu on 2017/2/26.
 * Email static.sadhu@gmail.com
 * Describe:
 */

public class FileUtils {
    public static File getStoryImageCacheFile() {
        File file;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = MyApplication.mContext.getExternalCacheDir().getAbsoluteFile();
        } else {
            file = MyApplication.mContext.getCacheDir().getAbsoluteFile();
        }
        file = new File(file, "uil-images");
        if (!file.isDirectory() || !file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
