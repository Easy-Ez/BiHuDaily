package cn.ml.saddhu.bihudaily.widget;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.util.ActivityUtils;
import cn.ml.saddhu.bihudaily.engine.util.SharePreferenceUtil;

/**
 * Created by sadhu on 2016/12/5.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryWebView extends WebView {
    private Context mContext;
    private Story c;


    public StoryWebView(Context context) {
        this(context, null);
    }

    public StoryWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StoryWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        // 是否是夜间模式
        SharePreferenceUtil sp = new SharePreferenceUtil(mContext);
        if (sp.isDark()) {
            this.setBackgroundColor(Color.argb(1, 0, 0, 0));
        }
        this.getSettings().setAllowFileAccess(true);
        this.getSettings().setAppCacheEnabled(true);
        this.getSettings().setAppCachePath(context.getApplicationContext().getDir("cache", 0).getPath());
        this.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        this.getSettings().setLoadWithOverviewMode(true);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setDisplayZoomControls(false);
        this.getSettings().setLoadsImagesAutomatically(true);
        this.setWebViewClient(new MyWebViewClient(this, context));
        this.addJavascriptInterface(this, "ZhihuDaily");
    }

    public final void a(String methodName, String[] arguments) {
        this.loadUrl("javascript:" + methodName + "(" + ("\'" + TextUtils.join("\',\'", arguments) + "\'") + ");");
    }

    @JavascriptInterface
    public void clickToLoadImage(String path) {
//        if (!TextUtils.isEmpty(path)) {
//            this.post(new Runnable(path) {
//                public final void run() {
//                    com.c.a.b.e.c v0 = new com.c.a.b.e.c(this.a, new e(1, 1), com.c.a.b.a.h.b);
//                    d v1 = d.a();
//                    v1.a(false);
//                    v1.a(this.a, ((com.c.a.b.e.a) v0), StoryWebView.b(this.b), new com.c.a.b.f.c() {
//                        public final void a(String arg8, View arg9, Bitmap arg10) {
//                            String v0 = "file://" + d.a().c().a(arg8);
//                            try {
//                                a("onImageLoadingComplete", new String[]{URLEncoder.encode(this.a.a, StandardCharsets.UTF_8.name()), v0});
//                            } catch (UnsupportedEncodingException e) {
//
//                            }
//                        }
//                    });
//                }
//            });
//        }
    }

    @JavascriptInterface
    public void loadImage(String arg2) {
//        if (!TextUtils.isEmpty(((CharSequence) arg2))) {
//            this.post(new Runnable(arg2) {
//                public final void run() {
//                    com.c.a.b.e.c v0 = new com.c.a.b.e.c(new e(1, 1), com.c.a.b.a.h.b);
//                    d v1 = d.a();
//                    v1.a(com.zhihu.daily.android.h.b.d(StoryWebView.a(this.b)));
//                    v1.a(this.a, ((com.c.a.b.e.a) v0), StoryWebView.b(this.b), new com.c.a.b.f.c() {
//                        public final void a(String arg8, View arg9, Bitmap arg10) {
//                            String v0 = "file://" + d.a().c().a(arg8);
//                            try {
//                                this.a.b.a("onImageLoadingComplete", new String[]{URLEncoder.encode(this.a.a, Charsets.UTF_8.name()), v0});
//                            } catch (UnsupportedEncodingException v0_1) {
//                                com.zhihu.android.base.a.a.a.a(((Throwable) v0_1));
//                            }
//                        }
//                    });
//                }
//            });
    }
}

    @JavascriptInterface
    public void openImage(String arg4) {
//        Context v0 = this.getContext();
//        Intent v1 = new Intent(v0, ImageViewActivity_.class);
//        v1.putExtra("extra_image_url", arg4);
//        ActivityCompat.startActivity(((Activity) v0), v1, null);
    }


public final class MyWebViewClient extends WebViewClient {
    private Context mContext;

    public MyWebViewClient(StoryWebView webView, Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        long id = 1;
        boolean result = false;
        if (!TextUtils.isEmpty(url)) {
            Uri schemeUri = Uri.parse(url);
            if (url.contains("zhihu.com")) {
                ActivityUtils.startActivityByUrl(this.mContext, url);
                result = true;
            } else if (url.contains("zhihu-theme-subscribe")) {
                result = true;
            } else {
                if (url.startsWith("zhihudaily")) {
                    if (url.contains("theme")) {
                        //统计进入方式
                        //com.zhihu.daily.android.b.a.a("User Behavior", "Enter Theme From News Bottom", StoryWebView.c(this.a).getAnalyticsLabel(), Long.valueOf(id));
                    } else if (url.contains("section")) {
                        //统计进入方式
                        //com.zhihu.daily.android.b.a.a("User Behavior", "Enter Section From News Bottom", StoryWebView.c(this.a).getAnalyticsLabel(), Long.valueOf(id));
                    }
                }

                HitTestResult hitTestResult = view.getHitTestResult();
                if (hitTestResult == null) {
                    return result;
                }

                if (hitTestResult.getType() == HitTestResult.SRC_ANCHOR_TYPE) {
                    ActivityUtils.startActivityByUrl(this.mContext, url);
                    return true;
                }

                if (hitTestResult.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    ActivityUtils.startActivityByUrl(this.mContext, url);
                    return true;
                }

                if (hitTestResult.getType() != 0) {
                    return result;
                }

                if (TextUtils.isEmpty(view.getOriginalUrl())) {
                    return result;
                }

                if (schemeUri.getScheme() == null) {
                    return result;
                }

                if (!schemeUri.getScheme().equals("http") && !schemeUri.getScheme().equals("https")) {
                    return result;
                }

                if (url.contains(view.getOriginalUrl())) {
                    return result;
                }
                ActivityUtils.startActivityByUrl(this.mContext, url);
                result = true;
            }
        }

        return result;
    }
}
