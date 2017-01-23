package cn.ml.saddhu.bihudaily.engine.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe: 左侧专栏bean
 */
@Entity
public class Theme implements Comparable<Theme> {
    public int color;
    public String thumbnail;
    public String description;
    @Id(autoincrement = true)
    public Long _id;
    public long id;
    public long owerId;
    public String name;
    public boolean isSubscribe;

    @Generated(hash = 612257047)
    public Theme(int color, String thumbnail, String description, Long _id, long id, long owerId,
            String name, boolean isSubscribe) {
        this.color = color;
        this.thumbnail = thumbnail;
        this.description = description;
        this._id = _id;
        this.id = id;
        this.owerId = owerId;
        this.name = name;
        this.isSubscribe = isSubscribe;
    }

    @Generated(hash = 979000295)
    public Theme() {
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwerId() {
        return this.owerId;
    }

    public void setOwerId(long owerId) {
        this.owerId = owerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsSubscribe() {
        return this.isSubscribe;
    }

    public void setIsSubscribe(boolean isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    @Transient
    private volatile int hashCode;

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 31;
            result = 31 * result + (int) (id ^ (id >>> 32));//long类型的数值
            result = 31 * result + (int) (owerId ^ (owerId >>> 32));//long类型的数值
            hashCode = result;
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Theme) && (id == ((Theme) obj).id) && (owerId == ((Theme) obj).owerId);
    }

    @Override
    public int compareTo(Theme o) {
        // 已关注的 排在最上面
        if (isSubscribe && !o.isSubscribe) {
            return -1;
        } else if (!isSubscribe && o.isSubscribe) {
            return 1;
        } else {
            //  都关注或者未关注 _id越小 越在上面
            return _id.compareTo(o._id);
        }
    }

    @Override
    public String toString() {
        return "Theme{" +
                "color=" + color +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", _id=" + _id +
                ", id=" + id +
                ", owerId=" + owerId +
                ", name='" + name + '\'' +
                ", isSubscribe=" + isSubscribe +
                ", hashCode=" + hashCode +
                '}';
    }
}
