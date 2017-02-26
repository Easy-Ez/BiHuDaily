package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import android.text.TextUtils;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetail;
import cn.ml.saddhu.bihudaily.engine.domain.StoryType;
import cn.ml.saddhu.bihudaily.engine.util.HTMLUtils;
import cn.ml.saddhu.bihudaily.engine.util.SharePreferenceUtil;
import cn.ml.saddhu.bihudaily.mvp.model.StoryFragModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.StoryFragModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.StoryFragDetailPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.StoryDetailFragView;

/**
 * Created by sadhu on 2017/2/25.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryFragDetailPresenterImpl implements StoryFragDetailPresenter, OnNetRefreshListener<StoryDetail> {
    private StoryDetailFragView mView;
    private StoryFragModel mModel;

    public StoryFragDetailPresenterImpl(StoryDetailFragView view) {
        this.mView = view;
        mModel = new StoryFragModelImpl(this);
    }

    @Override
    public void getStoryDetail(String storyId) {
        mModel.getStoryDetail(storyId);
    }

    @Override
    public void onRefreshSuccess(StoryDetail storyDetail) {
        mView.setViewWithData(storyDetail);
//        mModel.getBodyHtml(storyDetail,this);
//        if (!TextUtils.isEmpty(storyInfoDetail.body)) {
//            String v1 = "file:///android_asset/";
//            String body = storyInfoDetail.body;
//            String v2 = "";
//            // 大字体
//            if (SharePreferenceUtil.isLargeText(getContext())) {
//                v2 = v2 + "large ";
//            }
//            // 是否夜间模式
//            if (SharePreferenceUtil.isDark(getContext())) {
//                v2 = v2 + "night ";
//            }
//
//            v2 = String.format("<!doctype html><html><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width,user-scalable=no\"><link href=\"news_qa.min.css\" rel=\"stylesheet\"><script src=\"zepto.min.js\"></script><script src=\"img_replace.js\"></script><script src=\"video.js\"></script></head><body className=\"%s\" onload=\"onLoaded()\">", v2);
//            // 大字体
//            if (SharePreferenceUtil.isLargeText(getContext())) {
//                v2 = v2 + "<script src=\"large-font.js\"></script>";
//            }
//            // 是否夜间模式
//            if (SharePreferenceUtil.isDark(getContext())) {
//                v2 = v2 + "<script src=\"night.js\"></script>";
//            }
//
//            v2 = v2 + body;
//            if (storyInfoDetail.type == StoryType.NORMAL.value()) {
//                v2 = v2 + String.format("<script src=\"show_bottom_link.js\"></script><script>show(\'%s\');</script>", storyInfoDetail.sectionOrThemeInfo());
//            }
//
//            mWb.loadDataWithBaseURL(v1, HTMLUtils.parseBody(getActivity(), v2 + "</body></html>"), "text/html", "UTF-8", null);
//        } else if (!TextUtils.isEmpty(storyInfoDetail.share_url)) {
//            mWb.loadUrl(storyInfoDetail.share_url);
//        }
    }

    @Override
    public void onRefreshError(int code) {

    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }
}
