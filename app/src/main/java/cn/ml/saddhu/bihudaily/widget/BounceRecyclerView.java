package cn.ml.saddhu.bihudaily.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.orhanobut.logger.Logger;

/**
 * Created by sadhu on 2016/12/4.
 * Email static.sadhu@gmail.com
 * Describe: 快速触顶回弹效果
 */
public class BounceRecyclerView extends RecyclerView {

    private static final int MAX_BOUNCE_HEIGHT = 4;
    private ValueAnimator valueAnimator;
    private float mMaxBounceHeight;
    private boolean mAnimationFlag;// 惯性到顶部的时候 才允许动画

    public BounceRecyclerView(Context context) {
        this(context, null);
    }

    public BounceRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initBounceAnimation();
        mMaxBounceHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MAX_BOUNCE_HEIGHT, context.getResources().getDisplayMetrics());
    }

    private void initBounceAnimation() {
        valueAnimator = ObjectAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(150);
        valueAnimator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float x) {
                return (float) Math.sin(Math.PI * x);
            }
        });
        valueAnimator.cancel();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                Logger.d("tranlationY %f", animatedValue * mMaxBounceHeight);
                setTranslationY(animatedValue * mMaxBounceHeight);
            }
        });
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        // Logger.d("是否能向上滑动:" + canScrollVertically(-1) + ";当前是否允许动画:" + (mAnimationFlag));
        //惯性到顶部的情况 开启动画
        if (!canScrollVertically(-1) & mAnimationFlag) {
            valueAnimator.cancel();
            valueAnimator.start();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mAnimationFlag = false;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mAnimationFlag = true;
                break;
        }
        return super.onTouchEvent(e);
    }
}
