package cn.ml.saddhu.bihudaily.engine.domain;

import com.google.gson.JsonObject;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.dbconverter.StringConverter;

/**
 * Created by sadhu on 2017/2/22.
 * Email static.sadhu@gmail.com
 * Describe:文章详情
 */
@Entity
public class StoryDetail {
    public String body;
    public String image_source;
    public String title;
    public String image;
    public String share_url;
    public String ga_prefix;
    private long sectionId;
    @ToOne(joinProperty = "sectionId")
    public Section section;
    private long themeId;
    @ToOne(joinProperty = "themeId")
    public Theme theme;
    public int type;
    @Id
    public Long id;
    @Convert(columnType = String.class, converter = StringConverter.class)
    public List<String> css;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1605558911)
    private transient StoryDetailDao myDao;



    @Generated(hash = 1175391695)
    public StoryDetail() {
    }

    @Generated(hash = 928242074)
    public StoryDetail(String body, String image_source, String title, String image, String share_url, String ga_prefix, long sectionId, long themeId,
            int type, Long id, List<String> css) {
        this.body = body;
        this.image_source = image_source;
        this.title = title;
        this.image = image;
        this.share_url = share_url;
        this.ga_prefix = ga_prefix;
        this.sectionId = sectionId;
        this.themeId = themeId;
        this.type = type;
        this.id = id;
        this.css = css;
    }

    @Generated(hash = 277658736)
    private transient Long section__resolvedKey;
    @Generated(hash = 2088424640)
    private transient Long theme__resolvedKey;


    public String sectionOrThemeInfo() {
        JsonObject v1 = new JsonObject();

        if (this.theme != null) {
            v1.addProperty("theme_id", this.theme.getId());
            v1.addProperty("theme_name", this.theme.getName());
            v1.addProperty("theme_image", this.theme.getThumbnail());
        }

        if (this.section != null) {
            v1.addProperty("section_id", this.section.getId());
            v1.addProperty("section_name", this.section.getName());
            v1.addProperty("section_thumbnail", this.section.getThumbnailUrl());
        }

        Boolean v0 = this.isThemeSubscribed();
        if (v0 == null) {
            v0 = false;
        }

        v1.addProperty("theme_subscribed", v0);
        return v1.toString().replaceAll("\"", "\\\\\"");
    }

    public Boolean isThemeSubscribed() {
        // TODO: 2017/2/24 是否订阅 
//        Boolean v0;
//        if(this.theme == null) {
//            v0 = null;
//        }
//        else {
//            Model v0_1 = new Select().from(ThemeLog.class).where("theme_id = ?", new Object[]{Integer.valueOf(this.theme.getId())}).executeSingle();
//            v0 = v0_1 == null ? Boolean.valueOf(false) : ((ThemeLog)v0_1).isSubscribed();
//        }
        return false;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return this.image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return this.share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return this.ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public long getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public long getThemeId() {
        return this.themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public List<String> getCss() {
        return this.css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1090999800)
    public Section getSection() {
        long __key = this.sectionId;
        if (section__resolvedKey == null || !section__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SectionDao targetDao = daoSession.getSectionDao();
            Section sectionNew = targetDao.load(__key);
            synchronized (this) {
                section = sectionNew;
                section__resolvedKey = __key;
            }
        }
        return section;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 423846855)
    public void setSection(@NotNull Section section) {
        if (section == null) {
            throw new DaoException("To-one property 'sectionId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.section = section;
            sectionId = section.getId();
            section__resolvedKey = sectionId;
        }
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 817646422)
    public Theme getTheme() {
        long __key = this.themeId;
        if (theme__resolvedKey == null || !theme__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ThemeDao targetDao = daoSession.getThemeDao();
            Theme themeNew = targetDao.load(__key);
            synchronized (this) {
                theme = themeNew;
                theme__resolvedKey = __key;
            }
        }
        return theme;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 144011341)
    public void setTheme(@NotNull Theme theme) {
        if (theme == null) {
            throw new DaoException("To-one property 'themeId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.theme = theme;
            themeId = theme.get_id();
            theme__resolvedKey = themeId;
        }
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

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 818141325)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStoryDetailDao() : null;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
