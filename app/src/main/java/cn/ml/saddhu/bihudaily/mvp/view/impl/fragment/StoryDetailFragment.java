package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnToolBarNeedChangeListener;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetail;
import cn.ml.saddhu.bihudaily.engine.domain.StoryType;
import cn.ml.saddhu.bihudaily.engine.util.HTMLUtils;
import cn.ml.saddhu.bihudaily.engine.util.SharePreferenceUtil;
import cn.ml.saddhu.bihudaily.mvp.presenter.IStoryFragDetailPresenter;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.StoryFragDetailPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.IStoryDetailFragView;
import cn.ml.saddhu.bihudaily.widget.StoryWebView;

/**
 * Created by sadhu on 2017/2/21.
 * Email static.sadhu@gmail.com
 * 文章详情Fragment
 */
@EFragment(R.layout.frag_story_detail)
public class StoryDetailFragment extends LazyLoadForViewPageFragment implements IStoryDetailFragView {
    @ViewById(R.id.coordinator_layout)
    CoordinatorLayout coordinator_layout;
    @ViewById(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @ViewById(R.id.topHeader)
    RelativeLayout topHeader;
    @ViewById(R.id.nested_scrollview)
    NestedScrollView scrollView;

    @ViewById
    SimpleDraweeView sdv_cover;
    @ViewById
    TextView story_title;
    @ViewById
    TextView story_image_source;
    @ViewById(R.id.wb)
    StoryWebView mWb;

    @FragmentArg
    String mStoryId;

    OnToolBarNeedChangeListener mListener;
    private float mHeaderHeight;
    private IStoryFragDetailPresenter mPresenter;

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
        mPresenter = new StoryFragDetailPresenterImpl(this);
        coordinator_layout.setVisibility(View.GONE);
        calculateHeaderSize(true);
        mWb.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWb.setWebContentsDebuggingEnabled(true);
        }
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // 处理下误差
                if (mListener != null && Math.abs(Math.abs(appBarLayout.getTop()) - mHeaderHeight) <= 1) {
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

    private void calculateHeaderSize(boolean hasHeader) {
        TypedValue tv = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true);
        int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        mHeaderHeight = actionBarHeight + (hasHeader ? getResources().getDimension(R.dimen.story_header_height) : 0);
    }

    @Override
    public void setViewWithData(StoryDetail storyInfoDetail) {
        if (!TextUtils.isEmpty(storyInfoDetail.image)) {
            sdv_cover.setImageURI(storyInfoDetail.image);
            topHeader.setVisibility(View.VISIBLE);
            calculateHeaderSize(true);
        } else {
            calculateHeaderSize(false);
            topHeader.setVisibility(View.GONE);
        }
        story_title.setText(storyInfoDetail.title);
        story_image_source.setText(storyInfoDetail.image_source);

        if (!TextUtils.isEmpty(storyInfoDetail.body)) {
            String v1 = "file:///android_asset/";
            String body = storyInfoDetail.body;
            String v2 = "";
            // 大字体
            if (SharePreferenceUtil.isLargeText(getContext())) {
                v2 = v2 + "large ";
            }
            // 是否夜间模式
            if (SharePreferenceUtil.isDark(getContext())) {
                v2 = v2 + "night ";
            }

            v2 = String.format("<!doctype html><html><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width,user-scalable=no\"><link href=\"news_qa.min.css\" rel=\"stylesheet\"><script src=\"zepto.min.js\"></script><script src=\"img_replace.js\"></script><script src=\"video.js\"></script></head><body className=\"%s\" onload=\"onLoaded()\">", v2);
            // 大字体
            if (SharePreferenceUtil.isLargeText(getContext())) {
                v2 = v2 + "<script src=\"large-font.js\"></script>";
            }
            // 是否夜间模式
            if (SharePreferenceUtil.isDark(getContext())) {
                v2 = v2 + "<script src=\"night.js\"></script>";
            }

            v2 = v2 + body;
            if (storyInfoDetail.type == StoryType.NORMAL.value()) {
                v2 = v2 + String.format("<script src=\"show_bottom_link.js\"></script><script>show(\'%s\');</script>", storyInfoDetail.sectionOrThemeInfo());
            }

            mWb.loadDataWithBaseURL(v1, HTMLUtils.parseBody(getActivity(), v2 + "</body></html>"), "text/html", "UTF-8", null);
        } else if (!TextUtils.isEmpty(storyInfoDetail.share_url)) {
            mWb.loadUrl(storyInfoDetail.share_url);
        }
    }

    @Override
    public void loadDataWithBaseUrl(String htmlString) {
        mWb.loadDataWithBaseURL("file:///android_asset/", htmlString, "text/html", "UTF-8", null);
    }

    @Override
    public void loadUrl(String url) {
        mWb.loadUrl(url);
    }

    @Override
    void lazyLoadData() {
        coordinator_layout.setVisibility(View.VISIBLE);
        mPresenter.getStoryDetail(mStoryId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }


}