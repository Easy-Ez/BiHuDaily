package cn.ml.saddhu.bihudaily.engine.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.BuildConfig;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.ml.saddhu.bihudaily.engine.util.MD5Utils;


/**
 * Created by sadhu on 2017/2/20.
 * Email static.sadhu@gmail.com
 */
public class ImageLoader {
    private static final String ERROR_NOT_INIT = "ImageLoader must be init with configuration before using";
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "ImageLoader configuration can not be initialized with null";

    private static ImageLoader mInstance;
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskCache;

    private ImageLoaderConfiguration configuration;

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }
        return mInstance;
    }

    public synchronized void init(ImageLoaderConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }
        if (this.configuration == null) {
            this.configuration = configuration;
            mMemoryCache = new LruCache<String, Bitmap>(configuration.mMaxMemoryCache) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount() / 1024;
                }
            };

            File diskCacheFile = getDiskCacheFile(configuration.context);
            if (!diskCacheFile.exists() || !diskCacheFile.isDirectory()) {
                diskCacheFile.mkdirs();
            }
            try {
                mDiskCache = DiskLruCache.open(diskCacheFile, getAppVersion(), 1, configuration.mMaxDiskCache);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkConfiguration() {
        if (configuration == null) {
            throw new IllegalStateException(ERROR_NOT_INIT);
        }
    }


    public void loadImage(ImageView imageView, String url) {
        checkConfiguration();
        imageView.setTag(url);
        // 从缓存中获取
        Bitmap bitmap = getFromMemoryCache(url);
        if (bitmap == null) {
            // 从网络获取
            DownloadTask downloadTask = new DownloadTask(imageView);
            downloadTask.execute(url);
        } else {
            Logger.i("load from memory");
            imageView.setImageBitmap(bitmap);
        }

    }

    public void clearDiskCache() {
        try {
            mDiskCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCacheFile(String url) {
        String key = MD5Utils.getMD5(url);
        File cacheFile;
        if (isCachedInDisk(key)) {
            cacheFile = new File(configuration.context.getCacheDir(), configuration.mDiskCacheDirName);
            return new File(cacheFile, key);
        } else {
            return null;
        }
    }

    public boolean isCachedInDisk(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskCache.get(key);
            return snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Bitmap getFromMemoryCache(String url) {
        return mMemoryCache.get(url);
    }

    private void addToMemoryCache(String url, Bitmap bm) {
        mMemoryCache.put(url, bm);
    }

    private int getAppVersion() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * 获取磁盘缓存地址
     *
     * @param ctx
     * @return
     */
    private File getDiskCacheFile(Context ctx) {
        File cacheFile;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            cacheFile = ctx.getExternalCacheDir();
//        } else {
        cacheFile = ctx.getCacheDir();
//        }
        cacheFile = new File(cacheFile, configuration.mDiskCacheDirName);
        return cacheFile;
    }

    private class DownloadTask extends AsyncTask<String, Long, TaskResult> {

        private WeakReference<ImageView> mImageViewWeakReference;

        DownloadTask(ImageView imageView) {
            mImageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected TaskResult doInBackground(String... url) {
            String urlStr = url[0];
            FileInputStream fileInputStream = null;
            FileDescriptor fd = null;
            Bitmap bm = null;
            try {
                String key = MD5Utils.getMD5(urlStr);
                DiskLruCache.Snapshot snapshot = mDiskCache.get(key);
                if (snapshot == null) {
                    DiskLruCache.Editor editor = mDiskCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadImage(urlStr, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                        mDiskCache.flush();
                    }
                    snapshot = mDiskCache.get(key);
                    if (snapshot != null) {
                        fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                        fd = fileInputStream.getFD();
                    }
                } else {
                    fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                    fd = fileInputStream.getFD();
                }


                if (fd != null) {
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
                if (bm != null) {
                    // 将Bitmap对象添加到内存缓存当中
                    addToMemoryCache(urlStr, bm);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return new TaskResult(bm, urlStr);
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            Logger.d("download progress:" + values[0]);
        }

        @Override
        protected void onPostExecute(TaskResult result) {
            super.onPostExecute(result);
            ImageView imageView = mImageViewWeakReference.get();
            if (imageView != null && result.bitmap != null && result.tag.equals(imageView.getTag())) {
                imageView.setImageBitmap(result.bitmap);
            }
        }


        private boolean downloadImage(String urlStr, OutputStream outputStream) {
            HttpURLConnection conn = null;
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            try {
                URL url = new URL(urlStr);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(configuration.mConnectTimeOut);
                conn.setReadTimeout(configuration.mReadTimeOut);
                in = new BufferedInputStream(conn.getInputStream());
                out = new BufferedOutputStream(outputStream);
                int fileLength = conn.getContentLength();
                byte data[] = new byte[4096];
                int count;
                long total = 0;
                while ((count = in.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) {
                        // only if total length is known
                        publishProgress((total * 100 / fileLength));
                    }
                    out.write(data, 0, count);
                    out.flush();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class TaskResult {
        public Bitmap bitmap;
        public String tag;

        public TaskResult(Bitmap bitmap, String tag) {
            this.bitmap = bitmap;
            this.tag = tag;
        }
    }
}
