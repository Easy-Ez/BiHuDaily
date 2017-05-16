package cn.ml.saddhu.bihudaily.engine.domain;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by sadhu on 2017/2/24.
 * Email static.sadhu@gmail.com
 */
@Entity
public class Section {
    /**
     * thumbnail : http://pic4.zhimg.com/d26fbaaa7cafa663006a875f08583533.jpg
     * id : 28
     * name : 放映机
     */
    @SerializedName("description")
    private String description;
    @Id
    @SerializedName(value = "id")
    private Long id;
    @SerializedName(value = "name")
    private String name;
    @ToMany(referencedJoinProperty = "sectionId")
    @SerializedName(value = "stories")
    private List<Story> storyList;
    @SerializedName(value = "thumbnail")
    private String thumbnailUrl;
    @SerializedName(value = "timestamp")
    private int timestamp;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1718547978)
    private transient SectionDao myDao;


    @Generated(hash = 728115606)
    public Section(String description, Long id, String name, String thumbnailUrl, int timestamp) {
        this.description = description;
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.timestamp = timestamp;
    }


    @Generated(hash = 111791983)
    public Section() {
    }


    @Override
    public boolean equals(Object obj) {
        return (((obj instanceof Section)) && this.timestamp != 0 && ((Section) obj).getTimestamp() != 0 && this.timestamp == ((Section) obj).getTimestamp());
    }


    public String getDescription() {
        return this.description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }


    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


    public int getTimestamp() {
        return this.timestamp;
    }


    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1450413893)
    public List<Story> getStoryList() {
        if (storyList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StoryDao targetDao = daoSession.getStoryDao();
            List<Story> storyListNew = targetDao._querySection_StoryList(id);
            synchronized (this) {
                if (storyList == null) {
                    storyList = storyListNew;
                }
            }
        }
        return storyList;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1970796438)
    public synchronized void resetStoryList() {
        storyList = null;
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 479686395)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSectionDao() : null;
    }

}
