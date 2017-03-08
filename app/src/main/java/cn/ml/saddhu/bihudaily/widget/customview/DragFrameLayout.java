package cn.ml.saddhu.bihudaily.widget.customview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/3/8.
 * Email static.sadhu@gmail.com
 */
public class DragFrameLayout extends FrameLayout {


    private float mDescriptionMinheight;
    private ViewDragHelper mViewDragHelper;
    private ViewPager viewPager;
    private LinearLayout mDescriptionContainer;
    private TextView mTvTitle;
    private TextView mTvCurrentIndex;
    private TextView mTvTotalIndex;
    private TextView mTvDescription;
    private int mNewTop;
    private int mActualTop;


    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mDescriptionMinheight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getContext().getResources().getDisplayMetrics());
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child.getId() == R.id.ll_drag_desciption_container;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                com.orhanobut.logger.Logger.i("clampViewPositionVertical top %d", top);
                if (top > mNewTop) {
                    top = mNewTop;
                } else if (top < mActualTop) {
                    top = mActualTop;
                }
                return top;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
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
        if (mDescriptionMinheight < mDescriptionContainer.getHeight()) {
            mDescriptionMinheight = mDescriptionContainer.getHeight();
        }
        mNewTop = (int) (getHeight() - mDescriptionMinheight);
        mActualTop = bottom - mDescriptionContainer.getHeight();
        mDescriptionContainer.layout(mDescriptionContainer.getLeft(),
                mNewTop,
                mDescriptionContainer.getRight(),
                mNewTop + mDescriptionContainer.getHeight());
    }
}
