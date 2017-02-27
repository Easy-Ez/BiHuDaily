package cn.ml.saddhu.bihudaily.engine.imageloader;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.ml.saddhu.bihudaily.engine.util.FileUtils;
import cn.ml.saddhu.bihudaily.engine.util.MD5Utils;

/**
 * Created by sadhu on 2017/2/26.
 * Email static.sadhu@gmail.com
 * Describe:
 * // FIXME: 2017/2/27  queue 可能会崩溃
 */
public class ImageDownloadManager {
    private static final String TAG = "ImageDownloadManager log: %s";
    private static ImageDownloadManager mInstance;
    private final ExecutorService executorService;
    private LinkedList<DownloadRunnable> mQueue;
    private boolean taskActive;
    private ExecutorService mPostExecutors = Executors.newFixedThreadPool(2);
    private ExecutorService mTaskExecutors = Executors.newFixedThreadPool(2);

    public static ImageDownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (ImageDownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new ImageDownloadManager();
                }
            }
        }
        return mInstance;
    }

    private ImageDownloadManager() {
        executorService = Executors.newCachedThreadPool();
        mQueue = new LinkedList<>();
    }

    public void addTask(final String url, final DownloadListener listener) {
        mPostExecutors.execute(new Runnable() {
            @Override
            public void run() {
                if (checkInTask(url)) {
                    return;
                }
                mQueue.addFirst(new DownloadRunnable(url, listener));
                Logger.i(TAG, "addTask is invoke");
                if (!taskActive) {
                    Logger.i(TAG, "startLooper");
                    taskActive = true;
                    startLooper();
                }
            }
        });
    }

    private boolean checkInTask(String url) {
        for (DownloadRunnable runnable : mQueue) {
            if (runnable.getUrl().equals(url)) {
                return true;
            }
        }
        return false;
    }

    private void startLooper() {
        mTaskExecutors.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Runnable runnable = mQueue.poll();
                    if (runnable == null) {
                        synchronized (this) {
                            // Check again, this time in synchronized
                            runnable = mQueue.poll();
                            if (runnable == null) {
                                taskActive = false;
                                Logger.i(TAG, "taskActive is false");
                                return;
                            }
                        }
                    }
                    Logger.i(TAG, "task submit");
                    executorService.submit(runnable);
                }
            }
        });
    }


    private class DownloadRunnable implements Runnable {
        String url;
        DownloadListener listener;

        DownloadRunnable(String url, DownloadListener listener) {
            this.url = url;
            this.listener = listener;
        }

        @Override
        public void run() {
            File saveFile = download(url);
            if (saveFile != null) {
                listener.onSuccuss(url, saveFile.getAbsolutePath());
            }
        }

        public String getUrl() {
            return url;
        }
    }

    private File download(String url) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        HttpURLConnection conn = null;
        try {
            String key = MD5Utils.getMD5(url);
            // 先判断本地有木有
            File file = new File(FileUtils.getStoryImageCacheFile(), key + 1);
            if (file.exists()) {
                Logger.i(TAG, "find cache file");
                return file;
            }
            file = new File(FileUtils.getStoryImageCacheFile(), key);
            URL urlPath = new URL(url);
            conn = (HttpURLConnection) urlPath.openConnection();
            inputStream = conn.getInputStream();
            outputStream = new FileOutputStream(file);
            byte[] data = new byte[1024 * 4];
            int read;
            while ((read = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, read);
            }
            outputStream.flush();
            File renameFile = new File(file.getParentFile(), file.getName() + 1);
            file.renameTo(renameFile);
            Logger.i(TAG, "save file" + renameFile.getAbsolutePath());
            return renameFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static interface DownloadListener {
        void onSuccuss(String oldUrl, String newFile);
    }
}
