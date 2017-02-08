package cn.sadhu.daynightlibrary.widget;

import android.content.res.Resources;
import android.support.annotation.RawRes;
import android.view.View;

/**
 * Created by sadhu on 2017/2/8.
 * Email static.sadhu@gmail.com
 */
public interface DayNightInterface {
    public View getView();

    public void setTheme(Resources.Theme themeId);
}
