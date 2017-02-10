package cn.ml.saddhu.bihudaily.engine.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe: 获取文章列表返回的info
 */
public class StoryInfo implements Parcelable {
    public String date;
    public List<Story> stories;
    @SerializedName("top_stories")
    public List<TopStory> topStories;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeList(this.stories);
        dest.writeList(this.topStories);
    }

    public StoryInfo() {
    }

    protected StoryInfo(Parcel in) {
        this.date = in.readString();
        this.stories = new ArrayList<Story>();
        in.readList(this.stories, Story.class.getClassLoader());
        this.topStories = new ArrayList<TopStory>();
        in.readList(this.topStories, TopStory.class.getClassLoader());
    }

    public static final Parcelable.Creator<StoryInfo> CREATOR = new Parcelable.Creator<StoryInfo>() {
        @Override
        public StoryInfo createFromParcel(Parcel source) {
            return new StoryInfo(source);
        }

        @Override
        public StoryInfo[] newArray(int size) {
            return new StoryInfo[size];
        }
    };
}
