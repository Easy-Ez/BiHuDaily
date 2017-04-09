package cn.ml.saddhu.bihudaily.engine.push.huawei;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.huawei.hms.support.api.push.PushReceiver;
import com.orhanobut.logger.Logger;

/**
 * Created by sadhu on 2017/4/8.
 * Email static.sadhu@gmail.com
 * Describe: 华为推送
 */
public class HuaWeiPushReceiver extends PushReceiver {
    @Override
    public void onToken(Context context, String token, Bundle extras) {
        super.onToken(context, token, extras);
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " +
                belongId;
        Logger.d(content);
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPushState(Context context, boolean pushState) {
        super.onPushState(context, pushState);
        try {
            String content = "查询push通道状态： " + (pushState ? "已连接" : "未连接");
            Logger.d(content);
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPushMsg(Context context, byte[] bytes, Bundle bundle) {
        try {
            String content = "收到一条Push消息： " + new String(bytes, "UTF-8");
            Logger.d(content);
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onEvent(Context context, Event event, Bundle bundle) {
        if (Event.NOTIFICATION_OPENED.equals(event) ||
                Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = bundle.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
            String content = "收到通知附加消息： " +
                    bundle.getString(BOUND_KEY.pushMsgKey);
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
            Logger.d(content);
        }
        super.onEvent(context, event, bundle);
    }
}
