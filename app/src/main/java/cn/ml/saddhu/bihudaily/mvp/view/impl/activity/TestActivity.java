package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/2/21.
 * Email static.sadhu@gmail.com
 */
@EActivity(R.layout.frag_test)
public class TestActivity extends AppCompatActivity {
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
