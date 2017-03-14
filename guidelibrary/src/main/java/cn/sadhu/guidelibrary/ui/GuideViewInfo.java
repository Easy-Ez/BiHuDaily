package cn.sadhu.guidelibrary.ui;

import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sadhu on 2017/3/14.
 * Email static.sadhu@gmail.com
 */
public class GuideViewInfo implements Serializable {
    protected static final int DEFAULT_CHILD_GRAVITY = android.view.Gravity.TOP | android.view.Gravity.START;

    public int marginLeft;
    public int marginTop;
    public int marginRight;
    public int marginBottom;
    @DrawableRes
    public int resource;
    @Gravity
    public int gravity = DEFAULT_CHILD_GRAVITY;

    public GuideViewInfo setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
        return this;
    }

    public GuideViewInfo setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        return this;
    }

    public GuideViewInfo setMarginRight(int marginRight) {
        this.marginRight = marginRight;
        return this;
    }

    public GuideViewInfo setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        return this;
    }

    public GuideViewInfo setResource(int resource) {
        this.resource = resource;
        return this;
    }


    public GuideViewInfo setGravity(@Gravity int gravity) {
        this.gravity = gravity;
        return this;
    }



    @IntDef(flag = true, value = {
            android.view.Gravity.LEFT,
            android.view.Gravity.START,
            android.view.Gravity.TOP,
            android.view.Gravity.END,
            android.view.Gravity.RIGHT,
            android.view.Gravity.BOTTOM,
            android.view.Gravity.CENTER,
            android.view.Gravity.CENTER_HORIZONTAL,
            android.view.Gravity.CENTER_VERTICAL,})
    @Retention(RetentionPolicy.SOURCE)
    @Target(value = {ElementType.FIELD, ElementType.PARAMETER})
    @interface Gravity {
    }
}
