package cn.ml.saddhu.bihudaily.mvp.model;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public interface StoryListModel  extends BaseModel{
    void getHomePageList();

    void loadMoreHomePageList(String date);

    void setItemRead(String id);
}
