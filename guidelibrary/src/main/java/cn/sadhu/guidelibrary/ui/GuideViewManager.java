package cn.sadhu.guidelibrary.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

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
            mGuideViewInfo.mIsFullMode = true;
            mGuideViewInfo.mLists = new ArrayList<>();
            mStepTargetViewInfos = new ArrayList<>();
            mGuideViewInfo.mLists.add(mStepTargetViewInfos);
        }

        /**
         * 在当层添加引导图
         *
         * @param targetViewInfo
         * @return
         */
        public Builder addTargetView(@NonNull TargetViewInfo targetViewInfo) {
            mStepTargetViewInfos.add(targetViewInfo);
            return this;
        }

        /**
         * 切换到下一层
         *
         * @return
         */
        public Builder switchNextSetp() {
            if (mStepTargetViewInfos.size() != 0) {
                mStepTargetViewInfos = new ArrayList<>();
                mGuideViewInfo.mLists.add(mStepTargetViewInfos);
            }
            return this;
        }

        /**
         * 设置背景色
         *
         * @param color
         * @return
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            mGuideViewInfo.mBackgroundColor = color;
            return this;
        }

        /**
         * 进入退出是否显示动画
         *
         * @param withAnimation
         * @return
         */
        public Builder setWithAnimation(boolean withAnimation) {
            mGuideViewInfo.mWithAnimation = withAnimation;
            return this;
        }

        /**
         * 是否为全屏模式
         *
         * @param isFullMode
         * @return
         */
        public Builder setIsFullMode(boolean isFullMode) {
            mGuideViewInfo.mIsFullMode = isFullMode;
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
