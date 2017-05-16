package cn.sadhu.share_library.excutor.auth;

import android.app.Activity;

import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * Created by sadhu on 2017/5/13.
 * 微博授权登入
 */
public class WeiboAuthExcutor implements IAuthExcutor {

    private SsoHandler mSsoHandler;

    @Override
    public void auth(Activity activity) {
        mSsoHandler = new SsoHandler(activity);
        mSsoHandler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
            }

            @Override
            public void cancel() {

            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {

            }
        });
    }
}
