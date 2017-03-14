package cn.sadhu.guidelibrary.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.util.ArrayList;

import cn.sadhu.guidelibrary.R;
import cn.sadhu.guidelibrary.domain.GuideViewInfo;
import cn.sadhu.guidelibrary.domain.TargetViewInfo;

/**
 * Created by sadhu on 2017/3/14.
 * Email static.sadhu@gmail.com
 */
public class GuideViewManager {

    private Context mContext;
    private GuideViewInfo mGuideViewInfo;

    private GuideViewManager(Builder builder) {
        mContext = builder.mContext;
        mGuideViewInfo = builder.mGuideViewInfo;
    }

    public void show() {
        Intent intent = new Intent(mContext, GuideViewActivity.class);
        intent.putExtra(GuideViewActivity.EXTRA_LIST, mGuideViewInfo);
        mContext.startActivity(intent);
    }


    public static class Builder {
        private Context mContext;
        GuideViewInfo mGuideViewInfo;
        private ArrayList<TargetViewInfo> mStepTargetViewInfos;

        public Builder(Context context) {
            mContext = context;
            mGuideViewInfo = new GuideViewInfo();
            mGuideViewInfo.mBackgroundColor = mContext.getResources().getColor(R.color.background);
            mGuideViewInfo.mLists = new ArrayList<>();
            mStepTargetViewInfos = new ArrayList<>();
            mGuideViewInfo.mLists.add(mStepTargetViewInfos);
        }

        public Builder addTargetView(View view, @DrawableRes int recource) {
            return addTargetView(view, recource, TargetViewInfo.DEFAULT_CHILD_GRAVITY);
        }

        public Builder addTargetView(View view, @DrawableRes int recource, @TargetViewInfo.Gravity int gravity) {
            int[] localtion = new int[2];
            view.getLocationOnScreen(localtion);
            TargetViewInfo targetViewInfo = new TargetViewInfo();
            targetViewInfo.marginLeft = localtion[0];
            targetViewInfo.marginTop = localtion[1];
            targetViewInfo.resource = recource;
            targetViewInfo.gravity = gravity;
            mStepTargetViewInfos.add(targetViewInfo);
            return this;
        }

        public Builder addTargetView(TargetViewInfo targetViewInfo) {
            mStepTargetViewInfos.add(targetViewInfo);
            return this;
        }

        public Builder switchNextSetp() {
            if (mStepTargetViewInfos.size() != 0) {
                mStepTargetViewInfos = new ArrayList<>();
                mGuideViewInfo.mLists.add(mStepTargetViewInfos);
            }
            return this;
        }

        public Builder setBackgroundColor(@ColorInt int color) {
            mGuideViewInfo.mBackgroundColor = color;
            return this;
        }

        public GuideViewManager build() {
            if (mStepTargetViewInfos.size() == 0) {
                mGuideViewInfo.mLists.remove(mStepTargetViewInfos);
            }
            return new GuideViewManager(this);
        }

    }
}
