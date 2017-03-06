package cn.ml.saddhu.bihudaily.widget.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.ml.saddhu.bihudaily.R;


/**
 * Created by sadhu on 2017/2/28.
 * Email static.sadhu@gmail.com
 * 流式布局
 */
public class FlowLayout extends ViewGroup {

    private int mMaxLine;
    private int mSpanMargin;
    private int line = 1;
    private int mRealChildCount;
    private List<Integer> newLineIndexList;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        newLineIndexList = new ArrayList<>();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        mMaxLine = typedArray.getInt(R.styleable.FlowLayout_max_line, -1);
        mSpanMargin = typedArray.getDimensionPixelOffset(R.styleable.FlowLayout_span_margin, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        newLineIndexList.clear();
        line = 1;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        boolean isWidthExactly = widthMode == MeasureSpec.EXACTLY;
        boolean isHeightExactly = heightMode == MeasureSpec.EXACTLY;

        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        int freeTotalWidith = widthSize - getPaddingLeft() - getPaddingRight();
        for (int i = 0; i < getChildCount(); i++) {

            mRealChildCount = i + 1;
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;
            if (lineWidth + childWidth + (i == 0 ? 0 : mSpanMargin) <= freeTotalWidith) {
                lineWidth += childWidth + (i == 0 ? 0 : mSpanMargin);
                lineHeight = Math.max(lineHeight, childHeight);
            } else {
                line++;
                if (mMaxLine != -1 && line > mMaxLine) {
                    line = mMaxLine;
                    mRealChildCount--;
                    break;
                }
                // 有span在下一行
                newLineIndexList.add(i);
                // 上一行的宽
                width = Math.max(width, lineWidth);
                // 截止到上一行的高
                height += lineHeight + mSpanMargin;
                // 当前行的宽
                lineWidth = childWidth;
                // 当前行的高
                lineHeight = childHeight;
            }
        }
        width = Math.max(width, lineWidth);
        height += lineHeight;
        setMeasuredDimension(
                isWidthExactly ? widthSize : width + getPaddingLeft() + getPaddingRight(),
                isHeightExactly ? heightSize : height + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentLeft = l + getPaddingLeft();
        int curretnTop = t + getPaddingTop();
        int curretnBotton = curretnTop;
        int childLeft;
        int childRight;
        int childTop;
        int childBotton;
        int currentLine = 0;
        for (int i = 0; i < getRealChildCount(); i++) {
            if (currentLine < line
                    && newLineIndexList.size() > 0
                    && currentLine < newLineIndexList.size()
                    && i == newLineIndexList.get(currentLine)) {
                currentLine++;
                currentLeft = l + getPaddingLeft();
                curretnTop = curretnBotton + mSpanMargin;
            }
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            childLeft = currentLeft + layoutParams.leftMargin;
            childTop = curretnTop + layoutParams.topMargin;
            childRight = childLeft + child.getMeasuredWidth();
            childBotton = childTop + child.getMeasuredHeight();
            // 有换行的情况
            child.layout(childLeft, childTop, childRight, childBotton);
            currentLeft = childRight + layoutParams.leftMargin + mSpanMargin;
            curretnBotton = Math.max(curretnBotton, childBotton + layoutParams.bottomMargin);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public int getRealChildCount() {
        return mRealChildCount;
    }


}