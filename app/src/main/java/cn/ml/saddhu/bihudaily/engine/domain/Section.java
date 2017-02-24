package cn.ml.saddhu.bihudaily.engine.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sadhu on 2017/2/24.
 * Email static.sadhu@gmail.com
 */
public class Section {
    /**
     * thumbnail : http://pic4.zhimg.com/d26fbaaa7cafa663006a875f08583533.jpg
     * id : 28
     * name : 放映机
     */
    @SerializedName("description")
    private String description;
    @SerializedName(value = "id")
    private int id;
    @SerializedName(value = "name")
    private String name;
    @SerializedName(value = "stories")
    private List<Story> storyList;
    @SerializedName(value = "thumbnail")
    private String thumbnailUrl;
    @SerializedName(value = "timestamp")
    private int timestamp;


    @Override
    public boolean equals(Object obj) {
        return (((obj instanceof Section)) && this.timestamp != 0 && ((Section) obj).getTimestamp() != 0 && this.timestamp == ((Section) obj).getTimestamp());
    }

    public String getDescription() {
        return this.description;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        String name = this.name == null ? "" : this.name;
        return name;
    }

    public List<Story> getStoryList() {
        return this.storyList;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public int getTimestamp() {
        return this.timestamp;
    }
}
