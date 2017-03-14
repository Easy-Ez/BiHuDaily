package cn.sadhu.guidelibrary.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by sadhu on 2017/3/14.
 * Email static.sadhu@gmail.com
 */
public class GuideViewManager {

    private Context mContext;
    private ArrayList<ArrayList<GuideViewInfo>> mLists;

    private GuideViewManager(Builder builder) {
        mContext = builder.mContext;
        mLists = builder.mLists;
    }

    public void show() {
        Intent intent = new Intent(mContext, GuideViewActivity.class);
        intent.putExtra(GuideViewActivity.EXTRA_LIST, mLists);
        mContext.startActivity(intent);
    }


    public static class Builder {
        private Context mContext;
        private ArrayList<ArrayList<GuideViewInfo>> mLists;
        private ArrayList<GuideViewInfo> mGuideViewInfos;

        public Builder(Context context) {
            mContext = context;
            mGuideViewInfos = new ArrayList<>();
            mLists = new ArrayList<>();
            mLists.add(mGuideViewInfos);
        }

        public Builder addTargetView(View view, @DrawableRes int recource) {
            return addTargetView(view, recource, GuideViewInfo.DEFAULT_CHILD_GRAVITY);
        }

        public Builder addTargetView(View view, @DrawableRes int recource, @GuideViewInfo.Gravity int gravity) {
            int[] localtion = new int[2];
            view.getLocationOnScreen(localtion);
            GuideViewInfo guideViewInfo = new GuideViewInfo();
            guideViewInfo.marginLeft = localtion[0];
            guideViewInfo.marginTop = localtion[1];
            guideViewInfo.resource = recource;
            guideViewInfo.gravity = gravity;
            mGuideViewInfos.add(guideViewInfo);
            return this;
        }

        public Builder addTargetView(GuideViewInfo guideViewInfo) {
            mGuideViewInfos.add(guideViewInfo);
            return this;
        }

        public Builder switchNextSetp() {
            if (mGuideViewInfos.size() != 0) {
                mGuideViewInfos = new ArrayList<>();
                mLists.add(mGuideViewInfos);
            }
            return this;
        }

        public GuideViewManager build() {
            return new GuideViewManager(this);
        }

    }
}
