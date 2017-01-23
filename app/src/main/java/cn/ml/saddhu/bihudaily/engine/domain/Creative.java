package cn.ml.saddhu.bihudaily.engine.domain;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sadhu on 2016/11/12.
 * Email static.sadhu@gmail.com
 * Describe: 启动页
 */
@Entity
public class Creative {
    @Id
    public String id;
    public String url;
    @SerializedName("start_time")
    public int startTime;
    public String text;
    public int type;
    @Generated(hash = 401708737)
    public Creative(String id, String url, int startTime, String text, int type) {
        this.id = id;
        this.url = url;
        this.startTime = startTime;
        this.text = text;
        this.type = type;
    }
    @Generated(hash = 565415880)
    public Creative() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getStartTime() {
        return this.startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
}
