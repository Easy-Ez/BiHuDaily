package cn.sadhu.guidelibrary.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

import cn.sadhu.guidelibrary.R;
import cn.sadhu.guidelibrary.domain.GuideViewInfo;
import cn.sadhu.guidelibrary.domain.TargetViewInfo;
import cn.sadhu.guidelibrary.util.AnimationUtils;

/**
 * Created by sadhu on 2017/3/14.
 * Email static.sadhu@gmail.com
 */
public class GuideViewActivity extends AppCompatActivity {
    public static final String EXTRA_LIST = "extra_target_view_infos";
    FrameLayout fl_container;

    private GuideViewInfo mGuideViewInfo;
    private int mPeddingStep = 0;

    private AnimationUtils mAnimationUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGuideViewInfo = (GuideViewInfo) getIntent().getSerializableExtra(GuideViewActivity.EXTRA_LIST);
        if (mGuideViewInfo != null && mGuideViewInfo.mLists != null) {
            if (mGuideViewInfo.mIsFullMode) {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
                getWindow().setAttributes(attributes);
            }
            setContentView(R.layout.act_guide_view);
            if (mGuideViewInfo.mWithAnimation) {
                mAnimationUtils = new AnimationUtils();
                mAnimationUtils.addExitListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fl_container.removeAllViews();
                        addAllTargetView(mGuideViewInfo.mLists.get(mPeddingStep), true);

                    }
                });
            }
            fl_container = (FrameLayout) findViewById(R.id.fl_container);
            fl_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAnimationUtils == null || !mAnimationUtils.isRunning()) {
                        if (mGuideViewInfo.mLists.size() > 1 && mPeddingStep < mGuideViewInfo.mLists.size()) {
                            showTargetView(mGuideViewInfo.mLists.get(mPeddingStep));
                        } else {
                            finish();
                        }
                    }
                }
            });
            fl_container.setBackgroundColor(mGuideViewInfo.mBackgroundColor);
            showTargetView(mGuideViewInfo.mLists.get(mPeddingStep));
        } else {
            throw new RuntimeException("data is null");
        }
    }

    /**
     * 隐藏 引导图 动画结束后 开始显示下一层引导图
     */
    private void hideTargetView() {
        mAnimationUtils.preparedExitAnimator();
        for (int i = 0; i < fl_container.getChildCount(); i++) {
            ImageView imageView = (ImageView) fl_container.getChildAt(i);
            mAnimationUtils.createExitAnimator(imageView);
        }
        mAnimationUtils.startExitAnimator();
    }


    /**
     * 显示 引导图
     *
     * @param targetViewInfos
     */
    private void showTargetView(List<TargetViewInfo> targetViewInfos) {
        //如果已经添加过targerView 先执行退出动画 或者直接remove
        if (fl_container.getChildCount() > 0) {
            if (mGuideViewInfo.mWithAnimation) {
                // 执行退出动画,完成后执行进入动画
                hideTargetView();
            } else {
                // 直接remove
                fl_container.removeAllViews();
                // 直接add
                addAllTargetView(targetViewInfos, false);
            }
        } else {
            // 执行进入动画 或者直接add
            addAllTargetView(targetViewInfos, mGuideViewInfo.mWithAnimation);
        }

    }

    /**
     * 根据引导图信息 添加到屏幕上
     *
     * @param targetViewInfos 引导图信息
     * @param isWithAnimator  是否有动画效果
     */
    private void addAllTargetView(List<TargetViewInfo> targetViewInfos, boolean isWithAnimator) {
        if (isWithAnimator) {
            mAnimationUtils.preparedEnterAnimator();
        }
        for (TargetViewInfo info : targetViewInfos) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(info.marginLeft, info.marginTop, info.marginRight, info.marginBottom);
            params.gravity = info.gravity;
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(info.resource);
            fl_container.addView(imageView, params);
            if (isWithAnimator) {
                imageView.setImageAlpha(0);
                mAnimationUtils.createEnterAnimator(imageView);
            }
        }
        if (isWithAnimator) {
            mAnimationUtils.startEnterAnimator();
        }
        mPeddingStep++;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimationUtils != null) {
            mAnimationUtils.release();
        }
    }

}
