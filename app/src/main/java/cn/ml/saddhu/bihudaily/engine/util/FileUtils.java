package cn.ml.saddhu.bihudaily.engine.util;

import android.os.Environment;

import com.orhanobut.logger.Logger;

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

    public static File checkStoryImageInCahe(String md5Key) {
        // 先判断本地有木有
        File file = new File(FileUtils.getStoryImageCacheFile(), md5Key + 1);
        if (file.exists()) {
            Logger.i("find cache file");
            return file;
        } else {
            return null;
        }
    }
}
