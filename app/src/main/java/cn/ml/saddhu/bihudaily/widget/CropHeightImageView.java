package cn.ml.saddhu.bihudaily.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by sadhu on 2016/11/9.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class CropHeightImageView extends ImageView {


    public CropHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(widthSize, (int) Math.ceil(widthSize * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth()));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }


}
