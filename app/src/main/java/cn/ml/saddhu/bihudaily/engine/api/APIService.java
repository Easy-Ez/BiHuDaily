package cn.ml.saddhu.bihudaily.engine.api;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
import cn.ml.saddhu.bihudaily.engine.domain.Creatives;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetail;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeInfo;
import cn.ml.saddhu.bihudaily.engine.domain.Themes;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by sadhu on 2016/11/8.
 * Email static.sadhu@gmail.com
 * Describe: 逼乎API
 */
public interface APIService {
    @GET("7/prefetch-launch-images/{size}")
    Call<Creatives> getSplashImage(@Path("size") String size);

    @GET("4/themes")
    Call<Themes> getThemes();

    @GET("4/stories/latest")
    Call<StoryInfo> getHomePageList();

    @GET("4/stories/before/{date}")
    Call<StoryInfo> loadMoreHomePageList(@Path(("date")) String date);


    @GET("4/theme/{themeId}")
    Call<ThemeInfo> getThemePageList(@Path("themeId") String id);

    @GET("/api/4/theme/{themeId}/before/{lastStoryId}")
    Call<ResponseBody> loadMoreThemePageList(@Path("themeId") String themeId, @Path("lastStoryId") String id);


    @GET("/api/4/story/{storyId}")
    Call<StoryDetail> getStoryDetail(@Path("storyId") String storyId);

    @GET("/api/4/story-extra/{storyId}")
    Call<StoryDetailExtra> getStoryDetailExtra(@Path("storyId") String storyId);


    @GET("/api/4/story/{storyId}/long-comments")
    Call<ResponseBody> getLongComments(@Path("storyId") String storyId);


    @GET("/api/4/story/{storyId}/long-comments/before/{commentId}")
    Call<ResponseBody> getMoreLongComments(@Path("storyId") String storyId, @Path("commentId") String commentId);

    @GET("/api/4/story/{storyId}/short-comments")
    Call<ResponseBody> getShortComments(@Path("storyId") String storyId);


    @GET("/api/4/story/{storyId}/short-comments/before/{commentId}")
    Call<ResponseBody> getMoreShortComments(@Path("storyId") String storyId, @Path("commentId") String commentId);

    @POST("/api/4/anonymous-login")
    Call<ResponseBody> anonymousLogin(@Body RequestBody body);
}
