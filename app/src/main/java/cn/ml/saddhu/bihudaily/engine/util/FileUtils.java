package cn.ml.saddhu.bihudaily.engine.util;

import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static File checkStoryImageInCache(String md5Key) {
        // 先判断本地有木有
        File file = new File(FileUtils.getStoryImageCacheFile(), md5Key + 1);
        if (file.exists()) {
            Logger.i("find cache file");
            return file;
        } else {
            return null;
        }
    }

    public static File getImageCacheFile(String url) {
        String key = MD5Utils.getMD5(url);
        return new File(FileUtils.getStoryImageCacheFile(), key + 1);
    }

    public static String getImageSaveName(String url) {
        String name = url.substring(url.lastIndexOf("/"));
        return name.replaceAll("-", "");
    }

    /**
     * 复制
     *
     * @param srcFile 源位置
     * @param destFile 目标位置
     */
    public static void copy(File srcFile, File destFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
