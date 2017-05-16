package cn.sadhu.share_library.excutor.share;

import android.app.Activity;
import android.content.Intent;

import cn.sadhu.share_library.callback.IShareCallback;
import cn.sadhu.share_library.domain.ShareInfoBean;

/**
 * Created by sadhu on 2017/5/12.
 * 所有具体分享实现此接口
 */
public interface IShareExcutor {
    /**
     * 分享
     *
     * @param activity
     * @param infoBean
     * @param callback
     */
    void share(Activity activity, ShareInfoBean infoBean, IShareCallback callback);

    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
