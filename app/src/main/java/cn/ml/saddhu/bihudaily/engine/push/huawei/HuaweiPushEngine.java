package cn.ml.saddhu.bihudaily.engine.push.huawei;

import android.content.Context;

import com.huawei.android.pushagent.PushManager;

import cn.ml.saddhu.bihudaily.engine.push.IPushEngine;

/**
 * Created by sadhu on 2017/6/14.
 */
public class HuaweiPushEngine implements IPushEngine {
    private Context mContext;
    private String token;

    public HuaweiPushEngine(Context context) {
        mContext = context;
    }

    @Override
    public void registerPush() {
        PushManager.requestToken(mContext);
    }

    @Override
    public void unRegisterPush() {
        PushManager.deregisterToken(mContext, token);
    }

    @Override
    public void setTag() {

    }
}
