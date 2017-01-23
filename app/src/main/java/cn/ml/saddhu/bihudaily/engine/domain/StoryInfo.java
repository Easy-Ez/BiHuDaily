package cn.ml.saddhu.bihudaily.engine.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe: 获取文章列表返回的info
 */
public class StoryInfo {
    public String date;
    public List<Story> stories;
    @SerializedName("top_stories")
    public List<TopStory> topStories;
}
