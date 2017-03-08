package cn.ml.saddhu.bihudaily.widget.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/3/6.
 * Email static.sadhu@gmail.com
 * 可展开的textview
 */
public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {

    /* The default animation duration */
    private static final int DEFAULT_ANIM_DURATION = 300;

    /* The default alpha value when the animation starts */
    private static final float DEFAULT_ANIM_ALPHA_START = 0.7f;

    private int mMaxShowLine;
    private boolean mCollapsed;
    private long mAnimationDuration = DEFAULT_ANIM_DURATION;
    private float mAnimAlphaStart = DEFAULT_ANIM_ALPHA_START;

    private String mContentText;
    private boolean mRelayout = true;
    private int mTextHeightWithMaxLines;
    private int mCollapsedHeight;
    private boolean mAnimating;


    public ExpandableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        mMaxShowLine = typedArray.getInt(R.styleable.ExpandableTextView_max_line, 1);
        mCollapsed = typedArray.getBoolean(R.styleable.ExpandableTextView_collapse, false);
        mContentText = typedArray.getString(R.styleable.ExpandableTextView_text);
        typedArray.recycle();
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!mAnimating) {
            mAnimating = true;
            mCollapsed = !mCollapsed;
            ObjectAnimator animator;
            if (mCollapsed) {
                animator = ObjectAnimator.ofInt(this, "height", getHeight(), mCollapsedHeight);
            } else {
                animator = ObjectAnimator.ofInt(this, "height", getHeight(), getHeight() +
                        mTextHeightWithMaxLines - getHeight());
            }
            animator.setDuration(mAnimationDuration);

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimating = false;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                }
            });
            animator.start();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // If no change, measure and return
        if (!mRelayout || getVisibility() == GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;

        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        setMaxLines(Integer.MAX_VALUE);

        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // If the text fits in collapsed mode, we are done.
        if (getLineCount() <= mMaxShowLine) {
            return;
        }

        // Saves the text height w/ max lines
        mTextHeightWithMaxLines = getRealTextViewHeight();

        // Doesn't fit in collapsed mode. Collapse text view as needed. Show
        // button.
        if (mCollapsed) {
            setMaxLines(mMaxShowLine);
        }
        // Re-measure with new setup
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getLineHeight() * mMaxShowLine);
        if (mCollapsed) {
            // Saves the collapsed height of this ViewGroup
            mCollapsedHeight = getMeasuredHeight();
        }
    }

    private int getRealTextViewHeight() {
        int textHeight = getLayout().getLineTop(getLineCount());
        int padding = getCompoundPaddingTop() + getCompoundPaddingBottom();
        return textHeight + padding;
    }

    public void setMyText(@Nullable CharSequence text) {
        clearAnimation();
        mRelayout = true;
        mCollapsed = true;
        setText(text);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        requestLayout();
    }


}



