package cn.sadhu.share_library.callback;

import cn.sadhu.share_library.domain.ErrorInfo;
import cn.sadhu.share_library.domain.PlatformType;

/**
 * Created by sadhu on 2017/5/12.
 */
public interface IShareCallback {
    void onComplete(PlatformType type);

    void onError(PlatformType type, ErrorInfo errorInfo);

    void onCancel(PlatformType type);
}
