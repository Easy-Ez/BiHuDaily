package cn.ml.saddhu.bihudaily.widget.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/3/8.
 * Email static.sadhu@gmail.com
 */
public class DragFrameLayout extends FrameLayout {

    private static final int DEFAULT_DESCRIPTION_MIN_HEIGHT = 155;

    private float mDescriptionMinheight;
    private ViewDragHelper mViewDragHelper;
    private ViewPager viewPager;
    private LinearLayout mDescriptionContainer;
    private TextView mTvTitle;
    private TextView mTvCurrentIndex;
    private TextView mTvTotalIndex;
    private TextView mTvDescription;
    /**
     * 描述容器的top位置
     */
    private int mNewTop;
    /**
     * 描述容器的时机top位置,过长时会小于 {@link #mNewTop},过短时会大于{@link #mNewTop}
     */
    private int mActualTop;
    private float mDimension;
    private int mDescriptionBottomMargin;


    private PagerAdapter mPagerAdapter;


    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragFrameLayout);
        mDescriptionBottomMargin = typedArray.getDimensionPixelOffset(R.styleable.DragFrameLayout_description_bottom_margin, 0);
        typedArray.recycle();
        init();
    }


    private void init() {
        mDescriptionMinheight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_DESCRIPTION_MIN_HEIGHT, getContext().getResources().getDisplayMetrics());
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child.getId() == R.id.ll_drag_desciption_container;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (mNewTop > mActualTop) {
                    if (top > mNewTop) {
                        top = mNewTop;
                    } else if (top < mActualTop) {
                        top = mActualTop;
                    }
                }
                return top;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                Logger.i(child.toString());
                if (child.getId() == R.id.ll_drag_desciption_container) {
                    return mNewTop - mActualTop;
                }
                return super.getViewVerticalDragRange(child);
            }
        });
        viewPager = new ViewPager(getContext());
        addView(viewPager, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mDescriptionContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_drag_description_container, this, false);
        mTvTitle = (TextView) mDescriptionContainer.findViewById(R.id.tv_title);
        mTvCurrentIndex = (TextView) mDescriptionContainer.findViewById(R.id.tv_current_index);
        mTvTotalIndex = (TextView) mDescriptionContainer.findViewById(R.id.tv_total_index);
        mTvDescription = (TextView) mDescriptionContainer.findViewById(R.id.tv_description);
        addView(mDescriptionContainer);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mNewTop = (int) (bottom - mDescriptionMinheight - mDescriptionBottomMargin);
        mActualTop = bottom - mDescriptionContainer.getHeight() - mDescriptionBottomMargin;
        mDescriptionContainer.layout(mDescriptionContainer.getLeft(),
                mNewTop,
                mDescriptionContainer.getRight(),
                mNewTop + mDescriptionContainer.getHeight());
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter) {
        mPagerAdapter = pagerAdapter;
        viewPager.setAdapter(mPagerAdapter);
    }
}
