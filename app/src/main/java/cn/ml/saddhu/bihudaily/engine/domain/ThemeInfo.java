package cn.ml.saddhu.bihudaily.engine.domain;

import java.util.List;

/**
 * Created by sadhu on 2017/2/11.
 * Email static.sadhu@gmail.com
 * Describe: 主题
 */
public class ThemeInfo {
    public boolean subscribed;
    public String description;
    public String background;
    public String image;
    public long color;
    public String name;
    public List<Editor> editors;
    public List<BaseStory> stories;
}
