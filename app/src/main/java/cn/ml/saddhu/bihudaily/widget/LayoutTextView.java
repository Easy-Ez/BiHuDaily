package cn.ml.saddhu.bihudaily.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sadhu on 2017/3/26.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class LayoutTextView extends android.support.v7.widget.AppCompatTextView {
    private OnLayoutListener listener;

    public LayoutTextView(Context context) {
        super(context);
    }

    public LayoutTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (listener != null) {
            listener.onLayout(this);
        }
    }

    public void setOnLayoutListener(OnLayoutListener listener) {
        this.listener = listener;
    }

    public interface OnLayoutListener {
        void onLayout(TextView paramTextView);
    }

}
