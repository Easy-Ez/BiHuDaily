package cn.ml.saddhu.bihudaily.engine.imageloader;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.ml.saddhu.bihudaily.engine.util.FileUtils;
import cn.ml.saddhu.bihudaily.engine.util.MD5Utils;

/**
 * Created by sadhu on 2017/2/26.
 * Email static.sadhu@gmail.com
 * Describe: 下载详情中的图片
 */
// FIXME: 2017/3/16 生产者 消费者 下载队列 存在严重已知问题 了解清楚后修复
public class ImageDownloadManager {
    private static final int DEFAULT_TIME_OUT = 1000;
    private static final String TAG = "ImageDownloadManager log: %s";
    private static ImageDownloadManager mInstance;
    private final ExecutorService executorService;
    private LinkedList<DownloadRunnable> mQueue;
    private Map<String, GlobalDownloadListener> mGlobalListenerByUrl = new HashMap<>();
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

    public boolean checkInTask(String url) {
        Iterator<DownloadRunnable> iterator = mQueue.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getUrl().equals(url))
                return true;
        }
        return false;
    }

    public void registerGlobalListener(String key, GlobalDownloadListener listener) {
        GlobalDownloadListener globalDownloadListeners = mGlobalListenerByUrl.get(key);
        if (globalDownloadListeners == null) {
            mGlobalListenerByUrl.put(key, listener);
        }
    }

    public void unRegisterGlobalListener(String key) {
        mGlobalListenerByUrl.remove(key);
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
                if (mGlobalListenerByUrl.get(url) != null) {
                    mGlobalListenerByUrl.get(url).onSuccess(saveFile.getAbsolutePath(), url);
                }
            } else {
                Logger.i("donwload error");
                listener.onError(url);
                if (mGlobalListenerByUrl.get(url) != null) {
                    mGlobalListenerByUrl.get(url).onError();
                }
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
            File file = FileUtils.checkStoryImageInCahe(key);
            if (file != null) {
                return file;
            }
            file = new File(FileUtils.getStoryImageCacheFile(), key);
            URL urlPath = new URL(url);
            conn = (HttpURLConnection) urlPath.openConnection();
            conn.setConnectTimeout(DEFAULT_TIME_OUT);
            inputStream = conn.getInputStream();
            outputStream = new FileOutputStream(file);
            int totalLength = conn.getContentLength();
            int currentLength = 0;
            byte[] data = new byte[1024 * 4];
            int read;
            while ((read = inputStream.read(data)) != -1) {
                currentLength += read;
                outputStream.write(data, 0, read);
                Logger.i("download: " + url + " : " + currentLength + "/" + totalLength);
            }
            outputStream.flush();
            File renameFile = new File(file.getParentFile(), file.getName() + 1);
            file.renameTo(renameFile);
            Logger.i("save file" + renameFile.getAbsolutePath());
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

    public interface DownloadListener {
        void onSuccuss(String oldUrl, String newFile);

        void onError(String oldUrl);
    }

    public interface GlobalDownloadListener {
        void onError();

        void onSuccess(String localPath, String orginPath);
    }

}
