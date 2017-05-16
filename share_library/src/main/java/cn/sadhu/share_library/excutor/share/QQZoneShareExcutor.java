package cn.sadhu.share_library.excutor.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

import cn.sadhu.share_library.R;
import cn.sadhu.share_library.callback.IShareCallback;
import cn.sadhu.share_library.domain.ErrorInfo;
import cn.sadhu.share_library.domain.PlatformType;
import cn.sadhu.share_library.domain.ShareInfoBean;

/**
 * Created by sadhu on 2017/5/12.
 * QQ 分享分享
 */
public class QQZoneShareExcutor implements IShareExcutor {

    private IUiListener mListener;
    private IShareCallback callback;

    public QQZoneShareExcutor() {
        mListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                callback.onComplete(PlatformType.QQZOME);
            }

            @Override
            public void onError(UiError uiError) {
                callback.onError(PlatformType.QQZOME, new ErrorInfo(uiError.errorCode, uiError.errorMessage));
            }

            @Override
            public void onCancel() {
                callback.onCancel(PlatformType.QQZOME);
            }
        };
    }

    @Override
    public void share(Activity activity, ShareInfoBean infoBean, final IShareCallback callback) {
        this.callback = callback;
        ArrayList<String> objects = new ArrayList<>();
        objects.add(infoBean.getImageUrl());
        Bundle bundle = new Bundle();
        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, infoBean.getTittle());//必填
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, infoBean.getSummary());//选填
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, infoBean.getTargetUrl());//必填
        bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, objects);
        Tencent tencent = Tencent.createInstance(activity.getString(R.string.qq_app_id), activity.getApplication());
        tencent.shareToQzone(activity, bundle, mListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
    }
}
