package cn.ml.saddhu.bihudaily.mvp.api;

import cn.ml.saddhu.bihudaily.engine.domain.Creatives;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.engine.domain.Themes;
import retrofit2.Call;
import retrofit2.http.GET;
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
}