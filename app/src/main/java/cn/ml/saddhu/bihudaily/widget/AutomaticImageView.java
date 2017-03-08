package cn.ml.saddhu.bihudaily.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Random;

/**
 * Created by sadhu on 2017/2/13.
 * Email static.sadhu@gmail.com
 */
public class AutomaticImageView extends android.support.v7.widget.AppCompatImageView {

    public static final String TAG = "AutomaticImageView";
    Matrix matrix = null;
    private float mScale;
    private float mDx;
    private float mDy;
    private float mCurrentDx;
    private float mCurrentDy;
    private int mMoveMode = MOVE_MODE_AUTO;
    private static final float SCALE_VIEW = 1.3F;
    private ValueAnimator mValueAnimator;

    public AutomaticImageView(Context context) {
        super(context);
    }

    public AutomaticImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutomaticImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (matrix != null) {
            Drawable drawable = getDrawable();
            matrix.reset();
            matrix.setScale(mScale, mScale);
            matrix.postTranslate(mCurrentDx, mCurrentDy);
            canvas.concat(matrix);
            drawable.draw(canvas);
        } else {
            if (getDrawable() != null && matrix == null) {
                calculateMatrix();
                initAnimator();
            }
        }
    }

    /**
     * 计算matrix(scale以及偏移dx,dy)
     */
    private void calculateMatrix() {
        Drawable drawable = getDrawable();
        matrix = new Matrix();
        final int dwidth = drawable.getIntrinsicWidth();
        final int dheight = drawable.getIntrinsicHeight();
        final float vwidth = getWidth() - getPaddingLeft() - getPaddingRight();
        final float vheight = getHeight() - getPaddingTop() - getPaddingBottom();
        float scale;// 计算宽/高 刚好填充view的scale 再scale SCALE_VIEW倍,保证有足够的大小移动
        if (dwidth * vheight > vwidth * dheight) {
            scale = vheight / (float) dheight * SCALE_VIEW;
            mDx = (vwidth - dwidth * scale) * 0.5f;
            mDy = (vheight - dheight * scale) * 0.5f;
        } else {
            scale = vwidth / (float) dwidth * SCALE_VIEW;
            mDx = (vwidth - dwidth * scale) * 0.5f;
            mDy = (vheight - dheight * scale) * 0.5f;
        }
        mScale = scale;
        // 先扩大,然后移动到center位置
        matrix.setScale(mScale, mScale);
        matrix.postTranslate(Math.round(mDx), Math.round(mDy));
    }

    /**
     * 初始化动画
     */
    private void initAnimator() {
        if (mValueAnimator == null) {
            mValueAnimator = ObjectAnimator.ofFloat(0, 1);
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.setInterpolator(new AccelerateInterpolator());
            mValueAnimator.setRepeatMode(mMoveMode == MOVE_MODE_AUTO ? ValueAnimator.RESTART : ValueAnimator.REVERSE);
            mValueAnimator.setDuration(12000);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    calculateTranlateByMode(animation.getAnimatedFraction());
                    invalidate();
                }
            });
            if (mMoveMode == MOVE_MODE_AUTO) {
                mValueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        randomMoveMode();
                    }
                });
            }
        }
        mValueAnimator.start();
    }

    /**
     * 如果是 {@link #MOVE_MODE_AUTO}模式 每次repeat需要random一下
     */
    private void randomMoveMode() {
        Random random = new Random();
        mMoveMode = (MOVE_MODE_LEFT << random.nextInt(4))
                | (MOVE_MODE_LEFT << random.nextInt(4));
    }

    /**
     * 根据移动模式 计算当前dx,dy的值
     *
     * @param animatedFraction 当前动画的进度
     */
    private void calculateTranlateByMode(float animatedFraction) {
        switch (mMoveMode) {
            case MOVE_MODE_AUTO:
                randomMoveMode();
                calculateTranlateByMode(animatedFraction);
                break;
            case MOVE_MODE_LEFT:
                mCurrentDx = mDx / 2 * (1f + animatedFraction);
                break;
            case MOVE_MODE_RIGHT:
                mCurrentDx = mDx / 2 * (1f - animatedFraction);
                break;
            case MOVE_MODE_TOP:
                mCurrentDy = mDy / 2 * (1f + animatedFraction);
                break;
            case MOVE_MODE_BOTTOM:
                mCurrentDy = mDy / 2 * (1f - animatedFraction);
                break;
            case MOVE_MODE_LEFT | MOVE_MODE_TOP:
                mCurrentDx = mDx / 2 * (1f + animatedFraction);
                mCurrentDy = mDy / 2 * (1f + animatedFraction);
                break;
            case MOVE_MODE_LEFT | MOVE_MODE_BOTTOM:
                mCurrentDx = mDx / 2 * (1f + animatedFraction);
                mCurrentDy = mDy / 2 * (1f - animatedFraction);
                break;
            case MOVE_MODE_RIGHT | MOVE_MODE_TOP:
                mCurrentDx = mDx / 2 * (1f - animatedFraction);
                mCurrentDy = mDy / 2 * (1f + animatedFraction);
                break;
            case MOVE_MODE_RIGHT | MOVE_MODE_BOTTOM:
                mCurrentDx = mDx / 2 * (1f - animatedFraction);
                mCurrentDy = mDy / 2 * (1f - animatedFraction);
                break;
            default:
                mCurrentDx = mDx / 2 * (1f - animatedFraction);
                mCurrentDy = mDy / 2 * (1f - animatedFraction);
                break;
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (matrix != null && mValueAnimator != null) {
            mValueAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
        super.setImageBitmap(bm);
        matrix = null;
        invalidate();
    }

    /**
     * 重新设置移动模式
     *
     * @param moveMode
     */
    public void setMoveMode(@MoveMode int moveMode) {
        mMoveMode = moveMode;
    }

    /**
     * 添加一个或者多个移动模式
     *
     * @param moveMode
     */
    public void addMoveMode(@MoveMode int moveMode) {
        mMoveMode |= moveMode;
    }

    /**
     * 删除一项或多项移动模式
     */
    public void removeMoveMode(@MoveMode int moveMode) {
        mMoveMode &= ~moveMode;
    }

    /**
     * 是否拥某些移动模式
     */
    public boolean isHas(@MoveMode int moveMode) {
        return (mMoveMode & moveMode) == moveMode;
    }

    /**
     * 是否禁用了某些移动模式
     */
    public boolean isNotHas(@MoveMode int moveMode) {
        return (mMoveMode & moveMode) == 0;
    }

    /**
     * 是否仅有某些移动模式
     */
    public boolean isOnlyHas(@MoveMode int moveMode) {
        return mMoveMode == moveMode;
    }

    public static final int MOVE_MODE_LEFT = 0x00000001;
    public static final int MOVE_MODE_RIGHT = MOVE_MODE_LEFT << 1;
    public static final int MOVE_MODE_TOP = MOVE_MODE_LEFT << 2;
    public static final int MOVE_MODE_BOTTOM = MOVE_MODE_LEFT << 3;
    public static final int MOVE_MODE_AUTO = MOVE_MODE_LEFT << 4;

    @IntDef(flag = true, value = {MOVE_MODE_LEFT, MOVE_MODE_RIGHT, MOVE_MODE_TOP, MOVE_MODE_BOTTOM, MOVE_MODE_AUTO})
    @Retention(RetentionPolicy.SOURCE)
    @Target(value = {ElementType.PARAMETER, ElementType.FIELD})
    @interface MoveMode {
    }

}


