package cn.ml.saddhu.bihudaily.engine.domain;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.dbconverter.StringConverter;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe: 文章列表item
 */
@Entity
public class Story {
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> images;
    public String date;
    public int type;
    @Id
    public String id;
    public String title;
    public boolean multipic;
    public String tagName;
    public boolean isTag;
    @Generated(hash = 883416079)
    public Story(List<String> images, String date, int type, String id,
            String title, boolean multipic, String tagName, boolean isTag) {
        this.images = images;
        this.date = date;
        this.type = type;
        this.id = id;
        this.title = title;
        this.multipic = multipic;
        this.tagName = tagName;
        this.isTag = isTag;
    }
    @Generated(hash = 922655990)
    public Story() {
    }
    public List<String> getImages() {
        return this.images;
    }
    public void setImages(List<String> images) {
        this.images = images;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public boolean getMultipic() {
        return this.multipic;
    }
    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }
    public String getTagName() {
        return this.tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public boolean getIsTag() {
        return this.isTag;
    }
    public void setIsTag(boolean isTag) {
        this.isTag = isTag;
    }


}
