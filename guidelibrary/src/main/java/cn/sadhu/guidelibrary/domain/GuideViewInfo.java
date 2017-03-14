package cn.sadhu.guidelibrary.domain;

import android.support.annotation.ColorInt;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sadhu on 2017/3/14.
 * Email static.sadhu@gmail.com
 * 引导图相关的信息
 */
public class GuideViewInfo implements Serializable {
    @ColorInt
    public int mBackgroundColor;
    public ArrayList<ArrayList<TargetViewInfo>> mLists;
}
