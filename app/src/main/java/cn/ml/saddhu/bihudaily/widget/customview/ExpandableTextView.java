package cn.ml.saddhu.bihudaily.widget.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/3/6.
 * Email static.sadhu@gmail.com
 * 可展开的textview
 */
public class ExpandableTextView extends FrameLayout {

    private int mMaxShowLine;
    private boolean mDefaultExpand;

    private boolean mHasMeasure;
    private int mMeasuredHeight;

    private int mExpandHeight;
    private int mCollapseHeight;


    private TextView mCollapseTextView;
    private TextView mExpandTextView;
    private String mContentText;
    private Status mStatus;
    private int parentWidthMeasureSpec;
    private int parentHeightMeasureSpec;
    private TextView mMeasureTextView;

    public enum Status {
        COLLAPSE, EXPAND, ANIMATING, SHORT
    }

    public ExpandableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        mMaxShowLine = typedArray.getInt(R.styleable.ExpandableTextView_max_line, 1);
        mDefaultExpand = typedArray.getBoolean(R.styleable.ExpandableTextView_expand, false);
        mContentText = typedArray.getString(R.styleable.ExpandableTextView_text);
        typedArray.recycle();


        mMeasureTextView = new TextView(context);
        mMeasureTextView.setText(mContentText);


        mCollapseTextView = new TextView(context);
        mCollapseTextView.setText(mContentText);
        mCollapseTextView.setMaxLines(mMaxShowLine);
        mCollapseTextView.setEllipsize(TextUtils.TruncateAt.END);

        mExpandTextView = new TextView(context);
        mExpandTextView.setText(mContentText);


        mStatus = mDefaultExpand ? Status.EXPAND : Status.COLLAPSE;
        addView(mCollapseTextView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mExpandTextView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMeasureTextView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mCollapseTextView.post(new Runnable() {
            @Override
            public void run() {
                mCollapseHeight = mCollapseTextView.getMeasuredHeight();
                mCollapseTextView.setVisibility(mDefaultExpand ? GONE : VISIBLE);
            }
        });

        mMeasureTextView.post(new Runnable() {
            @Override
            public void run() {
                if (mMeasureTextView.getLineCount() <= mMaxShowLine) {
                    mStatus = Status.SHORT;
                }
                mExpandHeight = mMeasureTextView.getMeasuredHeight();
                mExpandTextView.setVisibility(mDefaultExpand ? VISIBLE : GONE);
                mMeasureTextView.setVisibility(GONE);
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidthMeasureSpec = widthMeasureSpec;
        parentHeightMeasureSpec = heightMeasureSpec;
    }

    public void setText(@StringRes int resid) {
        setText(getContext().getResources().getText(resid));
    }

    public void setText(CharSequence text) {

        mExpandTextView.setVisibility(VISIBLE);
        mMeasureTextView.setVisibility(VISIBLE);
        mCollapseTextView.setVisibility(VISIBLE);

        mExpandTextView.setText(text);
        mMeasureTextView.setText(text);
        mCollapseTextView.setText(text);

        mCollapseTextView.post(new Runnable() {
            @Override
            public void run() {
                mCollapseHeight = mCollapseTextView.getMeasuredHeight();
                mCollapseTextView.setVisibility(mStatus == Status.COLLAPSE ? VISIBLE : GONE);
            }
        });

        mMeasureTextView.post(new Runnable() {
            @Override
            public void run() {
                if (mMeasureTextView.getLineCount() <= mMaxShowLine) {
                    mStatus = Status.SHORT;
                }
                mExpandHeight = mMeasureTextView.getMeasuredHeight();
                mExpandTextView.setVisibility(mStatus == Status.EXPAND ? VISIBLE : GONE);
                mExpandTextView.setHeight(mExpandHeight);
                mMeasureTextView.setVisibility(GONE);
            }
        });

    }

    public void toggle() {
        switch (getStatus()) {
            case COLLAPSE:
                expandTextView();
                break;
            case EXPAND:
                collapseTextView();
                break;
        }
    }

    /**
     * 收起
     */
    public void collapseTextView() {
        mExpandTextView.setHeight(mExpandHeight);
        ObjectAnimator collapseAnimator = ObjectAnimator.ofInt(mExpandTextView, "height", mExpandHeight, mCollapseHeight);
        collapseAnimator.setDuration(300);
        collapseAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = Status.ANIMATING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = Status.COLLAPSE;
                mExpandTextView.setVisibility(GONE);
                mCollapseTextView.setVisibility(VISIBLE);
            }
        });
        collapseAnimator.start();
    }

    /**
     * 展开
     */
    public void expandTextView() {
        mExpandTextView.setHeight(mCollapseHeight);
        ObjectAnimator expandAnimator = ObjectAnimator.ofInt(mExpandTextView, "height", mCollapseHeight, mExpandHeight);
        expandAnimator.setDuration(300);
        expandAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = Status.ANIMATING;
                mExpandTextView.setVisibility(VISIBLE);
                mCollapseTextView.setVisibility(GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = Status.EXPAND;
            }
        });
        expandAnimator.start();
    }

    public Status getStatus() {
        return mStatus;
    }
}
