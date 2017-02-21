package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/2/21.
 * Email static.sadhu@gmail.com
 */
@EFragment(R.layout.frag_test)
public class TestFragment extends Fragment {
    @ViewById(R.id.wb)
    WebView mWb;

    @AfterViews
    void afterViews() {
        mWb.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWb.loadUrl("https://segmentfault.com/");
    }
}
