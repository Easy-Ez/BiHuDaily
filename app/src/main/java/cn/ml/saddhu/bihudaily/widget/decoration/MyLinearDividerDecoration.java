package cn.ml.saddhu.bihudaily.widget.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import cn.ml.saddhu.bihudaily.R;

/**
 * recyclerView 通用的Decoration
 * 给每个item 设置 margin值
 */
public class MyLinearDividerDecoration extends RecyclerView.ItemDecoration {

    public static final String TAG = "OffestDecoration";


    private int mHeightOrWidth;
    private int mPaddingLeftOrTop;
    private int mPaddingRightOrBottom;
    private boolean mShowBottom;
    private Paint mRectPaint;
   // private Paint mPaddingPaint;

    public MyLinearDividerDecoration(Context ctx) {
        this(ctx, R.color.theme_editor_divider);
    }

    public MyLinearDividerDecoration(Context ctx, int mPaddingLeftOrTop, int mPaddingRightOrBottom) {
        this(ctx, R.color.theme_editor_divider, 1, mPaddingLeftOrTop, mPaddingRightOrBottom, false);
    }

    public MyLinearDividerDecoration(Context ctx, @ColorRes int color) {
        this(ctx, color, 1, 0, 0);
    }

    public MyLinearDividerDecoration(Context ctx, @ColorRes int color, int height, int mPaddingLeftOrTop, int mPaddingRightOrBottom) {
        this(ctx, color, height, mPaddingLeftOrTop, mPaddingRightOrBottom, false);
    }

    public MyLinearDividerDecoration(Context ctx, @ColorRes int color, int mHeightOrWidth, int mPaddingLeftOrTop, int mPaddingRightOrBottom, boolean showBottom) {
        this.mHeightOrWidth = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHeightOrWidth, ctx.getResources().getDisplayMetrics()) + 0.5f);
        this.mPaddingLeftOrTop = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPaddingLeftOrTop, ctx.getResources().getDisplayMetrics()) + 0.5f);
        this.mPaddingRightOrBottom = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPaddingRightOrBottom, ctx.getResources().getDisplayMetrics()) + 0.5f);
        this.mShowBottom = showBottom;
        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setColor(ContextCompat.getColor(ctx, color));
        mRectPaint.setStyle(Paint.Style.FILL);
//        if (mPaddingLeftOrTop != 0 || mPaddingRightOrBottom != 0) {
//            mPaddingPaint = new Paint();
//            mPaddingPaint.setAntiAlias(true);
//            mPaddingPaint.setColor(Color.TRANSPARENT);
//            mPaddingPaint.setStyle(Paint.Style.FILL);
//        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        int childPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        if (manager != null) {
            if (manager instanceof LinearLayoutManager && needDrawDivider(parent, view)) {
                // manager为LinearLayoutManager时
                setLinearOffset(((LinearLayoutManager) manager).getOrientation(), outRect, childPosition, itemCount);
            }
        }
    }

    public boolean needDrawDivider(RecyclerView parent, View view) {
        return true;
    }

    /**
     * LayoutManager 设置offset
     *
     * @param orientation   方向
     * @param outRect       padding
     * @param childPosition 在 list 中的 postion
     * @param itemCount     list size
     */
    private void setLinearOffset(int orientation, Rect outRect, int childPosition, int itemCount) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (childPosition != itemCount - 1 || mShowBottom) {
                outRect.set(0, 0, mHeightOrWidth, 0);
            }
        } else {
            if (childPosition != itemCount - 1 || mShowBottom) {
                outRect.set(0, 0, 0, mHeightOrWidth);
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int childCount = parent.getChildCount();
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {


            } else {
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    if (needDrawDivider(parent, child)) {
                        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                                .getLayoutParams();
                        //以下计算主要用来确定绘制的位置
                        int top = child.getBottom() + params.bottomMargin;
                        int bottom = top + mHeightOrWidth;
                        c.drawRect(
                                mPaddingLeftOrTop,
                                top,
                                parent.getWidth() - mPaddingRightOrBottom,
                                bottom,
                                mRectPaint);
//                        if (mPaddingPaint != null) {
//                            if (mPaddingLeftOrTop != 0) {
//                                c.drawRect(
//                                        0,
//                                        top,
//                                        mPaddingLeftOrTop,
//                                        bottom,
//                                        mPaddingPaint);
//                            } else if (mPaddingRightOrBottom != 0) {
//                                c.drawRect(
//                                        parent.getWidth() - mPaddingRightOrBottom,
//                                        top,
//                                        parent.getWidth(),
//                                        bottom,
//                                        mPaddingPaint);
//                            }
//                        }
                    }
                }
            }

        }
    }
}

