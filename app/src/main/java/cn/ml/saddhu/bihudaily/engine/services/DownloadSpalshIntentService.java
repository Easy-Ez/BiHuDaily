package cn.ml.saddhu.bihudaily.engine.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by sadhu on 2016/11/26.
 * Email static.sadhu@gmail.com
 * Describe: 下载封面图
 */
public class DownloadSpalshIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadSpalshIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
