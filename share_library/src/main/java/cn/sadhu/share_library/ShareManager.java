package cn.sadhu.share_library;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import cn.sadhu.share_library.callback.IShareCallback;
import cn.sadhu.share_library.domain.PlatformType;
import cn.sadhu.share_library.domain.ShareInfoBean;
import cn.sadhu.share_library.excutor.share.IShareExcutor;
import cn.sadhu.share_library.excutor.share.QQShareExcutor;
import cn.sadhu.share_library.excutor.share.QQZoneShareExcutor;
import cn.sadhu.share_library.excutor.share.WeiboShareExcutor;

/**
 * Created by sadhu on 2017/5/12.
 * 分享manger
 */
public class ShareManager {
    private Map<PlatformType, IShareExcutor> mExcutorMap;
    private PlatformType mPlatformType;

    public ShareManager() {
        mExcutorMap = new HashMap<>();
    }

    public void onActivityResultData(int requestCode, int resultCode, Intent data) {
        if (mExcutorMap.get(mPlatformType) != null) {
            mExcutorMap.get(mPlatformType).onActivityResult(requestCode, resultCode, data);
        }
    }

    public void share(Activity activity, PlatformType type, ShareInfoBean infoBean, IShareCallback callback) {
        mPlatformType = type;
        IShareExcutor shareExcutor = getShareExcutor(type);
        shareExcutor.share(activity, infoBean, callback);
    }

    private IShareExcutor getShareExcutor(PlatformType type) {
        IShareExcutor excutor = mExcutorMap.get(type);
        if (excutor == null) {
            switch (type) {
                case QQ:
                    excutor = new QQShareExcutor();
                    break;
                case QQZOME:
                    excutor = new QQZoneShareExcutor();
                    break;
                case WECHAT:
                    break;
                case WECHAT_CIRCLE:
                    break;
                case WEIBO:
                    excutor = new WeiboShareExcutor();
                    break;
            }
            mExcutorMap.put(type, excutor);
        }
        return excutor;
    }

}
