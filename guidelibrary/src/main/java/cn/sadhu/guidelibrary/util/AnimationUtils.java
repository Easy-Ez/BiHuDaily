package cn.sadhu.guidelibrary.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.sadhu.guidelibrary.domain.TargetViewInfo;

/**
 * Created by sadhu on 2017/3/15.
 * Email static.sadhu@gmail.com
 * 动画工具类
 */
public class AnimationUtils {

    private List<TargetViewInfo> targetViewInfos;
    private AnimatorSet mAnimatorEnterSet;
    private AnimatorSet mAnimatorExitSet;
    private List<Animator> mAnimatorEnterList;
    private List<Animator> mAnimatorExitList;
    private List<Animator.AnimatorListener> mExitListener;
    private List<Animator.AnimatorListener> mEnterListener;

    public AnimationUtils() {
        init();
    }

    private void init() {
        mAnimatorEnterSet = new AnimatorSet();
        mAnimatorExitSet = new AnimatorSet();
        mAnimatorEnterList = new ArrayList<>();
        mAnimatorExitList = new ArrayList<>();
        mExitListener = new ArrayList<>();
        mEnterListener = new ArrayList<>();
        mAnimatorEnterSet.setDuration(500);
        mAnimatorExitSet.setDuration(500);
    }

    public AnimationUtils addEnterListener(Animator.AnimatorListener listener) {
        mEnterListener.add(listener);
        mAnimatorEnterSet.addListener(listener);
        return this;
    }

    public AnimationUtils addExitListener(Animator.AnimatorListener listener) {
        mExitListener.add(listener);
        mAnimatorExitSet.addListener(listener);
        return this;
    }

    public void preparedEnterAnimator() {
        mAnimatorExitSet.removeAllListeners();
        if (mAnimatorExitSet.isStarted()) {
            mAnimatorExitSet.cancel();
        }
        mAnimatorEnterList.clear();
    }

    public void startEnterAnimator() {
        Animator[] objectAnimators = mAnimatorEnterList.toArray(new Animator[mAnimatorEnterList.size()]);
        mAnimatorEnterSet.playTogether(objectAnimators);
        mAnimatorEnterSet.start();
    }

    public void preparedExitAnimator() {
        mAnimatorEnterSet.removeAllListeners();
        if (mAnimatorEnterSet.isStarted()) {
            mAnimatorEnterSet.cancel();
        }
        mAnimatorExitList.clear();
    }

    public void startExitAnimator() {
        Animator[] objectAnimators = mAnimatorExitList.toArray(new Animator[mAnimatorExitList.size()]);
        for (int i = 0; i < mExitListener.size(); i++) {
            mAnimatorExitSet.addListener(mExitListener.get(i));
        }
        mAnimatorExitSet.playTogether(objectAnimators);
        mAnimatorExitSet.start();
    }

    /**
     * 创建默认进入动画
     *
     * @param view
     * @return
     */
    public ObjectAnimator createEnterAnimator(ImageView view) {
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "ImageAlpha", 255);
        mAnimatorEnterList.add(animator);
        return animator;
    }

    /**
     * 创建默认退出动画
     *
     * @param view
     * @return
     */
    public ObjectAnimator createExitAnimator(ImageView view) {
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "ImageAlpha", 0);
        mAnimatorExitList.add(animator);
        return animator;
    }

    public boolean isRunning() {
        return mAnimatorExitSet.isRunning() || mAnimatorEnterSet.isRunning();
    }


    public void release() {
        mAnimatorEnterSet.cancel();
        mAnimatorExitSet.cancel();
        mAnimatorEnterSet.removeAllListeners();
        mAnimatorExitSet.removeAllListeners();
        mEnterListener.clear();
        mExitListener.clear();
    }
}
