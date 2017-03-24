package cn.ml.saddhu.bihudaily.engine.imageloader;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import cn.ml.saddhu.bihudaily.engine.util.FileUtils;
import cn.ml.saddhu.bihudaily.engine.util.MD5Utils;

/**
 * Created by sadhu on 2017/2/26.
 * Email static.sadhu@gmail.com
 * Describe: 下载详情中的图片
 */
public class ImageDownloadManager {
    private static final int DEFAULT_TIME_OUT = 1000;
    private static final int CODE_DOWNLOAD_SUCCESS = 1;
    private static final int CODE_DOWNLOAD_ERROR = 2;
    private static final String TAG = "ImageDownloadManager log: %s";
    private static ImageDownloadManager mInstance;
    private final ExecutorService executorService;
    private LinkedBlockingQueue<DownloadRunnable> mQueue;
    private List<String> mDownloadingUrl;
    private Map<String, GlobalDownloadListener> mGlobalListenerByUrl = new HashMap<>();
    private boolean taskActive;
    private ExecutorService mPostExecutors = Executors.newFixedThreadPool(2);
    private android.os.Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {


            switch (msg.what) {
                case CODE_DOWNLOAD_SUCCESS:
                    DonwloadSuccessInfo info = (DonwloadSuccessInfo) msg.obj;
                    GlobalDownloadListener listener = mGlobalListenerByUrl.get(info.orginPath);
                    if (listener != null) {
                        listener.onSuccess(info.localPath, info.orginPath);
                    }
                    break;
                case CODE_DOWNLOAD_ERROR:
                    String url = (String) msg.obj;
                    GlobalDownloadListener errorListener = mGlobalListenerByUrl.get(url);
                    if (errorListener != null) {
                        errorListener.onError();
                    }
                    break;
            }
        }
    };

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
        mQueue = new LinkedBlockingQueue<>();
        mDownloadingUrl = new CopyOnWriteArrayList<>();
    }

    public void addTask(final String url, final DownloadListener listener) {
        mPostExecutors.execute(new Runnable() {
            @Override
            public void run() {
                if (checkInTask(url)) {
                    return;
                }
                mDownloadingUrl.add(url);
                mQueue.add(new DownloadRunnable(url, listener));
                Logger.i(TAG, "addTask is invoke");
                if (!taskActive) {
                    Logger.i(TAG, "startLooper");
                    taskActive = true;
                    startLooper();
                }
            }
        });
    }

    public  boolean checkInTask(String url) {
        for (String downloadingUrl : mDownloadingUrl) {
            if (url.equals(downloadingUrl)) {
                return true;
            }
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
            mDownloadingUrl.remove(url);
            if (saveFile != null) {
                listener.onSuccuss(url, saveFile.getAbsolutePath());
                Message message = Message.obtain();
                message.what = CODE_DOWNLOAD_SUCCESS;
                message.obj = new DonwloadSuccessInfo(saveFile.getAbsolutePath(), url);
                handler.sendMessage(message);
            } else {
                listener.onError(url);
                Message message = Message.obtain();
                message.what = CODE_DOWNLOAD_ERROR;
                message.obj = url;
                handler.sendMessage(message);
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
            File file = FileUtils.checkStoryImageInCache(key);
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

    public class DonwloadSuccessInfo {
        public String localPath;
        public String orginPath;

        public DonwloadSuccessInfo(String localPath, String orginPath) {
            this.localPath = localPath;
            this.orginPath = orginPath;
        }
    }
}
