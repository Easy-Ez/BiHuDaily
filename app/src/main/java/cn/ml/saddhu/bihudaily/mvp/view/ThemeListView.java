package cn.ml.saddhu.bihudaily.mvp.view;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.domain.BaseStory;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeInfo;

/**
 * Created by sadhu on 2017/2/11.
 * Email static.sadhu@gmail.com
 * Describe: 主题list界面
 */
public interface ThemeListView extends BaseListView<ThemeInfo, List<BaseStory>> {
    void notifyItemChange(int position);
}
