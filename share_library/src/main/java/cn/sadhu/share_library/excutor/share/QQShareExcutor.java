package cn.sadhu.share_library.excutor.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import cn.sadhu.share_library.R;
import cn.sadhu.share_library.callback.IShareCallback;
import cn.sadhu.share_library.domain.ErrorInfo;
import cn.sadhu.share_library.domain.PlatformType;
import cn.sadhu.share_library.domain.ShareInfoBean;

/**
 * Created by sadhu on 2017/5/12.
 * QQ 分享
 */
public class QQShareExcutor implements IShareExcutor {

    private IUiListener mListener;
    private IShareCallback callback;

    public QQShareExcutor() {
        mListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                callback.onComplete(PlatformType.QQ);
            }

            @Override
            public void onError(UiError uiError) {
                callback.onError(PlatformType.QQ, new ErrorInfo(uiError.errorCode, uiError.errorMessage));
            }

            @Override
            public void onCancel() {
                callback.onCancel(PlatformType.QQ);
            }
        };
    }

    @Override
    public void share(Activity activity, ShareInfoBean infoBean, IShareCallback callback) {
        this.callback = callback;
        Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, infoBean.getTittle());
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, infoBean.getSummary());
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, infoBean.getTargetUrl());
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, infoBean.getImageUrl());
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        Tencent tencent = Tencent.createInstance(activity.getString(R.string.qq_app_id), activity.getApplication());
        tencent.shareToQQ(activity, bundle, mListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
    }
}
