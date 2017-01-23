package cn.ml.saddhu.bihudaily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by sadhu on 2016/12/5.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class ObservableScrollView extends ScrollView {
    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
