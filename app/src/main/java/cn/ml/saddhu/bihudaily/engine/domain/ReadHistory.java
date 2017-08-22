package cn.ml.saddhu.bihudaily.engine.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by sadhu on 2017/5/16.
 */
@Entity
public class ReadHistory {
    @Id
    public Long id;
    @Unique
    public String StoryId;

    @Generated(hash = 141898613)
    public ReadHistory(Long id, String StoryId) {
        this.id = id;
        this.StoryId = StoryId;
    }

    @Generated(hash = 1863927908)
    public ReadHistory() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoryId() {
        return this.StoryId;
    }

    public void setStoryId(String StoryId) {
        this.StoryId = StoryId;
    }
}
