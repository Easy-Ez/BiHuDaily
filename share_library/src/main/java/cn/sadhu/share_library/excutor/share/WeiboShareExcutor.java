package cn.sadhu.share_library.excutor.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;

import cn.sadhu.share_library.R;
import cn.sadhu.share_library.callback.IShareCallback;
import cn.sadhu.share_library.domain.ErrorInfo;
import cn.sadhu.share_library.domain.PlatformType;
import cn.sadhu.share_library.domain.ShareInfoBean;

/**
 * Created by sadhu on 2017/5/13.
 * 微博分享
 */
public class WeiboShareExcutor implements IShareExcutor {

    private WbShareHandler mWbShareHandler;
    private IShareCallback callback;

    @Override
    public void share(Activity activity, ShareInfoBean infoBean, IShareCallback callback) {
        this.callback = callback;
        mWbShareHandler = new WbShareHandler(activity);
        mWbShareHandler.registerApp();
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj();
        weiboMessage.mediaObject = getWebpageObj(activity, infoBean);
        mWbShareHandler.shareMessage(weiboMessage, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mWbShareHandler.doResultIntent(data, new WbShareCallback() {
            @Override
            public void onWbShareSuccess() {
                callback.onComplete(PlatformType.WEIBO);
            }

            @Override
            public void onWbShareCancel() {
                callback.onCancel(PlatformType.WEIBO);
            }

            @Override
            public void onWbShareFail() {
                callback.onError(PlatformType.WEIBO, new ErrorInfo(0, "分享失败"));
            }
        });
    }

    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = "医生站";
        textObject.title = "xxxx";
        textObject.actionUrl = "http://www.baidu.com";
        return textObject;
    }

    private WebpageObject getWebpageObj(Context ctx, ShareInfoBean infoBean) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = infoBean.getTittle();
        mediaObject.description = infoBean.getSummary();
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = infoBean.getTargetUrl();
        mediaObject.defaultText = infoBean.getTittle();
        return mediaObject;
    }
}
