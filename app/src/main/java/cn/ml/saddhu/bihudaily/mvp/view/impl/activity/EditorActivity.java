package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.lang.ref.WeakReference;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/2/15.
 * Email static.sadhu@gmail.com
 * Describe: 主编详情界面
 */
@EActivity(R.layout.act_editor)
public class EditorActivity extends AppCompatActivity {
    @Extra
    String mEditorId;
    @ViewById(R.id.toolbar_editor)
    Toolbar mToolbar;
    @ViewById(R.id.wb_editor_profit)
    WebView mWbProfile;

    @AfterViews
    void afterViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String url = getString(R.string.editor_profile_url, mEditorId);
        mWbProfile.getSettings().setDomStorageEnabled(true);
        mWbProfile.getSettings().setJavaScriptEnabled(true);
        mWbProfile.setWebViewClient(new MyWebViewClient(this));
        // 知乎没有处理这个问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWbProfile.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWbProfile.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    final class MyWebViewClient extends WebViewClient {
        private WeakReference<Context> mContextWeakReference;

        public MyWebViewClient(Context ctx) {
            super();
            mContextWeakReference = new WeakReference<Context>(ctx);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            try {
                if (mContextWeakReference.get() != null) {
                    mContextWeakReference.get().startActivity(intent);
                }
            } catch (ActivityNotFoundException e) {
                Logger.e(e.toString());
            }
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }

    }

}
