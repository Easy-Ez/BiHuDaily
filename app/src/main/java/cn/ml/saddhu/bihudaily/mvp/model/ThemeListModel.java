package cn.ml.saddhu.bihudaily.mvp.model;

/**
 * Created by sadhu on 2017/2/12.
 * Email static.sadhu@gmail.com
 * Describe: 主题界面数据操作接口
 */
public interface ThemeListModel extends BaseModel {


    /**
     * 获取主题界面第一页的数据/刷新
     */
    void getThemePageList(String id);

    /**
     * 主题界面加载更多
     *
     * @param id 获取 id 之前的数据
     */
    void loadMoreThemePageList(String themeId, String id);


    /**
     * 设置已读
     *
     * @param id
     */
    void setItemRead(String id);
}
