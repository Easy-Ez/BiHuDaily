package cn.ml.saddhu.bihudaily.engine.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sadhu on 2017/2/22.
 * Email static.sadhu@gmail.com
 * Describe: 文章顶部的信息
 */
public class StoryDetailExtra implements Parcelable {
    /**
     * vote_status : 0
     * popularity : 2402
     * favorite : false
     * long_comments : 3
     * comments : 144
     * short_comments : 141
     */
    public int vote_status;
    public int popularity;
    public boolean favorite;
    public int long_comments;
    public int comments;
    public int short_comments;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.vote_status);
        dest.writeInt(this.popularity);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
        dest.writeInt(this.long_comments);
        dest.writeInt(this.comments);
        dest.writeInt(this.short_comments);
    }

    public StoryDetailExtra() {
    }

    protected StoryDetailExtra(Parcel in) {
        this.vote_status = in.readInt();
        this.popularity = in.readInt();
        this.favorite = in.readByte() != 0;
        this.long_comments = in.readInt();
        this.comments = in.readInt();
        this.short_comments = in.readInt();
    }

    public static final Parcelable.Creator<StoryDetailExtra> CREATOR = new Parcelable.Creator<StoryDetailExtra>() {
        @Override
        public StoryDetailExtra createFromParcel(Parcel source) {
            return new StoryDetailExtra(source);
        }

        @Override
        public StoryDetailExtra[] newArray(int size) {
            return new StoryDetailExtra[size];
        }
    };
}
