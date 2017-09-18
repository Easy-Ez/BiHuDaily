package cn.ml.saddhu.bihudaily.mvp.model.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import cn.ml.saddhu.bihudaily.engine.api.APIHelper;
import cn.ml.saddhu.bihudaily.engine.api.APIService;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetLoadMoreListener;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.db.DBHelper;
import cn.ml.saddhu.bihudaily.engine.domain.BaseStory;
import cn.ml.saddhu.bihudaily.engine.domain.ReadHistory;
import cn.ml.saddhu.bihudaily.engine.domain.ReadHistoryDao;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeInfo;
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
public class ThemeListModelImpl extends BaseModelImpl<ThemeInfo, List<BaseStory>> implements ThemeListModel {
    private APIService mApiService;
    private final Gson mGson;
    private final Type mType;
    private final ReadHistoryDao mReadHistoryDao;

    public ThemeListModelImpl(OnNetRefreshListener<ThemeInfo> refreshListener, OnNetLoadMoreListener<List<BaseStory>> loadMoreListener) {
        super(refreshListener, loadMoreListener);
        mApiService = APIHelper.getInstance().create(APIService.class);
        mGson = new Gson();
        mType = new TypeToken<List<BaseStory>>() {
        }.getType();
        mReadHistoryDao = DBHelper.getInstance().getDaoSession().getReadHistoryDao();
    }


    @Override
    public void getThemePageList(String themeId) {
        Call<ThemeInfo> themePageListCall = mApiService.getThemePageList(themeId);
        mCalls.add(themePageListCall);
        themePageListCall.enqueue(new Callback<ThemeInfo>() {
            @Override
            public void onResponse(Call<ThemeInfo> call, Response<ThemeInfo> response) {
                if (mRefreshListener != null) {
                    ThemeInfo body = response.body();
                    if (body != null) {
                        // 是否已读
                        for (int i = 0; i < body.stories.size(); i++) {
                            if (mReadHistoryDao
                                    .queryBuilder()
                                    .where(ReadHistoryDao.Properties.StoryId.eq(body.stories.get(i).id))
                                    .unique() != null) {
                                body.stories.get(i).isRead = true;
                            }
                        }
                        mRefreshListener.onRefreshSuccess(body);
                    } else {
                        mRefreshListener.onRefreshError(1);
                    }
                }
            }

            @Override
            public void onFailure(Call<ThemeInfo> call, Throwable t) {
                if (mRefreshListener != null) {
                    // TODO: 2017/2/12  处理错误类型
                    mRefreshListener.onRefreshError(0);
                }
            }
        });
    }

    @Override
    public void loadMoreThemePageList(String themeId, String id) {
        Call<ResponseBody> baseStoryCall = mApiService.loadMoreThemePageList(themeId, id);
        mCalls.add(baseStoryCall);
        baseStoryCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (mLoadMoreListener != null) {
                    ResponseBody body = response.body();
                    JSONArray stories = null;
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        stories = jsonObject.getJSONArray("stories");
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        mLoadMoreListener.onLoadMoreError(1);
                    }
                    if (stories == null || stories.length() == 0) {
                        // TODO: 2017/2/12  处理空结果
                        mLoadMoreListener.onLoadMoreError(2);
                    } else {
                        List<BaseStory> baseStories = mGson.fromJson(stories.toString(), mType);
                        // 是否已读
                        for (int i = 0; i < baseStories.size(); i++) {
                            if (mReadHistoryDao
                                    .queryBuilder()
                                    .where(ReadHistoryDao.Properties.StoryId.eq(baseStories.get(i).id))
                                    .unique() != null) {
                                baseStories.get(i).isRead = true;
                            }
                        }
                        mLoadMoreListener.onLoadMoreSuccess(baseStories);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mLoadMoreListener != null) {
                    if (call.isCanceled()) {
                    } else {
                        // TODO: 2017/2/12  处理错误类型
                        mLoadMoreListener.onLoadMoreError(0);
                    }
                }
            }
        });

    }

    @Override
    public void setItemRead(String id) {
        ReadHistory readHistory = new ReadHistory();
        readHistory.setStoryId(id);
        mReadHistoryDao.insertOrReplace(readHistory);
    }

}
