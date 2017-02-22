package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnToolBarNeedChangeListener;

/**
 * Created by sadhu on 2017/2/21.
 * Email static.sadhu@gmail.com
 * 文章详情Fragment
 */
@EFragment(R.layout.frag_story_detail)
public class StoryDetailFragment extends Fragment {
    @ViewById(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @ViewById(R.id.nested_scrollview)
    NestedScrollView scrollView;
    @ViewById(R.id.wb)
    WebView mWb;
    OnToolBarNeedChangeListener mListener;
    private float mHeaderHeight;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnToolBarNeedChangeListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    void afterViews() {
        calculateHeaderSize();
        mWb.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWb.setWebContentsDebuggingEnabled(true);
        }
        mWb.loadUrl("https://segmentfault.com/");
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Logger.d("getTop:" + appBarLayout.getTop());
                if (mListener != null && Math.abs(appBarLayout.getTop()) == mHeaderHeight) {
                    if (scrollY > oldScrollY) {
                        // scroll to top
                        mListener.setToolBarAlpha(0.0f);
                        mListener.hideToolbar(true);
                    } else if (scrollY < oldScrollY) {
                        // scroll to bottom
                        mListener.setToolBarAlpha(1.0f);
                        mListener.hideToolbar(false);
                    }
                }
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (mListener != null) {
                    if (verticalOffset == 0) {
                        mListener.setToolBarAlpha(1.0f);
                    } else if (Math.abs(verticalOffset) == mHeaderHeight) {
                        mListener.setToolBarAlpha(0.0f);
                    } else {
                        float alpha = (mHeaderHeight - Math.abs(verticalOffset)) / mHeaderHeight;
                        mListener.setToolBarAlpha(alpha);
                    }
                }

            }
        });
    }

    private void calculateHeaderSize() {
        TypedValue tv = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true);
        int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        mHeaderHeight = actionBarHeight + getResources().getDimension(R.dimen.story_header_height);
    }
}