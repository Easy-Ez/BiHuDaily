package cn.sadhu.guidelibrary.domain;

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
 * Describe: 每张引导图信息封装
 */
public class TargetViewInfo implements Serializable {
    /**
     * 默认gravity为左上
     */
    public static final int DEFAULT_CHILD_GRAVITY = android.view.Gravity.TOP | android.view.Gravity.START;
    public int marginLeft;
    public int marginTop;
    public int marginRight;
    public int marginBottom;
    @DrawableRes
    public int resource;
    @TargetViewInfo.Gravity
    public int gravity = DEFAULT_CHILD_GRAVITY;

    public TargetViewInfo setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
        return this;
    }

    public TargetViewInfo setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        return this;
    }

    public TargetViewInfo setMarginRight(int marginRight) {
        this.marginRight = marginRight;
        return this;
    }

    public TargetViewInfo setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        return this;
    }

    public TargetViewInfo setResource(int resource) {
        this.resource = resource;
        return this;
    }


    /**
     * 设置引导图的gravity
     *
     * @param gravity {@link Gravity}
     * @return
     */
    public TargetViewInfo setGravity(@TargetViewInfo.Gravity int gravity) {
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
    public @interface Gravity {
    }
}
