package cn.ml.saddhu.bihudaily.engine.domain;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Story implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.images);
        dest.writeString(this.date);
        dest.writeInt(this.type);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeByte(this.multipic ? (byte) 1 : (byte) 0);
        dest.writeString(this.tagName);
        dest.writeByte(this.isTag ? (byte) 1 : (byte) 0);
    }

    protected Story(Parcel in) {
        this.images = in.createStringArrayList();
        this.date = in.readString();
        this.type = in.readInt();
        this.id = in.readString();
        this.title = in.readString();
        this.multipic = in.readByte() != 0;
        this.tagName = in.readString();
        this.isTag = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}
