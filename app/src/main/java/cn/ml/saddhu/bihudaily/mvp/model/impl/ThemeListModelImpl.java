package cn.ml.saddhu.bihudaily.mvp.model.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import cn.ml.saddhu.bihudaily.engine.domain.BaseStory;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeInfo;
import cn.ml.saddhu.bihudaily.mvp.api.APIHelper;
import cn.ml.saddhu.bihudaily.mvp.api.APIService;
import cn.ml.saddhu.bihudaily.mvp.model.ThemeListModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2017/2/12.
 * Email static.sadhu@gmail.com
 * Describe:主题界面数据操作接口实现类
 */
public class ThemeListModelImpl implements ThemeListModel {
    private APIService mApiService;
    private final Gson mGson;
    private final Type mType;

    public ThemeListModelImpl() {
        mApiService = APIHelper.getInstance().create(APIService.class);
        mGson = new Gson();
        mType = new TypeToken<List<BaseStory>>() {
        }.getType();
    }

    @Override
    public void getThemePageList(final OnRefreshListener onRefreshListener, String themeId) {
        Call<ThemeInfo> themePageListCall = mApiService.getThemePageList(themeId);
        themePageListCall.enqueue(new Callback<ThemeInfo>() {
            @Override
            public void onResponse(Call<ThemeInfo> call, Response<ThemeInfo> response) {
                if (onRefreshListener != null) {
                    ThemeInfo body = response.body();
                    if (body != null) {
                        onRefreshListener.onSuccuss(body);
                    } else {
                        onRefreshListener.onError(1);
                    }
                }
            }

            @Override
            public void onFailure(Call<ThemeInfo> call, Throwable t) {
                if (onRefreshListener != null) {
                    // TODO: 2017/2/12  处理错误类型
                    onRefreshListener.onError(0);
                }
            }
        });
    }

    @Override
    public void loadMoreThemePageList(final OnLoadMoreListener onLoadMoreListener, String themeId, String id) {
        Call<ResponseBody> baseStoryCall = mApiService.loadMoreThemePageList(themeId, id);
        baseStoryCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (onLoadMoreListener != null) {
                    ResponseBody body = response.body();
                    JSONArray stories = null;
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        stories = jsonObject.getJSONArray("stories");
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        onLoadMoreListener.onError(1);
                    }
                    if (stories == null || stories.length() == 0) {
                        // TODO: 2017/2/12  处理空结果
                        onLoadMoreListener.onError(2);
                    } else {
                        List<BaseStory> baseStories = mGson.fromJson(stories.toString(), mType);
                        onLoadMoreListener.onSuccuss(baseStories);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (onLoadMoreListener != null) {
                    // TODO: 2017/2/12  处理错误类型
                    onLoadMoreListener.onError(0);
                }
            }
        });

    }
}
